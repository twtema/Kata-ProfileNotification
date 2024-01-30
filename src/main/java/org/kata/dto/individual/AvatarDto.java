package org.kata.dto.individual;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvatarDto {

    private String icp;

    private String filename;

    private byte[] imageData;
}