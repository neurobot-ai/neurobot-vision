package com.nbai.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 项目锁
 * 
 * @author zhenmeng
 * @date 2021-4-22
 *
 */

public class ProjectLock {

	private static final ConcurrentHashMap<Long, AtomicInteger> cLock = new ConcurrentHashMap<>(); 
	
	
	/**
	 * 加锁
	 * @param projectId	项目id
	 */
	public static void lock(Long projectId) {
		if(cLock.containsKey(projectId)) {
			cLock.get(projectId).incrementAndGet();
			return;
		}
		cLock.put(projectId, new AtomicInteger(1)); 
	}
	
	
	/**
	 * 解锁
	 * @param projectId	项目id
	 */
	public static void unlock(Long projectId) {
		AtomicInteger count = cLock.get(projectId);
		if(!cLock.containsKey(projectId)) {
			return;
		}
		if(count.get() <= 1) {
			cLock.remove(projectId);
			return;
		}
		cLock.get(projectId).decrementAndGet();
	}
	
	/**
	 *项目被锁定
	 *
	 * @param projectId
	 * @return
	 */
	public static boolean isLocked(Long projectId){
		return cLock.containsKey(projectId);
	}
	
	/**
	 *项目未被锁定
	 *
	 * @param projectId
	 * @return
	 */
	public static boolean notLocked(Long projectId){
		return !isLocked(projectId);
	}
	
}
