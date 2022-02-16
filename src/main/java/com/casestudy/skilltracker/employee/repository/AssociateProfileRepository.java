package com.casestudy.skilltracker.employee.repository;

import com.casestudy.skilltracker.employee.model.AssociateProfile;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface AssociateProfileRepository extends CrudRepository<AssociateProfile, String> {
    Optional<AssociateProfile> findAssociateByUserId(String userId);
}
