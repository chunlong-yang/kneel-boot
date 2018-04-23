package com.kneel.core.utils;

import java.io.IOException;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.errors.EncodingException;

/**
 * ESAPI (Enterprise Security API)
 * 
 * it is a free, open source, web application security control library that 
 * makes it easier for programmers to write lower-risk applications
 * 
 * this util will be type up all functions of the ESAPI usually.
 * 
 * htmlCodec:  = new HTMLEntityCodec();
 *   1. char in IMMUNE_HTML list, return the original Character.
 *   2. char is alphanumeric [Lookup the hex value of any character that is not alphanumeric.] [1~9,A~Za~z][48~57,65~90,97~122], return original Character.
 *   3. check for illegal characters [?<32, ^\t,\n,\r, 127~159]
 *   4. special charactor [33~48] {!,",#,$,%,&,',(,),*,+,,,-,.,/}, [58~64]{:,;,<,=,>,?,@},[91~96]{[,\,],^,_,`},[123~126]{{,|,},~},[160~]
 * 
 * case one: Encode data for use in HTML using HTML entity encoding.
 *   1. org.owasp.esapi.reference.DefaultSecurityConfiguration: default configuration class, typeName:SecurityConfiguration
 *      (you can re-define in configuration file access to set system property.)
 *   2. invoke above class's getInstance, load -- "ESAPI.properties";
 *      *. set System property[org.owasp.esapi.resources] to redirect this file.
 *         [{custom path}/ESAPI.properties]
 *      *. [{system class path}/.esapi/ESAPI.properties]
 *      *. [{system class path}/esapi/ESAPI.properties]
 *      *. {{user.home}/.esapi/ESAPI.properties]
 *   3. load ESAPI.properties properties,
 * 	ESAPI.encoder():  we get ESAPI's Encoder from configuration.
 *      key: ESAPI.Encoder  -- default: org.owasp.esapi.reference.DefaultEncoder
 *         *. htmlCodec
 *         *. percentCodec
 *         *. javaScriptCodec
 *  ESAPI.encoder().encodeForHTML(html)
 *  this method, we will use htmlCodec, and call encode[ IMMUNE_HTML,input], the IMMUNE_HTML is not include the encode method.
 *  
 *  Character sets that define characters (in addition to alphanumerics) that are
 *  immune from encoding in various formats
	private final static char[] IMMUNE_HTML = { ',', '.', '-', '_', ' ' };
	
 *  public String encodeForHTML(String input) {
	    if( input == null ) {
	    	return null;
	    }
	    return htmlCodec.encode( IMMUNE_HTML, input);	    
	 }
 * 
 * 
 * case two: Encode data for use in HTML attributes.
 * difference of encodeForHTML is IMMNUE list, it can't contains empty space.
 * private final static char[] IMMUNE_HTMLATTR = { ',', '.', '-', '_' };
 * 
 *  public String encodeForHTMLAttribute(String input) {
	    if( input == null ) {
	    	return null;
	    }
	    return htmlCodec.encode( IMMUNE_HTMLATTR, input);
	} 
 * 
 * case three: Encode data for use in an XPath query.
 * private final static char[] IMMUNE_XPATH = { ',', '.', '-', '_', ' ' };
 * equals of encodeForHTML.
 * 
 * public String encodeForXPath(String input) {
	    if( input == null ) {
	    	return null;	
	    }
	    return htmlCodec.encode( IMMUNE_XPATH, input);
	}
 * 
 * case four: Encode data for use in JavaScript.
 * 
 * JavaScript IMMUNE list.
 * private final static char[] IMMUNE_JAVASCRIPT = { ',', '.', '_' };
 * 
 * case five: Encode data for use in css
 * 
 * private final static char[] IMMUNE_CSS = {};
 * only contains alphanumerics, others add \\
 * 
 * case six: Encode data for use in xml
 * 
 * private final static char[] IMMUNE_XML = { ',', '.', '-', '_', ' ' };
 * just alphanumerics ,others "&#x" + Integer.toHexString(c.charValue()) + ";"
 * 
 *  //populate entitites
		entityToCharacterMap = new HashTrie<Character>();
		entityToCharacterMap.put("lt", '<');
		entityToCharacterMap.put("gt", '>');
		entityToCharacterMap.put("amp", '&');
		entityToCharacterMap.put("apos", '\'');
		entityToCharacterMap.put("quot", '"');
 * 
 * case senven: Encode data for use in xml attribute.
 * 
 * difference is no empty.
 * private final static char[] IMMUNE_XMLATTR = { ',', '.', '-', '_' };
 * 
 * 
 * @author e557400
 *
 */
