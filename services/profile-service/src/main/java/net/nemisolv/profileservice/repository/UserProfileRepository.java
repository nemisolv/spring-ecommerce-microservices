package net.nemisolv.profileservice.repository;

import net.nemisolv.profileservice.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends MongoRepository<UserProfile, String > {

    Optional<UserProfile> findByUserId(String userId);


    @Query("{ '$text': { '$search': ?0 } }")
    Page<UserProfile> searchProfile(String searchQuery, Pageable pageable);
}