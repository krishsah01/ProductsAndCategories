package com.elkabani.firstspringboot.mappers;

import com.elkabani.firstspringboot.dtos.RegisterUserRequest;
import com.elkabani.firstspringboot.dtos.UpdateUserRequest;
import com.elkabani.firstspringboot.dtos.UserDto;
import com.elkabani.firstspringboot.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);
  User toEntity(RegisterUserRequest request);
 void update(UpdateUserRequest request,  @MappingTarget User user);
}
