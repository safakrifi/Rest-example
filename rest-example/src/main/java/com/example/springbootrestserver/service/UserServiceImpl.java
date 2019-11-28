
package com.example.springbootrestserver.service;
import java.util.Collection;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.rest.server.dao.UserRepository;
import com.example.springbootrestserver.model.User;

@Service(value = "userService")// l'annotation @Service est optionnelle ici, car il n'existe qu'une seule implémentation de l'interface UserService
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
  //  @Autowired
  //  private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Collection<User> getAllUsers() {
        return IteratorUtils.toList(userRepository.findAll().iterator());
    }
    
    @Override
    public User getUserById(Long id)  {
    	
    
   return   userRepository.findOne(id);
       
    }

    @Override
    @Transactional(readOnly=false)
    public User saveOrUpdateUser(User user) {
   //     user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly=false)
    public void deleteUser(Long id) {
        userRepository.delete(id);

    }

	

}