package edu.brandeis.cs12b.pa05;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import org.json.JSONObject;

/**
 * @author Vitor Mouzinho
 * NOTE:
 * Architecture and code design:
 * <p>
 * I chose to implement my code in
 * This format because it optimize run time of the code
 * instead of having code run O(n^3) majority of my algortims 
 * run constant or linear time the worst method is during creation O(n^3)  
 * and repopulate shelfCapacity which there wasn't any way to my knowledge
 * around it without using dependencies and I wasn't sure if we were allowed 
 * to do so or not.
 * <p>  
 */
public class Library{
	
	/**
	 * Construct a new library object based on the given parameters
	 * @param floors the number of floors
	 * @param casesPerFloor the number of cases per floor
	 * @param shelvesPerCase the number of shelves per case on each floor
	 * @param shelfCapacity the capacity of each shelf
	 * @return a library object
	 */
	private int floors;
	private int[] casesPerFloor;
	private int[][] shelvesPerCase;
	private int[][][] shelfCapacity;
	private Floor[] allFloors;
	public  HashMap<String, Book> books = new HashMap<String, Book>();
	public Library(int floors, int[] casesPerFloor, int[][] shelvesPerCase, int[][][] shelfCapacity) {
		if(edgeCases(floors,casesPerFloor,shelvesPerCase,shelfCapacity)){
			this.floors = floors;
			this.casesPerFloor = casesPerFloor;
			this.shelvesPerCase = shelvesPerCase;
			this.shelfCapacity = shelfCapacity;
			allFloors = new Floor[floors];
			for(int i = 0; i < floors; i++){
				allFloors[i] = new Floor(i, casesPerFloor[i], shelvesPerCase[i], shelfCapacity[i]);
			}
		}	
	}
	public boolean edgeCases(int floors, int[] casesPerFloor, int[][] shelvesPerCase, int[][][] shelfCapacity) {
		boolean empty = (floors > 0)&&(casesPerFloor!=null)
				&&(shelvesPerCase!=null)&&(shelfCapacity!=null);
		boolean checkValues = (floors==casesPerFloor.length)
				&&(casesPerFloor.length <= shelvesPerCase.length)
				&& (shelfCapacity.length == shelvesPerCase.length);
		return empty&&checkValues;
	}
	public Library(){
		
	}	
	/**
	 * Returns the capacity of the nth shelf at the particular floor and case
	 * @param floor
	 * @param bookcase
	 * @param shelf
	 * @return the shelf capacity
	 */
	public int getCapacityOfShelf(int floor, int bookcase, int shelf){
		return shelfCapacity[floor][bookcase][shelf];
	}
	/**
	 * Adds a new book and returns its location. If there is no space for the new book,
	 * return null.
	 * @param title the title of the book to add
	 * @return the location of the book or null
	 */
	public BookLocation addNewBook(String title){
		for(int i = 0; i < allFloors.length;i++) {
			if((allFloors[i].returnX() != 0)){
				BookLocation l = allFloors[i].findFreeShelf();
				Book book = new Book(title);
				book.setLocation(l);
				books.put(title,book);
				allFloors[i].decrementX();
				return getShelf(l).addBook(title);
			}
		}
		return null;
	}
	/**
	 * Return the location of the book with the given title, or null if
	 * it is not in the library
	 * @param title the title of the book to lookup
	 * @return the book's location
	 */
	public BookLocation getLocationOfBook(String title){
		if(this.books.containsKey(title) && this.books.get(title).isCheckedIn()){ 
			return this.books.get(title).getLocation();
		}
		return null;
		
	}
	/**
	 * Return the set of all books at the given location, or null if the location is invalid
	 * @param loc the location to list the books at (only checked in books should be listed)
	 * @return the set of books, or null
	 */
	public Set<String> getBooksAt(BookLocation loc){
		Set<String> s= new HashSet<String>();
		for (Entry<String, Book> key : books.entrySet()){
		    if(key.getValue().isCheckedIn()){
		    	s.add(key.getKey());
		    }
		}
		return s;
	}
	/**
	 * @return the specified shelf and its contents
	 *@param BookLocation x location of the needed shelf  
	 */
	public Shelf getShelf(BookLocation x){
		return allFloors[x.getFloor()].getSpecifiedBookCase(x.getCase())
				.getSpecifiedShelf(x.getShelf());
	}
	/**
	 * Checks out a book
	 * @param title the book to check out
	 * @return true if the book can be checked out
	 */
	public boolean checkOut(String title) {
		if(books.containsKey(title) && books.get(title).isCheckedIn()){
			books.get(title).setCheckedIn(false);
			return true;
		}
		return false;
	}
	/**
	 * Checks a book back in
	 * @param title the book
	 */
	public void checkIn(String title) {
		if(books.containsKey(title) && !(books.get(title).isCheckedIn())){
			books.get(title).setCheckedIn(true);
			//this.getShelf(books.get(title).getLocation()).returnToShelf(title);			
		}
	}
	/**
	 * Writes the contents of the library to the passed file
	 * in a format compatible with the LibraryFactory's makeLibraryFromFile method.
	 * @param f the file to write the library to
	 */
	public void writeToFile(File f) {
		JSONObject	jo	= new JSONObject();
		JSONObject	booksNames = new JSONObject();
		booksNames.put("names", 5);
		try(FileWriter file = new FileWriter(f)){
		    	 jo.put(
		    			 " "+floors+
		    			 " "+shelvesPerCase.length+
		    			 " "+shelvesPerCase[0].length+
		    			 " "+shelfCapacity.length+
		    			 " "+shelfCapacity[0].length+
		    			 " "+shelfCapacity[0][0].length
		    			 ,""
		    			+"@"+Arrays.toString(casesPerFloor)
		    			+"@"+Arrays.deepToString(shelvesPerCase)+
		    			 "@"+Arrays.deepToString(shelfCapacity));
		      //file.write(jo.toString());
		      file.write(booksNames.toString());
		      file.flush();
		      file.close();   
		}catch(IOException e) {
			System.out.println("File does not exist");
		}
	}
	/**
	 * Makes a new library from the passed file. The file will have been produced by your
	 * Library's writeToFile method.
	 * 
	 * @param f the file to read from
	 * @return the reconstructed library
	 */
	@SuppressWarnings("resource")
	public static  Library makeLibraryFromFile(File f) {
		String input = "";
		Scanner s;
		JSONObject x1;
		try {
			s = new Scanner(f);
			/*while (s.hasNextLine()) {
				//input += s.nextLine();
			}*/
			x1 = new JSONObject(s.nextLine());
			System.out.println(x1.get("names"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] n = input.split("names");
		s = new Scanner(input.replaceAll("[^0-9]", " "));
		int floorSize = s.nextInt();
		int[] cpf = new int[floorSize];
		int[][] spc = new int[s.nextInt()][s.nextInt()];
		int[][][] cfs = new int[s.nextInt()][s.nextInt()][s.nextInt()];
		
		for (int i = 0; i < cpf.length; i++) {
			cpf[i] = s.nextInt();
		}
		for (int i = 0; i < spc.length; i++) {
			for (int j = 0; j < spc[0].length; j++) {
				spc[i][j] = s.nextInt();
			}
		}
		for (int i = 0; i < cfs.length; i++) {
			for (int j = 0; j < cfs[0].length; j++) {
				for (int k = 0; k < cfs[0][0].length; k++) {
					cfs[i][j][k] = s.nextInt();
				}
			}
		}
		String[] namesBook = n[1].split(",");
		Library ll = new Library(floorSize, cpf, spc, cfs);
		for (String x : namesBook) {
			ll.addNewBook(x.replaceAll("[^A-Za-z0-9 ]", ""));
		}
		return ll;
	}
	/**
	 * Returns the number of floors this library has
	 * @return the number of floors
	 */
	public int getNumberOfFloors() {
		return floors;
	}
	/**
	 * Returns the number of cases on the nth floor (floors start at 0)
	 * @param floor 
	 * @return the number of cases
	 */
	public int getCasesOnFloor(int floor) {
		return this.casesPerFloor[floor];
	}
	/**
	 * Returns the number of shelves in a given case on a given floor
	 * @param floor
	 * @param bookcase
	 * @return the number of shelves
	 */
	public int getShelvesInCase(int floor, int bookcase) {
		return shelvesPerCase[floor][bookcase];
	}
}
