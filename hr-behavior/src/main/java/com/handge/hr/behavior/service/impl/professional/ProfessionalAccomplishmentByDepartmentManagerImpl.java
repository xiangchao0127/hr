package com.handge.hr.behavior.service.impl.professional;

import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.behavior.web.request.professional.ProfessionalAccomplishmentByDepartmentManagerDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.professional.TopOfProfessionalAccomplishmentByDepartmentManagerParam;
import com.handge.hr.domain.entity.behavior.web.response.professional.ProfessionalAccomplishmentByDepartmentManager;
import com.handge.hr.behavior.service.api.professional.IGoodOrBad;
import com.handge.hr.behavior.service.api.professional.IProfessionalAccomplishmentByDepartmentManager;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.mapper.EntityDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by MaJianfu on 2018/6/13.
 */
@Component
public class ProfessionalAccomplishmentByDepartmentManagerImpl implements IProfessionalAccomplishmentByDepartmentManager {
    @Autowired
    private IGoodOrBad iGoodOrBad;
    @Autowired
    EntityDepartmentMapper entityDepartmentMapper;
    /**
     * 部门经理职业素养 TOP5
     *
     * @return
     */
    @Override
    public Object listTopOfProfessionalAccomplishmentByDepartmentManager(TopOfProfessionalAccomplishmentByDepartmentManagerParam topOfProfessionalAccomplishmentByDepartmentManagerParam) {
            List<ProfessionalAccomplishmentByDepartmentManager> result = result(topOfProfessionalAccomplishmentByDepartmentManagerParam.getStartTime());
            List<ProfessionalAccomplishmentByDepartmentManager> numlist = numlist(result);
            List<ProfessionalAccomplishmentByDepartmentManager> professionalAccomplishmentByDepartmentManagers = topNumlist(topOfProfessionalAccomplishmentByDepartmentManagerParam.getN(), numlist);
            if(professionalAccomplishmentByDepartmentManagers == null || professionalAccomplishmentByDepartmentManagers.size() == 0){
                List<ProfessionalAccomplishmentByDepartmentManager> resultNew = new ArrayList<>();
                ProfessionalAccomplishmentByDepartmentManager pro=new ProfessionalAccomplishmentByDepartmentManager();
                resultNew.add(pro);
                return resultNew;
            }else{
                return professionalAccomplishmentByDepartmentManagers;
            }
    }
    /**
     * 部门经理职业素养排名
     *
     * @return
     */
    @Override
    public Object listProfessionalAccomplishmentByDepartmentManagerDetail(ProfessionalAccomplishmentByDepartmentManagerDetailParam professionalAccomplishmentByDepartmentManagerDetailParam) {
            List<ProfessionalAccomplishmentByDepartmentManager> result = result(professionalAccomplishmentByDepartmentManagerDetailParam.getStartTime());
            List<ProfessionalAccomplishmentByDepartmentManager> numlist = numlist(result);
            if(numlist == null || numlist.size() == 0){
                List<ProfessionalAccomplishmentByDepartmentManager> resultNew = new ArrayList<>();
                ProfessionalAccomplishmentByDepartmentManager pro=new ProfessionalAccomplishmentByDepartmentManager();
                resultNew.add(pro);
                return resultNew;
            }else{
                PageResults<ProfessionalAccomplishmentByDepartmentManager> pageResult = CollectionUtils.getPageResult(numlist, professionalAccomplishmentByDepartmentManagerDetailParam.getPageNo(), professionalAccomplishmentByDepartmentManagerDetailParam.getPageSize());
                return pageResult;
            }

        }

    public List<ProfessionalAccomplishmentByDepartmentManager> result(String date){
        HashMap<String, String> numScoreMap = numberDep(date);
        List<ProfessionalAccomplishmentByDepartmentManager> result = entityDepartmentMapper.getDepartmentInfo();
        result.forEach(o -> {
            String score=numScoreMap.get(o.getNumber());
            if(StringUtils.notEmpty(score)) {
                o.setScore(score);
            }
        });
        return result;
    }

    public HashMap<String,String> numberDep(String date){
        HashMap<String, String> numberMap = iGoodOrBad.scoreStaff(date,null);
        HashMap<String, String> numScoreMap = new HashMap<>();
        for (Map.Entry<String,String> entry :numberMap.entrySet()) {
            numScoreMap.put(entry.getKey().split("\\|")[0],entry.getValue().split("\\|")[3]);
        }
        return numScoreMap;
    }

    public List<ProfessionalAccomplishmentByDepartmentManager> numlist(List<ProfessionalAccomplishmentByDepartmentManager> result){
        List<ProfessionalAccomplishmentByDepartmentManager> resultTotal = new LinkedList<>();
        Collections.sort(result, new Comparator<ProfessionalAccomplishmentByDepartmentManager>() {
            @Override
            public int compare(ProfessionalAccomplishmentByDepartmentManager o1, ProfessionalAccomplishmentByDepartmentManager o2) {
                return -Double.compare(Double.parseDouble(o1.getScore()),
                        Double.parseDouble(o2.getScore()));
            }
        });
        int i=1;
        for(ProfessionalAccomplishmentByDepartmentManager pr:result){
            ProfessionalAccomplishmentByDepartmentManager pro=new ProfessionalAccomplishmentByDepartmentManager();
            pro.setNum(String.valueOf(i));
            pro.setDepartment(pr.getDepartment());
            pro.setNumber(pr.getNumber());
            pro.setName(pr.getName());
            pro.setScore(pr.getScore());
            resultTotal.add(pro);
            i++;
        }
        return resultTotal;
    }

    public List<ProfessionalAccomplishmentByDepartmentManager> topNumlist(int n,List<ProfessionalAccomplishmentByDepartmentManager> resultTotal){
        int i = 0;
        List<ProfessionalAccomplishmentByDepartmentManager> resultTopNum = new LinkedList<>();
        for(ProfessionalAccomplishmentByDepartmentManager mapping:resultTotal){
            ProfessionalAccomplishmentByDepartmentManager profession=new ProfessionalAccomplishmentByDepartmentManager();
            profession.setDepartment(mapping.getDepartment());
            profession.setName(mapping.getName());
            profession.setNumber(mapping.getNumber());
            profession.setScore(mapping.getScore());
            resultTopNum.add(profession);
            i++;
            if (i == n) {
                break;
            }
        }
        return resultTopNum;
    }
}
