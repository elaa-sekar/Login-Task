package com.technotackle.test.ui.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ActionMode
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.technotackle.test.R


open class OtpEditText : AppCompatEditText {

    var mSpace = 16f //24 dp by default, space between the lines
    var mNumChars = 6f
    var mLineSpacing = 12f //8dp by default, height of the text from our lines
    private val mMaxLength = 4
    var mLineStroke = 2f
    lateinit var mLinesPaint: Paint
    private var mClickListener: OnClickListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
//        val typeFace = Typeface.createFromAsset(context.assets, "fonts/HelveticaRoundedBold.otf")
//        typeface = typeFace
        val multi = context.resources.displayMetrics.density
        paint.color = Color.parseColor(
            "#${
                Integer.toHexString(
                    ContextCompat.getColor(
                        this.context,
                        R.color.light_orange
                    )
                )
            }"
        )

//        setTextColor(
//            ContextCompat.getColor(
//                context,
//                R.color.navy
//            )
//        )
        mLineStroke *= multi
        mLinesPaint = Paint(paint)
        mLinesPaint.strokeWidth = mLineStroke
        mLinesPaint.color = ContextCompat.getColor(context, R.color.light_orange)
        setBackgroundResource(0)
        mSpace *= multi //convert to pixels for our density
        mLineSpacing *= multi //convert to pixels for our density
        mNumChars = mMaxLength.toFloat()

        super.setOnClickListener { v ->
            // When tapped, move cursor to end of text.
            setSelection(text!!.length)
            mClickListener?.onClick(v)
        }
    }

//    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
//        super.onFocusChanged(focused, direction, previouslyFocusedRect)
//        //Timber.d("onFocusChanged called")
//        if (focused) {
//            //Timber.d("onFocusChanged2 called")
//            context.showKeyboard(this)
//        }
//    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l!!
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    override fun onDraw(canvas: Canvas) {
        val availableWidth = width - paddingRight - paddingLeft
        val mCharSize: Float
        mCharSize = if (mSpace < 0) {
            availableWidth / (mNumChars * 2 - 1)
        } else {
            (availableWidth - mSpace * (mNumChars - 1)) / mNumChars
        }

        var startX = paddingLeft
        val bottom = height - paddingBottom

        //Text Width
        val text = text
        val textLength = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)

        var i = 0
        while (i < mNumChars) {
            canvas.drawLine(
                startX.toFloat(), bottom.toFloat(), startX + mCharSize, bottom.toFloat(),
                mLinesPaint
            )
            if (getText()!!.length > i) {
                val middle = startX + mCharSize / 2
                canvas.drawText(
                    text,
                    i,
                    i + 1,
                    middle - textWidths[0] / 2,
                    bottom - mLineSpacing,
                    paint
                )
            }
            startX += if (mSpace < 0) {
                (mCharSize * 2).toInt()
            } else {
                (mCharSize + mSpace).toInt()
            }
            i++
        }
    }

}