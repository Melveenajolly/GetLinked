package com.springboot.getlinked.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Entity
@Table(name = "professional")
public class Professional {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "photo_directory", length = 10)
	private String photoDirectory;

	@Column(name = "photo_name", length = 10)
	private String photoName;

	@Column(name = "photo_extension", length = 5)
	private String photoExtension;
	
	@Column(name = "cert_extension", length = 5)
	private String certExtension;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "phonenumber")
	private Long phonenumber;

	@Column(name = "email", unique = true)
	private String email;

	@Transient
	private String plainPassword;

	@Transient
	private String repeatPassword;

	@Column(name = "password", length = 60)
	private String password;

	@Column(name = "categoryid", columnDefinition = "integer default '0'")
	private int categoryid;

	@Column(name = "role", length = 20)
	private String role;

	@Column(name = "location")
	private String location;

	@Column(name = "housenumber")
	private String housenumber;

	@Column(name = "streetname")
	private String streetname;

	@Column(name = "county")
	private String county;

	@Column(name = "postcode")
	private String postcode;

	@Column(name = "city")
	private String city;

	@Column(name = "verified", columnDefinition = "integer default '0'")
	private int verified;

	@Column(name = "categoryname")
	private String categoryname;

	@Column(name = "lat")
	private String lat;

	@Column(name = "lng")
	private String lng;
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public int getVerified() {
		return verified;
	}

	public void setVerified(int verified) {
		this.verified = verified;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(Long phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.password = new BCryptPasswordEncoder().encode(plainPassword);
		this.plainPassword = plainPassword;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	public String getStreetname() {
		return streetname;
	}

	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhotoDirectory() {
		return photoDirectory;
	}

	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoExtension() {
		return photoExtension;
	}

	public void setPhotoExtension(String photoExtension) {
		this.photoExtension = photoExtension;
	}
	
	public String getCertExtension() {
		return certExtension;
	}

	public void setCertExtension(String certExtension) {
		this.certExtension = certExtension;
	}

	public void setPhotoDetails(FileInfo info) {
		photoDirectory = info.getSubDirectory();
		photoExtension = info.getExtension();
		photoName = info.getBasename();
	}

}
