package com.handge.hr.manage.common.math;

import java.util.List;

/**
 * @author Liujuhao
 * @date 2018/7/23.
 */
public class Calculator {
    /**
     * 求给定双精度数组中值的最大值
     *
     * @param inputData 输入数据数组
     * @return 运算结果, 如果输入值不合法, 返回为-1
     */
    public double getMax(double[] inputData) {
        if (inputData == null || inputData.length == 0) {
            // TODO: 2018/7/25  抛异常
            return -1;
        }
        int len = inputData.length;
        double max = inputData[0];
        for (int i = 0; i < len; i++) {
            if (max < inputData[i]) {
                max = inputData[i];
            }
        }
        return max;
    }

    /**
     * 求给定双精度集合中值的最大值
     *
     * @param inputData 输入数据集合
     * @return
     */
    public double getMax(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getMax(data);
    }

    /**
     * 求给定双精度数组中值的最小值
     *
     * @param inputData 输入数据数组
     * @return 运算结果, 如果输入值不合法, 返回为-1
     */
    public double getMin(double[] inputData) {
        if (inputData == null || inputData.length == 0) {
            // TODO: 2018/7/25  抛异常
            return -1;
        }
        int len = inputData.length;
        double min = inputData[0];
        for (int i = 0; i < len; i++) {
            if (min > inputData[i]) {
                min = inputData[i];
            }
        }
        return min;
    }

    /**
     * 求给定双精度集合中值的最小值
     *
     * @param inputData 输入数据集合
     * @return
     */
    public double getMin(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getMin(data);
    }

    /**
     * 求给定双精度数组中值的和
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public double getSum(double[] inputData) {
        if (inputData == null || inputData.length == 0) {
            // TODO: 2018/7/25  抛异常
            return -1;
        }
        int len = inputData.length;
        double sum = 0;
        for (int i = 0; i < len; i++) {
            sum = sum + inputData[i];
        }
        return sum;
    }

    /**
     * 求给定双精度集合中值的和
     *
     * @param inputData 输入数据集合
     * @return
     */
    public double getSum(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getSum(data);
    }

    /**
     * 求给定双精度数组中值的数目
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public int getCount(double[] inputData) {
        if (inputData == null || inputData.length == 0) {
            // TODO: 2018/7/25 抛异常
            return -1;
        }
        return inputData.length;
    }

    /**
     * 求给定双精度集合中值的数目
     *
     * @param inputData 输入数据集合
     * @return
     */
    public int getCount(List<Double> inputData) {
        double[] data = new double[inputData.size()];
        int len = inputData.size();
        for (int i = 0; i < len; i++) {
            data[i] = inputData.get(i);
        }
        return getCount(data);
    }
}
