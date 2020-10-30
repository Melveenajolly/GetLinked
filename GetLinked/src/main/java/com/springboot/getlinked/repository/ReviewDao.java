package com.springboot.getlinked.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.getlinked.model.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {

	public List<Review> findByProfessionalid(Long professionalid);
}
