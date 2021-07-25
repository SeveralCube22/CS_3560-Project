import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class EmployeeSet
{
    private lateinit var connection: Connection

    init
    {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/meeting_scheduler?" + "user=root&password=ViswaM@01")
    }

    fun changeVisibility(id: Int, visibility: Boolean)
    {
        val statement = connection.createStatement()
        val query = "UPDATE EMPLOYEE SET Emp_Visible = ${visibility} WHERE Id = ${id}"
        statement.execute(query)
    }

    fun insertEmployee(employee: Employee)
    {
        val statement = connection.createStatement()
        val query = "INSERT INTO EMPLOYEE VALUES (${employee.id} ${employee.name} ${employee.visible})"
        statement.execute(query)
    }

    fun deleteEmployee(id: Int)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM EMPLOYEE WHERE Id = ${id}"
        statement.execute(query)
    }
}