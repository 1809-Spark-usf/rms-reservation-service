package com.revature.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.models.Resource;

@FeignClient("resources")
@Component
public interface ResourceClient {
	
	@GetMapping("{id}")
	public Resource getResourceById(@PathVariable int id);
	
	@GetMapping("{id}")
	public List<Resource> getResourcesById(@PathVariable int[] ids);
		
	@GetMapping("/campuses/{id}")
	public List<Resource> getResourcesByCampus(@PathVariable int id);
	
	
}
