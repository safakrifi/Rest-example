
package com.example.springbootrestserver.dto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserDTO implements Serializable {

	private static final long serialVersionUID = -443589941665403890L;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDTO.class);


	private String firstName;

	private Long id;
	private String job;
	private String lastName;

	public UserDTO() {
	}

	public UserDTO(Long id, String firstName) {
		this.id = id;
		this.firstName = firstName;
	}

	public UserDTO(Long id, String firstName, String lastName, String job) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.job = job;
	}

	public UserDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserDTO(String firstName, String lastName, String job) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.job = job;
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public String getJob() {
		return job;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		logger.info("UserDTO format ");
		return String.format("[id=%s, firstName=%s, lastName=%s, job=%s]", id, firstName, lastName, job);
	}

}