package com.example.springbootrestserver.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springboot.rest.server.dao.UserRepository;
import com.example.springbootrestserver.model.User;
import com.example.springbootrestserver.service.UserService;
import com.example.springbootrestserver.service.UserServiceImpl;



@RunWith(SpringRunner.class)
public class UserServiceImplTest {
 
    @TestConfiguration //création des beans nécessaires pour les tests
    static class UserServiceImplTestContextConfiguration {
        
        @Bean//bean de service
        public UserService userService () {
            return new UserServiceImpl();
        }
        
        @Bean//nécessaire pour hacher le mot de passe sinon échec des tests
        public BCryptPasswordEncoder passwordEncoder() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder;
        }
    }
    
    @Autowired
    private UserService userService;
    
    
    @MockBean //création d'un mockBean pour UserRepository
    private UserRepository userRepository;
    
   
    
    User user = new User("user4","user4","johanna", "smith","ingeneer" );
    
   List <String> list = new ArrayList<String>(1);
    
    @Test
    public void testFindAllUsers() throws Exception {
    
        User user = new User("user4","user4","johanna", "smith","ingeneer" );
        List<User> allUsers = Arrays.asList(user);           
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
        Collection<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(users, allUsers); 
        assertEquals(users.size(), allUsers.size());
        verify(userRepository).findAll();
    }
    
    @Test
    public void testSaveUser() throws Exception {
        User user = new User("user4","user4","johanna", "smith","ingeneer" );
        User userMock = new User("user4","user4","johanna", "smith","ingeneer" );
        Mockito.when(userRepository.save((user))).thenReturn(userMock);
        User userSaved = userService.saveOrUpdateUser(user);
        assertNotNull(userSaved);
        assertEquals(userMock.getId(), userSaved.getId());
         assertEquals(userMock.getusername(), userSaved.getusername());
         verify(userRepository).save(any(User.class));
    }
    
    @Test
    public void testFindUserById() {
        User user = new User("user4","user4","johanna", "smith","ingeneer");
        Mockito.when(userRepository.findOne(user.getId())).thenReturn(user);
        User userFromDB = userService.getUserById(user.getId());
        assertNotNull(userFromDB);
        assertThat(userFromDB.getusername(), is(user.getusername()));  
        verify(userRepository).findOne(any(Long.class));
     }
    
    @Test
    public void testDelete() throws Exception {
        User user = new User("user4","user4","johanna", "smith","ingeneer" );
        User userMock = new User("user4","user4","johanna", "smith","ingeneer" );
        Mockito.when(userRepository.save((user))).thenReturn(userMock);
        User userSaved = userService.saveOrUpdateUser(user);
        assertNotNull(userSaved);
        assertEquals(userMock.getId(), userSaved.getId());
        userService.deleteUser(userSaved.getId());
        verify(userRepository).delete(any(Long.class));
    }
    
    @Test
    public void testUpdateUser() throws Exception {
        User userToUpdate = new User("user4","user4","johanna", "smith","ingeneer" );
        User userUpdated = new User("user4","user4","johanna", "smith","ingeneer" );
        Mockito.when(userRepository.save((userToUpdate))).thenReturn(userUpdated);
        User userFromDB = userService.saveOrUpdateUser(userToUpdate);
        assertNotNull(userFromDB);
        assertEquals(userUpdated.getusername(), userFromDB.getusername());
        verify(userRepository).save(any(User.class));        
    }    
}