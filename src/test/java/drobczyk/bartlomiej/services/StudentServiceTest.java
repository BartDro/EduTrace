package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.dto.LessonDto;
import drobczyk.bartlomiej.model.lesson.Lesson;
import drobczyk.bartlomiej.model.student.Student;
import drobczyk.bartlomiej.model.subject.Subject;
import drobczyk.bartlomiej.repo.LessonRepo;
import drobczyk.bartlomiej.repo.StudentRepo;
import drobczyk.bartlomiej.session.TeacherSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private Student testStudent;
    private Set<Lesson> testLessons;
    private StudentService underTest;
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private LessonRepo lessonRepo;
    @Mock
    private SubjectService subjectService;
    @Mock
    private DayService dayService;
    @Mock
    private TeacherSession teacherSession;
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testLessons = Stream.generate(() -> new Lesson(new Subject("Fizyka"), "Arytmetyka", "Podręcznik",
                "Comment", getRandomDate(), testStudent))
                .limit(50)
                .collect(Collectors.toSet());
        testLessons.forEach(x -> x.setId(getNextLong()));
        testStudent.setLessons(testLessons);
        testStudent.setLastArchivedPosition(0L);
        testStudent.setId(7L);
        underTest = new StudentService(studentRepo, lessonRepo, subjectService, dayService, teacherSession);
        given(studentRepo.findById(testStudent.getId()))
                .willReturn(Optional.of(testStudent));
    }

    private Long getNextLong() {
        int nonNullSize = testLessons.stream()
                .filter(x->x.getId()!=null)
                .collect(Collectors.toSet())
                .size();
        return (long) nonNullSize;
    }

    private LocalDateTime getRandomDate() {
        return LocalDateTime.now().plusDays(random.longs(1l, 10l)
                .findFirst()
                .orElseThrow(() -> new DateTimeException("Błąd zakresu")));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 5, 10, 15, 20, 25, 30, 35, 45, 49})
    public void shouldProvideOrderedLessonSublist(Long index) {
        //given
        testStudent.setLastArchivedPosition(index);
        //when
        List<LessonDto> testSubList = underTest.getCurrentLessonsDto(testStudent.getId());
        //then
        assertThat(testSubList.size()).isEqualTo(testStudent.getLessons().size() - testStudent.getLastArchivedPosition());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 5, 10, 15, 20, 25, 30, 35, 45, 49})
    public void shouldSetArchivedIndexEqualListSize(Long index) {
        //given
        testStudent.setLastArchivedPosition(index);
        Long listSizeAndArchivedPositionDifference = testStudent.getLessons().size() - testStudent.getLastArchivedPosition();
        //when
        underTest.archiveCurrentLessons(testStudent.getId(),listSizeAndArchivedPositionDifference);

        //then
        var capturedStudent = ArgumentCaptor.forClass(Student.class);
        verify(studentRepo).save(capturedStudent.capture());
        assertThat(capturedStudent.getValue().getLastArchivedPosition()).isEqualTo(50L);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 5, 10, 15, 20, 25, 30, 35, 45, 49})
    public void shouldRemoveLessonFromStudent(Long index){
        //when
        underTest.deleteStudentsLesson(testStudent.getId(),index);
        //then
        assertThat(testStudent.getLessons().size()).isEqualTo(50-1);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 5, 10, 15, 20, 25, 30, 35, 45, 49})
    public void shouldRemoveLessonWhenStudentArchivePositionIsSet(Long index){
        //given
        testStudent.setLastArchivedPosition(10L);
        Long previousPosition = testStudent.getLastArchivedPosition();
        //when
        underTest.deleteStudentsLesson(testStudent.getId(),index);
        //then
        assertThat(testStudent.getLastArchivedPosition()).isEqualTo(previousPosition-1);
    }


}