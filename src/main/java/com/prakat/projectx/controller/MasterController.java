package com.prakat.projectx.controller;

import com.prakat.projectx.dto.MasterDto;
import com.prakat.projectx.exception.CustomException;
import com.prakat.projectx.service.MasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class MasterController {
    private static final Logger logger= LoggerFactory.getLogger(MasterController.class);

    @Autowired
    private MasterService masterService;
    @GetMapping("/masters")
    public ResponseEntity<MasterDto> getMaster() {
        try {
            MasterDto allMasters = masterService.getMasterDto();
            return ResponseEntity.ok(allMasters);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
