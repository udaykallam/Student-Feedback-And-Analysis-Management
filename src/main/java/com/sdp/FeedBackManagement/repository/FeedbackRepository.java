package com.sdp.FeedBackManagement.repository;

import com.sdp.FeedBackManagement.model.FeedbackForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackForm, Long> {
}
