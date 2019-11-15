
package com.example.springbootrestserver.service;
import java.util.Collection;

import com.example.springbootrestserver.model.User;

public interface UserService {

    Collection<User> getAllUsers();
    
    User getUserById(Long id);
        
    User saveOrUpdateUser(User user); 
    
    void deleteUser(Long id);
    
}