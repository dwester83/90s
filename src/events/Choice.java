package events;

public class Choice {
	String choice;
	int eventNumber;
	/**
	 * A Choice is an option in a dialog
	 * @param choice
	 * @param eventKey
	 */
	public Choice(String choice, int eventNumber){
		this.choice = choice;
		this.eventNumber = eventNumber;
	}
}
