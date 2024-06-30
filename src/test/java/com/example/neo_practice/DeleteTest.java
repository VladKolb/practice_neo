package com.example.neo_practice;

import com.example.neo_practice.servises.DeleteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeleteTest {

    @Autowired
    private DeleteService deleteService;

    @Test
    public void check(){
        //deleteService.deleteRevision("39f27871-ade8-4506-85b7-6a43f3e7137d");
        deleteService.deleteObject("26d3b00a-f812-4490-aceb-64b9f5f4dd05");
        //deleteService.deleteObjectFromRevision("92fa682a-403e-46b3-9f9c-f38f84199e39", "26d3b00a-f812-4490-aceb-64b9f5f4dd05");
    }
}
