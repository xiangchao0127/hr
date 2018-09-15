package com.handge.hr.common.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by MaJianfu on 2018/8/3.
 */
public class PageUtils {

    public static <T> PageResults<T> getPageInfo(List<T> list){
        PageInfo<T> info = new PageInfo<T>(list);
        return new PageResults(info.getNextPage(),info.getPrePage()+1,info.getPageSize(),
                info.getTotal(),info.getPages(),info.getList());
    }

    public static void startPage(int pageNo,int pageSize){
        PageHelper.startPage(pageNo, pageSize);
    }

}
