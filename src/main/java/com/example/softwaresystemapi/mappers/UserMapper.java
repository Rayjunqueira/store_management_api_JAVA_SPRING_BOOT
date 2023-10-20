package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.UserDto;
import com.example.softwaresystemapi.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "user_id", ignore = true)
    UserModel toUserModel (UserDto userDto);
}
