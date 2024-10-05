package com.spbut.ib12vp.kanban.service;

import com.spbut.ib12vp.kanban.model.KanbanDTO;
import com.spbut.ib12vp.kanban.model.Project;
import com.spbut.ib12vp.kanban.model.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface KanbanService {

    List<Project> getAllKanbanBoards();

    Optional<Project> getKanbanById(Long id);

    Optional<Project> getKanbanByName(String title);

    Project saveNewKanban(KanbanDTO kanbanDTO);

    Project updateKanban(Project oldProject, KanbanDTO newKanbanDTO);

    void deleteKanban(Project project);

    Project addNewTaskToKanban(Long kanbanId, TaskDTO taskDTO);
}
