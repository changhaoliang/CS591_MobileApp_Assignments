package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Equipment;
import com.example.ingredieat.entity.Ingredient;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EquipmentDao {

    @Select("SELECT * FROM `equipment` WHERE `id` = #{id}")
    Equipment getEquipmentById(int id);

    @Insert("INSERT INTO `equipment`(`id`, `name`) VALUES(#{id}, #{name})")
    void insertEquipment(Equipment equipment);

}
