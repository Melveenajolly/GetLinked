package com.springboot.getlinked.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "hireinfo")
public class HireInfo {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "Date")
//	@Temporal(TemporalType.TIMESTAMP)
//	@DateTimeFormat(pattern="yyyy/MM/dd hh:mm:ss")
	private Date date;

	@Column(name = "username")
	private String username;

	@Column(name = "professionalid")
	private Long professionalid;

	@Column(name = "hireaddress")
	private String hireaddress;

	@Column(name = "hireDate")
//	@DateTimeFormat(pattern = "dd-MM-yyyyTHH:mm")
	private String hireDate;

	@Column(name = "cardnumber")
	private String cardnumber;

	@Column(name = "cardexpiry")
	private String cardexpiry;

	@Column(name = "cvv")
	private String cvv;

	@Column(name = "category")
	private String category;

	@Column(name = "professionalContact")
	private String professionalContact;
	
	@Column(name = "userContact")
	private String userContact;
	
	@Column(name = "isCompleted", columnDefinition = "integer default '0'")
	private int isCompleted;
	
	public int getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(int isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProfessionalContact() {
		return professionalContact;
	}

	public void setProfessionalContact(String professionalContact) {
		this.professionalContact = professionalContact;
	}

	

//	@Column(name = "estimatedTimeInDays")
//	private String estimatedTimeInDays;
//	
//	@Column(name = "estimatedTimeInHours")
//	private String estimatedTimeInHours;
	
	public String getUserContact() {
		return userContact;
	}

	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getCardexpiry() {
		return cardexpiry;
	}

	public void setCardexpiry(String cardexpiry) {
		this.cardexpiry = cardexpiry;
	}

	public String getCvv() {
		return cvv;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getHireaddress() {
		return hireaddress;
	}

	public void setHireaddress(String hireaddress) {
		this.hireaddress = hireaddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
}