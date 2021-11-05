import java.util.*
import java.sql.Time
import java.time.LocalDate

fun main(args: Array<String>)
{
    val employee = Employee(1, "Viswadeep", true)
    //employee.insert()
    val e2 = Employee(2, "Ben", true)
    val e3 = Employee(3, "Ben", true)
    val e4 = Employee(4, "Gwen", true)
    //e2.insert()
    //e3.insert()
    //e4.insert()

    //Meeting("Meeting #1").updateTimings(LocalDate.of(2021, 7, 30), Time(1, 30, 5), Time(2, 30, 5), 1)
    EmployeePanel(employee)
    EmployeePanel(e2)
    EmployeePanel(e4)
}