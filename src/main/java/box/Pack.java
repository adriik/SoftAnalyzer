package box;

import java.util.ArrayList;

/**
 * Klasa enkapsulujacam, zawierajaca w sobie zbior wszystkich przeslanych projektow
 */

public class Pack {

	private ArrayList<Project> projectsList = new ArrayList<Project>();
	
	/**
	 * Metoda getProject() pozwala na pobranie projektu ze zbioru wszystkich przeslanych projektow
	 * @param nazwa - parametrem wejœciowym jest nazwa danego projektu
	 * @return metoda zwraca obiekt typu Projekt
	 */
	public Project getProject(String nazwa) {
		for (Project project : projectsList) {
			if(project.getNazwa().equals(nazwa)) {
				return project;
			}
		}
		return null;
	}
	
	/**
	 * Metoda addProject() pozwala na dodanie projektu ze zbioru wszystkich przeslanych projektow
	 * @param project - obiekt klasy Projekt, ktory chcemy dodac
	 */

	public void addProject(Project project) {
		projectsList.add(project);
	}
	
	/**
	 * Metoda removeProject() pozwala na usunac projekt ze zbioru wszystkich przeslanych projektow
	 * @param nazwa - parametrem wejsciowym jest nazwa danego projektu
	 */
	
	public void removeProject(String nazwa) {
		for (Project project : projectsList) {
			if(project.getNazwa().equals(nazwa)) {
				projectsList.remove(projectsList.indexOf(project));
			}
		}
	}
}
