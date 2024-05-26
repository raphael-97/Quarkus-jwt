package org.acme.mapper;

import org.acme.dto.RegistrationRequest;
import org.acme.dto.UserRequest;
import org.acme.dto.UserResponse;
import org.acme.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);

    User UserRequestToUser(UserRequest userRequest);

    User RegistrationRequestToUser(RegistrationRequest registrationRequest);

}
