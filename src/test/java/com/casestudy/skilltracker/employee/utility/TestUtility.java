package com.casestudy.skilltracker.employee.utility;

import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.dto.ErrorResponse;
import com.casestudy.skilltracker.employee.helper.model.AssociateProfileResponseHelper;
import com.casestudy.skilltracker.employee.model.SkillSet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class TestUtility {

    public static AssociateProfileResponse getExpectedResponse(ConditionType type) {
        AssociateProfileResponseHelper associateProfile;
        ObjectMapper mapper = new ObjectMapper();
        try {
            associateProfile = mapper.readValue(
                    new ClassPathResource(type.getResponseFileName()).getFile(),
                    AssociateProfileResponseHelper.class);
        } catch (IOException e) {
            e.printStackTrace();
            associateProfile =new AssociateProfileResponseHelper();
        }
        return mapToDto(associateProfile);
    }

    public static AssociateProfileRequest getSampleRequest(ConditionType type) {
        AssociateProfileRequest request;
        ObjectMapper mapper = new ObjectMapper();
        try {
            request = mapper.readValue(
                    new ClassPathResource(type.getRequestFileName()).getFile(),
                    AssociateProfileRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
            request = new AssociateProfileRequest();
        }
        return request;
    }
    public static SkillSet getSampleRequestForUpdate(ConditionType type) {
        SkillSet request;
        ObjectMapper mapper = new ObjectMapper();
        try {
            request = mapper.readValue(
                    new ClassPathResource(type.getRequestFileName()).getFile(),
                    SkillSet.class);
        } catch (IOException e) {
            e.printStackTrace();
            request = new SkillSet();
        }
        return request;
    }

    public static ErrorResponse getErrorResponse(ConditionType type) {
        ErrorResponse errorResponse;
        ObjectMapper mapper = new ObjectMapper();
        try {
            errorResponse = mapper.readValue(
                    new ClassPathResource(type.getResponseFileName()).getFile(),
                    ErrorResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            errorResponse =new ErrorResponse(400,"Bad request");
        }
        return errorResponse;
    }

    public static String getStringFormat(Object request) {
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = "";
        if (request != null) {
            try {
                if(request instanceof AssociateProfileRequest || request instanceof ErrorResponse
                        || request instanceof SkillSet || request instanceof AssociateProfileResponse)
                inputJson = mapper.writeValueAsString(request);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }
    public static AssociateProfileResponse mapToDto(AssociateProfileResponseHelper associateProfileResponseHelper) {
        AssociateProfileResponse associateProfileResponse=
                AssociateProfileResponse.builder()
                        .userId(associateProfileResponseHelper.getUserId())
                        .associateId(associateProfileResponseHelper.getAssociateId())
                        .email(associateProfileResponseHelper.getEmail())
                        .name(associateProfileResponseHelper.getName())
                        .mobile(associateProfileResponseHelper.getMobile())
                        .skills(associateProfileResponseHelper.getSkills())
                        .build();
        return associateProfileResponse;
    }


}
