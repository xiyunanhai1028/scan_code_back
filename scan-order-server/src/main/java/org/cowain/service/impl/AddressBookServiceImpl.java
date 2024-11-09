package org.cowain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cowain.context.BaseContext;
import org.cowain.entity.AddressBook;
import org.cowain.mapper.AddressBookMapper;
import org.cowain.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;
    //保存地址
    public void save(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }

    //查询用户所有添加的地址
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    //根据ID查询地址
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    //设置默认地址
    public void setDefault(AddressBook addressBook) {
        //现将默认地址修改为非默认
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefaultByUserId(addressBook);

        //将当前的设置为默认地址
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }

    //根据ID删除
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }
}
