import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.ResultSet

class MeetingMembershipSet
{
    private lateinit var connection: Connection

    init
    {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/meeting_scheduler?" + "user=root&password=ViswaM@01")
    }

    fun insert(membership: MeetingMembership)
    {
        val statement = connection.createStatement()
        val query = "INSERT INTO M_MEMBERSHIP VALUES (${membership.employeeId}, '${membership.employeeName}', " +
                    "'${membership.meetingTile}', ${membership.isOwner}, " +
                    "${if(membership.isOwner) "TRUE" else "FALSE"});"
        statement.execute(query)
    }

    fun deleteOwnedMeeting(meetingTitle: String)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM MEETING WHERE Title = ${meetingTitle};"
        statement.execute(query)
    }

    fun leaveMeeting(employeeId: Int, meetingTitle: String)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM M_MEMBERSHIP WHERE Emp_Id = ${employeeId} AND M_Title = ${meetingTitle};"
        statement.execute(query)
    }

    fun acceptMeeting(employeeId: Int, meetingTitle: String)
    {
        val statement = connection.createStatement()
        val query = "UPDATE M_MEMBERSHIP SET Emp_Status = A " +
                "WHERE Emp_Id = ${employeeId} M_Title = ${meetingTitle};"
        statement.execute(query)
    }

    fun viewInvitedMeetings(employeeId: Int): ArrayList<MeetingMembership>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM M_MEMBERSHIP WHERE Emp_Id = ${employeeId} AND Emp_Status = FALSE;"
        val result: ResultSet = statement.executeQuery(query)
        val memberships = ArrayList<MeetingMembership>()
        while(result.next())
        {
            val membership = MeetingMembership(result.getInt("Emp_Id"), result.getString("Emp_Name"),
                                              result.getString("M_Title"), result.getBoolean("M_Owner"))
            membership.status = Status.UNDECIDED
            memberships.add(membership)
        }
        return memberships
    }

    fun viewAttendingMeetings(employeeId: Int): ArrayList<MeetingMembership>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM M_MEMBERSHIP WHERE Emp_Id = ${employeeId} AND Emp_Status = TRUE;"
        val result: ResultSet = statement.executeQuery(query)
        val memberships = ArrayList<MeetingMembership>()
        while(result.next())
        {
            val membership = MeetingMembership(result.getInt("Emp_Id"), result.getString("Emp_Name"),
                result.getString("M_Title"), result.getBoolean("M_Owner"))
            membership.status = Status.ACCEPTED
            memberships.add(membership)
        }
        return memberships
    }

    fun viewOwnedMeetings(employeeId: Int): ArrayList<MeetingMembership>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM M_MEMBERSHIP WHERE Emp_Id = ${employeeId} AND M_Owner = TRUE;"
        val result: ResultSet = statement.executeQuery(query)
        val memberships = ArrayList<MeetingMembership>()
        while(result.next())
        {
            val membership = MeetingMembership(result.getInt("Emp_Id"), result.getString("Emp_Name"),
                result.getString("M_Title"), result.getBoolean("M_Owner"))
            membership.status = Status.ACCEPTED
            memberships.add(membership)
        }
        return memberships
    }

    fun getMeetingsOnDate(employeeId: Int, date: Date): ArrayList<MeetingMembership>
    {
        val statement = connection.createStatement()
        val query = "SELECT * StartDate FROM (M_MEMBERSHIP AS M_M JOIN EMPLOYEE AS E) ON M_M.Emp_Id = E.Id) " +
                    "JOIN MEETING AS M ON M_M.M_Title = M.Title " +
                    "WHERE E.Id = ${employeeId} AND M.StartDate = ${date};"

        val result: ResultSet = statement.executeQuery(query)
        val memberships = ArrayList<MeetingMembership>()
        while(result.next())
        {
            if(result.getBoolean("M_M.Emp_Status"))
            {
                val membership = MeetingMembership(
                    result.getInt("M_M.Emp_Id"), result.getString("M_M.Emp_Name"),
                    result.getString("M_M.M_Title"), result.getBoolean("M_M.M_Owner"))
                membership.status = Status.ACCEPTED
                memberships.add(membership)
            }
        }
        return memberships
    }
}