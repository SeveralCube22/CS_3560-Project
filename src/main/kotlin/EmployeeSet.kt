class EmployeeSet
{
    private val employees = HashMap<Int, Employee>()

    fun insertEmployee(employee: Employee) = employees.put(employee.id, employee)
    fun deleteEmployee(employee: Employee) = employees.remove(employee.id)
    fun getEmployee(id: Int): Employee? = employees.getOrDefault(id, null)
}