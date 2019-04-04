package com.yishi.code.general.example;

import com.yishi.code.general.annotation_.RequestMapping;
import com.yishi.code.general.annotation_.RequestParam;
import com.yishi.code.general.annotation_.ResponseBody;
import com.yishi.code.general.dto.Pages;
import com.yishi.code.general.dto.unalterable.Result;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/example"})
public class ExampleController {

    @Resource
    private ExampleServiceImpl exampleService;



    @RequestMapping({"/query"})
    @ResponseBody
    public List<Map> query(String unique___Name,@RequestParam Map var1) {
        return this.exampleService.query(unique___Name,var1);
    }

    @RequestMapping({"/queryByPage"})
    @ResponseBody
    public Pages<Map> queryByPage(String unique___Name, @RequestParam Map var2) {

        return this.exampleService.queryByPage(unique___Name, var2);
    }

    @ResponseBody
    @RequestMapping({"/save"})
    public Result save(String unique___Name, @RequestParam Map var1) {
        return this.exampleService.save(unique___Name,var1);
    }

    @RequestMapping({"/update"})
    @ResponseBody
    public Result update(String unique___Name,@RequestParam Map var1) {
        return this.exampleService.update(unique___Name,var1);
    }

    @RequestMapping({"/delete"})
    @ResponseBody
    public Result delete(String unique___Name,@RequestParam Map var1) {
        return this.exampleService.delete(unique___Name,var1);
    }
}






