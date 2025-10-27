package com.springdatajpa.springdatajpa.service;

import com.springdatajpa.springdatajpa.dto.TodoDto;
import com.springdatajpa.springdatajpa.entity.Todo;
import com.springdatajpa.springdatajpa.entity.User;
import com.springdatajpa.springdatajpa.exception.ResourceNotFoundException;
import com.springdatajpa.springdatajpa.repository.TodoRepository;
import com.springdatajpa.springdatajpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public TodoDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        return convertToDto(todo);
    }

    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream().map(todo -> convertToDto(todo)).toList();
    }

    public TodoDto updateTodo(Long id, Todo todoReq) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todo.setTitle(todoReq.getTitle());
        todo.setCompleted(todoReq.getCompleted());
        return convertToDto(todoRepository.save(todo));
    }

    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todoRepository.deleteById(id);
    }

    private TodoDto convertToDto(Todo todo) {
        TodoDto dto = new TodoDto();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setCompleted(todo.getCompleted());
        return dto;
    }
}
