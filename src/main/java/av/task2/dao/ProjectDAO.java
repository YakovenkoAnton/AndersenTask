package av.task2.dao;

import av.task2.entity.Project;

public interface ProjectDAO {
    void create(Project project);
    Project readById(Long id);

    void update(Project project);

    void delete(Project project);
}
