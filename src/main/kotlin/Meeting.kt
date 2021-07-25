import java.awt.Component
import java.awt.Font
import java.sql.Time
import java.sql.Date
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

data class Meeting(val title: String)
{
    var date: Date? = null
    var startTime: Time? = null
    var endTime: Time? = null
    var room: Int? = null

    companion object
    {
        val meetingSet = MeetingSet()
    }

    fun updateTimings(date: Date, startTime: Time, endTime: Time, room: Int) = meetingSet.updateTime(this.title, date, startTime, endTime, room)

    class MeetingRenderer(private val meetings: ArrayList<Meeting>): DefaultTableCellRenderer()
    {
        private val label = JLabel()

        override fun getTableCellRendererComponent(table: JTable, anyVal: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            val meeting = meetings.get(row)
            label.text = "Title: ${meeting.title}"
            return label
        }
    }
}