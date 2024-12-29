package com.hellspawn287.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistoryUserDto<U> {
    private String revisionType;
    private Number operationNumber;
    private U dto;
}