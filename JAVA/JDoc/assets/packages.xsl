<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
	<!-- =================================================================== -->
	<xsl:output method="html" indent="yes" encoding="UTF-8"/>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="/">
		<html>
			<head>
				<title>JavaDoc</title>
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
	<xsl:template match="JavaPackages">
		<div><b>Packages</b></div>
		<xsl:apply-templates select="JavaPackage">
			<xsl:sort select="@name"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="JavaPackage">
		<xsl:variable name="path" select="concat('./', translate(@name, '.', '/'), '/index.xml')"/>
		<div><a href="{$path}" target="classes"><xsl:value-of select="@name"/></a></div>
	</xsl:template>
	<!-- =================================================================== -->

</xsl:stylesheet>
