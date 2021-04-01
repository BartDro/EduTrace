package drobczyk.bartlomiej.model.student;

import drobczyk.bartlomiej.model.day.Day;
import drobczyk.bartlomiej.model.dto.StudentDto;
import drobczyk.bartlomiej.model.subject.Subject;
import drobczyk.bartlomiej.model.teacher.Teacher;
import drobczyk.bartlomiej.services.DayService;
import drobczyk.bartlomiej.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentMapper {
    private final SubjectService subjectService;
    private final DayService dayService;

    @Autowired
    public StudentMapper(SubjectService subjectService, DayService dayService) {
        this.subjectService = subjectService;
        this.dayService = dayService;
    }

    public StudentDto toStudentDto(Student student){
      List<String> subjects = getListedSubjects(student.getSubjects());
      List<String> days = getListedDays(student.getDays());
      return new StudentDto(student.getId(), student.getName(), student.getSurname(), student.getPhone(), student.getEmail(),
              student.getGrade(), student.getParent(), student.getParent(), student.getAdditionalInfo(), student.getAvatarUrl(),
              student.getLastArchivedPosition(), subjects,days,student.getLessons().size());
    }

    private List<String> getListedSubjects(Set<Subject> subjectSet){
        return subjectSet.stream()
                .map(Subject::getSubject)
                .collect(Collectors.toList());
    }

    private List<String> getListedDays(Set<Day> daysSet){
        return daysSet.stream()
                .map(Day::getDay)
                .collect(Collectors.toList());
    }

    public Student toStudentEntity(StudentDto studentDto, Teacher teacher){
        Set<Subject> subjects = studentDto.getSubjects().stream()
                .map(this::matchSubjcetWithFormDescription)
                .collect(Collectors.toSet());
        Set<Day> days = studentDto.getDays().stream()
                .map(this::matchDayWithFormDescription)
                .collect(Collectors.toSet());

        return new Student(studentDto.getAvatarUrl(), studentDto.getName(), studentDto.getSurname(), studentDto.getPhone(),
                studentDto.getEmail(), studentDto.getGrade(), studentDto.getParent(), studentDto.getParentPhone(), subjects, days,
                studentDto.getAdditionalInfo(), LocalDateTime.now(), teacher);
    }

    private Subject matchSubjcetWithFormDescription(String subjectDesc) {
        return subjectService.findSubjectByDesc(subjectDesc);
    }

    private Day matchDayWithFormDescription(String dayDesc) {
        return dayService.findDayByDesc(dayDesc);
    }
}
