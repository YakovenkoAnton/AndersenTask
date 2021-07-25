package av.task2.dao;

import av.task2.entity.Employee;
import av.task2.entity.Team;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTeamDAOTest {

    JdbcTeamDAO jdbcTeamDAO = new JdbcTeamDAO();

    @Test
    void create() {
        Employee employee = new Employee();
        employee.setId(1L);
        jdbcTeamDAO.create(new Team(5L, List.of(employee)));
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