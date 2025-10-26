//**************************************************************************************************
package phi.devmate.project;
//**************************************************************************************************

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//**************************************************************************************************
@RestController
@RequestMapping(path="/api/project")
public class ProjectController {

	//==============================================================================================
	private ProjectService projectService;
	//==============================================================================================

	//==============================================================================================
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	//==============================================================================================
	
	//==============================================================================================
	@GetMapping
	public List<Project> getProjects() {
		return projectService.getProjects();
	}
	//==============================================================================================

}
//**************************************************************************************************
