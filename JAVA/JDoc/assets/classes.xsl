<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
	<!-- =================================================================== -->
	<xsl:output method="html" indent="yes" encoding="UTF-8"/>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="/">
		<html>
			<head>
				<title>JavaDoc - Package <xsl:value-of select="JavaPackage/@name"/></title>
				<meta charset="UTF-8"/>
				<style type="text/css">
					li {
						list-style: none;
					}
				</style>
			</head>
			<body>
				<xsl:apply-templates/>
			</body>
		</html>
	</xsl:template>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="JavaPackage">
		<div><b>Classes</b></div>
		<xsl:apply-templates select="JavaClass">
			<xsl:sort select="@simpleName"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="JavaClass">
		<xsl:variable name="path" select="concat('./', @simpleName , '.xml')"/>
		<div><a href="{$path}" target="main"><xsl:value-of select="@simpleName"/></a></div>
	</xsl:template>
	<!-- =================================================================== -->

</xsl:stylesheet>
