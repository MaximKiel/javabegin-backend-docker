package ru.javabegin.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import ru.javabegin.backend.entity.Customer;
import ru.javabegin.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerRepository repository;

    // для упрощения кода - сразу будем вызывать метод репозитория, без класса @Service
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll(){
        List<Customer> list =  repository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}
