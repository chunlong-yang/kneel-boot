package com.kneel.core.utils;
 

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.Velocity;

/**
 * 
 * this StringUtils contains all String process, may be copy from other jar, but
 * have linked it.
 * 
 * Functions:
 * 
 * 1. is(Not)Blank(String str)
 * <li>- checks if a String contains text</li>
 * 2. isNumeric(String str)
 * <li>- checks if a String contains Numeric</li>
 * 3. getFromInputStream(InputStream is)/getFromString(String str)
 * <li>- get String from InputStream [Don't close InputStream]</li>
 * 4. Trim(String str)
 * <li>- removes leading and trailing whitespace</li>
 * 5. Equals/EqualsIgnoreCase
 * <li>- compares two strings null-safe</li>
 * 6. startsWith[ignoreCase]
 * <li>- check if a String starts with a prefix null-safe</li>
 * 7. endsWith[ignoreCase]
 * <li>- check if a String ends with a suffix null-safe</li>
 * 8. IndexOf/LastIndexOf/Contains
 * <li>- null-safe index-of checks</li>
 * 9. substring
 * <li>- null-safe substring extractions</li>
 * 10. Split/join -- tokenizeToStringArray
 * <li>- splits a String into an array of substrings and vice versa</li>
 * <li>- tokenizeToStringArray is provide by spring, can split mutiple splits to array.</li>
 * 11. deleteWhitespace
 * <li>- removes Whitespace of a String</li>
 * 12. capitalize/uncapitalize
 * <li>- changes the case of a String</li>
 * 13. defaultString
 * <li>- protects against a null input String</li>
 * 14. abbreviate
 * <li>- simple words for long words</li>
 * 15. getHtml/getHtmlAll/getHtmlContent/getHtmlContentAll/getHtmlContentAllGroup
 * <li>- use jdk regex to get content of html/xml</li>
 * <li>- this is simple get html content, {HttpClient}</li>
 * 16. escapeHtml/unescapeHtml
 * 17. wrap(sign)
 * 
 * NOTE:this is from my tool box
 * 
 * {@link org.apache.commons.lang3.StringUtils}
 * {@link org.springframework.util.StringUtils}
 * 
 * Ref: 
 *  commons-lang-3.4.jar  org.apache.commons.lang3.StringUtils
 *  commons-io-2.4.jar
 * 
 * @author e557400
 * 
 */
public class StringUtils {
	
	private static Log logger = LogFactory.getLog(StringUtils.class); 
	
	public static String comma=","; //comma separator
	
