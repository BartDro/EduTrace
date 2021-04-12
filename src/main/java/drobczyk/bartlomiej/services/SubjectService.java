package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.subject.Subject;
import drobczyk.bartlomiej.repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service

public class SubjectService {
    private SubjectRepo subjectRepo;

    @Autowired
    public SubjectService(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    public Subject findSubjectByDesc(String subjectDesc){
        return subjectRepo.findBySubject(subjectDesc).orElseThrow(NoSuchElementException::new);
    }
}
