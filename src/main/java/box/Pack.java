package box;

import java.util.ArrayList;

public class Pack {

	private ArrayList<Project> projectsList = new ArrayList<Project>();
	
	public Project getProject(String nazwa) {
		for (Project project : projectsList) {
			if(project.getNazwa().equals(nazwa)) {
				return project;
			}
		}
		return null;
	}

	public void addProject(Project project) {
		projectsList.add(project);
	}
	
	public void removeProject(String nazwa) {
		for (Project project : projectsList) {
			if(project.getNazwa().equals(nazwa)) {
				projectsList.remove(projectsList.indexOf(project));
			}
		}
	}
}
