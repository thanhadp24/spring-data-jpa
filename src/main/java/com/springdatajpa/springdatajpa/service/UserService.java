package com.springdatajpa.springdatajpa.service;

import com.springdatajpa.springdatajpa.dto.TodoDto;
import com.springdatajpa.springdatajpa.dto.UserDto;
import com.springdatajpa.springdatajpa.entity.Todo;
import com.springdatajpa.springdatajpa.entity.User;
import com.springdatajpa.springdatajpa.exception.ResourceNotFoundException;
import com.springdatajpa.springdatajpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto saveUser(User user) {
        return convertUserToDto(userRepository.save(user));
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertUserToDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> convertUserToDto(user)).collect(Collectors.toList());
    }

    public void addTodos(Long userId, List<Todo> todos) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        for (Todo todo : todos) {
            todo.setUser(user);
        }
        user.getTodos().addAll(todos);
        userRepository.save(user);
    }

    public UserDto updateUser(Long id, User userReq) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setUsername(userReq.getUsername());
        user.setEmail(userReq.getEmail());
        User save = userRepository.save(user);
        return convertUserToDto(save);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(existingUser);
    }

    public List<TodoDto> getTodosByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return user.getTodos().stream().map(todo -> convertTodoToDto(todo)).toList();
    }

    private UserDto convertUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private TodoDto convertTodoToDto(Todo todo) {
        TodoDto dto = new TodoDto();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setCompleted(todo.getCompleted());
        return dto;
    }
}
