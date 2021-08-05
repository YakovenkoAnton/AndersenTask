package av.task2.dao;

import av.task2.BaseIT;
import av.task2.entity.Employee;
import av.task2.entity.Project;
import av.task2.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
public class JPAProjectDAOTest extends BaseIT {

    @Autowired
    ProjectDAO projectDAO;


    @Test
    @Sql(scripts = "/truncate.sql")
    void create() {
        Employee projectManager = new Employee();
        String email = "EmailForCreateTest";
        projectManager.setEmail(email);

        Project project = new Project();
        String projectName = "projectName";
        project.setName(projectName);
        String method = "Method";
        project.setMethodology(method);
        String customer = "Customer";
        project.setCustomer(customer);
        project.setTeam(new Team());
        project.setProjectManager(projectManager);
        int duration = 12;
        project.setDuration(duration);


        projectDAO.create(project);


        Project actualProject = jdbcTemplate.queryForObject("SELECT * FROM project", new BeanPropertyRowMapper<>(Project.class));
        Long projectManagerId = jdbcTemplate.queryForObject("SELECT project_manager_id FROM project", Long.class);
        Long teamId = jdbcTemplate.queryForObject("SELECT team_id FROM project", Long.class);
        Employee actualProjectManager = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE id=" + projectManagerId, new BeanPropertyRowMapper<>(Employee.class));

        assertEquals(projectName, actualProject.getName());
        assertEquals(method, actualProject.getMethodology());
        assertEquals(customer, actualProject.getCustomer());
        assertEquals(duration, actualProject.getDuration());
        assertNotNull(teamId);
        assertEquals(email, actualProjectManager.getEmail());
    }

    @Test
    @Sql (scripts= {"/truncate.sql", "/data.sql"})
    void readById() {

        Project project = projectDAO.readById(1L);

        assertEquals("firstProject", project.getName());
        assertEquals("method", project.getMethodology());
        assertEquals("customer", project.getCustomer());
        assertEquals(5, project.getDuration());
        assertEquals(1L, project.getTeam().getId());
        assertEquals(1L, project.getProjectManager().getId());
        assertEquals(1L, project.getId());
    }

    @Sql (scripts= {"/truncate.sql", "/data.sql"})
    @Test
    void update() {
        Employee projectManager = new Employee();
        String email = "EmailForCreateTest";
        projectManager.setEmail(email);

        Project project = new Project();
        project.setId(1L);
        String projectName = "projectName1";
        project.setName(projectName);
        String method = "Method1";
        project.setMethodology(method);
        String customer = "Customer1";
        project.setCustomer(customer);
        project.setTeam(new Team());
        project.setProjectManager(projectManager);
        int duration = 13;
        project.setDuration(duration);


        projectDAO.update(project);


        Project actualProject = jdbcTemplate.queryForObject("SELECT * FROM project WHERE id = 1", new BeanPropertyRowMapper<>(Project.class));
        Long projectManagerId = jdbcTemplate.queryForObject("SELECT project_manager_id FROM project WHERE id = 1", Long.class);
        Long teamId = jdbcTemplate.queryForObject("SELECT team_id FROM project WHERE id=1", Long.class);
        Employee actualProjectManager = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE id=" + projectManagerId, new BeanPropertyRowMapper<>(Employee.class));

        assertEquals(projectName, actualProject.getName());
        assertEquals(method, actualProject.getMethodology());
        assertEquals(customer, actualProject.getCustomer());
        assertEquals(duration, actualProject.getDuration());
        assertNotNull(teamId);
        assertEquals(email, actualProjectManager.getEmail());
        assertEquals(1L, actualProject.getId());
    }
    @Sql (scripts= {"/truncate.sql", "/dataProjectWithoutRef.sql"})
    @Test
    void delete() {
        projectDAO.delete(new Project().setId(1L));
        int count =  jdbcTemplate.queryForObject("SELECT COUNT (*) FROM project WHERE id = 1", Integer.class);
        assertEquals(0, count);
    }
}
