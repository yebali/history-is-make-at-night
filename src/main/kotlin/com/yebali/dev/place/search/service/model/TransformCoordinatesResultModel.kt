package com.yebali.dev.place.search.service.model

import com.yebali.dev.place.util.CoordinateSystem

data class TransformCoordinatesResultModel(
    val coordinateSystem: CoordinateSystem,
    val x: Double,
    val y: Double,
)
