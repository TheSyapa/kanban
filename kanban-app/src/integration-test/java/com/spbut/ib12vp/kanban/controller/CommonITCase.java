package com.spbut.ib12vp.kanban.controller;

import com.spbut.ib12vp.kanban.model.Card;
import com.spbut.ib12vp.kanban.model.KanbanDTO;
import com.spbut.ib12vp.kanban.model.Project;
import com.spbut.ib12vp.kanban.model.TaskDTO;
import com.spbut.ib12vp.kanban.model.enums.TaskStatus;
import com.spbut.ib12vp.kanban.repository.KanbanRepository;
import com.spbut.ib12vp.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Optional;

@TestPropertySource( properties = {
        "spring.datasource.url=jdbc:h2:mem:test",
        "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect"
})
public class CommonITCase {

    @Autowired
    private KanbanRepository kanbanRepository;

    @Autowired
    private TaskRepository taskRepository;

    protected Project createSingleKanban(){
        Project project = new Project();
        int random = (int)(Math.random() * 100 + 1);
        project.setName("Test Kanban " + random);
        project.setCards(new ArrayList<>());
        return project;
    }

    protected Card createSingleTask(){
        Card card = new Card();
        int random = (int)(Math.random() * 100 + 1);
        card.setName("Test Task " + random);
        card.setDescription("Description " + random);
        card.setColor("Color " + random);
        card.setStatus(TaskStatus.TODO);
        return card;
    }

    protected KanbanDTO convertKanbanToDTO(Project project) {
        return new KanbanDTO().builder()
                .name(project.getName())
                .build();
    }

    protected TaskDTO convertTaskToDTO(Card card) {
        return new TaskDTO().builder()
                .name(card.getName())
                .description(card.getDescription())
                .color(card.getColor())
                .status(card.getStatus())
                .build();
    }

    protected Project saveSingleRandomKanban(){
        return kanbanRepository.save(createSingleKanban());
    }

    protected Project saveSingleKanbanWithOneTask(){
        Project project = createSingleKanban();
        Card card = createSingleTask();
        project.addTask(card);
        return kanbanRepository.save(project);
    }

    protected Card saveSingleTask(){
        return taskRepository.save(createSingleTask());
    }

    protected Optional<Project> findKanbanInDbById(Long id) {
        return kanbanRepository.findById(id);
    }

    protected Optional<Card> findTaskInDbById(Long id) {
        return taskRepository.findById(id);
    }
}
