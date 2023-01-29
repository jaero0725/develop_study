# CentOS 기본설정

## 5.2.2 timedatectl 명령어를 사용한 설정
#### 1) 시간정보 확인

```shell
[zeroco@localhost ~]$ timedatectl
      Local time: Sat 2023-01-28 23:39:18 PST
  Universal time: Sun 2023-01-29 07:39:18 UTC
        RTC time: Sun 2023-01-29 07:39:18
       Time zone: America/Los_Angeles (PST, -0800)
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
[zeroco@localhost ~]$ 
```
