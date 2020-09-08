/* RASHAAD SAUNDERSON
   LAB 2
 */
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter file name: ");
		String file = sc.nextLine();
		String[][] array = readData(file);
		sort(array);
		writeToFile(array);
		sc.close();
	}

	// sorts the array according to names using selection sort
	private static void sort(String[][] array) {
		for (int i = 0; i < array.length - 1; i++) { 
			int min = i;
			for (int j = i + 1; j < array.length; j++) 
				// element at index j is smaller than element at index min
				if (((String)array[j][0]).compareTo((String)array[min][0])<0) 
					min = j; 
			
			// swap elements at index i and min
			String temp = array[i][0];
			String val = array[i][1];
			array[i][0] = array[min][0];
			array[i][1] = array[min][1];
			array[min][0] = temp;
			array[min][1] = val;
		} 

	}

	// writes the passed array to a file
	private static void writeToFile(String[][] array) {

		// using filewriter to write to file
		// try with resources is used to automatically close the resources after use
		try (FileWriter writer = new FileWriter("out.csv");
				BufferedWriter bw = new BufferedWriter(writer)) {
			bw.write("Artist,Frequency"+System.lineSeparator());
			
			// write all array elements to file
			for(int i=0;i<array.length;i++) {
				bw.write(array[i][0]+","+array[i][1]+System.lineSeparator());;
			}
			
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		System.out.println("Report successfully written to 'out.csv'.");
	}

	// read data from passed file
	private static String[][] readData(String fileName) {

		String[][] array = new String[0][2];
		try {
			File f = new File(fileName);
			BufferedReader b = new BufferedReader(new FileReader(f));
			String line = "";
			
			// reading headings and extra line
			b.readLine();
			b.readLine();
			
			// read all lines one by one
			while ((line = b.readLine()) != null) {
				// remove position from line
				line =line.substring(line.indexOf(",")+1);
				
				// line starts with "
				if(line.charAt(0)=='"') {
					
					// go until the line starts with ','
					while(line.charAt(0)!=','){
						line = line.substring(line.indexOf("\"")+1);
						line = line.substring(line.indexOf("\"")+1);
					}
				}
				else {
					line =line.substring(line.indexOf(","));
				}
				// remove trailing commas
				line =line.substring(1);
				String artist;
				
				// remove track name
				// line starts with "
				if(line.charAt(0)=='"') {
					line = line.substring(line.indexOf("\"")+1);
					artist = line.substring(0 ,line.indexOf("\""));
				}
				else {
					artist = line.substring(0 ,line.indexOf(","));
				}
				
				// add artist to the array
				array = addArtist(artist, array);
			}

			// catches any error occurs while reading the file
		} catch (Exception e) {
			e.printStackTrace();
		}

		return array;
	}

	// add the passed artist to passed array
	private static String[][] addArtist(String artist, String[][] array) {
		
		// check if the artist already exists
		for(int i=0;i<array.length;i++) {
			
			// artist already exists, increase its frequency by 1
			if(array[i][0].equals(artist)) {
				array[i][1] = String.valueOf(Integer.valueOf(array[i][1]) + 1);
				return array;
			}
		}
		
		// artist does not exists, create a new array of 1 more size to accommodate new artist
		String[][] nArr = new String[array.length+1][2];
		
		// copy old elements
		for(int i=0;i<array.length;i++) {
			nArr[i][0] = array[i][0];
			nArr[i][1] = array[i][1];
		}
		
		// add new elements at last position
		nArr[array.length][0] = artist;
		nArr[array.length][1] = "1";
		return nArr;
	}
}