	/**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}

	

	/**
     * <p>Checks if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <p>Note that the method does not allow for a leading sign, either positive or negative.
     * Also, if a String passes the numeric test, it may still generate a NumberFormatException
     * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
     * for int or long respectively.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
	public static boolean isNumeric(String str) {
		if (isBlank(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * use String pattern to check whether Str contains other charactor
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric1(String str) {
		if (isBlank(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * use Char's ascii to check whether is Number
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric2(String str) {
		if (isBlank(str)) {
			return false;
		}
		char[] chars = str.toCharArray();
		for (char ch : chars) {
			if (ch < 48 || ch > 57)
				return false;
		}
		return true;

	}
	 

	/**
	 * Reader InputStream to String[Don't close InputStream]
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getFromInputStream(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		boolean firstLine = true;
		while ((line = br.readLine()) != null) {
			if (firstLine) {
				sb.append(line);
				firstLine = false;
			} else {
				sb.append(System.getProperty("line.separator"));
				sb.append(line);
			}
		} 

		return sb.toString();
	}
	
	/**
	 * Reader InputStream to String[Don't close InputStream]
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getFromInputStream(InputStream is,String charset) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(is,charset));
		boolean firstLine = true;
		while ((line = br.readLine()) != null) {
			if (firstLine) {
				sb.append(line);
				firstLine = false;
			} else {
				sb.append(System.getProperty("line.separator"));
				sb.append(line);
			}
		}

		return sb.toString();
	}
	
	/**
	 * Reader String to InputStream
	 * 
	 * @param str
	 * @return
	 */
	public static InputStream getFromString(String str){
		InputStream stream = null;
		try {
			stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}
	
	/**
	 * Reader InputString to String[IOUtils],use default "file.encoding"
	 * encoding.[Don't close InputStream]
	 * 
	 * TIP: new InputStreamReader(input, Charsets.toCharset(encoding));
	 * 
	 * other Encoding: "UTF-8" "ISO8859-1" "GBK" "GB2312"
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getFromInputStream1(InputStream is) throws IOException {
		StringBuilder builder = new StringBuilder();
		InputStreamReader in = new InputStreamReader(is, "UTF-8");
		char[] buffer = new char[1024 * 4]; 
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
        	 if (buffer != null) {
                 builder.append(buffer, 0, n);
             }  
        } 
		return builder.toString();
	}
	
	/**
	 * just remove ' '
     * <p>Removes control characters (char &lt;= 32) from both
     * ends of this String, handling <code>null</code> by returning
     * <code>null</code>.</p>
     *
     * <p>The String is trimmed using {@link String#trim()}.
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use {@link #strip(String)}.</p>
     *
     * <p>To trim your choice of characters, use the
     * {@link #strip(String, String)} methods.</p>
     *
     * <pre>
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str  the String to be trimmed, may be null
     * @return the trimmed string, <code>null</code> if null String input
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }
    
    /**
     * <p>Compares two Strings, returning <code>true</code> if they are equal.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered to be equal. The comparison is case sensitive.</p>
     *
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @see java.lang.String#equals(Object)
     * @param str1  the first String, may be null
     * @param str2  the second String, may be null
     * @return <code>true</code> if the Strings are equal, case sensitive, or
     *  both <code>null</code>
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }
    /**
     * <p>Compares two Strings, returning <code>true</code> if they are equal ignoring
     * the case.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered equal. Comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @see java.lang.String#equalsIgnoreCase(String)
     * @param str1  the first String, may be null
     * @param str2  the second String, may be null
     * @return <code>true</code> if the Strings are equal, case insensitive, or
     *  both <code>null</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }
     
    /**
     * remove Whitespace.
     * 
     * <p>Strips whitespace from the start and end of a String.</p>
     *
     * <p>This is similar to {@link #trim(String)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.strip(null)     = null
     * StringUtils.strip("")       = ""
     * StringUtils.strip("   ")    = ""
     * StringUtils.strip("abc")    = "abc"
     * StringUtils.strip("  abc")  = "abc"
     * StringUtils.strip("abc  ")  = "abc"
     * StringUtils.strip(" abc ")  = "abc"
     * StringUtils.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param str  the String to remove whitespace from, may be null
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String strip(String str){
    	 return StringUtils.strip(str);
    }
    
    /**
     * <p>Check if a String starts with a specified prefix.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered to be equal. The comparison is case sensitive.</p>
     *
     * <pre>
     * StringUtils.startsWith(null, null)      = true
     * StringUtils.startsWith(null, "abc")     = false
     * StringUtils.startsWith("abcdef", null)  = false
     * StringUtils.startsWith("abcdef", "abc") = true
     * StringUtils.startsWith("ABCDEF", "abc") = false
     * </pre>
     *
     * @see java.lang.String#startsWith(String)
     * @param str  the String to check, may be null
     * @param prefix the prefix to find, may be null
     * @return <code>true</code> if the String starts with the prefix, case sensitive, or
     *  both <code>null</code>
     * @since 2.4
     */
    public static boolean startsWith(String str, String prefix) {
    	 if (str == null || prefix == null) {
             return (str == null && prefix == null);
         }
    	 if (prefix.length() > str.length()) {
             return false;
         }
        return str.startsWith(prefix);
    }

	/**
	 * use JDK regex to check startsWith
	 * 
	 * @param str
	 * @param prefix
	 * @return
	 */
    public static boolean startsWith1(String str, String prefix) {
    	 if (str == null || prefix == null) {
             return (str == null && prefix == null);
         }
    	 if (prefix.length() > str.length()) {
             return false;
         }
    	String pattern = "^"+prefix+".*$";
    	Pattern p = Pattern.compile(pattern);
    	Matcher m = p.matcher(str); 
        return m.matches();
    }
    
    /**
     * <p>Check if a String ends with a specified suffix.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered to be equal. The comparison is case sensitive.</p>
     *
     * <pre>
     * StringUtils.endsWith(null, null)      = true
     * StringUtils.endsWith(null, "def")     = false
     * StringUtils.endsWith("abcdef", null)  = false
     * StringUtils.endsWith("abcdef", "def") = true
     * StringUtils.endsWith("ABCDEF", "def") = false
     * StringUtils.endsWith("ABCDEF", "cde") = false
     * </pre>
     *
     * @see java.lang.String#endsWith(String)
     * @param str  the String to check, may be null
     * @param suffix the suffix to find, may be null
     * @return <code>true</code> if the String ends with the suffix, case sensitive, or
     *  both <code>null</code>
     * @since 2.4
     */
    public static boolean endsWith(String str, String suffix) {
    	 if (str == null || suffix == null) {
             return (str == null && suffix == null);
         }
    	 if (suffix.length() > str.length()) {
             return false;
         }
        return str.endsWith(suffix);
    }
    
    /**
     * use JDK regex to check endsWith
     * 
     * @param str
     * @param suffix
     * @return
     */
    public static boolean endsWith1(String str, String suffix) {
   	 if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
   	 if (suffix.length() > str.length()) {
            return false;
        }
   	 String pattern = "^.*"+suffix+"$";
   	 Pattern p = Pattern.compile(pattern);
   	 Matcher m = p.matcher(str); 
   	 return m.matches();
   }
    
    /**
     * <p>Finds the first index within a String, handling <code>null</code>.
     * This method uses {@link String#indexOf(String)}.</p>
     *
     * <p>A <code>null</code> String will return <code>-1</code>.</p>
     *
     * <pre>
     * StringUtils.indexOf(null, *)          = -1
     * StringUtils.indexOf(*, null)          = -1
     * StringUtils.indexOf("", "")           = 0
     * StringUtils.indexOf("", *)            = -1 (except when * = "")
     * StringUtils.indexOf("aabaabaa", "a")  = 0
     * StringUtils.indexOf("aabaabaa", "b")  = 2
     * StringUtils.indexOf("aabaabaa", "ab") = 1
     * StringUtils.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str  the String to check, may be null
     * @param searchStr  the String to find, may be null
     * @return the first index of the search String,
     *  -1 if no match or <code>null</code> string input
     * @since 2.0
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }
    
    /**
     * use JDK regex to check indexOf [just the searchStr is pattern.]
     * 
     * @param str
     * @param searchStr
     * @return
     */
    public static int indexOf1(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        String pattern = searchStr;
      	Pattern p = Pattern.compile(pattern);
      	Matcher m = p.matcher(str); 
      	if(m.find()){
      		return m.start();
      	}
      	return -1;
    }
    
    /**
     * use JDK regex group to check indexOf [just the searchStr is pattern.]
     * 
     * @param str
     * @param searchStr
     * @return
     */
    public static int indexOf2(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        String pattern =  "("+searchStr+").*";
      	Pattern p = Pattern.compile(pattern);
      	Matcher m = p.matcher(str); 
      	while(m.find()){ 
      		if(logger.isDebugEnabled()){
      			logger.debug(m.group()+":"+m.start()+":"+m.end()+":("+m.groupCount()+")");
      			logger.debug(m.group(1)+":"+m.start(1)+":"+m.end(1)+":("+m.groupCount()+")");
      		} 
      		return m.start();
      	}
      	return -1;
    }
    
    /**
     * <p>Finds the last index within a String, handling <code>null</code>.
     * This method uses {@link String#lastIndexOf(String)}.</p>
     *
     * <p>A <code>null</code> String will return <code>-1</code>.</p>
     *
     * <pre>
     * StringUtils.lastIndexOf(null, *)          = -1
     * StringUtils.lastIndexOf(*, null)          = -1
     * StringUtils.lastIndexOf("", "")           = 0
     * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
     * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
     * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
     * StringUtils.lastIndexOf("aabaabaa", "")   = 8
     * </pre>
     *
     * @param str  the String to check, may be null
     * @param searchStr  the String to find, may be null
     * @return the last index of the search String,
     *  -1 if no match or <code>null</code> string input
     * @since 2.0
     */
    public static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr);
    }
    
    /**
     * use JDK regex to check lastindexOf [just the searchStr is pattern.]
     * 
     * @param str
     * @param searchStr
     * @return
     */
    public static int lastIndexOf1(String str, String searchStr) {
    	 if (str == null || searchStr == null) {
             return -1;
         }
    	String pattern = ".*("+searchStr+")";
       	Pattern p = Pattern.compile(pattern); 
       	Matcher m = p.matcher(str); 
       	while(m.find()){ 
       		if(logger.isDebugEnabled()){
      			logger.debug(m.group()+":"+m.start()+":"+m.end()+":("+m.groupCount()+")");
      			logger.debug(m.group(1)+":"+m.start(1)+":"+m.end(1)+":("+m.groupCount()+")");
      		}
       		 return m.start(1);
       	}
       	return -1;
    }
    
    /**
     * <p>Checks if String contains a search character, handling <code>null</code>.
     * This method uses {@link String#indexOf(int)}.</p>
     *
     * <p>A <code>null</code> or empty ("") String will return <code>false</code>.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @param searchChar  the character to find
     * @return true if the String contains the search character,
     *  false if not or <code>null</code> string input
     * @since 2.0
     */
    public static boolean contains(String str, char searchChar) {
        if (isBlank(str)) {
            return false;
        }
        return str.indexOf(searchChar) >= 0;
    }
      
    /**
     * <p>Gets a substring from the specified String avoiding exceptions.</p>
     *
     * <p>A negative start position can be used to start <code>n</code>
     * characters from the end of the String.</p>
     *
     * <p>A <code>null</code> String will return <code>null</code>.
     * An empty ("") String will return "".</p>
     *
     * <pre>
     * StringUtils.substring(null, *)   = null
     * StringUtils.substring("", *)     = ""
     * StringUtils.substring("abc", 0)  = "abc"
     * StringUtils.substring("abc", 2)  = "c"
     * StringUtils.substring("abc", 4)  = ""
     * StringUtils.substring("abc", -2) = "bc"
     * StringUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param str  the String to get the substring from, may be null
     * @param start  the position to start from, negative means
     *  count back from the end of the String by this many characters
     * @return substring from start position, <code>null</code> if null String input
     */
    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return "";
        }

        return str.substring(start);
    }
    
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives, which means last n characters
        if (end < 0) {
        	end = str.length() + end; // remember start is negative
        }

        if (end < 0) {
        	end = 0;
        }
        if (end < start) {
            return "";
        }

        return str.substring(start,end);
    }
    
	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * Trims tokens and omits empty tokens.
	 * <p>The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using {@code delimitedListToStringArray}
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String
	 * (each of those characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}
    
    
	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using {@code delimitedListToStringArray}
	 * @param str the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String
	 * (each of those characters is individually considered as delimiter)
	 * @param trimTokens trim the tokens via String's {@code trim}
	 * @param ignoreEmptyTokens omit empty tokens from the result array
	 * (only applies to tokens that are empty after trimming; StringTokenizer
	 * will not consider subsequent delimiters as token in the first place).
	 * @return an array of the tokens ({@code null} if the input String
	 * was {@code null})
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(
			String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}
	
	/**
	 * Copy the given Collection into a String array.
	 * The Collection must contain String elements only.
	 * @param collection the Collection to copy
	 * @return the String array ({@code null} if the passed-in
	 * Collection was {@code null})
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}
    
    /**
     * <p>Splits the provided text into an array, using whitespace as the
     * separator.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
     * </pre>
     *
     * @param str  the String to parse, may be null
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    public static String[] split(String str) { 
    	return StringUtils.split(str,comma);
    }
    
    /**
     * <p>Splits the provided text into an array, separator specified.
     * This is an alternative to using StringTokenizer.</p>
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     *
     * @param str  the String to parse, may be null
     * @param separatorChar  the character used as the delimiter
     * @return an array of parsed Strings, <code>null</code> if null String input
     * @since 2.0
     */
    public static String[] split(String str, char separatorChar) {
    	return StringUtils.split(str,separatorChar);
    }
    
    /**
     * <p>Splits the provided text into an array, separators specified.
     * This is an alternative to using StringTokenizer.</p>
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.
     * A <code>null</code> separatorChars splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str  the String to parse, may be null
     * @param separatorChars  the characters used as the delimiters,
     *  <code>null</code> splits on whitespace
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    public static String[] split(String str, String separatorChars) {
    	return StringUtils.split(str,separatorChars);
    }
    
    /**
     * <p>Splits the provided text into an array, separator specified,
     * preserving all tokens, including empty tokens created by adjacent
     * separators. This is an alternative to using StringTokenizer.</p>
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as separators for empty tokens.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.splitPreserveAllTokens(null, *)         = null
     * StringUtils.splitPreserveAllTokens("", *)           = []
     * StringUtils.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
     * StringUtils.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
     * StringUtils.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
     * StringUtils.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
     * StringUtils.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
     * </pre>
     *
     * @param str  the String to parse, may be <code>null</code>
     * @param separatorChar  the character used as the delimiter,
     *  <code>null</code> splits on whitespace
     * @return an array of parsed Strings, <code>null</code> if null String input
     * @since 2.1
     */
