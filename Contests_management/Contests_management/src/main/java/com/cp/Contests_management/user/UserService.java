package com.cp.Contests_management.user;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public UserResponseDto createUser(UserDto userDto){
        if (userRepository.existsByEmail(userDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }else if(userRepository.existsByName(userDto.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists");
        }
        var user = userMapper.dtoToUser(userDto);
        var savedUser = userRepository.save(user);
        return userMapper.userToUserResponseDto(savedUser);
    }
    public List<UserResponseDto> getAllUsers(){
        java.util.List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(userMapper::userToUserResponseDto)
                .collect(Collectors.toList());
    }
    public UserResponseDto getUserById(Integer userId) {
        return userMapper.userToUserResponseDto(
                userRepository.findById(userId)
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
        );
    }
    public List<UserResponseDto> getUserByName(String userName){
        List<User> users = userRepository.findByNameContaining(userName);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found with name: " + userName);
        }
        return users.stream().map(userMapper::userToUserResponseDto).collect(Collectors.toList());
    }

    public void deleteUserById(Integer userId){
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User  Id not found");
        }

        userRepository.deleteById(userId);
    }
    @Transactional
    public void deleteUserByName( String userName){
        if (!userRepository.existsByName(userName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Name not found");
        }
        this.userRepository.deleteByName(userName);
    }

}
