package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.DTO.LessonFormInfo;
import drobczyk.bartlomiej.model.Lesson.Lesson;
import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.repo.LessonRepo;
import drobczyk.bartlomiej.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepo studentRepo;
    private LessonRepo lessonRepo;
    private SubjectService subjectService;


    @Autowired
    public StudentService(StudentRepo studentRepo, LessonRepo lessonRepo, SubjectService subjectService) {
        this.studentRepo = studentRepo;
        this.lessonRepo = lessonRepo;
        this.subjectService = subjectService;
    }

    public void saveStudent(Student student){
        studentRepo.save(student);
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
        return new Lesson(subjectService.findSubjectByDesc(lessonFormInfo.getChosenLesson()),lessonFormInfo.getLessonSection(),
                        lessonFormInfo.getHomework(), lessonFormInfo.getLessonComment(), LocalDateTime.now(),student);
    }

    /*
    public List<Lesson> getCurrentLessons(Student student){
        List<Lesson> currentLessons;
        if (student.getLastArchivedPosition() != null){
            currentLessons = student.getLessons()
                    .subList(student.getLastArchivedPosition().intValue(),student.getLessons().size());
        }else{
            currentLessons = student.getLessons();
        }
        return currentLessons;
    }

     */
}
