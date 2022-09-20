package com.yebali.dev.place.util

import org.locationtech.proj4j.CRSFactory
import org.locationtech.proj4j.CoordinateReferenceSystem
import org.locationtech.proj4j.CoordinateTransformFactory
import org.locationtech.proj4j.ProjCoordinate

class CoordinateTransformer {
    /* Coordinate Systems */
    private val WGS84: CoordinateReferenceSystem
    private val KATEC: CoordinateReferenceSystem

    private val coordinateTransformFactory = CoordinateTransformFactory()

    init {
        // 새로운 좌표계가 필요하다면 아래 코드에 추가
        CRSFactory().also {
            WGS84 = it.createFromParameters("WGS84", "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs")
            KATEC = it.createFromParameters("TM128", "+proj=tmerc +lat_0=38 +lon_0=128 +k=0.9999 +x_0=400000 +y_0=600000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43")
        }
    }

    fun transformWGS84ToKATEC(x: String, y: String): Coordinate {
        return transformWGS84ToKATEC(x.toDouble(), y.toDouble())
    }

    fun transformWGS84ToKATEC(x: Double, y: Double): Coordinate {
        val transformer = coordinateTransformFactory.createTransform(WGS84, KATEC)
        val result = ProjCoordinate()

        transformer.transform(
            ProjCoordinate(x, y), // source
            result // output
        )

        return Coordinate(system = KATEC.name, x = result.x, y = result.y)
    }

    data class Coordinate(
        val system: String,
        val x: Double,
        val y: Double,
    )
}
