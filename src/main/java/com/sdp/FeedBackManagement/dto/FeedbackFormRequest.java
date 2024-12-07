package com.sdp.FeedBackManagement.dto;

import com.sdp.FeedBackManagement.model.FeedbackQuestion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FeedbackFormRequest {
    private String title;
    private List<Question> questions; // Change from List<String> to List<Question>

    public static class Question {
        private String text;
        private List<String> options;

        // Getters and setters
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }
    }
}
