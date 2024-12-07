package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.dto.FeedbackFormRequest;
import com.sdp.FeedBackManagement.model.FeedbackForm;
import com.sdp.FeedBackManagement.repository.FeedbackFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackFormService {
    @Autowired
    private FeedbackFormRepository feedbackFormRepository;

    public FeedbackForm createFeedbackForm(FeedbackForm feedbackForm) {
        return feedbackFormRepository.save(feedbackForm);
    }

    public List<FeedbackForm> getAllFeedbackForms() {
        return feedbackFormRepository.findAll();
    }
}