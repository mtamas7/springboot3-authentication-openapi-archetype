package com.divide.by.zero.security.users.service;

import com.divide.by.zero.security.users.model.User;
import com.divide.by.zero.security.users.repository.UserRepository;
import com.divide.by.zero.security.users.requests.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(final UserRequest user) {
        final User newUser = new User();
        newUser.setFirst_name(user.getFirst_name());
        newUser.setLast_name(user.getLast_name());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setRoles(user.getRoles());
        return userRepository.save(newUser);
    }
}
