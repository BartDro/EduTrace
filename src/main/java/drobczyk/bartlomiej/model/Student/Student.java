package drobczyk.bartlomiej.model.Student;

import drobczyk.bartlomiej.model.Lesson.Day;
import drobczyk.bartlomiej.model.Lesson.Lesson;
import drobczyk.bartlomiej.model.Lesson.Subject;
import drobczyk.bartlomiej.model.Teacher.Teacher;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private Integer grade;
    private String parent;
    private String parentNumber;
    private String additionalInfo;
    private String avatarUrl;
    private LocalDateTime registrationDate;
    private Long lastArchivedPosition;
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable (name = "student_subject",
    joinColumns = {@JoinColumn(name = "id_student",referencedColumnName = "student_id" )},
    inverseJoinColumns = {@JoinColumn(name = "id_subject",referencedColumnName = "subject_id")})
    private List<Subject> subjects = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "day_student",
    joinColumns = {@JoinColumn(name = "id_student", referencedColumnName ="student_id")},
    inverseJoinColumns = {@JoinColumn (name = "id_day",referencedColumnName ="day_id" )})
    private List<Day> days = new ArrayList<>();
    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Lesson> lessons = new HashSet<>();


    public Student(){
    }

    public Student( String avatarUrl,String name, String surname, String phone, String email, Integer grade,
                   String parent, String parentNumber,List<Subject> subjects,List<Day> days, String additionalInfo,
                    LocalDateTime registrationDate, Teacher teacher) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.grade = grade;
        this.parent = parent;
        this.parentNumber = parentNumber;
        this.subjects = subjects;
        this.additionalInfo = additionalInfo;
        this.days = days;
        this.registrationDate = registrationDate;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(String parentNumber) {
        this.parentNumber = parentNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Long getLastArchivedPosition() {
        return lastArchivedPosition;
    }

    public void setLastArchivedPosition(Long lastArchivedPosition) {
        this.lastArchivedPosition = lastArchivedPosition;
    }
}
