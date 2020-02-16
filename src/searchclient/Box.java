package searchclient;

import java.util.Objects;

public class Box {

	private char letter;
	private int xPos;
	private int yPos;

	public Box(char letter, int xPos, int yPos) {
		this.letter = letter;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public char getLetter() {
		return letter;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Box box = (Box) o;
		return letter == box.letter &&
				xPos == box.xPos &&
				yPos == box.yPos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(letter, xPos, yPos);
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
