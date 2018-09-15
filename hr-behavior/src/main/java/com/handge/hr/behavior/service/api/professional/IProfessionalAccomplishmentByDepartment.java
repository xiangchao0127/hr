package com.handge.hr.behavior.service.api.professional;

import com.handge.hr.domain.entity.behavior.web.request.professional.ProfessionalAccomplishmentByDepartmentDetailParam;
import com.handge.hr.domain.entity.behavior.web.request.professional.TopOfProfessionalAccomplishmentByDepartmentParam;

/**
 * Created by DaLu Guo on 2018/6/12.
 */
public interface IProfessionalAccomplishmentByDepartment {
    /**
     * 部门职业素养 TOP5
     *
     * @param
     * @return
     * @author MaJianfu
     * @date 2018/6/12 16:01
     **/
    public Object listTopOfProfessionalAccomplishmentByDepartment(TopOfProfessionalAccomplishmentByDepartmentParam topOfProfessionalAccomplishmentByDepartmentParam);

    /**
     * 部门职业素养排名
     *
     * @param
     * @return
     * @author MaJianfu
     * @date 2018/6/12 16:01
     **/
    public Object listProfessionalAccomplishmentByDepartmentDetail(ProfessionalAccomplishmentByDepartmentDetailParam professionalAccomplishmentByDepartmentDetailParam);


}
