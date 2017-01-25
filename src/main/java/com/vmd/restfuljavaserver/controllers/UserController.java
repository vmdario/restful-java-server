package com.vmd.restfuljavaserver.controllers;

import com.vmd.restfuljavaserver.ResponseJson;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vmd.restfuljavaserver.models.User;
import com.vmd.restfuljavaserver.repos.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    
    // POST /user/add
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody User user) {
        try {
            System.out.println("Saving user");
            userRepo.save(user);
        } catch(Exception e) {
            return ResponseJson.getError(e.getMessage());
        }
        return ResponseJson.getOk();
    }
    
    // GET /user/delete?id={id}
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public ResponseEntity delete(@RequestParam Long id) {
        User user = userRepo.findOne(id);
        if(user == null) {
            System.out.println("User not found");
            return ResponseJson.getError("User not found");
        }
        System.out.println("Deleting user "+ id);
        userRepo.delete(user);
        return ResponseJson.getOk();
    }

    // GET /user/all
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<User> getAll() {
        return userRepo.findAll();
    }

    // GET /user?id={id}
    @RequestMapping(path = "", method = RequestMethod.GET)
    public User getById(@RequestParam("id") long id) {
        return userRepo.findOne(id);
    }

    // GET /user/test
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test() {
        return "This is a test!";
    }
}