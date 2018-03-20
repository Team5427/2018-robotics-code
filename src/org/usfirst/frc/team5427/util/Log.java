package org.usfirst.frc.team5427.util;

/**
 * The log class is used for logging various things to the console, it is a
 * shortcut to System.out.print, and also allows for sorting your log messages
 * to various things, allowing some to be filtered out on runtime.
 * 
 * @author Andrew Kennedy, Bo Corman
 *
 */
@SuppressWarnings(value = { "all" })

@SameLine
public class Log {

	private static String s = "";

	/**
	 * shortcut to System.out.println()
	 * 
	 * @param text
	 */
	public static void pl(String text) {
		System.out.println(text);
	}

	/**
	 * shortcut to System.out.print()
	 * 
	 * @param text
	 */
	public static void p(String text) {
		System.out.print(text);
	}

	/**
	 * used in other methods here
	 * 
	 * @param logLevel
	 * @param text
	 */
	public static void log(String logLevel, String text) {
		if (Config.LOGGING || logLevel == "[ERROR]" || logLevel == "[FATAL]")
			System.out.println(Config.PROGRAM_NAME + " " + logLevel + " " + text);
	}

	/**
	 * Any messages that the vision processing will send to the robot.
	 * 
	 * @param text
	 */
	public static void vision(String text) {
		log("[Vision Processing]", text);
	}

	/**
	 * Logging semi-important warnings
	 * 
	 * @param text
	 */
	public static void warn(String text) {
		log("[WARN]", text);
	}

	/**
	 * Logging temporary, or unimportant debug information -- will only show up if
	 * DEBUG_MODE is enabled
	 * 
	 * @param text
	 */
	public static void debug(String text) {
//		if (Config.DEBUG_MODE)
//			log("[DEBUG]", text);
		if (Config.DEBUG_MODE)
			log("[DEBUG]", text);
	}

	/**
	 * Logging more important warnings -- will still show up even if LOGGING_ENABLED
	 * is false
	 * 
	 * @param text
	 */
	public static void error(String text) {
		log("[ERROR]", text);
	}

	/**
	 * Logging general information
	 * 
	 * @param text
	 */
	public static void info(String text) {
//		log("[INFO]", text);
	}

	/**
	 * Logging fatal, or otherwise important warnings or errors -- will still show
	 * up even if LOGGING_ENABLED is false.
	 * 
	 * @param text
	 */
	public static void fatal(String text) {
		log("[FATAL]", text);
	}

	/**
	 * Logging initialization information
	 * 
	 * @param text
	 */
	public static void init(String text) {
//		log("[INIT]", text);
	}
}
