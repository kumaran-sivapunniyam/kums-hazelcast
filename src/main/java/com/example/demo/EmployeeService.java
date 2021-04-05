package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeService {
	
	
	@Autowired
	private HazelcastCacheService hazelcastCacheService;
	
	@GetMapping("/employees")
	public List<Employee> getEmployee() {
		
		List<Employee> list = new ArrayList<Employee>();
		list.add(new Employee(1L, "Kumaran"));
		list.add(new Employee(2L, "Cheran"));
		list.add(new Employee(3L, "Srini"));
		list.add(new Employee(4L, "Kavin"));
		list.add(new Employee(5L, "Mughil"));
		
		//Add to Cache
		final String cacheCode = "EMP_LIST";
		final String searchCriteria = "ALL_EMPLOYEES";
		//Object value = list;
		final long expirationTime = 60000;
		hazelcastCacheService.addCacheValue(cacheCode, searchCriteria, list, expirationTime);
		
		return list;
		
	}
	
	@GetMapping("/eFromCache")
	public List<Employee> getEmployeeFromCache() {
		final String cacheCode = "EMP_LIST";
		final String searchCriteria = "ALL_EMPLOYEES";
		final int maxEntries = 1000;

		return hazelcastCacheService.getCacheValue(cacheCode, searchCriteria, maxEntries);

	}	
	
}
