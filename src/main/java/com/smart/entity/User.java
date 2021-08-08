package com.smart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class User {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int userId;
	
	@NotBlank(message = "username field cannot be blank")
	@Size(max = 20,min = 2, message="The username is between 2 to 20 char")
	private String userName;
	@Column(unique =  true)
	private String email;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String imageUrl;
	@Column(length =  1200)
	private String userAbout;
	private String userRole;
	@JsonIgnore
	private boolean enable;
	@OneToMany(mappedBy = "user", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Contact> contacts = new ArrayList<>();
	
	
}
