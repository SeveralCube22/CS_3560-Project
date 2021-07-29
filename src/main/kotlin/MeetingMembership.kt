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
        if(isOwner) membershipSet.deleteOwnedMeeting(meetingTile)
    }

    class MeetingMembershipRenderer(private val memberships: ArrayList<MeetingMembership>): DefaultListCellRenderer()
    {
        private val label = JLabel()

        override fun getListCellRendererComponent(list: JList<*>?, anyVal: Any?, row: Int, isSelected: Boolean, hasFocus: Boolean): Component?
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            val membership = memberships.get(row)
            label.text = "Employee: ${membership.employeeName} Status: ${membership.status}"
            return label
        }
    }
}