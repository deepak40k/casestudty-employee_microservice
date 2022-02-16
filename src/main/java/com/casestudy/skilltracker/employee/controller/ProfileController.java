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
        log.debug("Create request body: " + associateProfile);
        AssociateProfileResponse response = profileService.save(associateProfile);
        rabbitMqSender.send(response);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping(value = "/engineer/profile/{userId}/skills", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AssociateProfileResponse> update(@PathVariable String userId, @Valid @NotNull @RequestBody final SkillSet skillSet) {
        log.debug("Update request body: " + skillSet + " for userId: " + userId);
        AssociateProfileResponse response = profileService.update(userId, skillSet);
        rabbitMqSender.send(response);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
