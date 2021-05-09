/* 
 * Copyright (c) 2021 FXCM, LLC.
 * 55 Water Street 50 Floor, New York NY, 10041 USA
 *
 * THIS SOFTWARE IS THE CONFIDENTIAL AND PROPRIETARY INFORMATION OF
 * FXCM, LLC. ("CONFIDENTIAL INFORMATION"). YOU SHALL NOT DISCLOSE
 * SUCH CONFIDENTIAL INFORMATION AND SHALL USE IT ONLY IN ACCORDANCE
 * WITH THE TERMS OF THE LICENSE AGREEMENT YOU ENTERED INTO WITH
 * FXCM.
 *
 * $History: $
 *
 *  May 9, 2021 YShikalenko  #20916 FXTR - Bridger Integration (2021)
 */

package org.shikalenko.xprmntl.srv;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author yshikalenko
 *
 */
public class SrvTest {
    
    private static final String SYSTEM_PROP_URL = "test.url";
    private static final String SYSTEM_PROP_URL_DEFAULT = "http://srv-fxtr.gehtsoft.com:8073/xprmntl/app/index.html";
    private static final String URl = System.getProperty(SYSTEM_PROP_URL, SYSTEM_PROP_URL_DEFAULT); 

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testForm() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(URl);
            assertEquals("Welcome to XPRMTL", page.getTitleText());
            

            final String pageAsXml = page.asXml();
            System.out.println(pageAsXml);
            
            assertTrue(pageAsXml.contains("<body class=\"composite\">"));
            
            assertTrue(pageAsXml.contains("<td id=\"_spouse_name_label\">"));
            
            final HtmlElement spouseName = page.getHtmlElementById("_spouse_name_row");
            assertFalse(spouseName.isDisplayed());
            
            final HtmlElement maritalSingle =  page.getHtmlElementById("_marital_status_single");
            
            maritalSingle.click();
            
            assertFalse(spouseName.isDisplayed());

            System.out.println("=====================================");

            System.out.println(page.asNormalizedText());

            final HtmlElement maritalMarried =  page.getHtmlElementById("_marital_status_married");

            maritalMarried.click();
            
            assertTrue(spouseName.isDisplayed());

            System.out.println("=====================================");
            
            System.out.println(page.asNormalizedText());
            
        }
    }

}
