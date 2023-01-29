# CentOS 기본설정

## 5.2.2 timedatectl 명령어를 사용한 설정
#### 1) 시간정보 확인

```shell
[zeroco@localhost ~]$ timedatectl
      Local time: Sat 2023-01-28 23:39:18 PST -- 현재시간
  Universal time: Sun 2023-01-29 07:39:18 UTC -- 협정세계시
        RTC time: Sun 2023-01-29 07:39:18
       Time zone: America/Los_Angeles (PST, -0800)  -- 시간대 
     NTP enabled: yes
NTP synchronized: yes
 RTC in local TZ: no
      DST active: no
 Last DST change: DST ended at
                  Sun 2022-11-06 01:59:59 PDT
                  Sun 2022-11-06 01:00:00 PST
 Next DST change: DST begins (the clock jumps one hour forward) at
                  Sun 2023-03-12 01:59:59 PST
                  Sun 2023-03-12 03:00:00 PDT
```
### 2) 날짜와 시간 모두 설정 
```shell
[zeroco@localhost ~]$ timedatectl set-time '2023-01-29 16:41:30'
Failed to set time: Automatic time synchronization is enabled
== 안됨
```

### 3) 시간대(Time zone) 변경
```shell
[zeroco@localhost ~]$ timedatectl list-timezones | grep Asia
Asia/Aden
Asia/Almaty
Asia/Amman
...
Asia/Seoul
...
[zeroco@localhost ~]$ timedatectl set-timezone Asia/Seoul

```
### 4) RTC 하드웨어 시계 시간설정
- 시스템에서 하드웨어 시계를 사용하게 설정하고, 날짜와 시간을 설정한 다음에 시스템에서 하드웨어 시계를 사용하지 않게 하는 것

```shell
[zeroco@localhost ~]$ timedatectl set-local-rtc yes
[zeroco@localhost ~]$ timedatectl set-time "2023-01-29 16:29:35"
Failed to set time: Automatic time synchronization is enabled
[zeroco@localhost ~]$ timedatectl set-local-rtc no
```

### 5) 시간자동 동기화
- timedatectl 명령어는 NTP 를 사용하여 원격지에 잇는 타임서버에서 시간을 가져와 시스템 시계와 자동으로 동기화 시킬 수 있다. 
 ```shell
[zeroco@localhost ~]$ timedatectl set-ntp yes
[zeroco@localhost ~]$ timedatectl set-ntp no
```
