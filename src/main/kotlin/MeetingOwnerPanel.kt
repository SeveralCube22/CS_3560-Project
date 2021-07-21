import java.awt.*
import javax.swing.*

class MeetingOwnerPanel(val meeting: Meeting): JFrame() //TODO: Change constructor. Param should be employee owner
{
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

        val meetingTitleLabel = JLabel("Title: " + meeting.title) //TODO: Should be textfield
        meetingTitleLabel.font = font
        val meetingOwnerLabel = JLabel("Owner: " + meeting.owner.name) //TODO: Should be employee name
        meetingOwnerLabel.font = font

        val dateLabel = JLabel("Date: ")
        dateLabel.font = font
        val dateField = JTextField()
        dateField.columns = 10
        val datePanel = JPanel(GridBagLayout())
        val durationLabel = JLabel("Duration: ")
        durationLabel.font = font
        val durationField = JTextField()
        durationField.columns = 10
        val durationPanel = JPanel(GridBagLayout())

        val constraints = GridBagConstraints()
        constraints.weightx = .5
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 0
        constraints.gridy = 0
        datePanel.add(dateLabel, constraints)
        durationPanel.add(durationLabel, constraints)

        constraints.gridx = 1
        datePanel.add(dateField, constraints)
        durationPanel.add(durationField, constraints)

        val roomName = JLabel("Room: ")
        roomName.font = font

        val button = JButton() //TODO: On click, close window add meeting to employee
        button.text = "Create Meeting"

        infoConstraints.insets = Insets(10, 10, 10, 10)
        infoConstraints.fill = GridBagConstraints.VERTICAL
        infoConstraints.anchor = GridBagConstraints.WEST

        infoConstraints.gridx = 0
        infoConstraints.gridy = 0
        infoPanel.add(meetingTitleLabel, infoConstraints)
        infoConstraints.gridy = 1
        infoPanel.add(meetingOwnerLabel, infoConstraints)
        infoConstraints.gridy = 2
        infoPanel.add(datePanel, infoConstraints)
        infoConstraints.gridy = 3
        infoPanel.add(durationPanel, infoConstraints)
        infoConstraints.gridy = 4
        infoPanel.add(roomName, infoConstraints)
        infoConstraints.gridy = 8
        infoPanel.add(button, infoConstraints)

        val scrollPanelParent = JPanel(GridLayout(2, 1))
        scrollPanelParent.add(getEmployeesPanel())
        scrollPanelParent.add(getRoomsPanel())

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

        val employeeModel = DefaultListModel<Meeting>()
        employeeModel.size =  meeting.employees.size

        val employeeList = JList<Meeting>(employeeModel)
        employeeList.selectionBackground = Color.GREEN
        employeeList.background = Color.RED

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
        roomModel.size =  meeting.employees.size

        val roomList = JList<Meeting>(roomModel)
        roomList.selectionBackground = Color.GREEN
        roomList.background = Color.RED

        val rooms = JScrollPane(roomList)
        rooms.setBorder(BorderFactory.createTitledBorder("Rooms"))
        rooms.isOpaque = false
        rooms.isWheelScrollingEnabled = true;
        return rooms
    }
}