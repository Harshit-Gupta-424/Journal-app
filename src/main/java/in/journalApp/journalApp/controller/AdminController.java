package in.journalApp.journalApp.controller;

import in.journalApp.journalApp.cache.AppCache;
import in.journalApp.journalApp.entity.User;
import in.journalApp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        if(allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createAdmin")
    public void createAdmin(@RequestBody User user){
        userService.saveNewAdminUser(user);
    }

    @GetMapping("/clearCache")
    public void clearCache(){
        appCache.init();
    }

}
