import java.lang.NullPointerException
import java.util.Date
import java.sql.Time

enum class Visibility
{
    VISIBLE,
    INVISIBLE
}

data class Employee(val id: Int, val name: String)
{
    val ownedMeetings = HashMap<String, Meeting>()
    val attendingMeetings = HashMap<String, Meeting>()
    val invitedMeetings = HashMap<String, Meeting>()
    val tasks = HashMap<Date, ArrayList<Task>>()
    val notifications = ArrayList<Notification>()
    val visibility = Visibility.VISIBLE

    fun createMeeting(meeting: Meeting)
    {
        ownedMeetings.put(meeting.title, meeting)
        createTaskFromMeeting(meeting)
        //TODO: Insert meeting into MeetingSet
    }

    fun deleteMeeting(title: String)
    {
        //TODO: Send notification to employees invited to meeting
        ownedMeetings.remove(title)
        //TODO: Delete meeting from MeetingSet
        //TODO: Delete meeting from employees
    }

    fun acceptMeeting(title: String)
    {
        val meeting = invitedMeetings[title]!!
        meeting.acceptMeeting(this)
        attendingMeetings[title] = meeting
        createTaskFromMeeting(meeting)
        invitedMeetings.remove(title)
    }

    fun rejectMeeting(title: String)
    {
        invitedMeetings[title]!!.rejectMeeting(this)
        //TODO: Send Notification to meeting owner
        invitedMeetings.remove(title)
    }

    fun leaveMeeting(title: String)
    {
        attendingMeetings[title]!!.rejectMeeting(this)
        //TODO: Send Notification to meeting owner
        attendingMeetings.remove(title)
    }

    private fun createTaskFromMeeting(meeting: Meeting)
    {
        val task = Task(meeting.date, meeting.startTime, Time(meeting.startTime.hours + meeting.duration as Int,
            (meeting.startTime.minutes + meeting.duration - meeting.duration as Int * 100) as Int, 0), meeting.title)
        createTask(task)
    }

    fun createTask(task: Task)
    {
        if(tasks.getOrDefault(task.date, null) == null) tasks[task.date] = arrayListOf(task)
        else tasks[task.date]!!.add(task)
    }

    //TODO: Modify meeting. Send Notification based on what was changed
}