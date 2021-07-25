package av.task2.dao;

import av.task2.entity.Feedback;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JdbcFeedbackDAOTest {
    FeedbackDAO feedbackDAO = new JdbcFeedbackDAO();


    @Test
    void create() {
        feedbackDAO.create(new Feedback(1L, "testDescription", LocalDate.now()));
    }

    @Test
    void readById() {
        Feedback feedback = feedbackDAO.readById(1L);
        assertEquals(1L, feedback.getId());
        assertEquals("testDescription", feedback.getDescription());
    }

    @Test
    void update() {
        feedbackDAO.update(new Feedback(1L, "newTestDescription", LocalDate.now()));

    }

    @Test
    void delete() {
        feedbackDAO.delete(new Feedback(1L, null, null));
    }
}