package com.store.serveice;

import com.store.entity.District;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface IDistrictService {

    List<District> getByParent(String parent);
    /**
     * 根据省/市/区的行政代号获取省/市/区的名称
     * @param code 省/市/区的行政代号
     * @return 匹配的省/市/区的名称，如果没有匹配的数据则返回null
     */
    String getNameByCode(String code);
}
