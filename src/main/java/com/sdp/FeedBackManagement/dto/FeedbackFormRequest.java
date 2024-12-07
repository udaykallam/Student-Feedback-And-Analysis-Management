package com.sdp.FeedBackManagement.dto;

import com.sdp.FeedBackManagement.model.FeedbackQuestion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FeedbackFormRequest {
    private Long courseId;
//    private Long facultyId;
    private String title;
    private LocalDateTime expirationDate;
    private List<FeedbackQuestion> questions;
}
