package common

import androidx.compose.ui.graphics.Color


enum class EnumPriorities(val toString: String) {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low"),
}

enum class EnumCreateTaskSheetType {
    SELECT_PROJECT, SELECT_DUE_DATE, SELECT_PRIORITY
}

enum class EnumNotificationType{
    NewMessage, NewInvitation, ProjectUpdate, TaskUpdate, AccessUpdate, InvitationUpdate, MessageUpdate, General
}


enum class ChatScreenBottomSheetTypes() {
    MESSAGE_EMOTICONS, CUSTOM_EMOTICONS, PERSON_TAGGER, COLLABORATOR_SCREEN
}


enum class EnumProjectColors{
    Green,
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

fun String.getColor(): Color {
    return when(this){
        EnumProjectColors.Green.name -> Color(0xFF006261)
        EnumProjectColors.Pink.name -> Color(0xFFFF69B4)
        EnumProjectColors.Blue.name -> Color(0xff363CB5)
        EnumProjectColors.Red.name -> Color(0xFFF53C4F)
        EnumProjectColors.Yellow.name -> Color(0xFFFF8526)
        EnumProjectColors.Brown.name -> Color(0xFFA52A2A)
        EnumProjectColors.Black.name -> Color(0xFF302D2D)
        EnumProjectColors.DarkBlack.name -> Color(0xFF0A0909)
        EnumProjectColors.Cyan.name -> Color(0xFF8BDFFE)
        EnumProjectColors.Indigo.name -> Color(0xFF4b0082)
        EnumProjectColors.RoseRed.name -> Color(0xFFBF1363)
        EnumProjectColors.Murrey.name -> Color(0xFF820D43)
        else -> {
            Color(0xFF302D2D)
        }
    }
}

fun EnumProjectColors.getColor(): Color {
    return when(this){
        EnumProjectColors.Green -> Color(0xFF006261)
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