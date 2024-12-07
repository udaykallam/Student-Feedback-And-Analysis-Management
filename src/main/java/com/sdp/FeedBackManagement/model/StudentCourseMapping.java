package com.sdp.FeedBackManagement.model;

import jakarta.persistence.*;

@Entity
public class StudentCourseMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course selectedCourse;
}