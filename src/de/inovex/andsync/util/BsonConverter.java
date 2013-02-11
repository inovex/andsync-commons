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

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSONCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bson.BSONCallback;
import org.bson.BSONObject;
import org.bson.BasicBSONCallback;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class BsonConverter {
	
	static {
		
		InfinitePool.PoolHandler<BasicBSONEncoder> encHandler = new InfinitePool.PoolHandler<BasicBSONEncoder>() {

			@Override
			public BasicBSONEncoder create() {
				return new BasicBSONEncoder();
			}
			
		};
		
		InfinitePool.PoolHandler<BasicBSONDecoder> decHandler = new InfinitePool.PoolHandler<BasicBSONDecoder>() {

			@Override
			public BasicBSONDecoder create() {
				return new BasicBSONDecoder();
			}
			
		};
		
		InfinitePool.PoolHandler<BasicDBList> listHandler = new InfinitePool.PoolHandler<BasicDBList>() {

			@Override
			public BasicDBList create() {
				return new BasicDBList();
			}

			@Override
			public void free(BasicDBList obj) {
				obj.clear();
			}
		
		};
		
		encoderPool = new InfinitePool<BasicBSONEncoder>(encHandler, 10);
		decoderPool = new InfinitePool<BasicBSONDecoder>(decHandler, 10);
		dblistPool = new InfinitePool<BasicDBList>(listHandler, 50);
				
	}
	
	private static InfinitePool<BasicBSONEncoder> encoderPool;
	private static InfinitePool<BasicBSONDecoder> decoderPool;
	private static InfinitePool<BasicDBList> dblistPool;
	
	public static DBObject fromBson(byte[] bson) {
		JSONCallback callback = new JSONCallback();
		
		try {
			decoderPool.get().decode(bson, callback);
		} catch(Exception ex) {
			Log.e("Data was not a valid BSON object.");
			return null;
		}
		
		return (DBObject)callback.get();
	}
	
	public static List<DBObject> fromBsonList(byte[] bson) {
		
		JSONCallback callback = new JSONCallback();
		
		try {
			decoderPool.get().decode(bson, callback);
			//new BasicBSONDecoder().decode(bson, callback);
		} catch(Exception ex) {
			Log.w("Data was not a valid BSON object.");
			return null;
		}
		
		 BasicDBObject dbo = (BasicDBObject)callback.get();
		 List<DBObject> list = new ArrayList<DBObject>(dbo.size());
		 for(Object o : dbo.values()) {
			 list.add((DBObject)o);			 
		 }
		 return list;
		
	}
	
	public static DBObject fromBSONFirst(byte[] bson) {
		List<DBObject> objects = fromBsonList(bson);
		return objects.get(0);
	}
	
	public static byte[] toBSON(DBObject object) {
		BasicDBList list = dblistPool.get();
		//BasicDBList list = new BasicDBList();
		
		list.add(object);
		byte[] bson = toBSON(list);
		dblistPool.release(list);
		return bson;
	}
	
	public static byte[] bsonObjectAsBytes(BSONObject obj) {
		return encoderPool.get().encode(obj);
	}
	
	public static byte[] toBSON(Collection<DBObject> objects) {
		
		BasicDBList list = dblistPool.get();
		//BasicDBList list = new BasicDBList();
		for(BSONObject obj : objects) {
			list.add(obj);
		}
		byte[] bson = toBSON(list);
		dblistPool.release(list);
		return bson;
		
	}
	
	private static byte[] toBSON(BasicDBList dblist) {
		return encoderPool.get().encode(dblist);
	}
	
	public static BSONObject bytesAsBsonObject(byte[] obj) {
		BSONCallback callback = new BasicBSONCallback();
		decoderPool.get().decode(obj, callback);
		return (BSONObject)callback.get();
	}
	
}