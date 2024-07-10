package org.slaega.family_secret.mappers;

import org.mapstruct.Mapper;
import org.slaega.family_secret.dto.auth.SignInRequest;
import org.slaega.family_secret.dto.auth.SignUpRequest;

import org.slaega.family_secret.mobel.UserModel;

@Mapper(componentModel = "spring")
public interface  UserMapper {
    UserModel toEntity(SignUpRequest dto);
    UserModel toEntity(SignInRequest dto);
    
}
