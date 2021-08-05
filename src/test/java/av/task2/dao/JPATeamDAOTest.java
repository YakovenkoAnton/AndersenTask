package av.task2.dao;

import av.task2.BaseIT;
import av.task2.entity.Employee;
import av.task2.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JPATeamDAOTest extends BaseIT {

    @Autowired
    TeamDAO teamDAO;


    @Test
    @Sql(scripts = "/truncate.sql")
    void create() {
        Employee employee = new Employee();
        String email = "EmailForCreateTest";
        employee.setEmail(email);

        teamDAO.create(new Team(null, Set.of(employee)));

        Team team  = jdbcTemplate.queryForObject("SELECT * FROM team", new BeanPropertyRowMapper<>(Team.class));
        String sqlForEmployees = "SELECT * FROM employee WHERE team_id=" + team.getId();
        List<Employee> employees = jdbcTemplate.query(sqlForEmployees, new BeanPropertyRowMapper<>(Employee.class));
        assertNotNull(team.getId());
        assertEquals(1, employees.size());
        assertEquals(email, employees.get(0).getEmail());
    }

    @Test
    @Sql (scripts= {"/truncate.sql", "/data.sql"})
    void readById() {
    Team team = teamDAO.readById(1L);
        assertEquals(1L, team.getId() );
        assertEquals(2, team.getEmployees().size());
    }

    @Sql (scripts= {"/truncate.sql", "/data.sql"})
    @Test
    void update() {

        Employee employee = new Employee();
        String email = "E-mailForUpdate";
        employee.setEmail(email);
        Set<Employee> employees = Set.of(employee);

        teamDAO.update(new Team(1L, employees ));

        String sqlForEmployees = "SELECT * FROM employee WHERE team_id=1";
        List<Employee> actualEmployees = jdbcTemplate.query(sqlForEmployees, new BeanPropertyRowMapper<>(Employee.class));

        assertEquals(1, actualEmployees.size());
        assertEquals(email, actualEmployees.get(0).getEmail());
    }
    @Sql (scripts= {"/truncate.sql", "/dataTeamWithoutRef.sql"})
    @Test
    void delete() {
        teamDAO.delete(new Team(1L, null));

        int count =  jdbcTemplate.queryForObject("SELECT COUNT (*) FROM team WHERE id = 1", Integer.class);
        assertEquals(0, count);
    }
}