public class ESecurityUtils {
	 

	/**
	 * escape HTML, make special character " to &amp;
	 * 
	 * @param html
	 * @return
	 */
	public static String encodeForHtml(String html){
		return ESAPI.encoder().encodeForHTML(html);
	}
	
	/**
	 * difference of encodeForHTML is IMMNUE list, it can't contains empty space.
	 * 
	 * @param htmlAttribute
	 * @return
	 */
	public static String encodeForHTMLAttribute(String htmlAttribute){
		return ESAPI.encoder().encodeForHTMLAttribute(htmlAttribute);
	}
	
	/**
	 * equals of encodeForHTML
	 * 
	 * @param xpath
	 * @return
	 */
	public static String encodeForXPath(String xpath){
		return ESAPI.encoder().encodeForXPath(xpath);
	}
	
	/**
	 * change \" or \'  ==> 0x22 or 0x27
	 * Does not use backslash character escapes
	 * For example, if a javascript attribute, such as onmouseover, contains a \" that will close the entire attribute and
	 * allow an attacker to inject another script attribute.
	 * 
     * For example:
     * <pre>
     *  &lt;script&gt;
     *    &nbsp;&nbsp;window.setInterval('&lt;%= EVEN IF YOU ENCODE UNTRUSTED DATA YOU ARE XSSED HERE %&gt;');
     *  &lt;/script&gt;
     * </pre>
	 * 
	 * @param javascript
	 * @return
	 */
	public static String encodeForJavaScript(String javascript){
		return ESAPI.encoder().encodeForJavaScript(javascript);
	}
	
	/** 
	 *  only contains alphanumerics, others add \\
	 * 
	 * @param css
	 * @return
	 */
	public static String encodeForCSS(String css){
		return ESAPI.encoder().encodeForCSS(css);
	}
	
	/** 
	 * \' change to \'\'
	 * user data bring placed within an Oracle quoted string such as:
	 *  select * from table where user_name='  USERDATA    '
	 *  
	 * @param css
	 * @return
	 */
	public static String encodeForSQL(String sql){
		OracleCodec codec = new OracleCodec();
		return ESAPI.encoder().encodeForSQL(codec,sql);
	}
	
	/** 
	 * change value < > to &lt; &gt;
	 * 
	 * @param xml
	 * @return
	 */
	public static String encodeForXML(String xml){ 
		return ESAPI.encoder().encodeForXML(xml);
	}
	
	/**
	 * change value < > to &lt; &gt;
	 * 
	 * @param xmlAttribute
	 * @return
	 */
	public static String encodeForXMLAttribute(String xmlAttribute){ 
		return ESAPI.encoder().encodeForXMLAttribute(xmlAttribute);
	}
	
	/**
	 * Encode the URL to UTF-8, which is translate to Web URL.
	 * 
	 *  URLEncoder.encode(input, ESAPI.securityConfiguration().getCharacterEncoding());
	 *  Encryptor.CharacterEncoding: default is UTF-8
	 * 
	 * @param url
	 * @return
	 * @throws EncodingException 
	 */
	public static String encodeForURL(String url) throws EncodingException{
		return ESAPI.encoder().encodeForURL(url);
	}
	
	/**
	 * 
	 *  Decode the URL to UTF-8, which is translate to Web URL.
	 * 
	 *  URLEncoder.decode(input, ESAPI.securityConfiguration().getCharacterEncoding());
	 *  Encryptor.CharacterEncoding: default is UTF-8
	 * 
	 * @param url
	 * @return
	 * @throws EncodingException
	 */
	public static String decodeFromURL(String url) throws EncodingException{
		return ESAPI.encoder().decodeFromURL(url);
	}
	
	/**
	 * Encode the 64 bytes to content.
	 * 
	 * Base64.encodeBytes(input, options)
	 * 
	 * @param bts
	 * @param wrap
	 * @return
	 * @throws EncodingException
	 */
	public static String encodeForBase64(byte[] bts, boolean wrap){
		return ESAPI.encoder().encodeForBase64(bts, wrap);
	}
	
	/**
	 * Decode content to 64 bytes.
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static byte[] decodeFromBase64(String content) throws IOException{
		return ESAPI.encoder().decodeFromBase64(content);
	}
	
	/**
	 * un escape HTML, make &amp to "
	 * 
	 * @param html
	 * @return
	 */
	public static String decodeForHtml(String html){ 
		return ESAPI.encoder().decodeForHTML(html);
	} 
}
