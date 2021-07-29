import java.awt.Component
import java.awt.Font
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList


data class Employee(val id: Int, val name: String, var visible: Boolean)
{
    companion object
    {
        private val empSet = EmployeeSet()
    }

    fun insert() = empSet.insertEmployee(this)

    fun delete() = empSet.deleteEmployee(id)

    fun changeVisibility()
    {
        empSet.changeVisibility(this.id, !visible)
        visible = !visible
    }

    class EmployeeRenderer(private val employees: ArrayList<Employee>): DefaultListCellRenderer()
    {
        private val label = JLabel()

        override fun getListCellRendererComponent(list: JList<*>?, anyVal: Any?, row: Int, isSelected: Boolean, hasFocus: Boolean): Component?
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            val employee = employees.get(row)
            label.text = "$employee.id}  ${employee.name}"
            return label
        }
    }

}