package com.xuchangan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyRelation {
    private int relationId;
    private int mainUserId;
    private int memberUserId;
    private String relationType;
}
