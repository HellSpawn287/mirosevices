package com.hellspawn287.user.model;

import com.hellspawn287.user.model.dto.UserFullDto;
import com.hellspawn287.user.model.dto.RegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static java.sql.Timestamp.valueOf;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "userName")
    UserFullDto mapUserToRecord(User user);

    @Mapping(source = "userName", target = "username")
    User mapRecordToUser(UserFullDto record);

    @Mapping(source = "password", target = "hashedPassword")
    User mapRegistrationDtoToUser(RegistrationDto registrationDto);
}
