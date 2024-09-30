package common

import common.Emoticon.Amazed
import common.Emoticon.Anger
import common.Emoticon.CryingFace
import common.Emoticon.CuteFace
import common.Emoticon.Devil
import common.Emoticon.FeelingGood
import common.Emoticon.Hi
import common.Emoticon.LovingIt
import common.Emoticon.NotImpressed
import common.Emoticon.NotToday
import common.Emoticon.Omg
import common.Emoticon.Party
import common.Emoticon.Puzzled
import common.Emoticon.SadFace
import common.Emoticon.Scared
import common.Emoticon.Sleeping
import common.Emoticon.Thinking
import common.Emoticon.Wink
import common.Emoticon.Wow
import common.Emoticon.Yummy
import domain.models.Interaction
import org.jetbrains.compose.resources.DrawableResource
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.amazed
import youdo2.composeapp.generated.resources.anger
import youdo2.composeapp.generated.resources.crying_face
import youdo2.composeapp.generated.resources.cute_face
import youdo2.composeapp.generated.resources.devil
import youdo2.composeapp.generated.resources.feeling_good
import youdo2.composeapp.generated.resources.hi
import youdo2.composeapp.generated.resources.loving_it
import youdo2.composeapp.generated.resources.not_impressed
import youdo2.composeapp.generated.resources.not_today
import youdo2.composeapp.generated.resources.omg
import youdo2.composeapp.generated.resources.party
import youdo2.composeapp.generated.resources.puzzled
import youdo2.composeapp.generated.resources.sad_face
import youdo2.composeapp.generated.resources.scared
import youdo2.composeapp.generated.resources.sleeping
import youdo2.composeapp.generated.resources.thinking
import youdo2.composeapp.generated.resources.wink
import youdo2.composeapp.generated.resources.wow
import youdo2.composeapp.generated.resources.yummy

enum class Emoticon(val fileName : String, val drawableId : DrawableResource){
    Amazed("amazed", Res.drawable.amazed),
    Anger("anger", Res.drawable.anger),
    CryingFace("crying_face", Res.drawable.crying_face),
    CuteFace("cute_face", Res.drawable.cute_face),
    Devil("devil", Res.drawable.devil),
    FeelingGood("feeling_good", Res.drawable.feeling_good),
    Hi("hi", Res.drawable.hi),
    LovingIt("loving_it", Res.drawable.loving_it),
    NotImpressed("not_impressed", Res.drawable.not_impressed),
    NotToday("not_today", Res.drawable.not_today),
    Omg("omg", Res.drawable.omg),
    Party("party", Res.drawable.party),
    Puzzled("puzzled", Res.drawable.puzzled),
    SadFace("sad_face", Res.drawable.sad_face),
    Scared("scared", Res.drawable.scared),
    Sleeping("sleeping", Res.drawable.sleeping),
    Thinking("thinking", Res.drawable.thinking),
    Wow("wow", Res.drawable.wow),
    Wink("wink", Res.drawable.wink),
    Yummy("yummy", Res.drawable.yummy);

    fun getDrawableId() : DrawableResource{
        return drawableId
    }

    fun getFileName() : String{
        return fileName
    }
}


fun getDrawableIdByFileName(fileName : String) : DrawableResource{
    return when(fileName){
        "amazed" -> Amazed.getDrawableId()
        "anger" -> Anger.getDrawableId()
        "crying_face" -> CryingFace.getDrawableId()
        "cute_face" -> CuteFace.getDrawableId()
        "devil" -> Devil.getDrawableId()
        "feeling_good" -> FeelingGood.getDrawableId()
        "hi" -> Hi.getDrawableId()
        "loving_it" -> LovingIt.getDrawableId()
        "not_impressed" -> NotImpressed.getDrawableId()
        "not_today" -> NotToday.getDrawableId()
        "omg" -> Omg.getDrawableId()
        "party" -> Party.getDrawableId()
        "puzzled" -> Puzzled.getDrawableId()
        "sad_face" -> SadFace.getDrawableId()
        "scared" -> Scared.getDrawableId()
        "sleeping" -> Sleeping.getDrawableId()
        "thinking" -> Thinking.getDrawableId()
        "wow" -> Wow.getDrawableId()
        "wink" -> Wink.getDrawableId()
        "yummy" -> Yummy.getDrawableId()
        else -> Amazed.getDrawableId()
    }
}


fun String.getInteractions(): ArrayList<Interaction> {
    val interactions = arrayListOf<Interaction>()
    this.split(" | ").forEach { interactionString ->
        val interactionArray = interactionString.split(",")
        interactions.add(
            Interaction(
                userId = interactionArray[0],
                emoticonName = interactionArray[1]
            )
        )
    }
    return interactions
}


