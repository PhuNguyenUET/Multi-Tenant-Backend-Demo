package com.example.Demo_MultiTenant_MongoDB.Service;

import com.example.Demo_MultiTenant_MongoDB.Model.User;
import com.example.Demo_MultiTenant_MongoDB.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
