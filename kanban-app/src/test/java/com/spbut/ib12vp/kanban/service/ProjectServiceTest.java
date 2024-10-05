package com.spbut.ib12vp.kanban.service;

import com.spbut.ib12vp.kanban.model.Project;
import com.spbut.ib12vp.kanban.repository.KanbanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    KanbanService kanbanService;
    @Mock
    KanbanRepository kanbanRepository;

    @Before
    public void init() {
        kanbanService = new KanbanServiceImpl(kanbanRepository);
    }

    @Test
    public void when2KanbansInDatabase_thenGetListWithAllOfThem() {
        //given
        mockKanbanInDatabase(2);

        //when
        List<Project> projects = kanbanService.getAllKanbanBoards();

        //then
        assertEquals(2, projects.size());
    }

    private void mockKanbanInDatabase(int kanbanCount) {
        when(kanbanRepository.findAll())
                .thenReturn(createKanbanList(kanbanCount));
    }

    private List<Project> createKanbanList(int kanbanCount) {
        List<Project> projects = new ArrayList<>();
        IntStream.range(0, kanbanCount)
                .forEach(number ->{
                    Project project = new Project();
                    project.setId(Long.valueOf(number));
                    project.setName("Kanban " + number);
                    project.setCards(new ArrayList<>());
                    projects.add(project);
                });
        return projects;
    }
}
