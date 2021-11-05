import java.sql.Connection
import java.sql.Time
import java.time.LocalDate

class TaskSet
{
    private val connection: Connection = SqlConnection.connection!!

    fun insert(task: Task)
    {
        val statement = connection.createStatement()
        val query = "INSERT INTO TASK VALUES (${task.employeeId}, '${task.taskDescription}', " +
                "'${task.startDate}', '${task.startTime}', " +
                "'${task.endTime}');"
        statement.execute(query)
    }

    fun delete(employeeId: Int, startDate: LocalDate, startTime: Time)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM TASK WHERE Emp_Id = ${employeeId} AND StartDate = '${startDate}' AND StartTime = '${startTime}';"
        statement.execute(query)
    }
}