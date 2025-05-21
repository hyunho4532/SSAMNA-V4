/*
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

val SongPrint: ImageVector
	get() {
		if (_SongPrint != null) {
			return _SongPrint!!
		}
		_SongPrint = ImageVector.Builder(
            name = "SongIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(8.5f, 23f)
				curveTo(11.53760f, 230f, 140f, 20.53760f, 140f, 17.50f)
				curveTo(140f, 14.46240f, 11.53760f, 120f, 8.50f, 120f)
				curveTo(5.46240f, 120f, 30f, 14.46240f, 30f, 17.50f)
				curveTo(30f, 20.53760f, 5.46240f, 230f, 8.50f, 230f)
				close()
			}
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(11f, 4f)
				horizontalLineTo(14f)
				verticalLineTo(17f)
				horizontalLineTo(11f)
				verticalLineTo(4f)
				close()
			}
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(13f, 1f)
				horizontalLineTo(20f)
				arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 3f)
				verticalLineTo(5f)
				arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 20f, 7f)
				horizontalLineTo(13f)
				arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11f, 5f)
				verticalLineTo(3f)
				arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13f, 1f)
				close()
			}
		}.build()
		return _SongPrint!!
	}

private var _SongPrint: ImageVector? = null
