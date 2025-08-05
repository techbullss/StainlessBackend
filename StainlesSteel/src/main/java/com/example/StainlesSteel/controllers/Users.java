package com.example.StainlesSteel.controllers;

import com.example.StainlesSteel.Repository.UserRepository;
import com.example.StainlesSteel.Tables.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/users")
public class Users {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping
    public ResponseEntity <Map<String,Object>> saveUser(@RequestBody UserTable user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
 userRepo.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User created successfully");
        return ResponseEntity.ok(response);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserTable user) {
        user.setId(id); // Make sure ID is set
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        UserTable updatedUser = userRepo.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User updated successfully");
        response.put("user", updatedUser);
        return ResponseEntity.ok(response);
    }

    // For deletes
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<UserTable>> getUsers(){
     List<UserTable>  users=  userRepo.findAll();
     return  ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserTable> getUsersById(@PathVariable Long id){
        UserTable  users=  userRepo.findById(id).orElseThrow();
        return  ResponseEntity.ok(users);
    }

}
