package com.trch.maven.plugins;

public class YamlConversionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6356335978195019018L;

	public YamlConversionException(String message, Throwable cause)
	{
		super(message,cause);
	}
	
	public YamlConversionException(Throwable cause)
	{
		super(cause);
	}
}
