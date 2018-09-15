package com.handge.hr.behavior.service.impl.professional;

import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.behavior.web.request.professional.ProfessionalAccomplishmentByDepartmentDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.professional.TopOfProfessionalAccomplishmentByDepartmentParam;
import com.handge.hr.domain.entity.behavior.web.response.professional.ProfessionalAccomplishmentByDepartment;
import com.handge.hr.domain.entity.behavior.web.response.professional.ProfessionalAccomplishmentResult;
import com.handge.hr.behavior.service.api.professional.IProfessionalAccomplishmentByDepartment;
import com.handge.hr.behavior.common.utils.FormulaUtil;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.exception.custom.UnifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @author MaJianfu
 * @date 2018/6/15 13:09
 **/
@Component
public class ProfessionalAccomplishmentByDepartmentImpl implements IProfessionalAccomplishmentByDepartment {

    @Autowired
    IBaseDAO baseDAO;

    /**
     * 部门职业素养 TOP5
     *
     * @return
     */
    @Override
    public Object listTopOfProfessionalAccomplishmentByDepartment(TopOfProfessionalAccomplishmentByDepartmentParam topOfProfessionalAccomplishmentByDepartmentParam) {
        HashMap<String, String> deptScoreMap = deptScoreMap(topOfProfessionalAccomplishmentByDepartmentParam.getStartTime());
        List<ProfessionalAccomplishmentByDepartment> professionalAccomplishmentByDepartment = professionalAccomplishmentByDepartment(deptScoreMap);
        List<ProfessionalAccomplishmentByDepartment> result = topOfProfessionalAccomplishmentByDepartment(topOfProfessionalAccomplishmentByDepartmentParam.getN(), professionalAccomplishmentByDepartment);
        if(result == null || result.size() == 0){
            List<ProfessionalAccomplishmentByDepartment> resultNew = new ArrayList<>();
            ProfessionalAccomplishmentByDepartment pro=new ProfessionalAccomplishmentByDepartment();
            resultNew.add(pro);
            return resultNew;
        }else{
            return result;
        }

    }
    /**
     * 部门职业素养排名
     *
     * @return
     */
    @Override
    public Object listProfessionalAccomplishmentByDepartmentDetail(ProfessionalAccomplishmentByDepartmentDetailParam professionalAccomplishmentByDepartmentDetailParam) {
        HashMap<String, String> deptScoreMap = deptScoreMap(professionalAccomplishmentByDepartmentDetailParam.getStartTime());
        List<ProfessionalAccomplishmentByDepartment> professionalAccomplishmentByDepartment = professionalAccomplishmentByDepartment(deptScoreMap);
            if(professionalAccomplishmentByDepartment == null || professionalAccomplishmentByDepartment.size() == 0){
                List<ProfessionalAccomplishmentByDepartment> resultNew = new ArrayList<>();
                ProfessionalAccomplishmentByDepartment pro=new ProfessionalAccomplishmentByDepartment();
                resultNew.add(pro);
                return resultNew;
            }else{
                PageResults<ProfessionalAccomplishmentByDepartment> pageResult = CollectionUtils.getPageResult(professionalAccomplishmentByDepartment, professionalAccomplishmentByDepartmentDetailParam.getPageNo(), professionalAccomplishmentByDepartmentDetailParam.getPageSize());
                return pageResult;
            }

        }

    public HashMap<String,String> deptScoreMap(String date){
        if(StringUtils.notEmpty(date)){
            date= date.substring(0, 7).replace("-", "");
        }else{
            try {
                date= DateUtil.getNextMonth(DateUtil.timeStampToStrDate(System.currentTimeMillis(), DateFormatEnum.DAY),-1).replace("-", "");
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
        }
        List<ProfessionalAccomplishmentResult> results = baseDAO.getProfessionalAccomplishmentResults(date, false);
        HashMap<String, ArrayList<String>> employeeIps = baseDAO.getEmployeeIps(null);
        HashMap<String, ArrayList<BigDecimal>> depScores=new HashMap<>();
        for(HashMap.Entry<String, ArrayList<String>> map:employeeIps.entrySet()){
                for(ProfessionalAccomplishmentResult pro:results){
                    if(map.getValue().contains(pro.getStaticIp())){
                        if (depScores.get(map.getKey()) == null) {
                            depScores.put(map.getKey(), new ArrayList<>(Arrays.asList(new BigDecimal(pro.getWorkingAttitude()))));
                        } else {
                            depScores.get(map.getKey()).add(new BigDecimal(pro.getWorkingAttitude()));
                        }
                    }
                }
        }
        HashMap<String,String> deptScoreMap=new HashMap<>();
        for(HashMap.Entry<String, ArrayList<BigDecimal>> depMap:depScores.entrySet()){
            ArrayList<BigDecimal> value = depMap.getValue();
            deptScoreMap.put(depMap.getKey(), FormulaUtil.avgScore(value).toString());
        }
        return deptScoreMap;
    }

    public List<ProfessionalAccomplishmentByDepartment> professionalAccomplishmentByDepartment(HashMap<String,String> deptScoreMap){
        List<Map.Entry<String,String>> deptScorelList = new ArrayList<Map.Entry<String, String>>(deptScoreMap.entrySet());
        Collections.sort(deptScorelList, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return -Double.compare(Double.parseDouble(o1.getValue()),
                        Double.parseDouble(o2.getValue()));
            }
        });
        List<ProfessionalAccomplishmentByDepartment> result = new LinkedList<>();
        int i=1;
        for(Map.Entry<String, String> mapping:deptScorelList){
            ProfessionalAccomplishmentByDepartment pro=new ProfessionalAccomplishmentByDepartment();
            pro.setNum(String.valueOf(i));
            pro.setDepartment(mapping.getKey());
            pro.setScore(mapping.getValue());
            result.add(pro);
            i++;
        }
        return result;
    }

    public List<ProfessionalAccomplishmentByDepartment> topOfProfessionalAccomplishmentByDepartment(int n, List<ProfessionalAccomplishmentByDepartment> result){
        int i = 0;
        List<ProfessionalAccomplishmentByDepartment> resultTotal = new LinkedList<>();
        for(ProfessionalAccomplishmentByDepartment p:result){
            ProfessionalAccomplishmentByDepartment pro=new ProfessionalAccomplishmentByDepartment();
            pro.setDepartment(p.getDepartment());
            pro.setScore(p.getScore());
            resultTotal.add(pro);
            i++;
            if (i == n) {
                break;
            }
        }
        return resultTotal;
    }

}
