package com.prakat.projectx.serviceImpl;

import com.prakat.projectx.dto.*;
import com.prakat.projectx.entity.*;
import com.prakat.projectx.repo.*;
import com.prakat.projectx.service.MasterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired

    private BandRepository bandRepository;

    @Autowired

    private WorkLocationRepository workLocationRepository;

    @Autowired

    private SkillRepository skillRepository;

    @Autowired

    private JobTypeRepository jobTypeRepository;

    @Autowired

    private EmploymentTypeRepository employmentTypeRepository;

    @Autowired

    private ActivityRepository activityRepository;

    @Autowired

    private ProjectRepository projectRepository;

    @Autowired

    private GenderRepository genderRepository;
    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Autowired

    private ModelMapper modelMapper;


    public MasterDto getMasterDto() {

        MasterDto masterDto = new MasterDto();


        masterDto.setSkill(getAllSkill());

        masterDto.setJobtype(getAllJobType());

        masterDto.setGender(getAllGender());

        masterDto.setEmploymentType(getAllEmploymentType());

        masterDto.setBand(getAllBand());

        masterDto.setActivity(getAllActivity());

        masterDto.setWorkLocation(getAllWorkLocation());

        masterDto.setProjectBasics(getAllProjectBasics());
        masterDto.setProjectTypes(getAllProjectTypes());

        return masterDto;

    }


    @Override

    public List<BandDto> getAllBand() {

        List<Band> bands = bandRepository.findAll();

        return bands.stream().map((band) -> modelMapper.map(band, BandDto.class))

                .collect(Collectors.toList());

    }


    @Override

    public List<SkillDto> getAllSkill() {

        List<Skill> skills = skillRepository.findAll();

        return skills.stream().map((skill) -> modelMapper.map(skill, SkillDto.class))

                .collect(Collectors.toList());

    }


    @Override

    public List<WorkLocationDto> getAllWorkLocation() {

        List<WorkLocation> workLocations = workLocationRepository.findAll();

        return workLocations.stream().map((workLocation) -> modelMapper.map(workLocation, WorkLocationDto.class))

                .collect(Collectors.toList());

    }


    @Override

    public List<JobtypeDto> getAllJobType() {

        List<JobType> jobTypes = jobTypeRepository.findAll();

        return jobTypes.stream().map((jobType) -> modelMapper.map(jobType, JobtypeDto.class))

                .collect(Collectors.toList());

    }


    @Override

    public List<EmploymentTypeDto> getAllEmploymentType() {

        List<EmploymentType> employmentTypes = employmentTypeRepository.findAll();

        return employmentTypes.stream().map((employmentType) -> modelMapper.map(employmentType, EmploymentTypeDto.class))

                .collect(Collectors.toList());

    }


    @Override

    public List<ProjectBasicsDto> getAllProjectBasics() {

        List<Project> projects = projectRepository.findAll();

        return projects.stream().map((project) -> modelMapper.map(project, ProjectBasicsDto.class))

                .collect(Collectors.toList());


    }


    @Override

    public List<ActivityDto> getAllActivity() {

        List<Activity> activities = activityRepository.findAll();

        return activities.stream().map((activity) -> modelMapper.map(activity, ActivityDto.class))

                .collect(Collectors.toList());

    }


    @Override

    public List<GenderDto> getAllGender() {

        List<Gender> genders = genderRepository.findAll();

        return genders.stream().map((gender) -> modelMapper.map(gender, GenderDto.class))

                .collect(Collectors.toList());

    }
    @Override

    public List<ProjectTypeDto> getAllProjectTypes() {

        List<ProjectType> projectTypes = projectTypeRepository.findAll();

        return projectTypes.stream().map((projectType) -> modelMapper.map(projectType,ProjectTypeDto.class))
                .collect(Collectors.toList());
    }





}
