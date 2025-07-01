package phi.timer.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/task")
public class TaskController {

	private TaskService taskService;
	
	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping
	public void createTask(@RequestBody Task task) {
		taskService.createTask(task);
	}

	@PutMapping
	public void updateTask(@RequestBody Task task) {
		taskService.saveTask(task);
	}
	
	@GetMapping
	public List<Task> listAllTasks() {
		return taskService.listAllTasks();
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteTask(@PathVariable("id") UUID id) {
		taskService.deleteTask(id);
	}
	
}
