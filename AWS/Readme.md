# AWS
- 클라우드 컴퓨팅이란?

###  클라우드 장점 
```
  초기비용 -> 가변비용
  초기 비용 - 기술 리소스를 사용하기 전에 먼저투자
  가변 비용 - 사용한 만큼만 비용을 지불
  
  비용 최적화
  - 데이터 센터 운영 X -> 비용 최적화
  - 애플리케이션과 고객에 집중할 수 있음 
  
  용량
  - 필용한 인프라 용량을 추정할 필요가 없음
  - 필요에 따라 축소 및 확장

  규모의 경제
  - 더작은 규모 : 내 사용량만을 기준으로 더 비싼 요금을 지불
  - 규모의 경제 : 집계{된 고객 사용량으로 인한 이점
  
  속도와 민첩성
  - 비지니스 민첩성* 
  - 데이터 센터 - 리소스 필요 시점과 리소스 확보 시점 간의 간격의 주 단위 이지만, 
  - 클라우드 컴퓨팅을 사용하면 분단위로 가능함.
  
  몇 분 만에 전 세계에 배포 
  - 애플리케이션을 전세계에 빠르게 배포 가능
  - AWS 글로벌 인프라 사용할 수 있다. 
```

### 클라우드 컴퓨팅 배포 모델
```
  1. 클라우드 방식
  기존 인프라에서 클라우드로 마이그레이션을 하거나, 애초에 클라우드에서 시작하는 것 
  
  2. 하이브리드
  일반적으로 많이 활용하는 방식
  기존 클라우드 리소스와 데이터센터와 간의 인프라를 연결해서 사용하는 방법
  클라우드 + 온프레미스 
  
  3. 온프레미스(프라이빗 클라우드)  
```

### AWS란?
```
다양한 글로벌 클라우드 기반 제품을 제공하는 보안 클라우드 플랫폼

```

### AWS 기초 서비스 
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/1e631b37-e594-4a6e-8e92-04e00a690474)

### AWS 핵심 서비스
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/fa08f3cf-d55f-4758-b508-80664f3566a6)

## AWS 글로벌 인프라 
```
  
  리전?
  - 지리적 영역 
  - 리전 전체의 데이터 복제는 고객이 제어한다.
  - 리전 간 통신에는 AWS 백본 네트워크 인프라가 사용됨.
  - 리전은 일반적으로 3개 이상의 가용영역으로 구성됨. 
  
  리전 선택
  1. 데이터 거버넌스, 법적 요구 사항 - 유럽을 대상으로 서비스 할 경우 유럽 리전을 선택해야됨 
  2. 고객에 대한 근접성(지연시간) 
  3. 리전 내에서 사용 가능한 서비스 
  4. 비용(리전별로 다름) - 리전마다 서비스 비용이 다름. 
  
```
  가용영역 
  - 각 리전에는 다수의 가용 영역이 있다.
  - 각 가용 영역은 AWS 인프라의 완전히 격리된 파티션이다. 
  - 가용 영역을 이용하여 고가용성 에플리케이션을 구성 
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/cf66edbe-7112-4b3d-9745-27ad6c2e56e2)

## 컴퓨팅 
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/1a7161e6-4d91-48ba-afcd-8d7f713d258d)

### EC2?
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/7ee521ad-b05b-4d49-b31e-b74e3f118b65)
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/5ae930a2-d236-426e-a656-c335ee7258c4)
- 인스턴스 유형을 세분화 시켜 놔서 사용사례에 따라 고르면 됨. 
- r? : 메모리 집약적 애플리케이션 / c? : 대규모 배치시스템 고성능 ....

### 컴퓨팅 서비스 분류
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/b1ade3ca-0e0e-4150-9289-e409a53c1cf4)

### Elastic Load Balancing
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/4b60aa68-6e88-420f-b91a-e71b85284d5a)
- 수신되는 애플리케이션 또는 네트워크 트래픽을 단일 가용영역 또는 여러 가용 영역의 여러 대상에 분산함
- 시간이 지나면서 애플리케이션에 대한 트래픽이 변화하면 로드 밸런서를 자동으로 조정함

### Amazon Ec2 Auto Scaling
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/3318f859-78a5-4e97-b30d-6c9ad0178335)

