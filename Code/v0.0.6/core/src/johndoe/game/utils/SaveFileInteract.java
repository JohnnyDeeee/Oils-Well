package johndoe.game.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;


public class SaveFileInteract {

	private static File file;
	private static ArrayList<String[]> accounts;
	private static ArrayList<String> old_usernames;
	private static ArrayList<String> new_usernames;
	private static ArrayList<Integer> old_scores;
	private static ArrayList<Integer> new_scores;
	private static ArrayList<Integer> old_meters;
	private static ArrayList<Integer> new_meters;
	private static ArrayList<String> scores;
	private static FileHandle fileHandle;
	private static String username;
	private static int method = 1;
	
	public static void saveScore(FileHandle fileHandle, String object){
		file = new File(fileHandle.path());
		
		try {
			if(file.exists()){
				fileHandle.writeString(object, true);
				fileHandle.writeString("\n", true);
			}else{
				file.getParentFile().mkdir();
				file.createNewFile();
				
				fileHandle.writeString(object, true);
				fileHandle.writeString("\n", true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getScores(FileHandle fileHandle){
		accounts = new ArrayList<String[]>();
		old_usernames = new ArrayList<String>();
		new_usernames = new ArrayList<String>();
		old_scores = new ArrayList<Integer>();
		new_scores = new ArrayList<Integer>();
		old_meters = new ArrayList<Integer>();
		new_meters = new ArrayList<Integer>();
		scores = new ArrayList<String>();
		file = new File(fileHandle.path());
		
		try {
			if (fileHandle.exists()) {
				Scanner sc = new Scanner(fileHandle.file());
				int lineNum = 0;

				while (sc.hasNextLine() && lineNum < 5) {
					String line = sc.nextLine();
					lineNum++;
					
					String[] acInfo = line.split(",");
					
					if (!line.isEmpty()) {
						accounts.add(acInfo);
					}
				}

				sc.close();
			}else{
				file.getParentFile().mkdir();
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Method 0 = scores, method 1 = meters
		if (method == 0) {
			for (int i = 0; i < 5; i++) {
				old_usernames.add("empty");
				old_scores.add(0);
				new_scores.add(0);
			}
			
			if (accounts.size() > 0) {
				for (int i = 0; i < accounts.toArray().length; i++) {
					//			Gdx.app.log("JSONINTERACT", "[0] = " + account[0] + ", [1] = " + account[1]);
					String[] account = accounts.get(i);
					old_usernames.set(i, account[0]);
					old_scores.set(i, Integer.parseInt(account[1]));
					new_scores.set(i, Integer.parseInt(account[1]));
				}
			}
			
			Collections.sort(new_scores, Collections.reverseOrder());
			
			for(String username : old_usernames){
				new_usernames.add("");
			}
			
			for (int i = 0; i < old_scores.toArray().length; i++) {
				for (int j = 0; j < new_scores.toArray().length; j++) {
					if(old_scores.get(i) == new_scores.get(j)){
						username = old_usernames.get(i);
						if (new_usernames.get(j) == "") {
							new_usernames.set(j, username);
							break;
						}else{
							
						}
					}
				}
			}
			
			for (int i = 0; i < new_usernames.toArray().length; i++) {
				scores.add(new_usernames.get(i) + " : " + new_scores.get(i));
			}
		}else if(method == 1){
			for (int i = 0; i < 5; i++) {
				old_usernames.add("empty");
				old_meters.add(0);
				new_meters.add(0);
			}
			
			if (accounts.size() > 0) {
				for (int i = 0; i < accounts.toArray().length; i++) {
					String[] account = accounts.get(i);
					old_usernames.set(i, account[0]);
					old_meters.set(i, Integer.parseInt(account[2]));
					new_meters.set(i, Integer.parseInt(account[2]));
				}
			}
			
			Collections.sort(new_meters);
			
			for(String username : old_usernames){
				new_usernames.add("");
			}
			
			for (int i = 0; i < old_meters.toArray().length; i++) {
				for (int j = 0; j < new_meters.toArray().length; j++) {
					if(old_meters.get(i).equals(new_meters.get(j))){
						username = old_usernames.get(i);
						if (new_usernames.get(j) == "") {
							Gdx.app.log("SAVEFILEINTERACT", "Username = " + username);
							new_usernames.set(j, username);
							break;
						}else{
							
						}
					}
				}
			}
			
			for (int i = 0; i < new_usernames.toArray().length; i++) {
				scores.add(new_usernames.get(i) + " : " + new_meters.get(i));
			}
		}
		
		return scores;
	}
}
