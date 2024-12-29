package com.hellspawn287.user.model;

import com.hellspawn287.user.model.dto.RoleFullDto;
import jakarta.persistence.Converter;

import java.util.UUID;

import static java.sql.Timestamp.valueOf;

@Converter(autoApply = true)
public class RoleMapper extends BaseMapper{

    public RoleFullDto mapRoleToRecord(Role entity) {
        return new RoleFullDto(
                entity.getId().toString(),
                entity.getRoleName(),
                entity.getResourceName(),
                convertToDatabaseColumn(entity.getCreationDate()).toString(),
                entity.getCreatedBy(),
                convertToDatabaseColumn(entity.getLastModifiedDate()).toString(),
                entity.getLastModifiedBy()
        );
    }

    public Role mapRecordToRole(RoleFullDto record) {
        return new Role.RoleBuilder()
                .id(UUID.fromString(record.roleId()))
                .roleName(record.roleName())
                .resourceName(record.resourceName())
                .creationDate(convertToEntityAttribute(valueOf(record.creationDate())))
                .createdBy(record.createdBy())
                .lastModifiedDate(convertToEntityAttribute((valueOf(record.lastModifiedDate()))))
                .lastModifiedBy(record.lastModifiedBy())
                .build();
    }
}
