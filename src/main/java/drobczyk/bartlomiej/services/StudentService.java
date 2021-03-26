package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.DTO.addition_form.LessonFormInfo;
import drobczyk.bartlomiej.model.DTO.edit_form.BasicInfoEdit;
import drobczyk.bartlomiej.model.DTO.edit_form.SubjectInfoEdit;
import drobczyk.bartlomiej.model.Lesson.Day;
import drobczyk.bartlomiej.model.Lesson.Lesson;
import drobczyk.bartlomiej.model.Lesson.Subject;
import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.repo.LessonRepo;
import drobczyk.bartlomiej.repo.StudentRepo;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private StudentRepo studentRepo;
    private LessonRepo lessonRepo;
    private SubjectService subjectService;
    private DayService dayService;
    private TeacherSession teacherSession;
    private TeacherService teacherService;



    @Autowired
    public StudentService(StudentRepo studentRepo, LessonRepo lessonRepo, SubjectService subjectService, DayService dayService, TeacherSession teacherSession, TeacherService teacherService) {
        this.studentRepo = studentRepo;
        this.lessonRepo = lessonRepo;
        this.subjectService = subjectService;
        this.dayService = dayService;
        this.teacherSession = teacherSession;
        this.teacherService = teacherService;
    }

    public void saveStudent(Student student) {
        studentRepo.save(student);
    }

    public Student getStudentById(Long id) throws NoSuchElementException {
        Optional<Student> student = studentRepo.findById(id);
        return student.orElseThrow(NoSuchElementException::new);
    }

    public void saveLessonToStudent(Student student, LessonFormInfo lessonFormInfo) {
        Lesson lesson = createLessonFromForm(student, lessonFormInfo);
        student.getLessons().add(lesson);
        lessonRepo.save(lesson);
        teacherSession.updateTeacher();
    }

    private Lesson createLessonFromForm(Student student, LessonFormInfo lessonFormInfo) {
        return new Lesson(subjectService.findSubjectByDesc(lessonFormInfo.getChosenLesson()), lessonFormInfo.getLessonSection(),
                lessonFormInfo.getHomework(), lessonFormInfo.getLessonComment(), LocalDateTime.now(), student);
    }


    public List<Lesson> getCurrentLessons(Student student) {
        List<Lesson> currentLessons;
        if (student.getLastArchivedPosition() > 0) {
            currentLessons = getOrderedLessons(student)
                    .subList(student.getLastArchivedPosition().intValue(), student.getLessons().size());
        } else {
            currentLessons = getOrderedLessons(student);
        }
        return currentLessons;
    }

    public Student archiveCurrentLessons(Long studentId, Long positionToArchive) {
        Student student = this.getStudentById(studentId);
        Long newPosition = student.getLastArchivedPosition() + positionToArchive;
        student.setLastArchivedPosition(newPosition);
        this.saveStudent(student);
        return student;
    }

    public List<Lesson> getOrderedLessons(Student student){
        return student.getLessons().stream()
                .sorted(Comparator.comparing(Lesson::getLessonDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void changeStudentAvatar(String avatar, Long studentId){
        Student student = studentRepo.findById(studentId).orElseThrow(NoSuchElementException::new);
        student.setAvatarUrl(avatar);
        studentRepo.save(student);
    }

    public void editBasicInfo(BasicInfoEdit basicInfoEdit, Long studentId){
        Student student = studentRepo.findById(studentId).orElseThrow(NoSuchElementException::new);
        student.setName(basicInfoEdit.getName());
        student.setSurname(basicInfoEdit.getSurname());
        student.setPhone(basicInfoEdit.getPhone());
        student.setParent(basicInfoEdit.getParent());
        student.setEmail(basicInfoEdit.getMail());
        student.setParentNumber(basicInfoEdit.getParentPhone());
        studentRepo.save(student);
    }

    public void editSubjectInfo(SubjectInfoEdit subjectInfoEdit, Long studentId) {
        Set<Subject> subjects = subjectInfoEdit.getSubject().stream()
                .map(this::matchSubjcetWithFormDescription)
                .collect(Collectors.toSet());
        Set<Day> days = subjectInfoEdit.getDay().stream()
                .map(this::matchDayWithFormDescription)
                .collect(Collectors.toSet());

        Student student = studentRepo.findById(studentId).orElseThrow(NoSuchElementException::new);
        student.setGrade(subjectInfoEdit.getGrade());
        student.setSubjects(subjects);
        student.setDays(days);
        student.setAdditionalInfo(subjectInfoEdit.getExtraInfo());
        studentRepo.save(student);
    }
    private Subject matchSubjcetWithFormDescription(String subjectDesc) {
        return subjectService.findSubjectByDesc(subjectDesc);
    }

    private Day matchDayWithFormDescription(String dayDesc) {
        return dayService.findDayByDesc(dayDesc);
    }

    public void deleteStudent(Long studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(NoSuchElementException::new);
        student.getDays().clear();
        student.getLessons().clear();
        student.getSubjects().clear();
        teacherSession.getTeacher().getStudents().remove(student);
        studentRepo.delete(student);
        teacherSession.updateTeacher();
    }

    public List<Student> findStudentsInArchive(String student){
        String firstName = "";
        String lastName = "XDF-GHTYUFV_HJIOP";
        student.trim();
        if (student.contains(" ")){
            String[] splittedToNameAndSurname = student.split(" ");
            firstName = splittedToNameAndSurname[0];
            lastName = splittedToNameAndSurname[1];
            return studentRepo.findAllByNameIsStartingWithOrSurnameStartingWithIgnoreCase(firstName,lastName);
        }
        return studentRepo.findAllByNameIsStartingWithOrSurnameStartingWithIgnoreCase(student,lastName);
    }

}
