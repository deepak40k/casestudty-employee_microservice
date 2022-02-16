package com.casestudy.skilltracker.employee.unit.controller;

import com.casestudy.skilltracker.employee.controller.ProfileController;
import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.dto.ErrorResponse;
import com.casestudy.skilltracker.employee.exception.ValidationException;
import com.casestudy.skilltracker.employee.service.impl.ProfileServiceImpl;
import com.casestudy.skilltracker.employee.utility.ConditionType;
import com.casestudy.skilltracker.employee.utility.TestUtility;
import com.casestudy.skilltracker.employee.model.SkillSet;
import com.casestudy.skilltracker.employee.mq.sender.impl.RabbitMqSender;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {ProfileController.class})
class ProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfileServiceImpl service;

    @MockBean
    private RabbitMqSender rabbitMqSender;

    @Test
    void testCreateProfile_Success() throws Exception {
        AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
        String inputJson = TestUtility.getStringFormat(profileRequest);
        AssociateProfileResponse expectedResponse = TestUtility.getExpectedResponse(ConditionType.CREATE_PROFILE);
        Mockito.when(this.service.save(Mockito.any(AssociateProfileRequest.class))).thenReturn(expectedResponse);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/api/v1/engineer/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(ConditionType.CREATE_PROFILE.getHttpStatus(), status);
        assertEquals(TestUtility.getStringFormat(expectedResponse), actualResponse);
        verify(rabbitMqSender,times(1)).send(expectedResponse);
    }

    @Test
    void testCreateProfile_ValidationFailure() throws Exception {
        AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
        String inputJson = TestUtility.getStringFormat(profileRequest);
        Mockito.when(this.service.save(Mockito.any(AssociateProfileRequest.class))).thenThrow(new ValidationException("Validation Failure"));
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/api/v1/engineer/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void testCreateProfile_ValidateAssociateIDFailure() throws Exception {
        AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.INVALID_ASSOCIATEID);
        String inputJson = TestUtility.getStringFormat(profileRequest);
        ErrorResponse expectedResponse = TestUtility.getErrorResponse(ConditionType.INVALID_ASSOCIATEID);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/api/v1/engineer/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(ConditionType.INVALID_ASSOCIATEID.getHttpStatus(), status);
        assertEquals(TestUtility.getStringFormat(expectedResponse), actualResponse);
    }

    @Test
    void testCreateProfile_InternalServerError() throws Exception {
        AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
        String inputJson = TestUtility.getStringFormat(profileRequest);
        Mockito.when(this.service.save(Mockito.any(AssociateProfileRequest.class))).thenThrow(new RuntimeException("Internal Server Error"));
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.post("/api/v1/engineer/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    @Test
    void testUpdateSkills_Success() throws Exception {
        SkillSet skillSet = TestUtility.getSampleRequestForUpdate(ConditionType.UPDATE_SKILL);
        String inputJson = TestUtility.getStringFormat(skillSet);
        AssociateProfileResponse expectedResponse = TestUtility.getExpectedResponse(ConditionType.UPDATE_SKILL);
        Mockito.when(this.service.update(Mockito.any(String.class),Mockito.any(SkillSet.class))).thenReturn(expectedResponse);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put("/api/v1/engineer/profile/f8eeceba-b26a-4db2-ba56-c6d95bae35f6/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(ConditionType.UPDATE_SKILL.getHttpStatus(), status);
        assertEquals(TestUtility.getStringFormat(expectedResponse), actualResponse);
        verify(rabbitMqSender,times(1)).send(expectedResponse);

    }


    @Test
    void testUpdateSkills_ValidateFailure() throws Exception {
        SkillSet skillSet = TestUtility.getSampleRequestForUpdate(ConditionType.UPDATE_SKILL_WITH_INVALID_SKILL);
        String inputJson = TestUtility.getStringFormat(skillSet);
        ErrorResponse expectedResponse = TestUtility.getErrorResponse(ConditionType.UPDATE_SKILL_WITH_INVALID_SKILL);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.put("/api/v1/engineer/profile/f8eeceba-b26a-4db2-ba56-c6d95bae35f6/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(ConditionType.UPDATE_SKILL_WITH_INVALID_SKILL.getHttpStatus(), status);
        assertEquals(TestUtility.getStringFormat(expectedResponse), actualResponse);
    }

}
