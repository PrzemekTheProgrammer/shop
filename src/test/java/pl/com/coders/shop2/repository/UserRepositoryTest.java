package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.coders.shop2.domain.User;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = {"pl.com.coders.shop2.repository"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void createTest() {
        //given
        User user = User.builder()
                .firstName("Testowy")
                .lastName("testowe")
                .password("pass")
                .email("testowy@email.com")
                .build();
        //when
        user = userRepository.create(user);
        //then
        assertNotNull(user.getId());
        assertEquals("Testowy", user.getFirstName());
        assertEquals("testowe", user.getLastName());
        assertEquals("pass", user.getPassword());
        assertEquals("testowy@email.com", user.getEmail());
    }

    @Test
    void getByEmailTest() {
        //given
        User user = User.builder()
                .firstName("Testowy")
                .lastName("testowe")
                .password("pass")
                .email("testowy@email.com")
                .build();
        userRepository.create(user);
        User user2 = User.builder()
                .firstName("Testowy2")
                .lastName("testowe2")
                .password("pass2")
                .email("testowy2@email.com")
                .build();
        user2 = userRepository.create(user2);
        //when
        User receivedUser = userRepository.getByEmail(user2.getEmail());
        //then
        assertEquals("Testowy2", receivedUser.getFirstName());
        assertEquals("testowe2", receivedUser.getLastName());
        assertEquals("pass2", receivedUser.getPassword());
        assertEquals("testowy2@email.com", receivedUser.getEmail());
    }

}
