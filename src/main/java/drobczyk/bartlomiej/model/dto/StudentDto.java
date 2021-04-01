package drobczyk.bartlomiej.model.dto;

import java.util.List;

public class StudentDto {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private Integer grade;
    private String parent;
    private String parentPhone;
    private String additionalInfo;
    private String avatarUrl;
    private Long lastArchivedPosition;
    private List<String> subjects;
    private List<String> days;
    private Integer lessonsAmount;

    public StudentDto(Long id, String name, String surname, String phone, String email, Integer grade, String parent,
                      String parentNumber, String additionalInfo, String avatarUrl, Long lastArchivedPosition,
                      List<String> subjects, List<String> days, Integer lessonsAmount) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.grade = grade;
        this.parent = parent;
        this.parentPhone = parentNumber;
        this.additionalInfo = additionalInfo;
        this.avatarUrl = avatarUrl;
        this.lastArchivedPosition = lastArchivedPosition;
        this.subjects = subjects;
        this.days = days;
        this.lessonsAmount = lessonsAmount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getParent() {
        return parent;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Long getLastArchivedPosition() {
        return lastArchivedPosition;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getDays() {
        return days;
    }

    public Integer getLessonsAmount() {
        return lessonsAmount;
    }
}
