package hu.bme.archi;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;

import hu.bme.archi.issue.domain.Issue;
import hu.bme.archi.issue.domain.Project;
import hu.bme.archi.issue.repository.IssueRepository;
import hu.bme.archi.issue.repository.ProjectRepository;
import hu.bme.archi.user.domain.User;
import hu.bme.archi.user.repository.UserRepository;

public class IssueTrackerPermissionEvaluator extends DenyAllPermissionEvaluator {

	@Autowired private ProjectRepository projectRepository;
	@Autowired private IssueRepository issueRepository;
	@Autowired private UserRepository userRepository;
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {

		if(Project.class.getName().equals(targetType) && "assigned".equals(permission)) {
			Project project = projectRepository.findOne((Long)targetId);
			User user = userRepository.findByEmail(authentication.getName());
			
			if("admin".equals(user.getRole().getRoleName()) || project.getUsers().contains(user)) {
				return true;
			}
		} else if(Issue.class.getName().equals(targetType) && "assigned".equals(permission)) {
			Issue issue = issueRepository.findOne((Long)targetId);
			User user = userRepository.findByEmail(authentication.getName());
			
			if("admin".equals(user.getRole().getRoleName()) || issue.getProject().getUsers().contains(user)) {
				return true;
			}
		}
		
		return super.hasPermission(authentication, targetId, targetType, permission);
	}
}
