package com.elkabani.firstspringboot.mappers;

import com.elkabani.firstspringboot.dtos.UserDto;
import com.elkabani.firstspringboot.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);
}
