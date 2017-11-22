package com.mark.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * Author: Mark
 * Date  : 2017/11/22
 */
@Data
@Document(indexName = "bbs_user_alias", type = "bbs_user")
public class UserDO {

    @Id
    private Long        uid;        // 用户id
    private String      name;       // 用户名
    private Integer     verify;     // 是否认证
    private Long        postCount;  // 发帖数
    private Long        fansCount;  // 粉丝数

}
