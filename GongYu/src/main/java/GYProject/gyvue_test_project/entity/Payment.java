package GYProject.gyvue_test_project.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Payment {
    @Id
    private Integer id;
    private String serial;
}
