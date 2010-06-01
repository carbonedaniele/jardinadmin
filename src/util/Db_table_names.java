package util;

import java.io.Serializable;

public class Db_table_names implements Serializable {
	public static String SYSTEM_PREFIX = "__system_";
	public static String WRAP = "`";
	public static String T_RESULTSET = WRAP + SYSTEM_PREFIX + "resultset" + WRAP;
	public static String T_NOTIFY = WRAP + SYSTEM_PREFIX + "notify" + WRAP;
	public static String T_TOOLBAR = WRAP + SYSTEM_PREFIX + "toolbar" + WRAP;
	public static String T_USER = WRAP + SYSTEM_PREFIX + "user" + WRAP;
	public static String T_GROUP = WRAP + SYSTEM_PREFIX + "group" + WRAP;
	public static String T_MANAGEMENT = WRAP + SYSTEM_PREFIX + "management" + WRAP;
	public static String T_RESOURCE = WRAP + SYSTEM_PREFIX + "resource" + WRAP;
	public static String T_FIELD = WRAP + SYSTEM_PREFIX + "field" + WRAP;
	public static String T_HEADERPREFERENCE = WRAP + SYSTEM_PREFIX + "headerpreference" + WRAP;
	public static String T_FIELDINPREFERENCE = WRAP + SYSTEM_PREFIX + "fieldinpreference" + WRAP;
	public static String T_GROUPING = WRAP + SYSTEM_PREFIX + "grouping" + WRAP;
	public static String T_USERWARNINGS = WRAP + SYSTEM_PREFIX + "userwarnings" + WRAP;
	public static String T_GROUPWARNINGS = WRAP + SYSTEM_PREFIX + "groupwarnings" + WRAP;
	public static String T_PLUGIN = WRAP + SYSTEM_PREFIX + "plugin" +WRAP;
	public static String T_PLUGINASSOCIATION = WRAP + SYSTEM_PREFIX + "pluginassociation" +WRAP;
} 
