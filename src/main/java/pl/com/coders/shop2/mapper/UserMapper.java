package pl.com.coders.shop2.mapper;

import pl.com.coders.shop2.domain.User;
import pl.com.coders.shop2.dto.UserDTO;

public class UserMapper implements IMapper<User, UserDTO> {
    private final static UserMapper instance = new UserMapper();

    private UserMapper(){}

    public static UserMapper getInstance(){
        return instance;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(userDTO.getPassword())
                .build();
    }

    @Override
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();
    }
}
