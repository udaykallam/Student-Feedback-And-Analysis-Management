package com.sdp.FeedBackManagement.controller;

import com.sdp.FeedBackManagement.dto.FeedbackFormRequest;
import com.sdp.FeedBackManagement.model.FeedbackForm;
import com.sdp.FeedBackManagement.service.FeedbackFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackFormController {

    @Autowired
    private FeedbackFormService feedbackFormService;

    @PostMapping("/create")
    public FeedbackForm createFeedbackForm(@RequestBody FeedbackFormRequest request) {
        return feedbackFormService.createFeedbackForm(request);
    }

    @GetMapping("/{id}")
    public FeedbackForm getFeedbackForm(@PathVariable Long id) {
        return feedbackFormService.getFeedbackFormById(id);
    }
}
