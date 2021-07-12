package br.com.esig.project.amado.service;

import br.com.esig.project.amado.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();
    List<Task> findAllWithoutIp();
    List<Task> findAllWithIp();
    List<Task> findDone();
    List<Task> findPending();

    void save(Task task);

    void delete(Long id);
}
