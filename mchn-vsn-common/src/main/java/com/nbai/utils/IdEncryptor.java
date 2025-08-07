package com.nbai.utils;

import java.util.Random;

import org.springframework.util.Assert;

/**
 * ID加解密工具类
 *
 * @author zz
 * @date 2021-3-5
 */
public class IdEncryptor {

    /**
     * 最小可加密id
     */
    static public long MIN_ID = 10000;

    /**
     * 最大可加密id
     */
    static public long MAX_ID = 100_000_000_000L;

    /**
     * 加密过程中使用的一个magic number
     */
    static protected int MagicNumber = 1234;

    /**
     * 最大余数段长度，余数最多4位
     */
    static protected int MaxRemainderSectionLength = 4;

    /**
     * 随机数生成器，估计用量不大，所以就不用ThreadLocalRandom了
     */
    static protected Random random = new Random(System.currentTimeMillis() * MagicNumber);

    /**
     * 每次生成的随机秘钥的长度
     */
    static protected int RANDOM_INT_LENGTH = 3;

    /**
     * 获取一个随机数，范围在100-999之间
     *
     * @return 随机数
     */
    protected static synchronized int getRandomInt() {
        return random.nextInt(900) + 100;
    }

    /**
     * 返回一个[min, max]之间的随机整数
     *
     * @param min 区间下界
     * @param max 区间上界
     * @return 生成的区间内的随机数
     */
    protected static synchronized int nextRangedInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    /**
     * 加密：使用随机秘钥生成一个加密后的id
     *
     * @param id 待加密的id
     * @return 加密后的id
     */
    public static long encrypt(long id) {
        // 检查id的大小，太大的id无法加密
        if (id < MIN_ID) {
            throw new IllegalArgumentException("id过小，无法加密");
        }
        if (id >= MAX_ID) {
            throw new IllegalArgumentException("id过大，无法加密");
        }

        int rKey = getRandomInt();
        // 把id和随机数乘起来
        long product = id * rKey;
        // 用MagicNumber拆成商和余数
        long quotient = product / MagicNumber;
        long remainder = product % MagicNumber;
        // 余数的长度
        String remainderString = Long.toString(remainder);
        int remainderLength = remainderString.length();
        // 先拼接余数部分
        StringBuilder builder = new StringBuilder(20);
        builder.append(remainderString);
        // 拼接商的部分
        builder.append(quotient);
        // 植入秘钥
        String keyString = Integer.toString(rKey);
        for (int i = 0; i < RANDOM_INT_LENGTH; i++) {
            builder.insert(builder.length() - i * 2, keyString.charAt(i));
        }
        // 用余数第一位数字表达余数长度的插入位置
        if (0 == remainder) {
            // 如果余数是0，很坑，意味着生成的encryptId是0开头的，会导致转成long之后丢失首位
            // 将首位替换成非0值，并将余数长度换成一个超长值，表示余数为0
            // 一个2位随机数，十位用于替换掉值为0的余数，整体用于生成一个大于MaxRemainderSectionLength的随机数，表示余数为0
            int randomInt = nextRangedInt(10, 99);
            int newFirst = randomInt / 10;
            builder.replace(0, 1, Integer.toString(newFirst));
            remainderLength = MaxRemainderSectionLength + 1 + randomInt % (9 - MaxRemainderSectionLength);
        }
        // 插入余数长度
        int remainderInsertPosition = 1 + Integer.parseInt(builder.substring(0, 1)) % (builder.length());
        builder.insert(remainderInsertPosition, remainderLength);
        return Long.parseLong(builder.toString());
    }

    /**
     * 解密：解密生成的加密id
     *
     * @param eid 待解密的id
     * @return 解密后的id
     */
    public static long decrypt(long eid) {
        try {
			return decryptCode(eid);
		} catch (Exception e) {
			Assert.isNull(e, "解密失败");
		}
        return 0;
    }

	private static long decryptCode(long eid) {
		String eidString = Long.toString(eid);
        StringBuilder builder = new StringBuilder(eidString);

        // 抽取余数位置与长度
        int remainderInsertPosition = 1 + Integer.parseInt(eidString.substring(0, 1)) % (eidString.length() - 1);
        int remainderLength = Integer.parseInt(eidString.substring(remainderInsertPosition, remainderInsertPosition + 1));
        builder.delete(remainderInsertPosition, remainderInsertPosition + 1);

        // 抽取秘钥部分
        String keyString = "";
        for (int i = 0; i < RANDOM_INT_LENGTH; i++) {
            int pos = builder.length() - 1 - i;
            keyString += builder.charAt(pos);
            builder.delete(pos, pos + 1);
        }

        // 抽取余数部分
        StringBuilder tmpBuilder = new StringBuilder();
        if (remainderLength <= MaxRemainderSectionLength) {
            tmpBuilder.append(builder.substring(0, remainderLength));
        } else {
            // 余数长度超大，说明实际余数是0
            tmpBuilder.append(0);
            remainderLength = 1;
        }
        long remainder = Long.parseLong(tmpBuilder.toString());

        // 抽取商的部分
        long quotient = Long.parseLong(builder.substring(remainderLength));
        // 计算原始id
        int key = Integer.parseInt(keyString);
        long product = quotient * MagicNumber + remainder;
        // 检查合法性
        if (0 != product % key) {
            throw new IllegalArgumentException("illegal id");
        }
        return product / key;
	}
    
}
