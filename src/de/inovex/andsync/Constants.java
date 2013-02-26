/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.inovex.andsync;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class Constants {
	
	// Prevent instantiation or inheritance of this class
	private Constants() { }
	
	public static final String UTF8 = "UTF-8";
	
	public static final String REST_OBJECT_PATH = "objects";
	public static final String REST_FIELDS_KEY = "fields";
	public static final String REST_MTIME_PATH = "mtime";
	public static final String REST_META_PATH = "meta";
	
	public static final String REST_META_DELETION_PATH = "dtime";
	public static final String REST_META_SIZE_PATH = "size";
	
	public static final String MONGO_ID = "_id";
	public static final String MONGO_LAST_MODIFIED = "_mtime";
	public static final String MONGO_META_CLASS = "class";
	public static final String MONGO_META_DELETION = "dtime";
	
	/**
	 * We won't use the HTTP standard Last-Modified, since that expects the date format to be
	 * http://tools.ietf.org/html/rfc2822 but we just want to send timestamps.
	 */
	public static final String HTTP_MODIFIED_HEADER = "Last-Modification";
	
	public static final String LOG_TAG = "AndSync";
	
}
