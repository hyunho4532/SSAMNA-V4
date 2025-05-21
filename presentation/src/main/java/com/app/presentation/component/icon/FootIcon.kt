package com.app.presentation.component.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Footprint: ImageVector
	get() {
		if (_Footprint != null) {
			return _Footprint!!
		}
		_Footprint = ImageVector.Builder(
            name = "Footprint",
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
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(9.08549f, 11.5f)
				curveTo(9.13910f, 11.8970f, 9.32480f, 12.25830f, 9.57930f, 12.89830f)
				curveTo(10.14080f, 14.31070f, 10.47430f, 16.42020f, 8.33110f, 16.90360f)
				curveTo(6.18780f, 17.3870f, 5.44210f, 15.96140f, 5.30220f, 14.65920f)
				curveTo(5.2110f, 13.80920f, 5.17720f, 13.38140f, 4.81290f, 12.50f)
				moveTo(9.08549f, 11.5f)
				curveTo(9.02080f, 11.02090f, 9.14830f, 10.48980f, 9.57930f, 9.35390f)
				curveTo(10.36740f, 7.27670f, 9.86920f, 1.80570f, 6.31140f, 2.00530f)
				curveTo(2.1560f, 2.23850f, 2.62630f, 8.10570f, 3.8920f, 10.57570f)
				curveTo(4.33150f, 11.43350f, 4.6190f, 12.0310f, 4.81290f, 12.50f)
				moveTo(9.08549f, 11.5f)
				lineTo(4.81288f, 12.5f)
				moveTo(14.9145f, 16.5f)
				curveTo(14.86090f, 16.8970f, 14.67520f, 17.25830f, 14.42070f, 17.89830f)
				curveTo(13.85920f, 19.31070f, 13.52570f, 21.42020f, 15.66890f, 21.90360f)
				curveTo(17.81220f, 22.3870f, 18.5580f, 20.96140f, 18.69780f, 19.65920f)
				curveTo(18.7890f, 18.80920f, 18.82280f, 18.38140f, 19.18710f, 17.50f)
				moveTo(14.9145f, 16.5f)
				curveTo(14.97920f, 16.02090f, 14.85170f, 15.48980f, 14.42070f, 14.35390f)
				curveTo(13.63260f, 12.27670f, 14.13080f, 6.80570f, 17.68860f, 7.00530f)
				curveTo(21.8440f, 7.23850f, 21.37370f, 13.10570f, 20.1080f, 15.57570f)
				curveTo(19.66850f, 16.43350f, 19.3810f, 17.0310f, 19.18710f, 17.50f)
				moveTo(14.9145f, 16.5f)
				lineTo(19.1871f, 17.5f)
			}
		}.build()
		return _Footprint!!
	}

private var _Footprint: ImageVector? = null
