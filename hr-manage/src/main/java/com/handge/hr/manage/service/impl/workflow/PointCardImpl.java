package com.handge.hr.manage.service.impl.workflow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.enumeration.manage.ProjectStatusEnum;
import com.handge.hr.common.enumeration.manage.TaskStatusEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.domain.entity.manage.web.request.pointcard.AddPointCardParam;
import com.handge.hr.domain.entity.manage.web.request.pointcard.UpdatePointCardParam;
import com.handge.hr.domain.entity.manage.web.request.pointcard.ViewPointCardParam;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.*;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.common.enumeration.manage.EvaluateOriginTypeEnum;
import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.workflow.IPointCard;
import com.handge.hr.manage.service.api.workflow.ICompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import com.handge.hr.domain.entity.manage.web.request.pointcard.ListPointCardParam;
import com.handge.hr.domain.entity.manage.web.response.pointcard.PointCardInfo;
import com.handge.hr.domain.entity.manage.web.response.pointcard.PointItemInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuqian
 * @date 2018/8/23
 * @Description:积分卡接口实现类
 */
@Component
public class PointCardImpl implements IPointCard {
    @Autowired
    PerformanceHistoryPointCardMapper performanceHistoryPointCardMapper;
    @Autowired
    WorkflowTaskMapper workflowTaskMapper;
    @Autowired
    WorkflowProjectMapper workflowProjectMapper;
    @Autowired
    PerformancePointItemMapper pointItemMapper;
    @Autowired
    ICompletion iCompletion;
    @Autowired
    WorkflowProjectEventMapper workflowProjectEventMapper;
    @Autowired
    WorkflowTaskEventMapper workflowTaskEventMapper;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addPointCard(AddPointCardParam addPointCardParam) {
        PerformanceHistoryPointCard performanceHistoryPointCard = new PerformanceHistoryPointCard();
        WorkflowTask workflowTask = null;
        WorkflowProject workflowProject = null;
        //根据来源Id查询负责人id
        String empId = null;
        boolean isTask = EvaluateOriginTypeEnum.TASK.getCode().equals(addPointCardParam.getOrigin()) || EvaluateOriginTypeEnum.TASK_QC.getCode().equals(addPointCardParam.getOrigin());
        boolean isProject = EvaluateOriginTypeEnum.PROJECT.getCode().equals(addPointCardParam.getOrigin());
        if (isTask) {
            workflowTask = workflowTaskMapper.selectByPrimaryKey(addPointCardParam.getOriginId());
            //验证完成度
            String completion = iCompletion.getTaskCompletion(workflowTask.getId());
            boolean isCompletionAndStatus = "100".equals(completion) && TaskStatusEnum.EVALUATE.getValue().equals(workflowTask.getStatus());
            if (!isCompletionAndStatus) {
                throw new UnifiedException("完成度或状态", ExceptionWrapperEnum.IllegalArgumentException);
            }
            //验证是否是创建者
//            String user = "creator";   // TODO: 2018/8/27 用户参数
//            boolean isCreator = user.equals(workflowTask.getCreatedBy());
//            if(!isCreator){
//                throw new UnifiedException("creator", ExceptionWrapperEnum.Role_NOT_Power);
//            }
            empId = workflowTask.getReceiver();
        } else if (isProject) {
            workflowProject = workflowProjectMapper.selectByPrimaryKey(addPointCardParam.getOriginId());
            //验证完成度
//            String completion = iCompletion.getProjectCompletion(workflowProject.getId());
//            boolean isCompletionAndStatus = "100".equals(completion) && TaskStatusEnum.EVALUATE.getValue().equals(workflowProject.getStatus());
//            if (!isCompletionAndStatus) {
//                throw new UnifiedException("完成度或状态", ExceptionWrapperEnum.IllegalArgumentException);
//            }
            //验证是否是创建者
//            String user = "creator";   // TODO: 2018/8/27 用户参数
//            boolean isCreator = user.equals(workflowProject.getCreatedBy());
//            if(!isCreator){
//                throw new UnifiedException("creator", ExceptionWrapperEnum.Role_NOT_Power);
//            }
            empId = workflowProject.getPrincipal();
        } else {
            throw new UnifiedException("模块不存在", ExceptionWrapperEnum.IllegalArgumentException);
        }
        //region 积分卡入库
        performanceHistoryPointCard.setUsedDate(new Date());
        performanceHistoryPointCard.setGmtCreate(new Date());
        performanceHistoryPointCard.setContent(addPointCardParam.getContent());
        performanceHistoryPointCard.setEmployeeId(empId);
        performanceHistoryPointCard.setPointCardId(addPointCardParam.getPointCardId());
        performanceHistoryPointCard.setOrigin(addPointCardParam.getOrigin());
        performanceHistoryPointCard.setOriginId(addPointCardParam.getOriginId());
        performanceHistoryPointCard.setId(UuidUtils.getUUid());
        performanceHistoryPointCardMapper.insert(performanceHistoryPointCard);
        //endregion
        return 1;
    }

    @Override
    public Object viewPointCard(ViewPointCardParam viewPointCardParam) {
        PerformanceHistoryPointCard performanceHistoryPointCard = performanceHistoryPointCardMapper.selectByPrimaryKey(viewPointCardParam.getId());
        return performanceHistoryPointCard;
    }

