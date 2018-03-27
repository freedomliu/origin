package com.bedrock.origin.mapper;

import com.bedrock.origin.beans.areasize;

public interface areasizeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(areasize record);

    int insertSelective(areasize record);

    areasize selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(areasize record);

    int updateByPrimaryKey(areasize record);
}