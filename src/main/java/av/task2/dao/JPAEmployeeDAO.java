package av.task2.dao;

import av.task2.entity.Employee;
import av.task2.entity.Project;
import av.task2.entity.Team;
import av.task2.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JPAEmployeeDAO implements EmployeeDAO {
    private final SessionFactory sessionFactory;

    public JPAEmployeeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Employee employee) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            currentSession.persist(employee);
            transaction.commit();
        }
    }

    @Override
    public Employee readById(Long id) {
        try (Session currentSession = sessionFactory.openSession()) {
            Employee employee = currentSession.get(Employee.class, id);
            if (employee == null) {
                throw new EntityNotFoundException();
            }
            return employee;
        }
    }

    @Override
    public void update(Employee employee) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(employee.getId());
            currentSession.merge(employee);
            transaction.commit();
        }
    }

    @Override
    public void delete(Employee employee) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();
            readById(employee.getId());
            currentSession.delete(employee);
            transaction.commit();
        }
    }
}
