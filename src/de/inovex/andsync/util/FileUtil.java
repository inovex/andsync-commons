/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.inovex.andsync.util;

import java.io.File;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class FileUtil {
	
	public static void delete(File file) {
		if(file.isDirectory()) {
			for(File f : file.listFiles()) {
				delete(f);
			}
		}
		file.delete();
	}
	
}
