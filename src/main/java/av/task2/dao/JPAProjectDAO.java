package av.task2.dao;

import av.task2.entity.Project;
import av.task2.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class JPAProjectDAO implements ProjectDAO {
    private final SessionFactory sessionFactory;

    public JPAProjectDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Project project) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            currentSession.persist(project);
            transaction.commit();
        }
    }

    @Override
    public Project readById(Long id) {
        try (Session currentSession = sessionFactory.openSession()) {
            Project project = currentSession.get(Project.class, id);
            if (project == null) {
                throw new EntityNotFoundException();
            }
            return project;
        }
    }

    @Override
    public void update(Project project) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(project.getId());
            currentSession.merge(project);
            transaction.commit();
        }
    }

    @Override
    public void delete(Project project) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(project.getId());
            currentSession.delete(project);
            transaction.commit();
        }
    }
}
