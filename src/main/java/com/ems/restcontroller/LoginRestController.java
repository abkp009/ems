package com.ems.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ems.entity.UserEntity;
import com.ems.form.User;
import com.ems.form.UserResponse;
import com.ems.repository.UserRepository;
import com.ems.util.CommonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.GET})
public class LoginRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRestController.class);
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CommonService<User, UserEntity> toEntityConvertor;

    @PostMapping("/user")
    public ResponseEntity<UserEntity> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userRepo.save(toEntityConvertor.convert(user, UserEntity.class)));
    }
    @SuppressWarnings("deprecation")
    @GetMapping("/user/{page}/{size}")
    public ResponseEntity<List<UserEntity>> getUsers(@PathVariable("page") Integer page,
                                                     @PathVariable("size") Integer size) {
        return ResponseEntity.ok(userRepo.findAll(new PageRequest(page, size, Direction.ASC, "password")).getContent());
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody User user, BindingResult bindingResult) {
        UserResponse response = new UserResponse();
        response.setUser(user);
        if (bindingResult.hasErrors()) {
            user.clear();
            response.setError(bindingResult.toString());
            LOGGER.info(response.getError().toString());
            return ResponseEntity.ok(response);
        }
        UserEntity userEnt = new ObjectMapper().convertValue(user, UserEntity.class);
        if (null != user.getGender()) {
            if (Integer.compare(1, user.getGender()) == 0) {
                userEnt.setGender("Male");
            } else {
                userEnt.setGender("Female");
            }
        }
        LOGGER.info(userEnt.toString());
        if (userRepo.findOne(Example.of(userEnt)).isPresent()) {
            LOGGER.info("Successful Login by " + user.toString());
            return ResponseEntity.ok(response);
        } else {
            user.clear();
            LOGGER.info("Unsuccessful Login by " + user.getUserName());
            response.setError("Invalid Login Details");
            return ResponseEntity.ok(response);
        }
    }
}
