package com.example.ingredieat.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StepEquipmentDao {

    @Select("SELECT `equipment_id` WHERE `step_id` = #{stepId}")
    List<Integer> listEquipmentsIdsByStepId(int id);
}
