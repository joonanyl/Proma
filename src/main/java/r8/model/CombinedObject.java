package r8.model;

/**
 * An object containing either an account or a team object
 */
public class CombinedObject {
    private Account account;
    private Team team;
    //true if account, false if team
    private boolean accountCheck;

    public CombinedObject(Account account){
        this.account = account;
        this.team = null;
        accountCheck = true;
    }

    public CombinedObject(Team team){
        this.team = team;
        this.account = null;
        accountCheck = false;
    }

    public boolean checkIfAccount(){
        return accountCheck;
    }

    @Override
    public String toString(){
        if(!accountCheck){
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
