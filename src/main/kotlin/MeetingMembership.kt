import java.awt.Component
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

enum class Status
{
    ACCEPTED,
    UNDECIDED
}
data class MeetingMembership(val employeeName: String, val meetingTile: String)
{
    var status = Status.UNDECIDED

    class MeetingMembershipRenderer(private val memberships: ArrayList<MeetingMembership>): DefaultTableCellRenderer()
    {
        private val label = JLabel()

        override fun getTableCellRendererComponent(table: JTable, anyVal: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            val membership = memberships.get(row)
            label.text = "Employee: ${membership.employeeName} Status: ${membership.status}"
            return label
        }
    }
}