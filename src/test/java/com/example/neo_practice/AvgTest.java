package com.example.neo_practice;

import com.example.neo_practice.repositories.ObjectRepository;
import com.example.neo_practice.servises.AvgService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class AvgTest {

    @Autowired
    private AvgService avgService;

    @Autowired
    private ObjectRepository objectRepository;

    @Test
    public void check(){
        //avgService.findObjectWithMinQuantityRevision(objectRepository.findRoot());
        //System.out.println(avgService.findObjectWithMinRevisions(objectRepository.findRoot()).getId());
        //avgService.findObjectWithMaxQuantityRevision(objectRepository.findRoot());

        //avgService.findFullAverageAmount(objectRepository.findRoot());
        //avgService.findAverageAmountInObject(objectRepository.findOneById("88c5bac1-04c8-4cb4-a6a6-754492a1d99c"));
        //avgService.findAverageAmountInAllRevisionsInObject(objectRepository.findOneById("e1d3a7d9-9540-4638-8684-a9b0ce4d60cc"));

        //avgService.findSumAmountWithColorParametr("White");
        //avgService.findSumAmountWithStartAndEndDateParametr(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1,1));
        //avgService.findSumAmountWithAmountParametr(500, "<");
        //avgService.findFullSumAmount(objectRepository.findRoot());

        //avgService.findAvgAmountWithAmountParametr(500, ">");
        //avgService.findAvgAmountWithColorParametr("White");
        //avgService.findAvgAmountWithStartAndEndDataParametr(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1,1));

        //avgService.countObjectAndRevision(objectRepository.findRoot());
        avgService.countObjectAndRevisionWithAmountParametr(500, "<");
    }

}
