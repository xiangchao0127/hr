package com.handge.hr.manage.service.api.archive;


import com.handge.hr.domain.entity.manage.web.request.archive.InformationParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by MaJianfu on 2018/8/1.
 */
public interface IExcelInformationExport {

    public Integer exportInformationByTemplate(InformationParam information, HttpServletResponse response);
}
