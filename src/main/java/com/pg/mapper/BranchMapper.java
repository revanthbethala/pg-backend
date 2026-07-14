package com.pg.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pg.dto.BranchDto;
import com.pg.entity.BranchEntity;
@Mapper(componentModel = "spring")
public interface BranchMapper  {

	@Mapping(source = "user.id", target = "userId")
	@Mapping(target = "rooms", ignore = true)
	BranchDto toDto(BranchEntity branchEntity);

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "rooms", ignore = true)
	BranchEntity toEntity(BranchDto branchDto);
	
	
	java.util.List<BranchDto> toDtoList(java.util.List<BranchEntity> branchEntities);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "rooms", ignore = true)
	void updateEntityFromDto(BranchDto dto, @MappingTarget BranchEntity entity);

	
}
