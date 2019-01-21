package com.mainsoft.backend.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainsoft.backend.entity.Activity;
import com.mainsoft.backend.entity.Report;
import com.mainsoft.backend.entity.User;
import com.mainsoft.backend.exceptions.EntityNotFoundException;
import com.mainsoft.backend.repository.ActivityRepository;
import com.mainsoft.backend.repository.UserRepository;
import com.mainsoft.backend.security.jwt.JwtProvider;

@Service
public class ActivityServiceImpl implements ActivityService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public List<Activity> getAllActivityByUserName(String username) {

		if (!userRepository.existsByUsername(username)) {
			logger.error("User with username: " + username + " wasn't found!");
			throw new EntityNotFoundException("User with id: " + username + "wasn't found!");
		}

		return activityRepository.findAllByUserName(username);
	}

	@Override
	public Activity getActivityById(Long id) {

		if (!existActivityById(id)) {
			logger.error("Activity with id: " + id + " wasn't found!");
			throw new EntityNotFoundException("Activity with id: " + id + " wasn't found!");
		}

		return activityRepository.findById(id).get();
	}

	@Override
	public Activity addActivityByUserName(Activity a, String username) {

		if (!userRepository.existsByUsername(username)) {
			logger.error("User with username: " + username + " wasn't found!");
			throw new EntityNotFoundException("User with id: " + username + " wasn't found!");
		}

		User user = userRepository.findByUsername(username).get();
		a.setUser(user);

		Activity activity = activityRepository.save(a);
		logger.info("Activity with id: " + activity.getId() + " has been added for User with username: " + username);

		return activity;
	}

	@Override
	public Activity updateActivityByUserName(Activity a, String username) {

		if (!userRepository.existsByUsername(username)) {
			logger.error("User with username: " + username + " wasn't found!");
			throw new EntityNotFoundException("User with id: " + username + " wasn't found!");
		}

		User user = userRepository.findByUsername(username).get();
		a.setUser(user);

		Activity activity = activityRepository.save(a);
		logger.info("Activity with id: " + activity.getId() + " has been updated for User with username: " + username);

		return activity;
	}

	@Override
	public void deleteActivityById(Long id) {

		if (!existActivityById(id)) {
			logger.error("Activity with id: " + id + " wasn't found!");
			throw new EntityNotFoundException("Activity with id: " + id + " wasn't found!");
		}

		logger.info("Activity with id: " + id + " has been deleted!");
		activityRepository.deleteById(id);

	}

	@Override
	public boolean existActivityById(Long id) {

		return activityRepository.existsById(id);
	}

	/**
	 * get report for all weeks by username
	 */
	@Override
	public List<Report> getReportByUserName(String username) {

		if (!userRepository.existsByUsername(username)) {
			logger.error("User with username: " + username + " wasn't found!");
			throw new EntityNotFoundException("User with id: " + username + " wasn't found!");
		}

		List<Activity> lst = activityRepository.findAllByUserName(username);
		List<Report> report = new ArrayList<>();

		report = setTitle(createReport(lst, report));

		return report;
	}

	/**
	 * getting first day of week for any date
	 */
	private Date startOfWeek(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		return cal.getTime();

	}

	/**
	 * getting end of week from start week
	 */
	private Date endOfWeek(Date startWeek) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(startWeek);
		cal.add(Calendar.DATE, 6);

		return cal.getTime();

	}

	/**
	 * checking first date and second date from the same week
	 */
	private boolean isCurrentWeek(Date firstDate, Date secondDate) {

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(firstDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(secondDate);

		int weeka = cal1.get(Calendar.WEEK_OF_YEAR);
		int weekb = cal2.get(Calendar.WEEK_OF_YEAR);

		System.out.println(weeka + " " + weekb);

		if (weeka == weekb) {
			return true;

		} else {
			return false;
		}

	}

	/**
	 * creating one item in report by lst
	 */
	private void addReportItem(List<Activity> lst, List<Report> report) {

		double totalDistance = 0;
		double averageSpeed = 0;
		double averageTime = 0;
		int i = 0;
		for (Activity curr : lst) {

			totalDistance += curr.getDistance();
			averageSpeed += (curr.getDistance() * 60) / curr.getRunTime();
			averageTime += curr.getRunTime();
			i++;

		}

		Activity curr = lst.get(0);

		Report rep = new Report();
		rep.setStartWeek(startOfWeek(curr.getRunDate()));
		rep.setEndWeek(endOfWeek(startOfWeek(curr.getRunDate())));
		rep.setAverageSpeed(averageSpeed / i);
		rep.setAverageTime(averageTime / i);
		rep.setTotalDistance(totalDistance);

		report.add(rep);

	}

	/**
	 * creating report for lst
	 */
	private List<Report> createReport(List<Activity> lst, List<Report> report) {
		Activity first = lst.get(0);
		Activity last = lst.get(lst.size() - 1);

		if (isCurrentWeek(first.getRunDate(), last.getRunDate())) {

			addReportItem(lst, report);

		} else {

			for (int i = 0; i < lst.size(); i++) {

				if (!isCurrentWeek(lst.get(i).getRunDate(), lst.get(i + 1).getRunDate())) {

					List<Activity> sublst = lst.subList(0, i + 1);
					addReportItem(sublst, report);
					lst.removeAll(sublst);

					return createReport(lst, report);

				}

			}

		}

		return report;

	}

	/**
	 * set title for every item
	 */
	private List<Report> setTitle(List<Report> list) {
		int i = 1;
		for (Report report : list) {
			report.setTitle("Week " + i);
			i++;
		}

		return list;

	}

}
