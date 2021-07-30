import java.awt.Color
import java.awt.Component
import java.awt.Font
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList

enum class Status
{
    ACCEPTED,
    UNDECIDED
}
data class MeetingMembership(val employeeId: Int, val employeeName: String, val meetingTile: String, val isOwner: Boolean)
{
    var status = Status.UNDECIDED

    companion object
    {
        private val membershipSet = MeetingMembershipSet()
    }

    fun createMembership() = membershipSet.insert(this)

    fun leaveMeeting() = membershipSet.leaveMeeting(employeeId, meetingTile)

    fun acceptMeeting()
    {
        membershipSet.acceptMeeting(employeeId, meetingTile)
        status = Status.ACCEPTED
    }

    fun deleteMeeting()
    {
        if(isOwner) MeetingMembershipSet().delete(meetingTile)
    }

    class MeetingMembershipRenderer(): DefaultListCellRenderer()
    {
        private val label = JLabel()

        override fun getListCellRendererComponent(list: JList<*>?, membership: Any?, row: Int, isSelected: Boolean, hasFocus: Boolean): Component?
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            if(membership is MeetingMembership)
                label.text = "Employee: ${membership.employeeName} Status: ${membership.status}"
            return label
        }
    }

    class MeetingRenderer: DefaultListCellRenderer()
    {
        private val label = JLabel()

        override fun getListCellRendererComponent(list: JList<*>?, membership: Any?, row: Int, isSelected: Boolean, hasFocus: Boolean): Component?
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            label.isOpaque = true
            if(membership is MeetingMembership)
                label.text = "Title: ${membership.meetingTile}"
            if(isSelected)
                label.background = Color.GREEN
            else
                label.background = Color.WHITE
            return label
        }
    }

}