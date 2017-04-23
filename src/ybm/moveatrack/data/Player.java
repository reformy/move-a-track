package ybm.moveatrack.data;

import java.io.Serializable;

import ybm.moveatrack.MoveATrackException;

public abstract class Player implements Serializable
{
	Board board;
	
	void setBoard(Board board)
	{
		this.board = board;
	}
	
	abstract public BoardPosition play(TrackCard outCard) throws MoveATrackException;
}
