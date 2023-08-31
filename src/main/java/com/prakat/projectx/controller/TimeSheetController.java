package com.prakat.projectx.controller;

import com.prakat.projectx.dto.TimesheetDto;
import com.prakat.projectx.service.TimesheetService;
import com.prakat.projectx.service.TimesheetService;
import com.prakat.projectx.utils.Util;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/timesheet")
  public class TimeSheetController {
    TimesheetService timesheetservice;
    private final ModelMapper modelMapper;

    public TimeSheetController(TimesheetService timesheetservice, ModelMapper modelMapper) {
        this.timesheetservice = timesheetservice;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<TimesheetDto> createTimesheet(@RequestBody TimesheetDto timesheetDto) {
        String loggedInUserEmail = Util.getLoggedInUserEmail();
        timesheetDto.setEmail(loggedInUserEmail);
        TimesheetDto createdTimesheet = timesheetservice.createTimesheet(timesheetDto);
        return new ResponseEntity<>(createdTimesheet, HttpStatus.CREATED);
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<TimesheetDto> getByDate(@PathVariable LocalDate date) {
        TimesheetDto allTimesheet= timesheetservice.getByDate(date);
        return ResponseEntity.ok(allTimesheet);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<TimesheetDto>> getAllTimesheet() {
        List<TimesheetDto> allTimesheet= timesheetservice.getAllTimesheets();
        return ResponseEntity.ok(allTimesheet);
    }


    @DeleteMapping("/delete/{timesheetId}")
    public ResponseEntity<String> deleteTimesheet(@PathVariable Long timesheetId) {
        boolean deleted = timesheetservice.deleteTimesheet(timesheetId);
        if (deleted) {
            return ResponseEntity.ok("Timesheet with ID " + timesheetId + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Timesheet with ID " + timesheetId + " not found.");
        }
    }


}
