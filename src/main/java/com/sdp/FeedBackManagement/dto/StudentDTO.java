package com.sdp.FeedBackManagement.dto;

import com.sdp.FeedBackManagement.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String universityId;
    private String name;
    private String department;
    private String mail;
    private String mobileNumber;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.universityId = student.getUniversityId();
        this.name = student.getName();
        this.department = student.getDepartment();
        this.mail = student.getMail();
        this.mobileNumber = student.getMobileNumber();
    }
}
