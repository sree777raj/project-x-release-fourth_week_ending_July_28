package com.prakat.projectx.service;

import com.prakat.projectx.dto.TimesheetDto;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetService {
    TimesheetDto createTimesheet(TimesheetDto timesheetDto);


    TimesheetDto getByDate(LocalDate date);


    List<TimesheetDto> getAllTimesheets();

    boolean deleteTimesheet(Long id);

}
