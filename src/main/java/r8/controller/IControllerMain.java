package r8.controller;

import javafx.collections.ObservableList;
import r8.model.*;
import r8.model.dao.*;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.view.IViewController;

import java.util.List;
import java.util.Set;

public interface IControllerMain {
    
    IViewController getActiveViewController();

    void setActiveViewController(IViewController viewController);

    List<Account> getAllAccounts();

    List<Team> getAllTeams();

    List<Project> loadProjects(Account account);

    List<TaskType> getAllTaskTypes();

    void createTaskType(String name);

    void updateTask(Task task);

    void createTask(String name, TaskState ts, TaskType tt, float hours, String description, Set<Account> accounts, Set<Team> teams, Project project);

    void createProject(String name, String description, ObservableList<Account> accountList, ObservableList<String> teamList, ObservableList<Sprint> items);

    Project getProjectById(int projectId);

    List<Project> getProjects();

    List<Sprint> getAllSprints();

    void createComment(Comment comment);

    List<Comment> getComments(Task task);

    EventDAO getEventDAO();

    TaskDAO getTaskDAO();

    ProjectDAO getProjectDAO();

    SprintDAO getSprintDAO();

    TeamDAO getTeamDAO();
}
