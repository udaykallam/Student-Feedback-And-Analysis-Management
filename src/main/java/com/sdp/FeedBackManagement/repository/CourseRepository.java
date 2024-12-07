package com.sdp.FeedBackManagement.repository;

import com.sdp.FeedBackManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByYearOfOffering(int yearOfOffering);
}
