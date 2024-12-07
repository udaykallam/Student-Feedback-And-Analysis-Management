package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.model.Student;
import com.sdp.FeedBackManagement.model.User;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@Service
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);


    public void addStudent(Student student) {
        if (studentRepository.existsByMail(student.getMail())) {
            throw new IllegalArgumentException("Email already exists!");
        }

        if (studentRepository.existsByUniversityId(student.getUniversityId())) {
            throw new IllegalArgumentException("University ID already exists!");
        }

        User user = new User();
        user.setUsername(student.getUniversityId());
        user.setPassword(encoder.encode(student.getDateOfBirth()));
        user.setMail(student.getMail());
        user.setRole("Student");

        User savedUser = userRepository.save(user);
        if (savedUser.getId() == null) {
            throw new RuntimeException("User ID is null, ensure User is saved correctly.");
        }

        student.setUser(savedUser);
        studentRepository.save(student);
    }

    public void addStudentsFromCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Student student = new Student();
                student.setUniversityId(record.get("universityId"));
                student.setName(record.get("name"));
                student.setCurrentYear(Integer.parseInt(record.get("currentYear")));
                student.setDateOfBirth(record.get("dateOfBirth"));
                student.setDepartment(record.get("department"));
                student.setMail(record.get("mail"));
                student.setMobileNumber(record.get("mobileNumber"));

                addStudent(student);
            }
        }
    }


    public List<Course> getRegisteredCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        return student.getRegisteredCourses();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
