package com.trch.maven.plugins;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Goal is to convert a set of resources to yaml.
 * 
 * @goal convertResourcesToYaml
 * 
 */

public class ConvertResourcesToYamlMoJo extends AbstractMojo {
	
	/**
	 * @parameter expression="${project.build.directory}"
	 */
	private String targetDirectory;
	
	/**
	 * @parameter expression="${project}"
	 */
	private MavenProject mavenProject;

	/**
	 * @parameter expression="${resources}"
	 */
	private List<Resource> resources;

	public void execute() throws MojoExecutionException {

		if(getLog().isDebugEnabled()) {
			getLog().debug("In ConvertResourcesToYamlMoJo::execute().");
			getLog().debug(getResource().size() + " resources passed through.");
		}
		
		File rootTarget = new File(targetDirectory, "generated-resources");

		
		
		if(getLog().isDebugEnabled()) {
			getLog().debug("All YAML files will be relative to " + rootTarget.getAbsolutePath());
		}
		
		if(!rootTarget.exists()) {
			rootTarget.mkdir();
		}
		
		for(Resource resource : getResource()) {
			
			for(String file : resource.getIncludes()) {
				
				// The path for the YAML files will be rootTarget ( {project_root}/target/generated-resources/{chopped_path}/{yaml_file_name}
				String choppedPath = resource.getDirectory().getAbsolutePath().replace(mavenProject.getBasedir().getAbsolutePath(), "");				
				
				File targetYamlDir = new File(rootTarget, choppedPath);
				
				if(!targetYamlDir.exists()) {
					targetYamlDir.mkdirs();
				}
				
							
				
				File fileToBeConverted = new File(resource.getDirectory(), file);
				
				if(getLog().isDebugEnabled()){
					getLog().debug("The content of " + fileToBeConverted.getAbsolutePath() + " is about to be transformed to YAML");
					getLog().debug("The target YAML directory is " + targetYamlDir.getAbsolutePath());
					getLog().debug("Resource path is " + resource.getDirectory().getPath());
				}
				
				
				YamlConverter converter = YamlConverterFactory.getInstance().getYamlConverter(fileToBeConverted);
				
				if(converter == null) {
					throw new MojoExecutionException("ConvertResourcesToYamlMoJo does not support the file type that " + fileToBeConverted.getAbsolutePath() + 
							"has.");
				}
				
				File destinationYaml = determineDestinationFile(targetYamlDir, fileToBeConverted);
				
				if(getLog().isDebugEnabled()) {
					getLog().debug("About to create " + destinationYaml.getAbsolutePath());
				}
				
				try	{
					writeToYamlFile(converter.toYaml(fileToBeConverted),  destinationYaml);
				} catch (YamlConversionException e) {
					throw new MojoExecutionException("A problem occured whilst transforming " + fileToBeConverted.getAbsolutePath(), e);
				} catch (IOException e) {
					throw new MojoExecutionException("An IO issue occured whilst attempting to" +
							" write to " + destinationYaml.getAbsolutePath());
					
				}
			}
		}
		
	}
	
	private void writeToYamlFile(String content, File destination) throws IOException {
		
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(destination);
			pw.append(content);	
		} catch (FileNotFoundException e) {
			throw e;
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			
			try {
				if(pw != null){
					pw.flush();
					pw.close();
				}
			}
			catch(Exception ex) {
				
			}
		}
		
		
		
	}
	
	private File determineDestinationFile(File target, File dataFile) {
		
		File yaml = new File(convertToYamlFileName(dataFile));
		
		File suspect = new File(target, yaml.getName());
		
		if(!suspect.exists()) {
			return suspect;
		}
		
		boolean isNameTaken = true;
		int count = 2;
		
		while(isNameTaken) {
			
			suspect = new File(target, "[" + count + "]" + yaml.getName());
			count++;
			
			isNameTaken = suspect.exists();
		}
		
		return suspect;
	}
	
	private String convertToYamlFileName(File original){
		String name = original.getName();
		name = name.substring(0, name.indexOf("."));
		name += ".yaml";
		return name;
	}
	
	public List<Resource> getResource() {

		//Blank or empty config. Use default settings.
		if (resources == null ) {
			resources = new ArrayList<Resource>();
		}
		return resources;
	}

	public void setResource(List<Resource> resource) {
		this.resources = resource;
	}

	public String getFile() {
		return targetDirectory;
	}

	public void setFile(String file) {
		this.targetDirectory = file;
	}

	public String getProjectDirectory() {
		return targetDirectory;
	}

	public void setProjectDirectory(String projectDirectory) {
		this.targetDirectory = projectDirectory;
	}

	public org.apache.maven.project.MavenProject getMavenProject() {
		return mavenProject;
	}

	public void setMavenProject(org.apache.maven.project.MavenProject mavenProject) {
		this.mavenProject = mavenProject;
	}

}
