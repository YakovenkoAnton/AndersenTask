package av.task2.dao;

import av.task2.entity.Project;
import av.task2.entity.Team;
import av.task2.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

@Component
public class JPATeamDAO implements TeamDAO {
    private final SessionFactory sessionFactory;

    public JPATeamDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Team team) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            currentSession.persist(team);
            transaction.commit();
        }
    }

    @Override
    public Team readById(Long id) {
        try (Session currentSession = sessionFactory.openSession()) {
            Team team = currentSession.get(Team.class, id);
            if (team == null) {
                throw new EntityNotFoundException();
            }
            return team;
        }

    }

    @Override
    public void update(Team team) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(team.getId());
            currentSession.merge(team);
            transaction.commit();
        }
    }

    @Override
    public void delete(Team team) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(team.getId());
            currentSession.delete(team);
            transaction.commit();
        }
    }
}
