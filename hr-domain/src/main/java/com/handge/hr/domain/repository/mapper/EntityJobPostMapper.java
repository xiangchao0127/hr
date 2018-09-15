package com.handge.hr.domain.repository.mapper;

import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.archive.PostParam;
import com.handge.hr.domain.entity.manage.web.response.archive.PostRes;
import com.handge.hr.domain.repository.pojo.EntityJobPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface EntityJobPostMapper extends CommonMapper<EntityJobPost,String> {

    @SelectProvider(type = SqlBuilder.class, method = "buildGetJobPost")
    List<PostRes> getInformation(PostParam postParam);


    class SqlBuilder {
        public static String buildGetJobPost(PostParam postParam) {
            return new SQL() {{
                SELECT("post.name as name,post.description as description,post.remark as remark,common.name as status");
                FROM("entity_job_post post");
                JOIN("dict_common common on post.status=common.id");
                if (StringUtils.notEmpty(postParam.getName())) {
                    WHERE("post.name like '" + postParam.getName() + "'");
                }
                AND();
                if (StringUtils.notEmpty(postParam.getStatus())) {
                    WHERE("post.status = '" + postParam.getStatus() + "'");
                }
                AND();
            }}.toString();
        }
    }
}

