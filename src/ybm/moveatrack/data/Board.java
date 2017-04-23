package ybm.moveatrack.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ybm.moveatrack.MoveATrackException;
import ybm.moveatrack.data.TrackCard.Direction;
import ybm.moveatrack.data.TrackCard.Rotation;

public class Board implements Serializable
{
	public final int size;
	
	private TrackCard[][] mat;
	
	/**
	 * The playing players, each mapped to its state on the board.
	 */
	private Map<Player, PlayerState> players = new HashMap<Player, PlayerState>();
	
	public Board(int size)
	{
		this.size = size;
		this.mat = new TrackCard[size][size];
		homes = new BoardPosition[] { new BoardPosition(0, 0),
		    new BoardPosition(0, size - 1), new BoardPosition(size - 1, 0),
		    new BoardPosition(size - 1, size - 1) };
	}
	
	public TrackCard pushRow(TrackCard newCard, int rowIndex, boolean forward)
	    throws MoveATrackException
	{
		if (rowIndex % 2 == 0)
			throw new MoveATrackException("rowIndex must be even.");
		
		if (rowIndex < 0 || rowIndex >= size)
			throw new MoveATrackException(
			    "rowIndex must be between 0 and " + size + ".");
		
		int first = forward ? (size - 1) : 0;
		int last = forward ? 0 : (size - 1);
		int adv = forward ? -1 : 1;
		
		TrackCard outCard = mat[rowIndex][first];
		for (int iCol = first; forward ? (iCol > 0) : (iCol < last); iCol += adv)
		{
			mat[rowIndex][iCol] = mat[rowIndex][iCol + adv];
		}
		mat[rowIndex][last] = newCard;
		
		return outCard;
	}
	
	public TrackCard pushCol(TrackCard newCard, int colIndex, boolean forward)
	    throws MoveATrackException
	{
		if (colIndex % 2 == 0)
			throw new MoveATrackException("colIndex must be even.");
		
		if (colIndex < 0 || colIndex >= size)
			throw new MoveATrackException(
			    "colIndex must be between 0 and " + size + ".");
		
		int first = forward ? (size - 1) : 0;
		int last = forward ? 0 : (size - 1);
		int adv = forward ? -1 : 1;
		
		TrackCard outCard = mat[first][colIndex];
		for (int iRow = first; forward ? (iRow > 0) : (iRow < last); iRow += adv)
		{
			mat[iRow][colIndex] = mat[iRow + adv][colIndex];
		}
		mat[last][colIndex] = newCard;
		
		return outCard;
	}
	
	public String format()
	{
		String res = "";
		for (TrackCard[] row : mat)
		{
			res += Arrays.toString(row) + '\n';
		}
		
		return res;
	}
	
	public TrackCard init(List<TrackCard> trackCards) throws MoveATrackException
	{
		if (trackCards.size() < (size * size - 3))
			throw new MoveATrackException("Not enough cards.");
		
		int iCard = 0;
		for (int iRow = 0; iRow < size; iRow++)
		{
			for (int iCol = 0; iCol < size; iCol++)
			{
				// Corners are always corners.
				if (iRow == 0 && iCol == 0)
				{
					mat[iRow][iCol] = new TrackCard(Direction.CORNER, null);
					mat[iRow][iCol].setRotation(Rotation.RIGHT);
				}
				else if (iRow == 0 && iCol == size - 1)
				{
					mat[iRow][iCol] = new TrackCard(Direction.CORNER, null);
					mat[iRow][iCol].setRotation(Rotation.BOTTOM);
				}
				else if (iRow == size - 1 && iCol == 0)
				{
					mat[iRow][iCol] = new TrackCard(Direction.CORNER, null);
					mat[iRow][iCol].setRotation(Rotation.TOP);
				}
				else if (iRow == size - 1 && iCol == size - 1)
				{
					mat[iRow][iCol] = new TrackCard(Direction.CORNER, null);
					mat[iRow][iCol].setRotation(Rotation.LEFT);
				}
				else
				{
					mat[iRow][iCol] = trackCards.get(iCard++);
				}
			}
		}
		
		return trackCards.get(iCard++);
	}
	
	public TrackCard getCell(int row, int col)
	{
		return mat[row][col];
	}
	
	public Map<Player, PlayerState> getPlayers()
	{
		return Collections.unmodifiableMap(players);
	}
	
	final BoardPosition homes[];
	
	public void addPlayer(Player player)
	{
		PlayerState state = new PlayerState(homes[players.size()]);
		players.put(player, state);
	}
	
	public static class PlayerState
	{
		private BoardPosition position;
		public final BoardPosition home;
		
		PlayerState(BoardPosition home)
		{
			this.home = home.clone();
			position = home.clone();
		}
		
		public BoardPosition getPosition()
		{
			return position;
		}
	}
}
