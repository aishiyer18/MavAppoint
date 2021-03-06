package edu.uta.cse.group9.controller.async;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(urlPatterns = ServletMap.ASYNC_TIME_SLOTS, asyncSupported = true)
public class GetAdvisingTasksController extends AyncController {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Advisor.class.getName(),
			Student.class.getName()
		};
	
	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// extract parameters
		Integer advisorId = Integer.parseInt(request.getParameter("advisor_id"));
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime;
		Date endTime;
		try {
			startTime = format.parse(start);
			endTime = format.parse(end);
		} catch (ParseException e) {
			System.out.println("Error parsing datetime.");
			startTime = null;
			endTime = null;
		}
		
		DatabaseManager dbmgr = new DatabaseManager();
	    List<Object> result = new ArrayList<Object>();
	    String json = "";
		try {
			Advisor advisor = (Advisor) dbmgr.getUserById(advisorId);
			List<TimeSlot> timeslots = dbmgr.getTimeSlotsForAdvisor(advisor, startTime, endTime);
			if (timeslots != null) {
				for (TimeSlot timeslot : timeslots) {
					Map<String,String> map = new HashMap<String,String>();
					map.put("id", String.valueOf(timeslot.getId()));
					map.put("start", timeslot.getStartTime().toString());
					map.put("end", timeslot.getEndTime().toString());
					map.put("color", "blue");
					result.add(map);
				}
			}
		    json = gson.toJson(result);
		} catch(Exception e) {
			 Map<String,String> map = new HashMap<String,String>();
			 map.put("error", e.getMessage());
			 json = gson.toJson(map);
		}
	    response.setContentType("text/html");
	    response.getWriter().write(json);
	}

	@Override
	protected String[] authorizedGetUsers() {
		return null;
	}

	@Override
	protected String[] authorizedPostUsers() {
		return PERMISSIONS;
	}
}
