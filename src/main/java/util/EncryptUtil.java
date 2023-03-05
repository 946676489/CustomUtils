package util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * PBKDF2加密工具
 * @author TF
 */
public class EncryptUtil {

	/**
	 * 算法
	 */
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	/**
	 * 盐的长度
	 */
	public static final int SALT_BYTE_SIZE = 32 / 2;
	/**
	 * 生成密文的长度
	 */
	public static final int HASH_BIT_SIZE = 32 * 4;
	/**
	 * 迭代次数
	 */
	public static final int PBKDF2_ITERATIONS = 1000;

	/**
	 * @describe: 对输入的密码进行验证
	 * @param: [attemptedPassword(待验证密码), encryptedPassword(密文), salt(盐值)]
	 * @return: boolean
	 */
	private static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 用相同的盐值对用户输入的密码进行加密
		String encryptedAttemptedPassword = toHex(getEncryptedPassword(attemptedPassword, salt));
		// 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
		return encryptedAttemptedPassword.equals(encryptedPassword);
	}

	/**
	 * @describe: 生成密文
	 * @param: [password(明文密码), salt(盐值)]
	 * @return: java.lang.String
	 */
	public static byte[] getEncryptedPassword(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 参数 ：明文密码 ，盐值，和迭代次数和长度生成密文
		KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
		// 返回转换指定算法的秘密密钥的 SecretKeyFactory 对象
		// 此方法从首选 Provider 开始遍历已注册安全提供者列表。返回一个封装 SecretKeyFactorySpi 实现的新
		// SecretKeyFactory 对象，该实现取自支持指定算法的第一个 Provider。
		SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		// 根据提供的密钥规范（密钥材料）生成 SecretKey 对象。 然后以主要编码格式返回键，最后转换为16进制字符串
		return factory.generateSecret(spec).getEncoded();
	}

	/**
	 * @describe: 通过加密的强随机数生成盐(最后转换为16进制)
	 * @return: java.lang.String
	 */
	private static String generateSalt() throws NoSuchAlgorithmException {
		// 返回一个实现指定的 SecureRandom 对象
		// 随机数生成器 (RNG) 算法。
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		// 生成盐
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		// 返回16进制的字符串
		return toHex(salt);
	}

	/**
	 * @describe: 十六进制字符串转二进制字符串
	 * @param: [hex]
	 * @return: byte[]
	 */
	private static byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		try {
			for (int i = 0; i < binary.length; i++) {
				binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
			}
		} catch (NumberFormatException e) {
			return hex.getBytes();
		}
		return binary;
	}

	/**
	 * @describe: 二进制字符串转十六进制字符串
	 * @param: [array]
	 * @return: java.lang.String
	 */
	public static String toHex(byte[] array) {
		BigInteger bigInteger = new BigInteger(1, array);
		String hex = bigInteger.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	/**
	 * 生成可以保存的数据
	 * @param password 明文
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String generateStorngPasswordHash(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 先获取盐值
		String salt = EncryptUtil.generateSalt();
		return generateStorngPasswordHash(password, salt);
	}

	/**
	 * 生成可以保存的数据
	 * @param password 明文
	 * @param salt 盐值
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String generateStorngPasswordHash(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 生成密文
		String hash = toHex(EncryptUtil.getEncryptedPassword(password, salt));
		return salt + ":" + hash;
	}

	/**
	 * 明文密码和数据库保存的值比较
	 * @param originalPassword 明文密码
	 * @param storedPassword 数据库保存的值
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static boolean validatePassword(String originalPassword, String storedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] parts = storedPassword.split(":");
		String salt = parts[0];
		String hash = parts[1];
		return EncryptUtil.authenticate(originalPassword, hash, salt);
	}

	public static byte[] aesEncrypt(byte[] srcBytes, byte[] sKey, String cKey) throws Exception {
		if (sKey.length % 16 != 0) {
			System.err.println("Key's length is not 16 bit!");
			return new byte[0];
		}
		SecretKeySpec sKeySpec = new SecretKeySpec(sKey, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(cKey.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
		return cipher.doFinal(srcBytes);
	}
	public static byte[] aesDecode(byte[] srcBytes, byte[] sKey, String cKey) throws Exception {
		if (sKey.length % 16 != 0) {
			System.err.println("Key's length is not 16 bit!");
			return new byte[0];
		}
		SecretKeySpec sKeySpec = new SecretKeySpec(sKey, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(cKey.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
		return cipher.doFinal(srcBytes);
	}
}