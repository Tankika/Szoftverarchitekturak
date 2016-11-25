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

import hu.bme.archi.exception.InsufficientAuthoritesException;
import hu.bme.archi.issue.bean.AssignUserToIssueRequest;
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
import hu.bme.archi.user.domain.Authority;
import hu.bme.archi.user.domain.User;
import hu.bme.archi.user.repository.AuthorityRepository;
import hu.bme.archi.user.repository.UserRepository;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

	private ProjectRepository projectRepository;
	private IssueRepository issueRepository;
	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;

	@Autowired
	public IssueServiceImpl(ProjectRepository projectRepository, IssueRepository issueRepository, CommentRepository commentRepository, UserRepository userRepository, AuthorityRepository authorityRepository) {
		this.projectRepository = projectRepository;
		this.issueRepository = issueRepository;
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
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
		User user = getLoggedInUser();
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
		listIssuesData.setAssignee(mapUserDataToBean(issue.getAssignee()));
		listIssuesData.setComment(mapCommentDataToBean(issue.getComments()));
		return listIssuesData;
	}
	
	private List<hu.bme.archi.issue.bean.Comment> mapCommentDataToBean(Set<Comment> comments) {
		List<hu.bme.archi.issue.bean.Comment> commentList = new ArrayList<>();
		comments.forEach(c -> {
			hu.bme.archi.issue.bean.Comment comment = new hu.bme.archi.issue.bean.Comment();
			comment.setMessage(c.getMessage());
			comment.setTimeStamp(c.getTimeStamp());
			comment.setAuthor(mapUserDataToBean(c.getAuthor()));
			commentList.add(comment);
		});
		commentList.sort((c1, c2) -> c1.getTimeStamp().compareTo(c2.getTimeStamp()));
		return commentList;
	}
	
	private hu.bme.archi.issue.bean.User mapUserDataToBean(User src) {
		hu.bme.archi.issue.bean.User trg = new hu.bme.archi.issue.bean.User();
		trg.setId(src.getId());
		trg.setEmail(src.getEmail());
		trg.setRole(src.getRole().getRoleName());
		return trg;
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
	
	@Override
	public List<hu.bme.archi.issue.bean.User> listAssignableUsers(long projectId) {
		List<hu.bme.archi.issue.bean.User> userList = new ArrayList<hu.bme.archi.issue.bean.User>();
		userRepository.findByProjectsId(projectId).forEach(u -> {
			userList.add(mapUserDataToBean(u));
		});
		
		return userList;
	}

	@Override
	public void assignUserToIssue(AssignUserToIssueRequest assignUserToIssueRequest) {
		if(!userHasAuthority(getLoggedInUser(), "MODIFY_ISSUE")) {
			throw new InsufficientAuthoritesException();
		}
		
		Issue issue = issueRepository.findOne(assignUserToIssueRequest.getIssueId());
		User user = userRepository.findOne(assignUserToIssueRequest.getUserId());

		List<User> assignableUsers = userRepository.findByProjectsId(issue.getProject().getId());
		if(assignableUsers.contains(user)) {
			issue.getAssignee().getIssues().remove(issue);
			issue.setAssignee(user);
		}
	}

	private User getLoggedInUser() {
		UserDetails loggedInPrincipal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmailIgnoreCase(loggedInPrincipal.getUsername()).get(0);
		return user;
	}
	
	private boolean userHasAuthority(User user, String authority) {
		return user.getRole().getAuthorities().contains(authorityRepository.findByAuthorityIgnoreCase(authority));
	}

}
