package com.app.presentation.component.icon/*
* Converted using https://composables.com/svgtocompose
*/

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val TimePrint: ImageVector
	get() {
		if (_TimeIcon != null) {
			return _TimeIcon!!
		}
		_TimeIcon = ImageVector.Builder(
            name = "Icon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
			path(
    			fill = null,
    			fillAlpha = 1.0f,
    			stroke = SolidColor(Color(0xFF000000)),
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 2f,
    			strokeLineCap = StrokeCap.Round,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(10.9999f, 8.31202f)
				verticalLineTo(11.4499f)
				curveTo(10.99990f, 11.65360f, 11.08080f, 11.84890f, 11.22480f, 11.9930f)
				lineTo(12.9199f, 13.688f)
				moveTo(1.3999f, 6.00802f)
				verticalLineTo(5.24002f)
				curveTo(1.39990f, 3.11930f, 3.11910f, 1.40f, 5.23990f, 1.40f)
				horizontalLineTo(6.0079f)
				moveTo(15.9919f, 1.40002f)
				lineTo(16.7599f, 1.40002f)
				curveTo(18.88070f, 1.40f, 20.59990f, 3.11930f, 20.59990f, 5.240f)
				verticalLineTo(6.00802f)
				moveTo(4.8559f, 17.912f)
				lineTo(2.1679f, 20.6f)
				moveTo(19.8319f, 20.6f)
				lineTo(17.1439f, 17.912f)
				moveTo(19.4479f, 11.768f)
				curveTo(19.44790f, 16.43370f, 15.66560f, 20.2160f, 10.99990f, 20.2160f)
				curveTo(6.33420f, 20.2160f, 2.55190f, 16.43370f, 2.55190f, 11.7680f)
				curveTo(2.55190f, 7.10230f, 6.33420f, 3.320f, 10.99990f, 3.320f)
				curveTo(15.66560f, 3.320f, 19.44790f, 7.10230f, 19.44790f, 11.7680f)
				close()
			}
		}.build()
		return _TimeIcon!!
	}

private var _TimeIcon: ImageVector? = null
