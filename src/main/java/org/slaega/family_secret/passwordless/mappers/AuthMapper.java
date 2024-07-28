package org.slaega.family_secret.passwordless.mappers;

import org.mapstruct.Mapper;
import org.slaega.family_secret.passwordless.dto.SignInRequest;
import org.slaega.family_secret.passwordless.dto.SignUpRequest;
import org.slaega.family_secret.passwordless.model.AuthUser;

@Mapper(componentModel = "spring")
public interface  AuthMapper {
    AuthUser toEntity(SignUpRequest dto);
    AuthUser toEntity(SignInRequest dto);
    
}
