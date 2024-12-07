package com.sdp.FeedBackManagement.controller;

import com.sdp.FeedBackManagement.dto.CourseDTO;
import com.sdp.FeedBackManagement.dto.CourseFacultyStudentDTO;
import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/courses")
@CrossOrigin(origins = "http://localhost:5173")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();  // Get all courses from the service layer
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))  // Map each course entity to CourseDTO
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id);  // Make sure this method returns a Course
    }

    @PostMapping("/{courseId}/assign-faculty/{facultyId}")
    public String assignFaculty(@PathVariable Long courseId, @PathVariable Long facultyId) {
        courseService.assignFacultyToCourse(courseId, facultyId);
        return "Faculty assigned successfully";
    }

    @PostMapping("/{courseId}/register-student/{studentId}")
    public String registerStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.registerStudentToCourse(courseId, studentId);
        return "Student registered successfully";
    }

    @GetMapping("/{courseId}/details")
    public CourseFacultyStudentDTO getCourseDetails(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return new CourseFacultyStudentDTO(course);
    }
}
