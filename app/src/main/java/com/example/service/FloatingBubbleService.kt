package com.example.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.MatnnevisApp
import com.example.R
import com.example.core.config.AppConfig
import com.example.core.i18n.AppStrings
import com.example.core.i18n.Language
import com.example.data.local.entity.NoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FloatingBubbleService : Service() {

    private var windowManager: WindowManager? = null
    private var bubbleView: View? = null
    private var quickNoteDialogView: View? = null
    private var bubbleParams: WindowManager.LayoutParams? = null
    private var dialogParams: WindowManager.LayoutParams? = null

    private var isDialogShowing = false
    private val scope = CoroutineScope(Dispatchers.Main)
    private var currentStrings: AppStrings.Strings = AppStrings.get(Language.FA)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        if (!hasOverlayPermission(this)) {
            stopSelf()
            return
        }

        scope.launch {
            try {
                val prefs = MatnnevisApp.instance.preferences.userPreferencesFlow.first()
                currentStrings = AppStrings.get(prefs.language)
            } catch (e: Exception) {
                // Keep default
            }
        }

        startForeground(NOTIFICATION_ID, createNotification())
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        setupBubble()
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, MatnnevisApp.CHANNEL_OVERLAY)
            .setContentTitle("${AppConfig.APP_NAME} — ${currentStrings.floatingMode}")
            .setContentText(currentStrings.floatingBubbleDesc)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .build()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupBubble() {
        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val size = (56 * resources.displayMetrics.density).toInt()

        bubbleParams = WindowManager.LayoutParams(
            size,
            size,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 50
            y = 200
        }

        val bubbleContainer = FrameLayout(this).apply {
            val bg = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(0xFF1E3A8A.toInt()) // Persian Navy
                setStroke((2 * resources.displayMetrics.density).toInt(), 0xFF60A5FA.toInt())
            }
            background = bg
            elevation = 16f
        }

        val icon = ImageView(this).apply {
            setImageResource(R.mipmap.ic_launcher)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setPadding(14, 14, 14, 14)
        }
        bubbleContainer.addView(icon)

        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var isClick = true

        bubbleContainer.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = bubbleParams!!.x
                    initialY = bubbleParams!!.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isClick = true
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = (event.rawX - initialTouchX).toInt()
                    val dy = (event.rawY - initialTouchY).toInt()
                    if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
                        isClick = false
                    }
                    bubbleParams!!.x = initialX + dx
                    bubbleParams!!.y = initialY + dy
                    windowManager?.updateViewLayout(bubbleContainer, bubbleParams)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (isClick) {
                        toggleQuickNoteDialog()
                    }
                    true
                }
                else -> false
            }
        }

        bubbleView = bubbleContainer
        try {
            windowManager?.addView(bubbleView, bubbleParams)
        } catch (e: Exception) {
            e.printStackTrace()
            stopSelf()
        }
    }

    private fun toggleQuickNoteDialog() {
        if (isDialogShowing) {
            hideQuickNoteDialog()
        } else {
            showQuickNoteDialog()
        }
    }

    private fun showQuickNoteDialog() {
        if (isDialogShowing) return

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val width = (resources.displayMetrics.widthPixels * 0.88).toInt()
        val height = WindowManager.LayoutParams.WRAP_CONTENT

        dialogParams = WindowManager.LayoutParams(
            width,
            height,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        val cardLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val density = resources.displayMetrics.density
            val pad = (16 * density).toInt()
            setPadding(pad, pad, pad, pad)
            val bg = GradientDrawable().apply {
                setColor(0xFF1E293B.toInt()) // Modern Slate Dark
                cornerRadius = 24 * density
                setStroke((2 * density).toInt(), 0xFF3B82F6.toInt())
            }
            background = bg
            elevation = 24f
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        // Header Row: Title, Minimize, Close
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            weightSum = 1f
        }

        val titleTv = TextView(this).apply {
            text = "${AppConfig.APP_NAME} — ${currentStrings.quickNote}"
            setTextColor(Color.WHITE)
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val closeBtn = Button(this).apply {
            text = "✕"
            setTextColor(0xFFEF4444.toInt())
            setBackgroundColor(Color.TRANSPARENT)
            setOnClickListener { hideQuickNoteDialog() }
        }

        header.addView(titleTv)
        header.addView(closeBtn)
        cardLayout.addView(header)

        // Title Input
        val density = resources.displayMetrics.density
        val titleInput = EditText(this).apply {
            hint = currentStrings.titlePlaceholder
            setHintTextColor(0xFF94A3B8.toInt())
            setTextColor(Color.WHITE)
            textSize = 15f
            val bg = GradientDrawable().apply {
                setColor(0xFF0F172A.toInt())
                cornerRadius = 12 * density
            }
            background = bg
            setPadding((12 * density).toInt(), (10 * density).toInt(), (12 * density).toInt(), (10 * density).toInt())
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = (12 * density).toInt()
            }
        }
        cardLayout.addView(titleInput)

        // Content Input
        val contentInput = EditText(this).apply {
            hint = currentStrings.contentPlaceholder
            setHintTextColor(0xFF94A3B8.toInt())
            setTextColor(Color.WHITE)
            textSize = 14f
            minLines = 4
            maxLines = 8
            gravity = Gravity.TOP or Gravity.START
            val bg = GradientDrawable().apply {
                setColor(0xFF0F172A.toInt())
                cornerRadius = 12 * density
            }
            background = bg
            setPadding((12 * density).toInt(), (10 * density).toInt(), (12 * density).toInt(), (10 * density).toInt())
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = (10 * density).toInt()
            }
        }
        cardLayout.addView(contentInput)

        // Action Buttons Row
        val actionsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = (14 * density).toInt()
            }
        }

        val saveBtn = Button(this).apply {
            text = currentStrings.floatingSaveBtn
            setTextColor(Color.WHITE)
            val btnBg = GradientDrawable().apply {
                setColor(0xFF2563EB.toInt())
                cornerRadius = 12 * density
            }
            background = btnBg
            setOnClickListener {
                val title = titleInput.text.toString().trim()
                val body = contentInput.text.toString().trim()

                if (title.isNotEmpty() || body.isNotEmpty()) {
                    scope.launch {
                        val repository = MatnnevisApp.instance.repository
                        repository.saveNote(
                            NoteEntity(
                                title = title.ifBlank { currentStrings.quickNote },
                                content = body,
                                colorId = "blue"
                            )
                        )
                        Toast.makeText(this@FloatingBubbleService, currentStrings.floatingSavedToast, Toast.LENGTH_SHORT).show()
                        hideQuickNoteDialog()
                    }
                } else {
                    Toast.makeText(this@FloatingBubbleService, currentStrings.floatingEmptyWarning, Toast.LENGTH_SHORT).show()
                }
            }
        }

        actionsLayout.addView(saveBtn)
        cardLayout.addView(actionsLayout)

        quickNoteDialogView = cardLayout
        try {
            windowManager?.addView(quickNoteDialogView, dialogParams)
            isDialogShowing = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hideQuickNoteDialog() {
        if (!isDialogShowing) return
        quickNoteDialogView?.let {
            try {
                windowManager?.removeView(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        quickNoteDialogView = null
        isDialogShowing = false
    }

    override fun onDestroy() {
        hideQuickNoteDialog()
        bubbleView?.let {
            try {
                windowManager?.removeView(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        bubbleView = null
        super.onDestroy()
    }

    companion object {
        const val NOTIFICATION_ID = 1001

        fun hasOverlayPermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Settings.canDrawOverlays(context)
            } else {
                true
            }
        }

        fun start(context: Context) {
            val intent = Intent(context, FloatingBubbleService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, FloatingBubbleService::class.java))
        }
    }
}
