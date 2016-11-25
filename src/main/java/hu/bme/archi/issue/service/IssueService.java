package hu.bme.archi.issue.service;

import java.util.List;

import hu.bme.archi.issue.bean.Comment;
import hu.bme.archi.issue.bean.ConstantsResponse;
import hu.bme.archi.issue.bean.CreateNewProjectRequest;
import hu.bme.archi.issue.bean.ListIssuesData;
import hu.bme.archi.issue.bean.ListIssuesResponse;
import hu.bme.archi.issue.bean.ListProjectsResponse;
import hu.bme.archi.issue.bean.SendCommentRequest;

public interface IssueService {

	ListProjectsResponse listProjects();
	
	ListIssuesResponse listIssues(long projectId);

	void createNewProject(CreateNewProjectRequest createNewProjectRequest);
	
	List<Comment> sendComment(SendCommentRequest sendCommentRequest);
	
	ListIssuesData getIssueById(long issueId);
	
	ConstantsResponse getConstants();
}
