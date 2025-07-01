#/bin/bash
cd /mnt/d/PROJECTS/XPLAIN/
pwd
xsltproc --xinclude --output html/index.html ./xsl/stylesheet.xsl ./xml/index.xml 1> output.log 2> error.log
