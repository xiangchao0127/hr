package com.handge.hr.behavior.service.impl.professional;

import com.handge.hr.common.enumeration.behavior.StaffModelEnum;
import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.behavior.web.request.professional.StaffDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.professional.TopOfStaffParam;
import com.handge.hr.domain.entity.behavior.web.response.professional.Staff;
import com.handge.hr.behavior.service.api.professional.IGoodOrBad;
import com.handge.hr.behavior.common.utils.FormulaUtil;
import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.utils.DateUtil;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.mapper.BehaviorEntityDeviceBasicMapper;
import com.handge.hr.domain.repository.mapper.BehaviorLibProfessionalAccomplishmentMapper;
import com.handge.hr.domain.repository.pojo.BehaviorLibProfessionalAccomplishment;
import com.handge.hr.exception.custom.UnifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @author MaJianfu
 * @date 2018/6/15 13:08
 **/
@Component
public class GoodOrBadImpl implements IGoodOrBad {
    /**
     * 优秀员工 TOP5 或者 差劲员工 TOP5
     *
     * @return
     */

    @Autowired
    IBaseDAO baseDAO;

    @Autowired
    BehaviorEntityDeviceBasicMapper deviceBasicMapper;


    @Autowired
    BehaviorLibProfessionalAccomplishmentMapper libProfessionalAccomplishmentMapper;


    @Override
    public Object listTopOfStaff(TopOfStaffParam topOfStaffParam) {
        HashMap<String, String> stringStringHashMap = scoreStaff(topOfStaffParam.getStartTime(),topOfStaffParam.getDepartment());
        if(stringStringHashMap == null || stringStringHashMap.size() == 0){
            List<Staff> resultStaffTotal = new ArrayList<>();
            Staff sta=new Staff();
            resultStaffTotal.add(sta);
            return resultStaffTotal;
        }else{
        List<Staff> resultStaff = staffList(stringStringHashMap);
        if(StaffModelEnum.EXCELLENT_STAFF.getModel().equals(topOfStaffParam.getModel())){
            List<Staff> resultStaffTotal = bestStaff(resultStaff);
            List<Staff> topOfBestStaff = topOfBestStaff(topOfStaffParam.getN(), resultStaffTotal);
            return topOfBestStaff.subList(0, topOfBestStaff.size() >topOfStaffParam.getN() ? topOfStaffParam.getN() :topOfBestStaff.size());
        }else{
            List<Staff> topOfPoorStaff = topOfPoorStaff(topOfStaffParam.getN(), resultStaff);
            return topOfPoorStaff.subList(0, topOfPoorStaff.size() >topOfStaffParam.getN() ? topOfStaffParam.getN() :topOfPoorStaff.size());
        }
        }
    }
    /**
     * 优秀员工排名 或者 差劲员工排名
     *
     * @return
     */
    @Override
    public Object listStaffDetail(StaffDetailParam staffDetailParam) {
        HashMap<String, String> stringStringHashMap = scoreStaff(staffDetailParam.getStartTime(),staffDetailParam.getDepartment());
        if(stringStringHashMap == null || stringStringHashMap.size() == 0){
            List<Staff> resultStaffTotal = new ArrayList<>();
            Staff sta=new Staff();
            resultStaffTotal.add(sta);
            return resultStaffTotal;
        }else{
        List<Staff> resultStaff = staffList(stringStringHashMap);
        if(StaffModelEnum.EXCELLENT_STAFF.getModel().equals(staffDetailParam.getModel())){
            List<Staff> resultStaffTotal = bestStaff(resultStaff);
            PageResults<Staff> pageResult = CollectionUtils.getPageResult(resultStaffTotal, staffDetailParam.getPageNo(), staffDetailParam.getPageSize());
            return pageResult;
        }else{
            List<Staff> poorStaffList = poorStaff(resultStaff);
            PageResults<Staff> pageResult = CollectionUtils.getPageResult(poorStaffList, staffDetailParam.getPageNo(), staffDetailParam.getPageSize());
            return pageResult;
        }
        }
    }

