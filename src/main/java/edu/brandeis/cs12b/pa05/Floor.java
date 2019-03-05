package edu.brandeis.cs12b.pa05;

public class Floor {
	protected int FloorNumber;
	private int x = 0;
	BookCase[] bookCases;

	Floor(int floorNumber, int casesOnFloor, int[] shelvesPerCases, int[][] shelfCapacityy) {
		this.FloorNumber = floorNumber;
		bookCases = new BookCase[casesOnFloor];
		for (int i = 0; i < casesOnFloor; i++) {
			bookCases[i] = new BookCase(i, shelvesPerCases[i], shelfCapacityy);
			x += bookCases[i].getBooksAbleToHoldInCase();
		}
	}

	public Floor() {

	}

	public int returnX() {
		return this.x;
	}

	public void decrementX() {
		this.x--;
	}

	public BookLocation findFreeShelf() {
		int caseNum = 0;
		int shelfNum = 0;
		for (BookCase casee : bookCases) {
			if (casee.getBooksAbleToHoldInCase() != 0) {
				caseNum = casee.getCaseNumber();
				shelfNum = casee.getFreeShelf();
			}
		}
		return new BookLocation(FloorNumber, caseNum, shelfNum);
	}

	public BookCase getSpecifiedBookCase(int index) {
		return bookCases[index];
	}

	public void setBookCases(BookCase[] bookCases) {
		this.bookCases = bookCases;
	}
}
