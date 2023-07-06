package com.treeleaf.rating.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Rating {
    private String id;
    private int rating;
    private String comment;
}
