import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Persistent {
	public static void save(TimerModel model) {
		Gson g = new Gson();
//		Person person = g.fromJson("{\"name\": \"John\"}", Person.class);
		String json = g.toJson(model);
		writeToFile("data.json", json);
	}

	public static TimerModel read() {
		Gson g = new Gson();
		String json = readFromFile("data.json");
		TimerModel model = g.fromJson(json, TimerModel.class);
		if (model == null)
			return new TimerModel(0,0);
		else
			return model;
	}

	private static String readFromFile(String filename) {

		// This will reference one line at a time
		String line = null;
		String data = "";
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filename);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				data += line;
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		return data;
	}

	public static void writeToFile(String filename, String data) {
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(filename);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write(data);

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + filename + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}
}
