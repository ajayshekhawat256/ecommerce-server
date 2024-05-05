package com.ajay.eommerce.Controller;


import com.ajay.eommerce.Model.Cart;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Repository.UserRepository;
import com.ajay.eommerce.Request.LoginRequest;
import com.ajay.eommerce.Response.AuthReponse;
import com.ajay.eommerce.Service.CartService;
import com.ajay.eommerce.Service.CustomerUserServiceImpl;
import com.ajay.eommerce.Service.UserService;
import com.ajay.eommerce.config.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private CustomerUserServiceImpl customerUserService;
    private CartService cartService;

    public AuthController(UserRepository userRepository,CustomerUserServiceImpl customerUserService,PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider,CartService cartService){
        this.userRepository=userRepository;
        this.customerUserService=customerUserService;
        this.passwordEncoder=passwordEncoder;
        this.jwtTokenProvider=jwtTokenProvider;
        this.cartService=cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthReponse> createUserHandler(@RequestBody User user) throws UserException {
        String email=user.getEmail();
        String password=user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        String role=user.getRole();

       User isEmailExist=userRepository.findByEmail(email);
       if(isEmailExist!=null){
           throw new UserException("Email is already used with another account");
       }
       User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setRole(role);

        User savedUser=userRepository.save(createdUser);
        cartService.createCart(savedUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtTokenProvider.generateToken(authentication);
        AuthReponse authResponse= new AuthReponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup success");
        return new ResponseEntity<AuthReponse>(authResponse,HttpStatus.CREATED);
    }
    @PostMapping("/signIn")
    public ResponseEntity<AuthReponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication=authenticate(username,password);
        String token=jwtTokenProvider.generateToken(authentication);
        AuthReponse authReponse=new AuthReponse();
        authReponse.setJwt(token);
        authReponse.setMessage("Signin Success");
        return new ResponseEntity<AuthReponse>(authReponse,HttpStatus.OK);
    }
    private Authentication authenticate(String username,String password){
        UserDetails userDetails=customerUserService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
