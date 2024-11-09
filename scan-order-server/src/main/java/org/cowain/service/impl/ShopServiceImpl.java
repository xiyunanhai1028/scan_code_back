package org.cowain.service.impl;

import org.cowain.dto.ShopDTO;
import org.cowain.entity.Shop;
import org.cowain.mapper.ShopMapper;
import org.cowain.result.Result;
import org.cowain.service.ShopService;
import org.cowain.vo.ShopVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public void save(Shop shop) {
        shop.setStatus(0);
        shopMapper.insert(shop);
    }

    @Override
    public void updateById(ShopDTO shopDTO) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDTO, shop);
        shopMapper.update(shop);
    }

    @Override
    public Shop shopInfo() {
        return shopMapper.queryInfo();
    }


    public void updateStatusById(Integer status, Long id) {
        Shop shop = shopMapper.queryById(id);
        shop.setStatus(status);
        shopMapper.update(shop);
    }


    //根据店铺ID获取店铺信息
    public ShopVO getById(Long id) {
        Shop shop = shopMapper.queryById(id);
        ShopVO shopVO = new ShopVO();
        BeanUtils.copyProperties(shop, shopVO);
        return shopVO;
    }
}
