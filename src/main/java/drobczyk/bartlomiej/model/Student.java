package drobczyk.bartlomiej.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    private String name;
    private String surname;
    private String telephone;
    private String email;
    private String parentNumber;
    private List<String> lessons = new ArrayList<>();
    private List<String> subjcets = new ArrayList<>();
    private List<String> dayOfClasses = new ArrayList<>();

    public Student(){

    }

}
