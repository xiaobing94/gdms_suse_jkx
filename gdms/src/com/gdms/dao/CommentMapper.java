package com.gdms.dao;

import java.util.List;

import com.gdms.pojo.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);
    
    List<Comment> selectByDegreeId(Integer degreeId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}