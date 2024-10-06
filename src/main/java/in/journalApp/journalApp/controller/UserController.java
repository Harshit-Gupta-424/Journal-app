package in.journalApp.journalApp.controller;

import in.journalApp.journalApp.entity.User;
import in.journalApp.journalApp.repository.UserRepository;
import in.journalApp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldUser = userService.getUser(userName);
        if (oldUser != null) {
            oldUser.setUserName(newUser.getUserName());
            oldUser.setPassword(newUser.getPassword());
            userService.saveNewUser(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(newUser, HttpStatus.NOT_FOUND);


    } @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
