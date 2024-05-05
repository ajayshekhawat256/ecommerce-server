package com.ajay.eommerce.Service.Impl;

import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Repository.UserRepository;
import com.ajay.eommerce.Service.UserService;
import com.ajay.eommerce.config.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
            return  user.get();
        }
        throw new UserException("user not found with this id"+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtTokenProvider.getEmailFromJwtToken(jwt);
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("user not found with this email");
        }
        return user;
    }
}
