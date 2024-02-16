package com.Sumitav.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sumitav.Entity.Role;
import com.Sumitav.Entity.User;
import com.Sumitav.Entity.UserRole;
import com.Sumitav.Services.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
	 
	final private  UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {
         

        // password encode with Bcrypt
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));


        Set<UserRole> newRoles = new HashSet<>();
        Role role = new Role();
        role.setRoleId(23L);
        role.setRoleName("General USER");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        newRoles.add(userRole);

        return this.userService.createUser(user, newRoles);

    }
    //finding  User by Id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

//get all user
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


    //delete the user by id
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId")Long userId){

        this.userService.deleteUser(userId);
    }
  //update the user

//update user data by userid
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestBody User updatedUser
    ) {
        Optional<User> optionalUser = Optional.ofNullable(userService.getUserById(userId));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setFullName(updatedUser.getFullName());
            

            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
