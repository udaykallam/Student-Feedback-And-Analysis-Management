package com.sdp.FeedBackManagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "feedback_response")
public class FeedbackResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "feedback_form_id", nullable = false)
    private FeedbackForm feedbackForm;

    @ElementCollection
    @CollectionTable(name = "response_answers", joinColumns = @JoinColumn(name = "response_id"))
    @MapKeyColumn(name = "question_id")
    @Column(name = "answer")
    private Map<Long, String> answers; // Question ID to Answer mapping
}
