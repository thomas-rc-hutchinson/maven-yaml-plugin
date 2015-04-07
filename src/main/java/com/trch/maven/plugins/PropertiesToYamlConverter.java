package com.trch.maven.plugins;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

public class PropertiesToYamlConverter implements YamlConverter
{

	public String toYaml(File dataFile) throws YamlConversionException {
		
		StringBuffer yaml = new StringBuffer();
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(dataFile));
			String line;
			int commentNumber = 1;
			
			while((line = reader.readLine()) != null ) {
				
				if(!line.trim().startsWith("#")) {
					line = line.replaceAll("\\s","").replaceAll("=", " : ");					
				}
	
				line += System.getProperty("line.separator");
				yaml.append(line);
			}
			
		} catch (FileNotFoundException e) {
			new YamlConversionException(e);
		}
		catch (IOException e) {
			new YamlConversionException(e);
		}
		finally {
			silentlyCloseReader(reader);			
		}
		
		return yaml.toString();
	}
	
	private void silentlyCloseReader(Closeable reader) {
		if(reader == null) {
			return;
		}
		
		try {
			reader.close();
		}
		catch (Exception ex) {
		}
	}

}
