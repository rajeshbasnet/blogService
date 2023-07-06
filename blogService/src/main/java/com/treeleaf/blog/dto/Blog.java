package com.treeleaf.blog.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Blog {
    private String id;
    private String title;
    private String content;
}
