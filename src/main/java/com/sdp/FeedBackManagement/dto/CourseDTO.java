package com.sdp.FeedBackManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String courseName;
    private String courseCode;
    private int yearOfOffering;
    private FacultyDTO selectedFaculty; // This field is singular
}
