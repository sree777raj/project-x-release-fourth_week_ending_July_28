package com.prakat.projectx.dto;

import java.time.LocalDate;
import java.util.List;

import com.prakat.projectx.enums.EmployeesStatus;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String empId;
    private GenderDto gender;
    private LocalDate dob;
    private String phoneNumber;
    private String email;
    private BandDto bandId;
    private List<SkillDto> skillId;
    private WorkLocationDto locationId;
    private EmploymentTypeDto employmentType;
    private EmployeeDto manager;
    private Boolean rowStatus;
    private String ctc;
    private LocalDate joiningDate;
    private String userPassword;
    private EmployeesStatus status;

}