package com.casestudy.skilltracker.employee.controller;

import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.model.SkillSet;
import com.casestudy.skilltracker.employee.service.ProfileService;
import com.casestudy.skilltracker.employee.mq.sender.impl.RabbitMqSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @PostMapping(value = "/engineer/profile", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AssociateProfileResponse> save(@Valid @RequestBody final AssociateProfileRequest associateProfile) {
        log.info("Received create profile request : {}",associateProfile.toString());
        AssociateProfileResponse response = profileService.save(associateProfile);
        rabbitMqSender.send(response);
        log.debug("Response : {}",response.toString());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping(value = "/engineer/profile/{userId}/skills", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AssociateProfileResponse> update(@PathVariable String userId, @Valid @NotNull @RequestBody final SkillSet skillSet) {
        log.info("Received update profile request for user id : {} is : {}",userId,skillSet.toString());
        AssociateProfileResponse response = profileService.update(userId, skillSet);
        rabbitMqSender.send(response);
        log.debug("Response : {}",response.toString());
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
