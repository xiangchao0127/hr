package com.handge.hr.behavior.common.repository;

import com.handge.hr.behavior.common.utils.FormulaUtil;
import com.handge.hr.common.enumeration.behavior.EmployeeStatusEnum;
import com.handge.hr.common.utils.NumberUtil;
import com.handge.hr.domain.entity.behavior.web.response.professional.ProfessionalAccomplishmentResult;
import com.handge.hr.domain.entity.behavior.web.response.statistics.IpRegionOfCountry;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class BaseDAOImpl implements IBaseDAO {
    @Autowired
    BehaviorLibProfessionalAccomplishmentMapper behaviorLibProfessionalAccomplishmentMapper;
    @Autowired
    EntityDepartmentMapper entityDepartmentMapper;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    BehaviorEntityDeviceBasicMapper behaviorEntityDeviceBasicMapper;
    @Autowired
    BehaviorConfigParamMapper behaviorConfigParamMapper;
    @Autowired
    BehaviorDictCountryBasicMapper behaviorDictCountryBasicMapper;
    @Autowired
    BehaviorTagPropertyMapper behaviorTagPropertyMapper;

    Log logger = LogFactory.getLog(this.getClass());
    @Override
    public Integer totalNumberOfEmployeesOnGuard() {
        return entityEmployeeMapper.countTotalNumberOfEmployeesOnGuard(Integer.parseInt(EmployeeStatusEnum.QUIT.getStatus()));
    }
    @Override
    public HashMap<String, Integer> numberOfEmployeesGroupByDep() {
        List<HashMap<String, Object>> mapList = entityEmployeeMapper.getNumberOfEmployeesGroupByDept();
        HashMap<String, Integer> hashMapDepInfo = new HashMap<>();
        for (HashMap<String, Object> map : mapList) {
            hashMapDepInfo.put(map.get("name").toString(), Integer.parseInt(map.get("sumperson").toString()));
        }
        return hashMapDepInfo;
    }

    @Override
    public List<String> listTagsOfNonWorking() {
        return behaviorTagPropertyMapper.listTagsOfNonWorking();
    }

    @Override
    public List<String> listTagsOfWorking() {
        return behaviorTagPropertyMapper.listTagsOfWorking();
    }

    @Override
    public HashMap<String, String> getAllEmployeeIpAndNumber() {
        HashMap<String, String> hashMapDepInfo = new HashMap<>();
        List<HashMap<String, String>> allEmployeeIpAndNumber = entityEmployeeMapper.getAllEmployeeIpAndNumber();
        for (HashMap<String, String> map : allEmployeeIpAndNumber) {
            hashMapDepInfo.put(map.get("static_ip"), map.get("job_number"));
        }
        return hashMapDepInfo;
    }

    @Override
    public Map<String, String> getAllEmployeeNumberAndName() {
        HashMap<String, String> hashPersonInfo = new HashMap<>();
        List<EntityEmployee> entityEmployees = entityEmployeeMapper.selectAll();
        for(EntityEmployee e : entityEmployees){
            hashPersonInfo.put(e.getJobNumber(),e.getName());
        }
        return hashPersonInfo;
    }

    @Override
    public HashMap<String, String> getJobClass() {
        HashMap<String, String> hashMap = new HashMap<>();
        List<BehaviorTagProperty> behaviorTagProperties = behaviorTagPropertyMapper.selectAll();
        for(BehaviorTagProperty behaviorTagProperty : behaviorTagProperties){
            hashMap.put(behaviorTagProperty.getTagName(),behaviorTagProperty.getProperty());
        }
        return hashMap;
    }


    @Override
    public HashMap<String, ArrayList<String>> getEmployeeIps(String departmentName) {
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        List<HashMap<String, String[]>> mapList = entityEmployeeMapper.getEmployeeIpsMap(departmentName);
        for(HashMap<String, String[]> map : mapList){
            for(String key : map.keySet()){
                if("name".equals(key)){
                    hashMap.put(String.valueOf(map.get(key)),new ArrayList<>(Arrays.asList(map.get("array_agg"))));
                }
            }
        }
        return hashMap;
    }

    @Override
    public Map<Long, Object[]> getIpRegionOfCountry() {
        Map<Long, Object[]> countryMap = new TreeMap<>();
        List<IpRegionOfCountry> ip = behaviorDictCountryBasicMapper.getIp();
        ip.forEach(o -> {
            long start = NumberUtil.transferIp2Number(o.getStart());
            long end = NumberUtil.transferIp2Number(o.getEnd());
            countryMap.put(start,new Object[]{end,o.getName()});
        });
        return countryMap;
    }

    @Override
    public Map<String, Object[]> getInfoOfCountry() {
        Map<String, Object[]> result = new HashMap<>();
        List<BehaviorDictCountryBasic> countryBasics = behaviorDictCountryBasicMapper.selectAll();
        countryBasics.forEach(o -> {
            result.put(o.getName(),new Object[]{o.getNickName(),o.getCapitalGeo()});
        });
        return result;
    }

   /* @Override
    public ArrayList<AppIdentify> getTagInfo() {
        ArrayList<AppIdentify> appIdentifys = new ArrayList<>();
        String sql = "select a.app_name,a.web_url,a.keyword2,a.keyword3,a.basic_class from app_basic as a";
        ResultSet rs = mySQLProxy.queryBySQL(sql);
        try {
            while (rs.next()) {
                AppIdentify appIdentify = new AppIdentify();
                appIdentify.setApp_name(rs.getString(1));
                appIdentify.setWebsite(rs.getString(2));
                appIdentify.setKeyword2(rs.getString(3));
                appIdentify.setKeyword3(rs.getString(4));
                appIdentify.setBasicClass(rs.getString(5));
                appIdentifys.add(appIdentify);
            }
        } catch (Exception e) {
             throw new UnifiedException(e);
        }
        return appIdentifys;
    }*/

    @Override
    public Map<String, Object> getConfigParam() {
        Map<String, Object> result = new HashMap<>();
        List<BehaviorConfigParam> configParams = behaviorConfigParamMapper.selectAll();
        configParams.forEach(o -> {
            result.put(o.getParamName(),o.getParamValue());
        });
        return result;
    }

    @Override
    public List<String> getIpsByNo(String no) {
        return behaviorEntityDeviceBasicMapper.getIpsByNumber(no);
    }

    @Override
    public String[] getDepInfoByNo(String no) {
        String[] depInfo = new String[2];
        Map<String,Object> map = entityDepartmentMapper.getDepartmentCountByNo(no, Integer.valueOf(EmployeeStatusEnum.QUIT.getStatus()));
        depInfo[0] = map.get("name").toString();
        depInfo[1] = map.get("count").toString();
        logger.debug(depInfo[0]);
        logger.debug(depInfo[1]);
        return depInfo;
    }

    @Override
    public List<ProfessionalAccomplishmentResult> getProfessionalAccomplishmentResults(String yearMonth, boolean flag) {
        List<BehaviorLibProfessionalAccomplishment> accomplishments;
        if (flag) {
            String year = yearMonth.substring(0, 4) + "00";
            accomplishments = behaviorLibProfessionalAccomplishmentMapper.getModleResultRangeTime(yearMonth,year);
        } else {
            accomplishments = behaviorLibProfessionalAccomplishmentMapper.getModelResultByTime(yearMonth);
        }
        List<ProfessionalAccomplishmentResult> professionalAccomplishmentResults = new ArrayList<>();
        accomplishments.forEach(o -> {
            ProfessionalAccomplishmentResult professionalAccomplishmentResult = new ProfessionalAccomplishmentResult();
            professionalAccomplishmentResult.setStaticIp(o.getStaticIp());
            professionalAccomplishmentResult.setTime(o.getTime());
            professionalAccomplishmentResult.setWorkingAttitude(FormulaUtil.calculateComprehensiveScore(new BigDecimal(o.getWorkingAttitude()),
                    new BigDecimal(o.getLoyalty()),new BigDecimal(o.getComplianceDiscipline())).toString());
            professionalAccomplishmentResults.add(professionalAccomplishmentResult);
        });
        return professionalAccomplishmentResults;
    }


    @Override
    public List<String> getDepIpsByNo(String no) {
        return behaviorEntityDeviceBasicMapper.getDepartmentIpsByNo(no);
    }

    @Override
    public String getEmployeeNameByNumber(String number) {
        EntityEmployee entityEmployee = new EntityEmployee();
        entityEmployee.setJobNumber(number);
        String name = entityEmployeeMapper.selectOne(entityEmployee).getName();
        return name;
    }

    @Override
    public List<String> getNumbersByDepName(String departmentName) {
        return entityDepartmentMapper.getNumbersByDepartmentName(departmentName);
    }

    @Override
    public boolean isExistInLib(ArrayList<String> ips,String month) {
        BehaviorLibProfessionalAccomplishment behaviorLibProfessionalAccomplishment = new BehaviorLibProfessionalAccomplishment();
        List<BehaviorLibProfessionalAccomplishment> list = new ArrayList<>();
        ips.forEach(o -> {
            behaviorLibProfessionalAccomplishment.setStaticIp(o);
            behaviorLibProfessionalAccomplishment.setTime(month.replace("-",""));
            BehaviorLibProfessionalAccomplishment accomplishment = behaviorLibProfessionalAccomplishmentMapper.selectOne(behaviorLibProfessionalAccomplishment);
            if(!(accomplishment == null)){
                list.add(accomplishment);
            }
        });
        if (list.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
