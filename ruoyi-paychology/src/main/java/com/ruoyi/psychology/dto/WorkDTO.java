package com.ruoyi.psychology.dto;


import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class WorkDTO {

    private List<HeaderDTO> headers;

    private List<HashMap<String, String>> items;

}
