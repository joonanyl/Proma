package r8.model;


import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

public class ModelTest {

    public static void main(String args[]) {
        Project project = new Project("Testiprojecti", "1000€");
        Team team = new Team("Team 1");
        Account account = new Account("Testi", "Käyttäjä", "0401234567", "testi@testi.com");

        team.addMember(account);
        System.out.println(team.getMembers());
        project.addTeam(team);
        System.out.println("Projekti: " + project.getTeamsList());

        Task task = new Task("Testitask", TaskState.NOT_STARTED, TaskType.MEETING);
        task.assignToTeam(team.getTeamId());
        // Tämän jälkeen hibernatella yhteydet kuntoon
    }
}
