package com.sdp.FeedBackManagement.dto;

import com.sdp.FeedBackManagement.model.Faculty;
import com.sdp.FeedBackManagement.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyWithStudentsDTO {
    private String facultyName;
    private List<String> studentNames;

    public FacultyWithStudentsDTO(Faculty faculty) {
        this.facultyName = faculty.getName();
        this.studentNames = faculty.getCoursesTaught().stream()
                .flatMap(course -> course.getRegisteredStudents().stream())
                .map(Student::getName)
                .distinct()
                .toList();
    }
}
