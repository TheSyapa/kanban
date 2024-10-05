package com.spbut.ib12vp.kanban.repository;

import com.spbut.ib12vp.kanban.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Card, Long> {

    Optional<Card> findByName(String title);
}
