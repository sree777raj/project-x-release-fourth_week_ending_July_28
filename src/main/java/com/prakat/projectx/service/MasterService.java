package com.prakat.projectx.service;

import com.prakat.projectx.dto.*;

import java.util.List;

public interface MasterService {
    public MasterDto getMasterDto();
    public List<BandDto> getAllBand();
    public List<SkillDto> getAllSkill();
    public List<WorkLocationDto> getAllWorkLocation();
    public List<JobtypeDto> getAllJobType();
    public List<EmploymentTypeDto> getAllEmploymentType();
    public List<ProjectBasicsDto> getAllProjectBasics();
    public List<ActivityDto> getAllActivity();
    public List<GenderDto> getAllGender();
    public List<ProjectTypeDto> getAllProjectTypes();





}
