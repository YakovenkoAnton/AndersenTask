package av.task2.dao;

import av.task2.DataSource;
import av.task2.entity.Employee;
import av.task2.entity.Team;
import av.task2.exception.DatabaseCommunicationException;
import av.task2.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class JdbcTeamDAO implements TeamDAO {
    private JdbcEmployeeDAO jdbcEmployeeDAO = new JdbcEmployeeDAO();

    @Override
    public void create(Team team) {
        log.debug("Starting create Team");
        try {
            try (Connection conn = DataSource.getConnection()) {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO team(id) VALUES (?)");
                preparedStatement.setLong(1, team.getId());
                preparedStatement.executeUpdate();
                saveEmployeesToTeam(team, conn);
                conn.commit();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing create Team");
    }


    @Override
    public Team readById(Long id) {
        log.debug("Starting read Team");
        try {
            try (Connection conn = DataSource.getConnection()) {
                var statement = conn.prepareStatement("SELECT * FROM team WHERE id=?");
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Team team = new Team();
                    team.setId(resultSet.getLong("id"));
                    team.setEmployees(jdbcEmployeeDAO.readByTeamId(team.getId()));
                    log.debug("Finishing read Team");
                    return team;
                } else {
                    throw new EntityNotFoundException();
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
    }

    @Override
    public void update(Team team) {

        log.debug("Starting update Team");

        try {

            try (Connection conn = DataSource.getConnection()) {
                removeEmployeesFromTeam(team, conn);
                saveEmployeesToTeam(team, conn);
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing create Team");
    }

    private void removeEmployeesFromTeam(Team team, Connection conn) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE employee SET team_id=NULL WHERE team_id=?");
        preparedStatement.setLong(1, team.getId());
        preparedStatement.executeUpdate();
    }

    private void saveEmployeesToTeam(Team team, Connection conn) throws SQLException {
        List<Long> employeeIds = team.getEmployees().stream().map(Employee::getId).filter(Objects::nonNull).collect(Collectors.toList());
        if (!employeeIds.isEmpty()) {
            String sqlIN = employeeIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")"));

            String sql = "UPDATE employee SET team_id=? WHERE id IN(?)";
            sql = sql.replace("(?)", sqlIN);
            PreparedStatement preparedStatementEmp = conn.prepareStatement(sql);
            preparedStatementEmp.setLong(1, team.getId());
            preparedStatementEmp.executeUpdate();
        }
    }

    @Override
    public void delete(Team team) {
        log.debug("Starting delete Team");
        try {
            try (Connection conn = DataSource.getConnection()) {
                removeEmployeesFromTeam(team, conn);
                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM team WHERE id=?");
                preparedStatement.setLong(1, team.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing delete Team");
    }
}
