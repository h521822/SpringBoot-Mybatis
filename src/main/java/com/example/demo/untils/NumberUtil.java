package com.example.demo.untils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *@ClassName NumberUtil
 *@Author NumberUtil
 *@Date 2019/4/12 17:32
 *@Version 1.0
 **/
public class NumberUtil {


    private static final int DEF_DIV_SCALE = 8;

    /**
     * 每三位以逗号区隔,小数位最多保留2位
     */
    public static final String MAX_DECIMAL_TWO = "#,##0.##";

    /**
     * 每三位以逗号区隔,小数位保留2位
     */
    public static final String FULL_DECIMAL_TWO = "#,##0.00";

    /**
     * 每三位以逗号区隔,小数位最多保留6位
     */
    public static final String MAX_DECIMAL_SIX = "#,##0.######";

    /**
     * 每三位以逗号区隔,小数位保留6位
     */
    public static final String FULL_DECIMAL_SIX = "#,##0.000000";

    /**
     * 每三位以逗号区隔,小数位最多保留8位
     */
    public static final String MAX_DECIMAL_EIGHT = "#,##0.########";

    /**
     * 每三位以逗号区隔,小数位保留8位
     */
    public static final String FULL_DECIMAL_EIGHT = "#,##0.00000000";

    /**
     * 默认数值转换格式 : 每三位以逗号区隔,小数位保留2位
     */
    public static final String DEFAULT_FORMAT_TEMPLATE = FULL_DECIMAL_TWO;


    /**
     * 转换BigDecimal为Integer
     *
     * @param parm BigDecimal数据
     * @return Integerl数据
     */
    public static Integer toInteger(BigDecimal parm) {
        if (parm == null) {
            return null;
        } else {
            return new Integer(parm.intValue());
        }
    }

    /**
     * 对数据进行精确格式化，四舍五入
     *
     * @param val          源数据
     * @param scale        精确的位数
     * @param roundingMode 舍入方式
     */
    public static BigDecimal formatBigDecimal(BigDecimal val, int scale, int roundingMode) {
        return bigDecimalNullToZero(val).setScale(scale, roundingMode);
    }

    /**
     *  将字符串转为int型，默认给0
     * @param s 待转换对象
     * @return 返回值int
     */
    public static int convertInt(String s) {
        int v = 0;
        if (s != null && s.trim().length() > 0) {
            v = Integer.parseInt(s);
        }
        return v;
    }

    /**
     *  将String字符串转为BigDecimal
     * @param s 待转换对象
     * @return 转换结果
     */
    public static BigDecimal convertDecimal(String s) {
        BigDecimal v = null;
        if (s != null && s.trim().length() > 0) {
            v = new BigDecimal(s);
        }
        return v;
    }

    /**
     *  BigDecimal互相相加
     * @param val1 参数一
     * @param val2 参数二
     * @return 加法运算结果
     */
    public static BigDecimal bigDecimalAdd(BigDecimal val1, BigDecimal val2) {
        BigDecimal a = val1 == null ? BigDecimal.ZERO : val1;
        BigDecimal b = val2 == null ? BigDecimal.ZERO : val2;
        a = a.add(b);
        return a;
    }

    /**
     *  BigDecimal相减
     * @param val1 被减数
     * @param val2 减数
     * @return 减法运算结果
     */
    public static BigDecimal bigDecimalSub(BigDecimal val1, BigDecimal val2) {
        return bigDecimalNullToZero(val1).subtract(bigDecimalNullToZero(val2));
    }

    /**
     *  BigDecimal相乘
     * @param val1 被乘数
     * @param val2 乘数数
     * @return 乘法运算结果
     */
    public static BigDecimal bigDecimalMultiplyNoScale(BigDecimal val1, BigDecimal val2) {
        return bigDecimalNullToZero(val1).multiply(bigDecimalNullToZero(val2));
    }

    /**
     *  BigDecimal相乘
     * @param val1 被乘数
     * @param val2 乘数数
     * @return 乘法运算结果
     */
    public static BigDecimal bigDecimalMultiply(BigDecimal val1, BigDecimal val2) {
        return bigDecimalMultiply(val1, val2, 2);
    }

    /**
     *  BigDecimal相乘
     * @param val1 被乘数
     * @param val2 乘数数
     * @return 乘法运算结果
     */
    public static BigDecimal bigDecimalMultiply(BigDecimal val1, BigDecimal val2, int scale) {
        return bigDecimalNullToZero(val1).multiply(bigDecimalNullToZero(val2)).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *  BigDecimal互相除
     * @param val1 参数一
     * @param val2 参数二
     * @return 除法运算结果
     */
    public static BigDecimal bigDecimalDivide(BigDecimal val1, BigDecimal val2) {
        return bigDecimalDivide(val1, val2, 2);
    }

    /**
     *  BigDecimal互相除
     * @param val1 参数一
     * @param val2 参数二
     * @return 除法运算结果
     */
    public static BigDecimal bigDecimalDivide(BigDecimal val1, BigDecimal val2,int scale) {
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal a = val1 == null ? BigDecimal.ZERO : val1;
        BigDecimal b = val2 == null ? BigDecimal.ONE : val2 == BigDecimal.ZERO ? BigDecimal.ONE : val2;
        if(b.compareTo(BigDecimal.ZERO) == 0){
            throw new IllegalArgumentException(
                    "The BigDecimal must be not a zero");
        }
        a = a.divide(b, scale,BigDecimal.ROUND_HALF_UP);
        return a;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal roundDouble(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后8位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     *  double轉BigDecimal
     * @param v1  参数
     * @return 結果
     */
    public static BigDecimal doubleToBigDecimal(double v1) {
        return new BigDecimal(Double.toString(v1));
    }

    /**
     *  double轉BigDecimal
     * @param v1  参数
     * @return 結果
     */
    public static BigDecimal bigDecimalNullToZero(BigDecimal v1) {
        return (v1 == null ? BigDecimal.ZERO : v1);
    }

    /**
     *  double轉BigDecimal
     * @param v1  参数
     * @return 結果
     */
    public static BigDecimal longToBigDecimal(Long v1) {

        return (v1 == null ? BigDecimal.ZERO : new BigDecimal(v1));
    }

    /**
     * 数字格式化
     * 每三位以逗号区隔，保留两位小数
     *
     * @param v1 待格式化的数字
     * @return java.lang.String
     * @author He
     * @date 2019/4/12 16:19
     */
    public static String format(BigDecimal v1) {
        return format(null, v1);
    }

    /**
     * 数字格式化
     * 每三位以逗号区隔，保留两位小数
     *
     * @param preFix 前缀（一般为币种符号）
     * @param v1 待格式化的数字
     * @return java.lang.String
     * @author He
     * @date 2019/4/12 16:19
     */
    public static String format(String preFix, BigDecimal v1) {
        return format(v1, preFix == null ? DEFAULT_FORMAT_TEMPLATE : (preFix + DEFAULT_FORMAT_TEMPLATE));
    }

    /**
     * 数字格式化
     *
     * @param v1 待格式化的数字
     * @param pattern 格式字符串
     * @return java.lang.String
     * @author He
     * @date 2019/4/12 16:25
     */
    public static String format(BigDecimal v1, String pattern) {
        if (v1 == null) {
            return "";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern != null ? pattern : DEFAULT_FORMAT_TEMPLATE);
        // 四舍五入
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(v1);
    }
}
