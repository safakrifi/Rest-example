
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

import com.example.springbootrestserver.model.User;
import com.example.springbootrestserver.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/user/*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	// @Autowired
	// private RoleService roleService;

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

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		logger.debug("Utilisateur trouvé : " + user);
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}

	@PutMapping(value = "/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {

		User userToUpdate = userService.getUserById(id);
		if (userToUpdate == null) {
			logger.debug("L'utilisateur avec l'identifiant " + id + " n'existe pas");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}

		// logger.info("UPDATE ROLE: "+userToUpdate.getRoles().toString());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setJob(user.getJob());
		User userUpdated = userService.saveOrUpdateUser(userToUpdate);
		return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
	}

	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
}