package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 本类作为返回json的pojo类而存在
 */
public class EasyUIDataGridResult implements Serializable {
    private Long total;//总记录数
    private List rows;//商品集合

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
