package com.sdp.FeedBackManagement.repository;

import com.sdp.FeedBackManagement.model.FeedbackForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackFormRepository extends JpaRepository<FeedbackForm, Long> {
}
