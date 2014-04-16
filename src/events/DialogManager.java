package events;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Holds all the dialogs and tells which dialog is next
 * @author jchanke2607
 *
 */
public class DialogManager {
	private final int MAX_NUMBER_OF_CHOICES = 5;
	private HashMap<Integer, Choice> choices = new HashMap<Integer, Choice>();
	private HashMap<Integer, Dialog> dialogs = new HashMap<Integer, Dialog>();
	/**
	 * 
	 * @param csvFile
	 */
	public DialogManager(String dialogFile, String choiceFile){
		setDialogs(dialogFile);
		setChoices(choiceFile);
	}
	/**
	 * 
	 * @param eventNumber
	 */
	public void getNextDialog(int eventNumber) {
		//System.out.println(eventNumber);
		//System.out.println(dialogs.size());
		if(dialogs.containsKey(eventNumber)) displayDialog(eventNumber);
		else System.out.println("Dialog does not exist!");
	}
	/**
	 * 
	 * @param dialogKey
	 */
	private void displayDialog(int dialogKey){
		//System.out.println(dialogs.get(dialogKey).getDialog());
		//creates the menu and displays the dialog and choices whith it
		//code to display a dialog here
		//code to display the choices connected to the dialog here
	}
	/**
	 * 
	 * @param csvFile
	 */
	private void setDialogs(String csvFile){

		BufferedReader CSVFile;
		try {
			InputStream is = this.getClass().getResourceAsStream(csvFile);
			CSVFile = new BufferedReader(new InputStreamReader(is));
			String dataRow = CSVFile.readLine();
			dataRow = CSVFile.readLine();
			int key;
			String dialog;
			int[] choiceArray = new int[MAX_NUMBER_OF_CHOICES];


		while (dataRow != null)
		{
			key = 0;
			dialog = "";
			
			for(int j = 0; j < MAX_NUMBER_OF_CHOICES; j++){
				choiceArray[j] = -1;
			}

			String[] dataArray = dataRow.split(",");
			int i = 0;
			for (String item:dataArray) 
			{ 
				switch (i)
				{
				case 0:
					key = Integer.parseInt(item);
					i++;
					break;
				case 1:
					dialog = item;
					i++;
					break;
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					choiceArray[i-2] = Integer.parseInt(item);
					i++;
					break;
				default:
					i++;

				}
			}
			
			dialogs.put(key,new Dialog(dialog, choiceArray));

			dataRow = CSVFile.readLine();
		}

		CSVFile.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * @param csvFile
	 */
	private void setChoices(String csvFile){

		BufferedReader CSVFile;
		try {
			InputStream is = this.getClass().getResourceAsStream(csvFile);
			CSVFile = new BufferedReader(new InputStreamReader(is));
			String dataRow = CSVFile.readLine();
			dataRow = CSVFile.readLine();
			int key;
			String choiceStr;
			int effect;

		while (dataRow != null)
		{
			key = 0;
			choiceStr = "";
			effect = 0;

			String[] dataArray = dataRow.split(",");
			int i = 0;
			for (String item:dataArray) 
			{ 
				switch (i)
				{
				case 0:
					key = Integer.parseInt(item);
					i++;
					break;
				case 1:
					choiceStr = item;
					i++;
					break;
				case 2:
					effect = Integer.parseInt(item);
					i++;
					break;
				default:
					i++;

				}
			}
			
			choices.put(key, new Choice(choiceStr,effect));
			dataRow = CSVFile.readLine();
		}

		CSVFile.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

}
