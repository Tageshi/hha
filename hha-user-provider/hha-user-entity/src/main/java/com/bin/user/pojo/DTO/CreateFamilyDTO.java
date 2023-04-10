package com.bin.user.pojo.DTO;

import com.bin.user.pojo.PO.FamilyMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 1:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFamilyDTO implements Serializable {
    private List<FamilyMember> familyMembers;
    private String familyName;
}
