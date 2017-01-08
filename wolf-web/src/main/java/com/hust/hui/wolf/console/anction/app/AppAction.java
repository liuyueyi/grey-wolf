package com.hust.hui.wolf.console.anction.app;

import com.hust.hui.wolf.console.anction.base.Action;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yihui on 2017/1/8.
 */
@Controller
public class AppAction implements Action {


    @RequestMapping(value = {"/app/list", "/"}, method = {RequestMethod.GET})
    public String execute(ModelMap model) {
        return "app/list";
    }

}
