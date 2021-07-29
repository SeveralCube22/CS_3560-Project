import java.awt.Component
import java.awt.Font
import java.sql.Time
import java.sql.Date
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList

data class Meeting(val title: String)
{
    var date: Date? = null
    var startTime: Time? = null
    var endTime: Time? = null
    var room: Int? = null

    companion object
    {
        private val meetingSet = MeetingSet()
    }

    fun insert() = meetingSet.insert(this)

    fun updateTimings(date: Date, startTime: Time, endTime: Time, room: Int) = meetingSet.updateTime(this.title, date, startTime, endTime, room)

    class MeetingRenderer: DefaultListCellRenderer()
    {
        private val label = JLabel()

        override fun getListCellRendererComponent(list: JList<*>?, meeting: Any?, row: Int, isSelected: Boolean, hasFocus: Boolean): Component?
        {
            label.font = Font("Roboto", Font.PLAIN, 15)
            if(meeting is Meeting)
                label.text = "Title: ${meeting.title}"
            return label
        }
    }
}