
package com.example.springbootrestserver.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springbootrestserver.exception.BeanNotFoundException;
import com.example.springbootrestserver.jwt.JWTUtil;
import com.example.springbootrestserver.jwt.models.AuthenticationRequest;
import com.example.springbootrestserver.jwt.models.AuthenticationResponse;
import com.example.springbootrestserver.model.User;
import com.example.springbootrestserver.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/user/*")
@Api(value = "USER DETAILS", description = "Operations pertaining to users data")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtTokenUtil;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// JWT Authentication and annotation for swagger configuration
	@ApiOperation(value = "Authentication to get access to the API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "JWT Successfully created ",

			examples = @io.swagger.annotations.Example(value = {
					@io.swagger.annotations.ExampleProperty(value = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTU3Njc2NjgwNCwiaWF0IjoxNTc2NzYzMjA0fQ.jQwZHBGxavSNCTZsr6yZbqRuOURYl_Qmwf2DKKpavk0"
							, mediaType = "application/json") })),

			@ApiResponse(code = 401, message = "You are not authorized "),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiParam(value="userName and password are needed", example="[eyJhbGciOiJIUzI1NiJ9.eyJzdWI....]")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthentificationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		logger.info("authentication request user name and password are :" + authenticationRequest.getUserName() + "/"
				+ authenticationRequest.getPassword());

		final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUserName());

		// check if the encoded password in the dataBase matches the password entered by
		// the user
		if (bCryptPasswordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUserName(), userDetails.getPassword()));
			} catch (BadCredentialsException e) {
				throw new Exception("incorrect username or password please verify credentials", e);
			}
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}

		throw new BadCredentialsException("incorrect username or password please verify credentials");

	}
	@ApiOperation(value = "Delete a user from users list")
	@ApiResponses(value = { 
			@ApiResponse(code = 410, message = "user Successfully deleted "),
			@ApiResponse(code = 204,  message = "there is no content or details related to this user"),
			@ApiResponse(code = 401, message = "You are not authorized "),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) throws Exception {
		try {
		userService.deleteUser(id);
		}catch(Exception e){
			throw new BeanNotFoundException("user with id : " + id + " cannot be Found");
		}
		logger.info("user with id:" + id + " is deleted");
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
	@ApiOperation(value = "fetch a user from users list by Identification")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "user Successfully retreived "),
			@ApiResponse(code = 204,  message = "there is no content or details related to this user"),
			@ApiResponse(code = 401, message = "You are not authorized to access the resource "),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> findUserById(@PathVariable("id") Long id) throws BeanNotFoundException {
		User user = userService.getUserById(id);
		if (user == null)
			throw new BeanNotFoundException("user with id : " + id + " cannot be Found");

		logger.info("user found :" + user.toString());
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}
	@ApiOperation(value = "fetch a user from users list by userName")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "user Successfully retreived "),
			@ApiResponse(code = 204,  message = "there is no content or details related to this user"),
			@ApiResponse(code = 401, message = "You are not authorized to access the resource "),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/users/login/{username}")
	public ResponseEntity<User> findUserByName(@PathVariable("username") String username) {
		User user = userService.loadUserByUserLogin(username);
		if (user == null)
			throw new BeanNotFoundException("user with username: " + username + " cannot be Found");

		logger.info("user found :" + user.toString());
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}
	@ApiOperation(value = "Retreive the List of users ")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "List is successfully fetched "),
			@ApiResponse(code = 401, message = "You are not authorized to access the resource "),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/users")
	public ResponseEntity<Collection<User>> getAllUsers() {
		Collection<User> users = userService.getAllUsers();
		{
			logger.info("liste des utilisateurs : " + users.toString());
		}
		return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
	}
	@ApiOperation(value = "Add a new user to the users List")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "user Successfully Added to the users List "),
			@ApiResponse(code = 201,  message = "A new user is created"),
			@ApiResponse(code = 401, message = "You are not authorized to access the resource "),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping(value = "/users")
	@Transactional
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User userSave = userService.saveOrUpdateUser(user);
		logger.info("userSave : " + userSave.toString());
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	@ApiOperation(value = "Alter user information by user Id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "user Details Successfully changed "),
			@ApiResponse(code = 401, message = "You are not authorized to access the resource "),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
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

		logger.info("updated user info" + userUpdated.toString());

		return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
	}

}