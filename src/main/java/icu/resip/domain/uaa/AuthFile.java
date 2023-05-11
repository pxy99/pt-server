package icu.resip.domain.uaa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: Peng
 * @Date: 2022/3/31
 */
@Getter
@Setter
public class AuthFile {

    private Long id;

    private Long uid;

    private String sfzZ;

    private String sfzF;

    private String xszF;

    private String xszX;

    private String rlz;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
