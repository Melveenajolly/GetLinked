package com.springboot.getlinked.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@Entity
@Table(name = "users")
@com.springboot.getlinked.validation.PasswordMatch(message="{register.repeatpassword.mismatch}")
public class User {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "email", unique=true)
	@Email(message="{register.email.invalid}")
	@NotBlank(message="{register.email.invalid}")
	private String email;
	
	 
	@Transient
	@Size(min=5, max=15, message="{register.password.size}")
	@NotBlank(message="Password cannot be empty/whitespace")
	private String plainPassword;

	@Transient
	private String repeatPassword;
	
	@Column(name = "password", length =60)
	private String password;
	
	@Column(name="role", length=20)
	private String role;

	@Column(name="address")
	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		//encrypted password will be stored to database
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

}