package common


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
    Cyan,
    Indigo
}

enum class EnumDashboardTasksTabs{
    Today,Tomorrow,Yesterday,Pending,AllOther
}

