package hu.bme.archi.issue.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.archi.issue.bean.ConstantsResponse;
import hu.bme.archi.issue.bean.CreateNewProjectRequest;
import hu.bme.archi.issue.bean.ListIssuesData;
import hu.bme.archi.issue.bean.ListIssuesResponse;
import hu.bme.archi.issue.bean.ListProjectsData;
import hu.bme.archi.issue.bean.ListProjectsResponse;
import hu.bme.archi.issue.bean.SendCommentRequest;
import hu.bme.archi.issue.domain.Comment;
import hu.bme.archi.issue.domain.Issue;
import hu.bme.archi.issue.domain.Priority;
import hu.bme.archi.issue.domain.Project;
import hu.bme.archi.issue.domain.Severity;
import hu.bme.archi.issue.domain.Status;
import hu.bme.archi.issue.domain.Type;
import hu.bme.archi.issue.repository.CommentRepository;
import hu.bme.archi.issue.repository.IssueRepository;
import hu.bme.archi.issue.repository.ProjectRepository;
import hu.bme.archi.user.domain.User;
import hu.bme.archi.user.repository.UserRepository;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

	private ProjectRepository projectRepository;
	private IssueRepository issueRepository;
	private CommentRepository commentRepository;
	private UserRepository userRepository;

	@Autowired
	public IssueServiceImpl(ProjectRepository projectRepository, IssueRepository issueRepository, CommentRepository commentRepository, UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.issueRepository = issueRepository;
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public ListProjectsResponse listProjects() {
		// TODO: itt csak azokat kellene visszaadni, amikhez az aktuális usernek van joga (kivéve ha admin, akkor mindet vissza kell adni).
		ListProjectsResponse response = new ListProjectsResponse();
		
		projectRepository.findAll().forEach(p -> {
			ListProjectsData listProjectsData = new ListProjectsData();
			listProjectsData.setId(p.getId());
			listProjectsData.setName(p.getName());
			listProjectsData.setDescription(p.getDescription());
			
			response.getProjects().add(listProjectsData);
		});
		
		return response;
	}
	
	

	@Override
	public ListIssuesResponse listIssues(long projectId) {
		// TODO: itt csak azokat kellene visszaadni, amikhez az aktuális usernek van joga (kivéve ha admin, akkor mindet vissza kell adni).
		ListIssuesResponse listIssuesResponse = new ListIssuesResponse();
		
		listIssuesResponse.setProjectName(projectRepository.findOne(projectId).getName());
				
		issueRepository.findByProjectId(projectId).forEach(i -> {
			listIssuesResponse.getIssues().add(mapIssueDataToBean(i));
		});
		
		return listIssuesResponse;
	}

	@Override
	public void createNewProject(CreateNewProjectRequest createNewProjectRequest) {
		Project project = new Project();
		project.setName(createNewProjectRequest.getName());
		project.setDescription(createNewProjectRequest.getDescription());
		projectRepository.save(project);
	}

	@Override
	public List<hu.bme.archi.issue.bean.Comment> sendComment(SendCommentRequest sendCommentRequest) {
		UserDetails loggedInPrincipal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmailIgnoreCase(loggedInPrincipal.getUsername()).get(0);
		Issue issue = issueRepository.findOne(sendCommentRequest.getIssueId());
		
		Comment comment = new Comment();
		comment.setMessage(sendCommentRequest.getMessage());
		comment.setTimeStamp(Calendar.getInstance());
		comment.setIssue(issue);
		comment.setAuthor(user);
		commentRepository.save(comment);
		
		return mapCommentDataToBean(issue.getComments());
	}

	@Override
	public ListIssuesData getIssueById(long issueId) {
		return mapIssueDataToBean(issueRepository.findOne(issueId));
	}
	
	private ListIssuesData mapIssueDataToBean(Issue issue) {
		ListIssuesData listIssuesData = new ListIssuesData();
		listIssuesData.setId(issue.getId());
		listIssuesData.setName(issue.getName());
		listIssuesData.setDescription(issue.getDescription());
		listIssuesData.setReproductionSteps(issue.getReproductionSteps());
		listIssuesData.setVersion(issue.getVersion());
		listIssuesData.setType(issue.getType());
		listIssuesData.setStatus(issue.getStatus());
		listIssuesData.setPriority(issue.getPriority());
		listIssuesData.setSeverity(issue.getSeverity());
		listIssuesData.setCreationTimeStamp(issue.getCreationTimeStamp());
		listIssuesData.setAssigneeEmail(issue.getAssignee().getEmail());
		listIssuesData.setAssigneeId(issue.getAssignee().getId());
		listIssuesData.setComment(mapCommentDataToBean(issue.getComments()));
		return listIssuesData;
	}
	
	private List<hu.bme.archi.issue.bean.Comment> mapCommentDataToBean(Set<Comment> comments) {
		List<hu.bme.archi.issue.bean.Comment> commentList = new ArrayList<>();
		comments.forEach(c -> {
			hu.bme.archi.issue.bean.Comment comment = new hu.bme.archi.issue.bean.Comment();
			comment.setMessage(c.getMessage());
			comment.setTimeStamp(c.getTimeStamp());
			comment.setAuthorEmail(c.getAuthor().getEmail());
			comment.setAuthorRole(c.getAuthor().getRole().getRoleName());
			commentList.add(comment);
		});
		commentList.sort((c1, c2) -> c1.getTimeStamp().compareTo(c2.getTimeStamp()));
		return commentList;
	}

	@Override
	public ConstantsResponse getConstants() {
		ConstantsResponse response = new ConstantsResponse();
		response.setTypes(Type.values());
		response.setPriorities(Priority.values());
		response.setSeverities(Severity.values());
		response.setStatuses(Status.values());
		return response;
	}

}
