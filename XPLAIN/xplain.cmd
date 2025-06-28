@ECHO OFF
ubuntu run /mnt/d/PROJECTS/XPLAIN/xplain.sh %1 > "D:\PROJECTS\XPLAIN\xplain.log"
START "C:\Program Files\Google\Chrome\Application\chrome.exe" "D:\PROJECTS\XPLAIN\html\index.html"
