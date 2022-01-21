<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
<xsl:template match="productXmlRootElement">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simpleA4">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="16pt" font-weight="bold"
						space-after="5mm">Product
					</fo:block>
					<fo:block font-size="10pt" space-after="5mm" text-align="center">
						<fo:table border-collapse="collapse" border-style="solid" border-width="1pt">
							<fo:table-column column-width="2cm" border-style="solid" border-width="1pt"/>
							<fo:table-column column-width="3cm" border-style="solid" border-width="1pt"/>
							<fo:table-column column-width="3cm" border-style="solid" border-width="1pt"/>
							<fo:table-column column-width="3cm" border-style="solid" border-width="1pt"/>
							<fo:table-column column-width="3cm" border-style="solid" border-width="1pt"/>
							<fo:table-column column-width="3cm" border-style="solid" border-width="1pt"/>
							<fo:table-header>
								<fo:table-row font-weight="bold">
									<fo:table-cell border-style="solid" border-width="1pt">
										<fo:block>Serial No.</fo:block>	
									</fo:table-cell>
									<fo:table-cell border-style="solid" border-width="1pt">
										<fo:block>Barcode</fo:block>
									</fo:table-cell>
									<fo:table-cell border-style="solid" border-width="1pt">
										<fo:block>Name</fo:block>
									</fo:table-cell>
									<fo:table-cell border-style="solid" border-width="1pt">
										<fo:block>Brand</fo:block>	
									</fo:table-cell>
									<fo:table-cell border-style="solid" border-width="1pt">
										<fo:block>Category</fo:block>
									</fo:table-cell>
									<fo:table-cell border-style="solid" border-width="1pt">
										<fo:block>Mrp</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-header>
							<fo:table-body>
								<xsl:apply-templates select="productDatas" />
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
     </fo:root>
</xsl:template>
<xsl:template match="productDatas">
    <fo:table-row>   
      <fo:table-cell>
        <fo:block>
          <xsl:number value="position()" format="1" />
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="barcode"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="name"/>
        </fo:block>
      </fo:table-cell>    
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="brand"/>
        </fo:block>
      </fo:table-cell>   
      <fo:table-cell>
        <fo:block>
      <xsl:value-of select="category"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="mrp"/>
        </fo:block>
      </fo:table-cell> 
    </fo:table-row>
  </xsl:template>
</xsl:stylesheet>