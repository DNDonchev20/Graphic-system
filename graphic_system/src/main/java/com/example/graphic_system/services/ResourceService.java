package com.example.graphic_system.services;

import com.example.graphic_system.models.Resource;
import com.example.graphic_system.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public List<Resource> getResourcesByTaskId(Long taskId) {
        return resourceRepository.findByTaskId(taskId);
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long id, Resource resourceDetails) {
        Resource resource = resourceRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));

        resource.setName(resourceDetails.getName());
        resource.setType(resourceDetails.getType());
        resource.setDescription(resourceDetails.getDescription());
        resource.setTask(resourceDetails.getTask());

        return resourceRepository.save(resource);
    }

    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }
}
