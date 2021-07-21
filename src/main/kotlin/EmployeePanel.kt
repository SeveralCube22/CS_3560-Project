import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.Window
import javax.swing.*


class EmployeePanel(val employee: Employee): JFrame()
{
    init
    {
        //setTitle(currentViewedUser.getID() + "'s " + "User View")
        this.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        this.bounds = Rectangle(100, 100, 477, 435)

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
        ownedMeetingPanel.add(createButton)

        scrollPanelParent.add(ownedMeetingPanel)
        scrollPanelParent.add(getInvitations())
        scrollPanelParent.add(getNotifications())

        guiParent.add(monthPanel)
        guiParent.add(scrollPanelParent)
        this.add(guiParent)
        this.size = Dimension(1280, 720)
        this.minimumSize = Dimension(1280, 720)
        this.isResizable = true
        this.isVisible = true
        this.pack()
        println(monthPanel.size)
    }


    private fun getOwnedMeetingsPanel(): JScrollPane
    {
        val meetingModel = DefaultListModel<Meeting>()
        meetingModel.size =  employee.ownedMeetings.size

        val meetingList = JList<Meeting>(meetingModel)
        meetingList.selectionBackground = Color.GREEN
        meetingList.background = Color.RED

        val meetings = JScrollPane(meetingList)
        meetings.setBorder(BorderFactory.createTitledBorder("Owned Meetings" ))
        meetings.isOpaque = false
        meetings.isWheelScrollingEnabled = true;
        return meetings
    }

    private fun getInvitations(): JScrollPane
    {
        val meetingModel = DefaultListModel<Meeting>()
        meetingModel.size = employee.invitedMeetings.size

        val meetingList = JList<Meeting>(meetingModel)
        meetingList.selectionBackground = Color.GREEN
        meetingList.background = Color.RED

        val meetings = JScrollPane(meetingList)
        meetings.setBorder(BorderFactory.createTitledBorder("Invitations"))
        meetings.isOpaque = false
        meetings.isWheelScrollingEnabled = true;
        return meetings
    }

    private fun getNotifications(): JScrollPane
    {
        val notificationsModel = DefaultListModel<Meeting>()
        notificationsModel.size = employee.notifications.size

        val notificationList = JList<Meeting>(notificationsModel)
        notificationList.selectionBackground = Color.GREEN
        notificationList.background = Color.RED

        val notifications = JScrollPane(notificationList)
        notifications.setBorder(BorderFactory.createTitledBorder("Notifications"))
        notifications.isOpaque = false
        notifications.isWheelScrollingEnabled = true;
        return notifications
    }

    //TODO: DefaultListCellRenderer for Meetings
}