package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDate;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Feedback {
    private Long id;
    private String description;
    private LocalDate date;
}
