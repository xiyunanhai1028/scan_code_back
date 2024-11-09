package org.cowain.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.ShoppingCartDTO;
import org.cowain.entity.ShoppingCart;
import org.cowain.result.Result;
import org.cowain.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "C端-购物车相关接口")
@RestController("userShoppingCartController")
@RequestMapping("/user/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("添加购物车")
    @PostMapping("add")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车:{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success("添加成功");
    }

    @ApiOperation("查询购物车")
    @GetMapping("/list/{sitNum}")
    public Result<List<ShoppingCart>> list(@PathVariable("sitNum") Integer sitNum){
        List<ShoppingCart> list = shoppingCartService.showShoppingCart(sitNum);
        return Result.success(list);
    }

    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result<String> clean(Integer sitNum){
        shoppingCartService.cleanShoppingCart(sitNum);
        return Result.success("清空成功");
    }

    @ApiOperation("删除购物车中的一个商品")
    @PostMapping("/sub")
    public Result<String> sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("删除购物车中的一个商品:{}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success("删除成功");
    }
}
