package com.prakat.projectx.dto;


import com.prakat.projectx.enums.EmployeesStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmploymentTypeDto {

    private String employmentType;

    private EmployeesStatus status;
}
