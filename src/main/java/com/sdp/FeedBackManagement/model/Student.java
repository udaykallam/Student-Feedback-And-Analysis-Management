package com.sdp.FeedBackManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "student")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JsonBackReference(value = "student-ref")
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "university_id", unique = true, nullable = false)
    private String universityId;

    @Column(name = "current_year", nullable = false)
    private int currentYear;

    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "mail", unique = true, nullable = false)
    private String mail;

    @Column(name = "mobile_number", unique = true, nullable = false)
    private String mobileNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "registeredStudents")
    private List<Course> registeredCourses;

    @PreRemove
    private void preRemove() {
        if (registeredCourses != null) {
            registeredCourses.forEach(course -> course.getRegisteredStudents().remove(this));
        }
    }
}
