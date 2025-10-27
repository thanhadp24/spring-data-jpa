package com.springdatajpa.springdatajpa.service;

import com.springdatajpa.springdatajpa.entity.Todo;
import com.springdatajpa.springdatajpa.entity.User;
import com.springdatajpa.springdatajpa.exception.ResourceNotFoundException;
import com.springdatajpa.springdatajpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addTodos(Long userId, List<Todo> todos) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.getTodos().addAll(todos);
        userRepository.save(user);
    }

    public User updateUser(Long id, User userReq) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setUsername(userReq.getUsername());
        user.setEmail(userReq.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(existingUser);
    }

    public List<Todo> getTodosByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return user.getTodos();
    }
}
