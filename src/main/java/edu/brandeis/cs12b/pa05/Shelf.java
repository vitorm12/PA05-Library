package edu.brandeis.cs12b.pa05;

import java.util.HashMap;

public class Shelf extends BookCase {
	private int shelfCapacity;
	private int shelfNumber;
	private boolean isShelfFilled;
	private HashMap<String, Book> books = new HashMap<String, Book>();

	Shelf(int shelfNumber, int shelfCapacity) {
		this.setShelfCapacity(shelfCapacity);
		this.setShelfNumber(shelfNumber);
	}

	// adds book to shelf
	public BookLocation addBook(String title) {
		BookLocation l = null;
		if (books.containsKey(title) || this.shelfCapacity == 0) {
			isShelfFilled = true;
			return null;
		} else {
			l = new BookLocation(this.FloorNumber, this.caseNumber, this.shelfNumber);
			Book book = new Book(title);
			book.setLocation(l);
			books.put(title, book);
			this.shelfCapacity--;
			this.booksAbleToHoldInCase--;
			if (this.shelfCapacity == 0) {
				isShelfFilled = true;
			}
			return l;
		}
	}

	public HashMap<String, Book> getBooks() {
		return books;
	}

	public Book getBook(String title) {
		if (books.containsKey(title)) {
			return books.get(title);
		}
		return null;
	}

	public int getShelfCapacity() {
		return shelfCapacity;
	}

	public void setShelfCapacity(int shelfCapacity) {
		this.shelfCapacity = shelfCapacity;
	}

	public int getShelfNumber() {
		return shelfNumber;
	}

	public void setShelfNumber(int shelfNumber) {
		this.shelfNumber = shelfNumber;
	}

	public boolean isShelfFilled() {
		return isShelfFilled;
	}

	public void setShelfFilled(boolean isShelfFilled) {
		this.isShelfFilled = isShelfFilled;
	}

}
