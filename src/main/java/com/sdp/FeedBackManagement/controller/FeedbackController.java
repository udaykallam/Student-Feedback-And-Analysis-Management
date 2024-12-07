package com.sdp.FeedBackManagement.controller;

import com.sdp.FeedBackManagement.model.FeedbackForm;
import com.sdp.FeedBackManagement.model.FeedbackResponse;
import com.sdp.FeedBackManagement.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<FeedbackForm> createFeedbackForm(@RequestBody FeedbackForm feedbackForm) {
        try {
            // Call the service to handle the creation logic
            FeedbackForm createdFeedbackForm = feedbackService.createFeedbackForm(feedbackForm);

            // Return the created feedback form as the response
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedbackForm);
        } catch (Exception e) {
            // Handle exceptions (e.g., validation errors, database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackForm>> getAllFeedbackForms() {
        List<FeedbackForm> feedbackForms = feedbackService.getAllFeedbackForms();
        return ResponseEntity.ok(feedbackForms);
    }


    @PostMapping("/submit")
    public ResponseEntity<String> submitFeedback(@RequestBody FeedbackResponse feedbackResponse) {
        if (feedbackResponse.getFeedbackFormId() == null) {
            return ResponseEntity.badRequest().body("Feedback form ID is required.");
        }
        feedbackService.saveFeedbackResponse(feedbackResponse);
        return ResponseEntity.ok("Feedback submitted successfully.");
    }


    @GetMapping("/responses/{formId}")
    public List<FeedbackResponse> getResponses(@PathVariable Long formId) {
        return feedbackService.getResponsesByFormId(formId);
    }

    @GetMapping("/forms")
    public ResponseEntity<List<FeedbackForm>> getAllForms() {
        List<FeedbackForm> forms = feedbackService.getAllFeedbackForms();
        return forms.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(forms);
    }

    @GetMapping("/forms/{formId}")
    public ResponseEntity<FeedbackForm> getFormById(@PathVariable Long formId) {
        FeedbackForm form = feedbackService.getFeedbackFormById(formId);
        return form != null ? ResponseEntity.ok(form) : ResponseEntity.notFound().build();
    }
}
