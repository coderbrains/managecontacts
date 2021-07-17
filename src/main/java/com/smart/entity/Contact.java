package com.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_details")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactID;
	private String name;
	private String nickName;
	private String mobileNumber;
	private String work;
	private String email;
	@Column(length = 1200)
	private String contactDescription;
	private String imageUrl;
	@ManyToOne
	private User user;
	
	
}
