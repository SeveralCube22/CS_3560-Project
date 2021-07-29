import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class EmployeePanel(val employee: Employee): JFrame()
{
    private val ownedMeetingModel = DefaultListModel<Meeting>()
    private val invitedMeetingModel = DefaultListModel<Meeting>()

    init
    {
        this.setupComponents()
    }

    private fun setupComponents()
    {
        //setTitle(currentViewedUser.getID() + "'s " + "User View")
        this.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        this.bounds = Rectangle(100, 100, 325, 325)

        val guiParent = JPanel()
        val parentLayout = BoxLayout(guiParent, BoxLayout.X_AXIS)
        guiParent.layout = parentLayout

        val monthPanel = MonthPanel(7, 2021, this)

        val scrollPanelParent = JPanel()
        val scrollLayout = BoxLayout(scrollPanelParent, BoxLayout.Y_AXIS)
        scrollPanelParent.layout = scrollLayout
        val ownedMeetingPanel = JPanel()
        val ownedMeetingLayout = BoxLayout(ownedMeetingPanel, BoxLayout.Y_AXIS)
        ownedMeetingPanel.layout = ownedMeetingLayout
        ownedMeetingPanel.add(getOwnedMeetingsPanel())
        val createButton = JButton()
        createButton.text = "Create Meeting"
        createButton.addMouseListener(object: MouseAdapter()
        {
            override fun mouseClicked(e: MouseEvent?)
            {
                super.mouseClicked(e)
                val meetingTitle = JOptionPane.showInputDialog("Enter meeting title: ") //implement check duplicate in meeting set that returns true or false
                //depending on if meeting title is taken
                val meeting = Meeting(meetingTitle)
                meeting.insert()
                val meetingMembership = MeetingMembership(employee.id, employee.name, meetingTitle, true)
                meetingMembership.status = Status.ACCEPTED
                meetingMembership.createMembership()
                ownedMeetingModel.addElement(meeting)
                MeetingOwnerPanel(meeting)
            }
        })
        ownedMeetingPanel.add(createButton)

        scrollPanelParent.add(ownedMeetingPanel)
        scrollPanelParent.add(getInvitations())
        scrollPanelParent.add(getNotifications())

        guiParent.add(monthPanel)
        guiParent.add(scrollPanelParent)

        guiParent.addMouseListener(object: MouseAdapter()
        {
            override fun mouseClicked(e: MouseEvent)
            {
                super.mouseClicked(e)
                if(SwingUtilities.isRightMouseButton(e))
                {
                    val framePopup = FramePopMenu()
                    framePopup.show(e.component, e.x, e.y)
                }
            }
        })
        this.add(guiParent)
        this.size = Dimension(1280, 720)
        this.minimumSize = Dimension(1280, 720)
        this.isResizable = true
        this.isVisible = true
        this.pack()
    }

    private fun getOwnedMeetingsPanel(): JScrollPane
    {
        val memberships = MeetingMembershipSet().viewOwnedMeetings(employee.id)
        for(membership in memberships) ownedMeetingModel.addElement(Meeting(membership.meetingTile))

        val list = JList<Meeting>(ownedMeetingModel)

        list.background = Color.WHITE
        list.cellRenderer = Meeting.MeetingRenderer()
        val meetings = JScrollPane(list)

        meetings.setBorder(BorderFactory.createTitledBorder("Owned Meetings" ))
        meetings.isOpaque = false
        meetings.isWheelScrollingEnabled = true;
        return meetings
    }

    private fun getInvitations(): JScrollPane
    {
        val memberships = MeetingMembershipSet().viewInvitedMeetings(employee.id)
        for(membership in memberships) invitedMeetingModel.addElement(Meeting(membership.meetingTile))

        val list = JList<Meeting>(invitedMeetingModel)

        list.background = Color.WHITE
        list.cellRenderer = Meeting.MeetingRenderer()
        val meetings = JScrollPane(list)

        meetings.setBorder(BorderFactory.createTitledBorder("Invited Meetings" ))
        meetings.isOpaque = false
        meetings.isWheelScrollingEnabled = true;
        return meetings
    }

    private fun getNotifications(): JScrollPane
    {
        val notificationsModel = DefaultListModel<Meeting>()
        //notificationsModel.size = employee.notifications.size

        val notificationList = JList<Meeting>(notificationsModel)
        notificationList.selectionBackground = Color.GREEN
        notificationList.background = Color.RED

        val notifications = JScrollPane(notificationList)
        notifications.setBorder(BorderFactory.createTitledBorder("Notifications"))
        notifications.isOpaque = false
        notifications.isWheelScrollingEnabled = true;
        return notifications
    }

    private inner class FramePopMenu: JPopupMenu()
    {
        val visibility = JMenuItem("Change Visiblity")
        val refresh = JMenuItem("Refresh")

        init
        {
            visibility.isEnabled = true
            refresh.isEnabled = true

            visibility.addActionListener {
                this@EmployeePanel.employee.changeVisibility()
            }

            refresh.addActionListener {
                ownedMeetingModel.removeAllElements()
                var memberships = MeetingMembershipSet().viewOwnedMeetings(employee.id)
                for(membership in memberships) ownedMeetingModel.addElement(Meeting(membership.meetingTile))

                invitedMeetingModel.removeAllElements()
                memberships = MeetingMembershipSet().viewInvitedMeetings(employee.id)
                for(membership in memberships) invitedMeetingModel.addElement(Meeting(membership.meetingTile))
            }

            this.add(visibility)
            this.add(refresh)
        }
    }
}