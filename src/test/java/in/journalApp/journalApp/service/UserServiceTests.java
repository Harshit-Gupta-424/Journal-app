package in.journalApp.journalApp.service;

import in.journalApp.journalApp.entity.User;
import in.journalApp.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindByUserName(){
        User user = userRepository.findByUserName("Junaid");
        Assert.notNull(user, "User does not exist");
    }
}
