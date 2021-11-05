import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.math.BigDecimal
import java.sql.Time
import java.time.LocalDate
import javax.swing.*


class MeetingOwnerPanel(val meeting: Meeting): JFrame()
{
    private val membershipsModel = DefaultListModel<MeetingMembership>()

    init
    {
        this.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        this.bounds = Rectangle(100, 100, 477, 435)

        val guiParent = JPanel()
        val layout = BoxLayout(guiParent, BoxLayout.X_AXIS)
        guiParent.layout = layout

        val infoPanel = JPanel(GridBagLayout())
        val infoConstraints = GridBagConstraints()

        val font = Font(JLabel().font.name, Font.BOLD, 15)

        val meetingTitleLabel = JLabel("Title: " + meeting.title)
        meetingTitleLabel.font = font

        val dateLabel = JLabel("Date: ")
        dateLabel.font = font
        val dateField = JTextField()
        dateField.columns = 10
        dateField.text = meeting.date?.toString() ?: ""
        val datePanel = JPanel(GridBagLayout())

        val durationLabel = JLabel("Duration: ")
        durationLabel.font = font
        val durationField = JTextField()
        durationField.columns = 10
        durationField.text = ""
        val durationPanel = JPanel(GridBagLayout())

        val startTimeLabel = JLabel("Start Time: ")
        startTimeLabel.font = font
        val startTimeField = JComboBox<String>()
        startTimeField.selectedItem = meeting.startTime?.toString() ?: ""
        val startTimePanel = JPanel(GridBagLayout())

        val constraints = GridBagConstraints()
        constraints.weightx = .5
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 0
        constraints.gridy = 0
        datePanel.add(dateLabel, constraints)
        durationPanel.add(durationLabel, constraints)
        startTimePanel.add(startTimeLabel, constraints)

        constraints.gridx = 1
        datePanel.add(dateField, constraints)
        durationPanel.add(durationField, constraints)
        startTimePanel.add(startTimeField, constraints)

        val roomName = JLabel("Room: ")
        roomName.font = font

        val meetingButton = JButton()
        meetingButton.text = "Update Meeting"
        meetingButton.addMouseListener(object: MouseAdapter()
        {
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                val room = 1 //TODO Work on room functionality
                val start = Time.valueOf(startTimeField.selectedItem.toString())
                val duration = durationField.text.toDouble()
                val hours = duration.toInt()
                val minutes = (duration - hours) * 60
                val end = Time(start.hours + hours, start.minutes + minutes.toInt(), 0)

                meeting.updateTimings(LocalDate.parse(dateField.text), start, end, room)
            }
        })

