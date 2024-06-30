package com.example.neo_practice;

import com.example.neo_practice.models.Object;
import com.example.neo_practice.models.Revision;
import com.example.neo_practice.repositories.ObjectRepository;
import com.example.neo_practice.repositories.RevisionRepository;
import com.example.neo_practice.servises.TraverseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.AccessOptions;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class TraverseTest {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private TraverseService traverseService;

    @Test
    public void check(){
        traverseService.traverseTree(objectRepository.findRoot());
    }

}
