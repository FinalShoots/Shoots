package com.Shoots.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inquiry {
    private int inquiry_id;
    private String inquiry_type;
    private int inquiry_ref_idx;
    private String title;
    private String content;
    private String inquiry_file;
    private String register_date;
    private int cnt;
    private String user_id;
    private String business_id;
    private int idx;
    private int commentcount;
    private boolean hasReply;
    private String resolved_id;
}
