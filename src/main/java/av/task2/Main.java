package av.task2;

import av.task2.dao.FeedbackDAO;
import av.task2.dao.JdbcFeedbackDAO;
import av.task2.entity.Feedback;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        FeedbackDAO feedbackDAO = new JdbcFeedbackDAO();

        var feedback = new Feedback(10L, "asd", LocalDate.now());
        feedbackDAO.create(feedback);
    }
}
