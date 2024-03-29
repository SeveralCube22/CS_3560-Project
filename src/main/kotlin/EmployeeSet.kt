import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet
import java.sql.SQLException;

class EmployeeSet
{
    private val connection: Connection = SqlConnection.connection!!

    fun changeVisibility(id: Int, visibility: Boolean)
    {
        val statement = connection.createStatement()
        val query = "UPDATE EMPLOYEE SET Emp_Visible = ${visibility} WHERE Id = ${id};"
        statement.execute(query)
    }

    fun insertEmployee(employee: Employee)
    {
        val statement = connection.createStatement()
        val query = "INSERT INTO EMPLOYEE VALUES (${employee.id}, '${employee.name}', ${employee.visible});"
        statement.execute(query)
    }

    fun deleteEmployee(id: Int)
    {
        val statement = connection.createStatement()
        val query = "DELETE FROM EMPLOYEE WHERE Id = ${id};"
        statement.execute(query)
    }

    fun viewAllEmployees(): ArrayList<Employee>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM EMPLOYEE WHERE Emp_Visible = TRUE;"
        val result: ResultSet = statement.executeQuery(query)
        val employees = ArrayList<Employee>()
        while(result.next())
            employees.add(Employee(result.getInt("Id"), result.getString("Emp_Name"), result.getBoolean("Emp_Visible")))
        return employees
    }

    fun getEmployeesByName(employeeName: String): ArrayList<Employee>
    {
        val statement = connection.createStatement()
        val query = "SELECT * FROM EMPLOYEE WHERE Emp_Name = '${employeeName}' AND Emp_Visible = TRUE;"
        val result: ResultSet = statement.executeQuery(query)
        val employees = ArrayList<Employee>()
        while(result.next())
            employees.add(Employee(result.getInt("Id"), result.getString("Emp_Name"), result.getBoolean("Emp_Visible")))
        return employees
    }
}