package com.trch.maven.plugins;

import java.io.File;

public class YamlConverterFactory {
	
	public static final YamlConverterFactory INSTANCE = new YamlConverterFactory();
	
	private YamlConverterFactory() {
		//Singleton
	}
	
	public static YamlConverterFactory getInstance(){
		return INSTANCE;
	}
	
	public YamlConverter getYamlConverter(File file){
		
		if(file.getName().endsWith(".properties")) {
			return new PropertiesToYamlConverter();
		}
		
		return null;
	}
}
