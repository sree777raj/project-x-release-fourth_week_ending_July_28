package com.prakat.projectx.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class TimesheetEntryDto {

    private Long timesheetEntryId;
	private LocalDate entryDate;
    private LocalDate timeSheetDate;
    private Long projectId;
    private Long jobTypeId;
    private String comments;
    private double noOfHours;
    private Long activityId;
    private LocalTime startTime;
    private LocalTime endTime;


}
