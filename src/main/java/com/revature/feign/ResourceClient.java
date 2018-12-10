package com.revature.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.models.Resource;

@FeignClient(name="resources")
@Component
public interface ResourceClient {
	
	@GetMapping("")
//	@RequestLine("GET")
	public List<Resource> findResources();
	
	@GetMapping("/{id}")
//	@RequestLine("GET /{id}")
	public Resource getResources(@PathVariable int[] id);
	
	@GetMapping("/building/{id}")
//	@RequestLine("GET /building/{id}")
	public List<Resource> getByBuildingId(@PathVariable int id);
		
	@GetMapping("/campus/{id}")
//	@RequestLine("GET /campus/{id}")
	public List<Resource> getByCampus(@PathVariable int id);
	
}
