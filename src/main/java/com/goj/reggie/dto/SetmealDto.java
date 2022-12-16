package com.goj.reggie.dto;

import com.goj.reggie.entity.Setmeal;
import com.goj.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
