package com.sdp.FeedBackManagement.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateCourseRequest {
    private String courseCode;
    private String courseName;
    private int yearOfOffering;
    private List<String> facultyIds;

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setYearOfOffering(int yearOfOffering) {
        this.yearOfOffering = yearOfOffering;
    }

    public void setFacultyIds(List<String> facultyIds) {
        this.facultyIds = facultyIds;
    }
}
