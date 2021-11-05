import java.sql.Connection
import java.sql.ResultSet
import java.time.LocalDate

class MeetingMembershipSet
{
    private val connection: Connection = SqlConnection.connection!!

    fun insert(membership: MeetingMembership)
    {
        val statement = connection.createStatement()
        val query = "INSERT INTO M_MEMBERSHIP VALUES (${membership.employeeId}, '${membership.employeeName}', " +
                    "'${membership.meetingTitle}', ${membership.isOwner}, " +
                    "${if(membership.isOwner) "TRUE" else "FALSE"});"
        statement.execute(query)
    }

    fun delete(meetingTitle: String)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM MEETING WHERE Title = '${meetingTitle}';"
        statement.execute(query)
    }

    fun leaveMeeting(employeeId: Int, meetingTitle: String)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM M_MEMBERSHIP WHERE Emp_Id = ${employeeId} AND M_Title = '${meetingTitle}';"
        statement.execute(query)
    }

    fun acceptMeeting(employeeId: Int, meetingTitle: String)
    {
        val statement = connection.createStatement()
        val query = "UPDATE M_MEMBERSHIP SET Emp_Status = TRUE " +
                "WHERE Emp_Id = ${employeeId} AND M_Title = '${meetingTitle}';"
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

    fun getMeetingsOnDate(employeeId: Int, date: LocalDate): ArrayList<MeetingMembership>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM (M_MEMBERSHIP AS M_M JOIN EMPLOYEE AS E ON M_M.Emp_Id = E.Id) " +
                    "JOIN MEETING AS M ON M_M.M_Title = M.Title " +
                    "WHERE E.Id = ${employeeId} AND M.StartDate = '${date}';"

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

    fun isEmployeeMember(employeeId: Int, meetingTitle: String): Boolean
    {
        val statement = connection.createStatement()
        val query = "SELECT COUNT(*) AS Total FROM M_MEMBERSHIP WHERE Emp_Id = ${employeeId} AND M_Title = '${meetingTitle}';"

        val result: ResultSet = statement.executeQuery(query)
        result.next()
        return result.getInt("Total") != 0
    }

    fun viewAllEmployeesInMeeting(meetingTitle: String): ArrayList<MeetingMembership>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM M_MEMBERSHIP WHERE M_Title = '${meetingTitle}' AND M_Owner = FALSE;"
        val result: ResultSet = statement.executeQuery(query)
        val memberships = ArrayList<MeetingMembership>()
        while(result.next())
        {
            val membership = MeetingMembership(result.getInt("Emp_Id"), result.getString("Emp_Name"),
                result.getString("M_Title"), result.getBoolean("M_Owner"))
            membership.status = if(result.getBoolean("Emp_Status")) Status.ACCEPTED else Status.UNDECIDED
            memberships.add(membership)
        }
        return memberships
    }

    fun getUnlistedMeetings(employeeId: Int): ArrayList<MeetingMembership>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM (M_MEMBERSHIP AS M_M JOIN EMPLOYEE AS E ON M_M.Emp_Id = E.Id) " +
                "JOIN MEETING AS M ON M_M.M_Title = M.Title " +
                "WHERE E.Id = ${employeeId} AND M.StartDate IS NULL';"

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