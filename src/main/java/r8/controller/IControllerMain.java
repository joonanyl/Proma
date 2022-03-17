package r8.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.view.IViewController;

import java.util.List;

public interface IControllerMain {
    
    IViewController getActiveViewController();

    void setActiveViewController(IViewController viewController);

    List<Account> getAllAccounts();

    List<Team> getAllTeams();

    List<Project> loadProjects(Account account);

    List<TaskType> getAllTaskTypes();

    void createTaskType(String name);

    void updateTask(Task task);

    void createTask(String name, TaskState ts, TaskType tt, float hours, String description, ObservableList<Account> accounts, ObservableList<Team> teams, Project project);

    void createProject(String name, String description, ObservableList<Account> accountList, ObservableList<String> teamList);

}
