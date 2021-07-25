package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Project {
    private Long id;
    private String name;
    private String customer;
    private int duration;
    private String methodology;
    private Employee projectManager;
    private Team team;
}
