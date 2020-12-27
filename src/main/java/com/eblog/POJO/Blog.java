package com.eblog.POJO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Blog {
    private Long id;                //主键
    private String title;           //标题
    private String content;         //内容
    private String firstPicture;    //首图地址
    private String flag;            //标记是否原创
    private Integer views;          //浏览次数
    private Integer commentCount;   //评论次数
    private boolean appreciation;   //是否开启赞赏
    private boolean shareStatement; //是否开启版权
    private boolean commentabled;   //是否开启评论
    private boolean published;      //是否发布
    private boolean recommend;      //是否推荐
    private Date createTime;        //创建时间
    private Date updateTime;        //更新时间
    private String description;     //博客描述

    private Type type;              //分类
    private User user;              //用户
    private Long typeId;
    private Long userId;
    private List<Comment> comments = new ArrayList<>(); //


}
