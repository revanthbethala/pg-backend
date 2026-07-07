package com.pg.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pg.dto.GuestDto;
import com.pg.entity.GuestEntity;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    @Mapping(target = "room", ignore = true)
    GuestEntity toEntity(GuestDto dto);

    @Mapping(source = "room.id", target = "roomId")
    GuestDto toDto(GuestEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    void updateEntity(GuestDto dto, @MappingTarget GuestEntity entity);
}