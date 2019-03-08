package com.mwj.personweb.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-08 15:47 @Version 1.0
 */
@Mapper
@Repository
public interface IArchiveDao {

    @Select("select archiveName from archives order by id desc")
    List<String> findArchives();

    @Insert("insert into archives(archiveName) values(#{archiveName})")
    void addArchiveName(@Param("archiveName") String archiveName);

    @Select("select IFNULL(max(id),0) from archives where archiveName=#{archiveName}")
    int findArchiveNameByArchiveName(@Param("archiveName") String archiveName);
}
