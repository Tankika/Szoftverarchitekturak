package hu.bme.onlab.issue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.onlab.issue.bean.ListProjectsResponse;
import hu.bme.onlab.issue.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

	private ProjectService projectService;
	
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@RequestMapping(path = "/listProjects", method = RequestMethod.GET)
	public ListProjectsResponse listProjects() {
		return projectService.listProjects();
	}
	
}
