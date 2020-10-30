package com.springboot.getlinked.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reviews")
public class Review {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@Column(name = "username")
	private String username;
	
	@Column(name = "professionalid")
	private Long professionalid;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getProfessionalid() {
		return professionalid;
	}

	public void setProfessionalid(Long professionalid) {
		this.professionalid = professionalid;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "rating")
	private int rating;
	
	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy/MM/dd hh:mm:ss")
	private Date date;
	
	@Column(name = "comment")
	private String comment;

}
