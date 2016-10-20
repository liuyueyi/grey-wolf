package com.hust.hui.wolf.console.anction.base;

import com.hust.hui.wolf.console.anction.Action;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yihui on 16/9/25.
 */
@Controller
public class Route implements Action {

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String index() {
        return "app/list";
    }

    @RequestMapping(value = "/status", method = {RequestMethod.GET})
    @ResponseBody
    public String health() {
        return "SUCCESS";
    }
}
