package com.example.ui.screens.editor

import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint as AndroidPaint
import android.graphics.Path as AndroidPath
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File

private data class DrawingStroke(
    val points: List<Offset>
)

@Composable
fun DrawingCanvasDialog(
    onDismiss: () -> Unit,
    onSaveDrawing: (String) -> Unit
) {
    val context = LocalContext.current

    val strokes = remember {
        mutableStateListOf<DrawingStroke>()
    }

    var currentPoints by remember {
        mutableStateOf<List<Offset>>(emptyList())
    }

    var strokeWidth by remember {
        mutableStateOf(8f)
    }

    val drawingBackground = Color.White
    val drawingColor = Color.Black

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "نقاشی",
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "بستن"
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .background(drawingBackground)
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        currentPoints = listOf(offset)
                                    },
                                    onDrag = { change, _ ->
                                        change.consume()

                                        currentPoints =
                                            currentPoints + change.position
                                    },
                                    onDragEnd = {
                                        if (currentPoints.size >= 2) {
                                            strokes.add(
                                                DrawingStroke(
                                                    points = currentPoints
                                                )
                                            )
                                        }

                                        currentPoints = emptyList()
                                    },
                                    onDragCancel = {
                                        currentPoints = emptyList()
                                    }
                                )
                            }
                    ) {
                        strokes.forEach { stroke ->
                            if (stroke.points.size >= 2) {
                                val path = Path()

                                path.moveTo(
                                    stroke.points.first().x,
                                    stroke.points.first().y
                                )

                                stroke.points.drop(1).forEach { point ->
                                    path.lineTo(point.x, point.y)
                                }

                                drawPath(
                                    path = path,
                                    color = drawingColor,
                                    style = Stroke(
                                        width = strokeWidth,
                                        cap = StrokeCap.Round,
                                        join = StrokeJoin.Round
                                    )
                                )
                            }
                        }

                        if (currentPoints.size >= 2) {
                            val path = Path()

                            path.moveTo(
                                currentPoints.first().x,
                                currentPoints.first().y
                            )

                            currentPoints.drop(1).forEach { point ->
                                path.lineTo(point.x, point.y)
                            }

                            drawPath(
                                path = path,
                                color = drawingColor,
                                style = Stroke(
                                    width = strokeWidth,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (strokes.isNotEmpty()) {
                                strokes.removeAt(strokes.lastIndex)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "پاک کردن آخرین خط"
                        )
                    }

                    Text(
                        text = "ضخامت قلم",
                        modifier = Modifier.padding(
                            top = 12.dp
                        )
                    )
                }

                Slider(
                    value = strokeWidth,
                    onValueChange = {
                        strokeWidth = it
                    },
                    valueRange = 2f..30f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (strokes.isEmpty()) {
                        return@Button
                    }

                    try {
                        val width = 1200
                        val height = 1200

                        val bitmap = Bitmap.createBitmap(
                            width,
                            height,
                            Bitmap.Config.ARGB_8888
                        )

                        val androidCanvas = AndroidCanvas(bitmap)

                        androidCanvas.drawColor(
                            android.graphics.Color.WHITE
                        )

                        val scaleX = width.toFloat() / 1000f
                        val scaleY = height.toFloat() / 1000f

                        val paint = AndroidPaint().apply {
                            color = android.graphics.Color.BLACK
                            style = AndroidPaint.Style.STROKE
                            strokeCap = AndroidPaint.Cap.ROUND
                            strokeJoin = AndroidPaint.Join.ROUND
                            isAntiAlias = true
                            strokeWidth = strokeWidth * scaleX
                        }

                        strokes.forEach { stroke ->
                            if (stroke.points.size >= 2) {
                                val path = AndroidPath()

                                val first = stroke.points.first()

                                path.moveTo(
                                    first.x * scaleX,
                                    first.y * scaleY
                                )

                                stroke.points.drop(1).forEach { point ->
                                    path.lineTo(
                                        point.x * scaleX,
                                        point.y * scaleY
                                    )
                                }

                                androidCanvas.drawPath(
                                    path,
                                    paint
                                )
                            }
                        }

                        val drawingDirectory = File(
                            context.filesDir,
                            "drawings"
                        )

                        if (!drawingDirectory.exists()) {
                            drawingDirectory.mkdirs()
                        }

                        val file = File(
                            drawingDirectory,
                            "drawing_${System.currentTimeMillis()}.png"
                        )

                        file.outputStream().use { output ->
                            bitmap.compress(
                                Bitmap.CompressFormat.PNG,
                                100,
                                output
                            )
                        }

                        bitmap.recycle()

                        onSaveDrawing(file.absolutePath)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = " ذخیره"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("انصراف")
            }
        }
    )
}
