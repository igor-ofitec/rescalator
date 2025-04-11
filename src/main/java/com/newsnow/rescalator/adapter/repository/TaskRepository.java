package com.newsnow.rescalator.adapter.repository;

import com.newsnow.rescalator.core.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

