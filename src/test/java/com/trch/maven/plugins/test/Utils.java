package com.trch.maven.plugins.test;

import java.io.File;

public class Utils 
{
	public static File fromResources(String name)
	{
		return new File("src/test/resources", name);
	}
}
