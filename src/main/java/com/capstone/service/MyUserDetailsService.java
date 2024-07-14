package com.capstone.service;

import com.capstone.entity.JwtResponse;
import com.capstone.entity.User;
import com.capstone.entity.UserLogin;
import com.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public JwtResponse createToken(UserLogin userLogin){
        String jwtToken = jwtService.generateToken(loadUserByUsername(userLogin.userName()));
        User user = userRepository.findByUserName(userLogin.userName());
        return new JwtResponse(user, jwtToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null){
            var userObj = user;
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUserName())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username + "is not found");
        }
    }

    private String[] getRoles(User user) {
        if(user.getRole() == null){
            return new String[]{"User"};
        }
        return user.getRole().split(",");
    }



}
