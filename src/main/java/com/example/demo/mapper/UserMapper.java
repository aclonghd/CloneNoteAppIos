package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.module.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
}
