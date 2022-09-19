package com.yebali.dev.place.search.service.model

import com.yebali.dev.place.util.CoordinateSystem

data class TransformCoordinatesModel(
    val x: Double,
    val y: Double,
    val input_coord: CoordinateSystem,
    val output_coord: CoordinateSystem,
)
