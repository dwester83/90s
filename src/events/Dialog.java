package events;

public class Dialog {

	String dialog;
	int[] choices;
	/**
	 * 
	 * @param dialog
	 * @param choices
	 */
	public Dialog(String dialog, int[] choices){
		this.dialog = dialog;
		this.choices = choices;
	}

	/**
	 * @return the dialog
	 */
	public String getDialog() {
		return dialog;
	}

	/**
	 * @return the choices
	 */
	public int[] getChoices() {
		return choices;
	}

}
