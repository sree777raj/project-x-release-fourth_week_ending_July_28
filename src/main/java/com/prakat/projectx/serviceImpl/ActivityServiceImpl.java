package com.prakat.projectx.serviceImpl;

import com.prakat.projectx.dto.ActivityDto;
import com.prakat.projectx.service.ActivityService;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Override
    public ActivityDto getActivity(){
        ActivityDto activityDto=new ActivityDto();
        activityDto.setActivityId(1L);
        activityDto.setActivityType("Stand up call");
        return activityDto;
    }


}
