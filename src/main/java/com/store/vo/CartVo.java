package com.store.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 表示购物车的值对象 value object
 *
 * @author 32153
 */
public class CartVo implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private long price;
    private Integer num;
    private String title;
    private long realPrice;
    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartVo cartVo = (CartVo) o;
        return Objects.equals(cid, cartVo.cid) && Objects.equals(uid, cartVo.uid) && Objects.equals(pid, cartVo.pid) && Objects.equals(price, cartVo.price) && Objects.equals(num, cartVo.num) && Objects.equals(title, cartVo.title) && Objects.equals(realPrice, cartVo.realPrice) && Objects.equals(image, cartVo.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, uid, pid, price, num, title, realPrice, image);
    }

    @Override
    public String toString() {
        return "CartVo{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", price=" + price +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", realPrice=" + realPrice +
                ", image='" + image + '\'' +
                '}';
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(long realPrice) {
        this.realPrice = realPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}