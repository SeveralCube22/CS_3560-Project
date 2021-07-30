import java.awt.Color
import java.awt.Component
import java.awt.Font
import java.sql.Time
import java.time.LocalDate
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList

data class Meeting(val title: String)
{
    var date: LocalDate? = null
    var startTime: Time? = null
    var endTime: Time? = null
    var room: Int? = null

    companion object
    {
        private val meetingSet = MeetingSet()
    }

    fun insert() = meetingSet.insert(this)

    fun delete() = meetingSet.delete(title)

    fun updateTimings(date: LocalDate, startTime: Time, endTime: Time, room: Int) = meetingSet.updateTime(this.title, date, startTime, endTime, room)

    class MeetingRenderer: DefaultListCellRenderer()
    {
        private val label = JLabel()

        override fun getListCellRendererComponent(list: JList<*>?, meeting: Any?, row: Int, isSelected: Boolean, hasFocus: Boolean): Component?
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            label.isOpaque = true
            if(meeting is Meeting)
                label.text = "Title: ${meeting.title}"
            if(isSelected)
               label.background = Color.GREEN
            else
                label.background = Color.WHITE
            return label
        }
    }
}