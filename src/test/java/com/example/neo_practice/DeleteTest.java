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
        //deleteService.deleteObject("cc6201ce-3bd9-484a-a5dd-01c0bf08da38");
        deleteService.deleteObjectFromRevision("11cdc660-4fb1-46ce-a6e6-8b644cd5ac01", "cc6201ce-3bd9-484a-a5dd-01c0bf08da38");
    }
}
