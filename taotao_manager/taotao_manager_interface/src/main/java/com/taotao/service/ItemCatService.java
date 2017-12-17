package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    public List<EasyUITreeNode> getItemCatList(Long parentId);
}
