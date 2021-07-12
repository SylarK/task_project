package br.com.esig.project.amado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.esig.project.amado.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	
}
