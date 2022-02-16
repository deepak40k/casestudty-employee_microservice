package com.casestudy.skilltracker.employee.service;


import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.model.SkillSet;

public interface ProfileService {
    AssociateProfileResponse save(AssociateProfileRequest associateProfile);
    AssociateProfileResponse update(String userid, SkillSet skillSet);
}
