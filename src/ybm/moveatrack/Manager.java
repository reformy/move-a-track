package ybm.moveatrack;

import java.util.Collections;
import java.util.List;

import com.kwazylabs.utils.StdinUtils;

import ybm.moveatrack.data.Board;
import ybm.moveatrack.data.Target;
import ybm.moveatrack.data.TargetCard;
import ybm.moveatrack.data.TrackCard;
import ybm.moveatrack.data.TrackCard.Rotation;

public class Manager
{
	private Board board;
	private TrackCard outTrackCard;
	
	public void prepare() throws MoveATrackException
	{
		board = new Board(7);
		
		List<Target> targets = Target.prepare();
		
		List<TargetCard> targetCards = TargetCard.prepare(targets);
		Collections.shuffle(targetCards);
		
		List<TrackCard> trackCards = TrackCard.prepare(board.size * board.size + 1,
		    targets);
		Collections.shuffle(trackCards);
		
		outTrackCard = board.init(trackCards);
	}
	
	public static void main(String[] args) throws Exception
	{
		Manager m = new Manager();
		m.prepare();
		System.out.println(m.board.format());
		
		int row = StdinUtils.readInt();
		int col = StdinUtils.readInt();
		
		TrackCard cell = m.board.getCell(row, col);
		System.out
		    .println(" " + (cell.isConnectedFrom(Rotation.TOP) ? " " : "X") + " ");
		System.out.println((cell.isConnectedFrom(Rotation.LEFT) ? " " : "X") + cell
		    + (cell.isConnectedFrom(Rotation.RIGHT) ? " " : "X"));
		System.out.println(
		    " " + (cell.isConnectedFrom(Rotation.BOTTOM) ? " " : "X") + " ");
	}
}
