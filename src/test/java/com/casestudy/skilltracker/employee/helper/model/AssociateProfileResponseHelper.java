package com.casestudy.skilltracker.employee.helper.model;

import com.casestudy.skilltracker.employee.model.SkillSet;
import lombok.Data;

@Data
public class AssociateProfileResponseHelper {
    static final long serialVersionUID = 1L;
    private String userId;
    private String associateId;
    private String name;
    private String email;
    private String mobile;
    private SkillSet skills;
}
