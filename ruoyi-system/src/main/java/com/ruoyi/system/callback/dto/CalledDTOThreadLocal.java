package com.ruoyi.system.callback.dto;

import org.springframework.beans.BeanUtils;

public class CalledDTOThreadLocal {

    private static final ThreadLocal<CalledDTO> LOCAL = new ThreadLocal<>();

    public static void set(CalledDTO dto) {
        LOCAL.remove();

        LOCAL.set(dto);
    }

    public static CalledDTO getAndRemove() {
        CalledDTO l = LOCAL.get();

        CalledDTO val = new CalledDTO();
        BeanUtils.copyProperties(l, val);
        LOCAL.remove();
        return val;
    }

    public static void remove() {
        LOCAL.remove();
    }

}
