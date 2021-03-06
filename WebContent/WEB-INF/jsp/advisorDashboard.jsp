<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.uta.cse.group9.util.LinkMap"%>
<<<<<<< HEAD

<div>
	<h1>Advisor Dashboard</h1>
	<h5>Upcoming Appointments</h5>

	<div class="row">
		<div class="panel panel-default">
			<table class="table blacktable">
				<thead>
					<tr>
						<th>ID</th>
						<th>Student</th>
						<th>Reason</th>
						<th>Start</th>
						<th>End</th>
						<th>Status</th>
						<th><a href="<%=LinkMap.APPOINTMENT_CANCEL%>">Appointment
								Cancel</a></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${appointments}" var="entry">
						<tr>
							<td>${entry.getId()}</td>
							<td>${entry.getStudent().getLastName()},
								${entry.getStudent().getFirstName()}</td>
							<td>${entry.getTask().getName()}</td>
							<td>${entry.getStartTime().toString()}</td>
							<td>${entry.getEndTime().toString()}</td>
							<td>${entry.getStatus().description()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			=======
			<div class="container">
				<div class="row">
					<h1>${user.getFirstName()}'sDashboard</h1>
					<div class="col-xs-12 col-md-8 col-md-offset-2">
						<div class="panel panel-default">
							<div class="panel-heading">Basic Info</div>
							<div class="panel-body">
								<div class="col-xs-6 col-md-4">Name:
									${user.getLastName()}, ${user.getFirstName()}</div>
								<div class="col-xs-6 col-md-4">UTA ID: ${user.getUtaId()}
								</div>
								<div class="col-xs-6 col-md-4">Email: ${user.getEmail()}</div>
							</div>
						</div>
					</div>
				</div>
				<h3>Upcoming Appointments</h3>
				<div class="row">
					<div class="col-xs-12">
						<div class="panel panel-default">
							<table class="table blacktable">
								<thead>
									<tr>
										<th>ID</th>
										<th>Student</th>
										<th>Reason</th>
										<th>Start</th>
										<th>End</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${appointments}" var="entry">
										<tr>
											<td><a
												href="<%=LinkMap.APPOINTMENT_DETAIL%>?appt_id=${entry.getId()}">Details</a></td>
											<td>${entry.getStudent().getLastName()},
												${entry.getStudent().getFirstName()}</td>
											<td>${entry.getTask().getName()}</td>
											<td>${entry.getStartTime().toString()}</td>
											<td>${entry.getEndTime().toString()}</td>
											<td>${entry.getStatus().description()}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						>>>>>>> e1aa1a3ab10d0690e25ba2634092a295c75aaf27
					</div>
				</div>

				<h3>Time Slots</h3>
				<div class="row">
					<div class="col-xs-12">
						<div class="panel panel-default">
							<table class="table blacktable">
								<thead>
									<tr>
										<th>ID</th>
										<th>Start</th>
										<th>End</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${timeslots}" var="entry">
										<tr>
											<td>${entry.getId()}</td>
											<td>${entry.getStartTime().toString()}</td>
											<td>${entry.getEndTime().toString()}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>