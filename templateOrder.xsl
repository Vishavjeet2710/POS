<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo">
	<xsl:template match="orderXmlRootElement">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simpleA4"
					page-height="29.7cm" page-width="21cm" margin-top="2cm"
					margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:flow flow-name="xsl-region-body">
					<fo:block margin-bottom="1cm">
						<fo:external-graphic
							src="url(file:///D:/increff/repos/pos/logo.jpg)" />
					</fo:block>
					<fo:block>
					</fo:block>
					<fo:block  font-size="16pt"
						font-weight="bold" space-after="5mm">
						<fo:table border-collapse="collapse">
							<fo:table-column column-width="7cm" />
							<fo:table-column column-width="5cm" />
							<fo:table-column column-width="5cm" text-align="end"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block>Order ID :
											<xsl:value-of select="orderId" />
										</fo:block>
									</fo:table-cell>

									<fo:table-cell>
										<fo:block text-align="end">Date :
											<xsl:value-of select="date" />
										</fo:block>
									</fo:table-cell>

									<fo:table-cell>
										<fo:block text-align="end">Time :
											<xsl:value-of select="time" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
					<fo:block text-align="center" font-size="10pt">
						<fo:table border-collapse="collapse" border-style="solid"
							border-width="1pt">
							<fo:table-column column-width="3cm"
								border-style="solid" border-width="1pt" />
							<fo:table-column column-width="5cm"
								border-style="solid" border-width="1pt" />
							<fo:table-column column-width="5cm"
								border-style="solid" border-width="1pt" />
							<fo:table-column column-width="4cm"
								border-style="solid" border-width="1pt" />
							<fo:table-header>
								<fo:table-row font-weight="bold">
									<fo:table-cell border-style="solid"
										border-width="1pt">
										<fo:block>Serial No.</fo:block>
									</fo:table-cell>
									<fo:table-cell border-style="solid"
										border-width="1pt">
										<fo:block>Barcode</fo:block>
									</fo:table-cell>
									<fo:table-cell border-style="solid"
										border-width="1pt">
										<fo:block>Quantity</fo:block>
									</fo:table-cell>
									<fo:table-cell border-style="solid"
										border-width="1pt">
										<fo:block>Mrp</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-header>
							<fo:table-body>
								<xsl:apply-templates select="orderItemDatas" />
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="orderItemDatas">
		<fo:table-row>
			<fo:table-cell>
				<fo:block>
					<xsl:number value="position()" format="1" />
				</fo:block>
			</fo:table-cell>

			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="barcode" />
				</fo:block>
			</fo:table-cell>

			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="quantity" />
				</fo:block>
			</fo:table-cell>

			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="mrp" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
</xsl:stylesheet>