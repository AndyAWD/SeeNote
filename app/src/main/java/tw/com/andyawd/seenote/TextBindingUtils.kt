package tw.com.andyawd.seenote.notepage

import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.Note
import tw.com.andyawd.seenote.database.Setting
import java.text.DateFormat

@BindingAdapter("changeCreateTime")
fun TextView.setChangeEditTime(item: Note) {
    item.let {
        text = resources.getString(
            R.string.edit_date,
            DateFormat.getInstance().format(item.editDate)
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

@BindingAdapter("changeTitleTextColor")
fun TextView.changeTitleTextColor(item: Setting?) {
    item?.let {
        setTextColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.titleTextColor)
            )
        )
    }
}

@BindingAdapter("changeTitleBackgroundColor")
fun TextView.changeTitleBackgroundColor(item: Setting?) {
    item?.let {
        setBackgroundColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.titleBackgroundColor)
            )
        )
    }
}

@BindingAdapter("changeTitleHintTextColor")
fun TextView.changeTitleHintTextColor(item: Setting?) {
    item?.let {
        setHintTextColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.titleTextColor)
            )
        )
    }
}

@BindingAdapter("changeContentTextColor")
fun TextView.changeContentTextColor(item: Setting?) {
    item?.let {
        setTextColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.contentTextColor)
            )
        )
    }
}

@BindingAdapter("changeContentBackgroundColor")
fun TextView.changeContentBackgroundColor(item: Setting?) {
    item?.let {
        setBackgroundColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.contentBackgroundColor)
            )
        )
    }
}

@BindingAdapter("changeContentHintTextColor")
fun TextView.changeContentHintTextColor(item: Setting?) {
    item?.let {
        setHintTextColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.contentTextColor)
            )
        )
    }
}

@BindingAdapter("changeDateTextColor")
fun TextView.changeDateTextColor(item: Setting?) {
    item?.let {
        setTextColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.dateTextColor)
            )
        )
    }
}

@BindingAdapter("changeDateBackgroundColor")
fun TextView.changeDateBackgroundColor(item: Setting?) {
    item?.let {
        setBackgroundColor(
            ActivityCompat.getColor(
                context,
                colorResource(item.dateBackgroundColor)
            )
        )
    }
}

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
        else -> return R.color.hexColor129_DarkOrchid
    }
}