package kale.commonadapter.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.util.BaseModel;
import kale.commonadapter.model.ButtonModel;
import kale.commonadapter.model.ImageModel;
import kale.commonadapter.model.TextModel;

/**
 * @author Jack Tony
 * @date 2015/9/19
 */
public class DataManager {

    public static String[] IMAGES = new String[]{
            "http://g.hiphotos.baidu.com/zhidao/pic/item/09fa513d269759eee314015bb3fb43166c22dfde.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1esojfinxmxj20xc18gqfm.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1esiveg31hwj20u00gvn18.jpg"
            ,"http://ww1.sinaimg.cn/mw1024/6df127bfjw1esivelw317j20u00gvq77.jpg"
            ,"http://ww4.sinaimg.cn/mw1024/6df127bfjw1esbuy81ovzj20ku04taah.jpg"
            ,"http://ww4.sinaimg.cn/mw1024/6df127bfjw1esaen9u5k8j20hs0nq75k.jpg"
            ,"http://ww3.sinaimg.cn/mw1024/6df127bfjw1es1ixs8uctj20hs0vkgpc.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1erveujphhxj20xc18gwsy.jpg"
            ,"http://ww1.sinaimg.cn/mw1024/6df127bfjw1eroxgfbkopj216o0m543e.jpg"
            ,"http://ww4.sinaimg.cn/mw1024/6df127bfjw1erox2ywpn6j218g0xcwjp.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1erovfvilebj20hs0vkacd.jpg"
            ,"http://ww3.sinaimg.cn/mw1024/6df127bfjw1erj3jcayb1j20qo0zkgrv.jpg"
            ,"http://ww3.sinaimg.cn/mw1024/6df127bfgw1erc5yiqciaj20ke0b0abg.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1erbuxu8qa7j20ds0ct3zy.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1er0erdh1jaj20qo0zkq9j.jpg"
            ,"http://ww3.sinaimg.cn/mw1024/6df127bfjw1er0enq1o3lj218g18gnbx.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1er0gnhidtdj20hs0nogoi.jpg"
            ,"http://ww3.sinaimg.cn/mw1024/6df127bfjw1er0gqq4ff9j20hs0non07.jpg"
            ,"http://ww3.sinaimg.cn/mw1024/6df127bfjw1eqmfsasl7fj218g0p07a2.jpg"
            ,"http://ww2.sinaimg.cn/mw1024/6df127bfjw1eqmfuizagpj218g0r9dms.jpg"
    };

    /**
     * 模拟加载数据的操作
     */
    public static List<BaseModel> loadData(Context context) {
        List<BaseModel> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int type = (int) (Math.random() * 3);
            //Log.d(TAG, "itemType = " + itemType);
            switch (type) {
                case 0:
                    TextModel textModel = new TextModel();
                    textModel.text = "TextModel " + i;
                    list.add(textModel);
                    break;
                case 1:
                    ButtonModel buttonModel = new ButtonModel();
                    buttonModel.content = "ButtonModel " + i;
                    list.add(buttonModel);
                    break;
                case 2:
                    ImageModel imageModel = new ImageModel();
                    imageModel.imgUrl = IMAGES[i % IMAGES.length];
                    list.add(imageModel);
                    break;
                default:
            }
        }
        return list;
    }
}
