package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.model.Faculty;
import com.sdp.FeedBackManagement.model.Student;
import com.sdp.FeedBackManagement.repository.CourseRepository;
import com.sdp.FeedBackManagement.repository.FacultyRepository;
import com.sdp.FeedBackManagement.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Course createCourse(String courseCode, String courseName, int yearOfOffering, List<String> facultyIds) {
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);
        course.setYearOfOffering(yearOfOffering);

        List<Faculty> faculties = facultyRepository.findByFacultyIdIn(facultyIds);

        if (faculties.isEmpty()) {
            throw new IllegalArgumentException("No faculties found with the given facultyIds");
        }

        course.setFaculties(faculties);

        Course savedCourse = courseRepository.save(course);

        for (Faculty faculty : faculties) {
            faculty.getCoursesTaught().add(savedCourse);
            facultyRepository.save(faculty);
        }

        return savedCourse;
    }

    public List<Course> getCoursesByYear(int yearOfOffering) {
        return courseRepository.findByYearOfOffering(yearOfOffering);
    }

    public String registerStudentForCourse(Long courseId, Long studentId, Long facultyId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        course.getRegisteredStudents().add(student);
        course.setSelectedFaculty(faculty);
        courseRepository.save(course);

        return "Successfully registered for course!";
    }

    public List<Faculty> getInstructorsForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getFaculties();
    }

    public List<Course> getCoursesByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
        return student.getRegisteredCourses();
    }

    public void assignFacultyToCourse(Long courseId, Long facultyId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        course.getFaculties().add(faculty);
        courseRepository.save(course);
    }

    public void registerStudentToCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        course.getRegisteredStudents().add(student);
        courseRepository.save(course);
    }

    public List<Student> getStudentsByCourseAndFaculty(Long courseId, Long facultyId) {
        return studentRepository.findAll();
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
