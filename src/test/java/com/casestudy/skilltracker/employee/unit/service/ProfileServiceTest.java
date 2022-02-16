package com.casestudy.skilltracker.employee.unit.service;

import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.exception.UserNotFoundException;
import com.casestudy.skilltracker.employee.exception.ValidationException;
import com.casestudy.skilltracker.employee.repository.AssociateProfileRepository;
import com.casestudy.skilltracker.employee.service.ProfileService;
import com.casestudy.skilltracker.employee.utility.ConditionType;
import com.casestudy.skilltracker.employee.utility.TestUtility;
import com.casestudy.skilltracker.employee.model.AssociateProfile;
import com.casestudy.skilltracker.employee.model.SkillSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceTest {

	@Autowired
	private ProfileService profileService;

	@Autowired
	AssociateProfileRepository associateProfileRepository;

	@BeforeEach
	void init()
	{
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		try {
			associateProfileRepository.deleteById(profileRequest.getAssociateId());
		}catch (Exception e)
		{
			System.out.println("cleanUp");
		}
	}

	@Test
	void testCreateProfile_Success() {
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		AssociateProfileResponse actualResponse=profileService.save(profileRequest);
		assertNotNull(actualResponse.getUserId());
	}

	@Test
	void testCreateProfile_AlreadyExists(){
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		AssociateProfileResponse actualResponse=profileService.save(profileRequest);  
		   ValidationException exception = assertThrows(
		  			ValidationException.class,
		  			() -> profileService.save(profileRequest));
		   assertEquals("User Already Exists:" + profileRequest.getAssociateId(),exception.getMessage());
	}  

	@Test
	void testUpdateSkills_Success() {


		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		AssociateProfileResponse actualResponse=profileService.save(profileRequest);
		AssociateProfile associateProfile=associateProfileRepository.findAssociateByUserId(actualResponse.getUserId()).get();
		associateProfile.setLastModifiedDate(new Date(10000));
		associateProfile=associateProfileRepository.save(associateProfile);
		actualResponse.getSkills().getTechnical().get(0).setLevel(11);
		SkillSet skillSet =actualResponse.getSkills();
		AssociateProfileResponse updateResponse = profileService.update(actualResponse.getUserId(),skillSet);
		assertNotNull(updateResponse.getUserId());
	}

	@Test
	void testUpdateSkills_UpdateWithin10Days() {
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		AssociateProfileResponse actualResponse=profileService.save(profileRequest);
		actualResponse.getSkills().getTechnical().get(0).setLevel(11);
		SkillSet skillSet =actualResponse.getSkills();
		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> profileService.update(actualResponse.getUserId(),skillSet));
		assertEquals("Update of profile must be allowed only after 10 days of adding profile or last change:" + actualResponse.getUserId(),exception.getMessage());
	}


	@Test
	void testCreateProfile_UserNotFound(){
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		UserNotFoundException exception = assertThrows(
				UserNotFoundException.class,
				() -> profileService.update(profileRequest.getAssociateId(),profileRequest.getSkills()));
		assertEquals("User Id Not Found:" + profileRequest.getAssociateId(),exception.getMessage());
	}

}
