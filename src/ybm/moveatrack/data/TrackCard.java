package ybm.moveatrack.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kwazylabs.utils.RandomUtils;

public class TrackCard implements Serializable
{
	public static enum Direction
	{
		CORNER, // From top to right: └
		T, // From top to right and bottom: ┠
		LINE, // From top to bottom: │
	}
	public static char[] DIRECTION_CHARS = {'└', '┠', '│'};
	
	private static int[][] NORMALIZED_CONNECTED_ROTATIONS_PER_DIRECTION = {
	    { 0, 1 }, { 0, 1, 2 }, { 0, 2 } };
	
	public static enum Rotation
	{
		TOP,
		RIGHT,
		BOTTOM,
		LEFT
	}
	
	public final Direction direction;
	public final Target target;
	
	private Rotation rotation;
	
	public TrackCard(Direction direction, Target target)
	{
		this.direction = direction;
		this.target = target;
		rotation = Rotation.TOP;
	}
	
	public static List<TrackCard> prepare(int nCards, List<Target> targets)
	{
		List<TrackCard> res = new ArrayList<TrackCard>();
		for (int i = 0; i < nCards; i++)
		{
			Target t = i < targets.size() ? targets.get(i) : null;
			TrackCard tc = new TrackCard(RandomUtils.chooseFrom(Direction.values()),
			    t);
			res.add(tc);
		}
		return res;
	}
	
	public void setRotation(Rotation rotation)
	{
		this.rotation = rotation;
	}
	
	public Rotation getRotation()
	{
		return rotation;
	}
	
	public boolean isConnectedFrom(Rotation conRot)
	{
		for (int normalizedConncetedRotation : NORMALIZED_CONNECTED_ROTATIONS_PER_DIRECTION[direction
		    .ordinal()])
		{
			int conncetedRotation = (normalizedConncetedRotation + rotation.ordinal())
			    % Rotation.values().length;
			if (conRot.ordinal() == conncetedRotation)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "" + DIRECTION_CHARS[direction.ordinal()];
	}
}
