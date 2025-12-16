package com.example.expense_tracer.mapper;
import com.example.expense_tracer.dto.UserDto;
import com.example.expense_tracer.entity.User;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
    List<UserDto> toDtoList(List<User> users);
}
