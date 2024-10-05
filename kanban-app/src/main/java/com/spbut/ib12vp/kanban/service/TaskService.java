package com.spbut.ib12vp.kanban.service;

import com.spbut.ib12vp.kanban.model.Card;
import com.spbut.ib12vp.kanban.model.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Card> getAllTasks();

    Optional<Card> getTaskById(Long id);

    Optional<Card> getTaskByName(String title);

    Card saveNewTask(TaskDTO taskDTO);

    Card updateTask(Card oldCard, TaskDTO newTaskDTO);

    void deleteTask(Card card);
}
