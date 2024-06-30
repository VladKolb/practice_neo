package com.example.neo_practice;

import com.example.neo_practice.servises.FilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class FilterTest {

    @Autowired
    private FilterService filterService;

    @Test
    public void check(){
        //filterService.colorFilter("White");
        //filterService.startDateFilter(LocalDate.of(2022, 1,1), ">");
        filterService.amountFilter(50, "<");
    }
}
