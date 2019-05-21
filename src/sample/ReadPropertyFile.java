package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {

	public static String main(String key) {
		// Read properties file.
		Properties properties = new Properties();
		try {
		    FileInputStream ip= new FileInputStream("config.properties");
		    properties.load(ip);
		    String value = properties.getProperty(key);
		    return value;
		} 
		catch (IOException e) {
		}
		return null;
	}

}
