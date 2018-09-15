package com.handge.hr.manage.service.impl.archive;

import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.archive.IPost;
import com.handge.hr.common.utils.PageResults;
import com.handge.hr.common.utils.PageUtils;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.archive.PostParam;
import com.handge.hr.domain.entity.manage.web.response.archive.PostRes;
import com.handge.hr.domain.repository.mapper.DictCommonMapper;
import com.handge.hr.domain.repository.mapper.DictCommonTypeMapper;
import com.handge.hr.domain.repository.mapper.EntityJobPostMapper;
import com.handge.hr.domain.repository.pojo.EntityJobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @param
 * @author MaJianfu
 * @date 2018/8/3 9:48
 * @return
 **/
@Service
public class PostImpl implements IPost {

    @Autowired
    EntityJobPostMapper postMapper;

    @Autowired
    DictCommonMapper dictCommonMapper;

    @Autowired
    DictCommonTypeMapper dictCommonTypeMapper;

    @Override
    public int addPost(PostParam postParam) {
        EntityJobPost jobPost = new EntityJobPost();
        jobPost.setId(UuidUtils.getUUid());
        jobPost.setDescription(postParam.getDescription());
        jobPost.setGmtCreate(new Date());
        jobPost.setName(postParam.getName());
        jobPost.setRemark(postParam.getRemark());
        if (StringUtils.isEmpty(postParam.getStatus())) {
            //jobPost.setStatus(Integer.valueOf(dictCommonMapper.getId(DictCommonTypeEnum.POST_STATUS.getValue(), StatusEnum.normal.getValue())));
        } else {
            jobPost.setStatus(Integer.parseInt(postParam.getStatus()));
        }
        postMapper.insertSelective(jobPost);
        return 1;
    }

    @Override
    public int deletePost(PostParam postParam) {
        postMapper.deleteByIdList(postParam.getIds());
        return 1;
    }

    @Override
    public PageResults findPost(PostParam postParam) {
        PageUtils.startPage(postParam.getPageNo(), postParam.getPageSize());
        List<PostRes> list = postMapper.getInformation(postParam);
        PageResults<PostRes> results = PageUtils.getPageInfo(list);
        return results;
    }

    @Override
    public int modifyPost(PostParam postParam) {
        EntityJobPost jobPost = postMapper.selectByPrimaryKey(postParam.getId());
        jobPost.setGmtModified(new Date());
        jobPost.setDescription(postParam.getDescription());
        jobPost.setName(postParam.getName());
        jobPost.setRemark(postParam.getRemark());
        if (StringUtils.notEmpty(postParam.getStatus())) {
            jobPost.setStatus(Integer.parseInt(postParam.getStatus()));
        }
        postMapper.updateByPrimaryKey(jobPost);
        return 1;
    }
}
