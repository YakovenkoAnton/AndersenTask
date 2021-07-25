package av.task2.dao;

import av.task2.DataSource;
import av.task2.entity.Feedback;
import av.task2.exception.DatabaseCommunicationException;
import av.task2.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class JdbcFeedbackDAO implements FeedbackDAO {
    @Override
    public void create(Feedback feedback) {
        log.debug("Starting create Feedback");
        try {
            try (Connection conn = DataSource.getConnection()) {
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO feedback(id, description, date) VALUES (?, ?, ?)");
                preparedStatement.setLong(1, feedback.getId());
                preparedStatement.setString(2, feedback.getDescription());
                preparedStatement.setDate(3, Date.valueOf(feedback.getDate()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing create Feedback");
    }

    @Override
    public Feedback readById(Long id) {
        log.debug("Starting read Feedback");
        try {
            try (Connection conn = DataSource.getConnection()) {
                var statement = conn.prepareStatement("SELECT * FROM feedback WHERE id=?");
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setId(resultSet.getLong("id"));
                    feedback.setDescription(resultSet.getString("description"));
                    feedback.setDate(resultSet.getDate("date").toLocalDate());
                    log.debug("Finishing read Feedback");
                    return feedback;
                } else {
                    throw new EntityNotFoundException();
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
    }

    @Override
    public void update(Feedback feedback) {
        log.debug("Starting update Feedback");
        try {
            try (Connection conn = DataSource.getConnection()) {
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE feedback SET description=?, date=? WHERE id=?");
                preparedStatement.setLong(3, feedback.getId());
                preparedStatement.setString(1, feedback.getDescription());
                preparedStatement.setDate(2, Date.valueOf(feedback.getDate()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing update Feedback");
    }

    @Override
    public void delete(Feedback feedback) {
        log.debug("Starting delete Feedback");
        try {
            try (Connection conn = DataSource.getConnection()) {
                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM feedback WHERE id=?");
                preparedStatement.setLong(1, feedback.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseCommunicationException(ex);
        }
        log.debug("Finishing delete Feedback");
    }
}
