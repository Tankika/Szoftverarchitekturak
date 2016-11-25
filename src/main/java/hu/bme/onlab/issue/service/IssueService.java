package hu.bme.onlab.issue.service;

import java.util.List;

import hu.bme.onlab.issue.bean.Comment;
import hu.bme.onlab.issue.bean.ConstantsResponse;
import hu.bme.onlab.issue.bean.CreateNewProjectRequest;
import hu.bme.onlab.issue.bean.ListIssuesData;
import hu.bme.onlab.issue.bean.ListIssuesResponse;
import hu.bme.onlab.issue.bean.ListProjectsResponse;
import hu.bme.onlab.issue.bean.SendCommentRequest;

public interface IssueService {

	ListProjectsResponse listProjects();
	
	ListIssuesResponse listIssues(long projectId);

	void createNewProject(CreateNewProjectRequest createNewProjectRequest);
	
	List<Comment> sendComment(SendCommentRequest sendCommentRequest);
	
	ListIssuesData getIssueById(long issueId);
	
	ConstantsResponse getConstants();
}
