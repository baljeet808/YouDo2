package common

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


enum class DueDates(val toString: String) {
    NEXT_FRIDAY("Next Friday"),
    TOMORROW("Tomorrow"),
    TODAY("Today"),
    CUSTOM("Custom");

    fun getExactDate(): LocalDateTime {
        val currentDate = getCurrentDateTime()
        val timeZone = TimeZone.currentSystemDefault()

        return when (this) {
            TODAY -> currentDate
            TOMORROW -> currentDate.toInstant(timeZone).plus(1, DateTimeUnit.DAY, timeZone).toLocalDateTime(timeZone)
            NEXT_FRIDAY -> {
                val currentDayOfWeek = currentDate.dayOfWeek.isoDayNumber
                if (currentDayOfWeek == 5) {
                    currentDate.toInstant(timeZone).plus(1, DateTimeUnit.DAY, timeZone).toLocalDateTime(timeZone)
                } else if (currentDayOfWeek < 5) {
                    currentDate.toInstant(timeZone).plus(5-currentDayOfWeek, DateTimeUnit.DAY, timeZone).toLocalDateTime(timeZone)
                } else {
                    currentDate.toInstant(timeZone).plus(7 - (currentDayOfWeek - 5), DateTimeUnit.DAY, timeZone).toLocalDateTime(timeZone)
                }
            }
            else -> currentDate
        }
    }
}

enum class EnumPriorities(val toString: String) {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low"),
}

enum class EnumCreateTaskSheetType {
    SELECT_DUE_DATE, SELECT_PRIORITY
}

enum class EnumNotificationType{
    NewMessage, NewInvitation, ProjectUpdate, TaskUpdate, AccessUpdate, InvitationUpdate, MessageUpdate, General
}


enum class ChatScreenBottomSheetTypes() {
    MESSAGE_EMOTICONS, CUSTOM_EMOTICONS, PERSON_TAGGER, COLLABORATOR_SCREEN
}


enum class EnumProjectColors{
    Green,
    LightGreen,
    Pink,
    Blue,
    Red,
    Yellow,
    Brown,
    Black,
    DarkBlack,
    Cyan,
    Indigo,
    RoseRed,
    Murrey
}

enum class EnumDashboardTasksTabs{
    Today,Tomorrow,Yesterday,Pending,AllOther
}

fun Long.getColor(): Color {
    return Color(this)
}

fun EnumProjectColors.getColor(): Color {
    return when(this){
        EnumProjectColors.Green -> Color(0xFF006261)
        EnumProjectColors.LightGreen -> Color(0xFFD0E9BC)
        EnumProjectColors.Pink -> Color(0xFFFF69B4)
        EnumProjectColors.Blue -> Color(0xff363CB5)
        EnumProjectColors.Red -> Color(0xFFF53C4F)
        EnumProjectColors.Yellow -> Color(0xFFFF8526)
        EnumProjectColors.Brown -> Color(0xFFA52A2A)
        EnumProjectColors.Black -> Color(0xFF302D2D)
        EnumProjectColors.DarkBlack -> Color(0xFF0A0909)
        EnumProjectColors.Cyan -> Color(0xFF8BDFFE)
        EnumProjectColors.Indigo -> Color(0xFF4b0082)
        EnumProjectColors.RoseRed -> Color(0xFFBF1363)
        EnumProjectColors.Murrey -> Color(0xFF820D43)
    }
}

fun EnumProjectColors.getLongValueInString(): String {
    return  this.getColor().value.toLong().toString()
}