package r8.model;

import java.util.List;

public class CombinedList {
    private Account account;
    private Team team;

    public CombinedList(Account account, Team team){
        this.account = account;
        this.team = team;
    }

    public boolean checkIfAccount(){
        if(team == null){
            return true;
        }else return false;
    }

    @Override
    public String toString(){
        if(account == null){
            return team.toString();
        }else return account.toString();
    }

    public Account getAccount(){
        return account;
    }

    public Team getTeam(){
        return team;
    }

}
