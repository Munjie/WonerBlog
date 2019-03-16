package com.mwj.personweb.dao;

import com.mwj.personweb.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/16 00:15 @Description: */
@Mapper
@Repository
public interface IMessageDao {

  @Select("select * from message where sysuser=#{sysuser} and status = 1 order by id desc")
  List<Message> findAllMsg(String name);

  @Insert(
      " insert into message (id, articleid, sysuser,creattime, status, comname,msg, hdimg) values (#{id}, #{articleid}, #{sysuser},#{creattime}, #{status}, #{comname},#{msg},#{hdimg})")
  int addMessage(Message message);

  @Update("update message  set status = 0  where id = #{id,jdbcType=INTEGER}")
  int updateMessage(int id);
}
