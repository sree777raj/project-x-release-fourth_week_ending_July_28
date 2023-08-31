package com.prakat.projectx.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MasterDto {
    private List<BandDto> band;// Represents the band information.
    private List<WorkLocationDto> workLocation;
    private List<SkillDto> skill;
    private List<JobtypeDto> jobtype;
    private List<EmploymentTypeDto> employmentType;
    private List<GenderDto> gender;
    private List<ActivityDto> activity;
    private List<ProjectBasicsDto> projectBasics;
    private List<ProjectTypeDto> projectTypes;



}
