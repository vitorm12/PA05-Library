package edu.brandeis.cs12b.pa05;
public class BookCase extends Floor {

	private Shelf[] shelfs;
	private int numbersOfShelfs;
	protected int caseNumber;
	protected int booksAbleToHoldInCase;

	public BookCase(int caseNumber, int numbersOfShelfs, int[][] capcityPerShelf){
		this.setNumbersOfShelfs(numbersOfShelfs);
		this.caseNumber = caseNumber;
		shelfs = new Shelf[numbersOfShelfs];
		for (int i = 0; i < numbersOfShelfs; i++) {
			shelfs[i] = new Shelf(i, capcityPerShelf[caseNumber][i]);
			booksAbleToHoldInCase += capcityPerShelf[caseNumber][i];
		}
	}

	public BookCase() {

	}

	public int getFreeShelf() {
		for (Shelf shelf : shelfs) {
			if (!shelf.isShelfFilled()) {
				return shelf.getShelfNumber();
			}
		}
		return 0;
	}

	public Shelf getSpecifiedShelf(int index) {
		return shelfs[index];
	}

	public int getNumbersOfShelfs() {
		return numbersOfShelfs;
	}

	public void setNumbersOfShelfs(int numbersOfShelfs) {
		this.numbersOfShelfs = numbersOfShelfs;
	}

	public int getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	}

	public int getBooksAbleToHoldInCase() {
		return booksAbleToHoldInCase;
	}
}
