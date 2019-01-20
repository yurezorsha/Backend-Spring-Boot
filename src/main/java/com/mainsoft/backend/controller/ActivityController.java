package com.mainsoft.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mainsoft.backend.entity.Activity;
import com.mainsoft.backend.entity.Report;
import com.mainsoft.backend.service.ActivityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api")
@Api(description = "Api for work with activity, get, post, put, delete, get report.")
public class ActivityController {
	
	@Autowired
	ActivityService activityService;

	@GetMapping("activity/{id}")
	@ApiOperation("Returns activity by existing id.")
	public ResponseEntity<Activity> getActivityById(@PathVariable("id") Long id) {
		Activity a = activityService.getActivityById(id);
		return new ResponseEntity<Activity>(a, HttpStatus.OK);
	}

	@GetMapping("activities/{username}")
	@ApiOperation("Returns set of activity by existing username. ")
	public ResponseEntity<List<Activity>> getAllActivityByUserName(@PathVariable("username") String username) {
		
		List<Activity> list = activityService.getAllActivityByUserName(username);
		return new ResponseEntity<List<Activity>>(list, HttpStatus.OK);
	}
	
	@GetMapping("activity/report/{username}")
	@ApiOperation("Returns report for activity for all period by existing username.")
	public ResponseEntity<List<Report>> getAllActivityReportByUserName(@PathVariable("username") String username) {
		
		List<Report> list = activityService.getReportByUserName(username);
		return new ResponseEntity<List<Report>>(list, HttpStatus.OK);
	}


	@PostMapping("activity/{username}")
	@ApiOperation("Adding activity for existing username.")
	public ResponseEntity<Activity> addActivityByUserName(@RequestBody Activity a, @PathVariable("username") String username) {
		Activity activity = activityService.addActivityByUserName(a, username);
		return new ResponseEntity<Activity>(activity, HttpStatus.CREATED);
	}

	
	@PutMapping("activity/{username}")
	@ApiOperation("Updating activity by existing username.")
	public ResponseEntity<Activity> updateActivityByUserName(@RequestBody Activity a,  @PathVariable("username") String username) {
		Activity activity = activityService.updateActivityByUserName(a, username);
		return new ResponseEntity<Activity>(activity, HttpStatus.OK);
	}

	@DeleteMapping("activity/{id}")
	@ApiOperation("Deleting activity by existing id.")
	public ResponseEntity<Void> deleteActivity(@PathVariable("id") Long id) {
		activityService.deleteActivityById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
