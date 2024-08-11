package common

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime


fun String.isPasswordValid() : Boolean{
    if(this.isBlank()){
        return false
    }
    if(this.length < 5){
        return false
    }
    if(this.none { char -> char.isDigit() || char.isUpperCase() || !char.isLetterOrDigit() }){
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
