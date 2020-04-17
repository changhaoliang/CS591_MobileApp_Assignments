package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EquipmentDao {

    @Select("SELECT * FROM `equipment` WHERE `id` = #{id}")
    Equipment getEquipmentById(int id);
}
