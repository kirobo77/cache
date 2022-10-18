# 1. 캐싱이란 ?

- 나중에 요청할 결과를 미리 요청해 두었다가 빠르게 서비스를 제공해주는 역할
  - 컴퓨팅에서 캐시는 일반적으로 일시적인 특징이 있는 데이터 하위 집합을 저장하는 고속 데이터 스토리지 계층.
  - 캐시는 캐시의 접근 시간에 비해 원래 데이터를 접근하는 시간이 오래 걸리는 경우나 값을 다시 계산하는 시간을 절약하고 싶은 경우에 사용.
  - 캐시에 데이터를 미리 복사해 놓으면 계산이나 접근 시간 없이 더 빠른 속도로 데이터에 접근 가능.



# 2. 캐싱 작동 원리?

- 캐시의 데이터는 일반적으로 RAM(Random Access Memory)과 같이 빠르게 액세스할 수 있는 하드웨어에 저장되며, 소프트웨어 구성 요소와 함께 사용될 수도 있다.
- 캐시의 주요 목적은 더 느린 기본 스토리지 계층에 액세스해야 하는 필요를 줄임으로써 데이터 검색 성능을 높이는 것이다.
- 속도를 위해 용량을 절충하는 캐시는 일반적으로 데이터의 하위 집합을 일시적으로 저장한다. 
  - 전체를 저장하는 데이터베이스와 다르며 캐시에 어떤 데이타를 저장하느냐에 따라 캐싱의 성능이 좌우된다.



