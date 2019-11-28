
package com.example.springbootrestserver.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.springbootrestserver.exception.BeanNotFoundException;
import com.example.springbootrestserver.model.User;
import com.example.springbootrestserver.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/user/*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
		userService.deleteUser(id);
		logger.info("user with id:"+id +" is deleted");
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> findUserById(@PathVariable("id") Long id) throws BeanNotFoundException {
		User user = userService.getUserById(id);
		if (user == null)
			throw new BeanNotFoundException("user with id : " + id + " cannot be Found");

		logger.info("user found :"+ user.toString());
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}

	@GetMapping(value = "/users")
	public ResponseEntity<Collection<User>> getAllUsers() {
		Collection<User> users = userService.getAllUsers();
		{
			logger.info("liste des utilisateurs : " + users.toString());
		}
		return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
	}

	@PostMapping(value = "/users")
	@Transactional
	public ResponseEntity<User> saveUser(@RequestBody User user) {

		User userSave = userService.saveOrUpdateUser(user);
		logger.info("userSave : " + userSave.toString());
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {

		User userToUpdate = userService.getUserById(id);
		if (userToUpdate == null) {
			throw new BeanNotFoundException("user: " + id + " not Found");
		}
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setJob(user.getJob());

		User userUpdated = userService.saveOrUpdateUser(userToUpdate);
		
		logger.info("updated user info"+ userUpdated.toString());

		return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
	}
}