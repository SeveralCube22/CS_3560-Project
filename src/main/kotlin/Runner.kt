import java.util.*

fun main(args: Array<String>)
{
    val emp = Employee(1, "Test")
    val window = EmployeePanel(emp)

    val meeting = Meeting(emp, "Test Meeting")
    val window2 = MeetingOwnerPanel(meeting)
}