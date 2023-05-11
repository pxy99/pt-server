package icu.resip.web.controller;

import icu.resip.domain.order.Store;
import icu.resip.domain.order.Thing;
import icu.resip.result.Result;
import icu.resip.service.ThingService;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物品资源控制类
 * @Author Peng
 * @Date 2022/4/13
 */
@RestController
@RequestMapping("/api/thing")
public class ThingController {

    private ThingService thingService;

    @Autowired
    public void setThingService(ThingService thingService) {
        this.thingService = thingService;
    }

    // 查询物品类型
    @GetMapping("/type")
    public Result<List<Thing>> listType() {
        // 查询所有物品类型
        List<Thing> things = thingService.listType();

        return Result.success(things);
    }

    // 查询物品类型下的店名
    @GetMapping("/store/{id}")
    public Object listStore(@PathVariable("id") Long id) {
        // 校验参数
        AssertUtil.isParamsNull(id);

        // 根据物品id查询店名
        List<Store> stores = thingService.listStore(id);

        return Result.success(stores);
    }

}
