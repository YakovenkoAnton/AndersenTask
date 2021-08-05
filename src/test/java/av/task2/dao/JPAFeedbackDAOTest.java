package av.task2.dao;

import av.task2.BaseIT;
import av.task2.entity.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JPAFeedbackDAOTest extends BaseIT {

    @Autowired
    FeedbackDAO feedbackDAO;

    @Test
    @Sql(scripts = "/truncate.sql")
    void create() {
        String testDescription = "testDescription";
        LocalDate now = LocalDate.now();

        feedbackDAO.create(new Feedback(null, testDescription, now));

        Feedback feedback = jdbcTemplate.queryForObject("SELECT * FROM feedback", new BeanPropertyRowMapper<>(Feedback.class));
        assertEquals(testDescription, feedback.getDescription());
        assertEquals(now, feedback.getDate());
    }

    @Test

    @Sql({"/truncate.sql", "/data.sql"})
    void readById() {
        Feedback feedback = feedbackDAO.readById(1L);

        assertEquals(1L, feedback.getId());
        assertEquals("test_Descriprion_1", feedback.getDescription());
        assertEquals(LocalDate.parse("2021-08-01"), feedback.getDate());
    }
    @Sql({"/truncate.sql", "/data.sql"})
    @Test
    void update() {
        String newTestDescription = "newTestDescription";
        LocalDate now = LocalDate.now();

        feedbackDAO.update(new Feedback(1L, newTestDescription, now));

        Feedback feedback = jdbcTemplate.queryForObject("SELECT * FROM feedback WHERE id=1", new BeanPropertyRowMapper<>(Feedback.class));
        assertEquals(newTestDescription, feedback.getDescription());
        assertEquals(now, feedback.getDate());

    }
    @Sql({"/truncate.sql", "/dataFeedbackWithoutRef.sql"})
    @Test
    void delete() {
        feedbackDAO.delete(new Feedback(1L, null, null));
        int count =  jdbcTemplate.queryForObject("SELECT COUNT (*) FROM feedback WHERE id = 1", Integer.class);
        assertEquals(0, count);
    }
}