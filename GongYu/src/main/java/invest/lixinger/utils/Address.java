package invest.lixinger.utils;

import lombok.*;

@Setter
@Getter
@ToString
public class Address {
    private String lines;
    private String city;
    private String state;
    private Integer postal;
}
