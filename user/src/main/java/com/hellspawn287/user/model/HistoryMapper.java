package com.hellspawn287.user.model;

import com.hellspawn287.user.model.dto.HistoryUserDto;
import com.hellspawn287.user.model.dto.UserFullDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revision;

@Mapper(componentModel = "spring", uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HistoryMapper {

    @Mapping(source = "metadata.requiredRevisionNumber", target = "operationNumber")
    @Mapping(source = "metadata.revisionType", target = "revisionType")
    @Mapping(source = "entity", target = "dto")
    HistoryUserDto<UserFullDto> toDto(Revision<Integer, User> revision);

    default PageRequest toPageRequest(int page, int size) {
        return PageRequest.of( page, size);
    }
}
