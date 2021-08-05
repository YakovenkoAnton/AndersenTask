package av.task2.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDate;

@Data

@NoArgsConstructor
@Accessors(chain = true)
public class Employee {
    private Long id;
    private String name;
    private String surname;
    private String midname;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private byte experience;
    private LocalDate employmentDate;
    @EqualsAndHashCode.Exclude
    private Project project;
    private DevLevel developerLevel;
    private String englishLevel;
    private String skype;
    private Feedback feedback;
    public String getFIO() {
        return name + " " + midname + " " + surname;
    }
}
