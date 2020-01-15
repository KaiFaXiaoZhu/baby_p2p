package com.baby.controller.system;

import com.baby.pojo.SystemDictionaryItem;
import com.baby.service.system.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController {
    @Resource
    SystemService systemService;

    /**
     * 加载数据字典
     * @return
     */
    @GetMapping("/dictionaryItem/getAll")
    @ResponseBody
    public Map<String,Object> getDictionaryItem(){
        Map<String,Object> result = new HashMap<>();
        List<SystemDictionaryItem> systemDictionaryItemList = null;
        try {
            systemDictionaryItemList = systemService.selectDictionaryItem();
            if(systemDictionaryItemList.size() > 0){
                result.put("code",200);
                result.put("data",systemDictionaryItemList);
            } else {
                result.put("code",500);
                result.put("msg","加载字典失败");
            }
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","系统异常");
            e.printStackTrace();
        }
        return result;
    }

}
