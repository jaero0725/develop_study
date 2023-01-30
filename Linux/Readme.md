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
[root@lㅌocalhost /]# pwd
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
### 1. 사용자와 그룹 
> 리눅스는 다중 사용자 시스템이다.(Multi-user-system) : 즉 1대의 리눅스에 사용자 여러명이 동시에 접속해서 사용할 수 있는 시스템이다.<br>
> 리눅스를 설치하면 기본적으로 root라는 이름을 가진 슈퍼 유저가 있다.<br>
> 이 root 사용자는 시스템의 모든 작업을 실행할 수 있는 권한이 있다. 또한 시스템에 접속할 수 잇는 사용자를 생성할 수 있는 권한도 있다. <br>
> 그런데 모든 사용자는 혼자서 존재하는 것이 아니라 하나 이상의 그룹에 소속되어 있어야 한다. <br>

vi 에디터로 /etc/passwd 파일 확인

```shell
[root@localhost etc]# cat passwd
root:x:0:0:root:/root:/bin/bash
bin:x:1:1:bin:/bin:/sbin/nologin
daemon:x:2:2:daemon:/sbin:/sbin/nologin
adm:x:3:4:adm:/var/adm:/sbin/nologin
lp:x:4:7:lp:/var/spool/lpd:/sbin/nologin
sync:x:5:0:sync:/sbin:/bin/sync
shutdown:x:6:0:shutdown:/sbin:/sbin/shutdown
halt:x:7:0:halt:/sbin:/sbin/halt
mail:x:8:12:mail:/var/spool/mail:/sbin/nologin
operator:x:11:0:operator:/root:/sbin/nologin
games:x:12:100:games:/usr/games:/sbin/nologin
ftp:x:14:50:FTP User:/var/ftp:/sbin/nologin
nobody:x:99:99:Nobody:/:/sbin/nologin
systemd-network:x:192:192:systemd Network Management:/:/sbin/nologin
dbus:x:81:81:System message bus:/:/sbin/nologin
polkitd:x:999:998:User for polkitd:/:/sbin/nologin
libstoragemgmt:x:998:995:daemon account for libstoragemgmt:/var/run/lsm:/sbin/nologin
colord:x:997:994:User for colord:/var/lib/colord:/sbin/nologin
rpc:x:32:32:Rpcbind Daemon:/var/lib/rpcbind:/sbin/nologin
saned:x:996:993:SANE scanner daemon user:/usr/share/sane:/sbin/nologin
gluster:x:995:992:GlusterFS daemons:/run/gluster:/sbin/nologin
saslauth:x:994:76:Saslauthd user:/run/saslauthd:/sbin/nologin
abrt:x:173:173::/etc/abrt:/sbin/nologin
setroubleshoot:x:993:990::/var/lib/setroubleshoot:/sbin/nologin
rtkit:x:172:172:RealtimeKit:/proc:/sbin/nologin
pulse:x:171:171:PulseAudio System Daemon:/var/run/pulse:/sbin/nologin
radvd:x:75:75:radvd user:/:/sbin/nologin
chrony:x:992:987::/var/lib/chrony:/sbin/nologin
unbound:x:991:986:Unbound DNS resolver:/etc/unbound:/sbin/nologin
qemu:x:107:107:qemu user:/:/sbin/nologin
tss:x:59:59:Account used by the trousers package to sandbox the tcsd daemon:/dev/null:/sbin/nologin
sssd:x:990:984:User for sssd:/:/sbin/nologin
usbmuxd:x:113:113:usbmuxd user:/:/sbin/nologin
geoclue:x:989:983:User for geoclue:/var/lib/geoclue:/sbin/nologin
ntp:x:38:38::/etc/ntp:/sbin/nologin
gdm:x:42:42::/var/lib/gdm:/sbin/nologin
rpcuser:x:29:29:RPC Service User:/var/lib/nfs:/sbin/nologin
nfsnobody:x:65534:65534:Anonymous NFS User:/var/lib/nfs:/sbin/nologin
gnome-initial-setup:x:988:982::/run/gnome-initial-setup/:/sbin/nologin
sshd:x:74:74:Privilege-separated SSH:/var/empty/sshd:/sbin/nologin
avahi:x:70:70:Avahi mDNS/DNS-SD Stack:/var/run/avahi-daemon:/sbin/nologin
postfix:x:89:89::/var/spool/postfix:/sbin/nologin
tcpdump:x:72:72::/:/sbin/nologin
zeroco:x:1000:1000:ZEROCO01:/home/zeroco:/bin/bash
zerco_test:x:1001:1001:ZEROCO02:/home/zerco_test:/bin/bash

```
- 여러명의 사용자가 보임. root사용자부터, tcpdump까지 표준사용자이다. 

``` shell
* 의미 
사용자 이름 : 암호 : 사용자 ID : 사용자소속된 그룹 ID :전체이름 : 홈디렉터리 : 기본셸
zeroco:x:1000:1000:ZEROCO01:/home/zeroco:/bin/bash

```

