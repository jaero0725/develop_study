# 리눅스 기본 명령어 

### ls : list 의 약자로 windows의 dir과 같은 역할을 한다. 즉 해당 디렉터리에 있는 파일의 목록을 나열한다.
``` shell
> ls
> ls /etc/sysconfig
> ls -a : 숨긴파일 보여줌
> ls -l : 자세히 보여줌
> ls *.cfg : 확장자가 cfg인 목록 보여점
> ls -l /etc/sysconfig/a* : /etc/sysconfig 디렉토리에 있는 목록 중 앞 글자가 'a' 인것 의 목록을 자세히 보여줌

```

### cd : change directory 의 약자로 디렉터리를 이동하는 명령이다.
``` shell
> cd : 현재 사용자의 홈 디렉터리로 이동, 만약 현재 사용자가 root면 /root 디렉터리로 이동
> cd ~centos : centos 사용자의 홈 디렉터리로 이동
> cd .. : 바로 상위의 디렉터리로 이동 '..' 은 현 디렉터리의 부모 디렉터리를 의미
> cd /etc/sysconfig : /etc/sysconfig 디렉터리로 이동 (절대경로)
> cd ../etc/sysconfig : 상대경로로 이동, 현재 디렉터리의 상위로 이동한뒤 /etc/sysconfig 디렉터리로 이동 (상대경로)
```

### pwd : Print Working Directory의 약자로, 현재 디렉터리의 전체 경로를 화면에 보여준다.
``` shell
[root@localhost /]# pwd
/

```

### rm : ReMove의 약자로, 파일이나 디렉터리를 삭제한다. 삭제권한이 있어야 가능함. root 사용자는 모든 권한이 있으므로 명령에 제약X
### rmdir : ReMove DIRectory의 약자로 디렉터리를 삭제한다. 디렉터리가 비어 있어야 삭제가능. 
- 파일이 들어있는 디렉터리 삭제시 'rm -r' 명령을 실행한다. 
``` shell
[zeroco@localhost home]$ ll
total 4
drwx------.  3 zerco_test zerco_test   78 Jan 29 16:09 zerco_test
drwx------. 15 zeroco     zeroco     4096 Jan 29 17:28 zeroco
[zeroco@localhost home]$ rmdir zerco_test
rmdir: failed to remove ‘zerco_test’: Permission denied
```

### cp : CoPy의 약자로, 파일이나 디렉터리를 복사한다. 
### touch : 크기가 0인 새 파일을 생성하거나, 이미 파일이 존재한다면 파일의 최종 수정 시간을 변경한다.
### mv : Move의 약자로 파일이나 디렉터리의 이름을 변경하거나 다른 디렉터리뢰 옮길떄 사요한다.
### mkdir : 새로운 디렉터리를 만든다. 명령을 실행한 사용자의 소유가 된다. 
### cat : 파일 내용을 화면에 보여준다. 여러개의 파일을 나열하면 파일을 연결해서 보여준다.
``` shell
[zeroco@localhost Public]$ touch abc.txt
[zeroco@localhost Public]$ ll
total 0
-rw-rw-r--. 1 zeroco zeroco 0 Jan 29 22:20 abc.txt
[zeroco@localhost Public]$ cp abc.txt cba.txt
[zeroco@localhost Public]$ ls
abc.txt  cba.txt

```
### head, file : 앞뒤 ... 
### more : 텍스트 형식으로 작성된 파일을 페이지 단위로 화면에 출력한다. space bar를 누르면 다음페이지로 이동하며 B를 누르면 앞 페이지로 이동하고 q를 누르면 종료한다. 
### less : more과 비슷 하지만 기능이 확장되어 있음
### file : 해당 파일이 어떤 종류의 파일인지 표시해준다.
### clear 

<hr>
## 사용자 관리와 파일 속성 
- 리눅스에선 파일 관리를 하기 위해 파일 속성에 대해서 알아야한다. 다중 사용자 시스템인 리눅스에서 사용ㅈ아를 관리하는 방법에 대해서 알아야 함
### 사용자와 그룹 
> 리눅스는 다중 사용자 시스템이다.(Multi-user-system) : 즉 1대의 리눅스에 사용자 여러명이 동시에 접속해서 사용할 수 있는 시스템이다.<br>
> 리눅스를 설치하면 기본적으로 root라는 이름을 가진 슈퍼 유저가 있다.<br>
> 이 root 사용자는 시스템의 모든 작업을 실행할 수 있는 권한이 있다. 또한 시스템에 접속할 수 잇는 사용자를 생성할 수 있는 권한도 있다. <br>
> 그런데 모든 사용자는 혼자서 존재하는 것이 아니라 하나 이상의 그룹에 소속되어 있어야 한다. <br>
