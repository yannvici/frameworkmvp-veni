package com.hooooooo.android.veni.framework_mvp


import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import java.text.NumberFormat

/**
 * Created by yann on 2023/2/27
 * <p>
 * Describe:
 */
class ProgressDialog(context: Context, theme: Int = 0) : AlertDialog(context, theme) {
    companion object {
        /**
         * Creates a [ProgressDialog] with a circular, spinning progress
         * bar. This is the default.
         */
        const val STYLE_SPINNER: Int = 0

        /**
         * Creates a [ProgressDialog] with a horizontal progress bar.
         */
        const val STYLE_HORIZONTAL = 1
    }

    private var mProgress: ProgressBar? = null
    private var mMessageView: TextView? = null
    private lateinit var mProgressNumber: TextView
    private var mProgressStyle: Int = STYLE_SPINNER
    private var mProgressNumberFormat: String? = "%1d/%2d"
    private lateinit var mProgressPercent: TextView
    private var mProgressPercentFormat: NumberFormat? = NumberFormat.getInstance()
    private var mMax = 0
    private var mProgressVal = 0
    private var mSecondaryProgressVal = 0
    private var mIncrementBy = 0
    private var mIncrementSecondaryBy = 0
    private var mProgressDrawable: Drawable? = null
    private var mIndeterminateDrawable: Drawable? = null
    private var mMessage: CharSequence? = null
    private var mIndeterminate = false

    private var mHasStarted = false
    private var mViewUpdateHandler: Handler? = null

