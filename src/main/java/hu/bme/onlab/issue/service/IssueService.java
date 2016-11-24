package hu.bme.onlab.issue.service;

import hu.bme.onlab.issue.bean.ConstantsResponse;
import hu.bme.onlab.issue.bean.CreateNewProjectRequest;
import hu.bme.onlab.issue.bean.ListIssuesData;
import hu.bme.onlab.issue.bean.ListIssuesResponse;
import hu.bme.onlab.issue.bean.ListProjectsResponse;

public interface IssueService {

	ListProjectsResponse listProjects();
	
	ListIssuesResponse listIssues(long projectId);

	void createNewProject(CreateNewProjectRequest createNewProjectRequest);
	
	ListIssuesData getIssueById(long issueId);
	
	ConstantsResponse getConstants();
}
