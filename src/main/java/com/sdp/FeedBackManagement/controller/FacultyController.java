package com.sdp.FeedBackManagement.controller;
import com.sdp.FeedBackManagement.dto.StudentDTO;
import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.model.Student;
import com.sdp.FeedBackManagement.repository.CourseRepository;
import com.sdp.FeedBackManagement.service.FacultyService;
import com.sdp.FeedBackManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/{facultyId}/students")
    public List<Student> getStudents(@PathVariable Long facultyId) {
        return facultyService.getStudentsForFaculty(facultyId);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{facultyId}/courses")
    public ResponseEntity<List<Course>> getCoursesForFaculty(@PathVariable Long facultyId) {
        List<Course> courses = facultyService.getCoursesForFaculty(facultyId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{facultyId}/courses/{courseId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsForCourse(
            @PathVariable Long facultyId, @PathVariable Long courseId) {
        List<StudentDTO> students = facultyService.getStudentsForFacultyAndCourse(facultyId, courseId);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}
