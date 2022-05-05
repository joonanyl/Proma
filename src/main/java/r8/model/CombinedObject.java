package r8.model;

/**
 * An object containing either an account or a team object
 * @author Teemu Tallskog
 */
public class CombinedObject {
    private final Object object;
    //true if account, false if team
    private final boolean accountCheck;

    /**
     * Create a combined object containing an account object
     * @param account
     */
    public CombinedObject(Account account){
        this.object = account;
        accountCheck = true;
    }

    /**
     * Create a combined object containing a team object
     * @param team
     */
    public CombinedObject(Team team){
        this.object = team;
        accountCheck = false;
    }

    /**
     *
     * @return returns true if combinedObject is an account, false if it is a team
     */
    public boolean isAccount(){
        return accountCheck;
    }

    @Override
    public String toString(){
        return object.toString();
    }

    /**
     *
     * @return returns an account if this object is an account, null if it's not.
     */
    public Account getAccount(){
        try {
            return (Account) object;
        }catch (ClassCastException ignore){
            return null;
        }
    }

    /**
     *
     * @return returns a team object if this object is a team, null if it's not
     */
    public Team getTeam(){
        try {
            return (Team) object;
        }catch (ClassCastException ignore){
            return null;
        }
    }

}
