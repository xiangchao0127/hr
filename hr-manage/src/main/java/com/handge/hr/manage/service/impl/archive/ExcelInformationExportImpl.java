package com.handge.hr.manage.service.impl.archive;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerBorderImpl;
import com.handge.hr.common.utils.FileConfigurationUtils;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.excel.EmployeeExcel;
import com.handge.hr.domain.entity.manage.web.request.archive.InformationParam;
import com.handge.hr.domain.repository.mapper.DictCommonMapper;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import com.handge.hr.domain.repository.pojo.DictCommon;
import com.handge.hr.manage.service.api.archive.IExcelInformationExport;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MaJianfu on 2018/8/1.
 */
@Service
public class ExcelInformationExportImpl implements IExcelInformationExport {

    @Autowired
    EntityEmployeeMapper employee;
    @Autowired
    DictCommonMapper dictCommon;

    @Override
    public Integer exportInformationByTemplate(InformationParam information, HttpServletResponse response) {
        TemplateExportParams params = new TemplateExportParams(FileConfigurationUtils.getConfig("employeeInformation"));
        params.setStyle(ExcelExportStylerBorderImpl.class);
        Map<String, Object> map = new HashMap<String, Object>();
        List<EmployeeExcel> list = employee.getExcelInformation(information);
        Map<Integer, DictCommon> dictCommonMap = dictCommon.getDictCommonMap();
        for (EmployeeExcel e : list) {
            if (StringUtils.notEmpty(e.getJobStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getJobStatus()))) {
                e.setJobStatus(dictCommonMap.get(Integer.parseInt(e.getJobStatus())).getName());
            }
            if (StringUtils.notEmpty(e.getGender()) && dictCommonMap.containsKey(Integer.parseInt(e.getGender()))) {
                e.setGender(dictCommonMap.get(Integer.parseInt(e.getGender())).getName());
            }
            if (StringUtils.notEmpty(e.getMaritalStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getMaritalStatus()))) {
                e.setMaritalStatus(dictCommonMap.get(Integer.parseInt(e.getMaritalStatus())).getName());
            }
            if (StringUtils.notEmpty(e.getChildrenStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getChildrenStatus()))) {
                e.setChildrenStatus(dictCommonMap.get(Integer.parseInt(e.getChildrenStatus())).getName());
            }
            if (StringUtils.notEmpty(e.getEducation()) && dictCommonMap.containsKey(Integer.parseInt(e.getEducation()))) {
                e.setEducation(dictCommonMap.get(Integer.parseInt(e.getEducation())).getName());
            }
            if (StringUtils.notEmpty(e.getPoliticalStatus()) && dictCommonMap.containsKey(Integer.parseInt(e.getPoliticalStatus()))) {
                e.setPoliticalStatus(dictCommonMap.get(Integer.parseInt(e.getPoliticalStatus())).getName());
            }
        }
        Workbook workbook = ExcelExportUtil.exportExcel(params, EmployeeExcel.class, list, map);
        if (workbook == null) {
            return 0;
        }
        // 设置excel的文件名称
        String excelName = "wuzhouhanyun";
        // 重置响应对象
        response.reset();
        // 当前日期，用于导出文件名称
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "[" + excelName + "-" + sdf.format(new Date()) + "]";
        // 指定下载的文件名--设置响应头
        /*
        Content-Disposition参数：
        attachment --- 作为附件下载
        inline --- 在线打开
         */
        response.setHeader("Content-Disposition", "attachment;filename=" + dateStr + ".xlsx");
        //将查询结果导出到Excel
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        //不要网页存于缓存之中,没有缓存
        response.setHeader("Pragma", "NO-cache");
        response.setHeader("Cache-Control", "NO-cache");
        //设置过期的时间期限
        response.setDateHeader("Expires", 0);
        // 写出数据输出流到页面
        try {
            ServletOutputStream output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
