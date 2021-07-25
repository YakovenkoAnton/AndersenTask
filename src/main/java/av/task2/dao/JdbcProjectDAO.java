package av.task2.dao;

import av.task2.DataSource;
import av.task2.entity.Employee;
import av.task2.entity.Feedback;
import av.task2.entity.Project;
import av.task2.entity.Team;
import av.task2.exception.DatabaseCommunicationException;
import av.task2.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class JdbcProjectDAO implements ProjectDAO {
    private JdbcTeamDAO teamDAO = new JdbcTeamDAO();
    private JdbcEmployeeDAO employeeDAO = new JdbcEmployeeDAO();

    @Override
    public void create(Project project) {
        log.debug("Starting create Project");
        try {
            try (Connection conn = DataSource.getConnection()) {


                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO project(id, name, customer, " +
                        "duration, methodology, project_manager_id, team_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setLong(1, project.getId());
                preparedStatement.setString(2, project.getName());
                preparedStatement.setString(3, project.getCustomer());
                preparedStatement.setInt(4, project.getDuration());
                preparedStatement.setString(5, project.getMethodology());
                if (project.getProjectManager() != null) {
                    preparedStatement.setLong(6, project.getProjectManager().getId());
                } else {
                    preparedStatement.setNull(6, Types.INTEGER);
                }

                if (project.getTeam() != null) {
                    preparedStatement.setLong(7, project.getTeam().getId());
                } else {
                    preparedStatement.setNull(7, Types.INTEGER);
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing create Feedback");
    }

    @Override
    public Project readById(Long id) {
        log.debug("Starting read Project");
        try {
            try (Connection conn = DataSource.getConnection()) {
                var statement = conn.prepareStatement("SELECT * FROM project WHERE id=?");
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Project project = new Project();
                    project.setId(resultSet.getLong("id"));
                    project.setName(resultSet.getString("name"));
                    project.setCustomer(resultSet.getString("customer"));
                    project.setDuration(resultSet.getInt("duration"));
                    project.setMethodology(resultSet.getString("methodology"));

                    long projectManagerId = resultSet.getLong("project_manager_id");
                    if (projectManagerId != 0) {
                        project.setProjectManager(employeeDAO.readById(projectManagerId));
                    }

                    long projectTeamId = resultSet.getLong("team_id");
                    if (projectTeamId != 0) {
                        project.setTeam(teamDAO.readById(projectTeamId));
                    }


                    log.debug("Finishing read Project");
                    return project;
                } else {
                    throw new EntityNotFoundException();
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
    }

    @Override
    public void update(Project project) {
        log.debug("Starting create Project");
        try {
            try (Connection conn = DataSource.getConnection()) {


                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE project SET name=?, customer=?, " +
                        "duration=?, methodology=?, project_manager_id=?, team_id=? WHERE id=?");
                preparedStatement.setLong(7, project.getId());
                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getCustomer());
                preparedStatement.setInt(3, project.getDuration());
                preparedStatement.setString(4, project.getMethodology());
                if (project.getProjectManager() != null) {
                    preparedStatement.setLong(5, project.getProjectManager().getId());
                } else {
                    preparedStatement.setNull(5, Types.INTEGER);
                }

                if (project.getTeam() != null) {
                    preparedStatement.setLong(6, project.getTeam().getId());
                } else {
                    preparedStatement.setNull(6, Types.INTEGER);
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing create Feedback");
    }
    @Override
    public void delete(Project project) {

        log.debug("Starting delete Project");
        try {
            try (Connection conn = DataSource.getConnection()) {
                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM project WHERE id=?");
                preparedStatement.setLong(1, project.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing delete Project");

    }
}
