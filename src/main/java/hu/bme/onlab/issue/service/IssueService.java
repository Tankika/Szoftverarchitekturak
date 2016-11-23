package hu.bme.onlab.issue.service;

import hu.bme.onlab.issue.bean.ListIssuesResponse;
import hu.bme.onlab.issue.bean.ListProjectsResponse;

public interface IssueService {

	ListProjectsResponse listProjects();
	
	ListIssuesResponse listIssues(long projectId);
}
