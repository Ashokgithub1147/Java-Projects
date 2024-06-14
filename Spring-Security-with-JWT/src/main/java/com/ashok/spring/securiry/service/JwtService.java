package com.ashok.spring.securiry.service;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.function.Function;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String secretKey = "sdjlf234kjjhjkdahjadasjhbdads343k34dkfdkjsfdkjfdsakjdskfhkskafhkdaksfadhkafkfadkfsakdfajkfakdafsfkdjkfdadkjfdskakfdahkfd";
	/**
	 * This method will generate and return JWT Token
	 * @param userDetails
	 * @return String
	 */
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("authorities", populateAuthorities(userDetails.getAuthorities()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+86400000))
				.signWith(getSignInKey(), SignatureAlgorithm.HS512)
				.compact();
	}
	private Claims extractAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	public String getUserNameFromToken(String token) {
		return this.getClaimFromToken(token,Claims::getSubject);
	}
	private Key getSignInKey() {
		byte [] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> authoritiesSet = new HashSet<>();
		authoritiesSet = authorities.stream().map(authority->authority.getAuthority()).collect(Collectors.toSet());
		return String.join(",", authoritiesSet);
	}
	
}
