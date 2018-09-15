package com.handge.hr.manage.common.description;

/**
 * Created by MaJianfu on 2018/9/5.
 */
public class EventDescription {

    /**
     * 创建项目\任务
     */
    public static String create(String name, String type) {
        String value = name + "创建了该" + type;
        return value;
    }

    /**
     * 接收项目\任务
     */
    public static String receive(String name, String type) {
        String value = name + "接收了该" + type;
        return value;
    }

    /**
     * 直属任务的创建
     */
    public static String createTaskByProject(String name, String taskName, String type) {
        String value = name + "创建了一级" + type + ":" + taskName;
        return value;
    }

    /**
     * 直属任务的结束
     */
    public static String endTaskByProject(String name, String taskName, String type) {
        String value = name + "结束了一级" + type + ":" + taskName;
        return value;
    }

    /**
     * 超时
     */
    public static String overtime(String type) {
        String value = "该" + type + "已到达计划结束时间，由于实际未完成而进入了超时阶段";
        return value;
    }

    /**
     * 评价
     */
    public static String evaluate(String name, String type) {
        String value = name + "评价了该" + type;
        return value;
    }

    /**
     * 结束
     */
    public static String end(String name, String type) {
        String value = name + "结束了该" + type;
        return value;
    }

    /**
     * 终止
     */
    public static String terminate(String name, String reason, String type) {
        String value = name + "终止了该" + type + "，原因为:" + reason;
        return value;
    }

    /**
     * 终止(上级任务被终止)
     */
    public static String terminateTask(String name, String type) {
        String value = name + "终止了该" + type + "，原因为：上级任务被终止";
        return value;
    }

    /**
     * 终止(上级任务被终止)
     */
    public static String terminateProject(String name, String type) {
        String value = name + "终止了该" + type + "，原因为：所属项目被终止";
        return value;
    }

    /**
     * 提交任务
     */
    public static String submitTask(String name, String type) {
        String value = name + "提交了该" + type + "到审查";
        return value;
    }

    /**
     * 审查跳过
     */
    public static String skipEvaluate(String name, String type) {
        String value = name + "跳过了该" + type + "的审查";
        return value;
    }

    /**
     * 审查跳过(所属项目已跳过审查)
     */
    public static String skipEvaluateByProject(String name, String type) {
        String value = name + "跳过了该" + type + "的审查,原因为所属项目已跳过审查";
        return value;
    }

    /**
     * 审查通过
     */
    public static String reviewPass(String name, String type) {
        String value = name + "通过了该" + type + "的审查，并进行了审查评价";
        return value;
    }

    /**
     * 审查不通过
     */
    public static String reviewNoPass(String name, String type, String receiveName) {
        String value = name + "不通过该" + type + "的审查，任务已返回给" + receiveName;
        return value;
    }
}
