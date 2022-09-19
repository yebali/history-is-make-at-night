package com.yebali.dev.place.search.httpclient

import com.yebali.dev.place.config.openapi.KakaoOpenAPIProperties
import com.yebali.dev.place.feignclient.coodinate.kakao.KakaoTransformCoordinateFeign
import com.yebali.dev.place.feignclient.coodinate.kakao.model.KakaoTransformCoordinateFeignResponseModel
import com.yebali.dev.place.search.service.model.TransformCoordinatesModel
import com.yebali.dev.place.search.service.model.TransformCoordinatesResultModel
import com.yebali.dev.place.util.Logging
import org.springframework.stereotype.Component

@Component
class KaKaoTransformCoordinateHttpClient(
    private val kakaoTransformCoordinateFeign: KakaoTransformCoordinateFeign,
    private val kakaoOpenAPIProperties: KakaoOpenAPIProperties,
) {
    fun transformCoordinate(model: TransformCoordinatesModel): TransformCoordinatesResultModel {
        return transform(model)
            .documents.ifEmpty { throw Exception() }
            .first().let {
                TransformCoordinatesResultModel(
                    coordinateSystem = model.output_coord,
                    x = it.x,
                    y = it.y,
                )
            }
    }

    private fun transform(model: TransformCoordinatesModel): KakaoTransformCoordinateFeignResponseModel {
        try {
            return kakaoTransformCoordinateFeign.transformCoordinate(
                auth = kakaoOpenAPIProperties.restAPIKey,
                x = model.x,
                y = model.y,
                input_coord = model.input_coord.value,
                output_coord = model.output_coord.value,
            )
        } catch (e: Exception) {
            logger.error(e.message)
            throw Exception(e.message)
        }
    }

    companion object : Logging()
}
