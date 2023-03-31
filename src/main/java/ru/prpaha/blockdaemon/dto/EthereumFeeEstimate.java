package ru.prpaha.blockdaemon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EthereumFeeEstimate {

    @JsonProperty("most_recent_block")
    private Integer mostRecentBlock;

    @JsonProperty("estimated_fees")
    private EthereumEstimatedFees estimatedFees;

}