![img](https://d1.awsstatic.com/diagrams/product-page-diagrams/diagram_cachingmicrosite.bed642d676814a94f1a77d56fe1c252861198fbe.png)

| **계층**      | **클라이언트 측**                                            | **DNS**             | **웹**                                                       | **앱**                                    | **데이터베이스**                               |
| ------------- | ------------------------------------------------------------ | ------------------- | ------------------------------------------------------------ | ----------------------------------------- | ---------------------------------------------- |
| **사용 사례** | 웹 사이트에서 웹 콘텐츠를 검색하는 속도 가속화(브라우저 또는 디바이스) | 도메인과 IP 간 확인 | 웹 또는 앱 서버에서 웹 콘텐츠를 검색하는 속도를 높입니다. 웹 세션 관리(서버 측) | 애플리케이션 성능 및 데이터 액세스 가속화 | 데이터베이스 쿼리 요청과 관련한 지연 시간 단축 |
| **기술**      | HTTP 캐시 헤더, 브라우저                                     | DNS 서버            | HTTP 캐시 헤더, CDN, 역방향 프록시, 웹 액셀러레이터, 키-값 스토어 | 키-값 데이터 스토어, 로컬 캐시            | 데이터베이스 버퍼, 키-값 데이터 스토어         |
| **솔루션**    | 브라우저 내장 캐시                                           | DNS 캐시            | Vanish, nginx 등                                             | Redis, Caffein, EHCache 등                | Memory DB                                      |



# 3. 캐싱의 이점

### 3.1 애플리케이션 성능 개선

- 메모리는 디스크(마그네틱 또는 SSD)보다 훨씬 속도가 빠르기 때문에 인 메모리 캐시에서 데이터를 읽는 속도가 매우 빠릅니다(밀리초 미만).
-  이렇게 훨씬 더 빠른 데이터 액세스는 애플리케이션의 전반적인 성능을 개선합니다.

### 3.2 데이터베이스 비용 절감

- 단일 캐시 인스턴스는 수십만 IOPS(초당 입출력 작업)를 제공할 수 있으며, 따라서 수많은 데이터베이스 인스턴스를 대체할 수 있으므로 총 비용이 절감됩니다.
- 이 같은 비용 절감 이점은 기본 데이터베이스에서 처리량당 요금이 부과되는 경우 특히 큽니다. 이 경우 수십 퍼센트의 비용이 절감될 수 있습니다.

### 3.3 백엔드의 로드 감소

- 캐싱은 읽기 로드의 상당 부분을 백엔드 데이터베이스에서 인 메모리 계층으로 리디렉션함으로써 데이터베이스의 로드를 줄이고 로드 시에 성능이 저하되거나 작업 급증 시에 작동이 중단되지 않도록 보호할 수 있습니다.

### 3.4 예측 가능한 성능

- 모던 애플리케이션의 일반적인 과제 중 하나는 애플리케이션 사용량이 급증하는 시기에 대응하는 것입니다. 
- 슈퍼볼이나 선거 기간 동안의 소셜 앱, 블랙 프라이데이 기간 동안의 전자 상거래 웹 사이트 등을 예로 들 수 있습니다. 
- 데이터베이스에 대한 로드가 증가하면 데이터를 가져오는 데 있어 지연 시간이 길어지고 전반적인 애플리케이션 성능이 예측 불가능해집니다. 
- 높은 처리량의 인 메모리 캐시를 활용함으로써 이 문제를 완화할 수 있습니다.

### 3.5 데이터베이스 핫스팟 제거

- 많은 애플리케이션에서 유명 인사 프로필이나 인기있는 제품과 같은 작은 데이터 하위 집합이 나머지 부분에 비해 더 자주 액세스될 것입니다. 
- 이로 인해 데이터베이스에 핫스팟이 발생할 수 있으며 가장 자주 사용되는 데이터의 처리량 요구 사항에 맞추어 데이터베이스 리소스를 초과 프로비저닝해야 할 수 있습니다. 
- 인 메모리 캐시에 공통 키를 저장하면 가장 자주 액세스하는 데이터에 대해 예측 가능한 빠른 성능을 제공하는 동시에 초과 프로비저닝의 필요성을 줄일 수 있습니다.

### 3.6 읽기 처리량(IOPS) 증가

- 인 메모리 시스템은 지연 시간을 줄일 뿐만 아니라 유사한 디스크 기반 데이터베이스에 비해 훨씬 높은 요청 속도(IOPS)를 제공합니다. 
- 분산형 사이드 캐시로 사용되는 단일 인스턴스는 초당 수십만 건의 요청을 처리할 수 있습니다.



## 4. 캐싱유형

## 4.1 데이터베이스 캐싱

- 속도와 처리량 면에서, 데이터베이스가 제공하는 성능은 애플리케이션 전체 성능에 무엇보다 크게 영향을 미칠 수 있습니다. 
- 또한 오늘날 많은 데이터베이스가 비교적 우수한 성능을 제공하지만, 애플리케이션에 더 높은 성능이 요구되는 사용 사례도 많습니다. 
- 데이터베이스 캐싱을 사용하면 처리량을 크게 높이고 백엔드 데이터베이스와 관련한 데이터 검색 지연 시간을 줄일 수 있으므로 애플리케이션의 전반적인 성능이 향상됩니다. 캐시는 성능을 높이기 위해 애플리케이션에 사용할 수 있는 데이터베이스에 인접한 데이터 액세스 계층 역할을 합니다. 
- 데이터베이스 캐시 계층은 관계형 데이터베이스와 NoSQL 데이터베이스를 비롯하여 모든 유형의 데이터베이스의 프런트에 적용할 수 있습니다. 
- 캐시에 데이터를 로드하는 데 사용되는 일반적인 기법으로는 지연 로딩 및 라이트 스루 방식이 있습니다. 자세한 내용은 [여기를 클릭](https://aws.amazon.com/ko/caching/database-caching/)하세요.

## 4.2 콘텐츠 전송 네트워크(CDN)

- 웹 트래픽이 지리적으로 분산되어 있는 경우, 전 세계에 걸쳐 전체 인프라를 복제하는 방식이 현실적으로 불가능할 수도 있고 비용상 효율적이지도 않습니다. 
- [CDN](https://aws.amazon.com/ko/caching/cdn/)은 글로벌 엣지 로케이션 네트워크를 활용하여 동영상, 웹 페이지, 이미지 등 웹 콘텐츠의 캐싱된 복사본을 고객에게 제공하는 기능을 제공합니다. 
- 응답 시간을 줄이기 위해 [CDN](https://aws.amazon.com/ko/caching/cdn/)은 고객과 가장 가까운 엣지 로케이션 또는 원래 요청 위치를 활용함으로써 응답 시간을 단축합니다. 웹
-  자산이 캐시에서 제공되므로 처리량이 대폭 늘어납니다. 
- 동적 데이터의 경우 많은 수의 [CDN](https://aws.amazon.com/ko/caching/cdn/)을 구성하여 오리진 서버에서 데이터를 검색할 수 있습니다. 

## 4.3 DNS(Domain Name System) 캐싱

- 인터넷의 모든 도메인 요청에서는 도메인 이름과 연결된 IP 주소를 확인하기 위해 [DNS](https://aws.amazon.com/route53/what-is-dns/) 캐시 서버를 쿼리합니다. 
- DNS 캐싱은 OS를 비롯한 여러 수준에서 ISP 및 DNS 서버를 통해 실행될 수 있습니다.

## 4.4 웹 세션 관리

- HTTP 세션에는 로그인 정보, 쇼핑 카트 목록, 이전에 본 항목 등, 사이트 사용자와 웹 애플리케이션 간에 교환된 사용자 데이터가 포함됩니다. 
- 웹 사이트에서 우수한 사용자 환경을 제공하는 데 있어서는 사용자의 기본 설정을 기억하고 풍부한 사용자 컨텍스트를 제공함으로써 HTTP 세션을 효과적으로 관리하는 것이 중요합니다. 
- 중앙 집중식 세션 관리 데이터 스토어를 활용하는 것이 모든 웹 서버에서 일관된 사용자 환경을 제공하고, 탄력적인 웹 서버 플릿에서 더 나은 세션 내구성을 제공하며, 세션 데이터가 여러 캐시에 복제되는 경우에 더 높은 가용성을 제공하는 등 여러 가지 이유로 모던 애플리케이션 아키텍처에 이상적인 솔루션이라고 할 수 있습니다.

## 4.5 애플리케이션 프로그래밍 인터페이스(API)

- 오늘날 대부분의 웹 애플리케이션은 API를 기반으로 구축됩니다.
-  API는 일반적으로 HTTP를 통해 액세스할 수 있으며 사용자가 애플리케이션과 상호 작용할 수 있도록 리소스를 노출하는 RESTful 웹 서비스입니다. 
- API를 설계할 때 API의 예상 로드, 권한 부여, 버전 변경이 API 소비자에게 미치는 영향, 그리고 무엇보다 API의 사용 편의성 등을 고려하는 것이 중요합니다.
- API가 비즈니스 로직을 인스턴스화하고 모든 요청에서 데이터베이스로 백엔드 요청을 보내야 하는 것은 아닙니다. 
- 경우에 따라서는 API의 캐싱된 결과를 제공하는 것이 비용 효율적인 최적의 응답 방식이 될 수 있습니다. 
- 기반 데이터의 변경 속도에 맞춰 API 응답을 캐싱할 수 있는 경우 특히 그렇습니다. 
- 예를 들어 제품 리스팅 API를 사용자에게 노출했으며 제품 범주는 하루에 한 번만 변경된다고 가정해보겠습니다. 
- 제품 범주 요청에 대한 응답은 하루 종일 API에 대한 호출이 있을 때마다 동일하므로, 하루 동안 API 응답을 캐싱하는 것으로 충분합니다. 
- API 응답을 캐싱함으로써 애플리케이션 서버 및 데이터베이스를 비롯한 인프라에 가해지는 부담을 줄일 수 있습니다. 
- 또한 응답 시간이 빨라져 더 높은 성능의 API를 제공할 수 있습니다.

## 4.6 하이브리드 환경의 캐싱

- 하이브리드 클라우드 환경에는 클라우드에 상주하면서 온프레미스 데이터베이스에 자주 액세스해야 하는 애플리케이션이 있을 수 있습니다. 
- VPN 및 Direct Connect를 비롯하여, 클라우드와 온프레미스 환경 간의 연결을 구현하기 위해 사용할 수 있는 다양한 네트워크 토폴로지가 있습니다. 
- 또한 VPC에서 온프레미스 데이터 센터까지의 지연 시간이 짧더라도, 전체 데이터 검색 성능을 가속화하기 위해 클라우드 환경에서 온프레미스 데이터를 캐싱하는 것이 가장 바람직할 수도 있습니다.

## 4.7 웹 캐싱

- 최종 사용자에게 웹 콘텐츠를 제공할 때, 아티팩트를 캐싱하여 디스크 읽기와 서버 로드를 배제하면 이미지, html 문서, 동영상 등의 웹 자산 검색과 관련된 대부분의 지연 시간을 크게 줄일 수 있습니다. 
- 서버와 클라이언트 양쪽 모두에서 다양한 웹 캐싱 기술을 활용할 수 있습니다. 
- 서버 측 웹 캐싱은 일반적으로 프런트에 위치한 웹 서버의 웹 응답을 보존하는 웹 프록시를 활용하여 로드 및 대기 시간을 효과적으로 줄입니다. 
- 클라이언트 측 웹 캐싱에는 이전에 방문한 웹 콘텐츠의 캐싱된 버전을 유지하는 브라우저 기반 캐싱이 포함될 수 있습니다. 

## 4.7 일반 캐시

- 메모리에서 데이터에 액세스하면 디스크나 SSD에서 데이터에 액세스하는 것보다 훨씬 빠르기 때문에 캐시의 데이터를 활용하면 많은 이점이 있습니다.
-  트랜잭션 데이터 지원이나 디스크 기반의 내구성이 필요하지 않은 여러 사용 사례에서 인 메모리 키-값 스토어를 독립 실행형 데이터베이스로 사용하는 방법은 고성능 애플리케이션을 구축하는 데 효과적입니다.
-  애플리케이션 속도는 물론, 경제적인 가격으로 제공되는 높은 처리량을 통해 이점을 얻을 수 있습니다. 제품 그룹, 범주 목록, 프로파일 정보 등의 참조 가능한 데이터는 [일반 캐시](https://aws.amazon.com/ko/caching/general-cache/)의 좋은 사용 사례입니다. 

## 4.8 통합 캐시

- 통합 캐시는 오리진 데이터베이스에서 자주 액세스하는 데이터를 자동으로 캐싱하는 인 메모리 계층입니다.
- 가장 일반적으로, 기반 데이터베이스는 데이터가 캐시에 상주하는 경우 캐시를 사용하여 인바운드 데이터베이스 요청에 대한 응답을 제공합니다. 
- 이 경우 요청 지연 시간이 단축되고 데이터베이스 엔진의 CPU 및 메모리 사용률이 감소하여 데이터베이스의 성능이 크게 향상됩니다. 
- 통합 캐시의 중요한 특성 중 하나는 캐싱된 데이터가 데이터베이스 엔진이 디스크에 저장한 데이터와 일치한다는 것입니다.



# 5. 캐싱 전략 선택 시 고려할 점

캐시가 효율적으로 동작하기 위해선 데이터가 다음과 같은 특징을 가져야 합니다.

- 자주 조회됨
- 데이터의 결과값이 일정함
- 연산이 무거움

## 5.1 Local Cache

- Local 장비 내에서만 사용되는 캐시로, Local 장비의 Resource를 이용한다.
- Local에서만 작동하기 때문에 속도가 빠르다.
- Local에서만 작동하기 때문에 다른 서버와 데이터 공유가 어렵다.
- EhCache, Caffeine , ConcurrentMap 등이 있으며 일반적으로 K,V 구조만 지원한다.

##  5.2 Global Cache

- 여러 서버에서 Cache Server에 접근하여 사용하는 캐시로 분산된 서버에서 데이터를 저장하고 조회할 수 있다.
- 네트워크를 통해 데이터를 가져오므로, Local Cache에 비해 상대적으로 느리다.
- 별도의 Cache서버를 이용하기 때문에 서버 간의 데이터 공유가 쉽다.
- Redis, ElastiCache 등이 있고 Collection 등 다양한 자료구조를 지원한다.



## 5.3 Cache Hit

### 5.3.1 Hit Ratio



![img](https://k.kakaocdn.net/dn/EaC9H/btqGkQCmH4Z/yKV0xt77wjSSy1kaqFebzk/img.png)



> 위 그림은 파레토 법칙을 표현합니다.  
> 시스템 리소스 20%가 전체 전체 시간의 80% 정도를 소요함을 의미합니다.  
> 따라서 캐시 대상을 선정할 때에는 캐시 오브젝트가 얼마나 자주 사용하는지, 적용시 전체적인 성능을 대폭 개선할 수 있는지 등을 따져야합니다.

- Cache 용어

  - cache hit

    - 참조하려는 데이터가 캐시에 존재할 때 cache hit라 함

  - cache miss

    - 참조하려는 데이터가 캐시에 존재 하지 않을 때 cache miss라 함

  - cache hit ratio(캐시 히트율)

    - 

    ```
    (cache hit 횟수)/(전체참조횟수) = (cache hit 횟수)/(cache hit 횟수 + cache miss 횟수)
    ```

### 5.3.2 cache hit ratio 높이기 위한 방안

- 자주 참조되며 수정이 잘 발생하지 않는 데이터들로 구성되어야 한다.
- 데이터의 수정이 잦은 경우 데이터베이스 접근 및 캐시 데이터 일관성 처리 과정이 필요함.
- 캐시 만료 정책을 적절하게 설정하고 오랜 시간이 지난 데이터는 캐시 저장소에서 제거될 수 있도록 운영해야 한다.



# 6. 캐시 전략

### Cache-through

- 캐시에 데이터가 없는 상황에서 Miss가 발생했을 때, Application이 아닌 캐시제공자가 데이터를 처리한 다음 Application에게 데이터를 전달하는 방법입니다. 즉 기존에는 동기화의 책임이 Application에 있었다면, 해당 패턴은 캐시 제공자에게 책임이 위임됩니다.

## 6.1 읽기

### 6.1.1 No-Caching

캐시없이 Application에서 직접 DB로 요청하는 방식을 말합니다. 별도 캐시한 내역이 없으므로 매번 DB와의 통신이 필요하며, 부하가 유발되는 SQL이 지속 수행되면 DB I/O에 영향을 줍니다.

 ![img](https://k.kakaocdn.net/dn/bvg7yO/btqGisipDdU/8SMXlw4p3fcWsmWBSIIvck/img.png)



###  6.1.2 Cache-aside(Lazy-Loding)

- 애플리케이션에서 가장 일반적으로 사용되는 캐시전략.

- 주로 읽기 작업이 많은 애플리케이션에 적합함.

- Cache Hit 의 경우 DB 를 확인하지 않기 때문에 **캐시가 최신 데이터를 가지고 있는지 (동기화) 가 중요하다.**

- 캐시가 분리되어 있기 때문에 원하는 데이터만 별도로 구성하여 캐시에 저장할 수 있고 **캐시에 장애가 발생해도 DB 에서 데이터를 가져오는 데 문제가 없다.**

- 캐시에 장애가 발생했다는 뜻은 DB 로 직접 오는 요청이 많아져서 전체적인 장애로 이어질 수 있다. 



![img](https://k.kakaocdn.net/dn/bp1gsz/btqGebu9mu1/SJwJCBMlZ2Zz5J4T3tf8Vk/img.png)



 캐시에 데이터가 있는지 확인

2. 데이터가 존재하면 (Cache Hit) 해당 캐시 데이터를 반환
3. 데이터가 존재하지 않으면 (Cache Miss) **애플리케이션에서 DB 에 데이터 요청** 후 캐시에 저장하고 데이터를 반환

### 6.1.3 Read-through

- Cache Aside 와 비슷하지만 데이터 동기화를 라이브러리 또는 캐시 제공자에게 위임하는 방식이라는 차이점이 있다.

- 마찬가지로 읽기 작업이 많은 경우에 적합하며 두 방법 다 데이터를 처음 읽는 경우에는 Cache Miss 가 발생해서 느리다는 특징이 있다.

- Cache Aside 와는 다르게 캐시에 의존성이 높기 때문에 캐시에 장애가 발생한 경우 바로 전체 장애로 이어진다

- 이를 방지하기 위해 Cache Cluster 등 가용성 높은 시스템을 구축하여야 한다.

![img](https://k.kakaocdn.net/dn/Gco8s/btqGh5A2FMc/N5wWiU5bifY8vg22sHmoKk/img.png)

1. 캐시에 데이터 요청
2. 캐시는 데이터가 있으면 (Cache Hit) 바로 반환
3. 데이터가 없다면 (Cache Miss) **캐시가 DB 에서 데이터를 조회**한 후에 캐시에 저장 후 반환

### 6.1.4 읽기 캐시에서 발생 가능한 장애

####  6.1.4.1 Thundering Herd

- 캐시 읽기 전략에서는 공통적으로 캐시 확인 -> DB 확인 순서로 이어지는데 이 과정에서 캐시에 데이터가 있으면 DB 확인을 생략하는 것으로 성능을 향상시킨다.

- 하지만 서비스를 이제 막 오픈해서 **캐시가 비어있는 경우에는 들어오는 요청이 전부 Cache Miss 가 발생하고 DB 조회 후 캐시를 갱신**하느라 장애가 발생할 수 있다.

###### 해결방안

-  캐시에 데이터를 미리 세팅해두는 Cache Warm up 작업을 하거나 첫 요청이 캐시 갱신될 때까지 기다린 후에 이후 요청은 전부 캐시에서 반환하게 할 수 있다.

- Cache Warm up 작업을 할 때 어떤 데이터를 넣느냐에 따라 마찬가지로 Cache Miss 가 발생할 수 있기 때문에 자주 들어올만한 데이터의 예측이 중요.

#### 6.1.4.2 Cache Stampede

- Cache Stampede 란 캐시가 만료될 때, 대용량 트래픽의 경우 캐시 miss 가 여러번 발생할 수 있다.. 
- 여러 요청에 대해 cache miss 가 발생하면 각 request 는 DB 를 조회해서 데이터를 읽어와 캐시에 여러 번 쓰게 된다.
- cache miss 와 cache write 가 여러번 발생해서 성능에 영향을 주는 것이 Cache Stampede 입니다.

###### 해결방안

- 이런 현상을 해결하기 위해 PER(Probablistic Early Recomputation) 알고리즘을 도입.

- PER 이란 TTL (만료시간) 이후 캐시를 갱신할 것인지에 대한 알고리즘입니다. 보통은 TTL 이후 캐시를 버리지만, PER 알고리즘을 사용하면 일정 확률로 캐시를 갱신한다.


#### 6.1.4.3 Hot Key

- hot key 란 하나의 키에 대한 빈번한 접근을 말한다.
- 대용량 트래픽에서 hot key 에 대한 대응방안이 안돼있다면 성능 문제가 발생할 수 있다.

###### 해결방안

서버 증설

- 서버가 죽었을 때 failover 나 서버를 scale out 경우 등 고가용성을 위한 추가적인 관리요소가 발생한다.

복제본 생성 : 

- test 라는 hot-key 키가 있다면 앞에 prefix 를 붙이고 저장한 후 해당 키에 접근할 때는 prefix 를 랜덤으로 붙여 조회한다.

압축

- 데이터를 저장할 때, 용량이 크다면 성능 저하가 발생한다.
- 이를 해결하기 위해 압축을 고려해볼 수 있다.
- 압축을 할 때 고려사항은 적절한 압축 비율을 찾아야 한다.
  - cpu , 안정성 등을 고려해 다른 환경에서 테스트를 해봐야 한다.



## 6.2 쓰기

쓰기패턴은 쓰기 요청 시 어떤 시점에 캐시 갱신을 하는지에 따라 다음과 같이 세분화할 수 있다.

- Write Around: 캐시를 갱신하지 않음
- Write Through: 캐시를 바로 갱신
- Write Back: 캐시를 모아서 나중에 갱신

### 6.2.1 Write Around

- 데이타 쓰기 요청 시 DB에반 반영하고 캐시는 사용하지 않는다.

- 수정사항은 DB 에만 반영하고 캐시는 그대로 두기 때문에 Cache Miss 가 발생하기 전까지는 캐시 갱신이 발생하지 않는다.

- Cache 가 갱신된지 얼마 안된 경우에는 **캐시 Expire 처리 되기 전까지 계속 DB 와 다른 데이터를 갖고 있다는 단점**이 있습니다.

- 업데이트 이후 바로 조회되지 않을거라는 확신이 있다면 캐시를 초기화하여 Cache Miss 를 유도하는 방법으로 보완할 수 있습니다.

1. 데이터 추가/업데이트 요청이 들어오면 DB 에만 데이터를 반영
2. 쓰기 작업에서 캐시는 건들지 않고 읽기 작업 시 Cache Miss 가 발생하면 업데이트 됨

### 6.2.2 Write-through

- Read Through 와 마찬가지로 DB 동기화 작업을 캐시에게 위임합니다.

- 동기화까지 완료한 후에 데이터를 반환하기 때문에 **캐시를 항상 최신 상태로 유지할 수 있다**는 장점이 있습니다.

- 캐시 및 DB 를 동기식으로 갱신한 후에 최종 데이터 반환이 발생하기 때문에 전반적으로 느려질 수 있습니다.

- 새로운 데이터를 캐시에 미리 넣어두기 때문에 읽기 성능을 향상시킬 수 있지만 **이후에 읽히지 않을 데이터도 넣어두는 리소스 낭비**가 발생할 수 있습니다.

![img](https://k.kakaocdn.net/dn/bYPnro/btqGiKQGShj/IVd7xZJTx986KcXKcxC8lK/img.png)

1. 캐시에 데이터를 추가하거나 업데이트
2. 캐시가 DB 에 동기식으로 데이터 갱신
3. 캐시 데이터를 반환

### 6.2.3 Write-behind

- 캐시와 DB 동기화를 비동기로 하는 방법이며 동기화 과정이 생략되기 때문에 **쓰기 작업이 많은 경우에 적합**하다.

- 캐시에서 일정 시간 또는 일정량의 **데이터를 모아놓았다가 한번에 DB 에 업데이트** 하기 때문에 쓰기 비용을 절약할 수 있다.

- 다른 캐시 전략에 비해 구현하기 복잡한 편이며 캐시에서 DB 로 데이터를 업데이트 하기 전에 장애가 발생하면 데이터가 유실될 수 있다.

![img](https://k.kakaocdn.net/dn/V0X4m/btqGfMnWkrp/t8M6EkqGwNK75ZpKtVlKzK/img.png)

1. 캐시에 데이터를 추가하거나 업데이트
2. 캐시 데이터 반환
3. 캐시에 있던 데이터는 이후에 별도 서비스 (이벤트 큐 등) 를 통해 DB 에 업데이트

### 6.2.4 Refresh Ahead

- 자주 사용되는 데이터를 캐시 만료 전에 미리 TTL (Expire time) 을 갱신한다.

- 캐시 미스 발생을 최소화 할 수 있지만 Warm Up 작업과 마찬가지로 자주 사용되는 데이터를 잘 예측해야 한다.



# 7. Caching Data with Spring

### 7.1 개요

- **Spring에서 Caching 추상화를 사용하는 방법 과 일반적으로 시스템의 성능을 향상시키는 기능을 제공**한다.

### 7.2 의존성 추가

- Spring Boot를 사용하는 경우 *[spring-boot-starter-cache](https://search.maven.org/search?q=g:org.springframework.boot a:spring-boot-starter-cache)* 스타터 패키지를 활용하여 캐싱 종속성을 쉽게 추가할 수 있다.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
    <version>2.4.0</version>
</dependency>
```

### 7.3 캐싱 활성화

- 캐싱을 활성화하기 위해 Spring은 프레임워크에서 다른 구성 수준 기능을 활성화하는 것과 마찬가지로 어노테이션을 사용한다.
- *구성 클래스에 @EnableCaching* 주석을 추가하여 캐싱 기능을 활성화할 수 있다.
- Spring Boot에서는 `spring-boot-starter-cache` Artifact를 추가 하여 CacheManager를 구성 할 수 있다.
- 별도의 추가적인 서드파티 모듈이 없는 경우에는 Local Memory에 저장이 가능한 ConcurrentMap기반인 `ConcurrentMapCacheManager`가 Bean으로 자동 생성 된다.
- EHCache, Ceffeine, Redis등의 서드파티 모듈을 추가 하게 되면 `EHCacheCacheManager`,  CaffeineCacheManager, RedisCacheManager를 Bean으로 등록 하여 사용할 수 있다. 
- 별도로 다른 설정 없이도 단순 Memory Cache가 아닌 Cache Server를 대상으로 캐시를 저장 할 수 있도록 지원하고 있다.

```java
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("addresses");
    }
}
```



### 7.4 주석과 함께 캐싱 사용

- 캐싱을 사용하기 위해 어노테이션을 사용하여 캐싱 동작을 메서드에 바인딩할 수 있다.

### 7.4.1 @Cacheable

- 메소드에 대한 캐싱 동작을 활성화하는 가장 간단한 방법은 *@Cacheable* 로 구분 하고 결과가 저장될 캐시 이름으로 매개변수화한다.

```java
@Cacheable("addresses")
public String getAddress(Customer customer) {...}
```

*getAddress()* 호출 은 실제로 메서드를 호출한 다음 결과를 캐싱하기 전에 먼저 캐시 *주소 를 확인한다.*

대부분의 경우 하나의 캐시로 충분하지만 Spring 프레임워크는 매개변수로 전달할 여러 캐시도 지원한다..

```java
@Cacheable({"addresses", "directory"})
public String getAddress(Customer customer) {...}
```

이 경우 캐시에 필요한 결과가 포함된 경우 결과가 반환되고 메서드가 호출되지 않는다.

#### Optional Element

| **Element**      | **Description**                                              | **Type** |
| ---------------- | ------------------------------------------------------------ | -------- |
| **cacheNames**   | 캐시 이름 (설정 메서드 리턴값이 저장되는)                    | String[] |
| **value**        | cacheName의 alias                                            | String[] |
| **key**          | 동적인 키 값을 사용하는 SpEL 표현식 동일한 cache name을 사용하지만 구분될 필요가 있을 경우 사용되는 값 | String   |
| **condition**    | SpEL 표현식이 참일 경우에만 캐싱 적용 - or, and 등 조건식, 논리연산 가능 | String   |
| **unless**       | 캐싱을 막기 위해 사용되는 SpEL 표현식 condition과 반대로 참일 경우에만 캐싱이 적용되지 않음 | String   |
| **cacheManager** | 사용 할 CacheManager 지정 (EHCacheCacheManager, RedisCacheManager 등) | String   |
| **sync**         | 여러 스레드가 동일한 키에 대한 값을 로드하려고 할 경우, 기본 메서드의 호출을 동기화 즉, 캐시 구현체가 Thread safe 하지 않는 경우, 캐시에 동기화를 걸 수 있는 속성 | boolean  |

 

### 7.4.2 @CacheEvict

- 자주 필요하지 않은 값으로 캐시를 채울 경우 캐시는 상당히 크고 빠르게 증가할 수 있으며 오래되거나 사용되지 않는 데이터를 많이 보유할 수 있다. 
- 이 경우 새로운 값을 캐시에 다시 로드할 수 있도록 @CacheEvict 주석을 사용하여 하나 이상의 모든 값을 제거 할수 가 있다.

```java
@CacheEvict(value="addresses", allEntries=true)
public String getAddress(Customer customer) {...}
```

- 여기서 비울 캐시와 함께 *allEntries 추가 매개변수를 사용한다.* 이렇게 하면 캐시 *주소* 의 모든 항목이 지워지고 새 데이터를 위해 준비됩니다.

#### Optional Element

| **Element**          | **Description**                                              | **Type** |
| -------------------- | ------------------------------------------------------------ | -------- |
| **cacheNames**       | 제거할 캐시 이름                                             | String[] |
| **value**            | cacheName의 Alias                                            | String[] |
| **key**              | 동적인 키 값을 사용하는 SpEL 표현식 동일한 cache name을 사용하지만 구분될 필요가 있을 경우 사용되는 값 | String   |
| **allEntries**       | 캐시 내의 모든 리소스를 삭제할지의 여부                      | boolean  |
| **condition**        | SpEL 표현식이 참일 경우에만 삭제 진행 - or, and 등 조건식, 논리연산 가능 | String   |
| **cacheManager**     | 사용 할 CacheManager 지정 (EHCacheCacheManager, RedisCacheManager 등) | String   |
| **beforeInvocation** | true - 메서드 수행 이전 캐시 리소스 삭제 false - 메서드 수행 후 캐시 리소스 삭제 | boolean  |

 

### 7.4.3 @CachePut

- *@CacheEvict* 가 오래되고 사용되지 않는 항목을 제거하여 대용량 캐시에서 항목을 조회하는 오버헤드를 줄이는 동안 캐시에서 너무 많은 데이터를 제거하는 것을 방지할 필요성이 있을 경우 *@CachePut* 주석을 사용 하면 메서드 실행을 방해하지 않고 캐시 내용을 업데이트할 수 있다.

```java
@CachePut(value="addresses")
public String getAddress(Customer customer) {...}
```

- *@Cacheable* 과 *@CachePut* 의 차이점은 @Cacheable은 *메서드* 실행 을 **건너뛰지** 만 *@CachePut* 은 **실제로 메서드** 를 실행한 다음 그 결과를 캐시에 저장한다는 것이다.

#### Optional Element

| **Element**  | ***\*Description\****                                        | ***\**\*Type\*\**\*** |
| ------------ | ------------------------------------------------------------ | --------------------- |
| cacheName    | 입력할 캐시 이름                                             | String[]              |
| value        | cacheNamed의 Alias                                           | String[]              |
| key          | 동적인 키 값을 사용하는 SpEL 표현식 동일한 cache name을 사용하지만 구분될 필요가 있을 경우 사용되는 값 | String                |
| cacheManager | 사용 할 CacheManager 지정 (EHCacheCacheManager, RedisCacheManager 등) | String                |
| condition    | SpEL 표현식이 참일 경우에만 캐싱 적용 - or, and 등 조건식, 논리연산 가능 | String                |
| unless       | 캐싱을 막기 위해 사용되는 SpEL 표현식 condition과 반대로 참일 경우에만 캐싱이 적용되지 않음 | String                |

 

### 7.4.4 @Caching

- 메서드를 캐싱하기 위해 동일한 유형의 여러 어노테이션을 사용할 경우 아래와 같이 사용하는 것은 허용하지 않는다.

```java
@CacheEvict("addresses")
@CacheEvict(value="directory", key=customer.name)
public String getAddress(Customer customer) {...}
```

- 그럴경우 아래와 같이 캐싱관련 어노테이션을 그룹화하는 @Caching을 사용해야 한다.

```java
@Caching(evict = { 
  @CacheEvict("addresses"), 
  @CacheEvict(value="directory", key="#customer.name") })
public String getAddress(Customer customer) {...}
```

#### Optional Element

| **Element**  | ***\*Description\****                                        | ***\**\*Type\*\**\*** |
| ------------ | ------------------------------------------------------------ | --------------------- |
| cacheName    | 입력할 캐시 이름                                             | String[]              |
| value        | cacheNamed의 Alias                                           | String[]              |
| key          | 동적인 키 값을 사용하는 SpEL 표현식 동일한 cache name을 사용하지만 구분될 필요가 있을 경우 사용되는 값 | String                |
| cacheManager | 사용 할 CacheManager 지정 (EHCacheCacheManager, RedisCacheManager 등) | String                |
| condition    | SpEL 표현식이 참일 경우에만 캐싱 적용 - or, and 등 조건식, 논리연산 가능 | String                |
| unless       | 캐싱을 막기 위해 사용되는 SpEL 표현식 condition과 반대로 참일 경우에만 캐싱이 적용되지 않음 | String                |

 

### 7.4.5 @CacheConfig

- *@CacheConfig* 주석을 사용하면 캐시 구성의 일부를 클래스 수준의 단일 위치로 간소화할 수 있으므로 **여러** 번 선언할 필요가 없다.

```java
@CacheConfig(cacheNames={"addresses"})
public class CustomerDataService {

    @Cacheable
    public String getAddress(Customer customer) {...}
```

####  Optional Element

| **Element**  | ***\*Description\****                                        | ***\**\*Type\*\**\*** |
| ------------ | ------------------------------------------------------------ | --------------------- |
| cacheNames   | 해당 클래스 내 정의된 캐시 작업에서의 default 캐시 이름      | String[]              |
| cacheManager | 사용 할 CacheManager 지정 (EHCacheCacheManager, RedisCacheManager 등) | String                |

 

### 7.4.6  조건부 캐싱

- 경우에 따라 모든 상황에서 메서드에 대해 캐싱이 제대로 작동하지 않을 수 있으며 이 경우 조건부 캐싱을 활용하여 효과적인 캐쉬를 사용할 수 있다.

```java
@CachePut(value="addresses")
public String getAddress(Customer customer) {...}
```

- 조건 매개변수

주석이 활성화될 때 더 많은 제어를 원하면 SpEL 표현식을 사용하고 해당 표현식 평가를 기반으로 결과가 캐시되도록 하는 조건 매개 변수로 *@CachePut 을 매개변수화할 수 있다.*

```java
@CachePut(value="addresses", condition="#customer.name=='Tom'")
public String getAddress(Customer customer) {...}
```

- 매개변수가 아닌 경우

*if* 매개변수 를 통한 **입력이 아닌 메소드의 출력을 기반으로** 캐싱을 제어할 수 있다.

```java
@CachePut(value="addresses", unless="#result.length()<64")
public String getAddress(Customer customer) {...}
```

- 위의 주석은 주소가 64자 미만인 경우를 제외하고 주소를 캐시한다.
- 이러한 종류의 조건부 캐싱은 큰 결과를 관리하는 데 매우 효과적이며 모든 작업에 일반 동작을 적용하는 대신 입력 매개 변수를 기반으로 동작을 사용자 지정하는 데 유용하다.



### 7.4.7 장애 처리

- 레디스가 동작하지 않는 동안에는 캐시가 아닌 DB 에서 조회 하도록 fallback 기능이 있어야 한다.

- spring-retry 의 @Retryable 과 @Recover 를 사용하면 된다.
- @Retryable 은 예외 발생시 재시도를 하고, @Recover는 재시도 실패시에 동작하는 어노테이션 이다.

```java
@Retryable(maxAttempts = 1)
@Cacheable(cacheNames = "allBeacons", key = "'allBeacons:'+#storeId")
public List<StoreBeaconSignalHistoryDto> allBeacons(Long storeId) {
  return find(storeId);
}

@Recover
public List<StoreBeaconSignalHistoryDto> allBeacons(Exception e, Long storeId) {
  log.error(e.getMessage());
  return find(storeId);
}
```



### 7.4.7 CacheManager

- @Cacheable 어노테이션을 사용하여 생성한 캐시를 조회하고 삭제하는 등 로우레벨의 캐시를 활용할 수 있다.

```
import org.springframework.cache.CacheManager;

	@Autowired
	CacheManager cacheManager;
```

- Key, Value 정보 조회

```
public ResponseEntity<Object> ehCacheValues() {

    List<Map<String, String>> result = new ArrayList<>();
    for (String cacheName : cacheManager.getCacheNames()) {
        EhCacheCache cache = (EhCacheCache) cacheManager.getCache(cacheName);
        Ehcache ehcache = cache.getNativeCache();
        for (Object key : ehcache.getKeys()) {
            Element element = ehcache.get(key);
            if (element != null) {
                result.add(Map.of(cacheName, element.toString()));
            }
        }
    }

    return ResponseEntity.ok(result);
}
```

caffeine

```
public ResponseEntity<Object> caffeineValues() {

    Collection<String> cacheNames = cacheManager.getCacheNames();
    Map<String, ConcurrentMap<Object, Object>> result = new HashMap<>(cacheNames.size());
    for (String cacheName : cacheNames) {
        CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache<Object, Object> nativeCache = cache.getNativeCache();
            result.put(cacheName, nativeCache.asMap());
        }
    }

    return ResponseEntity.ok(result);
}

public ResponseEntity<Object> caffeineStatInfo() {

    Collection<String> cacheNames = cacheManager.getCacheNames();
    List<Map<String, String>> result = new ArrayList<>(cacheNames.size());
    for (String cacheName : cacheNames) {
        CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache<Object, Object> nativeCache = cache.getNativeCache();
            result.add(Map.of(cacheName, nativeCache.stats().toString()));
        }
    }

    return ResponseEntity.ok(result);
}
```

 

#### local cache 삭제

##### 다건 삭제

```
public void clearAll() {
cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
}
```

##### 단건 삭제

```
public void clearTarget(String cacheName) {
cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
}
```

 

## 7.5 Redis Repository와 함게 캐싱 사용

Spring Data Redis 의 Redis Repository 를 이용하면 간단하게 Domain Entity 를 Redis Hash 로 만들 수 있습니다.

다만 트랜잭션을 지원하지 않기 때문에 만약 트랜잭션을 적용하고 싶다면 `RedisTemplate` 을 사용해야 합니다.

Entity

```
@Getter
@RedisHash(value = "people", timeToLive = 30)
public class Person {

    @Id
    private String id;
    private String name;
    private Integer age;
    private LocalDateTime createdAt;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }
}
```



Redis 에 저장할 자료구조인 객체를 정의합니다.
일반적인 객체 선언 후 @RedisHash 를 붙이면 됩니다.
value : Redis 의 keyspace 값으로 사용됩니다.
timeToLive : 만료시간을 seconds 단위로 설정할 수 있습니다. 기본값은 만료시간이 없는 -1L 입니다.
@Id 어노테이션이 붙은 필드가 Redis Key 값이 되며 null 로 세팅하면 랜덤값이 설정됩니다.
keyspace 와 합쳐져서 레디스에 저장된 최종 키 값은 keyspace:id 가 됩니다.



**Repository**

```
public interface PersonRedisRepository extends CrudRepository<Person, String> {
}
```

- `CrudRepository` 를 상속받는 `Repository` 클래스 추가합니다.



**Example**

```java
public class RedisRepositoryTest {
    @Autowired
    private PersonRedisRepository repo;

    @Test
    void test() {
        Person person = new Person("Park", 20);
        // 저장
        repo.save(person);
        // `keyspace:id` 값을 가져옴
        repo.findById(person.getId());
        // Person Entity 의 @RedisHash 에 정의되어 있는 keyspace (people) 에 속한 키의 갯수를 구함
        repo.count();
        // 삭제
        repo.delete(person);
    }
}
```

- JPA 와 동일하게 사용할 수 있습니다.
- 여기서는 `id` 값을 따로 설정하지 않아서 랜덤한 키값이 들어갑니다.
- 저장할때 `save()` 를 사용하고 값을 조회할 때 `findById()` 를 사용합니다.



## 7.6 RedisTemplate와 함게 캐싱 사용

`RedisTemplate` 을 사용하면 특정 Entity 뿐만 아니라 여러가지 원하는 타입을 넣을 수 있다.

`template` 을 선언한 후 원하는 타입에 맞는 `Operations` 을 꺼내서 사용한다.

config

```
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
```

- `RedisTemplate` 에 `LettuceConnectionFactory` 을 적용해주기 위해 설정해준다.

- Redis는 여러 자료 구조를 가지고 있다. 
- 이런 여러 종류의 자료구조를 대응하기 위해 `RedisTemplate` 는 opsForValue, opsForSet, opsForZSet 등의 메서드를 제공한다. 
- 해당 메서드를 사용하면 각 자료구조에 대해서 쉽게 Serialize 및 Deserialize 할 수 있다.

- 각 메서드에 대한 설명은 아래와 같다.

| 메서드          | 설명                                                    |
| --------------- | ------------------------------------------------------- |
| **opsForValue** | Strings를 쉽게 Serialize / Deserialize 해주는 Interface |
| **opsForList**  | List를 쉽게 Serialize / Deserialize 해주는 Interface    |
| opsForSet       | Set를 쉽게 Serialize / Deserialize 해주는 Interface     |
| opsForZSet      | ZSet를 쉽게 Serialize / Deserialize 해주는 Interface    |
| **opsForHash**  | Hash를 쉽게 Serialize / Deserialize 해주는 Interface    |

### 7.6.1 Strings

- 대부분의 데이터는 문자열로 처리.

```
@Autowired
StringRedisTemplate redisTemplate;

@Test
public void testStrings() {
    final String key = "sabarada";

    final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

    stringStringValueOperations.set(key, "1"); // redis set 명령어
    final String result_1 = stringStringValueOperations.get(key); // redis get 명령어

    System.out.println("result_1 = " + result_1);

    stringStringValueOperations.increment(key); // redis incr 명령어
    final String result_2 = stringStringValueOperations.get(key);

    System.out.println("result_2 = " + result_2);
}
result_1 = 1
result_2 = 2
```

### 7.6.2 List

- 순서를 가진 데이터 구조 (Head --- tail)

  Pub-Sub (생산자-소비자) 패턴에서 많이 사용

```
@Autowired
StringRedisTemplate redisTemplate;

@Test
public void testList() {
    final String key = "sabarada";

    final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();

    stringStringListOperations.rightPush(key, "H");
    stringStringListOperations.rightPush(key, "e");
    stringStringListOperations.rightPush(key, "l");
    stringStringListOperations.rightPush(key, "l");
    stringStringListOperations.rightPush(key, "o");

    stringStringListOperations.rightPushAll(key, " ", "s", "a", "b", "a");

    final String character_1 = stringStringListOperations.index(key, 1);

    System.out.println("character_1 = " + character_1);

    final Long size = stringStringListOperations.size(key);

    System.out.println("size = " + size);

    final List<String> ResultRange = stringStringListOperations.range(key, 0, 9);

    System.out.println("ResultRange = " + Arrays.toString(ResultRange.toArray()));
}
character_1 = e
size = 10
ResultRange = [H, e, l, l, o,  , s, a, b, a]
```

### 7.6.3 Set

데이터 존재 여부 확인에서 많이 사용

```
@Test
public void testSet() {
    String key = "sabarada";
    SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();

    stringStringSetOperations.add(key, "H");
    stringStringSetOperations.add(key, "e");
    stringStringSetOperations.add(key, "l");
    stringStringSetOperations.add(key, "l");
    stringStringSetOperations.add(key, "o");

    Set<String> sabarada = stringStringSetOperations.members(key);

    System.out.println("members = " + Arrays.toString(sabarada.toArray()));

    Long size = stringStringSetOperations.size(key);

    System.out.println("size = " + size);

    Cursor<String> cursor = stringStringSetOperations.scan(key, ScanOptions.scanOptions().match("*").count(3).build());

    while(cursor.hasNext()) {
        System.out.println("cursor = " + cursor.next());
    }
}
members = [l, e, o, H]
size = 4
cursor = l
cursor = e
cursor = o
cursor = H
```

### 7.6.4 Sorted Set

정렬된 set 데이터 처리

랭킹 처리, 정렬에서 많이 사용

score : 요소의 가중치 ⇒ 요소의 정렬 결정 (default 오름차순)

```
@Test
public void testSortedSet() {
    String key = "sabarada";

    ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();

    stringStringZSetOperations.add(key, "H", 1);
    stringStringZSetOperations.add(key, "e", 5);
    stringStringZSetOperations.add(key, "l", 10);
    stringStringZSetOperations.add(key, "l", 15);
    stringStringZSetOperations.add(key, "o", 20);

    Set<String> range = stringStringZSetOperations.range(key, 0, 5);

    System.out.println("range = " + Arrays.toString(range.toArray()));

    Long size = stringStringZSetOperations.size(key);

    System.out.println("size = " + size);

    Set<String> scoreRange = stringStringZSetOperations.rangeByScore(key, 0, 13);

    System.out.println("scoreRange = " + Arrays.toString(scoreRange.toArray()));
}
range = [H, e, l, o]
size = 4
scoreRange = [H, e
```

### 7.6.5 Hash

키-값 쌍으로 이뤄진 데이터 (자바의 map 구조)

Key - Value 밑에 sub Key - Value 형식의 데이터 구조

```
@Test
public void testHash() {
    String key = "sabarada";

    HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();

    stringObjectObjectHashOperations.put(key, "Hello", "sabarada");
    stringObjectObjectHashOperations.put(key, "Hello2", "sabarada2");
    stringObjectObjectHashOperations.put(key, "Hello3", "sabarada3");

    Object hello = stringObjectObjectHashOperations.get(key, "Hello");

    System.out.println("hello = " + hello);

    Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);s

    System.out.println("entries = " + entries.get("Hello2"));

    Long size = stringObjectObjectHashOperations.size(key);

    System.out.println("size = " + size);
}
hello = sabarada
entries = sabarada2
size = 3
```



# 8. Demo Application

## 8.1  개요

- 네이티브 아키텍처의 기술 스택을 이용한 Demo Application을 구현하려고 한다. Demo Application은 카탈로그 서비스, 사용자 서비스, 주문 서비스라는 3개의 마이크로서비스로 구성되어 있는 간단한 E-commerce 애플리케이션이다.

- 구현의 간단함을 위해 각 마이크로서비스는 RESTful API를 제공하는 비즈니스 로직만 구현하였다. 제공하는 비즈니스 로직은 다음과 같다.

  ◾  카탈로그 서비스: 상품의 목록을 확인, 상품의 재고수량 업데이트

  ◾  사용자 서비스: 사용자 등록, 상품의 정보를 확인 후 상품 주문

  ◾  주문 서비스: 상품의 주문과 주문된 상품 확인


- Demo Application은 아래의 Git을 통해 다운로드 받을수 있다.

  ```shell
  git clone https://github.com/kirobo77/cachePattern.git
  ```

  

## 8.2  사전 준비 작업

### 8.2.1  기술 스택

- Demo Application은 Spirng Cloud 기반에서 동작하는 마이크로서비스이며 DDD 관점에 따라 3개의 마이크로 서비스로 구성되어 있다.

- CloudNative에서는 컨테이너가 핵심요소이지만 해당 실습에서는 Spring Boot를 활용한 어플리케이션 개발에 중점을 두며 해당 영역은 별도로 다루지 않는다.
  - 컨테이너 환경에서는 Spring Cloud의 기술요소를 대체할 수 있는 여러 훌륭한 솔루션이 존재한다.

| 기술 스택              | 참고 |
| ---------------------- | ---- |
| Spring MVC             |      |
| Spring Cloud OpenFeign |      |
| Spring Data JDBC       |      |
| Spring Data Redis      |      |

 

## 8.2.2  마이크로서비스

- 마이크로서비스는 카탈로그 서비스, 사용자 서비스, 주문 서비스 3개로 구성되어 있으며, Java 17 언어를 이용한 Spring Boot(v2.7.2) + Spring Cloud(2021.0.3) API를 이용하여 개발되었다.

| 서비스 명       | URI                                                       | HTTP  Method | 설명           |
| --------------- | --------------------------------------------------------- | ------------ | -------------- |
| User service    | http://localhost:50001/user-ms/users                      | POST         | 사용자 추가    |
|                 | http://localhost:50001/user-ms/users/**{userId}**         | GET          | 사용자 확인    |
| Catalog Service | http://localhost:50003/catalog-ms/catalogs                | GET          | 카테고리 목록  |
| Order service   | http://localhost:50002/order-ms/users/**{userId}**/orders | GET          | 상품 주문 확인 |
|                 | http://localhost:50002/order-ms/users/**{userId}**/orders | POST         | 상품 주문      |

 

## 8.3  시스템 구성도

 

![](D:/cloudnative/assets/demo-archi.png)

 

## 8.4 미들웨어 구동

### 8.4.3 Redis 실행

```
cd D:\cloudnative\infra\Redis-x64-3.2.100
.\redis-server.exe .\redis.windows.conf
```

## 8.5  상품 주문 프로세스

### 8.5.1 사용자 등록

> http://localhost/users-ms/users

- 먼저, 사용자 정보를 등록하도록 하자. 사용자 서비스에 다음과 같은 정보를 HTTP Post Method로 전달하도록 하자. Kubernetes의 서비스를 NodePort로 설정하였고 각 Node에 설치된 컨테이너의 포트는 다를 수 있기 때문에 직접 확인 후에 접속하도록 하자.

```
{
“name”: “test”, “email”: “test@test.com”, “pwd”: “test1234”
}
```

 ![image-20220823095439646](D:/cloudnative/assets/image-20220823095439646.png)



### 8.5.2 사용자 조회

> http://localhost/users-ms/users/a3af7fbc-b06c-4edb-95e9-b0efa0abc94b/

- 사용자 정보가 성공적으로 등록 되었다면, Response Body에서 “userId”를 확인할 수 있다. 반환된 “userId” 값을 이용하여 등록된 사용자의 정보를 확인해 보도록 하자. 

- 아직 사용자가 주문한 상품이 없기 때문에, “orders” 항목은 비어 있는 것을 확인할 수 있다.

![image-20220823095520646](D:/cloudnative/assets/image-20220823095520646.png)



### 8.4.3 상품 목록 조회

> http://localhost/catalogs-ms/catalogs

- 다음으로 카탈로그 서비스로부터 등록된 상품의 목록을 가져오도록 하자.

 

### 8.4.4 상품 주문

> http://localhost/orders-ms/users/a3af7fbc-b06c-4edb-95e9- b0efa0abc94b/orders

- 상품 조회를 통해 “productId(상품 코드)”, “unitPrice(상품 단가)”와 “qty(수량)”을 확인 한 다음, 주문하고자 하는 상품의 정보를 전달하여 상품 주문 요청을 하도록 하자.

```
{
“productId”: “CATALOG-0001”,
“qty”: 10,
“unitPrice”: 1500
}
```

 

### 8.4.5 상품 주문 조회

> http://localhost/orders-ms/users/a3af7fbc-b06c-4edb-95e9- b0efa0abc94b/orders

- 조금 전에 주문했던 내용은 상품 주문 조회 서비스를 통해 확인할 수 있다. 각 사용자 별로 주문한 내용을 확인해 볼 수 있다.



## 8.5  MicroService간 통신

- Demo Application은 상품 주문이 요청되었을 때, 주문 서비스로 사용자가 요청한 주문 정보를 전달하게 된다. 
- 그리고 사용자 서비스에서 사용자의 정보 확인 시, 해당 사용자가 주문한 상품의 목록을 확인할 수 있는데 여기에서 사용자 마이크로서비스로부터 주문 마이크로서비스로 주문 상품 목록을 요청하게 된다. 
- 이 작업을 위해, Demo Application에서는 Spring Cloud의 FeignClient를 이용하여 사용자 서비스에서 주문 서비스로 정보를 요청하고 있다. 



## 8.7 Cache Service를 통한 데이터 조회

- 카탈로그 서비스는 사용자가 상품을 주문할때 수량이 업데이트 된다. 카탈로그 서비스는 일반적인  E-commerce 애플리케이션에서 가장 많이 호출되는 서비스이다.

- 모노리틱 환경에서 카탈로그 서비스에 대량으로 조회 시 애플리케이션 전체에 영향을 끼치는 SPOF(*single point* of *failure*)가 된다.

- MSA 환경에는 카탈로그 서비스에 대량으로 조회가 발생하더라eh 해당 서비스에 대한 장애 발생되고 애플리케이션 전체로 전파되는것이 차단된다.

- 이 부분이 MSA가 가지는 주요 장점 중에 하나이다.

- 또한 카탈로그 서비스에 부하발생 시 Cahce 등을 활용하여 해당 서비스의 성능을 향상시킬 수 있는 환경을 제공한다.

* 특히 컨테이너환경에서는 Replica 를 활용하여 서비스에 대한 수평확장을 통한 성능향상을 제공한다.



## 5.9 테스트

| 서비스 명       | URI                                                          | 설명                        |
| --------------- | ------------------------------------------------------------ | --------------------------- |
| User service    | http://localhost:50001/swagger-ui/index.html                 | API 테스트                  |
|                 | http://localhost:50001/h2-console/                           | H2 DB 콘솔                  |
|                 | curl -X 'POST' 'http://localhost:50001/actuator/refresh' -H 'accept: */*' | 프로퍼티 갱신               |
|                 | http://localhost:50001/actuator/circuitbreakerevents         | circuit breaker 이벤트 조회 |
| Catalog Service | http://localhost:50003/swagger-ui/index.html                 | API 테스트                  |
|                 | http://localhost:50003/h2-console/                           | H2 DB 콘솔                  |
|                 | curl -X 'POST' 'http://localhost:50003/actuator/refresh' -H 'accept: */*' | 프로퍼티 갱신               |
| Order service   | http://localhost:50002/swagger-ui/index.html                 | API 테스트                  |
|                 | http://localhost:50002/h2-console/                           | H2 DB 콘솔                  |
|                 | curl -X 'POST' 'http://localhost:50002/actuator/refresh' -H 'accept: */*' | 프로퍼티 갱신               |
| Redis           | cd D:\cloudnative\infra\Redis-x64-3.2.100 .\redis-server.exe .\redis.windows.conf .\redis-cli.exe keys * del key "catalogs::SimpleKey []" | Redis 키 조회/삭제          |











# 워크샵: 웹 앱을 PWA로 전환

![심벌 마크](https://pwa-workshop.js.org/assets/img/logo-192.75a78546.png)

최신 웹 트렌드에서 프로그레시브 웹 앱(PWA)에 대해 들어본 적이 있을 것입니다. 그것은 무엇에 관한 것이며 어떻게 고전적인 웹 응용 프로그램을 개선합니까? 이 워크샵을 통해 약간의 연습 💪을 통해 PWA를 더 잘 이해할 수 있습니다.

우리는 함께 고전적인 웹 애플리케이션을 PWA로 변환할 것입니다. 이를 통해 사용자는 기본 오프라인 모드를 사용할 수 있으며 홈 화면의 바로 가기를 사용하여 스마트폰에 앱을 설치할 수 있습니다.

## [#](https://pwa-workshop.js.org/#requirements)요구 사항

- HTML, CSS 및 JavaScript에 대한 기본 지식. JS의 Promise와 비동기성을 이해하는 것은 플러스 요인이 될 수 있습니다.
- [Visual Studio Code](https://code.visualstudio.com/) 와 같은 **코드 편집기** 가 있는 컴퓨터[(새 창을 엽니다)](https://code.visualstudio.com/)
- **Google 크롬** ( *PWA는 대부분의 브라우저에서 지원되지만 워크샵 구성을 용이하게 하기 위해 개발하는 동안 모두 동일한 브라우저를 사용합니다* )
- **로컬 웹 서버** ( 없는 경우 [npmjs.com/http-server (새 창을 엽니다)](http://npmjs.com/http-server)와 함께 `cd app && http-server`)

## [#](https://pwa-workshop.js.org/#preparation)준비

- [예제 웹 앱 복제 또는 다운로드 (새 창을 엽니다)](https://github.com/sylvainpolletvillard/pwa-workshop.git)[앱 폴더](https://github.com/sylvainpolletvillard/pwa-workshop/tree/master/app) 에 있는[ (새 창을 엽니다)](https://github.com/sylvainpolletvillard/pwa-workshop/tree/master/app)이 리포지토리의.
- `app`코드 편집기에서 폴더를 엽니다 .
- HTTPS로 폴더 를 제공하도록 로컬 서버를 구성합니다 `app`(아래 지침 참조).
- `app/index.html`Google 크롬에서 페이지를 로드합니다 . 참석자 목록이 표시되어야 합니다.

## [#](https://pwa-workshop.js.org/#local-development-with-ssl)SSL을 사용한 로컬 개발

PWA는 HTTPS를 사용해야 합니다. 대부분의 웹 호스트가 기본적으로 HTTPS를 제공하기 때문에 배포된 PWA에는 큰 문제가 아닙니다. 그러나 지역발전의 경우는 그렇지 않다. 실제로 인증서를 수동으로 생성하고 인증서 저장소에 설치해야 합니다. [다행히 mkcert](https://mkcert.dev/) 라는 멋진 CLI 도구가 있습니다.[ (새 창을 엽니다)](https://mkcert.dev/)이 단계를 단순화합니다.

다음 단계에 따라 로컬 HTTPS 서버를 설정해 보겠습니다.

- [mkcert](https://github.com/FiloSottile/mkcert#installation) 설치[ (새 창을 엽니다)](https://github.com/FiloSottile/mkcert#installation)GitHub 페이지에 표시된 대로
- 실행 `mkcert -install`하여 로컬 CA(인증 기관) 설치

```text
Created a new local CA at "/Users/****/Library/Application Support/mkcert" 💥
The local CA is now installed in the system trust store! ⚡️
The local CA is now installed in the Firefox trust store (requires browser restart)! 🦊
```

- 웹 사이트 루트로 cd
- 서버에 대한 인증서를 생성한 다음 명령을 실행합니다.`mkcert localhost 127.0.0.1 ::1`

```text
Using the local CA at "/Users/****yassinebenabbas****/Library/Application Support/mkcert" ✨

Created a new certificate valid for the following names 📜
 - "localhost"
 - "127.0.0.1"
 - "::1"

The certificate is at "./localhost+2.pem" and the key at "./localhost+2-key.pem" ✅
```

- 우리는 두 개의 pem 파일을 얻을 것입니다. 이는 SSL 지원 개발 서버에서 사용됩니다.

![인증서](https://pwa-workshop.js.org/assets/img/certs.7e43f7a2.png)

- `http-server`아직 완료되지 않은 경우 npm 패키지를 설치 하십시오.`npm i -g http-server`
- SSL 모드에서 서버 실행:`http-server -S -o -C "localhost+2.pem" -K "localhost+2-key.pem"`

![인증서](https://pwa-workshop.js.org/assets/img/certok.41829eb4.png)

이 부분에서는 서비스 워커를 설치하는 방법과 두 가지 서비스 워커 수명 주기 이벤트인 **install** 및 **activate** 를 관리하는 방법을 살펴보았습니다 . 이제 이 서비스 워커로 유용한 작업을 수행하는 방법을 살펴보겠습니다.

## [#](https://pwa-workshop.js.org/#steps-of-the-workshop)워크샵의 단계

1. 웹 애플리케이션 매니페스트 추가
2. 서비스 워커 설치 및 활성화
3. 기본 오프라인 모드를 위한 정적 자산 사전 캐싱
4. API GET 요청에 대한 캐시/업데이트/새로고침 전략
5. 백그라운드 동기화 및 푸시 알림의 예
6. 설치 버튼 표시



# 1단계: 웹 앱 매니페스트 추가

매니페스트 파일을 만드는 것부터 시작하겠습니다. 이 파일은 [MDN 에서 정의합니다. (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/Manifest)다음과 같이:

> 웹 앱 매니페스트는 JSON 텍스트 파일에서 애플리케이션에 대한 정보(예: 이름, 작성자, 아이콘 및 설명)를 제공합니다.

매니페스트의 존재는 다른 이점도 제공합니다. 다음은 몇 가지입니다.

- 지원되는 브라우저는 PWA를 설치하고 홈 화면에 바로 가기를 추가할 수 있습니다.
- [PWA는 Windows 스토어](https://docs.microsoft.com/en-us/microsoft-edge/progressive-web-apps) 와 같은 일부 앱 스토어에서 참조할 수 있습니다.[ (새 창을 엽니다)](https://docs.microsoft.com/en-us/microsoft-edge/progressive-web-apps).
- PWA는 브라우저 UI 없이 전체 화면이나 독립 실행형 창으로 표시될 수 있습니다.

## [#](https://pwa-workshop.js.org/1-manifest/#manifest-fields)매니페스트 필드

매니페스트는 여러 속성이 포함된 JSON 파일입니다. PWA의 발견과 경험을 최적화하기 위해 대부분을 채우는 것이 좋습니다. 다음은 [현재 지정된 모든 필드입니다. (새 창을 엽니다)](https://developer.mozilla.org/en/docs/Web/Manifest):

- `name`- 앱 아이콘 아래 스플래시 화면에 표시

- `short_name`- 바탕 화면 또는 홈 화면의 바로 가기 아래에 표시

- `description`- 응용 프로그램에 대한 일반적인 설명

- `start_url`- 바로가기에서 애플리케이션을 열 때 가장 먼저 로드되는 URL

- `scope`- PWA 경험의 일부인 사이트 페이지. `"."`값에는 매니페스트 디렉터리와 하위 디렉터리의 모든 항목이 포함됩니다 . 따라서 후자를 사이트의 루트에 배치하는 것이 더 적절합니다.

- `background_color`- 시작 화면의 배경색

- `theme_color`- 예를 들어 표시되는 경우 상태 표시줄에 사용되는 응용 프로그램의 일반 테마 색상

- ```
  display
  ```

  \- 표시 모드를 지정합니다. 다음은 대체 순서에 따라 정렬된 사용 가능한 다양한 모드입니다.

  - `fullscreen`: 사용 가능한 모든 표시 영역이 사용되며 브라우저 UI가 표시되지 않습니다.
  - `standalone`: 독립 실행형 애플리케이션처럼 보이고 느껴집니다. 이는 응용 프로그램에 자체 창, 실행 프로그램에 자체 아이콘 등이 있음을 의미합니다. 이 모드에서 사용자 에이전트는 탐색 제어를 위한 UI 요소를 제외하지만 상태 표시줄과 같은 다른 UI 요소를 포함할 수 있습니다.
  - `minimal-ui`: 응용 프로그램은 독립 실행형 응용 프로그램처럼 보이고 느껴지지만 탐색을 제어하기 위한 최소한의 UI 요소 집합이 있습니다. 이러한 요소는 브라우저와 시스템에 따라 다릅니다.
  - `browser`(기본값): 브라우저 및 플랫폼에 따라 애플리케이션이 기존 브라우저 탭 또는 새 창에서 열립니다.

- `orientation`- 모바일 장치의 기본 방향을 정의합니다. `any`, `natural`, `landscape`, `portrait`...

- `dir`- 이름 및 설명 필드에 대한 기본 텍스트 방향을 지정합니다. `ltr`, `rtl`또는 `auto`.

- `lang`- 응용 프로그램의 기본 언어.

- `related_applications`- 기본 플랫폼에서 설치할 수 있고 유사/동등한 기능을 제공하는 PWA의 대안으로 의도된 기본 응용 프로그램의 배열입니다. 기본 애플리케이션이 없으면 무시하십시오.

- `icons`- 바로 가기 및 시작 화면에 사용되는 다양한 해상도의 응용 프로그램 아이콘 목록. 제공되는 권장 크기는 최소 192x192px 및 512x512px입니다. 장치는 케이스에 따라 가장 좋은 아이콘을 자동으로 선택합니다. 최대 크기에 맞는 SVG 벡터 버전의 아이콘을 제공하는 것도 흥미롭습니다.

## [#](https://pwa-workshop.js.org/1-manifest/#using-a-manifest-generator)매니페스트 생성기 사용

애플리케이션 매니페스트는 텍스트 파일이므로 손으로 작성하거나 작성을 간소화하는 도구를 사용할 수 있습니다. 여러 도구를 온라인에서 사용할 수 있습니다.

- [https://app-manifest.firebaseapp.com/(새 창을 엽니다)](https://app-manifest.firebaseapp.com/)
- [웹 앱 매니페스트 생성기 (새 창을 엽니다)](https://tomitm.github.io/appmanifest/).

이러한 도구 중 하나를 사용하여 앱에 대한 매니페스트 파일을 생성합니다. 매니페스트에는 최소한 `name`, `short_name`, `lang`, `start_url`, `display: 'standalone'`, `theme_color`및 `icons`192x192px 및 512x512px 크기의 PNG 아이콘이 두 개 이상 있고 SVG 버전이 하나 이상 있어야 합니다.

다음은 이러한 아이콘을 만드는 온라인 도구의 몇 가지 예입니다.

- [방법 추첨(새 창을 엽니다)](https://editor.method.ac/)
- [SVG 편집기: 벡터 페인트(새 창을 엽니다)](http://vectorpaint.yaks.co.nz/)
- [구글 드로잉(새 창을 엽니다)](https://docs.google.com/drawings/)

팁

`app/icons`시간을 절약하기 위해 폴더 에서 필요한 치수가 있는 예제 아이콘을 찾을 수 있습니다 .

그런 다음 매니페스트는 `manifest.json`앱 루트 폴더에서 호출되는 텍스트 파일에 저장할 수 있습니다.

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px; color: rgb(44, 62, 80); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, Cantarell, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-json extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-json" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token property" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

## [#](https://pwa-workshop.js.org/1-manifest/#adding-the-manifest-in-the-application)애플리케이션에 매니페스트 추가

마지막 단계는 섹션 의 `link`태그를 사용하여 애플리케이션의 HTML 페이지에서 매니페스트를 참조하는 것입니다 .`<head>``<link rel="manifest" href="manifest.json">`

다른 메타데이터도 일부 브라우저에서 사용되며 유용할 수 있습니다. 앞으로 이러한 메타데이터는 웹 앱 매니페스트의 새 필드를 위해 사라지는 경향이 있을 것입니다. 다음은 예입니다.

```html
<link rel="manifest" href="manifest.json" />
<meta name="mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="application-name" content="PWA Workshop" />
<meta name="apple-mobile-web-app-title" content="PWA Workshop" />
<meta name="msapplication-starturl" content="/index.html" />
<meta
  name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no"
/>
```

## [#](https://pwa-workshop.js.org/1-manifest/#testing)테스트

Chrome 개발자 도구 의 *애플리케이션* 탭 에서 매니페스트가 올바르게 검색되었는지 확인할 수 있습니다 . 매니페스트 속성 목록이 표시되어야 합니다.

![매니페스트 개발 도구](https://pwa-workshop.js.org/assets/img/manifest_dev_tools.a1a93822.png)

## [#](https://pwa-workshop.js.org/1-manifest/#pwa-compatibility-library)PWA 호환성 라이브러리

일부 브라우저는 아직 일부 PWA 기능을 지원하지 않습니다. 예를 들어, 모바일 Safari 12에는 시작 화면 지원이 없습니다. 라이브러리 [pwacompat (새 창을 엽니다)](https://github.com/GoogleChromeLabs/pwacompat)Google Chrome Labs에서 이러한 문제 중 일부를 수정합니다. 더 나은 호환성을 위해 PWA에 이 스크립트를 추가하는 것이 좋습니다.



# 2단계: 서비스 워커 설치

서비스 워커에 대해 알아보기 전에 먼저 웹 워커가 무엇인지 이해합시다.

## [#](https://pwa-workshop.js.org/2-service-worker/#introduction-to-web-workers)웹 작업자 소개

아시다시피 JavaScript는 *단일 스레드* 입니다. 즉, JavaScript 프로그램은 일반적으로 단일 스레드에서 실행됩니다. **웹 작업자** 는 메인 스레드와 분리된 다른 스레드에서 애플리케이션의 일부를 실행할 수 있습니다. 웹 작업자의 코드는 일반적으로 나머지 애플리케이션 코드와 분리된 별도의 JavaScript 파일에 있습니다. `postMessage`웹 작업자가 생성되면 함수와 `message`이벤트 를 사용하여 기본 JavaScript 프로그램과 데이터를 통신할 수 있습니다 .

웹 작업자는 자체 스레드를 가지고 있기 때문에 메인 스레드를 차단하고 일반적으로 웹 페이지와 같은 애플리케이션 반응성을 저하시키지 않고 프로세스가 많은 작업을 수행할 수 있습니다.

다음은 웹 작업자를 만들고 통신하는 웹 페이지의 최소 예입니다.

```html
<html>
  <head>
    <meta charset="UTF-8" />
    <script src="main.js"></script>
    <title>Web Worker example</title>
  </head>
  <body>
    <button onclick="sendMessageToWorker()">Hello !</button>
    <button onclick="askWorkerRecurringTask()">Recurring task</button>
    <output></output>
  </body>
</html>
```

다음은 작업자를 생성하고 작업자와 메시지를 주고받는 기본 JavaScript 코드입니다.

```javascript
// create a worker whose code is defined in the file passed as parameter
const worker = new Worker("worker.js");

function sendMessageToWorker() {
  worker.postMessage("hello");
}

function askWorkerRecurringTask() {
  worker.postMessage("recurring");
}

// This event is fired when the worker posts a message
// The value of the message is in messageEvent.data
worker.addEventListener("message", function(messageEvent) {
  // Log the received message in the output element
  const log = document.createElement("p");
  log.textContent = messageEvent.data;
  document.querySelector("output").prepend(log);
});
```

마지막으로 10초 동안 단일 메시지 또는 현재 날짜를 다시 게시하여 기본 JS에서 받은 메시지에 반응하는 웹 작업자의 코드입니다.

```javascript
// This event is fired when the worker recieves a message from the main JavaScript
// The value of the message is in messageEvent.data
self.addEventListener("message", function(messageEvent) {
  if (messageEvent.data === "hello") {
    // Post a message back to the main JS
    self.postMessage("Hello to you too !");
  }

  if (messageEvent.data === "recurring") {
    for (let i = 0; i < 10; i++) {
      setTimeout(() => self.postMessage(new Date()), i * 1000);
    }
  }
});
```

이것으로 웹 작업자에 대한 간단한 소개를 마칩니다. 우리는 다음에 서비스 워커와 놀 수 있습니다.

## [#](https://pwa-workshop.js.org/2-service-worker/#service-workers)서비스 워커

[서비스](https://developers.google.com/web/fundamentals/primers/service-workers/) 워커[ (새 창을 엽니다)](https://developers.google.com/web/fundamentals/primers/service-workers/)브라우저와 네트워크 사이에서 프록시 역할을 하는 웹 작업자 유형입니다. 그것은 많은 잠재력을 가진 상당히 큰 API를 가지고 있습니다. PWA의 맥락에서 서비스 워커는 주로 캐싱 전략을 정의하여 불안정한 연결을 더 잘 관리하거나 애플리케이션에 대한 완전한 오프라인 모드를 얻을 수 있도록 합니다.

서비스 워커에 대해 알아야 할 사항:

- 그들은 JavaScript로 코딩된 작업자입니다. 애플리케이션과 분리된 자체 스레드에서 실행되며 DOM 또는 전역 변수에 액세스할 수 없습니다. 하지만 앱과 작업자는 `postMessage`API를 통해 통신할 수 있습니다.
- 이들은 프로그래밍 가능한 네트워크 프록시처럼 작동할 수 있습니다. 브라우저에서 네트워크 요청을 가로채고 응답을 사용자 정의할 수 있습니다.
- 수명 주기는 연결된 웹 응용 프로그램과 무관합니다. 사용하지 않을 때 자동으로 중지되고 필요할 때 다시 시작됩니다.
- 연결된 웹 응용 프로그램이 실행되고 있지 않을 때도 작동할 수 있으므로 푸시 알림 보내기와 같은 몇 가지 새로운 기능을 허용합니다.
- 서비스 워커 내에서 여러 API를 사용하여 데이터를 로컬로 지속할 수 있습니다(예: [**Cache API ).** (새 창을 엽니다)](https://developer.mozilla.org/en/docs/Web/API/Cache)및 [**IndexedDB API** (새 창을 엽니다)](https://developer.mozilla.org/en/docs/Web/API/API_IndexedDB).
- 연결된 API의 대부분은 [Promise 를 사용합니다. (새 창을 엽니다)](https://developer.mozilla.org/en/docs/Web/JavaScript/Reference/Global_Objects/Promise).

폴더 에서 `app`새 `sw.js`파일을 만듭니다. 여기에는 서비스 워커의 코드가 포함됩니다.

## [#](https://pwa-workshop.js.org/2-service-worker/#registering-the-service-worker)서비스 워커 등록

서비스 워커를 사용하기 전에 애플리케이션에 등록해야 합니다. 서비스 워커는 일반적으로 페이지가 로드될 때 등록됩니다. 파일 에서 `scripts.js`다음 코드로 문서가 로드될 때 호출되는 함수를 완료합니다.

```js
if ("serviceWorker" in navigator) {
  navigator.serviceWorker
    .register("/sw.js")
    .then(serviceWorker => {
      console.log("Service Worker registered: ", serviceWorker);
    })
    .catch(error => {
      console.error("Error registering the Service Worker: ", error);
    });
}
```

페이지를 다시 로드하면 페이지가 로드되면 콘솔에 다음 로그가 표시되어야 합니다.

```text
Service Worker registered: [ServiceWorkerRegistration]
```

이는 서비스 워커가 등록되었음을 의미합니다. 이는 Chrome 개발자 도구 의 **애플리케이션 탭에서** **서비스 작업자** 하위 섹션을 보면 확인할 수 있습니다.

![서비스 워커 설치됨](https://pwa-workshop.js.org/assets/img/service-worker-setup.84b2c4b3.png)

다음 섹션에서 서비스 워커를 등록한 후 어떤 일이 발생하는지 살펴보겠습니다.

## [#](https://pwa-workshop.js.org/2-service-worker/#service-worker-life-cycle)서비스 워커 수명 주기

서비스 워커를 등록하면 수명 주기가 시작됩니다. 다음 다이어그램은 서비스 워커 라이프 사이클의 여러 단계를 보여줍니다( [소스 (새 창을 엽니다)](https://developers.google.com/web/fundamentals/primers/service-workers/)).

![서비스 워커 수명 주기](https://pwa-workshop.js.org/assets/img/sw-lifecycle.1d49eede.png)

첫 번째 단계는 설치 및 활성화입니다. *sw.js* 파일 에 다음 코드를 추가하여 확인해보자 .

```js
self.addEventListener("install", event => {
  console.log("Service Worker installing.");
});

self.addEventListener("activate", event => {
  console.log("Service Worker activating.");
});
```

페이지를 새로고침하고 로그를 확인합니다. 흥미롭게도 설치 로그만 표시됩니다.

```text
Service Worker registered [object ServiceWorkerRegistration]
Service Worker installing.
```

Dev Tools의 Service Worker 섹션에서 무슨 일이 일어나고 있는지 확인해 봅시다. 다음과 같이 표시되어야 합니다.

![활성화 대기 중인 서비스 워커](https://pwa-workshop.js.org/assets/img/sw-waiting.0b9f4663.png)

페이지를 새로 고치면 브라우저는 새 코드로 서비스 워커를 설치하고 활성화하려고 시도합니다. 후자는 2단계 시작 시 등록된 활성 서비스 워커와 다르기 때문에 새 서비스 워커의 활성화가 일시 중단됩니다. 이 경우 대기 모드가 되며 이전 서비스 워커가 클라이언트를 제어하지 않는 경우에만 설치됩니다. 이 경우 두 가지 솔루션이 있습니다. 첫 번째 서비스 워커가 제어하는 모든 탭을 닫거나 개발 도구에서 **skipWaiting** 링크를 클릭합니다.

**건너뛰기 대기** 링크 를 클릭 합니다. 이전 서비스 워커가 사라지고 기다리고 있던 사람이 그 자리를 차지했음을 주목하십시오. 활성화 로그는 콘솔에도 표시됩니다.

```text
Service Worker activating.
```

서비스 워커를 수정하지 않고 페이지를 새로 고치면 더 이상 설치 및 활성화 단계를 거치지 않습니다.

이 동작은 프로덕션에서 버전 업그레이드를 관리하는 데 필요합니다. 실제로 이 업데이트 프로세스를 관리하는 코드를 서비스 워커에 작성하고 `skipWaiting`모든 것이 올바르게 설정되면 프로그래밍 방식으로 호출합니다.

**개발하는 동안 업데이트 시 다시 로드** 확인란을 선택하여 작업을 단순하게 유지합니다 . 이 옵션은 미래의 새로운 서비스 워커를 즉시 활성화합니다. **매번 skipWaiting** 을 자동으로 클릭하는 것과 같습니다 .

메모

항상 최신 버전을 유지하려면 서비스 워커의 코드에서 작업할 때 **다시 로드 시 업데이트 옵션을** 활성화하십시오 .

![새로고침 시 업데이트](https://pwa-workshop.js.org/assets/img/devtools-update-on-reload.d1d00b91.png)

**그러나 이 옵션은 페이지를 표시 하기 전에** 서비스 워커를 설치하고 활성화 하므로 콘솔에서 이러한 이벤트와 관련된 로그를 볼 수 없습니다.



# 3단계: 기본 오프라인 모드를 위한 정적 자산 사전 캐싱

이전 단계에서 서비스 워커 라이프 사이클의 두 가지 방법인 `install`및 `activate`. 이 부분에서는 정적 파일을 캐싱하여 PWA 탐색을 계속할 것입니다.

## [#](https://pwa-workshop.js.org/3-precaching/#preamble-promises-and-async-await)서문: 약속과 async/await

서비스 작업자 API는 약속에 크게 의존합니다. 어떻게 작동하는지 간단히 살펴보겠습니다.

팁

[노드 또는 https://repl.it](https://repl.it/languages/nodejs) 과 같은 온라인 편집기를 사용하여 이 섹션의 코드를 시도할 수 있습니다.[(새 창을 엽니다)](https://repl.it/languages/nodejs)

Promise는 일련의 비동기 함수 호출을 보다 편리하게 처리할 수 있는 인터페이스를 제공합니다. 예를 들어 인수로 전달된 콜백에 비해 코드 가독성이 향상됩니다. ES2015 JavaScript 사양은 다음 생성자를 사용하여 약속을 만들 수 있습니다.

```javascript
const promise = new Promise((resolve, reject) => {
  // async function execution
  // resolve is called on success
  // reject is called on failure
});
```

다음은 1초 지연 후 난수를 생성하는 promise의 보다 구체적인 예입니다. 생성된 숫자가 짝수일 때 성공하고 홀수일 때 실패합니다.

```javascript
function generateRandomNumber() {
  return new Promise(function(resolve, reject) {
    setTimeout(function() {
      const nb = Math.floor(Math.random() * 10); // random number between 0 and 10
      if (nb % 2 == 0) {
        resolve(nb);
      } else {
        reject({ message: "odd number", number: nb });
      }
    }, 1000);
  });
}
```

`Promise`그러나 대부분의 서비스 작업자 API는 이미 약속을 반환하는 함수를 제공하기 때문에 이 생성자를 사용하지 않습니다 .

객체 가 있으면 `Promise`관련 코드가 비동기식으로 실행되기 시작합니다. 프라미스가 성공(호출 )하거나 실패( 호출)할 때 `then()`및 함수를 사용하여 `reject()`함수를 실행할 수 있습니다 .`resolve``reject`

다음 예제는 `generateRandomNumber()`함수에서 반환된 약속을 처리하는 방법을 보여줍니다. [온라인으로 실행(새 창을 엽니다)](https://repl.it/@yostane/promise01)

```javascript
const promise = generateRandomNumber(); // create a promise that generated a random number asynchronously
promise
  .then(function(number) {
    // this function is called when the promise succeds
    console.log(number);
  })
  .catch(function(error) {
    // this function is called when the promise fails
    console.error(error);
  });
console.log("Promise example"); // this message is shows first because the promise is async
```

`then`및 `catch`핸들러를 함수로 추출하여 약속 호출을 축약할 수 있습니다 . [온라인으로 실행(새 창을 엽니다)](https://repl.it/@yostane/promise02)

```javascript
function handleSuccess(number) {
  console.log(number);
}
function handleFailure(message) {
  console.error(message);
}
generateRandomNumber()
  .then(handleSuccess)
  .catch(handleFailure);
console.log("Promise example"); // this message is shows first because the promise is async
```

Promise의 가장 큰 특징은 쉽게 연결될 수 있다는 것입니다. 다음 예제에서는 첫 번째 약속이 성공한 경우에만 새 난수를 비동기식으로 생성합니다. [온라인으로 실행(새 창을 엽니다)](https://repl.it/@yostane/promise03)

```javascript
function handleSuccess(number) {
  console.log(number);
}
function handleFailure(message) {
  console.error(message);
}
generateRandomNumber()
  .then(handleSuccess)
  .then(generateRandomNumber)
  .then(handleSuccess) // chain a second promise and handle is result
  .catch(handleFailure); // if any of the previous calls fails, catch is called
console.log("Promise example"); // this message is shows first because the promise is async
```

ES2018 사양 이후로 더 *동기식* 스타일로 promise를 처리하는 데 사용할 수 있는 새로운 연산자가 있습니다. 호출과 콜백 을 연결하는 대신 `then`약속 결과를 기다리는 동안 함수 실행을 일시 중지한 다음 결과를 변수에 저장하고 함수 실행을 다시 시작할 수 있습니다. 이를 결과 대기라고 하며 `async/await`키워드를 사용합니다. `try/catch`이 메서드 를 사용하면 동기 예외를 catch하는 데 사용되는 것처럼 catch 메서드가 블록으로 대체됩니다 .

다음 코드 스니펫은 를 사용하도록 마지막 예제를 변환합니다 `async/await`. [온라인으로 실행(새 창을 엽니다)](https://repl.it/@yostane/promise04)

```javascript
// If we want to use await, we must be place the code in async function
// More reading https://github.com/tc39/proposal-top-level-await, https://gist.github.com/Rich-Harris/0b6f317657f5167663b493c722647221
async function mainAsync() {
  try {
    // create the promise asynchronously without blocking the thread
    // and wait for its result (the parameter passed to resolve)
    const nb1 = await generateRandomNumberAsync();
    console.log(nb1); // nb1 is not the promise but rather its result in case of success
    const nb2 = await generateRandomNumberAsync();
    console.log(nb2);
  } catch (error) {
    // this catch block is executed if any promise above fails
    console.error(error); // the error variable is the value passed to reject
  }
}
mainAsync(); // call the function that runs async code
console.log("Promise example with async / await");
```

이것으로 promise 및 async/await에 대한 개요를 마칩니다. 이 지식을 습득하면 서비스 워커의 캐싱 API를 보다 조용하게 사용할 수 있습니다.

*여전히 약속이 불편하다면 다음과 같은 몇 가지 연습이 있습니다. [세트 1 (새 창을 엽니다)](https://github.com/asakusuma/promise-workshop), [세트 2 (새 창을 엽니다)](https://repl.it/@AdamCahan/Promise-practice-exercises), [세트 3(새 창을 엽니다)](https://developers.google.com/web/ilt/pwa/lab-promises)*

## [#](https://pwa-workshop.js.org/3-precaching/#exploring-caching-apis)캐싱 API 탐색

[서비스 워커가 액세스할 수](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API) 있는 API 중[ (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API), 우리가 관심 있는 것은 [Cache API 입니다. (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/Cache). 실제로 메서드와 함께 요청/응답 쌍을 영구 캐시에 넣을 수 있습니다 `cache.put(request, response)`. 하나 이상의 URL을 `cache.add`및 `cache.addAll`메서드에 대한 인수로 전달할 수도 있습니다. 요청되고 네트워크 응답이 캐시에 추가됩니다. 이 방법으로 캐시 항목을 삭제할 수도 있습니다 `cache.delete`.

`caches`서비스 워커 의 변수를 통해 다양한 캐시에 액세스할 수 있습니다 . [CacheStorage API](https://developer.mozilla.org/en-US/docs/Web/API/CacheStorage) 입니다.[ (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/CacheStorage)이를 통해 무엇보다도 캐시 객체를 생성/검색하거나 `caches.open`및 `caches.delete`기능을 사용하여 캐시 객체를 삭제할 수 있습니다.

마지막으로 캐시의 또 다른 흥미로운 방법은 요청이 전달된 매개변수와 동일한 경우에 의해 관리되는 `match`모든 `Cache`객체 를 확인하는 것입니다. `CacheStorage`그렇다면 캐시된 응답으로 해결된 약속을 반환합니다.

## [#](https://pwa-workshop.js.org/3-precaching/#precaching-critical-static-files)중요한 정적 파일 사전 캐싱

우리는 가능한 한 빨리 응용 프로그램의 필수 정적 파일을 캐시합니다. 이를 수행하는 가장 좋은 타이밍은 `install`이벤트가 트리거될 때입니다. 서비스 워커 설치 시 한 번만 호출되기 때문입니다. 이를 *사전 캐싱* 이라고 합니다.

1. `sw.js`2단계 에 표시된 `install`대로 이벤트 콜백으로 이동합니다 .
2. 다음을 사용하여 캐시를 엽니다.[`caches.open('V1')` (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/CacheStorage/open), `cache`객체로 해결된 약속을 반환합니다. 캐시 이름의 버전 번호는 향후 업데이트에 유용합니다.
3. 캐시가 열리면 다음을 사용하여 캐시에 추가하십시오.[`cache.addAll(['url1', 'url2', ...\])` (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/Cache/addAll). 우리 애플리케이션의 필수 정적 파일에 대한 URL: 루트 HTML 페이지, `styles.css`파일 및 `scripts.js`파일.
4. 사전 캐싱이 완료된 후 서비스 워커가 활성화되도록 하려면 `Promise`반환된 by `cache.addAll`를 인수로 전달합니다.[`event.waitUntil ()` (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/ExtendableEvent/waitUntil), `event`설치 이벤트입니다.

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token constant" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token constant" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><font style="user-select: initial !important;"></font><font style="user-select: initial !important;"></font><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token constant" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token constant" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

페이지와 서비스 워커를 다시 로드하여 2단계에서 본 것처럼 서비스 워커의 새 버전이 이전 버전을 대체하는지 확인합니다. 그런 다음 *애플리케이션 의* *캐시 스토리지 섹션* 을 보고 파일이 캐시에 추가되었는지 확인할 수 있습니다. Chrome 개발자 도구 탭.

![캐시 스토리지](https://pwa-workshop.js.org/assets/img/cache_storage.04f1ba9e.png)

## [#](https://pwa-workshop.js.org/3-precaching/#response-with-cache-in-priority)캐시가 우선인 응답

이제 파일이 캐시되었으므로 브라우저에서 요청할 때 캐시된 버전을 사용하도록 지정해야 합니다. 이를 위해 우리는 `fetch`이벤트를 사용할 것입니다. 후자는 서비스 워커를 설치한 클라이언트의 모든 요청을 가로챕니다. [그런 다음 FetchEvent](https://developer.mozilla.org/en-US/docs/Web/API/FetchEvent) 가 있는 `event.respondWith`곳에서 사용자 지정 응답을 반환할 수 있습니다 .`event`[ (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/FetchEvent). 그만큼[`event.respondWith` (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/FetchEvent/respondWith)함수는 반환할 응답으로 해결되어야 하는 약속을 단일 인수로 취합니다.

```js
self.addEventListener("fetch", event => {
  console.log(`Request of ${event.request.url}`);

  // default behaviour: request the network
  event.respondWith(fetch(event.request));
});
```

기본 동작을 변경하고 이전에 캐시된 버전이 있는 경우 이를 반환하려고 합니다. 정확히는,[`caches.match` (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/CacheStorage/match)함수는 `Response`처럼 객체로 해결된 Promise를 반환합니다 `fetch (event.request)`. 따라서 네트워크에서 요청하는 대신 캐시에서 정적 파일을 다른 것으로 교체하고 반환할 수 있습니다.

1. 이전 단계에서 `fetch`와 동일한 방식으로 이벤트에 대한 콜백을 추가합니다 .`install`
2. `caches.match(event.request)`해당 요청에 대한 캐시된 응답에 대해 캐시를 검색하는 메서드를 호출합니다 .
3. 캐시 항목이 없으면 Promise가 값으로 해결됩니다 `undefined`. 이 경우 네트워크를 요청하고 `fetch(event.request)`대신 반환해야 합니다.
4. 마지막으로 클라이언트 요청에 대한 인수로 전달하여 이 응답 약속을 반환합니다.`event.respondWith()`

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><font style="user-select: initial !important;"></font><font style="user-select: initial !important;"></font><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

## [#](https://pwa-workshop.js.org/3-precaching/#testing-offline)오프라인 테스트

모든 것이 올바르게 완료되었으면 이제 오프라인 모드에서 애플리케이션을 테스트할 수 있습니다. 로컬 서버를 종료하고 응용 프로그램을 다시 로드하십시오. 캐시된 페이지가 표시되어야 합니다.

## [#](https://pwa-workshop.js.org/3-precaching/#put-files-in-cache-automatically)파일을 자동으로 캐시에 저장

오프라인일 때 일부 비필수 정적 파일은 다운로드할 수 없으며 캐시에 저장되지 않습니다. 예를 들어 헤더의 PWA 로고. 설치 시 사전 캐싱을 사용하지 않고 이러한 파일을 자동으로 캐시에 저장하도록 서비스 워커 코드를 변경해 보겠습니다.

1. `cache`아래 서비스 워커 코드에 이 함수를 추가 하세요.

```js
function cache(request, response) {
  if (response.type === "error" || response.type === "opaque") {
    return Promise.resolve(); // do not put in cache network errors
  }

  return caches
    .open(CACHE_NAME)
    .then(cache => cache.put(request, response.clone()));
}
```

경고

답변은 한 번만 읽을 수 있으므로 `.clone()`캐시하기 전에 메서드로 복제해야 합니다.

1. `fetch`이벤트 콜백에서 네트워크를 가져온 후 다른 명령을 추가 한 `then`다음 `cache`요청 및 응답과 함께 이전에 선언된 함수를 호출하여 캐시에 넣습니다.
2. `then`함수 에서 요구하는 대로 최종 값으로 네트워크 응답을 사용하여 약속이 확인되도록 마지막 명령을 하나 추가 `event.respondWith`합니다.

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><font style="user-select: initial !important;"></font><font style="user-select: initial !important;"></font><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

자동 캐시를 테스트하려면 온라인으로 돌아가 앱을 다시 로드하여 캐시에 PWA 로고를 넣습니다. *DevTools* 로 캐시에 올바르게 추가되었는지 확인한 다음 오프라인으로 돌아가 인터넷 연결 없이 이 로고를 로드해 보세요.

팁

Amazon에서 가져온 참석자 사진 및 Google에서 가져온 글꼴과 같이 CORS 요청을 허용하지 않는 외부 도메인에서 로드된 파일은 다른 정적 파일과 같이 캐시에 넣을 수 없습니다. 이러한 파일을 도메인에서 호스팅하거나 외부 도메인에서 CORS를 구성하여 파일을 캐시할 수 있도록 해야 합니다.

## [#](https://pwa-workshop.js.org/3-precaching/#cache-updates)캐시 업데이트

정적 파일을 캐싱하면 서버에서 이러한 파일을 추가, 삭제 또는 수정하면 어떻게 됩니까?

현재 서비스 워커를 코딩했으므로 항상 캐시된 파일을 로드하므로 서버에 배포된 새 버전은 사용되지 않습니다.

이 문제를 처리하기 위한 한 가지 솔루션은 다른 이름의 새 캐시를 사용하는 것입니다. 여기에서 아이디어는 새 파일을 포함하는 새 **V2** 캐시를 만들고 사용되지 않는 이전 캐시를 제거하는 것입니다.

1. 이벤트 콜백 에서 `install`캐시 이름을 다음으로 변경합니다.`V2`
2. `activate`이벤트 콜백에서 다음을 사용하여 이전 캐시를 삭제합니다 .`caches.delete('V1')`
3. 활성화 단계가 끝나기 전에 캐시가 제거될 때까지 기다리기 위해 `Promise`반환된 `caches.delete`을 전달 합니다.`event.waitUntil`
4. *(선택 사항)* - 사용된 캐시의 화이트리스트에 없는 모든 캐시를 제거하여 캐시 정리 코드를 개선합니다. `caches.keys()`메소드 를 사용하여 모든 기존 캐시를 탐색할 수 있습니다.

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token constant" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><font style="user-select: initial !important;"></font><font style="user-select: initial !important;"></font><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><font style="user-select: initial !important;"></font><font style="user-select: initial !important;"></font><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token constant" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token template-string" style="user-select: initial !important;"><span data-v-167f501e="" class="token template-punctuation string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token interpolation" style="user-select: initial !important;"><span data-v-167f501e="" class="token interpolation-punctuation punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token interpolation-punctuation punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></span><span data-v-167f501e="" class="token template-punctuation string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

![순서도](https://pwa-workshop.js.org/assets/img/schema.f921f8d6.png)







# 4단계: REST API의 GET 요청에 대한 캐시/업데이트/새로고침 전략

이전 단계에서 정적 파일을 캐시하는 방법을 보았습니다. 이러한 파일에 대해 적용한 전략을 *Cache-First* 라고 합니다. 즉, 가능한 경우 항상 캐시된 버전을 반환합니다. 동적 데이터, 일반적으로 API 응답은 어떻습니까?

오프라인 모드를 사용하려면 이 데이터를 로컬 캐시에 넣어야 합니다. 반면에 정적 파일과 달리 사용자는 가능한 한 빨리 최신 데이터("가장 최신")를 갖는 것이 중요합니다. 그래서 우리는 전략을 바꿔야 합니다.

## [#](https://pwa-workshop.js.org/4-api-cache/#cache-update-refresh)캐시, 업데이트, 새로 고침

이 캐시 전략은 약간 더 복잡하지만 일시적으로 오래된 데이터를 표시하는 경우에도 로컬 캐시를 사용하여 첫 번째 로드 시간을 크게 단축할 수 있습니다.

각 요청에 대해 작업자 서비스는 먼저 캐시된 응답이 있으면 즉시 반환한 다음 병렬로 네트워크를 쿼리합니다. 네트워크로부터 응답을 받으면 캐시된 항목이 업데이트되고 사용자 인터페이스가 자동으로 업데이트됩니다.

인터페이스 새로 고침은 사용 사례에 따라 다른 방식으로 수행할 수 있습니다. 예를 들어 인스턴트 대화의 메시지와 같이 목록에 항목을 동적으로 추가하기만 하면 됩니다. 또는 사용 가능한 새 콘텐츠를 로드하도록 제안하는 링크와 같은 다른 방법으로 사용자에게 알립니다.

![순서도](https://pwa-workshop.js.org/assets/img/schema.688fdb36.png)

장점:

- 응답이 캐시된 경우 즉시 로드
- 사용자 경험을 향상시킬 수 있습니다

단점:

- 사용자는 오래된 데이터와 상호 작용할 수 없어야 합니다.
- 네트워크 응답 후 인터페이스를 새로 고침하면 UX가 제대로 고려되지 않은 경우 사용자를 방해할 수 있습니다.

## [#](https://pwa-workshop.js.org/4-api-cache/#implementation)구현

우리 응용 프로그램의 경우 이 *캐시, 업데이트, 새로 고침* 전략을 구현하여 워크숍 참석자 목록을 로드합니다. 이 목록은 각 요청 사이에 약간 변경될 것이며 대부분의 연속적인 변경은 몇 명의 참석자가 추가되는 것입니다. 따라서 보기를 새로 고치는 것은 사용자에게 매우 자연스럽습니다. 이 전략은 이 경우에 가장 적합한 것 같습니다.

### [#](https://pwa-workshop.js.org/4-api-cache/#choosing-the-right-strategy-depending-on-the-request)요청에 따라 올바른 전략 선택

`fetch`이벤트 콜백 에서 서비스 워커 코드로 다시 시작하겠습니다 . 다른 전략을 적용하기 위해 API에 대한 요청을 정적 파일에 대한 요청과 구분합니다.

```js
self.addEventListener("fetch", event => {
  if (event.request.url.includes("/api/")) {
    // response to API requests, Cache Update Refresh strategy
  } else {
    // response to static files requests, Cache-First strategy
  }
});
```

`/api/`API에 대한 모든 요청 은 URL에 포함된 동일한 엔드포인트를 통과합니다 . 따라서 에서 액세스할 수 있는 URL을 기반으로 이러한 요청을 쉽게 식별할 수 있습니다 `event.request.url`. 다음은 [FetchEvent API 문서 입니다. (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/Web/API/FetchEvent).

### [#](https://pwa-workshop.js.org/4-api-cache/#_1-cache)1. 캐시

첫 번째 단계는 캐시된 응답이 있는 경우 즉시 응답하는 것입니다. `caches.match`정적 파일 요청에 응답하기 위해 3단계에서 본 기능 을 재사용하여 이를 수행할 수 있습니다 .

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px; color: rgb(44, 62, 80); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, Cantarell, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

### [#](https://pwa-workshop.js.org/4-api-cache/#_2-update)2. 업데이트

동시에, 네트워크에 대한 요청도 메서드로 이루어지고 `fetch(request)`메서드를 통해 응답으로 캐시를 업데이트합니다 `cache.put`. `update`서비스 워커 코드에서 아래 함수를 선언합니다 .

```js
function update(request) {
  return fetch(request.url).then(
    response =>
      cache(request, response) // we can put response in cache
        .then(() => response) // resolve promise with the Response object
  );
}
```

그런 다음 캐시된 버전의 응답과 병렬로 콜백에서 이 `update`함수를 호출합니다. 첫 번째 이후에도 다음 코드에서 개체를 `fetch`계속 사용할 수 있습니다 . 그러나 이벤트의 수명을 연장하고 초기 응답 이상으로 더 많은 작업을 수행해야 한다고 브라우저에 알리려면 메서드를 사용해야 합니다 .`event``event.respondWith``event.waitUntil()`

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px; color: rgb(44, 62, 80); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, Cantarell, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

### [#](https://pwa-workshop.js.org/4-api-cache/#_3-refresh)3. 새로 고침

네트워크가 응답하고 응답이 캐시된 경우 애플리케이션 보기를 새로 고치는 데 사용할 수 있는 새 데이터가 있음을 애플리케이션에 알리고 싶습니다.

등록 및 설치된 서비스 워커가 있는 애플리케이션은 이 서비스 워커의 "클라이언트"라고 하며 [여기에 문서화된 클라이언트 API 를 통해 액세스할 수 있습니다. (새 창을 엽니다)](https://developer.mozilla.org/en-US/docs/web/API/Clients). `client.postMessage`서비스 워커는 메소드 를 통해 클라이언트와 통신합니다 . 교환 형식은 텍스트이므로 데이터를 JSON으로 직렬화해야 합니다. 전송된 메시지는 메시지 유형을 식별하기 위한 하나 이상의 속성(여기서 해당 요청의 URL 선택)과 전송할 데이터(응답의 내용)를 포함하는 다른 속성으로 구성된 객체입니다.

`refresh`서비스 워커 코드에서 아래 함수를 선언합니다 .

```js
function refresh(response) {
  return response
    .json() // read and parse JSON response
    .then(jsonResponse => {
      self.clients.matchAll().then(clients => {
        clients.forEach(client => {
          // report and send new data to client
          client.postMessage(
            JSON.stringify({
              type: response.url,
              data: jsonResponse.data
            })
          );
        });
      });
      return jsonResponse.data; // resolve promise with new data
    });
}
```

남은 것은 3과 블록을 조합 `cache`하여 `update`전략 `refresh`을 완성하는 것뿐입니다. 로 캐시 응답을 `event.respondWith`한 다음 병렬로 업데이트하고 에서 새로 고침을 수행합니다 `event.waitUntil`. 이 `update`함수는 네트워크 응답으로 `Promise`해결된 결과를 반환하므로 네트워크 응답 `.then()`후에 다른 함수를 실행하기 위해 연결할 수 있습니다.

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px; color: rgb(44, 62, 80); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, Cantarell, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token comment" style="user-select: initial !important; color: rgb(153, 153, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

## [#](https://pwa-workshop.js.org/4-api-cache/#refreshing-the-view)보기 새로 고침

`navigator.serviceWorker.onmessage`클라이언트는 콜백 을 통해 서비스 워커가 내보낸 메시지를 수신할 수 있습니다 . 그런 다음 를 사용하여 메시지를 역직렬화할 수 있습니다 `JSON.parse(event.data)`. 에서 `scripts.js`서비스 워커가 등록되면 다음 콜백을 선언합니다.

```js
navigator.serviceWorker.onmessage = event => {
  const message = JSON.parse(event.data);
  //TODO: detect the type of message and refresh the view
};
```

이 `renderAttendees`기능을 사용하면 참석자 목록을 업데이트할 수 있습니다. 서비스 워커가 새 데이터와 함께 메시지를 보낼 때 보기가 업데이트됩니다. 위의 코드를 완성하여 메시지가 참석자 목록의 업데이트인지 확인한 다음 `renderAttendees`메시지에서 수신된 데이터로 함수를 호출합니다.

<details data-v-167f501e="" class="hidden" style="user-select: initial !important; padding: 0.5em 1em; background-color: rgb(204, 255, 204); border-radius: 5px; color: rgb(44, 62, 80); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, Oxygen, Ubuntu, Cantarell, &quot;Fira Sans&quot;, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;"><summary data-v-167f501e="" style="user-select: initial !important; outline: none; cursor: help;"><font style="user-select: initial !important; vertical-align: inherit;"><font style="user-select: initial !important; vertical-align: inherit;">솔루션 보기</font></font></summary><div data-v-167f501e="" class="language-js extra-class" style="user-select: initial !important; position: relative; background-color: rgb(40, 44, 52); border-radius: 6px;"><pre data-v-167f501e="" class="language-js" style="user-select: initial !important; color: rgb(204, 204, 204); background: transparent; font-family: Consolas, Monaco, &quot;Andale Mono&quot;, &quot;Ubuntu Mono&quot;, monospace; font-size: 1em; text-align: left; white-space: pre; word-spacing: normal; word-break: normal; overflow-wrap: normal; line-height: 1.4; tab-size: 4; hyphens: none; padding: 1.25rem 1.5rem; margin: 0.85rem 0px; overflow: auto; border-radius: 6px; position: relative; z-index: 1;"><code data-v-167f501e="" style="user-select: initial !important; font-family: source-code-pro, Menlo, Monaco, Consolas, &quot;Courier New&quot;, monospace; color: rgb(255, 255, 255); padding: 0px; margin: 0px; font-size: 0.85em; background-color: transparent; border-radius: 0px;"><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function-variable function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token parameter" style="user-select: initial !important;"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token constant" style="user-select: initial !important; color: rgb(248, 197, 85);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token keyword" style="user-select: initial !important; color: rgb(204, 153, 205);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token operator" style="user-select: initial !important; color: rgb(103, 205, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token string" style="user-select: initial !important; color: rgb(126, 198, 153);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token function" style="user-select: initial !important; color: rgb(240, 141, 73);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span><span data-v-167f501e="" class="token punctuation" style="user-select: initial !important; color: rgb(204, 204, 204);"></span></code></pre></div></details>

## [#](https://pwa-workshop.js.org/4-api-cache/#testing)테스트

로딩 전략이 제대로 작동하는지 테스트하기 위해 애플리케이션은 현재 거짓 데이터가 있는 목업 API를 사용하고 있으므로 참석자 수의 변화를 시뮬레이션하고 잘못된 네트워크 대기 시간을 추가하여 전략 실행을 관찰할 시간을 제공합니다.

`update`다음과 같이 기능을 변경하십시오 `sw.js`.

```js
const delay = ms => _ => new Promise(resolve => setTimeout(() => resolve(_), ms))

function update(request) {
	// mockup: load randomly between 1 and 10 attendees
	return fetch(request.url + `?per_page=${Math.ceil(Math.random() * 10)}`)
	.then(delay(3000)) // add a fake latency of 3 seconds
	.then(response => {
		(...)
```

서비스 워커가 최신 상태이고 다시 설치되었는지 확인하고(개발자 도구, 애플리케이션 > 서비스 워커 탭 *에서 새로고침 시 업데이트* 확인란을 선택하고 2단계 참조) 페이지를 다시 로드합니다. 이전 참석자 수가 표시되고 3초 후에 목록이 새로운 참석자 수로 새로 고쳐집니다.



# 5단계: 장치에 PWA 설치

## [#](https://pwa-workshop.js.org/5-pwa-install/#criteria-for-being-installable)설치 가능 기준

PWA의 장점 중 하나는 브라우저에 의존하는 특정 기준을 준수하면 설치할 수 있다는 것입니다. [Chrome](https://developers.google.com/web/fundamentals/app-install-banners/#criteria) 의 기준은 다음과 같습니다.[ (새 창을 엽니다)](https://developers.google.com/web/fundamentals/app-install-banners/#criteria):

- 웹 앱이 아직 설치되어 있지 않습니다.
  - 사실 이 `prefer_related_applications`아닙니다.
- 사용자 참여 휴리스틱 충족(현재 사용자는 최소 30초 동안 도메인과 상호작용했습니다)
- 다음을 포함하는 웹 앱 매니페스트를 포함합니다.
  - `short_name`또는`name`
  - 아이콘은 192px 및 512px 크기의 아이콘을 포함해야 합니다.
  - `start_url`
  - `fullscreen`표시는 , `standalone`, 또는 중 하나여야 합니다.`minimal-ui`
- HTTPS를 통해 제공됨(서비스 작업자에게 필요)
- 가져오기 이벤트 핸들러를 사용하여 서비스 워커를 등록했습니다.

## [#](https://pwa-workshop.js.org/5-pwa-install/#installing-the-pwa)PWA 설치

브라우저 및 OS에 따라 시스템에 PWA를 설치하기 위한 기술 요구 사항이 다를 수 있습니다. 그러나 이론적으로 매니페스트와 로 들어오는 요청을 처리하는 활성 서비스 워커가 있는 경우 `fetch`지원되는 모든 플랫폼에 해당 PWA를 설치할 수 있어야 하며 매니페스트와 서비스 워커를 활용합니다.

오늘날 최고의 지원을 받는 플랫폼은 **Android** 입니다. 로컬 연결을 공유하여 서버에 연결할 수 있는 Android 스마트폰이 있는 경우 Android용 Chrome을 통해 앱을 로드해 보세요. 웹 페이지가 열리면 Chrome 메뉴에 다음 옵션이 포함되어야 합니다. **홈 화면에 추가**

![홈 화면에 추가](https://pwa-workshop.js.org/assets/img/pwa_install_menu.41073796.jpg)

설치를 계속하십시오. 휴대전화 홈 화면에 새 바로가기가 나타납니다. 이것은 우리의 PWA에 대한 바로 가기입니다!

![PWA 북마크](https://pwa-workshop.js.org/assets/img/pwa_install.cc0b35b6.jpg) ![스플래시 스크린](https://pwa-workshop.js.org/assets/img/splash-screen.a247c4ec.jpg)

PWA가 설치되면 바로 가기를 클릭하면 스플래시 화면이 잠시 표시됩니다. 웹 앱 매니페스트에 지정된 색상과 아이콘을 사용합니다.

매니페스트에서 `display`속성을 설정한 경우 URL 표시줄과 나머지 브라우저 UI가 더 이상 표시되지 않습니다 .`standalone`

![북마크에서 PWA 실행](https://pwa-workshop.js.org/assets/img/pwa-fullscreen.20336af1.jpg)

## [#](https://pwa-workshop.js.org/5-pwa-install/#add-an-installation-button)설치 버튼 추가

원래 브라우저는 배너 표시부터 홈 화면에 앱 아이콘 추가까지 설치와 관련된 모든 단계를 처리했습니다. 그러나 이제 앱이 PWA 설치 프롬프트로 이어지는 UI 표시를 처리할 수 있습니다. 이 경우 브라우저는 해당 버튼과 프롬프트 표시 여부를 관리합니다. 앱은 `beforeinstallprompt`이벤트를 수신하여 설치 프롬프트를 표시할 수 있는지 확인하고 사용자가 요청하는 경우 브라우저에 설치 프롬프트를 표시하도록 요청해야 합니다.

다음 단계에 따라 PWA에 자체 **설치 프롬프트 버튼** 을 추가해 보겠습니다 .

1. [Preferences_related_applications](https://developers.google.com/web/fundamentals/app-install-banners/native#prefer_related_applications) 속성 설정[ (새 창을 엽니다)](https://developers.google.com/web/fundamentals/app-install-banners/native#prefer_related_applications)`false`매니페스트 파일에 .

```json
{
  "prefer_related_applications": false
}
```

1. 페이지 어딘가에 기본적으로 숨겨져 있는 버튼 태그를 추가합니다.

```html
<button id="install_button" hidden>Install</button>
```

1. `beforeinstallprompt`기본 JavaScript 파일 에서 PWA가 설치 기준을 충족할 때 발생 하는 이벤트를 가로챕 니다. 이 이벤트 이벤트 핸들러에서 이벤트에 대한 참조를 유지하고 설치 버튼을 표시해야 합니다.

```js
let deferredPrompt; // Allows to show the install prompt
const installButton = document.getElementById("install_button");

window.addEventListener("beforeinstallprompt", e => {
  console.log("beforeinstallprompt fired");
  // Prevent Chrome 76 and earlier from automatically showing a prompt
  e.preventDefault();
  // Stash the event so it can be triggered later.
  deferredPrompt = e;
  // Show the install button
  installButton.hidden = false;
  installButton.addEventListener("click", installApp);
});
```

1. 그런 다음 `installApp`설치 프롬프트를 표시하는 함수를 추가합니다.

```js
function installApp() {
  // Show the prompt
  deferredPrompt.prompt();
  installButton.disabled = true;

  // Wait for the user to respond to the prompt
  deferredPrompt.userChoice.then(choiceResult => {
    if (choiceResult.outcome === "accepted") {
      console.log("PWA setup accepted");
      installButton.hidden = true;
    } else {
      console.log("PWA setup rejected");
    }
    installButton.disabled = false;
    deferredPrompt = null;
  });
}
```

1. 선택적으로 앱 설치 이벤트를 수신하여 설치가 완료되면 추가 설정을 수행할 수 있습니다.

```js
window.addEventListener("appinstalled", evt => {
  console.log("appinstalled fired", evt);
});
```

실험적

`beforeinstallprompt`이벤트는 실험적 이며 `appinstalled`아직 표준화되지 않았습니다. 2020년 1월에는 Chrome, Android용 Chrome 및 삼성 인터넷에서만 지원되었습니다.

이제 테스트할 시간입니다. 캐시를 지운 후 주저하지 말고 페이지를 완전히 새로고침하세요.

![PWA 설치 버튼](https://pwa-workshop.js.org/assets/img/pwa_setup_button.1a13ee70.png) ![PWA 설치 프롬프트](https://pwa-workshop.js.org/assets/img/pwa_setup_prompt.4b3c7ae2.png)

설치된 PWA가 macOS에서 어떻게 보이는지 보여줍니다.

![PWA 설치](https://pwa-workshop.js.org/assets/img/pwa_installed.7df4c154.png)

# 6단계: 백그라운드 동기화 및 알림

이 단계에서는 Service Worker와 새로운 API인 **Background Sync** 를 사용하여 백그라운드에서 참석자 목록을 업데이트하고 새 참석자가 있을 때 사용자에게 알립니다.

비표준

Background Sync API는 아직 표준화되지 않았습니다. [사양](https://wicg.github.io/BackgroundSync/spec/) _[ (새 창을 엽니다)](https://wicg.github.io/BackgroundSync/spec/)아직 연구 중입니다. 2016년부터 이미 크롬과 안드로이드에 구현되었으며, 엣지와 파이어폭스에서 개발 중이다. 이 API는 * 일회성 * 및 * 주기적 *의 두 가지 유형의 동기화를 구별합니다. 현재 크롬에서는 **일회성** 동기화만 구현되어 있으며, 일부 구현 버그가 있을 수 있습니다.

알림과 관련하여 푸시 API를 사용하면 웹 애플리케이션이 포그라운드에 있지 않거나 현재 사용자 시스템에 로드되지 않은 경우에도 웹 애플리케이션이 서버에서 푸시된 푸시 알림을 수신할 수 있습니다. 그럼에도 불구하고 이는 Google Cloud Messenger와 같은 서버 측 푸시 서비스의 사용을 의미합니다. 이 워크샵에서는 푸시 API가 아닌 **알림 API를 사용** 합니다. 이 API를 사용하면 서버 부분 없이 알림을 보낼 수도 있지만 브라우저가 열려 있어야 합니다. 이러한 알림은 다중 플랫폼이므로 대상 플랫폼(예: Android 알림 또는 Windows 10의 알림 센터)에 맞게 조정됩니다.

## [#](https://pwa-workshop.js.org/6-background-sync/#notifications-and-permissions)알림 및 권한

백그라운드에서 동기화는 특별한 권한이 필요하지 않지만 알림을 게시하려면 사용자의 동의가 필요합니다. 스팸을 피하기 위해 브라우저는 이러한 권한을 요청할 수 있는 제약 조건을 추가했습니다. 예를 들어 링크를 클릭하는 것과 같은 특정 사용자 작업 후에 이 작업을 수행할 수 있습니다.

`index.html`따라서 알림을 활성화 하려면 페이지의 어딘가 아래에 이 링크를 추가하십시오 .

```html
<a onclick="registerNotification()">Notify me when there are new attendees</a>
```

그런 다음 다음 함수를 선언하십시오.`scripts.js`

```js
function registerNotification() {
	Notification.requestPermission(permission => {
		if (permission === 'granted'){ registerBackgroundSync() }
		else console.error("Permission was not granted.")
	})
}
```

사용자가 알림 표시 권한을 부여하면 `registerBackgroundSync`백그라운드 동기화를 설정하는 함수를 호출합니다.

## [#](https://pwa-workshop.js.org/6-background-sync/#background-synchronization)백그라운드 동기화

클라이언트는 백그라운드에서 동기화 작업을 명시적으로 등록해야 합니다. 이를 위해 먼저 `ServiceWorkerRegistration`서비스 워커와 클라이언트 간의 등록 링크를 나타내는 에 대한 참조를 검색합니다. 이를 수행하는 가장 간단한 방법은 [ ](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerContainer/ready)를 사용하는 것 입니다. 이는 서비스 워커가 설치되어 실행 중일 때 해결됨 `navigator.serviceWorker.ready`을 반환합니다. .`Promise``ServiceWorkerRegistration`

유형 의 `registration`개체 가 검색되면 백그라운드에서 일회성 동기화 작업을 등록 `ServiceWorkerRegistration`하는 메서드를 호출할 수 있습니다 .`registration.sync.register`

```js
function registerBackgroundSync() {
    if (!navigator.serviceWorker){
        return console.error("Service Worker not supported")
    }

    navigator.serviceWorker.ready
    .then(registration => registration.sync.register('syncAttendees'))
    .then(() => console.log("Registered background sync"))
    .catch(err => console.error("Error registering background sync", err))
}
```

서비스 워커 측에서는 `sync`시스템이 동기화를 트리거하기로 결정하면 이벤트가 발생합니다. 이 결정은 연결, 배터리 상태, 전원 등 다양한 매개변수를 기반으로 합니다. 그래서 우리는 동기화가 언제 트리거될지 확신할 수 없습니다. 사양은 향후 매개변수화 옵션을 계획하고 있지만 현재로서는 기다리는 것뿐입니다. 그러나 이 워크샵의 조건에서 이 작업은 몇 초 밖에 걸리지 않습니다.

```js
self.addEventListener('sync', function(event) {
	console.log("sync event", event);
    if (event.tag === 'syncAttendees') {
        event.waitUntil(syncAttendees()); // sending sync request
    }
});
```

## [#](https://pwa-workshop.js.org/6-background-sync/#update-and-notification)업데이트 및 알림

동기화 요청은 클라이언트가 아닌 시스템에서 요청한다는 점을 제외하고 4단계 의 `update`및 와 유사합니다.`refresh`

```js
function syncAttendees(){
	return update({ url: `https://reqres.in/api/users` })
    	.then(refresh)
    	.then((attendees) => self.registration.showNotification(
    		`${attendees.length} attendees to the PWA Workshop`
    	))
}
```

클라이언트에 알림을 볼 수 있는 권한이 있는 경우 `self.registration.showNotification`메서드는 등록된 클라이언트에 원하는 텍스트와 함께 알림을 표시해야 합니다.

## [#](https://pwa-workshop.js.org/6-background-sync/#testing)테스트

경고

이전 단계와 마찬가지로 항상 최신 버전의 서비스 워커를 다시 로드하려면 개발자 도구에서 **다시 로드 시 업데이트 상자가 선택되어 있는지 확인하십시오.**

알림 활성화 링크를 클릭하면 권한 요청이 표시되어야 합니다. `Registered background sync`수락 후 콘솔에서 관찰해야 합니다 . 시스템이 동기화를 트리거하고 알림을 표시할 때까지 기다리기만 하면 됩니다. 몇 초, 최대 1분 정도 걸립니다. `refresh`웹 애플리케이션이 포그라운드에 있는 경우 참가자 목록도 의 기능 으로 업데이트되어야 합니다 `syncAttendees`.

알림 링크를 다시 클릭하여 새 동기화를 요청할 수 있습니다.







#  완성된 ? 축하합니다 ! 🎉

이제 프로그레시브 웹 앱의 기본 사항을 알게 되었지만 아직 발견할 것이 많습니다!

## [#](https://pwa-workshop.js.org/finish.html#quiz)퀴즈

PWA에 대한 지식을 테스트하고 싶습니까? 여기에서 퀴즈를 볼 수 있습니다: [Quiz PWA(새 창을 엽니다)](https://sylvainpolletvillard.github.io/quiz-static/?quiz=pwa)

## [#](https://pwa-workshop.js.org/finish.html#additional-exercises)추가 연습

다음은 이 워크숍에서 얻은 지식을 통해 달성할 수 있는 몇 가지 추가 연습입니다.

- `fallback.jpg`네트워크 요청이 실패하면 캐시되지 않은 참석자의 이미지를 기본 이미지로 교체
- API GET 요청 캐시에 만료 시간 추가
- 이전 동기화 1분 후 자동으로 백그라운드 동기화 요청

## [#](https://pwa-workshop.js.org/finish.html#a-comprehensive-documentation-on-pwa)PWA에 대한 포괄적인 문서

여기에서 프로그레시브 웹 앱에 대한 전체 문서를 찾을 수 있습니다. [pwa-cookbook.js.org(새 창을 엽니다)](http://pwa-cookbook.js.org/)

## [#](https://pwa-workshop.js.org/finish.html#remark-correction-suggestion)지적, 수정, 제안?

모든 자료는 [**Github 에서 사용할 수 있습니다.** (새 창을 엽니다)](https://github.com/sylvainpolletvillard/pwa-workshop)* Pull Requests *를 통해 외부 기여를 받을 수 있습니다.





