package com.example.springbootrestserver.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "UTILISATEUR")
@XmlRootElement(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", updatable = false, nullable = false)
	private Long id;

	@Column(name = "FIRST_NAME", insertable = true, updatable = true, nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", insertable = true, updatable = true, nullable = false)
	private String lastName;

	@Column(name = "JOB", insertable = true, updatable = true, nullable = false)
	private String job;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String job) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.job = job;
	}

	public User(Long id, String firstName) {
		this.id = id;
		this.firstName = firstName;
	}

	public User(String firstName) {
		this.firstName = firstName;
	}

	/*
	 * public User(UserDTO userDTO) { this.setId(userDTO.getId()); //
	 * this.setFirstName(userDTO.getFirstName()); //
	 * this.setLastName(userDTO.getLastName()); }
	 */

	/*
	 * public User(UserRegistrationForm userRegistrationForm) {
	 * this.setLogin(userRegistrationForm.getLogin());
	 * this.setPassword(userRegistrationForm.getPassword()); }
	 */
	public User(Long id, String firstName, String lastName, String job) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.job = job;
	}

	public Long getId() {
		return id;
	}

	@XmlElement
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public String getLogin() { return login; }
	 * 
	 * @XmlElement public void setLogin(String login) { this.login = login; }
	 * 
	 * public String getPassword() { return password; }
	 * 
	 * @XmlElement public void setPassword(String password) { this.password =
	 * password; }
	 * 
	 * public Integer getActive() { return active; }
	 * 
	 * @XmlElement public void setActive(Integer active) { this.active = active; }
	 */
	public String getFirstName() {
		return firstName;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJob() {
		return job;
	}

	@XmlElement
	public void setJob(String job) {
		this.job = job;
	}

	/*
	 * public Set<Role> getRoles() { return roles; }
	 * 
	 * @XmlElement public void setRoles(Set<Role> roles) { this.roles = roles; }
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", first Name=" + firstName + ", Last Name=" + lastName + ", Job=" + job + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		// result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		return result;
	}

	// user comparator
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;

		return true;
	}
}