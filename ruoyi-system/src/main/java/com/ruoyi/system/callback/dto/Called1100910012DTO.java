package com.ruoyi.system.callback.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Called1100910012DTO {
    /**
     * friend_serial_no: "B397DDA54D4629DA0E17231E11A607CD"
     * last_seen_ago: "08:59:35.6597896"
     * Is_bot: false
     * phone: "593999310256"
     * last_name: "Huffman"
     * first_name: "Rowe"
     * username: ""
     * status: {}
     * was_online: "2024-02-01T07:42:18Z"
     */

    private String friend_serial_no;

    private String last_seen_ago;

    private Boolean Is_bot;

    private String phone;

    private String last_name;

    private String first_name;

    private String username;

    private Called1100910012StatusDTO status;

    @Data
    public static class Called1100910012StatusDTO {
        private LocalDateTime was_online;
    }

}
