package com.example.neo_practice;

import com.example.neo_practice.repositories.RevisionRepository;
import com.example.neo_practice.servises.TreeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateTreeTest {

    @Autowired
    private TreeService treeService;

    @Autowired
    private RevisionRepository revisionRepository;

    @Test
    public void generateTree(){
        //treeService.generateTree();
        treeService.createObject(revisionRepository.findOneById("92fa682a-403e-46b3-9f9c-f38f84199e39"));
    }

}
