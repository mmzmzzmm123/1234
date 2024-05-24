package com.jjpt.business.domain.dto;


import lombok.Data;
import java.util.List;

/**
 * @author bool
 */
@Data
public class PaperAnswerDTO extends PaperQuQueryDTO {


    private List<String> answers;


    private String answer;

}
