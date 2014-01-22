<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:import href="copyright.xsl"/>



<xsl:template match="id|property|many-to-one|set" mode="properties">
  <xsl:if test="@type">
  private <xsl:value-of select="@type"/><xsl:text> </xsl:text><xsl:value-of select="@name"/>;<xsl:text/>
  </xsl:if>
  <xsl:if test="@class">
  private <xsl:value-of select="@class"/><xsl:text> </xsl:text><xsl:value-of select="@name"/>;<xsl:text/>
  </xsl:if>
  
</xsl:template>

<xsl:template match="id|property" mode="methods">


</xsl:template>

</xsl:stylesheet>
