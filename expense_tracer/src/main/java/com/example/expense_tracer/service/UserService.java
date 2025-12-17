package com.example.expense_tracer.service;

import com.example.expense_tracer.dto.UserDto;
import com.example.expense_tracer.entity.User;
import com.example.expense_tracer.mapper.UserMapper;
import com.example.expense_tracer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return userMapper.toDto(user);
    }

    public UserDto add(UserDto dto) {
        User user = userMapper.toEntity(dto);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}