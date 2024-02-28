package com.fdmgroup.CreditCardProject;

import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void init() {
        User testUser = new User();
        testUser.setUsername("newUser");
        testUser.setPassword(passwordEncoder.encode("1234"));
        if (!userRepository.existsByUsername("newUser"))
            userRepository.save(testUser);
        else
            userRepository.delete(testUser);
    }


    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(formLogin("/login").user("newUser").password("1234"))
                .andExpect(status().isFound()) // Redirection status code (302)
                .andExpect(redirectedUrl("/home"));
    }
    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(formLogin("/login").user("user").password("wrong"))
                .andExpect(status().isFound()) // Redirection status code (302)
                .andExpect(redirectedUrl("/login?error=Login+Fail.+Invalid+username+or+password.+Please+try+again."));
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        mockMvc.perform(post("/register").param("username", "newUser2").param("password", "1234").param("confirmPassword", "1234"))
                .andExpect(status().isFound()) // Redirection status code (302)
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    public void testRegisterFailureMismatchPasswords() throws Exception {
        mockMvc.perform(post("/register").param("username", "newUser").param("password", "1234").param("confirmPassword", "12345"))
                .andExpect(status().isFound()) // Redirection status code (302)
                .andExpect(redirectedUrl("/register"));
    }


}
