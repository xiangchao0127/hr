package com.handge.hr.manage.common.math;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Liujuhao
 * @date 2018/7/23.
 */
public class Statistictor {
    private Calculator calculator = new Calculator();

    /**
     * 求给定双精度数组中值的平均值
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public double getAverage(double[] inputData) {
        if (inputData == null || inputData.length == 0) {
            // TODO: 2018/7/25 抛异常
            return -1;
        }
        int len = inputData.length;
        double result;
        result = calculator.getSum(inputData) / len;
        return result;
    }

    /**
     * 求给定双精度集合中值的平均值
     *
     * @param inputData 输入数据集合
     * @return
     */
    public double getAverage(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getAverage(data);
    }


    /**
     * 求给定双精度数组中值的平方和
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public double getSquareSum(double[] inputData) {
        if (inputData == null || inputData.length == 0) {
            // TODO: 2018/7/25 抛异常
            return -1;
        }
        int len = inputData.length;
        double sqrsum = 0.0;
        for (int i = 0; i < len; i++) {
            sqrsum = sqrsum + inputData[i] * inputData[i];
        }
        return sqrsum;
    }

    /**
     * 求给定双精度集合中值的平方和
     *
     * @param inputData 输入数据集合
     * @return
     */
    public double getSquareSum(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getSquareSum(data);
    }

    /**
     * 求给定双精度数组中值的方差
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public double getVariance(double[] inputData) {
        if (inputData == null || inputData.length == 0) {
            // TODO: 2018/7/25 抛异常
            return -1;
        }
        int count = inputData.length;
        double sqrsum = getSquareSum(inputData);
        double average = getAverage(inputData);
        double result;
        result = (sqrsum - count * average * average) / count;
        return result;
    }

    /**
     * 求给定双精度集合中值的方差
     *
     * @param inputData 输入数据集合
     * @return
     */
    public double getVariance(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getVariance(data);
    }

    /**
     * 求给定双精度数组中值的标准差
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public double getStandardDiviation(double[] inputData) {
        double result;
        //绝对值化很重要
        result = Math.sqrt(Math.abs(getVariance(inputData)));
        return result;
    }

    /**
     * 求给定双精度集合中值的标准差
     *
     * @param inputData 输入数据集合
     * @return
     */
    public double getStandardDiviation(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getStandardDiviation(data);
    }

    /**
     * 求给定双精度数组中值的中位数
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public double getMid(double[] inputData) {
        double mid;
        Arrays.sort(inputData);
        int len = inputData.length;
        int two = 2;
        if (len % two == 0) {
            mid = (inputData[len / 2 - 1] + inputData[len / 2]) / 2;
        } else {
            mid = inputData[len / 2];
        }
        return mid;
    }

    /**
     * 求给定双精度集合中值的中位数
     *
     * @param inputData 输入数据集合
     * @return 运算结果
     */
    public double getMid(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getMid(data);
    }

    /**
     * 求给定双精度数组中值的众数
     *
     * @param inputData 输入数据数组
     * @return
     */
    public List<Double> getMode(double[] inputData) {
        int n = inputData.length;

        if (n == 0) {
            return Collections.EMPTY_LIST;
        }

        if (n == 1) {
            return Arrays.asList(inputData[0]);
        }

        Map<Double, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // 统计数组中每个数出现的频率
            Integer v = freqMap.get(inputData[i]);
            // v == null 说明 freqMap 中还没有这个 arr[i] 这个键
            freqMap.put(inputData[i], v == null ? 1 : v + 1);
        }

        // 将 freqMap 中所有的键值对（键为数，值为数出现的频率）放入一个 ArrayList
        List<Map.Entry<Double, Integer>> entries = new ArrayList(freqMap.entrySet());
        // 对 entries 按出现频率从大到小排序
        Collections.sort(entries, new Comparator<Map.Entry<Double, Integer>>() {
            @Override
            public int compare(Map.Entry<Double, Integer> e1, Map.Entry<Double, Integer> e2) {
                return e2.getValue() - e1.getValue();
            }
        });

        List<Double> modalNums = new ArrayList<>();
        // 排序后第一个 entry 的键肯定是一个众数
        modalNums.add(entries.get(0).getKey());

        int size = entries.size();
        for (int i = 1; i < size; i++) {
            // 如果之后的 entry 与第一个 entry 的 value 相等，那么这个 entry 的键也是众数
            if (entries.get(i).getValue().equals(entries.get(0).getValue())) {
                modalNums.add(entries.get(i).getKey());
            } else {
                break;
            }
        }
        return modalNums;
    }

    /**
     * 求给定双精度集合中值的众数
     *
     * @param inputData 输入数据集合
     * @return
     */
    public List<Double> getMode(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getMode(data);
    }


    /**
     * 求给定双精度数组中值的四分位数
     *
     * @param inputData 输入数据数组
     * @return
     */
    public List<String> getQuartile(double[] inputData) {
        //排序（按升序排列）
        Arrays.sort(inputData);
        //最后结果保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        int len = calculator.getCount(inputData);
        List<String> list = new ArrayList<>();
        double quartile1;
        double quartile2;
        double quartile3;
        double q1 = (double) (len + 1) / 4;
        double q2 = (double) (2 * (len + 1)) / 4;
        double q3 = (double) (3 * (len + 1)) / 4;

        int four = 4;
        if ((len + 1) % four == 0) {
            quartile1 = inputData[(int) q1 - 1];
            quartile2 = inputData[(int) q2 - 1];
            quartile3 = inputData[(int) q3 - 1];
        } else {
            //第一四分位
            //整数部分
            int n1 = (int) q1;
            //小数部分
            double decimal1 = q1 - n1;
            quartile1 = inputData[n1 - 1] * (1 - decimal1) + inputData[n1] * decimal1;

            //第二四分位
            //整数部分
            int n2 = (int) q2;
            //小数部分
            double decimal2 = q2 - n2;
            quartile2 = inputData[n2 - 1] * (1 - decimal2) + inputData[n2] * decimal2;

            //第三四分位
            //整数部分
            int n3 = (int) q3;
            //小数部分
            double decimal3 = q3 - n3;
            quartile3 = inputData[n3 - 1] * (1 - decimal3) + inputData[n3] * decimal3;
        }
        list.add(df.format(quartile1));
        list.add(df.format(quartile2));
        list.add(df.format(quartile3));
        return list;
    }

    /**
     * 求给定双精度集合中值的四分位数
     *
     * @param inputData 输入数据集合
     * @return
     */
    public List<String> getQuartile(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getQuartile(data);
    }

    /**
     * 求同比增长
     *
     * @param theTarget  现年某个指标的值
     * @param lastTarget 上年同期这个指标的值
     * @return
     */
    public Double getYearOnYear(double theTarget, double lastTarget) {
        return 100 * (theTarget - lastTarget) / Math.abs(lastTarget);
    }

    /**
     * 求环比增长
     *
     * @param theTarget  本期指标值
     * @param lastTarget 上期指标值
     * @return
     */
    public Double getRingRatio(double theTarget, double lastTarget) {
        return 100 * (theTarget - lastTarget) / Math.abs(lastTarget);
    }
}
