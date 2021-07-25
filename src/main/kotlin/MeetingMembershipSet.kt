import java.sql.Connection
import java.sql.DriverManager

class MeetingMembershipSet
{
    private lateinit var connection: Connection

    init
    {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/meeting_scheduler?" + "user=root&password=ViswaM@01")
    }

}