package com.spbut.ib12vp.kanban.repository;

import com.spbut.ib12vp.kanban.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KanbanRepository extends CrudRepository<Project, Long> {

    Optional<Project> findByName(String title);
}
