package com.springboot.getlinked.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.getlinked.model.User;



@Repository
public interface UserDao extends JpaRepository<User, Long> {
	User findByEmail(String email);

}
