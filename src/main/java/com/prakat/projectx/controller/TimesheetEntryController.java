package com.prakat.projectx.controller;

import com.prakat.projectx.dto.TimesheetEntryDto;
import com.prakat.projectx.service.TimesheetEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/timesheetentry")
public class TimesheetEntryController {
    private static final Logger logger= LoggerFactory.getLogger(TimesheetEntryController.class);
    @Autowired
    private TimesheetEntryService timesheetEntryService;

    @PostMapping("/")
    public ResponseEntity<TimesheetEntryDto> createTimesheetEntry(@RequestBody TimesheetEntryDto timesheetEntryDto) {
        TimesheetEntryDto createTimesheetEntry = timesheetEntryService.createUpdateTimesheetEntry(timesheetEntryDto);
        return new ResponseEntity<TimesheetEntryDto>(createTimesheetEntry, HttpStatus.CREATED);
    }

    @DeleteMapping("/{timesheetEntryId}")
    public ResponseEntity<String> deleteTimesheetEntry(@PathVariable Long timesheetEntryId) {
        boolean deleted = timesheetEntryService.deleteTimesheetEntry(timesheetEntryId);

        if (deleted) {
            return ResponseEntity.ok("Timesheet with ID " + timesheetEntryId + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Timesheet with ID " + timesheetEntryId + " not found.");
        }
    }
    @PutMapping("/{id}")
    public Object updateTimesheetEntry(@RequestBody TimesheetEntryDto timesheetEntryDto)
    {
        if(timesheetEntryDto !=null && timesheetEntryDto.getTimesheetEntryId() !=null && timesheetEntryDto.getTimesheetEntryId()!=0L){
            TimesheetEntryDto updateTimesheetEntry = timesheetEntryService.createUpdateTimesheetEntry(timesheetEntryDto);
            return new ResponseEntity<TimesheetEntryDto>(updateTimesheetEntry, HttpStatus.OK);
        }else{
            return ResponseEntity.badRequest();
        }

    }


}
