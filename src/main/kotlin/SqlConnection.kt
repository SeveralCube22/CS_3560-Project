import java.sql.Connection
import java.sql.DriverManager

class SqlConnection
{
    companion object
    {
        var connection: Connection? = null
        get() {
            if(field == null)
                field = createConnection()
            return field
        }

        private fun createConnection(): Connection
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            return DriverManager.getConnection("jdbc:mysql://us-cdbr-east-04.cleardb.com/heroku_0377c8e06926cca?autoReconnect=true",
                                              "b6db188242310e",
                                           "8668209c")
        }
    }
}