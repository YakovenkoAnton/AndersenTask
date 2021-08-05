package av.task2.dao;

import av.task2.BaseIT;
import av.task2.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component

class JPAEmployeeDAOTest extends BaseIT {
    @Autowired
    EmployeeDAO employeeDAO;


    @Test
    @Sql(scripts = "/truncate.sql")
    void create() {
        Employee employee = new Employee();
        String testName = "TestName";
        employee.setName(testName);
        String testSurname = "TestSurName";
        employee.setSurname(testSurname);
        String testMidName = "TestMidName";
        employee.setMidname(testMidName);
        LocalDate employmentDate = LocalDate.now();
        employee.setEmploymentDate(employmentDate);
        LocalDate birthday = LocalDate.now().plusDays(1);
        employee.setBirthdate(birthday);
        DevLevel devLevel = DevLevel.J1;
        employee.setDeveloperLevel(devLevel);
        String email = "Test@mail";
        employee.setEmail(email);
        String testEngLevel = "TestEngLevel";
        employee.setEnglishLevel(testEngLevel);
        String testSkype = "TestSkype";
        employee.setSkype(testSkype);
        String phoneNumber = "phoneNumber";
        employee.setPhone(phoneNumber);
        byte experience = 5;
        employee.setExperience(experience);

        Feedback feedback = new Feedback();
        String createEmployeeFeedbackDescription = "CreateEmployeeFeedbackDescription";
        feedback.setDescription(createEmployeeFeedbackDescription);
        employee.setFeedback(feedback);

        Project project = new Project();
        String createEmployeeProjectNameTest = "CreateEmployeeProjectTest";
        project.setName(createEmployeeProjectNameTest);
        employee.setProject(project);


        employeeDAO.create(employee);


        Employee actualEmployee = jdbcTemplate.queryForObject("SELECT * FROM employee", new BeanPropertyRowMapper<>(Employee.class));
        Feedback actualFeedback = jdbcTemplate.queryForObject("SELECT * FROM feedback", new BeanPropertyRowMapper<>(Feedback.class));
        Project actualProject = jdbcTemplate.queryForObject("SELECT * FROM project", new BeanPropertyRowMapper<>(Project.class));

        assertEquals(testName, actualEmployee.getName());
        assertEquals(testSurname, actualEmployee.getSurname());
        assertEquals(testMidName, actualEmployee.getMidname());
        assertEquals(employmentDate,actualEmployee.getEmploymentDate());
        assertEquals(birthday, actualEmployee.getBirthdate());
        assertEquals(devLevel, actualEmployee.getDeveloperLevel());
        assertEquals(email, actualEmployee.getEmail());
        assertEquals(testEngLevel, actualEmployee.getEnglishLevel());
        assertEquals(testSkype, actualEmployee.getSkype());
        assertEquals(createEmployeeFeedbackDescription, actualFeedback.getDescription());
        assertEquals(createEmployeeProjectNameTest, actualProject.getName());
        assertEquals(experience, actualEmployee.getExperience());
        assertEquals(phoneNumber, actualEmployee.getPhone());
    }

    @Test
    @Sql(scripts = {"/truncate.sql", "/data.sql"})
    void readById() {
        Employee employee = employeeDAO.readById(1L);

        assertEquals("testName", employee.getName());
        assertEquals("surname", employee.getSurname());
        assertEquals("midname", employee.getMidname());
        assertEquals(LocalDate.parse("2021-08-02"),employee.getEmploymentDate());
        assertEquals(LocalDate.parse("2021-08-01"), employee.getBirthdate());
        assertEquals(DevLevel.J1, employee.getDeveloperLevel());
        assertEquals("email", employee.getEmail());
        assertEquals("englishlevel", employee.getEnglishLevel());
        assertEquals("skype", employee.getSkype());
        assertEquals("test_Descriprion_1", employee.getFeedback().getDescription());
        assertEquals("firstProject", employee.getProject().getName());
        assertEquals(5, employee.getExperience());
        assertEquals("phone", employee.getPhone());
    }

    @Test
    void update() {
        Employee employee = new Employee();
        employee.setId(1L);
        String testName = "TestName";
        employee.setName(testName);
        String testSurname = "TestSurName";
        employee.setSurname(testSurname);
        String testMidName = "TestMidName";
        employee.setMidname(testMidName);
        LocalDate employmentDate = LocalDate.now();
        employee.setEmploymentDate(employmentDate);
        LocalDate birthday = LocalDate.now().plusDays(1);
        employee.setBirthdate(birthday);
        DevLevel devLevel = DevLevel.J1;
        employee.setDeveloperLevel(devLevel);
        String email = "Test@mail";
        employee.setEmail(email);
        String testEngLevel = "TestEngLevel";
        employee.setEnglishLevel(testEngLevel);
        String testSkype = "TestSkype";
        employee.setSkype(testSkype);
        String phoneNumber = "phoneNumber";
        employee.setPhone(phoneNumber);
        byte experience = 5;
        employee.setExperience(experience);

        Feedback feedback = new Feedback();
        String createEmployeeFeedbackDescription = "CreateEmployeeFeedbackDescription";
        feedback.setDescription(createEmployeeFeedbackDescription);
        employee.setFeedback(feedback);

        Project project = new Project();
        String createEmployeeProjectNameTest = "CreateEmployeeProjectTest";
        project.setName(createEmployeeProjectNameTest);
        employee.setProject(project);


        employeeDAO.update(employee);


        Employee actualEmployee = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE id = 1", new BeanPropertyRowMapper<>(Employee.class));
        Long feedbackId = jdbcTemplate.queryForObject("SELECT feedback_id FROM employee WHERE id = 1", Long.class);
        Long projectId = jdbcTemplate.queryForObject("SELECT project_id FROM employee WHERE id = 1", Long.class);


        Feedback actualFeedback = jdbcTemplate.queryForObject("SELECT * FROM feedback WHERE id=" +  feedbackId, new BeanPropertyRowMapper<>(Feedback.class));
        Project actualProject = jdbcTemplate.queryForObject("SELECT * FROM project WHERE id=" + projectId , new BeanPropertyRowMapper<>(Project.class));

        assertEquals(testName, actualEmployee.getName());
        assertEquals(testSurname, actualEmployee.getSurname());
        assertEquals(testMidName, actualEmployee.getMidname());
        assertEquals(employmentDate,actualEmployee.getEmploymentDate());
        assertEquals(birthday, actualEmployee.getBirthdate());
        assertEquals(devLevel, actualEmployee.getDeveloperLevel());
        assertEquals(email, actualEmployee.getEmail());
        assertEquals(testEngLevel, actualEmployee.getEnglishLevel());
        assertEquals(testSkype, actualEmployee.getSkype());
        assertEquals(createEmployeeFeedbackDescription, actualFeedback.getDescription());
        assertEquals(createEmployeeProjectNameTest, actualProject.getName());
        assertEquals(experience, actualEmployee.getExperience());
        assertEquals(phoneNumber, actualEmployee.getPhone());

    }

    @Test
    @Sql(scripts = {"/truncate.sql", "/dataEmployeeWithoutRef.sql"})
    void delete() {
        employeeDAO.delete(new Employee().setId(1L));

        int count =  jdbcTemplate.queryForObject("SELECT COUNT (*) FROM employee WHERE id = 1", Integer.class);
        assertEquals(0, count);





    }
}