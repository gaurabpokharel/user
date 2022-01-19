package com.demoproject.user.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private Long blogId;
    private String blogName;
    private String blogDescription;
}
