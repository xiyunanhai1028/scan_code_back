package org.cowain.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.ShopDTO;
import org.cowain.result.Result;
import org.cowain.service.ShopService;
import org.cowain.vo.ShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = "店铺相关接口")
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ShopService shopService;


    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺当前状态：{}", status == 1 ? "营业中" : "停业中");
        return Result.success(status);
    }

    @ApiOperation("获取商铺信息")
    @GetMapping("/{id}")
    public Result<ShopVO> getShopInfo(@PathVariable("id") Long id){
        log.info("获取商铺信息:{}",id);
        ShopVO shopVO = shopService.getById(id);
        return Result.success(shopVO);
    }
}
