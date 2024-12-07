package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.dto.FeedbackFormRequest;
import com.sdp.FeedBackManagement.model.FeedbackForm;
import com.sdp.FeedBackManagement.repository.FeedbackFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackFormService {

    @Autowired
    private FeedbackFormRepository feedbackFormRepository;

    public FeedbackForm createFeedbackForm(FeedbackFormRequest request) {
        FeedbackForm feedbackForm = new FeedbackForm();
        feedbackForm.setCourseId(request.getCourseId());
//        feedbackForm.setFacultyId(request.getFacultyId());
        feedbackForm.setTitle(request.getTitle());
        feedbackForm.setExpirationDate(request.getExpirationDate());
        feedbackForm.setQuestions(request.getQuestions());
        return feedbackFormRepository.save(feedbackForm);
    }

    public FeedbackForm getFeedbackFormById(Long id) {
        return feedbackFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback form not found with id: " + id));
    }
}
