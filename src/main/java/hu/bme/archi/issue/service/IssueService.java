package hu.bme.archi.issue.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import hu.bme.archi.issue.bean.AssignUserToIssueRequest;
import hu.bme.archi.issue.bean.Comment;
import hu.bme.archi.issue.bean.ConstantsResponse;
import hu.bme.archi.issue.bean.CreateNewProjectRequest;
import hu.bme.archi.issue.bean.ListIssuesData;
import hu.bme.archi.issue.bean.ListIssuesResponse;
import hu.bme.archi.issue.bean.ListProjectsResponse;
import hu.bme.archi.issue.bean.SaveIssueRequest;
import hu.bme.archi.issue.bean.SendCommentRequest;
import hu.bme.archi.issue.bean.User;

public interface IssueService {

	ListProjectsResponse listProjects();
	
	ListIssuesResponse listIssues(long projectId);

	@PreAuthorize("hasAuthority('CREATE_PROJECT')")
	void createNewProject(CreateNewProjectRequest createNewProjectRequest);
	
	@PreAuthorize("hasAuthority('CREATE_ISSUE')")
	void saveIssue(long projectId,  SaveIssueRequest saveIssueRequest);
	
	@PreAuthorize("hasAuthority('MODIFY_ISSUE')")
	void saveIssue(long projectId, long issueId,  SaveIssueRequest saveIssueRequest);
	
	List<Comment> sendComment(SendCommentRequest sendCommentRequest);
	
	ListIssuesData getIssueById(long issueId);
	
	ConstantsResponse getConstants();
	
	List<User> listAssignableUsers(long projectId);

	@PreAuthorize("hasAuthority('MODIFY_ISSUE')")
	void assignUserToIssue(AssignUserToIssueRequest assignUserToIssueRequest);
}
