package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.exceptions.StudentNotFoundException;
import drobczyk.bartlomiej.model.dto.LessonDto;
import drobczyk.bartlomiej.model.dto.StudentDto;
import drobczyk.bartlomiej.model.dto.addition_form.LessonFormInfo;
import drobczyk.bartlomiej.model.dto.edit_form.BasicInfoEdit;
import drobczyk.bartlomiej.model.dto.edit_form.SubjectInfoEdit;
import drobczyk.bartlomiej.model.day.Day;
import drobczyk.bartlomiej.model.lesson.Lesson;
import drobczyk.bartlomiej.model.lesson.LessonMapper;
import drobczyk.bartlomiej.model.student.StudentMapper;
import drobczyk.bartlomiej.model.subject.Subject;
import drobczyk.bartlomiej.model.student.Student;
import drobczyk.bartlomiej.repo.LessonRepo;
import drobczyk.bartlomiej.repo.StudentRepo;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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


    @Autowired
    public StudentService(StudentRepo studentRepo, LessonRepo lessonRepo, SubjectService subjectService, DayService dayService, TeacherSession teacherSession) {
        this.studentRepo = studentRepo;
        this.lessonRepo = lessonRepo;
        this.subjectService = subjectService;
        this.dayService = dayService;
        this.teacherSession = teacherSession;
    }

    public void saveStudent(Student student) {
        studentRepo.save(student);
    }

    public Student getStudentById(Long id) throws NoSuchElementException {
        Optional<Student> student = studentRepo.findById(id);
        return student.orElseThrow(() -> new StudentNotFoundException(id));
    }

    public List<StudentDto> provideStudentsDtosAccordingToTeacher() {
        return teacherSession.getTeacher().getStudents().stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentDto provideStudentDto(Long studentId) {
        return StudentMapper.toDto(getStudentById(studentId));
    }

    public void saveLessonToStudent(Student student, LessonFormInfo lessonFormInfo) {
        Lesson lesson = createLessonFromForm(student, lessonFormInfo);
        student.getLessons().add(lesson);
        lessonRepo.save(lesson);
        teacherSession.refresh();
    }

    private Lesson createLessonFromForm(Student student, LessonFormInfo lessonFormInfo) {

        return new Lesson(subjectService.findSubjectByDesc(lessonFormInfo.getChosenLesson()), lessonFormInfo.getLessonSection(),
                lessonFormInfo.getHomework(), lessonFormInfo.getLessonComment(), LocalDateTime.now(), student);
    }


    public List<LessonDto> getCurrentLessonsDto(Long studentId) {
        Student student = getStudentById(studentId);
        List<Lesson> currentLessons;
        if (student.getLastArchivedPosition() > 0) {
            currentLessons = getOrderedLessons(student)
                    .subList(student.getLastArchivedPosition().intValue(), student.getLessons().size());
        } else {
            currentLessons = getOrderedLessons(student);
        }
        return currentLessons.stream()
                .map(LessonMapper::toDto)
                .collect(Collectors.toList());
    }

    public Student archiveCurrentLessons(Long studentId, Long positionToArchive) {
        Student student = this.getStudentById(studentId);
        Long newPosition = student.getLastArchivedPosition() + positionToArchive;
        student.setLastArchivedPosition(newPosition);
        this.saveStudent(student);
        return student;
    }

    public List<Lesson> getOrderedLessons(Student student) {
        return student.getLessons().stream()
                .sorted(Comparator.comparing(Lesson::getLessonDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void changeStudentAvatar(String avatar, Long studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        student.setAvatarUrl(avatar);
        studentRepo.save(student);
    }

    public void editBasicInfo(BasicInfoEdit basicInfoEdit, Long studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
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

        Student student = studentRepo.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        student.setGrade(subjectInfoEdit.getGrade());
        student.setSubjects(subjects);
        student.setDays(days);
        student.setAdditionalInfo(subjectInfoEdit.getExtraInfo());
        studentRepo.save(student);
        teacherSession.refresh();
    }

    private Subject matchSubjcetWithFormDescription(String subjectDesc) {
        return subjectService.findSubjectByDesc(subjectDesc);
    }

    private Day matchDayWithFormDescription(String dayDesc) {
        return dayService.findDayByDesc(dayDesc);
    }

    public void deleteStudent(Long studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        student.getDays().clear();
        student.getLessons().clear();
        student.getSubjects().clear();
        teacherSession.getTeacher().getStudents().remove(student);
        studentRepo.delete(student);
        teacherSession.refresh();
    }

    public List<StudentDto> findTeachersStudentsInArchive(String studentInfo) {
        Set<Student> teachersStudents = teacherSession.getTeacher().getStudents();
        return studentRepo.findMatchedStudentsByString(studentInfo).stream()
                .filter(teachersStudents::contains)
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

}