        val timingsButton = JButton()
        timingsButton.addMouseListener(object: MouseAdapter()
        {
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                if(!dateField.text.isEmpty() && !durationField.text.isEmpty())
                {
                    val timings = MeetingSet().getAvailableTimings(meeting.title, LocalDate.parse(dateField.text), Time(6, 0, 0),
                                                                  Time(21, 0, 0), durationField.text.toDouble()) //Passing default values to time. Allow user to decide?
                    println("HERE")
                    for(time in timings) startTimeField.addItem(time)
                }
            }
        })
        timingsButton.text = "Find Timings"

        infoConstraints.insets = Insets(10, 10, 10, 10)
        infoConstraints.fill = GridBagConstraints.VERTICAL
        infoConstraints.anchor = GridBagConstraints.WEST

        infoConstraints.gridx = 0
        infoConstraints.gridy = 0
        infoPanel.add(meetingTitleLabel, infoConstraints)
        infoConstraints.gridy = 1
        infoPanel.add(datePanel, infoConstraints)
        infoConstraints.gridy = 2
        infoPanel.add(durationPanel, infoConstraints)
        infoConstraints.gridy = 3
        infoPanel.add(startTimePanel, infoConstraints)
        infoConstraints.gridy = 4
        infoPanel.add(roomName, infoConstraints)
        infoConstraints.gridy = 8
        infoPanel.add(meetingButton, infoConstraints)
        infoConstraints.gridx = 1
        infoPanel.add(timingsButton, infoConstraints)

        val scrollPanelParent = JPanel(GridLayout(2, 1))
        scrollPanelParent.add(getEmployeesPanel())
        scrollPanelParent.add(getRoomsPanel())

        guiParent.addMouseListener(object: MouseAdapter()
        {
            override fun mouseClicked(e: MouseEvent)
            {
                if(SwingUtilities.isRightMouseButton(e))
                {
                    val framePopup = FramePopMenu()
                    framePopup.show(e.component, e.x, e.y)
                }
            }
        })

        guiParent.add(infoPanel)
        guiParent.add(scrollPanelParent)
        this.add(guiParent)
        this.size = Dimension(720, 1000)
        this.minimumSize = Dimension(720, 1000)
        this.isVisible = true
        this.isResizable = false
        this.pack()
    }

    private fun getEmployeesPanel(): JPanel
    {
        val panel = JPanel(GridBagLayout())
        panel.setBorder(BorderFactory.createTitledBorder("Employees"))
        val constraints = GridBagConstraints()

        val search = JTextField()
        search.addActionListener {
            val employeePopup = EmployeePopMenu(search.text)
            employeePopup.show(search, search.getBounds().x, search.getBounds().y)
        }

        var memberships = MeetingMembershipSet().viewAllEmployeesInMeeting(this@MeetingOwnerPanel.meeting.title)
        membershipsModel.addAll(memberships)

        val employeeList = JList<MeetingMembership>(membershipsModel)
        employeeList.selectionBackground = Color.GREEN
        employeeList.background = Color.WHITE
        employeeList.cellRenderer = MeetingMembership.MeetingMembershipRenderer()

        val employees = JScrollPane(employeeList)
        employees.isOpaque = false
        employees.isWheelScrollingEnabled = true;

        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.weightx = 1.0
        constraints.gridx = 0
        constraints.gridy = 0
        panel.add(search, constraints)
        constraints.fill = GridBagConstraints.VERTICAL
        constraints.weighty = 1.0
        constraints.gridy = 1
        panel.add(employees, constraints)
        return panel
    }

    private fun getRoomsPanel(): JScrollPane
    {
        val roomModel = DefaultListModel<Meeting>()
        //roomModel.size =  meeting.employees.size

        val roomList = JList<Meeting>(roomModel)
        roomList.selectionBackground = Color.GREEN
        roomList.background = Color.RED

        val rooms = JScrollPane(roomList)
        rooms.setBorder(BorderFactory.createTitledBorder("Rooms"))
        rooms.isOpaque = false
        rooms.isWheelScrollingEnabled = true;
        return rooms
    }

    private inner class EmployeePopMenu(employeeName: String): JPopupMenu()
    {
        val potEmployees = EmployeeSet().getEmployeesByName(employeeName)

        init
        {
            val membership = MeetingMembershipSet()
            for(employee in potEmployees)
            {
                if(!membership.isEmployeeMember(employee.id, this@MeetingOwnerPanel.meeting.title))
                {
                    val menu = JMenuItem("Id: ${employee.id} Name: ${employeeName}")
                    menu.addActionListener{
                        val member = MeetingMembership(employee.id, employee.name, this@MeetingOwnerPanel.meeting.title, false)
                        member.createMembership()
                        membershipsModel.addElement(member)
                    }
                    this.add(menu)
                }
            }
        }
    }

    private inner class FramePopMenu: JPopupMenu()
    {
        val refresh = JMenuItem("Refresh")

        init
        {
            refresh.addActionListener {
                membershipsModel.removeAllElements()
                var memberships = MeetingMembershipSet().viewAllEmployeesInMeeting(this@MeetingOwnerPanel.meeting.title)
                membershipsModel.addAll(memberships)
            }

            this.add(refresh)
        }
    }
}