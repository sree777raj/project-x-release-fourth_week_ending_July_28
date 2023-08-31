package com.prakat.projectx.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prakat.projectx.dto.TimesheetEntryDto;
import com.prakat.projectx.entity.*;
import com.prakat.projectx.repo.*;
import com.prakat.projectx.service.TimesheetEntryService;
import com.prakat.projectx.utils.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimesheetEntryServiceImpl implements TimesheetEntryService {
    @Autowired
    private TimesheetEntryRepository timesheetEntryRepository;
    @Autowired
    private TimesheetRepository timesheetRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JobTypeRepository jobTypeRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public TimesheetEntryDto createUpdateTimesheetEntry(TimesheetEntryDto timesheetEntryDto) {

        Optional<JobType> jobTypeOptional=jobTypeRepository.findById(timesheetEntryDto.getJobTypeId());
        JobType jobType = jobTypeOptional.orElseThrow(() -> new IllegalArgumentException("JobType not found " +
                "with ID: " + " " + timesheetEntryDto.getJobTypeId()));
        Optional<Project> projectOptional=projectRepository.findById(timesheetEntryDto.getProjectId());
        Project project = projectOptional.orElseThrow(() -> new IllegalArgumentException("Project not found " +
                "with ID: " + " " + timesheetEntryDto.getProjectId()));
        Optional<Activity> activityOptional=activityRepository.findById(timesheetEntryDto.getActivityId());
        Activity activity = activityOptional.orElseThrow(() -> new IllegalArgumentException("Activity not found " +
                "with ID: " + " " + timesheetEntryDto.getActivityId()));

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(Util.getLoggedInUserEmail());

        if (!optionalEmployee.isPresent()) {
            throw new IllegalArgumentException("Employee with given email does not exist");
        }
        TimesheetId timesheetId=new TimesheetId();
        timesheetId.setEmployee(optionalEmployee.get());
        timesheetId.setDate(timesheetEntryDto.getEntryDate());
        Optional<Timesheet>  optionalTimesheet = timesheetRepository.findByTimesheetId(timesheetId);
        Timesheet timesheet;

        if (optionalTimesheet.isPresent()) {
            timesheet = optionalTimesheet.get();
        } else {
            timesheet = new Timesheet();
            timesheet.setTimesheetId(timesheetId);
            timesheet.setDeleted(false);
            timesheetRepository.save(timesheet);
        }

        TimesheetEntry timesheetEntry = modelMapper.map(timesheetEntryDto, TimesheetEntry.class);
        timesheetEntry.setJobType(jobType);
        timesheetEntry.setProject(project);
        timesheetEntry.setActivity(activity);
        timesheetEntry.setTimesheet(timesheet);

        if (timesheetEntryDto.getTimesheetEntryId() != null && timesheetEntryDto.getTimesheetEntryId() != 0L) {
            // Update logic
            Optional<TimesheetEntry> existingEntryOptional = timesheetEntryRepository.findById(timesheetEntryDto.getTimesheetEntryId());
            if (existingEntryOptional.isPresent()) {

                TimesheetEntry existingEntry = existingEntryOptional.get();
                existingEntry.setJobType(jobType);
                existingEntry.setProject(project);
                existingEntry.setActivity(activity);
                existingEntry.setTimesheetEntryId(timesheetEntryDto.getTimesheetEntryId());
                existingEntry.setComments(timesheetEntryDto.getComments());
                existingEntry.setStartTime(timesheetEntryDto.getStartTime());
                existingEntry.setEndTime(timesheetEntryDto.getEndTime());
                existingEntry.setEntryDate(timesheetEntryDto.getEntryDate());
                existingEntry.setNoOfHours(timesheetEntryDto.getNoOfHours());
                existingEntry.setTimeSheetDate(timesheetEntryDto.getTimeSheetDate());

                TimesheetEntry updatedEntry = timesheetEntryRepository.save(existingEntry);
                return modelMapper.map(updatedEntry, TimesheetEntryDto.class);
            } else {
                throw new IllegalArgumentException("TimesheetEntry with given ID not found for updating.");
            }
        } else {

            TimesheetEntry createdEntry = timesheetEntryRepository.save(timesheetEntry);
            return modelMapper.map(createdEntry, TimesheetEntryDto.class);
        }
    }

    @Override
    public boolean deleteTimesheetEntry(Long timesheetEntryId) {
        Optional<TimesheetEntry> timesheetEntryOptional = timesheetEntryRepository.findById(timesheetEntryId);

        if (timesheetEntryOptional.isPresent()) {
            timesheetEntryRepository.deleteById(timesheetEntryId);
            return true;
        } else {
            return false;
        }
    }
}
