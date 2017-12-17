package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        //1.注入mapper
        //2.创建example
        TbItemCatExample example = new TbItemCatExample();
        //3.设置查询条件
        example.createCriteria().andParentIdEqualTo(parentId);
        //4.执行查询,得到类目结果集
        List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
        //5.将类型结果集转化成节点集合,返回出去给EasyUI使用
        List<EasyUITreeNode> nodes = new ArrayList<>();
        //遍历类目结果集,将查询出的结果项封装成节点对象
        for (TbItemCat itemCat : itemCats) {
            //创建节点对象
            EasyUITreeNode node = new EasyUITreeNode();
            //封装节点对象
            node.setText(itemCat.getName());
            node.setId(itemCat.getId());
            //设置节点状态,利用三元运算符判断itemCat是否为父节点,若为父节点则状态设置为closed,否则open
            node.setState(itemCat.getIsParent()?"closed":"open");
            //将封装好的节点对象添加到节点集合中
            nodes.add(node);
        }
        //6.返回集合
        return nodes;
    }
}
