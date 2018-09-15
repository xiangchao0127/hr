package com.handge.hr.behavior.service.impl.professional;

import com.handge.hr.common.enumeration.behavior.ModeEnum;
import com.handge.hr.domain.entity.behavior.web.request.professional.ProfessionalAccomplishmentParam;
import com.handge.hr.domain.entity.behavior.web.response.professional.ProfessionalAccomplishment;
import com.handge.hr.behavior.service.api.professional.IProfessionalAccomplishment;
import com.handge.hr.behavior.common.utils.FormulaUtil;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.mapper.BehaviorLibProfessionalAccomplishmentMapper;
import com.handge.hr.domain.repository.pojo.BehaviorLibProfessionalAccomplishment;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DaLu Guo on 2018/6/12.
 */
@Component
public class ProfessionalAccomplishmentImpl implements IProfessionalAccomplishment {
    /**
     * 基础数据库查询Bean
     */
    @Autowired
    IBaseDAO baseDAO;
    @Autowired
    BehaviorLibProfessionalAccomplishmentMapper behaviorLibProfessionalAccomplishmentMapper;

    @Override
    public Object getProfessionalAccomplishment(ProfessionalAccomplishmentParam professionalAccomplishmentParam) {
        String searchTime = null;
        try {
            searchTime = DateUtil.str2Str(professionalAccomplishmentParam.getStartTime(), DateFormatEnum.MONTHNEW);
        } catch (ParseException e) {
            throw new UnifiedException(e);
        }
        List<String> ips = new ArrayList<>();
        if (ModeEnum.PERSON.getMode().equals(professionalAccomplishmentParam.getModel())) {
            if (StringUtils.isEmpty(professionalAccomplishmentParam.getNumber())) {
                throw new UnifiedException("工号", ExceptionWrapperEnum.IllegalArgumentException);
            }
            String number = professionalAccomplishmentParam.getNumber();
            ips = baseDAO.getIpsByNo(number);

        } else if (ModeEnum.DEPARTMENT.getMode().equals(professionalAccomplishmentParam.getModel())) {
            if (StringUtils.isEmpty(professionalAccomplishmentParam.getDepartment())) {
                throw new UnifiedException("部门", ExceptionWrapperEnum.IllegalArgumentException);
            }
            String departName = professionalAccomplishmentParam.getDepartment();
            ips = baseDAO.getEmployeeIps(departName).get(departName);

        } else {
            for (String key : baseDAO.getEmployeeIps("").keySet()) {
                ips.addAll(baseDAO.getEmployeeIps("").get(key));
            }
        }
        Map<String, String[]> result = getIpLevel(searchTime, ips);
        if(result.size()>0) {
            return calculateScores(result);
        }
        else {
            return new ProfessionalAccomplishment() {{
                this.setComplianceDiscipline("");
                this.setComprehensiveScore("");
                this.setWorkingAttitude("");
                this.setLoyalty("");
            }};
        }
    }

    public Map<String, String[]> getIpLevel(String time, List<String> ips) {
        Map<String, String[]> result = new HashMap<>();
        Example example = new Example(BehaviorLibProfessionalAccomplishment.class);
        example.createCriteria().andEqualTo("time",time).andIn("staticIp",ips);
        List<BehaviorLibProfessionalAccomplishment> score = behaviorLibProfessionalAccomplishmentMapper.selectByExample(example);
        score.forEach(o -> {
            String[] level = new String[3];
            level[0] = o.getWorkingAttitude();
            level[1] = o.getLoyalty();
            level[2] = o.getComplianceDiscipline();
            result.put(o.getStaticIp(), level);
        });
        return result;
    }

    private ProfessionalAccomplishment calculateScores(Map<String, String[]> result) {

        List<BigDecimal> workingAttitudeScores = new ArrayList<>();
        List<BigDecimal> loyaltyScores = new ArrayList<>();
        List<BigDecimal> complianceDisciplineScores = new ArrayList<>();
        List<BigDecimal> ipScores = new ArrayList<>();
        for (String ip : result.keySet()) {
            workingAttitudeScores.add(new BigDecimal(result.get(ip)[0]));
            loyaltyScores.add(new BigDecimal(result.get(ip)[1]));
            complianceDisciplineScores.add(new BigDecimal(result.get(ip)[2]));
            BigDecimal ipScore = FormulaUtil.calculateComprehensiveScore(new BigDecimal(result.get(ip)[0]), new BigDecimal(result.get(ip)[1]), new BigDecimal(result.get(ip)[2]));
            ipScores.add(ipScore);
        }
        ProfessionalAccomplishment professionalAccomplishment = new ProfessionalAccomplishment();
        BigDecimal workingAttitudeScore = FormulaUtil.avgScore(workingAttitudeScores);
        BigDecimal loyaltyScore = FormulaUtil.avgScore(loyaltyScores);
        BigDecimal complianceDisciplineScore = FormulaUtil.avgScore(complianceDisciplineScores);
        BigDecimal comprehensiveScore = FormulaUtil.avgScore(ipScores);
        professionalAccomplishment.setWorkingAttitude(String.valueOf(workingAttitudeScore));
        professionalAccomplishment.setLoyalty(String.valueOf(loyaltyScore));
        professionalAccomplishment.setComplianceDiscipline(String.valueOf(complianceDisciplineScore));
        professionalAccomplishment.setComprehensiveScore(comprehensiveScore.toString());
        return professionalAccomplishment;
    }
}
