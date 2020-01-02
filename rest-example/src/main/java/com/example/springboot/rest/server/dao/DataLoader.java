package com.example.springboot.rest.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.springbootrestserver.model.User;
import com.example.springbootrestserver.service.UserService;

@Component
public class DataLoader implements ApplicationRunner {
	
    
    @Autowired
    private UserService userService;

    @Autowired
    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    public void run(ApplicationArguments args) {
    	
    	User user1 = new User("user1","user1", "john", "Doe", "Engineer");		
		userService.saveOrUpdateUser(user1);
		User user2 = new User("user2","user2", "Jammy", "Lannister", "Student");
		userService.saveOrUpdateUser(user2);
		User user3 = new User("user3","user3", "John", "Snow", "Engineer");		
		userService.saveOrUpdateUser(user3);
    }
}
