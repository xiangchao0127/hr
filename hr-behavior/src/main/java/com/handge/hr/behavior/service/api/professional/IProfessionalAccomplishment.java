package com.handge.hr.behavior.service.api.professional;


import com.handge.hr.domain.entity.behavior.web.request.professional.ProfessionalAccomplishmentParam;

/**
 * Created by DaLu Guo on 2018/6/12.
 */
public interface IProfessionalAccomplishment {
    /**
     * 职业素养
     */
    Object getProfessionalAccomplishment(ProfessionalAccomplishmentParam professionalAccomplishmentParam);
}
