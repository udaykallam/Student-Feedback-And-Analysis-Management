package com.sdp.FeedBackManagement.controller;

import com.sdp.FeedBackManagement.dto.CreateCourseRequest;
import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.model.Faculty;
import com.sdp.FeedBackManagement.model.Student;
import com.sdp.FeedBackManagement.service.AdminService;
import com.sdp.FeedBackManagement.service.CourseService;
import com.sdp.FeedBackManagement.service.FacultyService;
import com.sdp.FeedBackManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            studentService.addStudent(student);
            return ResponseEntity.ok("Student added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
        }
    }

    @PostMapping("/upload-students")
    public ResponseEntity<String> addStudentsFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            studentService.addStudentsFromCsv(file);
            return ResponseEntity.ok("Students added successfully from CSV!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload students from CSV.");
        }
    }



    @PostMapping("/add-faculty")
    public ResponseEntity<String> addFaculty(@RequestBody Faculty faculty) {
        try {
            facultyService.addFaculty(faculty);
            return ResponseEntity.ok("Faculty added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
        }
    }

    @PostMapping("/upload-faculty")
    public ResponseEntity<String> addFacultiesFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            facultyService.addFacultiesFromCsv(file);
            return ResponseEntity.ok("Faculties added successfully from CSV!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload faculty data from CSV.");
        }
    }


    @PostMapping("/create-course")
    public ResponseEntity<Course> createCourse(@RequestBody CreateCourseRequest request) {
        Course course = courseService.createCourse(
                request.getCourseCode(),
                request.getCourseName(),
                request.getYearOfOffering(),
                request.getFacultyIds()
        );
        return ResponseEntity.ok(course);
    }

    @GetMapping("/faculty/list")
    public ResponseEntity<List<Faculty>> getFacultyList() {
        try {
            List<Faculty> facultyList = facultyService.getAllFaculties();
            return ResponseEntity.ok(facultyList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // Get list of students
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = adminService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Get list of faculty
    @GetMapping("/faculty")
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        List<Faculty> faculties = adminService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    // Update student
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = adminService.updateStudent(id, student);
        if (updatedStudent != null) {
            return ResponseEntity.ok(updatedStudent);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Update faculty
    @PutMapping("/faculty/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty updatedFaculty = adminService.updateFaculty(id, faculty);
        if (updatedFaculty != null) {
            return ResponseEntity.ok(updatedFaculty);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            adminService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
        }
    }


    // Delete faculty
    @DeleteMapping("/faculty/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        adminService.deleteFaculty(id);
        return ResponseEntity.ok("Faculty deleted successfully!");
    }

}
