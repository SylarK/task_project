package br.com.esig.project.amado.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.esig.project.amado.exception.ResourceNotFoundException;
import br.com.esig.project.amado.model.Task;
import br.com.esig.project.amado.repository.TaskRepository;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/")
public class TaskController {

	@Autowired
	private TaskRepository taskrepository;

	//get task
	@GetMapping("task")
	public List<Task> getAllTask(){
		return this.taskrepository.findAll();
	}
	
	
	//get task id
	@GetMapping("/task/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId)
		throws ResourceNotFoundException{
		
		Task task = taskrepository.findById(taskId)
					.orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
					return ResponseEntity.ok().body(task);
	}
	
	//save
	@PostMapping("task")
	public Task createTask(@RequestBody Task task) {
		return this.taskrepository.save(task);
	}
	
	//update
	@PutMapping("/task/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId, 
			@Validated @RequestBody Task taskDetails) throws ResourceNotFoundException{
		Task task = taskrepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
		
		task.setTitulo(taskDetails.getTitulo());
		task.setDescricao(taskDetails.getDescricao());
		task.setPrioridade(taskDetails.getPrioridade());
		task.setResponsavel(taskDetails.getResponsavel());
		task.setSituacao(taskDetails.getSituacao());
		task.setDate(taskDetails.getDate());
		
		return ResponseEntity.ok(this.taskrepository.save(task));
	}
	
	//delete task
	@DeleteMapping("task/{id}")
	public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException{
		Task task = taskrepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
		
		this.taskrepository.delete(task);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return response;

	}

}
