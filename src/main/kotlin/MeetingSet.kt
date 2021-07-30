import java.sql.*
import java.time.LocalDate

class MeetingSet
{
    private lateinit var connection: Connection

    init
    {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/meeting_scheduler?" + "user=root&password=ViswaM@01")
    }

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
        val query = "SELECT * FROM MEETING WHERE Title = ${title};"
        val result: ResultSet = statement.executeQuery(query)
        result.next()
        val meeting = Meeting(result.getString("Title"))
        meeting.date = result.getDate("StartDate").toLocalDate()
        meeting.startTime = result.getTime("StartTime")
        meeting.endTime = result.getTime("EndTime")
        meeting.room = result.getInt("Room_Id")
        return meeting
    }
}