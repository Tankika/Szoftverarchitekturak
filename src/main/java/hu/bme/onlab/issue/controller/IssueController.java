package hu.bme.onlab.issue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.onlab.issue.bean.ListIssuesResponse;
import hu.bme.onlab.issue.bean.ListProjectsResponse;
import hu.bme.onlab.issue.service.IssueService;

@RestController
@RequestMapping("/issue")
public class IssueController {

	private IssueService issueService;
	
	@Autowired
	public IssueController(IssueService projectService) {
		this.issueService = projectService;
	}
	
	@RequestMapping(path = "/listProjects", method = RequestMethod.GET)
	public ListProjectsResponse listProjects() {
		return issueService.listProjects();
	}
	
	@RequestMapping(path = "/listIssues/{projectId}", method = RequestMethod.GET)
	public ListIssuesResponse listIssues(@PathVariable long projectId) {
		return issueService.listIssues(projectId);
	}
	
}
