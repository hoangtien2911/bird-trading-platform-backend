package com.gangoffive.birdtradingplatform.dto;

import com.gangoffive.birdtradingplatform.entity.TypeAccessory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessoryDto extends ProductDto{

    private String origin;
    private TypeAccessory typeAccessory;


}
