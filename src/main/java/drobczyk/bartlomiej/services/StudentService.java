package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.DTO.LessonFormInfo;
import drobczyk.bartlomiej.model.Lesson.Lesson;
import drobczyk.bartlomiej.model.Lesson.Subject;
import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.repo.LessonRepo;
import drobczyk.bartlomiej.repo.StudentRepo;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepo studentRepo;
    private LessonRepo lessonRepo;


    @Autowired
    public StudentService(StudentRepo studentRepo, LessonRepo lessonRepo) {
        this.studentRepo = studentRepo;
        this.lessonRepo = lessonRepo;
    }

    public Student getStudentById(Long id) throws NoSuchElementException{
        Optional<Student> student = studentRepo.findById(id);
        return student.orElseThrow(NoSuchElementException::new);
    }

    public void saveLessonToStudent(Student student, LessonFormInfo lessonFormInfo){
        Lesson lesson = createLessonFromForm(student,lessonFormInfo);
        student.getLessons().add(lesson);
        lessonRepo.save(lesson);


    }

    private Lesson createLessonFromForm (Student student, LessonFormInfo lessonFormInfo){
        return new Lesson(new Subject(lessonFormInfo.getChosenLesson()),lessonFormInfo.getLessonSection(),
                        lessonFormInfo.getHomework(), lessonFormInfo.getLessonComment(), LocalDateTime.now(),student);
    }
}
