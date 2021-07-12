package br.com.esig.project.amado.controller;

import br.com.esig.project.amado.model.Task;
import br.com.esig.project.amado.service.FilterToTask;
import br.com.esig.project.amado.service.TaskService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Scope;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Component
@Getter
@Setter
//@Scope("view")
public class TaskControllerIndex {

    @Autowired
    private TaskService taskService;

    private Task task;

    private Task selectedTask;

    private List<Task> tasks;

    private long tarefaCount, doneTarefasCount, pendingTarefasCount;

    public String filter = "3";

    @PostConstruct
    public void init(){
        tasks = new ArrayList<>();
        task = new Task();
        loadData();
    }

    private void loadData() {
        System.out.println("VALOR : " +this.filter);
        if (Integer.parseInt(this.filter) == 1)
        {
            tasks = taskService.findPending();
        } else if (Integer.parseInt(this.filter) == 2) {
            tasks = taskService.findDone();
        } else if (Integer.parseInt(this.filter) == 3){
            tasks = taskService.findAll();
        }
    }

    public void changeFilter(){
        task = new Task();
       loadData();
    }


    public void delete() {
        taskService.delete(selectedTask.getId());
        loadData();
    }

    public void save() {
        taskService.save(selectedTask);
        selectedTask = null;
        loadData();
    }

    public void changeSituation() {
        if(selectedTask.getSituacao().equalsIgnoreCase("concluido")){
            selectedTask.setDone(false);
            selectedTask.setSituacao("Em andamento");
            taskService.save(selectedTask);
        }else{
            selectedTask.setDone(true);
            selectedTask.setSituacao("Concluido");
            taskService.save(selectedTask);
        }
        selectedTask = null;
        loadData();
    }
    public void create() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        task.setIp(ipAddress);
        task.setSituacao("em andamento");
        taskService.save(this.task);
        task = new Task();

        loadData();
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public void clearTask() {
        task = new Task();
    }

    public FilterToTask getDoneFilter() {
        return FilterToTask.DONE;
    }

    public FilterToTask getPendingFilter() {
        return FilterToTask.PENDING;
    }

    //


}
