package com.example.GuessFootballerBot.Model.Service;


import com.example.GuessFootballerBot.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


}
