package hu.bme.onlab.issue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.onlab.issue.bean.Comment;
import hu.bme.onlab.issue.bean.CreateNewProjectRequest;
import hu.bme.onlab.issue.bean.ListIssuesData;
import hu.bme.onlab.issue.bean.ListIssuesResponse;
import hu.bme.onlab.issue.bean.ListProjectsData;
import hu.bme.onlab.issue.bean.ListProjectsResponse;
import hu.bme.onlab.issue.domain.Project;
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
			ListIssuesData listIssuesData = new ListIssuesData();
			listIssuesData.setName(i.getName());
			listIssuesData.setDescription(i.getDescription());
			listIssuesData.setReproductionSteps(i.getReproductionSteps());
			listIssuesData.setVersion(i.getVersion());
			listIssuesData.setType(i.getType());
			listIssuesData.setStatus(i.getStatus());
			listIssuesData.setPriority(i.getPriority());
			listIssuesData.setSeverity(i.getSeverity());
			i.getComments().forEach(c -> {
				Comment comment = new Comment();
				comment.setMessage(c.getMessage());
				comment.setTimeStamp(c.getTimeStamp());
			});
			
			listIssuesResponse.getIssues().add(listIssuesData);
		});
		
		return listIssuesResponse;
	}

	@Override
	public void createNewProject(CreateNewProjectRequest createNewProjectRequest) {
		Project project = new Project();
		project.setName(createNewProjectRequest.getName());
		projectRepository.save(project);
	}

}
