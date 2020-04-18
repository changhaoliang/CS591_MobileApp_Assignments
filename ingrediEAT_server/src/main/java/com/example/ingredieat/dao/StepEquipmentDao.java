package com.example.ingredieat.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StepEquipmentDao {

    @Select("SELECT `equipment_id` FROM `step_equipment` WHERE `step_id` = #{stepId}")
    List<Integer> listEquipmentsIdsByStepId(int id);

    @Insert("INSERT INTO `step_equipment`(`step_id`, `equipment_id`) values(#{stepId}, #{equipmentId})")
    void insertStepEquipment(int stepId, int equipmentId);
}
