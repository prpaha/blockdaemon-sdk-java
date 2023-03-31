package ru.prpaha.blockdaemon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EthereumEstimatedFees {

    @JsonProperty("fast")
    private EthereumFeeEstimateValue fast;

    @JsonProperty("medium")
    private EthereumFeeEstimateValue medium;

    @JsonProperty("slow")
    private EthereumFeeEstimateValue slow;

}
