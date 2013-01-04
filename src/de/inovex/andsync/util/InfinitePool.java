package de.inovex.andsync.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class InfinitePool<T> {
	
	private Queue<T> queue = new ConcurrentLinkedQueue<T>();
	private final PoolHandler<T> handler;
	private final int maxPoolSize;
	
	public InfinitePool(PoolHandler<T> handler, int maxPoolSize) {
		this.handler = handler;
		this.maxPoolSize = maxPoolSize;
	}
	
	public InfinitePool(PoolHandler<T> handler) {
		this(handler, Integer.MAX_VALUE);
	}
	
	public T get() {
		if(queue.size() == 0) {
			return handler.create();
		}
		return queue.remove();
	}
	
	public void release(T obj) {
		handler.free(obj);
		if(queue.size() < maxPoolSize) {
			queue.add(obj);
		}
	}
	
	public static abstract class PoolHandler<T> {
		
		public abstract T create();
		
		public void free(T obj) { }
		
	}
	
}
