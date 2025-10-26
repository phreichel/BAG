<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/publication">
  <html>
  <head>
  	 <link rel="stylesheet" href="../css/xplain.css"></link>
  </head>
  <body>
    <h1>Reference</h1>
    <xsl:apply-templates/>
  </body>
  </html>
</xsl:template>

<xsl:template match="categories">
	<h2>Categories</h2>
	<table>
		<tr><th>Label</th></tr>
		<xsl:apply-templates select=".//category"/>
	</table>
</xsl:template>

<xsl:template match="category">
		<tr><td><xsl:value-of select="@tag"/></td></tr>
</xsl:template>

</xsl:stylesheet>