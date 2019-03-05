package edu.brandeis.cs12b.pa05;

public class Book{
	String bookName;
	private boolean CheckedIn = true;
	private BookLocation location;
	Book(String bookName){
		this.bookName = bookName;
	}
	
	public String getBookName(){
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", CheckedIn=" + CheckedIn + ", location=" + location.toString() + "]";
	}

	public boolean isCheckedIn() {
		return CheckedIn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (CheckedIn ? 1231 : 1237);
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		Book other = (Book) obj;
		if (CheckedIn != other.CheckedIn)
			return false;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	public void setCheckedIn(boolean checkedIn) {
		CheckedIn = checkedIn;
	}

	public BookLocation getLocation() {
		return location;
	}

	public void setLocation(BookLocation location) {
		this.location = location;
	}
	
}
