import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val PermiLocation: ImageVector
	get() {
		if (_PermiLocation != null) {
			return _PermiLocation!!
		}
		_PermiLocation = ImageVector.Builder(
			name = "PermiLocation",
			defaultWidth = 32.dp,
			defaultHeight = 32.dp,
			viewportWidth = 32f,
			viewportHeight = 32f
		).apply {
			path(
				fill = SolidColor(Color(0xFF284EE9)),
				pathFillType = PathFillType.NonZero
			) {
				moveTo(16f, 19.2f)
				curveTo(16f, 19.2f, 21.6348f, 14.1914f, 21.6348f, 10.4348f)
				curveTo(21.6348f, 7.32283f, 19.112f, 4.80005f, 16f, 4.80005f)
				curveTo(12.888f, 4.80005f, 10.3652f, 7.32283f, 10.3652f, 10.4348f)
				curveTo(10.3652f, 14.1914f, 16f, 19.2f, 16f, 19.2f)
				close()
			}
			path(
				fill = SolidColor(Color(0xFF2067C2)),
				pathFillType = PathFillType.NonZero
			) {
				moveTo(17.8002f, 10.2002f)
				curveTo(17.8002f, 11.1943f, 16.9944f, 12.0002f, 16.0002f, 12.0002f)
				curveTo(15.0061f, 12.0002f, 14.2002f, 11.1943f, 14.2002f, 10.2002f)
				curveTo(14.2002f, 9.20605f, 15.0061f, 8.40016f, 16.0002f, 8.40016f)
				curveTo(16.9944f, 8.40016f, 17.8002f, 9.20605f, 17.8002f, 10.2002f)
				close()
			}
		}.build()
		return _PermiLocation!!
	}

private var _PermiLocation: ImageVector? = null
