package com.ajay.eommerce.Service;


import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;



}
