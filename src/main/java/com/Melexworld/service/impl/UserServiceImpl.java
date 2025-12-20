package com.Melexworld.service.impl;

import com.Melexworld.configuration.JwtProvider;
import com.Melexworld.exceptions.UserException;
import com.Melexworld.model.User;
import com.Melexworld.repository.UserRepository;
import com.Melexworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User getUserFromJwtToken(String token) throws UserException {

        String email = jwtProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("Invalid toke");
        }

        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("user not found");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("user not found");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception, UserException {
        return userRepository.findById(id).orElseThrow(
                ()-> new UserException("User Not found")
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
