package com.prakat.projectx.controller;

import com.prakat.projectx.dto.ActivityDto;
import com.prakat.projectx.serviceImpl.ActivityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ActivityController {
    private static final Logger logger= LoggerFactory.getLogger(ActivityController.class);
    @Autowired
    private ActivityServiceImpl activityServiceImpl;
    @GetMapping("activity")
    public ResponseEntity<ActivityDto> getActivity(){
        return ResponseEntity.ok(activityServiceImpl.getActivity());
    }
    @PostMapping("activity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto){
        logger.info(activityDto.toString());
        return ResponseEntity.ok(activityDto);
    }
}
