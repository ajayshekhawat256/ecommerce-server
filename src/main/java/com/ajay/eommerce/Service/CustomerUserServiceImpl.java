package com.ajay.eommerce.Service;

import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    public CustomerUserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("user not found with this email"+username);
        }
        List<GrantedAuthority> authorites=new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorites);
    }
}
