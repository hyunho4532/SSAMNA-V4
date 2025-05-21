package com.app.presentation.component.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SearchPrint: ImageVector
	get() {
		if (_SearchPrint != null) {
			return _SearchPrint!!
		}
		_SearchPrint = ImageVector.Builder(
            name = "SearchPrint",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
			group {
				path(
    				fill = null,
    				fillAlpha = 1.0f,
    				stroke = SolidColor(Color(0xFF2B70C9)),
    				strokeAlpha = 1.0f,
    				strokeLineWidth = 2f,
    				strokeLineCap = StrokeCap.Round,
    				strokeLineJoin = StrokeJoin.Round,
    				strokeLineMiter = 1.0f,
    				pathFillType = PathFillType.NonZero
				) {
					moveTo(21f, 21f)
					lineTo(16.6569f, 16.6569f)
				}
				path(
    				fill = SolidColor(Color(0xFF1CB0F6)),
    				fillAlpha = 1.0f,
    				stroke = SolidColor(Color(0xFF1CB0F6)),
    				strokeAlpha = 1.0f,
    				strokeLineWidth = 2f,
    				strokeLineCap = StrokeCap.Round,
    				strokeLineJoin = StrokeJoin.Round,
    				strokeLineMiter = 1.0f,
    				pathFillType = PathFillType.NonZero
				) {
					moveTo(19f, 11f)
					curveTo(190f, 13.20910f, 18.10460f, 15.20910f, 16.65690f, 16.65690f)
					curveTo(15.20910f, 18.10460f, 13.20910f, 190f, 110f, 190f)
					curveTo(6.58170f, 190f, 30f, 15.41830f, 30f, 110f)
					curveTo(30f, 6.58170f, 6.58170f, 30f, 110f, 30f)
					curveTo(15.41830f, 30f, 190f, 6.58170f, 190f, 110f)
					close()
				}
}
		}.build()
		return _SearchPrint!!
	}

private var _SearchPrint: ImageVector? = null
