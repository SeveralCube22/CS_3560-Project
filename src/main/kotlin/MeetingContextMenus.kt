import javax.swing.DefaultListModel
import javax.swing.JMenuItem
import javax.swing.JPopupMenu

class OwnedMeetingsMenu(membership: MeetingMembership, model: DefaultListModel<MeetingMembership>): JPopupMenu()
{
    val modify = JMenuItem("Modify")
    val delete = JMenuItem("Delete")

    init
    {
        delete.addActionListener{
            membership.deleteMeeting()
            updateListModel(model, membership.employeeId, true)
        }

        this.add(modify)
        this.add(delete)
    }
}

class InvitedMeetingsMenu(membership: MeetingMembership, model: DefaultListModel<MeetingMembership>): JPopupMenu()
{
    val accept = JMenuItem("Accept")
    val decline = JMenuItem("Decline")

    init
    {
        accept.addActionListener {
            membership.acceptMeeting()
            updateListModel(model, membership.employeeId, false)
        }

        decline.addActionListener {
            membership.leaveMeeting()
            updateListModel(model, membership.employeeId, false)
        }
        this.add(accept)
        this.add(decline)
    }
}

private fun updateListModel(model: DefaultListModel<MeetingMembership>, employeeId: Int, owned: Boolean) { //pass in enum
    model.removeAllElements()
    var memberships = if (owned) MeetingMembershipSet().viewOwnedMeetings(employeeId) else MeetingMembershipSet().viewInvitedMeetings(employeeId)
    for (membership in memberships)
        model.addElement(membership)
}