/*
    MonthPanel class credit goes to user Gilbert Le Blanc from StackOverflow
    Link: https://stackoverflow.com/questions/17232038/calendar-display-using-java-swing

 */


import java.awt.*
import java.util.*
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class MonthPanel(protected var month: Int, protected var year: Int, val frame: JFrame) : JPanel()
{
    protected var monthNames = arrayOf(
        "January", "February",
        "March", "April", "May", "June", "July", "August", "September",
        "October", "November", "December"
    )
    protected var dayNames = arrayOf(
        "S", "M", "T", "W",
        "T", "F", "S"
    )

    protected fun createGUI(): JPanel
    {
        val monthPanel = JPanel()
        monthPanel.border = BorderFactory
            .createLineBorder(SystemColor.activeCaption)
        monthPanel.layout = BorderLayout()
        monthPanel.background = Color.WHITE
        monthPanel.foreground = Color.BLACK
        monthPanel.add(createTitleGUI(), BorderLayout.NORTH)
        monthPanel.add(createDaysGUI(), BorderLayout.SOUTH)
        return monthPanel
    }

    protected fun createTitleGUI(): JPanel
    {
        val titlePanel = JPanel(true)
        titlePanel.border = BorderFactory
            .createLineBorder(SystemColor.activeCaption)
        titlePanel.layout = FlowLayout()
        titlePanel.background = Color.WHITE
        val label = JLabel(monthNames[month] + " " + year)
        label.foreground = SystemColor.activeCaption
        titlePanel.add(label, BorderLayout.CENTER)
        return titlePanel
    }

    protected fun createDaysGUI(): JPanel
    {
        val dayPanel = JPanel(true)
        dayPanel.layout = GridLayout(0, dayNames.size)
        val today = Calendar.getInstance()
        val tMonth = today[Calendar.MONTH]
        val tYear = today[Calendar.YEAR]
        val tDay = today[Calendar.DAY_OF_MONTH]
        val calendar = Calendar.getInstance()
        calendar[Calendar.MONTH] = month
        calendar[Calendar.YEAR] = year
        calendar[Calendar.DAY_OF_MONTH] = 1
        val iterator = calendar.clone() as Calendar
        iterator.add(
            Calendar.DAY_OF_MONTH,
            -(iterator[Calendar.DAY_OF_WEEK] - 1)
        )
        val maximum = calendar.clone() as Calendar
        maximum.add(Calendar.MONTH, +1)
        for (i in dayNames.indices)
        {
            val dPanel = JPanel(true)
            dPanel.preferredSize = Dimension(frame.width / 3, frame.height / 3)
            dPanel.border = BorderFactory.createLineBorder(Color.BLACK)
            val dLabel = JLabel(dayNames[i])
            dPanel.add(dLabel)
            dayPanel.add(dPanel)
        }
        var count = 0
        val limit = dayNames.size * 6
        while (iterator.timeInMillis < maximum.timeInMillis)
        {
            val lMonth = iterator[Calendar.MONTH]
            val lYear = iterator[Calendar.YEAR]
            val dPanel = JPanel(true)
            dPanel.border = BorderFactory.createLineBorder(Color.BLACK)
            val dayLabel = JLabel()
            if (lMonth == month && lYear == year)
            {
                val lDay = iterator[Calendar.DAY_OF_MONTH]
                dayLabel.text = Integer.toString(lDay)
                dPanel.background = Color.WHITE
            }
            else
            {
                dayLabel.text = " "
                dPanel.background = Color.WHITE
            }
            dPanel.add(dayLabel)
            dayPanel.add(dPanel)
            iterator.add(Calendar.DAY_OF_YEAR, +1)
            count++
        }
        for (i in count until limit)
        {
            val dPanel = JPanel(true)
            dPanel.border = BorderFactory.createLineBorder(Color.BLACK)
            val dayLabel = JLabel()
            dayLabel.text = " "
            dPanel.background = Color.WHITE
            dPanel.add(dayLabel)
            dayPanel.add(dPanel)
        }
        return dayPanel
    }

    companion object
    {
        private const val serialVersionUID = 1L
    }

    init
    {
        this.add(createGUI())
    }
}