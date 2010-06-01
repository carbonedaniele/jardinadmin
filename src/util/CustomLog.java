package util;

import java.io.Serializable;

public class CustomLog implements Serializable{
	public static void log (String logMsg, Exception e){
		e.printStackTrace();
		System.out.println(logMsg);
	}
}