package kale.adapter.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Kale
 * @date 2016/11/14
 */

public class ItemTypeUtilTest {

    @Test
    public void testInputType() {
        ItemTypeUtil util = new ItemTypeUtil();

        Assert.assertEquals(0, util.getIntType("01"));
        Assert.assertEquals(0, util.getIntType("01"));
        Assert.assertEquals(1, util.getIntType("02"));
        Assert.assertEquals(2, util.getIntType("03"));
        Assert.assertEquals(0, util.getIntType("01"));
        Assert.assertEquals(1, util.getIntType("02"));
    }
}
