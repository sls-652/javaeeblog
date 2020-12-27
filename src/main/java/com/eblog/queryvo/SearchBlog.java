package com.eblog.queryvo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SearchBlog {
    private String title;
    private Long typeId;
}
