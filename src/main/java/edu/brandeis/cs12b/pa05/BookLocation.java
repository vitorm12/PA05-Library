package edu.brandeis.cs12b.pa05;

public class BookLocation {
	private int floor;
	private int bookcase;
	private int shelf;

	public BookLocation(int floor, int bookcase, int shelf) {
		this.floor = floor;
		this.bookcase = bookcase;
		this.shelf = shelf;
	}

	public BookLocation() {

	}

	public int getFloor() {
		return floor;
	}

	public int getCase() {
		return bookcase;
	}

	public int getShelf() {
		return shelf;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public void setCase(int bookcase) {
		this.bookcase = bookcase;
	}

	public void setShelf(int shelf) {
		this.shelf = shelf;
	}

	// used to compare location values
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookcase;
		result = prime * result + floor;
		result = prime * result + shelf;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookLocation other = (BookLocation) obj;
		if (bookcase != other.bookcase)
			return false;
		if (floor != other.floor)
			return false;
		if (shelf != other.shelf)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookLocation [floor=" + floor + ", bookcase=" + bookcase + ", shelf=" + shelf + "]";
	}

}
