package com.handge.hr.domain.repository.mapper;



import tk.mybatis.mapper.additional.aggregation.AggregationMapper;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;



public interface CommonMapper<T, PK> extends Mapper<T>,InsertListMapper<T>,DeleteByIdListMapper<T, PK>,ConditionMapper<T>,SelectByIdListMapper<T,PK>,AggregationMapper<T> {

}
