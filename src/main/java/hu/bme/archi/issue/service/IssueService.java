package hu.bme.archi.issue.service;

import java.util.List;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import hu.bme.archi.issue.bean.AssignUserToIssueRequest;
import hu.bme.archi.issue.bean.Comment;
import hu.bme.archi.issue.bean.ConstantsResponse;
import hu.bme.archi.issue.bean.CreateNewProjectRequest;
import hu.bme.archi.issue.bean.ListIssuesData;
import hu.bme.archi.issue.bean.ListIssuesResponse;
import hu.bme.archi.issue.bean.ListProjectsData;
import hu.bme.archi.issue.bean.SaveIssueRequest;
import hu.bme.archi.issue.bean.SendCommentRequest;
import hu.bme.archi.issue.bean.User;

@PreAuthorize("denyAll()")
public interface IssueService {

	@PreAuthorize("hasAuthority('LOGIN')")
	@PostFilter("hasPermission(filterObject.id, 'hu.bme.archi.issue.domain.Project', 'assigned')")
	List<ListProjectsData> listProjects();

	@PreAuthorize("hasPermission(#projectId, 'hu.bme.archi.issue.domain.Project', 'assigned')")
	ListIssuesResponse listIssues(long projectId);

	@PreAuthorize("hasAuthority('CREATE_PROJECT')")
	void createNewProject(CreateNewProjectRequest createNewProjectRequest);
	
	@PreAuthorize("hasAuthority('CREATE_ISSUE')")
	void createIssue(long projectId,  SaveIssueRequest saveIssueRequest);
	
	@PreAuthorize("hasAuthority('MODIFY_ISSUE')")
	void saveIssue(long projectId, long issueId,  SaveIssueRequest saveIssueRequest);
	
	@PreAuthorize("hasAuthority('DELETE_ISSUE')")
	void deleteIssue(long issueId);	
	
	@PreAuthorize("hasAuthority('COMMENT_ISSUE') && hasPermission(#sendCommentRequest.issueId, 'hu.bme.archi.issue.domain.Issue', 'assigned')")
	List<Comment> sendComment(SendCommentRequest sendCommentRequest);
	
	@PreAuthorize("hasPermission(#issueId, 'hu.bme.archi.issue.domain.Issue', 'assigned')")
	ListIssuesData getIssueById(long issueId);

	@PreAuthorize("hasAuthority('CREATE_ISSUE') || hasAuthority('MODIFY_ISSUE')")
	ConstantsResponse getConstants();
	
	@PreAuthorize("hasPermission(#projectId, 'hu.bme.archi.issue.domain.Project', 'assigned')")
	List<User> listAssignableUsers(long projectId);

	@PreAuthorize("hasAuthority('MODIFY_ISSUE')")
	void assignUserToIssue(AssignUserToIssueRequest assignUserToIssueRequest);
}
