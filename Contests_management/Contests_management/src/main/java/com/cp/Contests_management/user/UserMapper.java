package com.cp.Contests_management.user;


import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User dtoToUser(UserDto userDto){
        if(userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        return user;
    }
    public UserResponseDto userToUserResponseDto(User user){
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return new UserResponseDto(
                user.getName(),
                user.getEmail(),
                user.getRating()
        );
    }


}
