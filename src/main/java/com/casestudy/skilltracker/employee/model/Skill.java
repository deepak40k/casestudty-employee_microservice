package com.casestudy.skilltracker.employee.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.casestudy.skilltracker.employee.validator.ValidSkill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@DynamoDBDocument
public class Skill implements Serializable {
    static final long serialVersionUID = 1L;
    @NotNull(message = "Skill name can not be null")
    @ValidSkill
    @DynamoDBAttribute
    private String name;
    @NotNull
    @Min(value=0, message = "Skill level should be between 0 and 20")
    @Max(value=20,message = "Skill level should be between 0 and 20")
    @DynamoDBAttribute
    private Integer level;
    @JsonIgnore
    @DynamoDBAttribute
    private String type;
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if ((o instanceof Skill) && (((Skill) o).getName().equals(this.name))) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

