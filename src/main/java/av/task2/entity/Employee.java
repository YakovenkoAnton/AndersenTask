package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data

@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String midname;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private byte experience;
    @Column(name = "employment_date")
    private LocalDate employmentDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "project_id")
    private Project project;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "developer_level")
    private DevLevel developerLevel;
    @Column(name = "english_level")
    private String englishLevel;
    private String skype;
    @OneToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    public String getFIO() {
        return name + " " + midname + " " + surname;
    }

}
