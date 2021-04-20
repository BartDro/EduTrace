package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.roles.UserRole;
import drobczyk.bartlomiej.model.teacher.Teacher;
import drobczyk.bartlomiej.repo.RoleRepo;
import drobczyk.bartlomiej.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TeacherService{
    private TeacherRepo teacherRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private static final String DEFAULT_ROLE = "ROLE_USER";


    @Autowired
    public TeacherService(TeacherRepo teacherRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.teacherRepo = teacherRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveTeacher(Teacher teacher){
        teacherRepo.save(teacher);
    }

    public void registerTeacher(Teacher teacher){
        UserRole defaultRole = roleRepo.findByRole(DEFAULT_ROLE)
                .orElseThrow(NoSuchElementException::new);
        teacher.getRoles().add(defaultRole);
        String encodedPassword = passwordEncoder.encode(teacher.getPassword());
        teacher.setPassword(encodedPassword);
        teacherRepo.save(teacher);
    }

    public Teacher updateTeacher(Teacher teacher){
        return teacherRepo.save(teacher);
    }
}