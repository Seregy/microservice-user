package com.microservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Map<String, String> getEndpoints(HttpServletRequest servletRequest) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("getUser",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/user/{id}").build().toString());
        map.put("getUsers",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/users/").build().toString());
        map.put("createUser",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/user/").build().toString());
        map.put("updateUser",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/user/{id}").build().toString());
        return map;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getList();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUser(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody User newUser, HttpServletRequest servletRequest) {
        User created = userService.createUser(newUser.getEmail(), newUser.getPassword(), newUser.getName());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/user/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }
}