    @Override
    public List<PointCardInfo> listPointCard(ListPointCardParam listPointCardParam) {
        Gson gson = new Gson();
        //积分卡列表
        List<PointCardInfo> pointCardList = new ArrayList<>();
        //数据库查询积分卡结果
        List<Map<String, Object>> pointCardFromDB = pointItemMapper.getCardsInfo(listPointCardParam.getOrigin());
        //数据库查询字段
        String columnCardId = "card_id";
        String columnCardName = "card_name";
        String columnItemId = "item_id";
        String columnItemName = "item_name";
        String columnInputType = "input_type";
        String columnOptions = "options";
        //查询结果积分卡信息去重，避免积分卡ID和名称重复的积分卡实体
        Map<String, String> cardsMap = new HashMap<>(30);
        pointCardFromDB.forEach(o -> {
            String cardId = o.get(columnCardId).toString();
            if (!cardsMap.containsKey(cardId)) {
                cardsMap.put(cardId, o.get(columnCardName).toString());
            }
        });
        //通过积分卡ID和名称实例化积分卡实体
        for (Map.Entry<String, String> entry : cardsMap.entrySet()) {
            PointCardInfo pointCardInfo = new PointCardInfo();
            pointCardInfo.setCardId(entry.getKey());
            pointCardInfo.setCardName(entry.getValue());
            pointCardList.add(pointCardInfo);
        }
        //将数据库查询结果中的积分项放入对应的积分卡实体中
        pointCardList.forEach(pointCardEntity -> {
            List<PointItemInfo> itemList = new ArrayList<>();
            pointCardFromDB.forEach(line -> {
                boolean isBelongThisPointCard = line.get(columnCardId).toString().equals(pointCardEntity.getCardId());
                if (isBelongThisPointCard) {
                    PointItemInfo pointItemInfo = new PointItemInfo() {
                        {
                            this.setItemId(line.get(columnItemId).toString());
                            this.setItemName(line.get(columnItemName).toString());
                            this.setInputType(line.get(columnInputType).toString());
                            Object options = line.get(columnOptions);
                            if (options != null) {
                                //将json数据转换为Map<String,String>
                                Type type = new TypeToken<Map<String, String>>() {
                                }.getType();
                                Map<String, String> map = gson.fromJson(options.toString(), type);
                                List<Map<String, String>> optionsList = new ArrayList<>(10);
                                for(Map.Entry<String,String> entry:map.entrySet()){
                                    Map<String, String> item = new HashMap<>(2);
                                    //"optionId"--积分项ID，"optionContent"--积分项内容
                                    item.put("optionId",entry.getKey());
                                    item.put("optionContent",entry.getValue());
                                    optionsList.add(item);
                                }
                                this.setOptions(optionsList);
                            }
                        }
                    };
                    itemList.add(pointItemInfo);
                }
            });
            pointCardEntity.setItems(itemList);
        });
        return pointCardList;
    }

    @Override
    public Object updatePointCard(UpdatePointCardParam updatePointCardParam) {
        PerformanceHistoryPointCard performanceHistoryPointCard = performanceHistoryPointCardMapper.selectByPrimaryKey(updatePointCardParam.getId());
        //region验证
        //验证创建者和状态
        boolean isTask = EvaluateOriginTypeEnum.TASK.getCode().equals(updatePointCardParam.getOrigin()) || EvaluateOriginTypeEnum.TASK_QC.getCode().equals(updatePointCardParam.getOrigin());
        boolean isProject = EvaluateOriginTypeEnum.PROJECT.getCode().equals(updatePointCardParam.getOrigin());
//        if (isTask) {
//
//            WorkflowTask workflowTask = workflowTaskMapper.selectByPrimaryKey(updatePointCardParam.getOriginId());
//            boolean isCompleted ;
//            isCompleted = TaskStatusEnum.COMPLETED.getValue().equals(workflowTask.getStatus());
//            if(!isCompleted){
//                throw new UnifiedException("状态", ExceptionWrapperEnum.STATUS_ERROR);
//            }
//            String user = "creator";// TODO: 2018/8/29 用户信息
//            boolean isCreator = user.equals(workflowTask.getCreatedBy());
//            if(!isCreator){
//                throw new UnifiedException("creator", ExceptionWrapperEnum.Role_NOT_Power);
//            }
//        } else if (isProject) {
//            WorkflowProject workflowProject = workflowProjectMapper.selectByPrimaryKey(updatePointCardParam.getOriginId());
//            boolean isCompleted ;
//            isCompleted = ProjectStatusEnum.COMPLETED.getValue().equals(workflowProject.getStatus());
//            if(!isCompleted){
//                throw new UnifiedException("状态", ExceptionWrapperEnum.STATUS_ERROR);
//            }
//            String user = "creator";// TODO: 2018/8/29 用户信息
//            boolean isCreator = user.equals(workflowProject.getCreatedBy());
//            if(!isCreator){
//                throw new UnifiedException("creator", ExceptionWrapperEnum.Role_NOT_Power);
//            }
//        }else{
//            throw new UnifiedException("模块不存在",ExceptionWrapperEnum.IllegalArgumentException);
//        }
        //验证时间
        long currentTimeStamp = 0;
        long usedTimeStamp = 0;
        try {
            currentTimeStamp = DateUtil.dateToTimeStamp(DateUtil.date2Str(new Date(), DateFormatEnum.MONTH), DateFormatEnum.MONTH);
            usedTimeStamp = DateUtil.dateToTimeStamp(DateUtil.date2Str(performanceHistoryPointCard.getUsedDate(), DateFormatEnum.MONTH), DateFormatEnum.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean isOverTime = currentTimeStamp != usedTimeStamp;
        if (isOverTime) {
            throw new UnifiedException("生效时间已过，不能修改", ExceptionWrapperEnum.IllegalArgumentException);
        }


        //endregion
        performanceHistoryPointCard.setContent(updatePointCardParam.getContent());
        performanceHistoryPointCard.setGmtModified(new Date());
        performanceHistoryPointCardMapper.updateByPrimaryKey(performanceHistoryPointCard);
        return 1;
    }
}


