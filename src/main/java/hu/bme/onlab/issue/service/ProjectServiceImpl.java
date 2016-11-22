package hu.bme.onlab.issue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.onlab.issue.bean.ListProjectsData;
import hu.bme.onlab.issue.bean.ListProjectsResponse;
import hu.bme.onlab.issue.repository.ProjectRepository;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	private ProjectRepository projectRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	@Override
	public ListProjectsResponse listProjects() {
		ListProjectsResponse response = new ListProjectsResponse();

		projectRepository.findAll().forEach(p -> {
			ListProjectsData listProjectsData = new ListProjectsData();
			listProjectsData.setId(p.getId());
			listProjectsData.setName(p.getName());
			
			response.getProjects().add(listProjectsData);
		});
		
		return response;
	}

}
