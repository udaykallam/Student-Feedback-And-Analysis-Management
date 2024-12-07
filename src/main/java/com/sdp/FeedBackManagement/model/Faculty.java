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
@Table(name = "faculty")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JsonBackReference(value = "faculty-ref")
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String facultyId;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String experience;

    @Column(nullable = false)
    private String qualification;

    @ManyToMany(mappedBy = "faculties")
    @JsonBackReference(value = "assigned-faculty")
    private List<Course> coursesTaught;

    @PreRemove
    private void preRemove() {
        if (coursesTaught != null) {
            coursesTaught.forEach(course -> course.getFaculties().remove(this));
        }
    }
}
