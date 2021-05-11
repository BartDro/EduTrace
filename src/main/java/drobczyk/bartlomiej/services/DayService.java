package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.exceptions.NoSuchDayException;
import drobczyk.bartlomiej.model.day.Day;
import drobczyk.bartlomiej.repo.DayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class DayService {
    private DayRepo dayRepo;

    @Autowired
    public DayService(DayRepo dayRepo) {
        this.dayRepo = dayRepo;
    }

    public Day findDayByDesc(String dayDesc){
        return dayRepo.findByDay(dayDesc).orElseThrow(NoSuchDayException::new);
    }
}