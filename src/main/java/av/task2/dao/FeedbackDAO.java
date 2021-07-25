package av.task2.dao;

import av.task2.entity.Feedback;

public interface FeedbackDAO {
    void create(Feedback feedback);

    Feedback readById(Long id);

    void update(Feedback feedback);

    void delete(Feedback feedback);
}
