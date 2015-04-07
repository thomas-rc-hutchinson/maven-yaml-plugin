package com.trch.maven.plugins.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.trch.maven.plugins.PropertiesToYamlConverter;
import com.trch.maven.plugins.YamlConversionException;
import com.trch.maven.plugins.YamlConverter;

public class PropertiesToYamlConverterUTest 
{

	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String SMALL_PROPERTIES_FILE = "SmallProperties.properties";
	private static final String EXPECTED_YAML_FROM_SMALL_PROPERTIES = "#user credentials" + NEW_LINE + 
																		"admin : bob" + NEW_LINE +
																		"sever : tc" + NEW_LINE +
																		"#port number" + NEW_LINE +
																		"port : 8080" + NEW_LINE +
																		"password : changeme" + NEW_LINE +
																		"host : www.example.com" + NEW_LINE ;
	
	
	@Test
	public void testSmallPropertiesFiles() throws YamlConversionException 
	{
		File file = Utils.fromResources(SMALL_PROPERTIES_FILE);
		YamlConverter converter = getYamlConverter();
		String yamlString = converter.toYaml(file);
		
		log("Expected:\n" + EXPECTED_YAML_FROM_SMALL_PROPERTIES + "\n\n");
		log("Actual:\n" + yamlString);
		
		assertEquals(EXPECTED_YAML_FROM_SMALL_PROPERTIES, yamlString);
	}
	
	private YamlConverter getYamlConverter()
	{
		return new PropertiesToYamlConverter();
	}
	
	private void log(String message)
	{
		System.out.println(message);
	}

}
