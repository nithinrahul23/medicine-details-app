package com.capstone.service;

import com.capstone.entity.User;
import com.capstone.exception.AppException;
import com.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null){
            user.setRole("User");
        }
        return userRepository.save(user);
    }

    public User fetchUserDetailsByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public User updateUserDetails(String userName, User user) throws AppException{
        User selectedUser = userRepository.findByUserName(userName);
        if(selectedUser == null){
            throw new AppException("No user details found with given username");
        }
        selectedUser.setRole(user.getRole());
        return userRepository.save(selectedUser);
    }

    public void deleteUserDetails(String userName) throws AppException{
        User user = userRepository.findByUserName(userName);
        if(user == null){
            throw new AppException("No details found for the given username");
        }
        userRepository.delete(user);
    }

}
