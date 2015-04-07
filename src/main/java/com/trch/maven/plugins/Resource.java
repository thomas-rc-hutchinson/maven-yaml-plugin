package com.trch.maven.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resource {
	
	/**
	* 	@parameter
	*   expression="${directory}"
	*/
	private File directory;
	
	
	/**
	* 	@parameter
	*   expression="${includes}"
	*/
	private List<String> includes;
	
	/**
	* 	@parameter
	*   expression="${excludes}"
	*/
	private List<String> excludes;
	
	
	//Required by Maven
	public Resource(){
		
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public List<String> getIncludes() {
		
		if(includes == null || includes.isEmpty()){
			includes = new ArrayList<String>();
		}
		return includes;
	}

	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}

	public List<String> getExcludes() {
		
		if(excludes == null || excludes.isEmpty()){
			excludes = new ArrayList<String>();
		}
		return excludes;
	}

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}
	
	
}
