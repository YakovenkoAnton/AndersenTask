package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private LocalDate date;
}
