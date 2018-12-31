package com.revature.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.models.Resource;

/**
 * The Interface ResourceClient.
 *
 * @author 1811-Java-Nick 12/27/18
 */
@FeignClient(name="resources")
@Component
public interface ResourceClient {
	
	/**
	 * Find resources.
	 *
	 * @return the list
	 */
	@GetMapping("")
//	@RequestLine("GET")
	public List<Resource> findResources();
	
	/**
	 * Gets the resources.
	 *
	 * @param id the id
	 * @return the resources
	 */
	@GetMapping("/{id}")
//	@RequestLine("GET /{id}")
	public Resource getResources(@PathVariable int[] id);
	
	/**
	 * Gets the by building id.
	 *
	 * @param id the id
	 * @return the by building id
	 */
	@GetMapping("/building/{id}")
//	@RequestLine("GET /building/{id}")
	public List<Resource> getByBuildingId(@PathVariable int id);
	
	/**
	 * Gets the by campus.
	 *
	 * @param id the id
	 * @return the by campus
	 */
	@GetMapping("/campus/{id}")
//	@RequestLine("GET /campus/{id}")
	public List<Resource> getByCampus(@PathVariable int id);
	
}
