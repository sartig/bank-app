package service;


import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



//    public boolean verifyUser(String username, String password) {
//        Optional<User> user = userRepository.findByUsername(username);
//        return user.map(value -> value.getPassword().equals(password)).orElse(false);
//    }



}
