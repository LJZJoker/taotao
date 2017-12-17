package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
   @Autowired
   private TbItemMapper mapper;

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //设置分页条件
        PageHelper.startPage(page,rows);
        //创建example
        TbItemExample example =new TbItemExample();
        //执行查询
        List<TbItem> tbItemList = mapper.selectByExample(example);
        //封装分页信息
        PageInfo<TbItem> info = new PageInfo<>(tbItemList);
        //设置分页信息
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(info.getTotal());
        result.setRows(info.getList());
        return result;
    }

    @Autowired
    private TbItemDescMapper descMapper;
    @Override
    public TaotaoResult saveItem(TbItem item, String desc) {
        //1.生成商品id
        Long itemId = IDUtils.genItemId();
        //2.补全TbItem对象属性
        item.setId(itemId);
        item.setStatus((byte)1);//商品状态 1-正常 ,2-下架,3-删除
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //3.向商品表插入数据
        mapper.insert(item);
        //4.创建一个TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        //5.补全TbItemDesc对象属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //6.向商品描述表插入数据
        descMapper.insert(itemDesc);
        //7.TaotaoResult.ok()
        return TaotaoResult.ok();
    }
}
