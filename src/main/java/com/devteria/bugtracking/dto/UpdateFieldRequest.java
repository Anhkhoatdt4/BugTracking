package com.devteria.bugtracking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateFieldRequest {
    private Long id;
    private String field;
    private String value;

}
