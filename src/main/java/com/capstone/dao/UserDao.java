package com.capstone.dao;

import com.capstone.entity.JwtResponse;
import com.capstone.entity.User;
import com.capstone.entity.UserLogin;
import com.capstone.exception.AppException;
import com.capstone.service.JwtService;
import com.capstone.service.MyUserDetailsService;
import com.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/medicoApp")
@CrossOrigin
public class UserDao {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping(value="/signUp")
    public User addUser(@RequestBody User user) throws AppException{
        String temp = user.getUserName();
        if(temp != null && !"".equals(temp)){
            User userDetails = userService.fetchUserDetailsByUserName(temp);
            if(userDetails != null){
                throw new AppException("User with username "+ temp +" is already exists.");
            }
        }
        User userDetails = userService.addUser(user);
        return userDetails;
    }

    @PostMapping(value = "/login")
    public JwtResponse loginUserDetails(@RequestBody UserLogin userLogin) throws AppException{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.userName(),userLogin.password()
        ));
        if(authentication.isAuthenticated()){
            return myUserDetailsService.createToken(userLogin);
        }
        else{
            throw new AppException("Invalid Credentials");
        }
    }

    @GetMapping("/admin/allUsers")
    public List<User> getAllUsers() throws AppException{
        List<User> users = userService.getAllUsers();
        if(users == null){
            throw new AppException("Users Not found");
        }
        return users;
    }

    @PutMapping("/admin/users/{userName}")
    public User updateUserDetails(@PathVariable String userName, @RequestBody User user) throws AppException{
        return userService.updateUserDetails(userName, user);
    }

    @DeleteMapping("/admin/{userName}")
    public void deleteUserDetails(@PathVariable String userName) throws AppException{
        userService.deleteUserDetails(userName);
    }
}
