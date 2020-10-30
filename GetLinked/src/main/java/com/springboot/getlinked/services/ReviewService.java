package com.springboot.getlinked.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.getlinked.model.Professional;
import com.springboot.getlinked.model.Review;
import com.springboot.getlinked.repository.ReviewDao;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewDao reviewDao;
	
	//save review
	public void saveReview(Review review) {
		reviewDao.save(review);
	}
	//reviews of a specific professional
	public List<Review> getByProfessionalid(Long professionalid)	{
		return reviewDao.findByProfessionalid(professionalid);
	}

}