//    public static String[] splitTokens(String str, String separatorChars) {
//    	return org.apache.commons.lang.StringUtils.splitPreserveAllTokens(str,separatorChars);
//    }
//     

    /**
	 * Splits the provided text into an array, separator specified, preserving
	 * all tokens, including empty tokens created by adjacent separators.
	 *
	 * CSVUtil.split(null, *, true) = null
	 * CSVUtil.split("", *, , true) = []
	 * CSVUtil.split("a.b.c", '.', true) = ["a", "b", "c"]
	 * CSVUtil.split("a...c", '.', true) = ["a", "", "", "c"]
	 * CSVUtil.split("a...c", '.', false) = ["a", "c"]
	 *
	 * @param str
	 *            the string to parse
	 * @param separatorChar
	 *            the seperator char
	 * @param preserveAllTokens
	 *            if true, adjacent separators are treated as empty token
	 *            separators
	 * @return the splitted string
	 */
	public static String[] splitPreserveAllTokens(String str, String separatorChars, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (separatorChars.indexOf(str.charAt(i))>=0) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					list.add(String.valueOf(str.charAt(i)));//add token
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			}
			lastMatch = false;
			match = true;
			i++;
		}
		if (match || preserveAllTokens && lastMatch) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}

    
    /**
     * append Array's all element together, system.out is a good usage.
     * 
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No separator is added to the joined String.
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null)            = null
     * StringUtils.join([])              = ""
     * StringUtils.join([null])          = ""
     * StringUtils.join(["a", "b", "c"]) = "abc"
     * StringUtils.join([null, "", "a"]) = "a"
     * </pre>
     *
     * @param array  the array of values to join together, may be null
     * @return the joined String, <code>null</code> if null array input
     * @since 2.0
     */
