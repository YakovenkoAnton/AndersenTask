package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data

@NoArgsConstructor
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
    private Project project;
    private DevLevel developerLevel;
    private String englishLevel;
    private String skype;
    private Feedback feedback;

    public String getFIO() {
        return name + " " + midname + " " + surname;
    }

}
