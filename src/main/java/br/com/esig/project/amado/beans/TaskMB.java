package br.com.esig.project.amado.beans;

import br.com.esig.project.amado.model.Task;
import br.com.esig.project.amado.repository.TaskRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "alunoMB")
@ManagedBean
@ViewScoped
public class TaskMB implements Serializable {

    @Getter
    @Setter
    private List<Task> task = new ArrayList<>();

    @Getter
    @Setter
    private Task aluno;

    @Autowired
    private TaskRepository taskRepository;

    @PostConstruct
    public List<Task> listarTodos() {
        task = taskRepository.findAll();
        return task;
    }


}

