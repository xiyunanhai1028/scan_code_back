package org.cowain.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.context.BaseContext;
import org.cowain.entity.AddressBook;
import org.cowain.result.Result;
import org.cowain.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "C端-地址簿相关接口")
@RestController("userAddressBookController")
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @ApiOperation("新增地址")
    @PostMapping
    public Result<String> save(@RequestBody AddressBook addressBook) {
        addressBookService.save(addressBook);
        return Result.success("保存成功");
    }

    @ApiOperation("查询当前登录用户的所有地址信息")
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    @ApiOperation("根据ID查询地址")
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@RequestParam Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @ApiOperation("根据ID修改地址")
    @PutMapping
    public Result<String> update(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);
        return Result.success("修改删除");
    }

    @ApiOperation("设置默认地址")
    @PutMapping("/default")
    public Result<String> setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);
        return Result.success("设置成功");
    }

    @ApiOperation("根据ID删除地址")
    @DeleteMapping
    public Result<String> deleteById(@RequestParam Long id) {
        addressBookService.deleteById(id);
        return Result.success("删除成功");
    }

    @ApiOperation("查看默认地址")
    @GetMapping("/default")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());

        List<AddressBook> list = addressBookService.list(addressBook);
        if (list != null && list.size() > 0) {
            return Result.success(list.get(0));
        }

        return Result.error("没有查询到默认地址");
    }
}
