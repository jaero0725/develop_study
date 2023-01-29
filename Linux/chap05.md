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