/etc/group 확인
``` shell
[root@localhost etc]# cat group
root:x:0:
bin:x:1:
daemon:x:2:
sys:x:3:
adm:x:4:
tty:x:5:
disk:x:6:
lp:x:7:
mem:x:8:
kmem:x:9:
wheel:x:10:
cdrom:x:11:
mail:x:12:postfix
man:x:15:
dialout:x:18:
floppy:x:19:
games:x:20:
tape:x:33:
video:x:39:
ftp:x:50:
lock:x:54:
audio:x:63:
nobody:x:99:
users:x:100:
utmp:x:22:
utempter:x:35:
input:x:999:
systemd-journal:x:190:
systemd-network:x:192:
dbus:x:81:
polkitd:x:998:
printadmin:x:997:
cgred:x:996:
libstoragemgmt:x:995:
colord:x:994:
rpc:x:32:
saned:x:993:
dip:x:40:
gluster:x:992:
ssh_keys:x:991:
saslauth:x:76:
abrt:x:173:
setroubleshoot:x:990:
rtkit:x:172:
pulse-access:x:989:
pulse-rt:x:988:
pulse:x:171:
radvd:x:75:
chrony:x:987:
unbound:x:986:
kvm:x:36:qemu
qemu:x:107:
tss:x:59:
libvirt:x:985:
sssd:x:984:
usbmuxd:x:113:
geoclue:x:983:
ntp:x:38:
gdm:x:42:
rpcuser:x:29:
nfsnobody:x:65534:
gnome-initial-setup:x:982:
sshd:x:74:
slocate:x:21:
avahi:x:70:
postdrop:x:90:
postfix:x:89:
stapusr:x:156:
stapsys:x:157:
stapdev:x:158:
tcpdump:x:72:
zeroco:x:1000:zeroco
zerco_test:x:1001:

* 의미
그룹 이름 : 비밀번호 : 그룹 id : 그룹에 속한 사용자 이름 
```

```shell
디렉토리, 파일 복사
> cp -R srcdir destdir


링크

하드링크
- ref 필드 주목 : ref-1 if 0 이면 삭제됨 
- 원본이든 복사본이든 hard link이면 그 개념이 애매해짐 
- 하드링크는 파일에만 적용됨

소프트 링크 (심볼릭 링크)
- 바로가기 버튼
- 링크파일이 링크 파일을 참조하는 방법, 파일 디렉토리 든 상관 없음
- 오픈소스 라이버리에서 적용됨 

[root@localhost Public]# ls
bbb  cba.txt  testDir
[root@localhost Public]# touch library.0.1.so
[root@localhost Public]# ln -s library0.1.so library.so
[root@localhost Public]# ls -l
total 0
-rw-rw-r--. 1 zeroco zeroco  0 Jan 29 22:20 bbb
-rw-rw-r--. 1 zeroco zeroco  0 Jan 29 22:21 cba.txt
-rw-r--r--. 1 root   root    0 Jan 30 23:19 library.0.1.so
lrwxrwxrwx. 1 root   root   13 Jan 30 23:19 library.so -> library0.1.so
drwxrwxr-x. 2 zeroco zeroco 26 Jan 29 22:24 testDir
[root@localhost Public]# touch library.0.2.so
[root@localhost Public]# rm - f library.so
rm: cannot remove ‘-’: No such file or directory
rm: cannot remove ‘f’: No such file or directory
rm: remove symbolic link ‘library.so’? ls
[root@localhost Public]# ^C
[root@localhost Public]# ls
bbb  cba.txt  library.0.1.so  library.0.2.so  library.so  testDir
[root@localhost Public]# ls -l
total 0
-rw-rw-r--. 1 zeroco zeroco  0 Jan 29 22:20 bbb
-rw-rw-r--. 1 zeroco zeroco  0 Jan 29 22:21 cba.txt
-rw-r--r--. 1 root   root    0 Jan 30 23:19 library.0.1.so
-rw-r--r--. 1 root   root    0 Jan 30 23:20 library.0.2.so
lrwxrwxrwx. 1 root   root   13 Jan 30 23:19 library.so -> library0.1.so
drwxrwxr-x. 2 zeroco zeroco 26 Jan 29 22:24 testDir
[root@localhost Public]# ls -li
total 0
   76999 -rw-rw-r--. 1 zeroco zeroco  0 Jan 29 22:20 bbb
   77000 -rw-rw-r--. 1 zeroco zeroco  0 Jan 29 22:21 cba.txt
   76910 -rw-r--r--. 1 root   root    0 Jan 30 23:19 library.0.1.so
   76912 -rw-r--r--. 1 root   root    0 Jan 30 23:20 library.0.2.so
   76911 lrwxrwxrwx. 1 root   root   13 Jan 30 23:19 library.so -> library0.1.so
51652754 drwxrwxr-x. 2 zeroco zeroco 26 Jan 29 22:24 testDir

```
![image](https://user-images.githubusercontent.com/55049159/215502925-a0bcd068-79c5-488e-8d5c-9bf5d068d51a.png)

