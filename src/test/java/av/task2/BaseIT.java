package av.task2;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class BaseIT {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
