package com.hellspawn287.user.model.dto;

public record RoleFullDto(String roleId,
                          String roleName,
                          String resourceName,
                          String creationDate,
                          String createdBy,
                          String lastModifiedDate,
                          String lastModifiedBy) {
}
