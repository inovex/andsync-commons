/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.inovex.andsync.util;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class Joiner {
	
	public static String join(String sep, String... parts) {
		
		if(parts == null || parts.length < 1) return "";
		
		StringBuilder builder = new StringBuilder(parts[0]);
		
		for(int i = 1; i < parts.length; i++) {
			if(parts[i] == null) continue;
			builder.append(sep);
			builder.append(parts[i]);
		}
		
		return builder.toString();
	}
	
}
