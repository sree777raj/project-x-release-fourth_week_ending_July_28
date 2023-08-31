package com.prakat.projectx.dto;



import com.prakat.projectx.enums.EmployeesStatus;
import lombok.Data;

@Data
public class ProjectHoursDto {
    private Integer approvedHours;
    private EmployeesStatus status;
    private BandDto band;


}
