package com.handge.hr.behavior.service.api.professional;


import com.handge.hr.domain.entity.behavior.web.request.professional.ScoreDistributionParam;

/**
 * Created by DaLu Guo on 2018/6/12.
 */
public interface IScoreDistribution {
    Object listScoreDistribution(ScoreDistributionParam scoreDistributionParam);
}
