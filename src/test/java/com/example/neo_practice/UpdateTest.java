package com.example.neo_practice;

import com.example.neo_practice.servises.UpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class UpdateTest {

    @Autowired
    private UpdateService updateService;

    @Test
    public void check(){
        //updateService.addRevisionToObject("f495d77c-67db-4434-8678-ea7df80abe61", "86abaf45-898f-4c19-9698-35bae9d0b0b3");
        updateService.addObjectToRevision("75cf0981-9093-4fac-ad98-d2cd99194214", "d989ae91-5465-4192-b6f6-52bcca05b6b3");
    }
}
