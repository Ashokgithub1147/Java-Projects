package com.exam.utilities;

import java.io.Serializable;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtility implements Serializable{

private static final long serialVersionUID = 50185165626007484L;
	
	//token is valid upto 12 hours
	public static final long JWT_TOKEN_VALIDITY = 12*60*60;

	//secret pass code to validate JWT token
	@Value("${jwt.secret}")
	private String secret;
	
	/**
	 * This method will return userName from JWT token
	 * @param token
	 * @return String (userName)
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	/**
	 * This method will return issued date of JWT token
	 * @param token
	 * @return Date
	 */
	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}
	/**
	 * This method will return expiration date of JWT token
	 * @param token
	 * @return Date
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}	

	/**
	 * This method will return Claim from token based on claimResolver
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return <T> T (Claim collected from token)
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	/**
	 * We will get all claims from token and return the Claims
	 * @param token
	 * @return Claims
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * Checking JWT token expiration in this method
	 * @param token
	 * @return Boolean (true/false)
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	/**
	 * ignoreTokenExpiration method
	 * @param token
	 * @return Boolean (true/false)
	 */
	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	/**
	 * This method will generates the jwt token where we pass claims and usernaName to generate token
	 * @param userDetails
	 * @return String
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", populateAuthorities(userDetails.getAuthorities()));
		return doGenerateToken(claims, userDetails.getUsername());
	}

	/**
	 * The actual method which will generate JWT token using HS512 signature algorithm 
	 * and returns jwt token as string
	 * @param claims
	 * @param subject
	 * @return String (JWT token)
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
				.signWith(getSignInKey(),SignatureAlgorithm.HS512)
				.compact();
	}
	/**
	 * This method will check whether JWT token can be refreshed or not
	 * @param token
	 * @return Boolean (true/false)
	 */
	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}
	//method to validate jwt token passed through api request
	/**
	 * This method will validate JWT token
	 * @param token
	 * @param userDetails
	 * @return Boolean (true/false)
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	/**
	 * This method is used to decode sign key with Base64 decoder
	 * @return Key
	 */
	private Key getSignInKey() {
		byte [] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	/**
	 * This method is used to populate authorities in JWT token
	 * @param authorities
	 * @return Set<String>
	 */
	private Set<String> populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> authoritiesSet = new HashSet<>();
		authoritiesSet = authorities.stream().map(authority->authority.getAuthority()).collect(Collectors.toSet());
//		return String.join(",", authoritiesSet);
		return authoritiesSet;
	}
}
