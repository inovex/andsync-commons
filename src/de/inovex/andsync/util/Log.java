/*
 * Copyright 2012 Tim Roes <tim.roes@inovex.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.inovex.andsync.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class Log {
	
	private static final Logger logger = Logger.getLogger("andsync");
	
	public static void d(String message, Object... params) {
		// TODO: Set to debug level as soon as I found out how to display it...
		log(Level.INFO, message, params);
	}
	
	public static void i(String message, Object... params) {
		log(Level.INFO, message, params);
	}
	
	public static void w(String message, Object... params) {
		log(Level.WARNING, message, params);
	}
	
	public static void e(String message, Object... params) {
		log(Level.SEVERE, message, params);
	}
	
	private static void log(Level level, String message, Object... params) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String methodName = stackTrace[3].getMethodName();
		String className = stackTrace[3].getClassName();
		logger.logp(level, className, methodName, String.format(message, params));
	}
	
}
