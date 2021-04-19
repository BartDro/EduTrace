package drobczyk.bartlomiej.controller;

import drobczyk.bartlomiej.model.dto.edit_form.BasicInfoEdit;
import drobczyk.bartlomiej.model.dto.edit_form.SubjectInfoEdit;
import drobczyk.bartlomiej.model.student.Student;
import drobczyk.bartlomiej.services.StudentService;
import drobczyk.bartlomiej.services.api.ApiService;
import drobczyk.bartlomiej.session.TeacherSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EditPanelController {
    private StudentService studentService;
    private TeacherSession teacherSession;
    private ApiService apiService;

    @Autowired
    public EditPanelController(StudentService studentService, TeacherSession teacherSession, ApiService apiService) {
        this.studentService = studentService;
        this.teacherSession = teacherSession;
        this.apiService = apiService;
    }

    @GetMapping("/edit-student")
    public String presentEditPanel(@RequestParam Long studentId, Model model){
        if (teacherSession.isTeacherLogged()){
            Student student = studentService.getStudentById(studentId);
            model.addAttribute("basicInfo", new BasicInfoEdit());
            model.addAttribute("subjectInfo",new SubjectInfoEdit());
            model.addAttribute("chosenStudent",studentService.provideStudentDto(studentId));
            model.addAttribute("students", studentService.provideStudentsDtosAccordingToTeacher());
            model.addAttribute("weather",apiService.provideWeather(apiService.provideLocationDto()));
            model.addAttribute("quote",apiService.provideRandomQuote());
            return "editProfilePanel";
        }
        return "redirect:/";
    }

    @PostMapping("/edit-student/avatar")
    public String changeAvatar(@RequestParam String avatar,@RequestParam Long studentId){
        studentService.changeStudentAvatar(avatar,studentId);
        teacherSession.refresh();
        return "redirect:/edit-student?studentId="+studentId;
    }

    @PostMapping("/edit-student/basic-info")
    public String editBasicInfo(@ModelAttribute BasicInfoEdit basicInfo,@RequestParam Long studentId){
        studentService.editBasicInfo(basicInfo, studentId);
        teacherSession.refresh();
        return "redirect:/edit-student?studentId="+studentId;
    }

    @PostMapping("/edit-student/subject-info")
    public String editSubjectInfo(@ModelAttribute SubjectInfoEdit subjectInfoEdit,@RequestParam Long studentId){
        studentService.editSubjectInfo(subjectInfoEdit,studentId);
        teacherSession.refresh();
        return "redirect:/edit-student?studentId="+studentId;
    }
}