import javax.swing.DefaultListModel
import javax.swing.JMenuItem
import javax.swing.JPopupMenu

enum class OwnershipType
{
    OWNED,
    INVITED,
    ATTENDING
}

class OwnedMeetingsMenu(membership: MeetingMembership, model: DefaultListModel<MeetingMembership>): JPopupMenu()
{
    val modify = JMenuItem("Modify")
    val delete = JMenuItem("Delete")

    init
    {
        modify.addActionListener {
            MeetingOwnerPanel(MeetingSet().getMeeting(membership.meetingTitle))
        }
        delete.addActionListener{
            membership.deleteMeeting()
            updateListModel(model, membership.employeeId, OwnershipType.OWNED)
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
            updateListModel(model, membership.employeeId, OwnershipType.INVITED)
        }

        decline.addActionListener {
            membership.leaveMeeting()
            updateListModel(model, membership.employeeId, OwnershipType.ATTENDING)
        }
        this.add(accept)
        this.add(decline)
    }
}

class AttendingMeetingsMenu(membership: MeetingMembership, model: DefaultListModel<MeetingMembership>): JPopupMenu()
{
    val leave =  JMenuItem("Leave")

    init
    {
        leave.addActionListener {
            membership.leaveMeeting()
        }

        this.add(leave)
    }
}

private fun updateListModel(model: DefaultListModel<MeetingMembership>, employeeId: Int, type: OwnershipType) { //pass in enum
    model.removeAllElements()
    var memberships = if (type == OwnershipType.OWNED) MeetingMembershipSet().viewOwnedMeetings(employeeId)
                      else if(type == OwnershipType.INVITED) MeetingMembershipSet().viewInvitedMeetings(employeeId)
                      else MeetingMembershipSet().viewAttendingMeetings(employeeId)
    model.addAll(memberships)
}