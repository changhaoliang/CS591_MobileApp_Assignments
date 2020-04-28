package com.example.ingredieat.dao;


import com.example.ingredieat.entity.Equipment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * This interface is used to operate the `equipment` table.
 */
@Mapper
public interface EquipmentDao {

    @Select("SELECT * FROM `equipment` WHERE `id` = #{id}")
    Equipment getEquipmentById(int id);

    @Select("SELECT `equipment`.`id`, `equipment`.`name` " +
            "FROM `equipment`, `step_equipment` " +
            "WHERE `equipment`.`id` = `step_equipment`.`equipment_id`" +
            "AND `step_equipment`.`step_id` = #{stepId}")
    List<Equipment> listEquipmentsByStepId(int stepId);

    @Insert("INSERT INTO `equipment`(`id`, `name`) VALUES(#{id}, #{name})")
    void insertEquipment(Equipment equipment);

}
