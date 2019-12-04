
package com.example.springbootrestserver.service;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.rest.server.dao.UserRepository;
import com.example.springbootrestserver.model.User;

@Service(value = "userService")// l'annotation @Service est optionnelle ici, car il n'existe qu'une seule implémentation de l'interface UserService
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

	
	public User loadUserByUserLogin(String username) {
		Collection<User> users = getAllUsers();
		User user = users.stream()
				  .filter(customer -> username.equalsIgnoreCase(customer.getusername()))
				  .findAny()
				  .orElse(null);
		return  user ;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		System.out.println("were at 1");
		Collection<User> users = getAllUsers();
		User user = users.stream()
				  .filter(customer -> username.equalsIgnoreCase(customer.getusername()))
				  .findAny()
				  .orElse(null);
		System.out.println("were at 2:");
		System.out.println("userDetails are username /"+user.getusername());
		System.out.println("userDetails are password /" +user.getPassword());

		return  new org.springframework.security.core.userdetails.User(user.getusername(),user.getPassword(),new ArrayList<>()) ;
	}

	
	

}