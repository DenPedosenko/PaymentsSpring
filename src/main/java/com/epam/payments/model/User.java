package com.epam.payments.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 
	@Column(name="first_name") 
	@NotBlank(message = "First name cannot be blank")
	private String firstName;
	
	@Column(name="last_name")
	@NotBlank(message = "Last name cannot be blank")
	private String lastName;
	
	@NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
	private String email;
	
	@Column(name="user_password")
	@NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should have at least 8 characters")
	private String password;
	
	@OneToMany(mappedBy="user")
	private List<UserAccount> accounts;
	
	@ManyToOne
	@JoinColumn(name="user_type_id")
	private UserType userType;
	
	@ManyToOne
	@JoinColumn(name="user_status_id")
	private UserStatus userStatus;

	public String getUserName() {
		return firstName + " " + lastName;
	}
}
