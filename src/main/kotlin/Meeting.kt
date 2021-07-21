import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

enum class Status
{
    ACCEPTED,
    UNDECIDED
}

data class Meeting(val owner: Employee, val title: String)
{
    lateinit var date: Date
    lateinit var startTime: Time
    var duration: Double = 0.0
    lateinit var room: Room
    val employees = HashMap<Employee, Status>()

    fun acceptMeeting(employee: Employee)
    {
        employees[employee] = Status.ACCEPTED
    }

    fun rejectMeeting(employee: Employee) = employees.remove(employee)

    //TODO: Calculate timeslot based on accpeted employees
    //TODO: Calculate appropriate room

    //TODO: DefaultListCellRenderer for Employees
}