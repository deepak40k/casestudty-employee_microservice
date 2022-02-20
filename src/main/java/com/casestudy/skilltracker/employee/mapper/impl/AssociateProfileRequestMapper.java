package com.casestudy.skilltracker.employee.mapper.impl;

import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.mapper.DTOMapper;
import com.casestudy.skilltracker.employee.model.AssociateProfile;
import com.casestudy.skilltracker.employee.model.Skill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AssociateProfileRequestMapper implements DTOMapper<AssociateProfileRequest, AssociateProfile> {

    @Override
    public AssociateProfile mapToEntity(AssociateProfileRequest associateProfileRequest) {
        List<Skill> skills = new ArrayList<>();
        if (associateProfileRequest.getSkills() != null) {
            if (associateProfileRequest.getSkills().getTechnical() != null) {
                List<Skill> technicalSkills = associateProfileRequest.getSkills().getTechnical();
                technicalSkills.forEach(
                        x -> x.setType("Technical")
                );
                skills.addAll(technicalSkills);
            }
            if (associateProfileRequest.getSkills().getNonTechnical() != null) {
                List<Skill> nonTechnicalSkills = associateProfileRequest.getSkills().getNonTechnical();
                nonTechnicalSkills.forEach(
                        x -> x.setType("NonTechnical")
                );
                skills.addAll(nonTechnicalSkills);
            }
        }
        AssociateProfile associateProfile =new AssociateProfile();
        associateProfile.setUserId(associateProfileRequest.getUserId());
        associateProfile.setAssociateId(associateProfileRequest.getAssociateId());
        associateProfile.setEmail(associateProfileRequest.getEmail());
        associateProfile.setName(associateProfileRequest.getName());
        associateProfile.setMobile(associateProfileRequest.getMobile());
        associateProfile.setSkills(skills);
        log.debug("Mapped AssociateProfile:"+associateProfile);
        return associateProfile;
    }

    @Override
    public AssociateProfileRequest mapToDto(AssociateProfile associateProfile) {
        throw new RuntimeException("Mapping not allowed");
    }
}

