package com.sdp.FeedBackManagement.dto;


import com.sdp.FeedBackManagement.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseFacultyStudentDTO {
    private String courseName;
    private List<FacultyWithStudentsDTO> faculties;

    public CourseFacultyStudentDTO(Course course) {
        this.courseName = course.getCourseName();
        this.faculties = course.getFaculties().stream()
                .map(FacultyWithStudentsDTO::new)
                .toList();
    }
}
