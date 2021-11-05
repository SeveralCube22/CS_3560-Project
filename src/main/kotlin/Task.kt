import java.sql.Time
import java.time.LocalDate

/**
 * Represents a task
 * @author Viswadeep Manam
 **/
class Task(val employeeId: Int, val taskDescription: String, val startDate: LocalDate, val startTime: Time, val endTime: Time)
{
    /**
     * Static companion object for Task Set.
     **/
    companion object
    {
        val taskSet = TaskSet()
    }

    /**
     * Inserts this task into database
     **/
    fun insert() = taskSet.insert(this)
    fun delete() = taskSet.delete(employeeId, startDate, startTime)
}