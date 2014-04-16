package saveload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SaveLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4601788845082635524L;

	/**
	 * Requires an ArrayList to be passed will save it from there.
	 * 
	 * @param classList
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void save(ArrayList<Object> classList) throws IOException, ClassNotFoundException {
		File file = new File("save.ser");
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream fout = new FileOutputStream("save.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(classList);
		oos.close();
	}

	/**
	 * Returns a ArrayList that lets us load the game.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Object> load() throws IOException, ClassNotFoundException {
		FileInputStream streamIn = new FileInputStream("save.ser");
		ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
		@SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) objectinputstream.readObject();
		objectinputstream.close();
		return list;
	}


}