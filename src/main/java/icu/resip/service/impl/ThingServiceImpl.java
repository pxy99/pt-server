package icu.resip.service.impl;

import icu.resip.domain.order.Store;
import icu.resip.domain.order.Thing;
import icu.resip.mapper.ThingMapper;
import icu.resip.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/13
 */
@Service
public class ThingServiceImpl implements ThingService {

    private ThingMapper thingMapper;

    @Autowired
    public void setThingMapper(ThingMapper thingMapper) {
        this.thingMapper = thingMapper;
    }

    @Override
    public List<Thing> listType() {
        return thingMapper.listAll();
    }

    @Override
    public List<Store> listStore(Long tid) {
        return thingMapper.listStore(tid);
    }

}
