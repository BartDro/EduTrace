package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.DTO.StudentFormInfo;
import drobczyk.bartlomiej.model.Lesson.Day;
import drobczyk.bartlomiej.model.Lesson.Subject;
import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.model.Teacher.Teacher;
import drobczyk.bartlomiej.repo.StudentRepo;
import drobczyk.bartlomiej.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainPanelService {
    private TeacherRepo teacherRepo;
    private StudentRepo studentRepo;

    @Autowired
    public MainPanelService(TeacherRepo teacherRepo, StudentRepo studentRepo) {
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }

    public void addStudentToTeacher(StudentFormInfo formInfo, Teacher teacher) {
        Student student = createStudentFromForm(formInfo, teacher);
        studentRepo.save(student);
        teacher.getStudents().add(student);
        teacherRepo.save(teacher);
    }

    private Student createStudentFromForm(StudentFormInfo formInfo, Teacher teacher) {
        List<Subject> subjects = formInfo.getSubject().stream()
                .map(this::mapStringToSubject)
                .collect(Collectors.toList());
        List<Day> days = formInfo.getDay().stream()
                .map(this::mapStringToDay)
                .collect(Collectors.toList());
        return new Student(formInfo.getAvatar(), formInfo.getName(), formInfo.getSurname(), formInfo.getPhone(),
                formInfo.getMail(), formInfo.getGrade(), formInfo.getParent(), formInfo.getParentPhone(), subjects, days,
                formInfo.getExtraInfo(), LocalDateTime.now(), teacher);
    }

    private Subject mapStringToSubject(String s) {
        return new Subject(s);
    }

    private Day mapStringToDay(String s) {
        return new Day(s);
    }
}
