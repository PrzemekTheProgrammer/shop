package pl.com.coders.shop2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.User;
import pl.com.coders.shop2.dto.UserDTO;
import pl.com.coders.shop2.mapper.UserMapper;
import pl.com.coders.shop2.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO create(UserDTO userDto) {
        User user = UserMapper.getInstance().toEntity(userDto);
        User savedUser = userRepository.create(user);
        return UserMapper.getInstance().toDTO(savedUser);
    }
}
