package com.pg.mapper;

import org.mapstruct.Mapper;

import com.pg.dto.RegisterRequestDto;
import com.pg.dto.UserDto;
import com.pg.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@org.mapstruct.Mapping(target = "branches", ignore = true)
	@org.mapstruct.Mapping(target = "refreshToken", ignore = true)
	@org.mapstruct.Mapping(target = "id", ignore = true)
	UserEntity toEntity(RegisterRequestDto request);
	@org.mapstruct.Mapping(target = "branches", ignore = true)
	@org.mapstruct.Mapping(target = "refreshToken", ignore = true)
	UserEntity toEntity(UserDto userDto);

	@org.mapstruct.Mapping(target = "password", ignore = true)
	UserDto toDto(UserEntity userEntity);
}
