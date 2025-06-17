# Lotto Map Backend

## 개요
Lotto Map Backend는 **명당 로또 판매점 위치 정보**를 제공하는 서비스의 백엔드 서버입니다.  
당첨 이력을 기준으로 판매점 순위를 매겨 제공합니다.  
이 프로젝트는 사용자 주변의 명당 판매점을 쉽게 찾을 수 있도록 도와주는것을 목적으로 합니다.

## 기술 스택
- **프레임워크:** Spring Boot
- **언어:** Java 21
- **데이터베이스:** postgresql
- **ORM:** Spring Data JPA
- **크롤러** Jsoup

## API 명세

### 공통 응답 형식
모든 API는 JSON 형식의 응답을 반환합니다.

### 엔드포인트

#### 1. 사용자 주변 판매점 조회
- **URL:** `/stores?lat={위도}&lon={경도}`
- **Method:** `GET`
- **Response:**
  ```json
  {    
    "data": [
      {
        "id": 1,
        "name": "판매점명",
        "address": "판매점 주소",
        "phone" : "전화번호",
        "lat": "위도",
        "lon": "경도"        
      },
      ...
    ]
  }
#### 2. 특정 판매점 상세 정보 조회
- **URL:** `/stores/:id`
- **Method:** `GET`
- **Response:**
```json
  {    
    "data": {
        "id": 1,
        "name": "판매점명",
        "address": "판매점 주소",
        "phone" : "전화번호",
        "lat": "위도",
        "lon": "경도" ,
        "winningInfo" : [
          {
            "id" : "당첨정보 ID",
            "store_id" : 1,
            "draw_no" : "회차번호",
            "rank" : "당첨 등수",
            "category" : "당첨 분류(자동,수동)"          
          },
          
        ]        
      }
  }
```

## Schedule
* 변경 혹은 폐업한 판매점정보를 갱신합니다.
* `Jsoup`를 이용해, 동행복권 사이트에 접속해 당첨정보를 추출하여 저장합니다.
* `store_ranking` View를 갱신합니다.
* 매주 일요일 02시에 실행됩니다.

## Entities

#### Store
| 컬럼명 | 데이터타입 | 설명 |
|-------|-------|-------|
| id | integer | 판매점 ID (Primary Key) |
| name | varchar | 판매점 이름 |
| address | varchar | 판매점 주소 |
| phone | varchar | 판매점 전화번호 |
| lat | decimal | 판매점 위도 |
| lon | decimal | 판매점 경도 |

#### WinningInfo
| 컬럼명 | 데이터타입 | 설명 |
|-------|-------|-------|
| id | integer | 당첨 정보 ID (Primary Key) |
| store_id | integer | 판매점 ID (Foreign Key - stores.id) |
| draw_no | integer | 회차번호 |
| rank | integer | 당첨 등수 |
| category | varchar | 당첨 분류 (자동,수동) |

## View

#### store_ranking
| 컬럼명 | 데이터타입 | 설명 |
|-------|-------|-------|
| id | integer | 판매점 ID (Primary Key) |
| name | varchar | 판매점 이름 |
| address | varchar | 판매점 주소 |
| phone | varchar | 판매점 전화번호 |
| lat | decimal | 판매점 위도 |
| frist_prize | integer | 1등 당첨횟수 |
| second_prize | integer | 2등 당첨횟수 |
| score | integer | 통계 점수 |


>판매점당 통계 점수를 미리 계산해 두어, 조회 시 더 빠르게 처리할 수 있도록 설계하였습니다.  
>스케줄이 실행되어 당첨 정보가 추가될 때, 갱신되도록 설정하였습니다.
