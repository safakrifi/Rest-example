
package com.example.springbootrestserver.service;
import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.springbootrestserver.model.User;

public interface UserService extends UserDetailsService {

    Collection<User> getAllUsers();
    
    User getUserById(Long id) ;
            
    User saveOrUpdateUser(User user) ;
    
    void deleteUser(Long id);
    
    public User loadUserByUserLogin(String Login);
    
    public UserDetails loadUserByUsername(String username);
}