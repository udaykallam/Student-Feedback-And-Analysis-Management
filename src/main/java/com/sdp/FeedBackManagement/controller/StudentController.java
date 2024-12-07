package com.sdp.FeedBackManagement.controller;

import com.sdp.FeedBackManagement.dto.CourseDTO;
import com.sdp.FeedBackManagement.dto.FacultyDTO;
import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.model.Faculty;
import com.sdp.FeedBackManagement.service.CourseService;
import com.sdp.FeedBackManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @GetMapping("/courses/{year}")
    public ResponseEntity<List<Course>> getCoursesByYear(@PathVariable int year) {
        List<Course> courses = courseService.getCoursesByYear(year);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/register/{courseId}/{studentId}/{facultyId}")
    public ResponseEntity<String> registerForCourse(@PathVariable Long courseId,
                                                    @PathVariable Long studentId,
                                                    @PathVariable Long facultyId) {
        try {
            System.out.println("Registering student " + studentId + " for course " + courseId + " with faculty " + facultyId);
            String message = courseService.registerStudentForCourse(courseId, studentId, facultyId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering for course");
        }
    }


    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseDTO>> getRegisteredCourses(@PathVariable Long studentId) {
        List<Course> courses = courseService.getCoursesByStudentId(studentId);

        List<CourseDTO> courseDTOs = courses.stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setCourseName(course.getCourseName());
            courseDTO.setCourseCode(course.getCourseCode());
            courseDTO.setYearOfOffering(course.getYearOfOffering());

            // Setting the selected faculty instead of a list
            if (course.getSelectedFaculty() != null) {
                courseDTO.setSelectedFaculty(new FacultyDTO(course.getSelectedFaculty().getId(), course.getSelectedFaculty().getName()));
            }

            return courseDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(courseDTOs);
    }


    @GetMapping("/courses/{courseId}/instructors")
    public ResponseEntity<List<Faculty>> getInstructorsForCourse(@PathVariable Long courseId) {
        List<Faculty> instructors = courseService.getInstructorsForCourse(courseId);
        return ResponseEntity.ok(instructors);
    }

}
