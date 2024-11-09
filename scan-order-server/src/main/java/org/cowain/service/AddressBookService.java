package org.cowain.service;

import org.cowain.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    //保存地址
    void save(AddressBook addressBook);

    //查询用户所有添加的地址
    List<AddressBook> list(AddressBook addressBook);

    //根据ID查询地址
    AddressBook getById(Long id);


    void update(AddressBook addressBook);

    //设置默认地址
    void setDefault(AddressBook addressBook);

    void deleteById(Long id);
}
