package com.handge.hr.manage.service.api.archive;


import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.manage.web.request.archive.PostParam;

/**
 * @param
 * @author MaJianfu
 * @date 2018/8/3 10:03
 * @return
 **/
public interface IPost {
    /**
     * 添加职务
     *
     * @param postParam
     * @return
     */
    int addPost(PostParam postParam);

    /**
     * 删除职务
     *
     * @param postParam
     * @return
     */
    int deletePost(PostParam postParam);

    /**
     * 查询职务
     *
     * @param postParam
     * @return
     */
    PageResults findPost(PostParam postParam);

    /**
     * 修改职务
     *
     * @param postParam
     * @return
     */
    int modifyPost(PostParam postParam);
}
