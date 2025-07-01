package phi.timer.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private TaskRepository taskRepository;
	
	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public void createTask(Task task) {
		taskRepository.save(task);
	}

	public void saveTask(Task task) {
		taskRepository.save(task);
	}

	public void deleteTask(UUID id) {
		taskRepository.deleteById(id);
	}
	
	public List<Task> listAllTasks() {
		return taskRepository.findAll();
	}

}
