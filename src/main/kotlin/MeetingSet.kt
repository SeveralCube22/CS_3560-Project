import java.math.BigDecimal
import java.sql.*
import java.sql.Connection
import java.time.LocalDate

class MeetingSet
{
    private val connection: Connection = SqlConnection.connection!!

    fun insert(meeting: Meeting)
    {
        val statement = connection.createStatement()
        val query = "INSERT INTO MEETING VALUES ('${meeting.title}', ${meeting.date}, ${meeting.startTime}, ${meeting.endTime}, ${meeting.room});"
        statement.execute(query)
    }

    fun delete(meetingTitle: String)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM MEETING WHERE Title = '${meetingTitle}';"
        statement.execute(query)
    }

    fun updateTime(title: String, date: LocalDate, startTime: Time, endTime: Time, room: Int)
    {
        val statement = connection.createStatement()
        val query = "UPDATE MEETING SET StartDate = '${date}', StartTime = '${startTime}', EndTime = '${endTime}', Room_Id = ${room} WHERE Title = '${title}';"
        statement.execute(query)
    }

    fun getMeeting(title: String): Meeting
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM MEETING WHERE Title = '${title}';"
        val result: ResultSet = statement.executeQuery(query)
        result.next()
        val meeting = Meeting(result.getString("Title"))
        meeting.date = result.getDate("StartDate")?.toLocalDate()
        meeting.startTime = result.getTime("StartTime")
        meeting.endTime = result.getTime("EndTime")
        meeting.room = result.getInt("Room_Id")
        return meeting
    }

    fun getAvailableTimings(title: String, date: LocalDate, start: Time, end: Time, duration: Double): ArrayList<String>
    {
        val statement = connection.createStatement()
        var sessionStart = start
        val availableTimes = ArrayList<String>()

        val countQuery = "SELECT COUNT(*) " +
                "FROM M_MEMBERSHIP " +
                "WHERE M_Title = '${title}'"
        var result = statement.executeQuery(countQuery)
        result.next()
        val numEmployees = result.getInt(1)

        while(sessionStart < end)
        {
            val hours = duration.toInt()
            println(hours)
            val minutes = (duration - hours) * 60

            val sessionEnd = Time(sessionStart.hours + hours, sessionStart.minutes + minutes.toInt(), 0)

            val meetingQuery = "SELECT COUNT(*) " +
                        "FROM EMPLOYEE AS E JOIN M_MEMBERSHIP ON E.Id = Emp_Id " +
                        "WHERE M_Title = '${title}' " +
                        "AND NOT EXISTS(SELECT * " +
                                       "FROM M_MEMBERSHIP AS MM JOIN MEETING AS M ON M.Title = MM.M_Title " +
                                       "WHERE MM.Emp_Id = E.Id AND '${date}' = M.StartDate " +
                                       "AND '${sessionStart}' >= M.StartTime AND '${sessionStart}' < M.EndTime OR " +
                                       "'${sessionEnd}' > M.StartTime AND '${sessionEnd}' < M.EndTime);"

            val taskQuery = "SELECT COUNT(*) " +
                            "FROM EMPLOYEE AS E JOIN M_MEMBERSHIP ON E.Id = Emp_Id " +
                            "WHERE M_Title = '${title}' " +
                            "AND NOT EXISTS(SELECT * " +
                                           "FROM TASK AS T " +
                                           "WHERE T.Emp_Id = E.Id AND '${date}' = T.StartDate " +
                                           "AND '${sessionStart}' >= T.StartTime AND '${sessionStart}' < T.EndTime OR " +
                                           "'${sessionEnd}' > T.StartTime AND '${sessionEnd}' < T.EndTime);"

            val mResult = statement.executeQuery(meetingQuery)
            mResult.next()
            val mEmp = mResult.getInt(1)
            val tResult = statement.executeQuery(taskQuery)
            tResult.next()
            val tEmp = tResult.getInt(1)
            if(mEmp == numEmployees && tEmp == numEmployees) availableTimes.add(sessionStart.toString())

            sessionStart = sessionEnd
        }
        return availableTimes
    }
}