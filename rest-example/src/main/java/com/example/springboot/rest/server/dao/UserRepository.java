
package com.example.springboot.rest.server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.springbootrestserver.model.User;

@Component
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAll();

	User findOne(Long id);

	void delete(Long id);
	
	
    
  
}