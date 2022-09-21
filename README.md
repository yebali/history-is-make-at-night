## API 사용 방법
### 장소 검색
#### [POST] localhost:8080/place/search
* request params:
  * keyword: String
  * size: Int(Default value = 10) 
  
> ex) localhost:8080/place/search?keyword=치킨

## 기술 요구사항

- 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 설계 및 구현 (예시. 키워드 별로 검색된 횟수)
: 키워드 별로 검색된 횟수를 정확히 카운트 하기위해 REDIS의 zSet을 사용하였습니다. zSet의 score에 검색 횟수를 원자적으로 저장하여 동시성 이슈를 해결하였습니다.

- 카카오, 네이버 등 검색 API 제공자의 “다양한” 장애 발생 상황에 대한 고려 
: 카카오/네이버 API 제공자의 장애 시, 에러를 던지며 애플리케이션 로직이 멈추는 것을 방지하기 위해 빈리스트를 응답으로 처리했습니다. 그리고 에러 내용을 로깅하여 추후 분석하거나 알 수 있도록 하였습니다.   

- 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
: 검색 제공자들의 API 호출 구현부(feignclient, httpclient 클래스들)를 분리하여 구현했기 때문에 새로운 API제공자(ex. google)가 추가 될 경우 'GoogleSearchPlaceFeign'와 'SearchPlaceFetcher'를 구현하는 'GoogleSearchPlaceHttpClient'만 추가하면 새로운 제공자가 사용 될 수 있도록 하였습니다.     

- 서비스 오류 및 장애 처리 방법에 대한 고려

- 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 고려
: 반응성을 장소 검색 시, 검색 횟수를 저장하는 로직을 @Asycn를 사용하여 비동기로 처리했습니다.

- 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계

- 이 외에도 본인의 기술 역량을 잘 드러낼 수 있는 부분을 과제 코드 내에서 강조