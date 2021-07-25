import java.awt.Component
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

data class Employee(val id: Int, val name: String, var visible: Boolean)
{
    companion object
    {
        val empSet = EmployeeSet()
    }

    fun changeVisibility()
    {
        empSet.changeVisibility(this.id, !visible)
        visible = !visible
    }

    class EmployeeRenderer(private val employees: ArrayList<Employee>): DefaultTableCellRenderer()
    {
        private val label = JLabel()

        override fun getTableCellRendererComponent(table: JTable, anyVal: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            val employee = employees.get(row)
            label.text = "$employee.id}  ${employee.name}"
            return label
        }
    }

}