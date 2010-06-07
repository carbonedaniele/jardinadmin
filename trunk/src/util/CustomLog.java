package util;

import java.io.Serializable;

public class CustomLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void log (String logMsg, Exception e){
		e.printStackTrace();
		System.out.println(logMsg);
	}
}