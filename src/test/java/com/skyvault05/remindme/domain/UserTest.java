package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void userForTest(){
        for(long i=1; i < 10 ; i++){
            User user = User.builder()
                    .userEmail("email@email.com" + i)
                    .userName("name" + i)
                    .userPicture("pic" + i)
                    .userRole(UserRole.USER)
                    .build();
            for(long j=0; j<i; j++){
                User friend = userRepository.findById(j).orElse(null);
                if(friend != null) user.getUserFriend().add(friend);
            }
            userRepository.save(user);
        }
    }

    @Test
    @WithMockUser(roles = "USER")
    public void saveUser(){
        User user = User.builder()
                .userEmail("email@email.com")
                .userName("name")
                .userPicture("pic")
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);
    }

    @Test
    public void updateUser(){
        User user = userRepository.findById(1L).orElse(null);
        user.setUserName("updateUserName");
        userRepository.save(user);
    }

    @Test
    public void deleteUser(){
        User user = userRepository.findById(6L).orElse(null);
        userRepository.delete(user);
    }

    @Test
    public void readUser(){
        User user = userRepository.findById(1L).orElse(null);
        System.out.println(user.getUserFriend());
    }
}
