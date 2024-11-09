package org.cowain.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.ShopDTO;
import org.cowain.entity.Shop;
import org.cowain.result.Result;
import org.cowain.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = "店铺相关接口")
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ShopService shopService;

//    @ApiOperation("设置店铺状态")
//    @PutMapping("/{status}")
//    public Result<String> setShopStatus(@PathVariable Integer status) {
//        log.info("设置店铺当前状态：{}", status == 1 ? "营业中" : "停业中");
//        redisTemplate.opsForValue().set(KEY, status);
//        return Result.success(status == 1 ? "营业中" : "停业中");
//    }
//
//    @ApiOperation("获取店铺营业状态")
//    @GetMapping("/status")
//    public Result<Integer> getShopStatus() {
//        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
//        log.info("获取店铺当前状态：{}", status == 1 ? "营业中" : "停业中");
//        return Result.success(status);
//    }

    @ApiOperation("获取店铺信息")
    @GetMapping ()
    public Result<Shop> shopInfo(){
        Shop shop =shopService.shopInfo();
        return Result.success(shop);
    }

    @ApiOperation("保存店铺信息")
    @PostMapping
    public Result<String> save(@RequestBody Shop shop){
        shopService.save(shop);
        return Result.success("保存成功");
    }

    @ApiOperation("修改店铺信息")
    @PutMapping
    public Result<String> update(@RequestBody ShopDTO shopDTO){
        log.info("修改店铺信息:{}",shopDTO);
        shopService.updateById(shopDTO);
        return Result.success("保存成功");
    }

    @ApiOperation("更新店铺状态")
    @PostMapping ("/update/status/{status}")
    public Result<String> update(@PathVariable Integer status,Long id){
        shopService.updateStatusById(status,id);
        return Result.success("保存成功");
    }

}
