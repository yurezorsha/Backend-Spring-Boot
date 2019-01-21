package com.mainsoft.backend.service;

import java.util.List;

import com.mainsoft.backend.entity.Activity;
import com.mainsoft.backend.entity.Report;

public interface ActivityService {

	List<Activity> getAllActivityByUserName(String username);

	Activity getActivityById(Long id);

	Activity addActivityByUserName(Activity a, String username);

	Activity updateActivityByUserName(Activity a, String username);

	void deleteActivityById(Long id);

	boolean existActivityById(Long id);

	List<Report> getReportByUserName(String username);

}
