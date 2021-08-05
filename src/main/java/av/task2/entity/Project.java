package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Project {
    private Long id;
    private String name;
    private String customer;
    private int duration;
    private String methodology;
    private Employee projectManager;
    private Team team;
}