//    public static String join(Object[] array) {
//    	return org.apache.commons.lang.StringUtils.join(array);
//    }
    
    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null array input
     */
//    public static String join(Object[] array, String separator) {
//    	return org.apache.commons.lang.StringUtils.join(array,separator);
//    }
    
    /**
     * <p>Deletes all whitespaces from a String as defined by
     * {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.deleteWhitespace(null)         = null
     * StringUtils.deleteWhitespace("")           = ""
     * StringUtils.deleteWhitespace("abc")        = "abc"
     * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str  the String to delete whitespace from, may be null
     * @return the String without whitespaces, <code>null</code> if null String input
     */
    public static String deleteWhitespace(String str) {
    	 if (isBlank(str)) {
             return str;
         }
         int sz = str.length();
         char[] chs = new char[sz];
         int count = 0;
         for (int i = 0; i < sz; i++) {
             if (!Character.isWhitespace(str.charAt(i))) {
                 chs[count++] = str.charAt(i);
             }
         }
         if (count == sz) {
             return str;
         }
         return new String(chs, 0, count);
     }
    
    /**
     * change first letter to Uppercase
     * 
     * <p>Capitalizes a String changing the first letter to title case as
     * per {@link Character#toTitleCase(char)}. No other letters are changed.</p>
     *
     * <p>For a word based algorithm, see { #capitalize(String)}.
     * A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.capitalize(null)  = null
     * StringUtils.capitalize("")    = ""
     * StringUtils.capitalize("cat") = "Cat"
     * StringUtils.capitalize("cAt") = "CAt"
     * </pre>
     *
     * @param str  the String to capitalize, may be null
     * @return the capitalized String, <code>null</code> if null String input
     * @see  #capitalize(String)
     * @see #uncapitalize(String)
     * @since 2.0
     */
    public static String capitalize(String str) { 
        if (str == null || str.length() == 0) {
            return str;
        }
        return new StringBuffer().append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString(); 
    }
    
    /**
     * change first letter to Lowercase.
     * 
     * <p>Uncapitalizes a String changing the first letter to title case as
     * per {@link Character#toLowerCase(char)}. No other letters are changed.</p>
     *
     * <p>For a word based algorithm, see {@link  #uncapitalize(String)}.
     * A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtils.uncapitalize(null)  = null
     * StringUtils.uncapitalize("")    = ""
     * StringUtils.uncapitalize("Cat") = "cat"
     * StringUtils.uncapitalize("CAT") = "cAT"
     * </pre>
     *
     * @param str  the String to uncapitalize, may be null
     * @return the uncapitalized String, <code>null</code> if null String input
     * @see  #uncapitalize(String)
     * @see #capitalize(String)
     * @since 2.0
     */
    public static String uncapitalize(String str) { 
        if (str == null || str.length() == 0) {
            return str;
        }
        return new StringBuffer().append(str.substring(0, 1).toLowerCase()).append(str.substring(1)).toString(); 
    }
    
    /**
     * <p>Checks if the String contains only unicode letters.</p>
     *
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = false
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains letters, and is non-null
     */
    public static boolean isAlpha(String str) {
        if (isBlank(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetter(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * <p>Checks if the String contains only whitespace.</p>
     *
     * <p><code>null</code> will return <code>false</code>.
     * An empty String (length()=0) will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains whitespace, and is non-null
     * @since 2.0
     */
    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * thougth it is easy , but it is usage in application normalrize.
     * 
     * <p>Returns either the passed in String,
     * or if the String is <code>null</code>, an empty String ("").</p>
     *
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     * 
     * @see String#valueOf(Object)
     * @param str  the String to check, may be null
     * @return the passed in String, or the empty String if it
     *  was <code>null</code>
     */
    public static String defaultString(String str) {
        return str == null ? "" : str;
    }
    
    /**
     * <p>Abbreviates a String using ellipses. This will turn
     * "Now is the time for all good men" into "Now is the time for..."</p>
     *
     * <p>Specifically:
     * <ul>
     *   <li>If <code>str</code> is less than <code>maxWidth</code> characters
     *       long, return it.</li>
     *   <li>Else abbreviate it to <code>(substring(str, 0, max-3) + "...")</code>.</li>
     *   <li>If <code>maxWidth</code> is less than <code>4</code>, throw an
     *       <code>IllegalArgumentException</code>.</li>
     *   <li>In no case will it return a String of length greater than
     *       <code>maxWidth</code>.</li>
     * </ul>
     * </p>
     *
     * <pre>
     * StringUtils.abbreviate(null, *)      = null
     * StringUtils.abbreviate("", 4)        = ""
     * StringUtils.abbreviate("abcdefg", 6) = "abc..."
     * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 4) = "a..."
     * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
     * </pre>
     *
     * @param str  the String to check, may be null
     * @param maxWidth  maximum length of result String, must be at least 4
     * @return abbreviated String, <code>null</code> if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 2.0
     */
//    public static String abbreviate(String str, int maxWidth) {
//        return org.apache.commons.lang.StringUtils.abbreviate(str, maxWidth);
//    }
    
    /**
	 * get xml data with JDK regex.
	 * 
	 * html format exp:
	 * 
	 * 1. "<title>.*?</title>";
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static String getHtml(String input,String regex){
		List<String> htmls = new ArrayList<String>();
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(input);
		while(ma.find()){
			htmls.add(ma.group());
		}
		return htmls.get(0);
	}
    
    /**
	 * get xml data with JDK regex.
	 * 
	 * html format exp:
	 * 
	 * 1. "<title>.*?</title>";
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static List<String> getHtmlAll(String input,String regex){
		List<String> htmls = new ArrayList<String>();
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(input);
		while(ma.find()){
			htmls.add(ma.group());
		}
		return htmls;
	}
	
	 /**
	 * get xml data with JDK regex.
	 * 
	 * html format exp:
	 * 
	 * 1. "<title>.*?</title>";
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static String getHtmlContent(String input,String regex){
		List<String> htmls = new ArrayList<String>();
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(input);
		while(ma.find()){
			htmls.add(ma.group(1));
		}
		return htmls.get(0);
	}
	
	 /**
	 * get xml data with JDK regex.
	 * 
	 * html format exp:
	 * 
	 * 1. "<title>.*?</title>";
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static List<String> getHtmlContentAll(String input,String regex){
		List<String> htmls = new ArrayList<String>();
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(input);
		while(ma.find()){
			htmls.add(ma.group(1));
		}
		return htmls;
	}
	
	 /**
	 * get xml data with JDK regex.
	 * 
	 * html format exp:
	 * 
	 * 1. "<title>.*?</title>";
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static Map<Integer,List<String>> getHtmlContentAllGroup(String input,String regex){
		Map<Integer,List<String>> htmls = new HashMap<Integer,List<String>>();
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(input);
		int regroup = 0;
		while(ma.find()){
			int groupindex = 1; 
			while(groupindex<=ma.groupCount()){ 
				List<String> groupvs =  htmls.get(regroup);
				if(groupvs == null){
					groupvs = new ArrayList<String>();
					htmls.put(regroup, groupvs);
				}
				groupvs.add(ma.group(groupindex));
				groupindex++;
			}
			regroup ++;
		}
		return htmls;
	}
	
	/**
	 * camel String
	 * 
	 * 1. abc_dd_ed  ==>  abcDdEd
	 * 2. _dd_ed     ==>  DdEd(special)
	 * 3. dd_ed_     ==>  ddEd (not support for this.)
	 * 
	 * @param name
	 * @return
	 */
	public static String camel(String name){
		name = name.toLowerCase();
		StringBuffer sb = new StringBuffer();
		String slits[] = name.split("_");
		int index = 0;
		for(String slit:slits){
			if(index == 0){
				sb.append(slit);
			}else{
				sb.append(slit.substring(0, 1).toUpperCase()).append(slit.substring(1));
			} 
			index++;
		}
		return sb.toString();
	} 
	
	/**
	 * ocamel String
	 * 
	 * 1. abcDdEd  ==>  abc_dd_ed
	 * 2. DdEd     ==>  _dd_ed(special)
	 * 3. ddEd     ==>  dd_ed
	 * 
	 * @param name
	 * @return
	 */
	public static String ocamel(String camelname){
		StringBuffer sb = new StringBuffer();  
		for(char slit:camelname.toCharArray()){ 
			if(Character.isUpperCase(slit)){
				sb.append("_");
			} 
			sb.append(Character.toLowerCase(slit));  
		}
		return sb.toString();
	}
	
	public static String escapeHtml(String html){
		return null;//escapeHtml4(html);
	}
 
	public static String unescapeHtml(String html){
		return null;//StringEscapeUtils.unescapeHtml4(html);
	}
	
//	public static String escapeEsv(String esv){
//		return org.apache.commons.lang.StringEscapeUtils.escapeCsv(esv);
//	}
//	
//	public static String unescapeEsv(String esv){
//		return org.apache.commons.lang.StringEscapeUtils.unescapeCsv(esv);
//	}
	
	public static String escapeXml(String xml){
		return null;//escapeXml10(xml);
	}
	
//	public static String unescapeXml(String xml){
//		return org.apache.commons.lang.StringEscapeUtils.unescapeXml(xml);
//	}
	
    /**
     * <p>
     * Wraps a String with another String.
     * </p>
     * 
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     * 
     * <pre>
     * StringUtils.wrap(null, *)         = null
     * StringUtils.wrap("", *)           = ""
     * StringUtils.wrap("ab", null)      = "ab"
     * StringUtils.wrap("ab", "x")       = "xabx"
     * StringUtils.wrap("ab", "\"")      = "\"ab\""
     * StringUtils.wrap("\"ab\"", "\"")  = "\"\"ab\"\""
     * StringUtils.wrap("ab", "'")       = "'ab'"
     * StringUtils.wrap("'abcd'", "'")   = "''abcd''"
     * StringUtils.wrap("\"abcd\"", "'") = "'\"abcd\"'"
     * StringUtils.wrap("'abcd'", "\"")  = "\"'abcd'\""
     * </pre>
     * 
     * @param str
     *            the String to be wrapper, may be null
     * @param wrapWith
     *            the String that will wrap str
     * @return wrapped String, {@code null} if null String input
     * @since 3.4
     */
    public static String wrap(final String str, final String wrapWith) {

        if (isBlank(str)) {
            return wrapWith.concat(wrapWith);
        }

        return wrapWith.concat(str).concat(wrapWith);
    }
    
    public static Map<String,String> htmlTagCache = new HashMap<String,String>();
    
    /**
     * <p>
     * Wraps a String with another String.
     * </p>
     * 
     * <p>
     * A {@code null} input String returns {@code null}.
     * </p>
     * 
     * <pre>
     * StringUtils.wrap(null, *)         = null
     * StringUtils.wrap("", *)           = ""
     * StringUtils.wrap("ab", null)      = "ab"
     * StringUtils.wrap("ab", "x")       = "xabx"
     * StringUtils.wrap("ab", "\"")      = "\"ab\""
     * StringUtils.wrap("\"ab\"", "\"")  = "\"\"ab\"\""
     * StringUtils.wrap("ab", "'")       = "'ab'"
     * StringUtils.wrap("'abcd'", "'")   = "''abcd''"
     * StringUtils.wrap("\"abcd\"", "'") = "'\"abcd\"'"
     * StringUtils.wrap("'abcd'", "\"")  = "\"'abcd'\""
     * </pre>
     * 
     * @param str
     *            the String to be wrapper, may be null
     * @param wrapWith
     *            the String that will wrap str
     * @return wrapped String, {@code null} if null String input
     * @since 3.4
     */
    public static String wrapHtml(final String str, final String tag) {
    	String htmlTag = htmlTagCache.get(tag);
    	if(htmlTag == null){
    		htmlTag = "<"+tag+">|</"+tag+">";
    		htmlTagCache.put(tag, htmlTag);
    	}
    	String startTag = htmlTag.split("\\|")[0];
    	String endTag = htmlTag.split("\\|")[1];
    	
        if (isBlank(str)) {
            return startTag;
        }

        return startTag.concat(str).concat(endTag);
    }
    
	
	/**
	 * errors.range=${name} is not in the range ${who} through ${there}.
	 * 
	 *  [name="ycl",who="who",there="kdd"]
	 * 
	 * @return
	 */
//	public static String placeMap(String source,Map<String,String> map){
//		return StrSubstitutor.replace(source, map);
//	}
	
	/**
	 * only replace once of the string with placeholder and replacement.
	 * 
	 * @param template
	 * @param placeholder
	 * @param replacement
	 * @return
	 */
	public static String replaceOnce(String template, String placeholder, String replacement) {
		if ( template == null ) {
			return null;  // returnign null!
		}
		int loc = template.indexOf( placeholder );
		if ( loc < 0 ) {
			return template;
		}
		else {
			return template.substring( 0, loc ) + replacement + template.substring( loc + placeholder.length() );
		}
	}
	
	
//	/**
//	 * Velocity
//	 * 
//	 * @param str
//	 * @param namedMap
//	 * @return
//	 */
//	public static String evaluate(String str, Map<String,String> namedMap){
//		VelocityContext context = new VelocityContext(); 
//		for(Map.Entry<String, String> paramentity:namedMap.entrySet()){
//			context.put(paramentity.getKey(), paramentity.getValue());
//		}
//		StringWriter sw = new StringWriter();
//		Velocity.evaluate(context, sw, "NamedSQL", str);
//		return sw.toString().replaceAll("[\\t\\n\\r]", "").trim();
//	}
}
