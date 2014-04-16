package events;


/**
 * Event manager handles all of the events in game
 * @author jchanke2607
 *
 */
public class EventManager {
	DialogManager dialogs;
	private final int MIN_DIALOG = 0;
	private final int MAX_DIALOG = 100;
	public EventManager(DialogManager dialogs){
		this.dialogs = dialogs;
	}
	public void doEvent(int eventNumber){
		if(eventNumber >= MIN_DIALOG || eventNumber <= MAX_DIALOG){
			displayNextDialog(eventNumber);
		}else if(eventNumber == 101){
			//do something
		}else if(eventNumber >= 102 || eventNumber <= 110){
			areaTransitionEvent(eventNumber);
		}else if(eventNumber >= 111 || eventNumber <= 120){
			openStoreEvent(eventNumber);
		}else{
			//do something
		}
			
	}
	private void openStoreEvent(int eventNumber) {
		switch (eventNumber){
		case 111:
			//Open bobs store
		break;
		case 112:
			//Open dougs store
		break;
		case 113:
			//Open the devils store
		break;
		default:
		break;
		}
	}
	private void areaTransitionEvent(int eventNumber) {
		switch (eventNumber){
		case 102:
			//Go to Town
		break;
		case 103:
			//Go to Forest
		break;
		case 104:
			//Got to Dungeon
		break;
		default:
		break;
		}
		
	}
	private void displayNextDialog(int eventNumber){
		dialogs.getNextDialog(eventNumber);
	}
}
