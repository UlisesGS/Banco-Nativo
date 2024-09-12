package com.igrowker.nativo.mappers;

import com.igrowker.nativo.dtos.RegisterUserDto;
import com.igrowker.nativo.dtos.UserDto;
import com.igrowker.nativo.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User registerUsertoUser(RegisterUserDto registerUserDto);
    UserDto userToUserDTO(User user);
}
