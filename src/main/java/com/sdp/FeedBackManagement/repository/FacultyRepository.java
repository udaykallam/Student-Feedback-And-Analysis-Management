package com.sdp.FeedBackManagement.repository;


import com.sdp.FeedBackManagement.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    boolean existsByMail(String mail);
    boolean existsByFacultyId(String facultyId);

    @Query("SELECT f FROM Faculty f JOIN f.user u WHERE u.username IN :usernames")
    List<Faculty> findByUserUsernameIn(@Param("usernames") List<String> usernames);

    List<Faculty> findByFacultyIdIn(List<String> facultyIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM Faculty f WHERE f.id = :id")
    void deleteFacultyById(@Param("id") Long id);
}
