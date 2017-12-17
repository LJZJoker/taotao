package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    /**
     *分页查询商品项
     * @param page 当前页
     * @param rows 每页显示行数
     * @return
     */
    public EasyUIDataGridResult getItemList(Integer page,Integer rows);

    /**
     * 保存商品项
     * @param item
     * @param desc
     * @return
     */
    public TaotaoResult saveItem(TbItem item,String desc);


}
