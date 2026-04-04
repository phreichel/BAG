<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
	<!-- =================================================================== -->
	<xsl:output method="html" indent="yes" encoding="UTF-8"/>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="/">
		<html>
			<head>
				<title>JavaDoc - <xsl:value-of select="/JavaClass/@name"/></title>
				<meta charset="UTF-8"/>
				<style type="text/css">
					hr {
						color: #f0f0f0;
					}
					span.red {
						color: red;
						font-weight: bold;
						margin-left: 2pt;
						margin-right: 2pt;
					}
					span.blue {
						color: blue;
						font-weight: bold;
						margin-left: 2pt;
						margin-right: 2pt;
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
	<xsl:template match="JavaClass">
		
		<div>class <span class="red"><xsl:value-of select="@simpleName"/></span></div>
		<div>package: <span class="blue"><xsl:value-of select="@package"/></span></div>

		<hr/>
		<div><b>Method Overview</b></div>
		<table>
			<xsl:apply-templates select="JavaMethod">
				<xsl:sort select="@name"/>
			</xsl:apply-templates>
		</table>
		<hr/>
		<div><b>Method Detail</b></div>
		<xsl:apply-templates select="JavaMethod" mode="detail">
			<xsl:sort select="@name"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="JavaMethod">

		<tr>
			<td style="text-align: right;"><span class="red" title="{@return}"><xsl:value-of select="@simpleReturn"/></span></td>
			<td><a href="#{@name}" class="blue"><xsl:value-of select="@name"/></a>( <xsl:apply-templates/> )</td>
		</tr>

	</xsl:template>
	<!-- =================================================================== -->


	<!-- =================================================================== -->
	<xsl:template match="JavaParameter">
		<span class="red" title="{@type}"><xsl:value-of select="@simpleType"/></span>
		<b><xsl:value-of select="@name"/></b>
	</xsl:template>
	<!-- =================================================================== -->

	<!-- =================================================================== -->
	<xsl:template match="JavaMethod" mode="detail">
		<hr/>
		<a name="{@name}"/>
		<table>
			<tr>
				<td colspan="4"><xsl:value-of select="@name"/></td>
			</tr>
			<xsl:apply-templates select="JavaParameter" mode="detail"/>
		</table>
	</xsl:template>
	<!-- =================================================================== -->

	<!-- =================================================================== -->
	<xsl:template match="JavaParameter" mode="detail">
		<tr>
			<td></td>
			<td><xsl:value-of select="@name"/></td>
			<td><xsl:value-of select="@type"/></td>
			<td></td>
		</tr>
	</xsl:template>
	<!-- =================================================================== -->

</xsl:stylesheet>
