package tw.com.andyawd.seenote.notepage

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.color.MaterialColors
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import java.text.DateFormat

@BindingAdapter("changeEditTime")
fun TextView.setChangeEditTime(item: Note) {
    item.let {
        text = resources.getString(
            R.string.edit_date,
            DateFormat.getInstance().format(item.date?.edit)
        )
    }
}

@BindingAdapter("changeTitle")
fun TextView.setChangeTitle(item: Note) {
    text = item.title.ifEmpty {
        resources.getString(R.string.no_title)
    }
}

@BindingAdapter("changeContent")
fun TextView.setChangeContent(item: Note) {
    text = item.content.ifEmpty {
        resources.getString(R.string.no_content)
    }
}

@BindingAdapter("noteTitleTextColor", "settingTitleTextColor", requireAll = false)
fun TextView.changeTitleTextColor(note: Note?, setting: Setting?) {
    val noteTextColor = note?.titleColor?.textColor
    val settingTextColor = setting?.title?.textColor

    if (noteTextColor.isNullOrEmpty() && settingTextColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.BLACK)
        return
    }

    if (noteTextColor.isNullOrEmpty()) {
        val hexColor = settingTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    } else {
        val hexColor = noteTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    }
}

@BindingAdapter("noteContentTextColor", "settingContentTextColor", requireAll = false)
fun TextView.changeContentTextColor(note: Note?, setting: Setting?) {
    val noteTextColor = note?.contentColor?.textColor
    val settingTextColor = setting?.content?.textColor

    if (noteTextColor.isNullOrEmpty() && settingTextColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.BLACK)
        return
    }

    if (noteTextColor.isNullOrEmpty()) {
        val hexColor = settingTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    } else {
        val hexColor = noteTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    }
}

@BindingAdapter("noteDateTextColor", "settingDateTextColor", requireAll = false)
fun TextView.changeDateTextColor(note: Note?, setting: Setting?) {
    val noteTextColor = note?.dateColor?.textColor
    val settingTextColor = setting?.date?.textColor

    if (noteTextColor.isNullOrEmpty() && settingTextColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.BLACK)
        return
    }

    if (noteTextColor.isNullOrEmpty()) {
        val hexColor = settingTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    } else {
        val hexColor = noteTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    }
}

@BindingAdapter("noteTagTextColor", "settingTagTextColor", requireAll = false)
fun TextView.changeTagTextColor(note: Note?, setting: Setting?) {
    val noteTextColor = note?.tagColor?.textColor
    val settingTextColor = setting?.label?.textColor

    if (noteTextColor.isNullOrEmpty() && settingTextColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.BLACK)
        return
    }

    if (noteTextColor.isNullOrEmpty()) {
        val hexColor = settingTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    } else {
        val hexColor = noteTextColor.toString()
        val color = getColor(context, hexColor)
        setTextColor(color)
    }
}

@BindingAdapter("noteTitleBackgroundColor", "settingTitleBackgroundColor", requireAll = false)
fun TextView.changeTitleBackgroundColor(note: Note?, setting: Setting?) {
    val noteBackgroundColor = note?.titleColor?.backgroundColor
    val settingBackgroundColor = setting?.title?.backgroundColor

    if (noteBackgroundColor.isNullOrEmpty() && settingBackgroundColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnSecondary, Color.BLACK)
        return
    }

    if (noteBackgroundColor.isNullOrEmpty()) {
        val hexColor = settingBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    } else {
        val hexColor = noteBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    }
}

@BindingAdapter("noteContentBackgroundColor", "settingContentBackgroundColor", requireAll = false)
fun TextView.changeContentBackgroundColor(note: Note?, setting: Setting?) {
    val noteBackgroundColor = note?.contentColor?.backgroundColor
    val settingBackgroundColor = setting?.content?.backgroundColor

    if (noteBackgroundColor.isNullOrEmpty() && settingBackgroundColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnSecondary, Color.BLACK)
        return
    }

    if (noteBackgroundColor.isNullOrEmpty()) {
        val hexColor = settingBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    } else {
        val hexColor = noteBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    }
}

@BindingAdapter("noteDateBackgroundColor", "settingDateBackgroundColor", requireAll = false)
fun TextView.changeDateBackgroundColor(note: Note?, setting: Setting?) {
    val noteBackgroundColor = note?.dateColor?.backgroundColor
    val settingBackgroundColor = setting?.date?.backgroundColor

    if (noteBackgroundColor.isNullOrEmpty() && settingBackgroundColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnSecondary, Color.BLACK)
        return
    }

    if (noteBackgroundColor.isNullOrEmpty()) {
        val hexColor = settingBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    } else {
        val hexColor = noteBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    }
}

@BindingAdapter("noteTagBackgroundColor", "settingTagBackgroundColor", requireAll = false)
fun TextView.changeTagBackgroundColor(note: Note?, setting: Setting?) {
    val noteBackgroundColor = note?.tagColor?.backgroundColor
    val settingBackgroundColor = setting?.label?.backgroundColor

    if (noteBackgroundColor.isNullOrEmpty() && settingBackgroundColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnSecondary, Color.BLACK)
        return
    }

    if (noteBackgroundColor.isNullOrEmpty()) {
        val hexColor = settingBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    } else {
        val hexColor = noteBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setBackgroundColor(color)
    }
}

