package com.example.springbootrestserver.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springboot.rest.server.dao.UserRepository;
import com.example.springbootrestserver.model.User;
import com.example.springbootrestserver.service.UserService;

@RunWith(SpringRunner.class)//permet d'établir une liaison entre JUnit et Spring
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    User user = new User("user4","user4","johanna", "smith","ingeneer" );
    
    @Before
    public void setup(){
        entityManager.persist(user);//on sauvegarde l'objet user au début de chaque test
        entityManager.flush();
    }
    @Test
    public void testFindAllUsers() {
        List<User> users = userRepository.findAll();
        Assert.assertTrue((users.size()==4));//on a trois users dans le fichier d'initialisation data.sql et un utilisateur ajouté lors du setup du test
    }
    
    @Test
    public void testSaveUser(){
        User user = new User("user4","user4","johanna", "smith","ingeneer" );
        User userSaved =  userRepository.save(user);
        assertNotNull(userSaved.getId());
        Assert.assertTrue(userSaved.getFirstName().equals("johanna"));
    }
    @Test
    public void testFindByLogin() {
        User userFromDB = userService.loadUserByUserLogin("user2");     
        Assert.assertTrue(userFromDB.getusername().equals("user2"));//user2 a été créé lors de l'initialisation du fichier data.sql     
    }
    
    @Test
    public void testDeleteUser(){
        userRepository.delete(user.getId());
        User userFromDB = userRepository.findOne(user.getId());
        assertNull(userFromDB);
    }
    
    @Test
    public void testUpdateUser() {
        User userToUpdate = userRepository.findOne(user.getId());
        userRepository.save(userToUpdate);        
        User userUpdatedFromDB = userRepository.findOne(userToUpdate.getId());
        assertNotNull(userUpdatedFromDB);
       
    }        
}