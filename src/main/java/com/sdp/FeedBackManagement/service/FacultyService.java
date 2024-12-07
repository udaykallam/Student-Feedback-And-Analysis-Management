package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.dto.StudentDTO;
import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.model.Faculty;
import com.sdp.FeedBackManagement.model.Student;
import com.sdp.FeedBackManagement.model.User;
import com.sdp.FeedBackManagement.repository.FacultyRepository;
import com.sdp.FeedBackManagement.repository.StudentRepository;
import com.sdp.FeedBackManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public void addFaculty(Faculty faculty) {
        if (facultyRepository.existsByMail(faculty.getMail())) {
            throw new IllegalArgumentException("Email already exists!");
        }

        if (facultyRepository.existsByFacultyId(faculty.getFacultyId())) {
            throw new IllegalArgumentException("Faculty ID already exists!");
        }

        User user = new User();
        user.setUsername(faculty.getFacultyId());
        user.setPassword(encoder.encode("klu@123456"));
        user.setMail(faculty.getMail());
        user.setRole("Faculty");

        User savedUser = userRepository.save(user);
        if (savedUser.getId() == null) {
            throw new RuntimeException("User ID is null, ensure User is saved correctly.");
        }

        faculty.setUser(savedUser);
        facultyRepository.save(faculty);
    }

    public void addFacultiesFromCsv(MultipartFile file) throws Exception {
        List<Faculty> facultyList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("name", "facultyId", "mail", "department", "experience", "qualification").withSkipHeaderRecord(true))) {

            for (CSVRecord record : csvParser) {
                Faculty faculty = new Faculty();
                faculty.setName(record.get("name"));
                faculty.setFacultyId(record.get("facultyId"));
                faculty.setMail(record.get("mail"));
                faculty.setDepartment(record.get("department"));
                faculty.setExperience(String.valueOf(Integer.parseInt(record.get("experience"))));
                faculty.setQualification(record.get("qualification"));

                if (facultyRepository.existsByMail(faculty.getMail()) || facultyRepository.existsByFacultyId(faculty.getFacultyId())) {
                    throw new IllegalArgumentException("Duplicate email or faculty ID found in CSV: " + faculty.getMail() + " or " + faculty.getFacultyId());
                }

                User user = new User();
                user.setUsername(faculty.getFacultyId());
                user.setMail(faculty.getMail());
                user.setPassword(encoder.encode("klu@123456"));
                user.setRole("Faculty");
                userRepository.save(user);

                faculty.setUser(user);
                facultyList.add(faculty);
            }
            facultyRepository.saveAll(facultyList);
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file: " + e.getMessage(), e);
        }
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public List<Student> getStudentsForFaculty(Long facultyId) {
        return studentRepository.findByRegisteredCourses_SelectedFaculty_Id(facultyId);
    }

    public List<Course> getCoursesForFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found with ID: " + facultyId));
        return faculty.getCoursesTaught();
    }


    public List<StudentDTO> getStudentsForFacultyAndCourse(Long facultyId, Long courseId) {
        List<Student> students = studentRepository.findByRegisteredCourses_IdAndRegisteredCourses_SelectedFaculty_Id(courseId, facultyId);
        return students.stream()
                .filter(student -> student != null)
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }




}