## 스토리지
### 스토리지 서비스 범주
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/1e30a4c7-b955-4ea9-8683-54ab13781034)

### Amazon S3 
- 데이터는 버킷 내에 객체로 저장됨 
- 무제한 스토리지 / 단일 객체는 5TB로 제한됨
- 99.9999999999999999% 내구성
- 버킷 및 객체에 대한 세분화된 액세스 

### Amazon EBS
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/b03a022c-b400-4c49-ac94-b3947e823c9e)

- 인스턴스에 연결할 영구 블록 스토리지 
- 복제를 통해 보호 / 스냅샷 기능 
- 상이한 드라이브 유형
- 몇분 만에 확장또는 축소
- 프로비저닝한 만큼만 비용 지불

1. SSD 타입 - 일반적으로 SSD을 선택  
2. HDD 타입 - 로그 정보 같은 경우 이걸 사용하면 좋음

### 공유 파일 시스템
#### Q. 여러 인스턴스가 동일한 스토리지를 사용해야 하는 경우는? 
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/1cd8f3cf-2b72-43be-8a0c-cee39d6639bf)
- S3는 인터넷을 통해서 접근하기 때문에, 보안적인 요구사항을 총족하지 못할 수 있다 따라서 이럴 경우는 EFS/FSx를 사용하는 것이 좋다. 

### Amazon S3 Glacier?
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/9adc8239-49cd-41e8-aa63-cba9edba51b4)
- 매우 저렴한 데이터 보관 및 장기 백업
- 3~5시간 또는 12시간 이내 (Bulk형 데이터)
- 사용 빈도에 따라 일련의 데이터 관리 정책을 만들 어 비용을 최적화 할 수 있다.

## 데이터 베이스 
### 데이터 베이스 서비스 범주
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/f50e987f-907d-4101-bd62-c56c61ca1d2c)

### DIY와 AWS 데이터베이스 서비스 비교 
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/be179c43-7579-4526-90db-6be8deafeed4)
- OS에 대해 직접적인 접근이 필요할 때, 비관리형으로 사용 
- 관리형 - 애플리케이션에만 집중해야 할때

### Amazon RDS
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/9497c843-b892-4fab-b70a-217546abb1b4)
- Amazon Auroara : MySql, PostgreSQL과 호환, 고성능의 관계형 DB

### Amazon DyanamoDB
- 어떤 규모에서든 빠르고 유현한 NoSQL 데이터 베이스 서비스
- 완전관리형/지연 시간이 짧은 쿼리/세분화된 엑세스 제어/리전 및 글로벌 옵션 
- 서버리스 NoSQL DB

### 그외의 데이터베이스
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/2eac6071-2ece-4276-8edf-2dfa2d1ac0c3)
- Redshift  
- DocumentDB : mongoDb호환
- Neptune 

## 네트워크 
- 네트워크를 간과하는 경우가 많다. 
- 네트워크를 어떻게 하느냐에 따라, 비용 보안 가용성능 많은 부분이 달라질 수 있기 때문에 중요하다. 
- 
### 네트워크 서비스 범주
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/7f031165-3da9-4603-81b9-f5652331cbdc)

### Amazon VPC
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/8703a90b-a5b7-497c-acc8-dfbbd858217b)
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/0f678cfe-2346-4b58-b44d-32987a48b15c)

## 보안
- AWS의 최우선 과제
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/2535e415-548f-4c7f-bebf-35b69de4e0dc)

### IAM
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/390bb6ec-dc4c-44c2-96eb-b174689a48f0)

### AWS Organizations
- multi Account : 획일화된 방법으로 계정을 관리 
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/e3ec7f9f-9bb4-4c4a-a109-51ab745d9672)

### Multi-Factor Authntication 
- AWS 계정에 추가 보호 계층을 제공함
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/63d8d9e2-ed8d-472f-b65f-e44b0ada383b)

## 요금
- 사용량에 따라 지불
- 예약을 통한 비용 절감
- 사용량이 많을수록 비용 절감
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/ea20902d-ed5e-4ff2-a156-07521975b363)


## 요금 계산기 
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/ceb3507b-6764-4ac7-9e0b-ecdd38c801fa)
![image](https://github.com/jaero0725/Database-Infra_Study/assets/55049159/282d78d4-ab08-4007-846c-f05b002545c0)


