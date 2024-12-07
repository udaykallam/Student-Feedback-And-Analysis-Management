package com.sdp.FeedBackManagement.controller;

import com.sdp.FeedBackManagement.dto.FeedbackFormRequest;
import com.sdp.FeedBackManagement.model.FeedbackForm;
import com.sdp.FeedBackManagement.service.FeedbackFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbackForms")
public class FeedbackFormController {
    @Autowired
    private FeedbackFormService feedbackFormService;

    @PostMapping
    public ResponseEntity<FeedbackForm> createFeedbackForm(@RequestBody FeedbackForm feedbackForm) {
        FeedbackForm createdForm = feedbackFormService.createFeedbackForm(feedbackForm);
        return ResponseEntity.ok(createdForm);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackForm>> getAllFeedbackForms() {
        List<FeedbackForm> feedbackForms = feedbackFormService.getAllFeedbackForms();
        return ResponseEntity.ok(feedbackForms);
    }

    // ... other endpoints for retrieving, updating, and deleting feedback forms
}