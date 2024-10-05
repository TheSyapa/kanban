package com.spbut.ib12vp.kanban.service;

import com.spbut.ib12vp.kanban.model.Card;
import com.spbut.ib12vp.kanban.model.TaskDTO;
import com.spbut.ib12vp.kanban.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public List<Card> getAllTasks() {
        List<Card> tasksList = new ArrayList<>();
        taskRepository.findAll().forEach(tasksList::add);
        return tasksList;
    }

    @Override
    @Transactional
    public Optional<Card> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Card> getTaskByName(String title) {
        return taskRepository.findByName(title);
    }


    @Override
    @Transactional
    public Card saveNewTask(TaskDTO taskDTO) {
        return taskRepository.save(convertDTOToTask(taskDTO));
    }

    @Override
    @Transactional
    public Card updateTask(Card oldCard, TaskDTO newTaskDTO) {
        return taskRepository.save(updateTaskFromDTO(oldCard, newTaskDTO));
    }

    @Override
    @Transactional
    public void deleteTask(Card card) {
        taskRepository.delete(card);
    }

    private Card convertDTOToTask(TaskDTO taskDTO) {
        Card card = new Card();
        card.setName(taskDTO.getName());
        card.setDescription(taskDTO.getDescription());
        card.setColor(taskDTO.getColor());
        card.setStatus(taskDTO.getStatus());
        return card;
    }

    private Card updateTaskFromDTO(Card card, TaskDTO taskDTO){
        if(Optional.ofNullable(taskDTO.getName()).isPresent()){
            card.setName(taskDTO.getName());
        }

        if (Optional.ofNullable((taskDTO.getDescription())).isPresent()) {
            card.setDescription(taskDTO.getDescription());
        }

        if (Optional.ofNullable((taskDTO.getColor())).isPresent()) {
            card.setColor(taskDTO.getColor());
        }

        if (Optional.ofNullable((taskDTO.getStatus())).isPresent()) {
            card.setStatus(taskDTO.getStatus());
        }
        return card;
    }
}
