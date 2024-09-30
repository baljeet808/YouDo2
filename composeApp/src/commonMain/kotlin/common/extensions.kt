package common

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime


fun String.isPasswordValid() : Boolean{
    if(this.isBlank()){
        return false
    }
    if(this.length < 5){
        return false
    }
    if(this.none { char -> char.isDigit()}  || this.none { char -> char.isLetter()} || this.none { char -> !char.isLetterOrDigit()}){
        return false
    }
    return true
}


fun String.isEmailValid() : Boolean{
    if(this.isBlank()){
        return false
    }
    if(this.length < 6){
        return false
    }
    if(this.none { char -> char == '@' }  || this.none { char -> char == '.' }){
        return false
    }
    return true
}

fun LocalDateTime.formatNicelyWithoutYear(): String {
    return dayOfMonth.toString().plus(" ").plus(month.name).plus(", ").plus(dayOfWeek.name)
}

fun getCurrentDateTime() : LocalDateTime {
    return Clock.System.now().toLocalDateTime(getCurrentTimeZone())
}


fun LocalDateTime.toNiceDateTimeFormat(onlyShowTime: Boolean = false): String {
    this.let { dateTime ->

        val hours = if (hour > 12) {
            hour - 12
        } else hour
        val minutes = if (minute < 10) {
            "0".plus(minute)
        } else minute
        val isAM = dateTime.hour < 12

        var dateString = ""

        val givenDateTIme = dateTime.date
        val currentDate = getCurrentDateTime().date

        if (onlyShowTime.not()) {
            dateString = if (givenDateTIme.compareTo(currentDate) == 0) {
                dateString.plus("Today ")
            } else if (givenDateTIme > currentDate.minus(1,DateTimeUnit.DAY)) {
                dateString.plus("Yesterday ")
            } else if (givenDateTIme < currentDate.plus(1,DateTimeUnit.DAY)) {
                dateString.plus("Tomorrow ")
            } else {
                dateString.plus(dayOfMonth.toString()).plus(" ")
                    .plus(month.name.plus(", "))
                    .plus(year.toString().plus("   "))
            }
        }

        dateString = dateString.plus(hours.toString().plus(":"))
            .plus(minutes.toString())
            .plus(
                if (isAM) {
                    " AM"
                } else {
                    " PM"
                }
            )

        return dateString
    }
}

fun Long.toNiceDateTimeFormat(onlyShowTime: Boolean = false): String {
    return this.toLocalDateTime().toNiceDateTimeFormat(onlyShowTime)
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}