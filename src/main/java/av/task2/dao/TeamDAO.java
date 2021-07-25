package av.task2.dao;

import av.task2.entity.Team;

public interface TeamDAO {
    void create(Team team);

    Team readById(Long id);

    void update(Team team);

    void delete(Team team);

}
