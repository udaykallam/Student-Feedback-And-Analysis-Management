package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.model.Course;
import com.sdp.FeedBackManagement.model.Faculty;
import com.sdp.FeedBackManagement.model.Student;
import com.sdp.FeedBackManagement.repository.FacultyRepository;
import com.sdp.FeedBackManagement.repository.StudentRepository;

import com.sdp.FeedBackManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get all faculty
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    // Update student
    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student existingStudent = student.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setMail(updatedStudent.getMail());
            existingStudent.setMobileNumber(updatedStudent.getMobileNumber());
            existingStudent.setCurrentYear(updatedStudent.getCurrentYear());
            existingStudent.setDepartment(updatedStudent.getDepartment());
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    // Update faculty
    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            Faculty existingFaculty = faculty.get();
            existingFaculty.setName(updatedFaculty.getName());
            existingFaculty.setMail(updatedFaculty.getMail());
            existingFaculty.setDepartment(updatedFaculty.getDepartment());
            existingFaculty.setExperience(updatedFaculty.getExperience());
            existingFaculty.setQualification(updatedFaculty.getQualification());
            return facultyRepository.save(existingFaculty);
        }
        return null;
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student with ID " + id + " not found.");
        }
        studentRepository.deleteStudentById(id);
        userRepository.deleteById(id);
    }

    // Delete faculty with proper handling
    @Transactional
    public void deleteFaculty(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new IllegalArgumentException("Faculty with ID " + id + " not found.");
        }
        facultyRepository.deleteFacultyById(id);
        userRepository.deleteById(id);
    }
}
