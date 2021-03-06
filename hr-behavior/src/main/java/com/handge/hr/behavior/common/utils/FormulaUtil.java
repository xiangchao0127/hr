package com.handge.hr.behavior.common.utils;


import com.handge.hr.behavior.common.repository.BaseDAOImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

/**
 * Created by DaLu Guo on 2018/6/12.
 */
public class FormulaUtil {

    static BaseDAOImpl baseDao = new BaseDAOImpl() ;

    /**
     * 将传入的次数转化成 “小时，分钟 ” 的格式
     *
     * @param times 次数
     * @return String  例如三小时20分钟，则显示：3，20
     * @author MaJianfu
     * @date 11:31
     **/
    public static String timesToHourMinute(Object times,String minuteByOneClick) {

        double minutes;
        double minuteByOneClicks = Double.parseDouble(minuteByOneClick);
        if (times instanceof String) {
            minutes = Integer.parseInt((String) times) * minuteByOneClicks;
        } else if (times instanceof Number) {
            minutes = Double.parseDouble(times.toString()) * minuteByOneClicks;
        } else {
            throw new Error("不支持的数据类型：" + times.getClass().getTypeName());
        }
        int hour = (int) Math.floor(minutes / 60);
        return String.valueOf(hour + "," + (int) (minutes % 60));
    }

    /**
     * 根据等级获取分数
     *
     * @param degree
     * @return
     * @author xc
     */
    @Deprecated
    public static BigDecimal scoreCalculate(String degree) {
        double score = 0.0;
        Random random = new Random();
        switch (degree) {
            case "A":
                score = 80;
                break;
            case "B":
                score = 60;
                break;
            case "C":
                score = 40;
                break;
            case "D":
                score = 20;
                break;
            case "E":
                score = 10;
                break;
            default:
                score = 0;
                break;
        }
        return new BigDecimal(score).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算平均分
     *
     * @param sorces
     * @return
     */
    public static BigDecimal avgScore(List<BigDecimal> sorces) {
        BigDecimal total = new BigDecimal(0);
        for (BigDecimal s : sorces) {
            total = total.add(s);
        }
        return (total.divide(new BigDecimal(sorces.size()), 1, RoundingMode.HALF_UP));
    }

    /**
     * 计算综合素养得分
     */
    public static BigDecimal calculateComprehensiveScore(BigDecimal workingAttitudeScore, BigDecimal loyaltyScore, BigDecimal complianceDisciplineScore) {
        BigDecimal comprehensiveScore = ((workingAttitudeScore.add(loyaltyScore).add(complianceDisciplineScore)).divide(new BigDecimal(3), 1, RoundingMode.HALF_UP));
        return comprehensiveScore;
    }

}
