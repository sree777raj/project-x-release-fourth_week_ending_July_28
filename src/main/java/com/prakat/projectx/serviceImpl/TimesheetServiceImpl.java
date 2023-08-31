package com.prakat.projectx.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prakat.projectx.dto.TimesheetDto;
import com.prakat.projectx.entity.*;
import com.prakat.projectx.repo.*;

import com.prakat.projectx.service.TimesheetService;
import com.prakat.projectx.utils.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final ModelMapper modelMapper;

    private final EmployeeRepository employeeRepository;

    private final ActivityRepository activityRepository;

    private final JobTypeRepository jobTypeRepository;

    private final ProjectRepository projectRepository;
    private  final TimesheetEntryRepository timesheetEntryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public TimesheetServiceImpl(TimesheetRepository timesheetRepository, ModelMapper modelMapper,EmployeeRepository employeeRepository,ActivityRepository activityRepository,JobTypeRepository jobTypeRepository,ProjectRepository projectRepository,TimesheetEntryRepository timesheetEntryRepository) {
        this.timesheetRepository = timesheetRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.activityRepository = activityRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.projectRepository = projectRepository;
        this.timesheetEntryRepository = timesheetEntryRepository;
    }


    @Override
    public TimesheetDto createTimesheet(TimesheetDto timesheetDto) {
        // Check if the date and comments fields in the TimesheetDto are not empty or null
        if (timesheetDto.getDate() == null) {
            throw new IllegalArgumentException("Date cannot be empty or null.");
        }

        if (timesheetDto.getComments() == null || timesheetDto.getComments().isEmpty()) {
            throw new IllegalArgumentException("Comments cannot be empty or null.");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(timesheetDto.getEmail());

        if(!optionalEmployee.isPresent()){
            throw new IllegalArgumentException("Employee with given email does not exist");
        }

        // Map the TimesheetDto to a Timesheet entity using ModelMapper
        Timesheet timesheet = modelMapper.map(timesheetDto, Timesheet.class);


        if(timesheetDto.getTimesheetId() ==null){
            // Timesheet create case.
            TimesheetId timesheetId=new TimesheetId();
            timesheetId.setEmployee(optionalEmployee.get());
            timesheetId.setDate(timesheetDto.getDate());
            Optional<Timesheet>  optionalTimesheet = timesheetRepository.findByTimesheetId(timesheetId);
            if(optionalTimesheet.isPresent()){
                throw new IllegalArgumentException("Timesheet for this employee already exist, cannot create another timesheet for same day");
            }


            List<TimesheetEntry> timesheetEntries= timesheet.getTimesheetEntryId();
            timesheet.setTimesheetEntryId(null);
            timesheet.setTimesheetId(timesheetId);
            timesheetRepository.save(timesheet);

            for(int i=0;i<timesheetEntries.size();i++){
                TimesheetEntry timesheetEntry = timesheetEntries.get(i);

                //set Activity Object
                Optional<Activity> optionalActivity = activityRepository.findById(timesheetDto.getTimesheetEntryId().get(i).getActivityId());
                if(optionalActivity.isPresent()){
                    timesheetEntry.setActivity(optionalActivity.get());
                }

                //Set JobType Object
                Optional<JobType> optionalJobType = jobTypeRepository.findById(timesheetDto.getTimesheetEntryId().get(i).getJobTypeId());
                if(optionalJobType.isPresent()){
                    timesheetEntry.setJobType(optionalJobType.get());
                }

                //Set Project
                Optional<Project> optionalProject = projectRepository.findById(timesheetDto.getTimesheetEntryId().get(i).getProjectId());
                if(optionalProject.isPresent()){
                    timesheetEntry.setProject(optionalProject.get());
                }

                timesheetEntry.setTimesheet(timesheet);

            }

            timesheetEntryRepository.saveAll(timesheetEntries);

        }else {

            // Timesheet update case.
            TimesheetId timesheetId=new TimesheetId();
            timesheetId.setEmployee(optionalEmployee.get());
            timesheet.setTimesheetId(timesheetId);

           // timesheet.setEmployee(optionalEmployee.get());

            for(int i=0;i<timesheet.getTimesheetEntryId().size();i++){
                TimesheetEntry timesheetEntry = timesheet.getTimesheetEntryId().get(i);

                //set Activity Object
                Optional<Activity> optionalActivity = activityRepository.findById(timesheetDto.getTimesheetEntryId().get(i).getActivityId());
                if(optionalActivity.isPresent()){
                    timesheetEntry.setActivity(optionalActivity.get());
                }

                //Set JobType Object
                Optional<JobType> optionalJobType = jobTypeRepository.findById(timesheetDto.getTimesheetEntryId().get(i).getJobTypeId());
                if(optionalJobType.isPresent()){
                    timesheetEntry.setJobType(optionalJobType.get());
                }

                //Set Project
                Optional<Project> optionalProject = projectRepository.findById(timesheetDto.getTimesheetEntryId().get(i).getProjectId());
                if(optionalProject.isPresent()){
                    timesheetEntry.setProject(optionalProject.get());
                }

                timesheetEntry.setTimesheet(timesheet);

            }

            timesheetRepository.save(timesheet);

        }


        // Save the timesheet to the database and let the repository generate the ID
        // Return the saved Timesheet entity directly as a TimesheetDto
        TimesheetId timesheetId=new TimesheetId();
        timesheetId.setDate(timesheetDto.getDate());
        timesheetId.setEmployee(optionalEmployee.get());

        return modelMapper.map(timesheetRepository.findByTimesheetId(timesheetId), TimesheetDto.class);
        //return objectMapper.convertValue(timesheetRepository.findById(timesheet.getId()), TimesheetDto.class);
    }

    @Override
    public TimesheetDto getByDate(LocalDate date) {
        String loggedInUserEmail = Util.getLoggedInUserEmail();
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(loggedInUserEmail);
        if (!optionalEmployee.isPresent()) {
            throw new IllegalArgumentException("Employee with given email not found");
        }

        Employee employee = optionalEmployee.get();
        TimesheetId timesheetId=new TimesheetId();
        timesheetId.setEmployee(optionalEmployee.get());
        timesheetId.setDate(date);
        Optional<Timesheet>  optionalTimesheet = timesheetRepository.findByTimesheetId(timesheetId);
        if (!optionalTimesheet.isPresent()) {
            throw new IllegalArgumentException("Timesheet with given date and employee does not exist");
        }

        Timesheet timesheet = optionalTimesheet.get();
        return modelMapper.map(timesheet, TimesheetDto.class);
    }

    @Override
    public List<TimesheetDto> getAllTimesheets() {
        List<Timesheet> timesheets = timesheetRepository.findAll();
        return timesheets.stream()
                .map(timesheet -> modelMapper.map(timesheet, TimesheetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTimesheet(Long id) {
        Optional<Timesheet> timesheetOptional = timesheetRepository.findById(id);

        if (timesheetOptional.isPresent()) {
            timesheetRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}