    @Override
    public HashMap<String, String> scoreStaff(String date,String departmentName) {
        if(StringUtils.notEmpty(date)){
            date= date.substring(0, 7).replace("-", "");
        }else{
            try {
                date= DateUtil.getNextMonth(DateUtil.timeStampToStrDate(System.currentTimeMillis(), DateFormatEnum.DAY),-1).replace("-", "");
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
        }
            List<BehaviorLibProfessionalAccomplishment> resultByTime = libProfessionalAccomplishmentMapper.getModelResultByTime(date);
            DecimalFormat dFormat = new DecimalFormat("#0.0");
            HashMap<String, String> ipMap = new HashMap<>();
            for(BehaviorLibProfessionalAccomplishment lib:resultByTime){
                String staticIp=lib.getStaticIp();
                String time=lib.getTime();
                String loyalty=lib.getLoyalty();
                String workingAttitude=lib.getWorkingAttitude();
                String complianceDiscipline=lib.getComplianceDiscipline();
                String avgScore= FormulaUtil.calculateComprehensiveScore(new BigDecimal(loyalty), new BigDecimal(workingAttitude), new BigDecimal(complianceDiscipline)).toString();
                ipMap.put(staticIp+"|"+time,loyalty+"|"+workingAttitude+"|"+complianceDiscipline+"|"+avgScore);
            }
        HashMap<String, ArrayList<String>> numberMap = new HashMap<>();
        HashMap<String, String> map = baseDAO.getAllEmployeeIpAndNumber();
        Map<String, Map<String, String>> ipDepMap = deviceBasicMapper.getStaticIpDepartment();
            for (Map.Entry<String,String> entry :ipMap.entrySet()) {
                String[] str = entry.getValue().split("\\|");
                String number ="";
                if(StringUtils.notEmpty(departmentName) && ipDepMap.containsKey(entry.getKey().split("\\|")[0]) ){
                    if(departmentName.equals(ipDepMap.get(entry.getKey().split("\\|")[0]).get("departmentName"))){
                        if(map.containsKey(entry.getKey().split("\\|")[0])){
                            number = map.get(entry.getKey().split("\\|")[0]);
                            if (numberMap.get(number+"|"+entry.getKey().split("\\|")[1]) == null) {
                                numberMap.put(number+"|"+entry.getKey().split("\\|")[1], new ArrayList<>(Arrays.asList(str[0]+"|"+str[1]+"|"+str[2]+"|"+str[3])));
                            } else {
                                numberMap.get(number+"|"+entry.getKey().split("\\|")[1]).add(str[0]+"|"+str[1]+"|"+str[2]+"|"+str[3]);
                            }
                        }
                    }
                }else{
                    if(map.containsKey(entry.getKey().split("\\|")[0])){
                        number = map.get(entry.getKey().split("\\|")[0]);
                        if (numberMap.get(number+"|"+entry.getKey().split("\\|")[1]) == null) {
                            numberMap.put(number+"|"+entry.getKey().split("\\|")[1], new ArrayList<>(Arrays.asList(str[0]+"|"+str[1]+"|"+str[2]+"|"+str[3])));
                        } else {
                            numberMap.get(number+"|"+entry.getKey().split("\\|")[1]).add(str[0]+"|"+str[1]+"|"+str[2]+"|"+str[3]);
                        }
                    }
                }

            }
            HashMap<String, String> scoreMap = new HashMap<>();
            for (Map.Entry<String, ArrayList<String>> entry : numberMap.entrySet()) {
                ArrayList<String> value = entry.getValue();
                Double loyalty=0.0;
                Double workingAttitude=0.0;
                Double complianceDiscipline=0.0;
                Double avgScore=0.0;
                int i=0;
                for(String scoreToTal:value){
                    String[] score = scoreToTal.split("\\|");
                    loyalty+= Double.parseDouble(score[0]);
                    workingAttitude+= Double.parseDouble(score[1]);
                    complianceDiscipline+= Double.parseDouble(score[2]);
                    avgScore+= Double.parseDouble(score[3]);
                    i++;
                }
                String loyaltyTotal=dFormat.format(loyalty/i);
                String workingAttitudeTotal=dFormat.format(workingAttitude/i);
                String complianceDisciplineTotal=dFormat.format(complianceDiscipline/i);
                String avgScoreTotal=dFormat.format(avgScore/i);
                scoreMap.put(entry.getKey().split("\\|")[0]+"|"+entry.getKey().split("\\|")[1],loyaltyTotal+"|"+workingAttitudeTotal+"|"+complianceDisciplineTotal+"|"+avgScoreTotal);
            }
            return scoreMap;
    }

    public List<Staff> staffList(HashMap<String, String> scoreMap){
        List<Staff> resultStaff = new ArrayList<>();
            Map<String, String> numberNameMap = baseDAO.getAllEmployeeNumberAndName();
        for (Map.Entry<String,String> entry :scoreMap.entrySet()) {
                String name ="";
                //String grade ="";
            if(numberNameMap.containsKey(entry.getKey().split("\\|")[0])){
                name = numberNameMap.get(entry.getKey().split("\\|")[0]);
                }
                String[] value = entry.getValue().split("\\|");
                /*String professionalism =score(value[0]+"|"+ProfessionalismEnum.loyalty.getValue(),
                        value[1]+"|"+ProfessionalismEnum.working_attitude.getValue(),
                        value[2]+"|"+ProfessionalismEnum.compliance_discipline.getValue());
            double v = Double.parseDouble(professionalism.split("\\|")[0]);
            if(v >= 0 && v <= 20){
                grade= GradeEnum.E.getValue();
            }else if(v > 20 && v <= 40){
                grade= GradeEnum.D.getValue();
            }else if(v > 40 && v <= 60){
                grade= GradeEnum.C.getValue();
            }else if(v > 60 && v <= 80){
                grade= GradeEnum.B.getValue();
            }else if(v > 80 && v <= 100){
                grade= GradeEnum.A.getValue();
            }*/
                Staff staff=new Staff();
                staff.setNumber(entry.getKey().split("\\|")[0]);
                staff.setName(name);
                staff.setScore(value[3]);
                //staff.setProfessionalism(professionalism.split("\\|")[1]);
                //staff.setGrade(grade);
                resultStaff.add(staff);
            }
            return resultStaff;
    }

    /*public String score(String a,String b,String c){
        Double double1=Double.parseDouble(a.split("\\|")[0]);
        Double double2=Double.parseDouble(b.split("\\|")[0]);
        Double double3=Double.parseDouble(c.split("\\|")[0]);
        Double min= (double1<double2) ? double1 : double2;
        min = (min <double3) ? min :double3;
        if(min.equals(double1)){
            return a;
        }else if(min.equals(double2)){
            return b;
        }else{
            return c;
        }

    }*/

    public List<Staff> bestStaff(List<Staff> resultStaff){
        List<Staff> resultStaffTotal = new LinkedList<>();
        Collections.sort(resultStaff, new Comparator<Staff>() {
            @Override
            public int compare(Staff o1, Staff o2) {
                return -Double.compare(Double.parseDouble(o1.getScore()),
                        Double.parseDouble(o2.getScore()));
            }
        });
        int i=1;
        for(Staff st:resultStaff){
            Staff staff=new Staff();
            staff.setNum(String.valueOf(i));
            staff.setName(st.getName());
            staff.setScore(st.getScore());
            staff.setNumber(st.getNumber());
            resultStaffTotal.add(staff);
            i++;
        }
        return resultStaffTotal;
    }

    public List<Staff> topOfBestStaff(int n, List<Staff> resultStaffTotal){
        int i = 0;
        List<Staff> list = new LinkedList<>();
        for (Staff mapping : resultStaffTotal) {
            Staff staff=new Staff();
            staff.setName(mapping.getName());
            staff.setScore(mapping.getScore());
            list.add(staff);
            i++;
            if (i == n) {
                break;
            }
        }
        return list;
    }

    public List<Staff> poorStaff(List<Staff> resultStaff){
        List<Staff> poorStaffTotal = new LinkedList<>();
        Collections.sort(resultStaff, new Comparator<Staff>() {
            @Override
            public int compare(Staff o1, Staff o2) {
                return Double.compare(Double.parseDouble(o1.getScore()),
                        Double.parseDouble(o2.getScore()));
            }
        });
        int i=1;
        for(Staff sta:resultStaff){
            Staff staff=new Staff();
            staff.setNum(String.valueOf(i));
            staff.setName(sta.getName());
            staff.setScore(sta.getScore());
            staff.setNumber(sta.getNumber());
            poorStaffTotal.add(staff);
            i++;
        }
        return poorStaffTotal;
    }

    public List<Staff> topOfPoorStaff(int n, List<Staff> resultStaff){
        List<Staff> staffList = new LinkedList<>();
        Collections.sort(resultStaff, new Comparator<Staff>() {
            @Override
            public int compare(Staff o1, Staff o2) {
                return Double.compare(Double.parseDouble(o1.getScore()),
                        Double.parseDouble(o2.getScore()));
            }
        });
        int i = 0;
        for(Staff sta:resultStaff){
            Staff staff=new Staff();
            staff.setName(sta.getName());
            staff.setScore(sta.getScore());
            //staff.setProfessionalism(sta.getProfessionalism());
            //staff.setGrade(sta.getGrade());
            staffList.add(staff);
            i++;
            if (i == n) {
                break;
            }
        }
        return staffList;
    }
}
