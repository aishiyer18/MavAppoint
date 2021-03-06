package edu.uta.cse.group9.database.command;
import java.sql.SQLException;

import edu.uta.cse.group9.model.TimeSlot;

public class UpdateTimeSlotCommand extends SQLCommand {

	private TimeSlot timeslot;

	public UpdateTimeSlotCommand(TimeSlot timeslot) {
		this.timeslot = timeslot;
	}

	@Override
	void queryDB() throws Exception {
		try {
			String query = String.format("UPDATE %s.%s SET advisor_id=?, start_time=?, end_time=? WHERE id=?", config.DBNAME, TimeSlot.TABLE_NAME);
			statement = connection.prepareStatement(query);
	
			java.sql.Timestamp startDate = new java.sql.Timestamp(this.timeslot.getStartTime().getTime());
			java.sql.Timestamp endDate = new java.sql.Timestamp(this.timeslot.getEndTime().getTime());
			statement.setInt(1, this.timeslot.getAdvisor().getId());	
			statement.setTimestamp(2, startDate);
			statement.setTimestamp(3, endDate);
			statement.setInt(4, this.timeslot.getId());
			
			statement.executeUpdate();
			result = true;
		} catch(SQLException e) {
			result = false;
			throw new Exception(e.getMessage());
		}
	}

	@Override
	void processResult() throws Exception {	
	}
}
