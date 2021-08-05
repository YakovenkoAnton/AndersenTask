package av.task2.dao;

import av.task2.entity.Feedback;
import av.task2.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class JPAFeedbackDAO implements FeedbackDAO {
    private final SessionFactory sessionFactory;

    public JPAFeedbackDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void create(Feedback feedback) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            currentSession.persist(feedback);
            transaction.commit();
        }
    }

    @Override
    public Feedback readById(Long id) {
        try (Session currentSession = sessionFactory.openSession()) {
            Feedback feedback = currentSession.get(Feedback.class, id);
            if (feedback == null) {
                throw new EntityNotFoundException();
            }
            return feedback;
        }
    }


    @Override
    public void update(Feedback feedback) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(feedback.getId());
            currentSession.merge(feedback);
            transaction.commit();
        }
    }

    @Override
    public void delete(Feedback feedback) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(feedback.getId());
            currentSession.delete(feedback);
            transaction.commit();
        }
    }
}
