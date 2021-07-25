package av.task2.dao;

import av.task2.entity.Employee;

public interface EmployeeDAO {
    void create(Employee employee);

    Employee readById(Long id);

    void update(Employee employee);

    void delete(Employee employee);
}

