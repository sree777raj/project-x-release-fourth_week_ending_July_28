package com.prakat.projectx.dto;

import com.prakat.projectx.enums.EmployeesStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectDetailDto {

    private String projectName;

    private String projectValue;
    private String approvedBudget;

    private Date startDate;

    private Date endDate;

    private String projectType;

    private List<ProjectHoursDto> projectHours;

    private int maxNumberHours;

    private EmployeesStatus status;



}
