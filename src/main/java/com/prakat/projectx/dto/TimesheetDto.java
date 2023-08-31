package com.prakat.projectx.dto;


import com.prakat.projectx.entity.TimesheetId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
/**
 * Data Transfer Object (DTO) representing a timesheet.
 * This class is used to transfer timesheet-related information between different layers of the application,
 * such as between the UI and service layer or between the service layer and the persistence layer.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimesheetDto {
    private TimesheetId timesheetId;
    private LocalDate date;
    private String comments;
    private List<TimesheetEntryDto> timesheetEntryId;
    private String email;

}
