package com.sdp.FeedBackManagement.service;

import com.sdp.FeedBackManagement.model.FeedbackForm;
import com.sdp.FeedBackManagement.model.FeedbackResponse;
import com.sdp.FeedBackManagement.model.Question;
import com.sdp.FeedBackManagement.repository.FeedbackFormRepository;
import com.sdp.FeedBackManagement.repository.FeedbackRepository;
import com.sdp.FeedBackManagement.repository.FeedbackResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackFormRepository feedbackFormRepository;

    @Autowired
    private FeedbackResponseRepository feedbackResponseRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public FeedbackForm createFeedbackForm(FeedbackForm feedbackForm) {
        // Validate form name
        if (feedbackForm.getFormName() == null || feedbackForm.getFormName().isEmpty()) {
            throw new IllegalArgumentException("Form name cannot be empty");
        }

        // Validate questions
        if (feedbackForm.getQuestions() == null || feedbackForm.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("At least one question is required");
        }

        for (Question question : feedbackForm.getQuestions()) {
            if (question.getQuestionText() == null || question.getQuestionText().isEmpty()) {
                throw new IllegalArgumentException("Question text cannot be empty");
            }
            if (question.getOptions() != null && question.getOptions().isEmpty()) {
                throw new IllegalArgumentException("Options cannot be empty if provided");
            }
        }

        // Save the feedback form (this is just an example; you'd likely persist to a database)
        feedbackFormRepository.save(feedbackForm);

        // Return the saved feedback form (or any other object as needed)
        return feedbackForm;
    }

    public FeedbackResponse saveResponse(FeedbackResponse response) {
        return feedbackResponseRepository.save(response);
    }

    public List<FeedbackResponse> getResponsesByFormId(Long formId) {
        return feedbackResponseRepository.findAll().stream()
                .filter(response -> response.getFeedbackFormId().equals(formId))
                .collect(Collectors.toList());
    }


    public List<FeedbackForm> getAllFeedbackForms() {
        return feedbackRepository.findAll();
    }

    public FeedbackForm getFeedbackFormById(Long formId) {
        return feedbackRepository.findById(formId).orElse(null);
    }

    public void saveFeedbackResponse(FeedbackResponse feedbackResponse) {
        if (feedbackResponse.getFeedbackFormId() == null) {
            throw new IllegalArgumentException("Feedback form ID cannot be null.");
        }

        feedbackResponseRepository.save(feedbackResponse);
    }
}
