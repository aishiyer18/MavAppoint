package edu.uta.cse.group9.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uta.cse.group9.database.DatabaseManager;
import edu.uta.cse.group9.model.Admin;
import edu.uta.cse.group9.model.Advisor;
import edu.uta.cse.group9.model.Appointment;
import edu.uta.cse.group9.model.Student;
import edu.uta.cse.group9.model.TimeSlot;
import edu.uta.cse.group9.model.User;
import edu.uta.cse.group9.util.JSPMap;
import edu.uta.cse.group9.util.ServletMap;

@WebServlet(ServletMap.PREFERENCES)
public class EditPreferencesController extends Controller {
	private static final long serialVersionUID = 1L;

	private static final String[] PERMISSIONS = new String[] {
			Student.class.getName(),
			Advisor.class.getName(),
			Admin.class.getName()
		};

    public EditPreferencesController() {
        super();
    }

	@Override
	protected void performGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	User user = (User) request.getSession().getAttribute("user");
    	String error = request.getParameter("error");
    	if (error != null) {
    		request.setAttribute("error", error);
    	}
    	String success = request.getParameter("success");
    	if (success != null) {
    		request.setAttribute("success", success);
    	}
    	if (user != null) {
    		try {
           		DatabaseManager dbmgr = new DatabaseManager();
           		if (user.getClass().getName().equals(Admin.class.getName())) {
           			List<Advisor> advisors = dbmgr.getAdvisorList();
           			request.setAttribute("advisors", advisors);
           		}
    		} catch(Exception e) {
				request.setAttribute("error", e.getMessage());
				getServletContext().getRequestDispatcher(JSPMap.HOME_URL).forward(request, response);
				return;	
    		}
    	}
		getServletContext().getRequestDispatcher(JSPMap.PREFERENCES_URL).forward(request, response);
	}

	@Override
	protected void performPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		if (user.getClass().getSimpleName().equals("Admin")) {
			Boolean allowAny = false;
			if (request.getParameter("allowAny") != null) {
				allowAny = new Boolean(request.getParameter("allowAny"));
			}
			Integer numAdvisors = new Integer(request.getParameter("numAdv"));
			DatabaseManager dbMgr = new DatabaseManager();
			try {
				for (int i = 1; i <= numAdvisors; i++) {
					Integer id = new Integer(request.getParameter("id" + i));
					Boolean assigned = false;
					if(request.getParameter("assigned" + i) != null) {
						assigned = true;
					}
					String start = request.getParameter("startVal" + i).substring(0, 1);
					String end = request.getParameter("endVal" + i).substring(0, 1);
					Advisor adv = (Advisor) dbMgr.getUserById(id);
					adv.setAssignedStudentRange(assigned);
					adv.setStartStudent(start);
					adv.setEndStudent(end);
					dbMgr.updateUser(adv);
				}
				request.setAttribute("success", "Preferences updated.");
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
				return;
			} catch(Exception e) {
				request.setAttribute("error", e.getMessage());
				getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
				return;
			}			
		} else {
			request.setAttribute("error", "This functionality is not yet implemented for Student or Advisor.");
			getServletContext().getRequestDispatcher(ServletMap.HOME).forward(request, response);
			return;			
		}
	}

	@Override
	protected String[] authorizedGetUsers() {
		return PERMISSIONS;
	}

	@Override
	protected String[] authorizedPostUsers() {
		return PERMISSIONS;
	}
}
