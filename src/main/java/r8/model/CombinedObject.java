package r8.model;

/**
 * An object containing either an account or a team object
 */
public class CombinedObject {
    private Account account;
    private Team team;
    //true if account, false if team
    private boolean accountCheck;

    /**
     * Create a combined object containing an account object
     * @param account
     */
    public CombinedObject(Account account){
        this.account = account;
        this.team = null;
        accountCheck = true;
    }

    /**
     * Create a combined object containing a team object
     * @param team
     */
    public CombinedObject(Team team){
        this.team = team;
        this.account = null;
        accountCheck = false;
    }

    /**
     *
     * @return returns true if combinedObject is an account, false if it is a team
     */
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
