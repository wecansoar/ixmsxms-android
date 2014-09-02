package net.flower.ixmsxms.model;


import javax.management.loading.MLetContent;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sis on 14. 8. 15..
 */
public class Child implements Serializable{
    private  static final long serivalVersionUID =1233444;

    public long childId;
    public long userId;
    public String name;
    public String sex;
    public String imageUrl;
    public String status;
    public Float stature;
    public Float weight;
    public String regDatetime;
    public String modDatetime;
    public String birthDate;
    public long age;
    public long days;
    public long months;


}
