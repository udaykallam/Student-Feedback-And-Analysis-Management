package com.sdp.FeedBackManagement.repository;

import com.sdp.FeedBackManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByMail(String mail);
    boolean existsByUniversityId(String universityId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.id = :id")
    void deleteStudentById(@Param("id") Long id);

    List<Student> findByRegisteredCourses_SelectedFaculty_Id(Long facultyId);

    List<Student> findByRegisteredCourses_IdAndRegisteredCourses_SelectedFaculty_Id(Long courseId, Long facultyId);
}
