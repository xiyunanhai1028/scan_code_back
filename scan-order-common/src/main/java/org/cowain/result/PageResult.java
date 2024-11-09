package org.cowain.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult implements Serializable {
    private static final long serialVersionUID = 7349891385857204499L;
    private long total;//总记录数
    private List records;//当前页数据集合
}
