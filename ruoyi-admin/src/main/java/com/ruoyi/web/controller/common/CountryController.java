package com.ruoyi.web.controller.common;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.Country;
import com.ruoyi.system.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {
    @Resource
    private CountryService countryService;

    @Anonymous
    @GetMapping("/all")
    public R<List<Country>> all() {
        return R.ok(countryService.getAll());
    }
}
