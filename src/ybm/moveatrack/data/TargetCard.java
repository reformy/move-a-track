package ybm.moveatrack.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TargetCard implements Serializable
{
	public final Target target;
	
	public TargetCard(Target target)
	{
		this.target = target;
	}
	
	public static List<TargetCard> prepare(List<Target> targets)
	{
		List<TargetCard> res = new ArrayList<TargetCard>();
		
		for (Target t : targets)
		{
			res.add(new TargetCard(t));
		}
		
		return res;
	}
}
