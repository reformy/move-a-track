package ybm.moveatrack.data;

public class BoardPosition
{
	public int row;
	public int col;
	
	public BoardPosition(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	public BoardPosition clone()
	{
		return new BoardPosition(row, col);
	}
}
