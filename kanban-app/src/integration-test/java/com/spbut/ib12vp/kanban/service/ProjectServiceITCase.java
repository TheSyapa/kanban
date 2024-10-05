package com.spbut.ib12vp.kanban.service;

import com.spbut.ib12vp.kanban.model.KanbanDTO;
import com.spbut.ib12vp.kanban.model.Project;
import com.spbut.ib12vp.kanban.config.H2DatabaseConfig4Test;
import com.spbut.ib12vp.kanban.repository.KanbanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { H2DatabaseConfig4Test.class })
public class ProjectServiceITCase {

    @Autowired
    private KanbanRepository kanbanRepository;
    private KanbanService kanbanService;


    @Before
    public void init() {
        kanbanService = new KanbanServiceImpl(kanbanRepository);
    }


    @Test
    public void whenNewKanbanCreated_thenKanbanIsSavedInDb() {
        //given
        KanbanDTO kanbanDTO = KanbanDTO.builder()
                                    .name("Test Kanban")
                                .build();

        //when
        kanbanService.saveNewKanban(kanbanDTO);

        //then
        List<Project> projects = (List<Project>) kanbanRepository.findAll();

        assertNotNull(projects.get(0));
        assertEquals("Test Kanban", projects.get(0).getName());
    }
}