@BindingAdapter("noteTitleHintTextColor", "settingTitleHintTextColor", requireAll = false)
fun TextView.changeTitleHintTextColor(note: Note?, setting: Setting?) {
    val noteBackgroundColor = note?.titleColor?.textColor
    val settingBackgroundColor = setting?.title?.textColor

    if (noteBackgroundColor.isNullOrEmpty() && settingBackgroundColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.BLACK)
        return
    }

    if (noteBackgroundColor.isNullOrEmpty()) {
        val hexColor = settingBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setHintTextColor(color)
    } else {
        val hexColor = noteBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setHintTextColor(color)
    }
}

@BindingAdapter("noteContentHintTextColor", "settingContentHintTextColor", requireAll = false)
fun TextView.changeContentHintTextColor(note: Note?, setting: Setting?) {
    val noteBackgroundColor = note?.contentColor?.textColor
    val settingBackgroundColor = setting?.content?.textColor

    if (noteBackgroundColor.isNullOrEmpty() && settingBackgroundColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.BLACK)
        return
    }

    if (noteBackgroundColor.isNullOrEmpty()) {
        val hexColor = settingBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setHintTextColor(color)
    } else {
        val hexColor = noteBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setHintTextColor(color)
    }
}

@BindingAdapter("noteTagHintTextColor", "settingTagHintTextColor", requireAll = false)
fun TextView.changeTagHintTextColor(note: Note?, setting: Setting?) {
    val noteBackgroundColor = note?.tagColor?.textColor
    val settingBackgroundColor = setting?.label?.textColor

    if (noteBackgroundColor.isNullOrEmpty() && settingBackgroundColor.isNullOrEmpty()) {
        MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.BLACK)
        return
    }

    if (noteBackgroundColor.isNullOrEmpty()) {
        val hexColor = settingBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setHintTextColor(color)
    } else {
        val hexColor = noteBackgroundColor.toString()
        val color = getColor(context, hexColor)
        setHintTextColor(color)
    }
}

@BindingAdapter("writeNoteButtonSize")
fun TextView.writeNoteButtonSize(setting: Setting?) {
    setting?.let {
        val writerNote = setting.textSize?.writerNote
        val newSize = writerNote?.div(6)?.toFloat()
        textSize = newSize ?: 80F
    }
}

fun getColor(context: Context, hexColor: String): Int =
    ActivityCompat.getColor(context, colorResource(hexColor))

fun colorResource(color: String): Int {
    when (color) {
        BaseConstants.BLACK -> return R.color.hexColor000_Black
        BaseConstants.GRAY -> return R.color.hexColor002_Gray
        BaseConstants.SILVER -> return R.color.hexColor004_Silver
        BaseConstants.GAINSBORO -> return R.color.hexColor006_Gainsboro
        BaseConstants.WHITE -> return R.color.hexColor007_White
        BaseConstants.MIDNIGHTBLUE -> return R.color.hexColor036_MidnightBlue
        BaseConstants.MEDIUMBLUE -> return R.color.hexColor039_MediumBlue
        BaseConstants.DODGERBLUE -> return R.color.hexColor041_DodgerBlue
        BaseConstants.SKYBLUE -> return R.color.hexColor045_SkyBlue
        BaseConstants.LIGHTCYAN -> return R.color.hexColor049_LightCyan
        BaseConstants.DARKGREEN -> return R.color.hexColor061_DarkGreen
        BaseConstants.FORESTGREEN -> return R.color.hexColor063_ForestGreen
        BaseConstants.LIMEGREEN -> return R.color.hexColor076_LimeGreen
        BaseConstants.LIGHTGREEN -> return R.color.hexColor069_LightGreen
        BaseConstants.GREENYELLOW -> return R.color.hexColor074_GreenYellow
        BaseConstants.MAROON -> return R.color.hexColor102_Maroon
        BaseConstants.FIREBRICK -> return R.color.hexColor105_Firebrick
        BaseConstants.RED -> return R.color.hexColor115_Red
        BaseConstants.LIGHTSALMON -> return R.color.hexColor111_LightSalmon
        BaseConstants.PINK -> return R.color.hexColor121_Pink
        BaseConstants.SADDLEBROWN -> return R.color.hexColor101_SaddleBrown
        BaseConstants.CHOCOLATE -> return R.color.hexColor099_Chocolate
        BaseConstants.ORANGE -> return R.color.hexColor093_Orange
        BaseConstants.GOLD -> return R.color.hexColor092_Gold
        BaseConstants.LIGHTYELLOW -> return R.color.hexColor084_LightYellow
        BaseConstants.DEFAULT -> return R.color.hexColor084_LightYellow
        else -> return R.color.hexColor138_MediumSlateBlue
    }
}