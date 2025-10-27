package com.springdatajpa.springdatajpa.controller;

import com.springdatajpa.springdatajpa.dto.TodoDto;
import com.springdatajpa.springdatajpa.dto.UserDto;
import com.springdatajpa.springdatajpa.entity.Todo;
import com.springdatajpa.springdatajpa.entity.User;
import com.springdatajpa.springdatajpa.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody User user){
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody User user){
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/todos")
    public ResponseEntity<List<TodoDto>> getTodosByUserId(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getTodosByUserId(id));
    }

    @PostMapping("/{id}/todos")
    public ResponseEntity<Void> addTodosByUser(@PathVariable Long id, @RequestBody List<Todo> todos){
        userService.addTodos(id, todos);
        return ResponseEntity.ok().build();
    }
}
