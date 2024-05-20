package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exam.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//JPA method to get user with userName field
	public Optional<User> findByuserName(String userName);

}
