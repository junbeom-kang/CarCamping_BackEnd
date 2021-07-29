package com.Hanium.CarCamping.domain.dto.campsite;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCampSiteDto {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    private String image;
    @NotBlank
    private String explanation;
    private Float score;
    private String videoLink;
    private String region;
}
