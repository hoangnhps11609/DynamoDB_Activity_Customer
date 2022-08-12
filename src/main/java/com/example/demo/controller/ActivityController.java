package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ActivityDao;
import com.example.demo.model.Activity;

@RestController
public class ActivityController {

	@Autowired
	private ActivityDao activityDao;

	@GetMapping("")
	public String test() {
		return "test";
	}

	// Insert Activity
	@PostMapping("/activity")
	public Activity saveActivity(@RequestBody Activity activity) {
		return activityDao.save(activity);
	}

	// Delete Activity
	@DeleteMapping("/activity/{id}")
	public String deleteActivity(@PathVariable("id") String activityId) {
		return activityDao.delete(activityId);
	}

	// Update Activity
	@PutMapping("/activity/{id}")
	public String updateActivity(@PathVariable("id") String activityId, @RequestBody Activity activity) {
		String em = activityDao.update(activityId, activity);
		System.out.println(em);
		return em;
	}

	// Find by Id
	@GetMapping("/activity/getById/{id}")
	public Activity getActivity(@PathVariable("id") String activityId) {
		return activityDao.getActivityById(activityId);
	}

	//Find by ActivityType
	@GetMapping("/activity/getByActivityType/{userName}/{activityType}")
	public List<Activity> getActivityByName(@PathVariable("userName") String userName,
			@PathVariable("activityType") String activityType) {
		return activityDao.getActivityByName(userName, activityType);
	}

	//Find by Amount
	@GetMapping("/activity/getByAmount/{userName}/{from}/{to}")
	public List<Activity> getFilteByAmount(@PathVariable("userName") String userName, @PathVariable("from") String from,
			@PathVariable("to") String to) {
		return activityDao.getActivityByAmount(userName, from, to);
	}

	//Find by Date
	@GetMapping("/activity/getByDate/{userName}/{from}/{to}")
	public List<Activity> getFiltesByDate(@PathVariable("userName") String userName, @PathVariable("from") String from,
			@PathVariable("to") String to) {
		return activityDao.getActivityByDate(userName, from, to);
	}

	//Find by movement
	@GetMapping("/activity/getByMovement/{userName}/{movement}")
	public List<Activity> getActivityByMovement(@PathVariable("userName") String userName,
			@PathVariable("movement") String movement) {
		return activityDao.getActivityByMovement(userName, movement);
	}

	//Find by All
	@GetMapping("/activity/filter")
	public List<Activity> getFilter(@RequestBody Activity activity) {

		return activityDao.getFilterAll(activity.getCustomer().getUserName(), activity.getActivityType(),
				activity.getMovement());
	}

}