package com.spbut.ib12vp.kanban.service;

import com.spbut.ib12vp.kanban.model.Card;
import com.spbut.ib12vp.kanban.model.KanbanDTO;
import com.spbut.ib12vp.kanban.model.Project;
import com.spbut.ib12vp.kanban.model.TaskDTO;
import com.spbut.ib12vp.kanban.repository.KanbanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KanbanServiceImpl implements KanbanService {

    private final KanbanRepository kanbanRepository;

    @Override
    @Transactional
    public List<Project> getAllKanbanBoards() {
        List<Project> projectList = new ArrayList<>();
        kanbanRepository.findAll().forEach(projectList::add);
        return projectList;
    }

    @Override
    @Transactional
    public Optional<Project> getKanbanById(Long id) {
        return kanbanRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Project> getKanbanByName(String title) {
        return kanbanRepository.findByName(title);
    }

    @Override
    @Transactional
    public Project saveNewKanban(KanbanDTO kanbanDTO) {
        return kanbanRepository.save(convertDTOToKanban(kanbanDTO));
    }

    @Override
    @Transactional
    public Project updateKanban(Project oldProject, KanbanDTO newKanbanDTO) {
        oldProject.setName(newKanbanDTO.getName());
        return kanbanRepository.save(oldProject);
    }

    @Override
    @Transactional
    public void deleteKanban(Project project) {
        kanbanRepository.delete(project);
    }

    @Override
    @Transactional
    public Project addNewTaskToKanban(Long kanbanId, TaskDTO taskDTO) {
        Project project = kanbanRepository.findById(kanbanId).get();
        project.addTask(convertDTOToTask(taskDTO));
        return kanbanRepository.save(project);
    }

    private Project convertDTOToKanban(KanbanDTO kanbanDTO){
        Project project = new Project();
        project.setName(kanbanDTO.getName());
        return project;
    }

    private Card convertDTOToTask(TaskDTO taskDTO) {
        Card card = new Card();
        card.setName(taskDTO.getName());
        card.setDescription(taskDTO.getDescription());
        card.setColor(taskDTO.getColor());
        card.setStatus(taskDTO.getStatus());
        return card;
    }
}
