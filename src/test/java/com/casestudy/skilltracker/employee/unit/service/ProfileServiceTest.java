package com.casestudy.skilltracker.employee.unit.service;

import com.casestudy.skilltracker.employee.dto.AssociateProfileRequest;
import com.casestudy.skilltracker.employee.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.employee.exception.UserNotFoundException;
import com.casestudy.skilltracker.employee.exception.ValidationException;
import com.casestudy.skilltracker.employee.mapper.DTOMapper;
import com.casestudy.skilltracker.employee.model.AssociateProfile;
import com.casestudy.skilltracker.employee.model.SkillSet;
import com.casestudy.skilltracker.employee.repository.AssociateProfileRepository;
import com.casestudy.skilltracker.employee.service.ProfileService;
import com.casestudy.skilltracker.employee.utility.ConditionType;
import com.casestudy.skilltracker.employee.utility.TestUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceTest {

	@Autowired
	private ProfileService profileService;

	@MockBean
	AssociateProfileRepository associateProfileRepository;

	@Autowired
	DTOMapper<AssociateProfileRequest, AssociateProfile> requestMapper;

	@Autowired
	DTOMapper<AssociateProfileResponse, AssociateProfile> responseMapper;

	private AssociateProfile associateProfile;

	@BeforeEach
	void init()
	{
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		associateProfile=requestMapper.mapToEntity(profileRequest);
		associateProfile.setUserId("f8eeceba-b26a-4db2-ba56-c6d95bae35f6");
		Mockito.when(this.associateProfileRepository.save(Mockito.any(AssociateProfile.class))).thenReturn(associateProfile);
	}

	@Test
	void testCreateProfile_Success() {
		Mockito.when(this.associateProfileRepository.existsById(Mockito.any(String.class))).thenReturn(false);
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		AssociateProfileResponse actualResponse=profileService.save(profileRequest);
		assertNotNull(actualResponse.getUserId());
	}

	@Test
	void testCreateProfile_AlreadyExists(){
		Mockito.when(this.associateProfileRepository.existsById(Mockito.any(String.class))).thenReturn(true);
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		   ValidationException exception = assertThrows(
		  			ValidationException.class,
		  			() -> profileService.save(profileRequest));
		   assertEquals("User Already Exists:" + profileRequest.getAssociateId(),exception.getMessage());
	}  

	@Test
	void testUpdateSkills_Success() {
		associateProfile.setLastModifiedDate(new Date(10000));
		Mockito.when(this.associateProfileRepository.findAssociateByUserId(Mockito.any(String.class))).thenReturn(Optional.of(associateProfile));
		AssociateProfileResponse actualResponse=responseMapper.mapToDto(associateProfile);
		actualResponse.getSkills().getTechnical().get(0).setLevel(11);
		SkillSet skillSet =actualResponse.getSkills();
		AssociateProfileResponse updateResponse = profileService.update(associateProfile.getUserId(),skillSet);
		assertNotNull(updateResponse.getUserId());
	}

	@Test
	void testUpdateSkills_UpdateWithin10Days() {
		associateProfile.setLastModifiedDate(new Date());
		Mockito.when(this.associateProfileRepository.findAssociateByUserId(Mockito.any(String.class))).thenReturn(Optional.of(associateProfile));
		AssociateProfileResponse actualResponse=responseMapper.mapToDto(associateProfile);
		actualResponse.getSkills().getTechnical().get(0).setLevel(11);
		SkillSet skillSet =actualResponse.getSkills();
		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> profileService.update(actualResponse.getUserId(),skillSet));
		assertEquals("Update of profile must be allowed only after 10 days of adding profile or last change:" + actualResponse.getUserId(),exception.getMessage());
	}

	@Test
	void testCreateProfile_UserNotFound(){
		Mockito.when(this.associateProfileRepository.findAssociateByUserId(Mockito.any(String.class))).thenReturn(Optional.empty());
		AssociateProfileRequest profileRequest = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		UserNotFoundException exception = assertThrows(
				UserNotFoundException.class,
				() -> profileService.update(profileRequest.getAssociateId(),profileRequest.getSkills()));
		assertEquals("User Id Not Found:" + profileRequest.getAssociateId(),exception.getMessage());
	}
}
