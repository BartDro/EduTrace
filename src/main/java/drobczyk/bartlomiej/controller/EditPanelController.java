package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.DTO.edit_form.BasicInfoEdit;
import drobczyk.bartlomiej.model.DTO.edit_form.SubjectInfoEdit;
import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.services.StudentService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EditPanelController {
    private StudentService studentService;
    private TeacherSession teacherSession;

    @Autowired
    public EditPanelController(StudentService studentService, TeacherSession teacherSession) {
        this.studentService = studentService;
        this.teacherSession = teacherSession;
    }

    @GetMapping("/edit-student")
    public String presentEditPanel(@RequestParam Long studentId, Model model){
        if (teacherSession.isTeacherLogged()){
            Student student = studentService.getStudentById(studentId);
            model.addAttribute("basicInfo", new BasicInfoEdit());
            model.addAttribute("subjectInfo",new SubjectInfoEdit());
            model.addAttribute("chosenStudent",student);
            return "editProfilePanel";
        }
        return "redirect:/";
    }

    @PostMapping("/edit-student/avatar")
    public String changeAvatar(@RequestParam String avatar,@RequestParam Long studentId){
        studentService.changeStudentAvatar(avatar,studentId);
        return "redirect:/edit-student?studentId="+studentId;
    }

    @PostMapping("/edit-student/basic-info")
    public String editBasicInfo(@ModelAttribute BasicInfoEdit basicInfo,@RequestParam Long studentId){
        studentService.editBasicInfo(basicInfo, studentId);
        return "redirect:/edit-student?studentId="+studentId;
    }

    @PostMapping("/edit-student/subject-info")
    public String editSubjectInfo(@ModelAttribute SubjectInfoEdit subjectInfoEdit,@RequestParam Long studentId){
        studentService.editSubjectInfo(subjectInfoEdit,studentId);
        return "redirect:/edit-student?studentId="+studentId;
    }
}
