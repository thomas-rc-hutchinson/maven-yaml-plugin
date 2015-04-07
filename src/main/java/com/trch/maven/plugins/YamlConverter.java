package com.trch.maven.plugins;

import java.io.File;

public interface YamlConverter 
{
	String toYaml(File dataFile) throws YamlConversionException;
}
