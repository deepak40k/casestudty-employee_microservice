package com.casestudy.skilltracker.employee.dto;

import com.casestudy.skilltracker.employee.model.SkillSet;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;

@Getter
@Valid
@Builder
public class AssociateProfileResponse {
    static final long serialVersionUID = 1L;
    private String userId;
    private String associateId;
    private String name;
    private String email;
    private String mobile;
    private SkillSet skills;
}
