package com.example.demo.config;

import com.example.demo.entity.User;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;

/**
 * @ClassName ShiroConfig
 * @Author ShiroConfig
 * @Date 2019/4/16 22:30
 * @Version 1.0
 * 密码加密加盐（参考资料：https://blog.wuwii.com/springboot-14.html）
 **/
public class ShiroConfig {
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");//散列算法:MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512等。
		hashedCredentialsMatcher.setHashIterations(1);//散列的次数，默认1次， 设置两次相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}


	/**
	 * 随机生成 salt 需要指定 它的字符串的长度
	 *
	 * @param len 字符串的长度
	 * @return salt
	 */
	public static String generateSaltRandom(int len) {
		//一个Byte占两个字节
		int byteLen = len >> 1;
		SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
		return secureRandom.nextBytes(byteLen).toHex();
	}

	/**
	 * 使用shiro的hash方式
	 *
	 * @param algorithmName  算法
	 * @param source         源对象
	 * @param salt           加密盐
	 * @param hashIterations hash次数
	 * @return 加密后的字符
	 */
	public static String hashByShiro(String algorithmName, Object source, Object salt, int hashIterations) {
		return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
	}

	/**
	 * 生成随机密码盐字符串
	 *
	 * @param length 长度
	 * @return 随机密码盐
	 */
	private static String generateSalt(int length) {
		//随机字符串的随机字符库
		String keyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder();
		int len = keyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(keyString.charAt(new Random().nextInt(len - 1)));
		}
		return sb.toString();
	}

	/**
	 * 获取加密后的密码，使用默认hash迭代的次数 1 次
	 *
	 * @param hashAlgorithm hash算法名称 MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512、etc。
	 * @param password      需要加密的密码
	 * @param salt          盐
	 * @return 加密后的密码
	 */
	public static String encryptPassword(String hashAlgorithm, String password, String salt) {
		return encryptPassword(hashAlgorithm, password, salt, 1);
	}

	/**
	 * 获取加密后的密码，需要指定 hash迭代的次数
	 *
	 * @param hashAlgorithm  hash算法名称 MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512、etc。
	 * @param password       需要加密的密码
	 * @param salt           盐
	 * @param hashIterations hash迭代的次数
	 * @return 加密后的密码
	 */
	public static String encryptPassword(String hashAlgorithm, String password, String salt, int hashIterations) {
		SimpleHash hash = new SimpleHash(hashAlgorithm, password, salt, hashIterations);
		return hash.toString();
	}

	public static void main(String[] args) {
		System.out.println(encryptPassword("MD5", "123456",generateSalt(6)));
	}


	public void save(User sysUser) {
		sysUser.setCreateDate(new Date());
		String salt = generateSalt(20);
		sysUser.setPassword(encryptPassword("SHA-256", sysUser.getPassword(), salt));
		// 保存盐，以用来登录时验证密码是否正确
		sysUser.setSalt(salt);
		sysUser.setUserName(sysUser.getEmail());
		// sysUserDao.save(sysUser);
	}


}
