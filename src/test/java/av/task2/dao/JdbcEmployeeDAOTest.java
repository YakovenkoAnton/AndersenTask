package av.task2.dao;

import av.task2.entity.DevLevel;
import av.task2.entity.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JdbcEmployeeDAOTest {
EmployeeDAO employeeDAO = new JdbcEmployeeDAO();

    @Test
    void create() {

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("TestName");
        employee.setSurname("TestSurName");
        employee.setMidname("TestMidName");
        employee.setEmploymentDate(LocalDate.now());
        employee.setBirthdate(LocalDate.now().plusDays(1));
        employee.setDeveloperLevel(DevLevel.J1);
        employee.setEmail("Test@mail");
        employee.setEnglishLevel("TestEngLevel");
        employee.setSkype("TestSkype");

        employeeDAO.create(employee);
    }

    @Test
    void readById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}