package com.pg.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pg.dto.RoomDto;
import com.pg.entity.RoomEntity;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "branchId", ignore = true)
    @Mapping(target = "guests", ignore = true)
    RoomEntity toEntity(RoomDto dto);

    @Mapping(source = "branchId.id", target = "branchId")
    RoomDto toDto(RoomEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branchId", ignore = true)
    @Mapping(target = "guests", ignore = true)
    void updateEntity(RoomDto dto, @MappingTarget RoomEntity entity);

}