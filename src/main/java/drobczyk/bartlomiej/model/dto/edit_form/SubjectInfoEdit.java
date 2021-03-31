package drobczyk.bartlomiej.model.dto.edit_form;

import java.util.List;

public class SubjectInfoEdit {
    private Integer grade;
    private List<String> subject;
    private List<String> day;
    private String extraInfo;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public List<String> getDay() {
        return day;
    }

    public void setDay(List<String> day) {
        this.day = day;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
