package com.casestudy.skilltracker.employee.mapper.impl;

import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.mapper.DTOMapper;
import com.casestudy.skilltracker.employee.model.SkillSet;
import com.casestudy.skilltracker.employee.model.AssociateProfile;
import com.casestudy.skilltracker.employee.model.Skill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AssociateProfileResponseMapper implements DTOMapper<AssociateProfileResponse, AssociateProfile> {

    @Override
    public AssociateProfile mapToEntity(AssociateProfileResponse associateProfileResponse) {
        throw new RuntimeException("Mapping not allowed");
    }

    @Override
    public AssociateProfileResponse mapToDto(AssociateProfile associateProfile) {
        List<Skill> skills = associateProfile.getSkills();
        List<Skill> technicalSkills = skills.stream().
                filter(x -> x.getType().equals("Technical")).collect(Collectors.toList());
        List<Skill> nonTechnicalSkills = skills.stream().
                filter(x -> x.getType().equals("NonTechnical")).collect(Collectors.toList());
        SkillSet skillSet = new SkillSet(technicalSkills, nonTechnicalSkills);
        AssociateProfileResponse associateProfileResponse=
                AssociateProfileResponse.builder()
                .userId(associateProfile.getUserId())
                .associateId(associateProfile.getAssociateId())
                .email(associateProfile.getEmail())
                .name(associateProfile.getName())
                .mobile(associateProfile.getMobile())
                .skills(skillSet)
                .build();
        log.debug("Mapped AssociateProfileDTO:"+associateProfileResponse);
        return associateProfileResponse;
    }
}

