//package com.prakat.projectx.serviceImpl;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import com.prakat.projectx.dto.*;
//import com.prakat.projectx.enums.EmployeesStatus;
//import com.prakat.projectx.service.MasterService;
//import org.hibernate.collection.spi.PersistentBag;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import com.prakat.projectx.entity.Band;
//import com.prakat.projectx.entity.Skill;
//import com.prakat.projectx.entity.WorkLocation;
//import com.prakat.projectx.entity.JobType;
//import com.prakat.projectx.entity.EmploymentType;
//import com.prakat.projectx.entity.Activity;
//import com.prakat.projectx.entity.Project;
//import com.prakat.projectx.entity.Gender;
//import com.prakat.projectx.repo.BandRepository;
//import com.prakat.projectx.repo.SkillRepository;
//import com.prakat.projectx.repo.WorkLocationRepository;
//import com.prakat.projectx.repo.JobTypeRepository;
//import com.prakat.projectx.repo.EmploymentTypeRepository;
//import com.prakat.projectx.repo.ActivityRepository;
//import com.prakat.projectx.repo.ProjectRepository;
//import com.prakat.projectx.repo.GenderRepository;
//import org.modelmapper.ModelMapper;
//
//@RunWith(MockitoJUnitRunner.class)
//public class MasterServiceImplTest {
//
//    @Mock
//    private BandRepository bandRepository;
//    @Mock
//    private WorkLocationRepository workLocationRepository;
//    @Mock
//    private SkillRepository skillRepository;
//    @Mock
//    private JobTypeRepository jobTypeRepository;
//    @Mock
//    private EmploymentTypeRepository employmentTypeRepository;
//    @Mock
//    private ActivityRepository activityRepository;
//    @Mock
//    private ProjectRepository projectRepository;
//    @Mock
//    private GenderRepository genderRepository;
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private MasterServiceImpl masterService;
//
//    private List<Band> mockBands;
//    private List<Skill> mockSkills;
//    private List<WorkLocation> mockWorkLocations;
//    private List<JobType> mockJobTypes;
//    private List<EmploymentType> mockEmploymentTypes;
//    private List<Activity> mockActivities;
//    private List<Project> mockProjects;
//    private List<Gender> mockGenders;
//    @Test
//    public void testGetAllBand() {
//
//        Band band = new Band();
//        band.setBandId(1L);
//        band.setBandName("L1");
//        List<Band> mockBands = Arrays.asList(band);
//        when(bandRepository.findAll()).thenReturn(mockBands);
//        BandDto bandDto = new BandDto();
//        bandDto.setBandId(1L);
//        bandDto.setBandName("L1");
//        when(modelMapper.map(band, BandDto.class)).thenReturn(bandDto);
//        List<BandDto> result = masterService.getAllBand();
//        assertEquals(1, result.size());
//        assertEquals(bandDto.getBandId(), result.get(0).getBandId());
//        assertEquals(bandDto.getBandName(), result.get(0).getBandName());
//
//            }
//    @Test
//    public void testGetAllSkill() {
//
//        Skill skill = new Skill();
//        skill.setSkillId(1L);
//        skill.setSkillName("java");
//        List<Skill> mockSkills = Arrays.asList(skill);
//        when(skillRepository.findAll()).thenReturn(mockSkills);
//        SkillDto skillDto = new SkillDto();
//        skillDto.setSkillId(1L);
//        skillDto.setSkillName("java");
//        when(modelMapper.map(skill, SkillDto.class)).thenReturn(skillDto);
//        List<SkillDto> result = masterService.getAllSkill();
//        assertEquals(1, result.size());
//        assertEquals(skillDto.getSkillId(), result.get(0).getSkillId());
//        assertEquals(skillDto.getSkillName(), result.get(0).getSkillName());
//
//    }
//    @Test
//    public void testGetAllWorkLocation() {
//
//        WorkLocation workLocation = new WorkLocation();
//        workLocation.setLocationId(1L);
//        workLocation.setLocationName("Bangalore");
//        List<WorkLocation> mockWorklocation = Arrays.asList(workLocation);
//        when(workLocationRepository.findAll()).thenReturn(mockWorklocation);
//        WorkLocationDto workLocationDto  = new WorkLocationDto();
//        workLocationDto.setLocationId(1L);
//        workLocationDto.setLocationName("Bangalore");
//        when(modelMapper.map(workLocation, WorkLocationDto.class)).thenReturn(workLocationDto);
//        List<WorkLocationDto> result = masterService.getAllWorkLocation();
//        assertEquals(1, result.size());
//        assertEquals(workLocationDto.getLocationId(), result.get(0).getLocationId());
//        assertEquals(workLocationDto.getLocationName(), result.get(0).getLocationName());
//
//    }
//    @Test
//    public void testGetAllJobType() {
//
//        JobType jobType = new JobType();
//        jobType.setJobId(1L);
//        jobType.setJobType("consultant");
//        List<JobType> mockJobType = Arrays.asList(jobType);
//        when(jobTypeRepository.findAll()).thenReturn(mockJobType);
//        JobtypeDto jobtypeDto  = new JobtypeDto();
//        jobtypeDto.setJobId(1L);
//        jobType.setJobType("consultant");
//        when(modelMapper.map(jobType, JobtypeDto.class)).thenReturn(jobtypeDto);
//        List<JobtypeDto> result = masterService.getAllJobType();
//        assertEquals(1, result.size());
//        assertEquals(jobtypeDto.getJobId(), result.get(0).getJobId());
//        assertEquals(jobtypeDto.getJobType(), result.get(0).getJobType());
//
//    }
//
//    @Test
//    public void testGetAllEmploymentType() {
//
//        EmploymentType employmentType = new EmploymentType();
//        employmentType.setEmploymentType("contract");
//        employmentType.setDeleted(false);
//        List<EmploymentType> mockEmployementType = Arrays.asList(employmentType);
//        when(employmentTypeRepository.findAll()).thenReturn(mockEmployementType);
//        EmploymentTypeDto employmentTypeDto  = new EmploymentTypeDto();
//        employmentTypeDto.setEmploymentType("consultant");
//        employmentTypeDto.setStatus(EmployeesStatus.ACTIVE);
//        when(modelMapper.map(employmentType, EmploymentTypeDto.class)).thenReturn(employmentTypeDto);
//        List<EmploymentTypeDto> result = masterService.getAllEmploymentType();
//        assertEquals(1, result.size());
//        assertEquals(employmentTypeDto.getEmploymentType(), result.get(0).getEmploymentType());
//        assertEquals(employmentTypeDto.getStatus(), result.get(0).getStatus());
//
//    }
//
//    @Test
//    public void testGetAllProjectBasics() {
//
//        Project project = new Project();
//        project.setId(1L);
//        project.setProjectName("projectx");
//        List<Project> mockProjects = Arrays.asList(project);
//        when(projectRepository.findAll()).thenReturn(mockProjects);
//        ProjectBasicsDto projectBasicsDto = new ProjectBasicsDto();
//        projectBasicsDto.setId(1L);
//        projectBasicsDto.setProjectName("projectx");
//        when(modelMapper.map(project, ProjectBasicsDto.class)).thenReturn(projectBasicsDto);
//        List<ProjectBasicsDto> result = masterService.getAllProjectBasics();
//        assertEquals(1, result.size());
//        assertEquals(projectBasicsDto.getId(), result.get(0).getId());
//        assertEquals(projectBasicsDto.getProjectName(), result.get(0).getProjectName());
//
//    }
//    @Test
//    public void testGetAllActivity() {
//
//        Activity activity = new Activity();
//        activity.setActivityId(1L);
//        activity.setActivityType("Meeting");
//        List<Activity> mockActivities = Arrays.asList(activity);
//        when(activityRepository.findAll()).thenReturn(mockActivities);
//        ActivityDto activityDto = new ActivityDto();
//        activityDto.setActivityId(1L);
//        activityDto.setActivityType("Meeting");
//        when(modelMapper.map(activity, ActivityDto.class)).thenReturn(activityDto);
//        List<ActivityDto> result = masterService.getAllActivity();
//        assertEquals(1, result.size());
//        assertEquals(activityDto.getActivityId(), result.get(0).getActivityId());
//        assertEquals(activityDto.getActivityType(), result.get(0).getActivityType());
//
//    }
//    @Test
//    public void testGetAllGender() {
//
//        Gender gender = new Gender();
//        gender.setGenderId('F');
//        gender.setGender("female");
//        List<Gender> mockGenders = Arrays.asList(gender);
//        when(genderRepository.findAll()).thenReturn(mockGenders);
//        GenderDto genderDto = new GenderDto();
//        genderDto.setId('F');
//        genderDto.setGender("female");
//        when(modelMapper.map(gender, GenderDto.class)).thenReturn(genderDto);
//        List<GenderDto> result = masterService.getAllGender();
//        assertEquals(1, result.size());
//        assertEquals(genderDto.getId(), result.get(0).getId());
//        assertEquals(genderDto.getGender(), result.get(0).getGender());
//
//    }
//    @Test
//    public void testGetMasterDto() {
//
//        MasterDto masterDto = new MasterDto();
//        Band band = new Band();
//        band.setBandId(1L);
//        band.setBandName("L1");
//        List<Band> mockBands = Arrays.asList(band);
//        when(bandRepository.findAll()).thenReturn(mockBands);
//        Skill skill = new Skill();
//        skill.setSkillId(1L);
//        skill.setSkillName("java");
//        List<Skill> mockSkills = Arrays.asList(skill);
//        when(skillRepository.findAll()).thenReturn(mockSkills);
//        WorkLocation workLocation = new WorkLocation();
//        workLocation.setLocationId(1L);
//        workLocation.setLocationName("Bangalore");
//        List<WorkLocation> mockWorklocation = Arrays.asList(workLocation);
//        when(workLocationRepository.findAll()).thenReturn(mockWorklocation);
//        JobType jobType = new JobType();
//        jobType.setJobId(1L);
//        jobType.setJobType("consultant");
//        List<JobType> mockJobType = Arrays.asList(jobType);
//        when(jobTypeRepository.findAll()).thenReturn(mockJobType);
//        EmploymentType employmentType = new EmploymentType();
//        employmentType.setEmploymentType("contract");
//        employmentType.setDeleted(false);
//        List<EmploymentType> mockEmployementType = Arrays.asList(employmentType);
//        when(employmentTypeRepository.findAll()).thenReturn(mockEmployementType);
//        Project project = new Project();
//        project.setId(1L);
//        project.setProjectName("projectx");
//        List<Project> mockProjects = Arrays.asList(project);
//        when(projectRepository.findAll()).thenReturn(mockProjects);
//        Activity activity = new Activity();
//        activity.setActivityId(1L);
//        activity.setActivityType("Meeting");
//        List<Activity> mockActivities = Arrays.asList(activity);
//        when(activityRepository.findAll()).thenReturn(mockActivities);
//        Gender gender = new Gender();
//        gender.setGenderId('F');
//        gender.setGender("female");
//        List<Gender> mockGenders = Arrays.asList(gender);
//        when(genderRepository.findAll()).thenReturn(mockGenders);
//
//        BandDto bandDto = new BandDto();
//        WorkLocationDto workLocationDto = new WorkLocationDto();
//        SkillDto skillDto = new SkillDto();
//        JobtypeDto jobtypeDto = new JobtypeDto();
//        EmploymentTypeDto employmentTypeDto = new EmploymentTypeDto();
//        GenderDto genderDto = new GenderDto();
//        ActivityDto activityDto = new ActivityDto();
//        ProjectBasicsDto projectBasicsDto = new ProjectBasicsDto();
//
//        bandDto.setBandId(1L);
//        bandDto.setBandName("A1");
//
//        workLocationDto.setLocationId(1L);
//        workLocationDto.setLocationName("Bengaluru");
//
//        skillDto.setSkillId(1L);
//        skillDto.setSkillName("Presentation");
//
//        jobtypeDto.setJobId(1L);
//        jobtypeDto.setJobType("Full-time");
//
//        employmentTypeDto.setEmploymentType("contract");
//        employmentTypeDto.setStatus(EmployeesStatus.ACTIVE);
//
//        genderDto.setId('M');
//        genderDto.setGender("Male");
//
//        activityDto.setActivityId(1L);
//        activityDto.setActivityType("Meeting call");
//
//        projectBasicsDto.setId(1L);
//        projectBasicsDto.setProjectName("Project-x");
//
//
//
//        List<BandDto> bandDtosands = Arrays.asList(bandDto);
//        masterDto.setBand(bandDtosands);
//        List<SkillDto> skillDtos=Arrays.asList(skillDto);
//        masterDto.setSkill(skillDtos);
//        List<WorkLocationDto> workLocationDtos=Arrays.asList(workLocationDto);
//        masterDto.setWorkLocation(workLocationDtos);
//        List<JobtypeDto> jobtypeDtos=Arrays.asList(jobtypeDto);
//        masterDto.setJobtype(jobtypeDtos);
//        List<EmploymentTypeDto> employmentTypeDtos=Arrays.asList(employmentTypeDto);
//        masterDto.setEmploymentType(employmentTypeDtos);
//        List<ProjectBasicsDto> projectBasicsDtos=Arrays.asList(projectBasicsDto);
//        masterDto.setProjectBasics(projectBasicsDtos);
//        List<ActivityDto> activityDtos=Arrays.asList(activityDto);
//        masterDto.setActivity(activityDtos);
//        List<GenderDto> genderDtos=Arrays.asList(genderDto);
//        masterDto.setGender(genderDtos);
//        when(modelMapper.map(band, BandDto.class)).thenReturn(bandDto);
//        when(modelMapper.map(skill, SkillDto.class)).thenReturn(skillDto);
//        when(modelMapper.map(workLocation, WorkLocationDto.class)).thenReturn(workLocationDto);
//        when(modelMapper.map(jobType, JobtypeDto.class)).thenReturn(jobtypeDto);
//        when(modelMapper.map(employmentType, EmploymentTypeDto.class)).thenReturn(employmentTypeDto);
//        when(modelMapper.map(project, ProjectBasicsDto.class)).thenReturn(projectBasicsDto);
//        when(modelMapper.map(activity, ActivityDto.class)).thenReturn(activityDto);
//        when(modelMapper.map(gender, GenderDto.class)).thenReturn(genderDto);
//
//         List<MasterDto> result=Arrays.asList(masterService.getMasterDto());
//
//        assertEquals(masterDto.getBand(), result.get(0).getBand());
//        assertEquals(masterDto.getSkill(), result.get(0).getSkill());
//        assertEquals(masterDto.getWorkLocation(), result.get(0).getWorkLocation());
//        assertEquals(masterDto.getJobtype(), result.get(0).getJobtype());
//        assertEquals(masterDto.getEmploymentType(), result.get(0).getEmploymentType());
//        assertEquals(masterDto.getProjectBasics(), result.get(0).getProjectBasics());
//        assertEquals(masterDto.getActivity(), result.get(0).getActivity());
//        assertEquals(masterDto.getGender(), result.get(0).getGender());
//
//
//
//    }
//
//
//}
//
//
