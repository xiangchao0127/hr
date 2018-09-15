package com.handge.hr.behavior.service.api.professional;


import com.handge.hr.domain.entity.behavior.web.request.professional.RankingParam;

/**
 * Created by DaLu Guo on 2018/6/12.
 */
public interface IRanking {
    /**
     * 排名
     */
    Object getRanking(RankingParam rankingParam);
}
