package av.task2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Team {
    private Long id;
    private Set<Employee> employees;
}
