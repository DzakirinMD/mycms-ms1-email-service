package com.mycmsms1emailservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class going to be used for transfer data between Producer and Consumer using apache kafka
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InhouseTransferEventDTO implements Serializable {

    private String message;
    private String status;
    private InhouseTransferDTO inhouseTransferDTO;
}
