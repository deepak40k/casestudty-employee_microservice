package com.casestudy.skilltracker.employee.model;

import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Valid
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SkillSet implements Serializable {
    @Valid
    private List<Skill> technical=new ArrayList<>();
    @Valid
    private List<Skill> nonTechnical=new ArrayList<>();
}
