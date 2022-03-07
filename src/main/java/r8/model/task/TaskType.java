package r8.model.task;

/**
 * 
 * @author sanku
 *
 */
public enum TaskType {
	BUG ("Bug"),
	UI ("UI"),
	MEETING ("Meeting"),
	USER_DEFINED ("User defined");

	private final String name;

	private TaskType (String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return name;
	}
}
