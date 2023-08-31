package com.prakat.projectx.service;

import com.prakat.projectx.dto.TimesheetEntryDto;

public interface TimesheetEntryService {
    public TimesheetEntryDto createUpdateTimesheetEntry(TimesheetEntryDto timesheetEntryDto);

    boolean deleteTimesheetEntry(Long timesheetEntryId);
}