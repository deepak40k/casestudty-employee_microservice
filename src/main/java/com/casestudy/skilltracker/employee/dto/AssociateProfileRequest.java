package com.casestudy.skilltracker.employee.dto;

import com.casestudy.skilltracker.employee.model.SkillSet;
import com.casestudy.skilltracker.employee.validator.AssociateID;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Valid
public class AssociateProfileRequest {
    static final long serialVersionUID = 1L;
    private String userId;
    @NotNull(message = "Associate Id can not be null")
    @AssociateID
    @Size(min = 5, max = 30,message = "Associate Id size must be between 5 and 30")
    private String associateId;
    @NotNull(message = "Name can not be null")
    @Size(min = 5, max = 30, message = "name size must be between 5 and 30")
    private String name;
    @NotNull(message = "Email Id can not be null")
    @Email
    private String email;
    @NotNull(message = "Mobile number can not be null")
    @Size(min = 10, max = 10, message = "mobile number must be of 10 digit")
    private String mobile;
    @Valid
    private SkillSet skills;
}
