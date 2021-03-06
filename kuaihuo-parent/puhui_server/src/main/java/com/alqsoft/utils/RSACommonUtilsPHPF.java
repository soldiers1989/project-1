package com.alqsoft.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.alqframework.security.RSAUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月30日
 *
 */
public class RSACommonUtilsPHPF {
	
	private static Logger logger = LoggerFactory.getLogger(RSACommonUtilsPHPF.class);

	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiahhmGlsiwtIgRNX0HgHDeqsmCPtGqZ3fiig5TLv9Qqj/On4yCSk/xx+I2esiNT1z0WhWqTnN9UbbyfmmFpqhXcpC8PyLXrbPcK/F7jldGreXtiTfBcBFyIQ33rCL7AE4mrcYnz7yYc036db2fzenWDlAR7srJUQ8xFZ6ZzVRZwIDAQAB";

	private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKJqGGYaWyLC0iBE1fQeAcN6qyYI+0apnd+KKDlMu/1CqP86fjIJKT/HH4jZ6yI1PXPRaFapOc31RtvJ+aYWmqFdykLw/Itets9wr8XuOV0at5e2JN8FwEXIhDfesIvsATiatxifPvJhzTfp1vZ/N6dYOUBHuyslRDzEVnpnNVFnAgMBAAECgYB8itzg1s8R7dERu5dRVf1GN/er9/Evs3uDYj8MCWKSh55t8R26IrpF1NzCpVCgb7EK8gaPhb70x2QpwEy25th1McVSPp4eBxyBnHxauBALfNbkjd+YesOJxwap5W1rszYCgIdfeoFYX2aQ+zYdBG3287OGJ1rTL+GHJ5jOQXTUSQJBANmBCM52H7E3Pq04CIbDM/b/E9Ldjb1rH2tjMFpmEXiGx2AOlrqEckLbiK9i39o0WS2pkJxFXbsjMDlR1fiXFzMCQQC/KPypGNmcUYwp51AqdO9x+70tJW4nVA/kJM880KthizFiDleIP1pn8mFjQt0gDx8cAVbypOTUA83Vw01T/wz9AkBvRqWzwxk5uDLXuhgaohme3ydMNU9Dvl9nuGpPhXKgwAuWqEATEpnGfLx0RliSMUACrYKvgImQqVMNsEGRGfOjAkEAjpSRr8abOR8R/odLof9KQahUuAG9IZRvrelOsbuquLzLfqnX3AX09qcwwIq0yB6/Q3I0WhNnokOcGZ7sZIROOQJBAI4PjSfxw1zyFwWk9xf2B6ta8ldwptwgnJ1FRHmPkhRLQUSZ3rJpOYvsiUfkIv1dQ8X0rpyjSR/XZONg68WzFjs=";
	
	public static enum CharSet {
		UTF8 {
			public String getName() {
				return "UTF-8";
			}
		};
		public abstract String getName();
	}
	
	/**
	 * 创建公钥  私钥
	 * @return
	 */
	public static Map<String, Object> createRSAKey() {
		Map<String, Object> keyMap = null;
		try {
			keyMap = RSAUtils.genKeyPair();
			logger.info("公钥-->"+RSAUtils.getPublicKey(keyMap));
			logger.info("私钥-->"+RSAUtils.getPrivateKey(keyMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取公私钥失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}
		return keyMap;
	}

	/**
	 * 根据公钥加密
	 * @param content 需加密内容
	 * @param charSet 字符集编码格式
	 * @return
	 */
	public static String encryptByPublicKey(String content, CharSet charSet) {
		byte[] b=null;
		try {
			b = encrypt(content.getBytes(charSet.getName()),true);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return bytesToString(b);
	}
	
	/**
	 * 根据私钥加密
	 * @param content
	 * @param charSet
	 * @return
	 */
	public static String encryptByPrivateKey(String content, CharSet charSet) {
		byte[] b=null;
		try {
			b = encrypt(content.getBytes(charSet.getName()),false);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return bytesToString(b);
	}

	/**
	 * 根据私钥解密字符串
	 * @param content  学解密内容
	 * @param charSet  字符集编码格式
	 * @return
	 */
	public  static String decryptByPrivateKey(String content, CharSet charSet) {
 
		byte[] b = decrypt(stringToBytes(content),false);
		try {
			return new String(b, charSet.getName());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 根据公钥解密字符串
	 * @param content
	 * @param charSet
	 * @return
	 */
	public  static String decryptByPublicKey(String content, CharSet charSet) {
		 
		byte[] b = decrypt(stringToBytes(content),true);
		try {
			return new String(b, charSet.getName());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return null;
	}

	/**
	 * 字符串转换成为字节数组
	 * @param decrytString
	 * @return
	 */
	private  static byte[] stringToBytes(String decrytString) {
		 
		char[] charHex=decrytString.toCharArray();
		byte[] clone=null;
		try {
			clone = Hex.decodeHex(charHex);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			logger.info("字符串转换字节数组失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}		
		return clone;
	}

	/**
	 * 字节数组转换为字符串
	 * @param decrytString
	 * @return
	 */
	private  static String bytesToString(byte[] encrytpByte) {
		char[] charHex=Hex.encodeHex(encrytpByte);
		return new String(charHex);
	}

	/**
	 * 加密
	 * @param data
	 * @return
	 */
	private static byte[] encrypt(byte[] data,boolean isPublicKey) {
		try {
			
			if(isPublicKey){
				return RSAUtils.encryptByPublicKey(data, publicKey);
			}
			return RSAUtils.encryptByPrivateKey(data, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("加密失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param data
	 * @return
	 */
	private static byte[] decrypt(byte[] data,boolean isPublicKey) {
		 
		try {
			if(isPublicKey){
				return RSAUtils.decryptByPublicKey(data, publicKey);
			}
			return RSAUtils.decryptByPrivateKey(data, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("私钥解密失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}
		 
		return null;
	}
	

	public static void main(String[] args) throws Exception {
		  
		/**
		 * RSAUtilsLieTouToPuHui.createRSAKey();
		 */
		
		
		  /*测试信息*/
		  String   kk= RSACommonUtilsPHPF.encryptByPrivateKey("18822119676", RSACommonUtilsPHPF.CharSet.UTF8) ;
		  System.out.println(kk );
		  System.out.println(RSACommonUtilsPHPF.decryptByPublicKey(kk, RSACommonUtilsPHPF.CharSet.UTF8));
		  
	}

}
