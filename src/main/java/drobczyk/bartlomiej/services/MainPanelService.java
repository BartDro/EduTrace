package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.dto.StudentDto;
import drobczyk.bartlomiej.model.student.StudentMapper;
import drobczyk.bartlomiej.model.student.Student;
import drobczyk.bartlomiej.model.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainPanelService {
    private TeacherService teacherService;
    private StudentService studentService;
    private StudentMapper studentMapper;


    @Autowired
    public MainPanelService(TeacherService teacherService, StudentService studentService,
                            StudentMapper studentMapper) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    public void addStudentToTeacher(StudentDto studentDto, Teacher teacher) {
        Student student = studentMapper.toStudentEntity(studentDto,teacher);
        studentService.saveStudent(student);
        teacher.getStudents().add(student);
        teacherService.saveTeacher(teacher);
    }
}
