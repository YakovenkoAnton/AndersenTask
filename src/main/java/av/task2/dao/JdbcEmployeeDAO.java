package av.task2.dao;

import av.task2.DataSource;
import av.task2.entity.DevLevel;
import av.task2.entity.Employee;
import av.task2.entity.Feedback;
import av.task2.entity.Project;
import av.task2.exception.DatabaseCommunicationException;
import av.task2.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcEmployeeDAO implements EmployeeDAO {

    private FeedbackDAO feedbackDAO = new JdbcFeedbackDAO();
    private ProjectDAO projectDAO = new JdbcProjectDAO();

    @Override
    public void create(Employee employee) {
        log.debug("Starting create Employee");
        try {
            try (Connection conn = DataSource.getConnection()) {
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO employee" +
                        "(id, name, surname, midname, email, phone, birthdate, experience, employment_date, project_id, " +
                        "developer_level, english_level, skype, feedback_id)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setLong(1, employee.getId());
                preparedStatement.setString(2, employee.getName());
                preparedStatement.setString(3, employee.getSurname());
                preparedStatement.setString(4, employee.getMidname());
                preparedStatement.setString(5, employee.getEmail());
                preparedStatement.setString(6, employee.getPhone());
                preparedStatement.setDate(7, Date.valueOf(employee.getBirthdate()));
                preparedStatement.setInt(8, employee.getExperience());
                preparedStatement.setDate(9, Date.valueOf(employee.getEmploymentDate()));
                if (employee.getProject() != null) {
                    preparedStatement.setLong(10, employee.getProject().getId());
                } else {
                    preparedStatement.setNull(10, Types.INTEGER);
                }
                preparedStatement.setString(11, employee.getDeveloperLevel().name());
                preparedStatement.setString(12, employee.getEnglishLevel());
                preparedStatement.setString(13, employee.getSkype());
                if (employee.getFeedback() != null) {
                    preparedStatement.setLong(14, employee.getFeedback().getId());
                } else {
                    preparedStatement.setNull(14, Types.INTEGER);
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing create Employee");
    }

    @Override
    public Employee readById(Long id) {
        log.debug("Starting read Employee");
        try {
            try (Connection conn = DataSource.getConnection()) {

                var statement = conn.prepareStatement("SELECT * FROM employee WHERE id=?");
                statement.setLong(1, id);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return mapToEmployee(resultSet);
                } else {
                    throw new EntityNotFoundException();
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
    }

    @Override
    public void update(Employee employee) {
        log.debug("Starting update Employee");
        try {
            try (Connection conn = DataSource.getConnection()) {
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE employee SET name=?, surname=?, " +
                        "midname=?, email=?, phone=?, birthdate=?, experience=?, employment_date=?, project_id=?," +
                        "developer_level=?, english_level=?, skype=?, feedback_id=? WHERE id=?");

                preparedStatement.setLong(14, employee.getId());
                preparedStatement.setString(1, employee.getName());
                preparedStatement.setString(2, employee.getSurname());
                preparedStatement.setString(3, employee.getMidname());
                preparedStatement.setString(4, employee.getEmail());
                preparedStatement.setString(5, employee.getPhone());
                preparedStatement.setDate(6, Date.valueOf(employee.getBirthdate()));
                preparedStatement.setInt(7, employee.getExperience());
                preparedStatement.setDate(8, Date.valueOf(employee.getEmploymentDate()));
                if (employee.getProject() != null) {
                    preparedStatement.setLong(9, employee.getProject().getId());
                } else {
                    preparedStatement.setNull(9, Types.INTEGER);
                }
                preparedStatement.setString(10, employee.getDeveloperLevel().name());
                preparedStatement.setString(11, employee.getEnglishLevel());
                preparedStatement.setString(12, employee.getSkype());
                if (employee.getFeedback() != null) {
                    preparedStatement.setLong(13, employee.getFeedback().getId());
                } else {
                    preparedStatement.setNull(13, Types.INTEGER);
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing update Employee");
    }

    @Override
    public void delete(Employee employee) {
        log.debug("Starting delete Employee");
        try {
            try (Connection conn = DataSource.getConnection()) {
                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM employee WHERE id=?");
                preparedStatement.setLong(1, employee.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing delete Employee");
    }



    public List<Employee> readByTeamId(Long teamId){
        log.debug("Starting readByTeam Employee");
        try {
            try (Connection conn = DataSource.getConnection()) {

                var statement = conn.prepareStatement("SELECT * FROM employee WHERE team_id=?");
                statement.setLong(1, teamId);

                ResultSet resultSet = statement.executeQuery();
                List<Employee> employees = new ArrayList<>();
                while (resultSet.next()){
                    Employee employee = mapToEmployee(resultSet);
                    employees.add(employee);
                }
                return employees;
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
    }

    private Employee mapToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setName(resultSet.getString("name"));
        employee.setSurname(resultSet.getString("surname"));
        employee.setMidname(resultSet.getString("midname"));
        employee.setEmail(resultSet.getString("email"));
        employee.setPhone(resultSet.getString("phone"));
        employee.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
        employee.setExperience(resultSet.getByte("experience"));

        long projectId = resultSet.getLong("project_id");
        if (projectId != 0) {
            employee.setProject(projectDAO.readById(projectId));
        }
        String developerLevel = resultSet.getString("developer_level");
        if (developerLevel!=null){
            employee.setDeveloperLevel(DevLevel.valueOf(developerLevel));
        }
        employee.setEnglishLevel(resultSet.getString("english_level"));
        employee.setSkype(resultSet.getString("skype"));

        long feedbackId = resultSet.getLong("feedback_id");
        if (feedbackId != 0) {
            employee.setFeedback(feedbackDAO.readById(feedbackId));
        }

        log.debug("Finishing read Feedback");
        return employee;
    }


}