    init {
        mProgressPercentFormat?.maximumFractionDigits = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val a: TypedArray = context.obtainStyledAttributes(
            null, com.hooooooo.android.veni.frameworkmvp.R.styleable.AlertDialog, com.hooooooo.android.veni.frameworkmvp.R.attr.alertDialogStyle, 0
        )
        if (mProgressStyle == STYLE_HORIZONTAL) {
            mViewUpdateHandler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    val progress: Int = mProgress?.progress ?: 0
                    val max: Int = mProgress?.max ?: 0
                    mProgressNumberFormat?.apply {
                        mProgressNumber.text = String.format(this, progress, max)
                    } ?: run {
                        mProgressNumber.text = ""
                    }
                    mProgressPercentFormat?.apply {
                        val percent: Double = progress.toDouble() / max.toDouble()
                        val tmp = SpannableString(this.format(percent))
                        tmp.setSpan(
                            StyleSpan(Typeface.BOLD), 0, tmp.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        mProgressPercent.text = ""
                    } ?: run {
                        mProgressPercent.text = ""
                    }
                }
            }
            val view: View = inflater.inflate(
                a.getResourceId(
                    com.hooooooo.android.veni.frameworkmvp.R.styleable.AlertDialog_horizontalProgressLayout,
                    com.hooooooo.android.veni.frameworkmvp.R.layout.alert_dialog_progress
                ), null
            )
            mProgress = view.findViewById(com.hooooooo.android.veni.frameworkmvp.R.id.progress)
            mProgressNumber = view.findViewById(com.hooooooo.android.veni.frameworkmvp.R.id.progress_number)
            mProgressPercent = view.findViewById(com.hooooooo.android.veni.frameworkmvp.R.id.progress_percent)
            setView(view)
        } else {
            val view: View = inflater.inflate(
                a.getResourceId(
                    com.hooooooo.android.veni.frameworkmvp.R.styleable.AlertDialog_progressLayout,
                    com.hooooooo.android.veni.frameworkmvp.R.layout.progress_dialog
                ), null
            )
            mProgress = view.findViewById(com.hooooooo.android.veni.frameworkmvp.R.id.progress)
            mMessageView = view.findViewById(com.hooooooo.android.veni.frameworkmvp.R.id.message)
            setView(view)
        }
        a.recycle()
        if (mMax > 0) {
            setMax(mMax)
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal)
        }
        if (mSecondaryProgressVal > 0) {
            setSecondaryProgress(mSecondaryProgressVal)
        }
        if (mIncrementBy > 0) {
            incrementProgressBy(mIncrementBy)
        }
        if (mIncrementSecondaryBy > 0) {
            incrementSecondaryProgressBy(mIncrementSecondaryBy)
        }
        mProgressDrawable?.apply {
            setProgressDrawable(this)
        }
        mIndeterminateDrawable?.apply {
            setIndeterminateDrawable(this)
        }
        mMessage?.apply {
            setMessage(this)
        }
        setIndeterminate(mIndeterminate)
        onProgressChanged()
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mHasStarted = true
    }

    override fun onStop() {
        super.onStop()
        mHasStarted = false
    }

    /**
     * Sets the current progress.
     *
     * @param value the current progress, a value between 0 and [getMax]
     *
     * @see ProgressBar.setProgress
     */
    fun setProgress(value: Int) =
        if (mHasStarted) {
            mProgress?.progress = value
            onProgressChanged()
        } else {
            mProgressVal = value
        }


    /**
     * Sets the secondary progress.
     *
     * @param secondaryProgress the current secondary progress, a value between 0 and
     * [getMax]
     *
     * @see ProgressBar#setSecondaryProgress(int)
     */
    fun setSecondaryProgress(secondaryProgress: Int) =
        mProgress?.apply {
            setSecondaryProgress(secondaryProgress)
            onProgressChanged()
        } ?: run {
            mSecondaryProgressVal = secondaryProgress
        }


    /**
     * Gets the current progress.
     *
     * @return the current progress, a value between 0 and [getMax]
     */
    fun getProgress(): Int = mProgress?.progress ?: mProgressVal

    /**
     * Gets the current secondary progress.
     *
     * @return the current secondary progress, a value between 0 and [getMax]
     */
    fun getSecondaryProgress(): Int = mProgress?.secondaryProgress ?: mSecondaryProgressVal

    /**
     * Gets the maximum allowed progress value. The default value is 100.
     *
     * @return the maximum value
     */
    fun getMax(): Int = mProgress?.max ?: mMax

    /**
     * Sets the maximum allowed progress value.
     */
    fun setMax(max: Int) =
        mProgress?.apply {
            this.max = max
            onProgressChanged()
        } ?: run {
            mMax = max
        }

    /**
     * Increments the current progress value.
     *
     * @param diff the amount by which the current progress will be incremented,
     * up to [getMax]
     */
    fun incrementProgressBy(diff: Int) = mProgress?.apply {
        incrementProgressBy(diff)
        onProgressChanged()
    } ?: run {
        mIncrementBy += diff
    }

    /**
     * Increments the current secondary progress value.
     *
     * @param diff the amount by which the current secondary progress will be incremented,
     * up to [getMax]
     */
    fun incrementSecondaryProgressBy(diff: Int) = mProgress?.apply {
        incrementSecondaryProgressBy(diff)
        onProgressChanged()
    } ?: run {
        mIncrementSecondaryBy += diff
    }

    /**
     * Sets the drawable to be used to display the progress value.
     *
     * @param d the drawable to be used
     *
     * @see ProgressBar.setProgressDrawable
     */
    fun setProgressDrawable(d: Drawable) = mProgress?.apply {
        progressDrawable = d
    } ?: run {
        mProgressDrawable = d
    }

    /**
     * Sets the drawable to be used to display the indeterminate progress value.
     *
     * @param d the drawable to be used
     *
     * @see ProgressBar.setProgressDrawable
     * @see setIndeterminate
     */
    fun setIndeterminateDrawable(d: Drawable) = mProgress?.apply {
        indeterminateDrawable = d
    } ?: run {
        mIndeterminateDrawable = d
    }

    /**
     * Change the indeterminate mode for this ProgressDialog. In indeterminate
     * mode, the progress is ignored and the dialog shows an infinite
     * animation instead.
     *
     * <p><strong>Note:</strong> A ProgressDialog with style [STYLE_SPINNER]
     * is always indeterminate and will ignore this setting.</p>
     *
     * @param indeterminate true to enable indeterminate mode, false otherwise
     *
     * @see setProgressStyle
     */
    fun setIndeterminate(indeterminate: Boolean) = mProgress?.apply {
        isIndeterminate = indeterminate
    } ?: run {
        mIndeterminate = indeterminate
    }

    /**
     * Whether this ProgressDialog is in indeterminate mode.
     *
     * @return true if the dialog is in indeterminate mode, false otherwise
     */
    fun isIndeterminate(): Boolean = mProgress?.isIndeterminate ?: mIndeterminate

    override fun setMessage(message: CharSequence?) {
        if (mProgress != null) {
            if (mProgressStyle == STYLE_HORIZONTAL) {
                super.setMessage(message)
            } else {
                mMessageView?.text = message
            }
        } else {
            mMessage = message
        }
    }

    /**
     * Sets the style of this ProgressDialog, either  [STYLE_SPINNER] or
     * [STYLE_HORIZONTAL]. The default is  [STYLE_SPINNER].
     *
     * <p><strong>Note:</strong> A ProgressDialog with style [STYLE_SPINNER]
     * is always indeterminate and will ignore the [setIndeterminate]setting.</p>
     *
     * @param style the style of this ProgressDialog, either [STYLE_SPINNER] or
     * [STYLE_HORIZONTAL]
     */
    fun setProgressStyle(style: Int) {
        mProgressStyle = style
    }

    /**
     * Change the format of the small text showing current and maximum units
     * of progress.  The default is "%1d/%2d".
     * Should not be called during the number is progressing.
     * @param format A string passed to [String.format];
     * use "%1d" for the current number and "%2d" for the maximum.  If null,
     * nothing will be shown.
     */
    fun setProgressNumberFormat(format: String) {
        mProgressNumberFormat = format
        onProgressChanged()
    }

    /**
     * Change the format of the small text showing the percentage of progress.
     * The default is[NumberFormat.getPercentInstance]
     * Should not be called during the number is progressing.
     * @param format An instance of a [NumberFormat] to generate the
     * percentage text.  If null, nothing will be shown.
     */
    fun setProgressPercentFormat(format: NumberFormat) {
        mProgressPercentFormat = format
        onProgressChanged()
    }

    private fun onProgressChanged() {
        if (mProgressStyle == STYLE_HORIZONTAL) {
            mViewUpdateHandler?.apply {
                if (!hasMessages(0)) {
                    sendEmptyMessage(0)
                }
            }
        }
    }
}