package hu.bme.onlab.issue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.onlab.issue.bean.Comment;
import hu.bme.onlab.issue.bean.ConstantsResponse;
import hu.bme.onlab.issue.bean.CreateNewProjectRequest;
import hu.bme.onlab.issue.bean.ListIssuesData;
import hu.bme.onlab.issue.bean.ListIssuesResponse;
import hu.bme.onlab.issue.bean.ListProjectsData;
import hu.bme.onlab.issue.bean.ListProjectsResponse;
import hu.bme.onlab.issue.domain.Issue;
import hu.bme.onlab.issue.domain.Priority;
import hu.bme.onlab.issue.domain.Project;
import hu.bme.onlab.issue.domain.Severity;
import hu.bme.onlab.issue.domain.Status;
import hu.bme.onlab.issue.domain.Type;
import hu.bme.onlab.issue.repository.IssueRepository;
import hu.bme.onlab.issue.repository.ProjectRepository;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

	private ProjectRepository projectRepository;
	private IssueRepository issueRepository;

	@Autowired
	public IssueServiceImpl(ProjectRepository projectRepository, IssueRepository issueRepository) {
		this.projectRepository = projectRepository;
		this.issueRepository = issueRepository;
	}
	
	@Override
	public ListProjectsResponse listProjects() {
		// TODO: itt csak azokat kellene visszaadni, amikhez az aktuális usernek van joga.
		ListProjectsResponse response = new ListProjectsResponse();
		
		projectRepository.findAll().forEach(p -> {
			ListProjectsData listProjectsData = new ListProjectsData();
			listProjectsData.setId(p.getId());
			listProjectsData.setName(p.getName());
			
			response.getProjects().add(listProjectsData);
		});
		
		return response;
	}
	
	

	@Override
	public ListIssuesResponse listIssues(long projectId) {
		// TODO: figyelni kellene a projectId-ra, meg kell nézni, hogy az aktuális user, hozzá van-e rendelve a projekthez, amelynek az issue-it listázni akarjuk.
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
		projectRepository.save(project);
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
		listIssuesData.setComment(mapCommentDataToBean(issue.getComments()));
		return listIssuesData;
	}
	
	private List<Comment> mapCommentDataToBean(Set<hu.bme.onlab.issue.domain.Comment> comments) {
		List<Comment> commentList = new ArrayList<>();
		comments.forEach(c -> {
			Comment comment = new Comment();
			comment.setMessage(c.getMessage());
			comment.setTimeStamp(c.getTimeStamp());
			commentList.add(comment);
		});
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
