<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <style>
                    table,table tr{
                        border:none;
                        border-collapse: 0;
                        border-spacing: 0;
                    }
                </style>
            </head>
            <body>
                <h2>jvm bit code</h2>
                <table border="1">
                    <tr>
                        <th>name</th>
                        <th>ins</th>
                        <th>description</th>
                        <th>hex</th>
                        <th>stack_before</th>
                        <th>stack_after</th>
                    </tr>
                    <xsl:for-each select="instruction/symbol">
                        <xsl:sort select="hex"/>
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="ins"/></td>
                            <td><xsl:value-of select="desc"/></td>
                            <td><xsl:value-of select="hex"/></td>
                            <td><xsl:value-of select="stack_before"/></td>
                            <td><xsl:value-of select="stack_after"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>