package com.skyvault05.remindme.service;

import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public void throwError(){
        throw new UserNotFoundException("실험으로 에러 발생시켜봄");
    }
}
