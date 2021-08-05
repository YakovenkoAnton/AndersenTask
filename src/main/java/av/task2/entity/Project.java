package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Accessors(chain = true)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String customer;
    @Column
    private int duration;
    @Column
    private String methodology;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "project_manager_id")
    private Employee projectManager;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "team_id")
    private Team team;
}
