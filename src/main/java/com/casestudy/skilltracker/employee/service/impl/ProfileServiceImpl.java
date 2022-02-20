package com.casestudy.skilltracker.employee.service.impl;

import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.exception.UserNotFoundException;
import com.casestudy.skilltracker.employee.exception.ValidationException;
import com.casestudy.skilltracker.employee.mapper.DTOMapper;
import com.casestudy.skilltracker.employee.model.AssociateProfile;
import com.casestudy.skilltracker.employee.model.Skill;
import com.casestudy.skilltracker.employee.model.SkillSet;
import com.casestudy.skilltracker.employee.repository.AssociateProfileRepository;
import com.casestudy.skilltracker.employee.service.ProfileService;
import com.casestudy.skilltracker.employee.util.DateUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    AssociateProfileRepository associateProfileRepository;
    @Autowired
    DTOMapper<AssociateProfileRequest, AssociateProfile> requestMapper;
    @Autowired
    DTOMapper<AssociateProfileResponse, AssociateProfile> responseMapper;

    @Override
    public AssociateProfileResponse save(AssociateProfileRequest associateProfileRequest) {
        if (associateProfileRepository.existsById(associateProfileRequest.getAssociateId())) {
            log.error("User Already Exists:{}",associateProfileRequest.getAssociateId());
            throw new ValidationException("User Already Exists:" + associateProfileRequest.getAssociateId());
        }
        AssociateProfile associateProfile=requestMapper.mapToEntity(associateProfileRequest);
        associateProfile = associateProfileRepository.save(associateProfile);
        return responseMapper.mapToDto(associateProfile);
    }

    @Override
    public AssociateProfileResponse update(String userid, SkillSet skillSet) {
        Optional<AssociateProfile> oAssociateProfile = associateProfileRepository.findAssociateByUserId(userid);
        if (!oAssociateProfile.isPresent()) {
            log.error("User Id Not Found:{}",userid);
            throw new UserNotFoundException("User Id Not Found:" + userid);
        }
        AssociateProfile associateProfile = oAssociateProfile.get();
        if (!associateProfile.getLastModifiedDate().before(DateUtility.getDateBeforeDays(10))) {
            log.error("Update of profile must be allowed only after 10 days of adding profile or last change for user id:{}",userid);
            throw new ValidationException("Update of profile must be allowed only after 10 days of adding profile or last change:"
                    + userid);
        }
        List<Skill> existingSkills = associateProfile.getSkills();
        existingSkills = addSkills(existingSkills, skillSet);
        associateProfile.setSkills(existingSkills);
        associateProfile = associateProfileRepository.save(associateProfile);
        return responseMapper.mapToDto(associateProfile);
    }

    private List<Skill> addSkills(List<Skill> existingSkills, SkillSet newSkills) {
        AssociateProfileRequest request = new AssociateProfileRequest();
        request.setSkills(newSkills);
        List<Skill> listNewSkills = requestMapper.mapToEntity(request).getSkills();
        if (existingSkills != null) {
            if (newSkills != null) {
                existingSkills.removeAll(listNewSkills);
                existingSkills.addAll(listNewSkills);
            }
        } else
            existingSkills = listNewSkills;
        return existingSkills;
    }
}
