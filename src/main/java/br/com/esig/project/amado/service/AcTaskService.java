package br.com.esig.project.amado.service;

import br.com.esig.project.amado.model.Task;
import br.com.esig.project.amado.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AcTaskService implements TaskService {

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public List<Task> findAll() {
        return taskRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllWithoutIp() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllWithIp() {
        return null;
    }

    @Override
    public List<Task> findDone() {
        return taskRepository.findAll().stream().filter(task -> task.getSituacao().equalsIgnoreCase("concluido")).collect(Collectors.toList());
    }

    @Override
    public List<Task> findPending() {
        return taskRepository.findAll().stream().filter(task -> task.getSituacao().equalsIgnoreCase("em andamento")).collect(Collectors.toList());
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(Long.valueOf(id));
    }



}
