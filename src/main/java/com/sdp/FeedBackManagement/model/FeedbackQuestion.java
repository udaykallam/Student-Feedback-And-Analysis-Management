package com.sdp.FeedBackManagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class FeedbackQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feedback_form_id")
    private FeedbackForm feedbackForm;

    @Column(nullable = false)
    private String questionText;

    @OneToMany(mappedBy = "question")
    private List<FeedbackOption> options;

    // ... other fields as needed
}