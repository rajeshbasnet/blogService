package com.treeleaf.blog.dto;

import lombok.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Blog {
    private String id;
    private String title;
    private String content;
    private Rating rating;
}
