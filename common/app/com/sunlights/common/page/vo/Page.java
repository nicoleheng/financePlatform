package com.sunlights.common.page.vo;

import com.sunlights.common.page.Pager;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: fsp</p>
 * <p>Title: Page.java</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2014 Sunlights.cc</p>
 * <p>All Rights Reserved.</p>
 *
 * @author <a href="mailto:zhencai.yuan@sunlights.cc">yuanzhencai</a>
 */
public class Page {
    public int index = 0;
    public int pageSize = 0;
    public int count = 0;
    public List list = new ArrayList();

    public Page(Pager pager) {
        inPager(pager);
    }

    public void inPager(Pager pager) {
        this.index = pager.getIndex();
        this.pageSize = pager.getPageSize();
        this.count = pager.getCount();
        this.list = pager.getList();
    }
}
