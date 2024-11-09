package org.cowain.service;

import org.cowain.dto.ShopDTO;
import org.cowain.entity.Shop;
import org.cowain.result.Result;
import org.cowain.vo.ShopVO;

public interface ShopService {
    //保存店铺信息
    void save(Shop shop);

    void updateById(ShopDTO shop);

    Shop shopInfo();

    void updateStatusById(Integer status,Long id);

    //根据ID获取商品信息
    ShopVO getById(Long id);
}
