package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUser(){
        User user = User.builder()
                .email("email@email.com")
                .name("name")
                .picture("pic")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
}
