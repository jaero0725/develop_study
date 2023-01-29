# chap07. 사용자와 그룹관리 
## 명령어 요약
> useradd : 사용자 및 그룹관리 도구 <br>
> passwd : 사용자 추가 명령어<br>
> usermod : 사용자 비밀번호 변경 및 각종설정<br>
> chage : 사용자 정보변경 <br>
> /etc/login.defs : 사용자 설정 및 UID , GID범위, 비밀번호 관련 설정파일<br>
> groups : 사용자가 속한 그룹 확인<br>
> userdel : 사용자 삭제<br>
> groupadd : 그룹추가<br>
> groupmod : 그룹수정<br>
> groupmems : 그룹 멤버 관리<br>
> groupdel : 그룹삭제<br>
> /etc/sudoers : sudo 관련 설정파일<br>
> sudo : 지정한 사용자 또는 root로 명령어 전환<br>
> su  : 지정한 사용자 또는 root로 전환

- CentOS7부터 system-config-* 툴에 대한 지원이 적어졌다. 그래서 CentOS7 설치 시에 기본적으로 사용자 및 그룹관리 도구가 설치 되지 않는다.
- 다음과 같은 방법으로 system-config-users 패키지를 설치한다.

```shell
[zeroco@localhost /]$ yum install system-config-users
=> 안됨 root계정으로 변경해야됨
Loaded plugins: fastestmirror, langpacks
You need to be root to perform this command.

[zeroco@localhost /]$ su
Password: 
[root@localhost /]# yum install system-config-users
Loaded plugins: fastestmirror, langpacks
You need to be root to perform this command.
[zeroco@localhost /]$ yum install system-config-users
Loaded plugins: fastestmirror, langpacks
You need to be root to perform this command.
[zeroco@localhost /]$ su
Password: 
[root@localhost /]# yum install system-config-users
Loaded plugins: fastestmirror, langpacks
Loading mirror speeds from cached hostfile
 * base: mirror.navercorp.com
 * extras: mirror.navercorp.com
 * updates: mirror.navercorp.com
Resolving Dependencies
--> Running transaction check
---> Package system-config-users.noarch 0:1.3.5-5.el7_9 will be installed
--> Processing Dependency: system-config-users-docs for package: system-config-users-1.3.5-5.el7_9.noarch
--> Running transaction check
---> Package system-config-users-docs.noarch 0:1.0.9-6.el7 will be installed
--> Processing Dependency: rarian-compat for package: system-config-users-docs-1.0.9-6.el7.noarch
--> Running transaction check
---> Package rarian-compat.x86_64 0:0.8.1-11.el7 will be installed
--> Processing Dependency: rarian = 0.8.1-11.el7 for package: rarian-compat-0.8.1-11.el7.x86_64
--> Processing Dependency: rarian for package: rarian-compat-0.8.1-11.el7.x86_64
--> Processing Dependency: librarian.so.0()(64bit) for package: rarian-compat-0.8.1-11.el7.x86_64
--> Running transaction check
---> Package rarian.x86_64 0:0.8.1-11.el7 will be installed
--> Finished Dependency Resolution

Dependencies Resolved

===========================================================================================================================================
 Package                                     Arch                      Version                            Repository                  Size
===========================================================================================================================================
Installing:
 system-config-users                         noarch                    1.3.5-5.el7_9                      updates                    337 k
Installing for dependencies:
 rarian                                      x86_64                    0.8.1-11.el7                       base                        98 k
 rarian-compat                               x86_64                    0.8.1-11.el7                       base                        66 k
 system-config-users-docs                    noarch                    1.0.9-6.el7                        base                       308 k

Transaction Summary
===========================================================================================================================================
Install  1 Package (+3 Dependent packages)

Total download size: 809 k
Installed size: 3.9 M
Is this ok [y/d/N]: y
Downloading packages:
warning: /var/cache/yum/x86_64/7/updates/packages/system-config-users-1.3.5-5.el7_9.noarch.rpm: Header V3 RSA/SHA256 Signature, key ID f4a80eb5: NOKEY
Retrieving key from file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7
Importing GPG key 0xF4A80EB5:
 Userid     : "CentOS-7 Key (CentOS 7 Official Signing Key) <security@centos.org>"
 Fingerprint: 6341 ab27 53d7 8a78 a7c2 7bb1 24c6 a8a7 f4a8 0eb5
 Package    : centos-release-7-9.2009.0.el7.centos.x86_64 (@anaconda)
 From       : /etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7
Is this ok [y/N]: y
Running transaction check
Running transaction test
Transaction test succeeded
Running transaction
  Installing : rarian-0.8.1-11.el7.x86_64                                                                                              1/4 
  Installing : rarian-compat-0.8.1-11.el7.x86_64                                                                                       2/4 
  Installing : system-config-users-docs-1.0.9-6.el7.noarch                                                                             3/4 
  Installing : system-config-users-1.3.5-5.el7_9.noarch                                                                                4/4 
  Verifying  : system-config-users-1.3.5-5.el7_9.noarch                                                                                1/4 
  Verifying  : rarian-compat-0.8.1-11.el7.x86_64                                                                                       2/4 
  Verifying  : rarian-0.8.1-11.el7.x86_64                                                                                              3/4 
  Verifying  : system-config-users-docs-1.0.9-6.el7.noarch                                                                             4/4 

Installed:
  system-config-users.noarch 0:1.3.5-5.el7_9                                                                                               

Dependency Installed:
  rarian.x86_64 0:0.8.1-11.el7          rarian-compat.x86_64 0:0.8.1-11.el7          system-config-users-docs.noarch 0:1.0.9-6.el7         

Complete!


-- 사용자 리스트 확인
[root@localhost /] system-config-users
-- 아래 창이뜸
```
![image](https://user-images.githubusercontent.com/55049159/215313558-7407fbc5-00a3-48dc-8e21-023d133b750c.png)

```shell
-- cat 명령어를 사용하여 같은 시스템 사용자를 제외하고 보는법 
[root@localhost /]# cat /etc/passwd | awk -F: '{if($3>=1000 && $3 <=60000) print $0}'
zeroco:x:1000:1000:ZEROCO01:/home/zeroco:/bin/bash

```

## 사용자 정보 수정
```shell
* 사용자 명 변경 : 로그인한 사용자를 변경할 수 있다. (권장x)
[root@localhost /] # usermod zeroco -l zeroco2
> 사용자 계정을 변경 

* 이름 변경 : 이름은 코멘트를 변경하는 것이다.
[root@localhost /] # usermod zeroco -c "zeroco22"

* 암호변경 
[root@localhost /] passwd zeroco
Changing password for user test.
New password: 
BAD PASSWORD: The password is shorter than 8 characters
Retype new password: 
Sorry, passwords do not match.
New password: 
Retype new password: 
passwd: all authentication tokens updated successfully.

* 홈디렉토리 변경
[root@localhost /] usermod zeroco -d "home/zeroco"

* 로그인 쉘 변경
[root@localhost /] usermod zeroco -s "/bin/csh"

```

