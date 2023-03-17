package com.store.serveice.impl;

import com.store.entity.District;
import com.store.mapper.DistrictMapper;
import com.store.serveice.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }
    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }

}