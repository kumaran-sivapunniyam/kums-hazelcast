package com.example.demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Service
public class HazelcastCacheService {

	@Autowired
	private HazelcastInstance hzcInstance;

	public List<Employee> getCacheValue(String cacheCode, String searchCriteria, int maxEntries) {

		IMap<String, List<Employee>> iMap = hzcInstance.getMap(cacheCode);
		final Config config = hzcInstance.getConfig();
		final int iMapMaxsize = config.getMapConfig(cacheCode).getMaxSizeConfig().getSize();

		if (iMapMaxsize != maxEntries) {
			config.getMapConfig(cacheCode).getMaxSizeConfig().setSize(maxEntries);
		}
		return iMap.get(searchCriteria);
	}

	public void addCacheValue(String cacheCode, String searchCriteria, List<Employee> value, long expirationTime) {
		IMap<String, List<Employee>> iMap = hzcInstance.getMap(cacheCode);
		iMap.put(searchCriteria, value, expirationTime, TimeUnit.SECONDS);
		
		System.out.println("iMap.size=" + iMap.size());
	}
}
