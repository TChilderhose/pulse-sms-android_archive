package xyz.klinker.messenger.shared.view.emoji

import android.content.Context
import android.support.text.emoji.widget.EmojiTextViewHelper
import android.support.v7.widget.AppCompatTextView
import android.text.InputFilter
import android.util.AttributeSet
import xyz.klinker.messenger.shared.data.Settings
import xyz.klinker.messenger.shared.data.pojo.EmojiStyle

class EmojiableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatTextView(context, attrs, defStyleAttr) {

    private val useEmojiCompat: Boolean
        get() = Settings.get(context).emojiStyle != EmojiStyle.DEFAULT

    private var helper: EmojiTextViewHelper? = null
    private val emojiHelper: EmojiTextViewHelper
        get() {
            if (helper == null) {
                helper = EmojiTextViewHelper(this)
            }
            return helper as EmojiTextViewHelper
        }

    init {
        if (useEmojiCompat) {
            try {
                emojiHelper.updateTransformationMethod()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun setFilters(filters: Array<InputFilter>) {
        if (useEmojiCompat) {
            try {
                super.setFilters(emojiHelper.getFilters(filters))
            } catch (e: Exception) {
                e.printStackTrace()
                super.setFilters(filters)
            }
        } else {
            super.setFilters(filters)
        }
    }

    override fun setAllCaps(allCaps: Boolean) {
        super.setAllCaps(allCaps)
        try {
            emojiHelper.setAllCaps(allCaps)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}