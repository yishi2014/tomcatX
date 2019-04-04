package com.yishi.code.general.dummy;

import com.alibaba.fastjson.JSON;
import com.yishi.code.general.dao.BaseDao;
import com.yishi.code.general.dto.$Dictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class $BaseDictionaryServiceImpl {

    @Resource
    private BaseDao baseDao;

    public List<$Dictionary> getDictionary(String groupID) {
        List param=new ArrayList();
        param.add(groupID);
        List list= baseDao.selectByHql("select new com.code.general.dto.$Dictionary(fieldcode,fieldname) from BaseBasicdataselect where  parentcode=?  ",param);
        System.out.println(JSON.toJSONString(list));
        return list;

    }
}
