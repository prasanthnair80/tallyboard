package com.phonex.tallyboard.service;

import com.phonex.tallyboard.model.Project;
import com.phonex.tallyboard.store.InMemoryStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final InMemoryStore store;

    public ProjectService(InMemoryStore store) {
        this.store = store;
    }

    public List<Project> getAll() {
        return store.getProjects();
    }

    public Optional<Project> findById(String id) {
        return store.findProjectById(id);
    }
}
