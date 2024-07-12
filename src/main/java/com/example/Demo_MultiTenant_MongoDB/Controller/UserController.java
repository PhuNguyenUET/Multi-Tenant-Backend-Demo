package com.example.Demo_MultiTenant_MongoDB.Controller;

import com.example.Demo_MultiTenant_MongoDB.Model.Auth.Role;
import com.example.Demo_MultiTenant_MongoDB.Model.User;
import com.example.Demo_MultiTenant_MongoDB.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.USER);
        User cus = userService.saveUser(user);
        return ResponseEntity.ok(cus);
    }

    @GetMapping("/getById")
    public ResponseEntity<User> getUserById(@RequestParam("id") String userId) {
        User user = userService.getUserById(userId);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/deleteByEmail")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam("email") String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("Deleted successfully");
    }
}
