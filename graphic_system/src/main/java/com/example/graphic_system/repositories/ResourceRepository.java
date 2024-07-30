package com.example.graphic_system.repositories;

import com.example.graphic_system.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByTaskId(Long taskId);
}
