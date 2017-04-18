package ybm.moveatrack.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ybm.moveatrack.Config;

public class Target implements Serializable
{
	public final String name;
	
	public Target(String name)
	{
		this.name = name;
	}
	
	public static List<Target> prepare()
	{
		List<Target> res = new ArrayList<Target>();
		
		for (String name : Config.TARGET_NAMES)
		{
			res.add(new Target(name));
		}
		
		return res;
	}
}
