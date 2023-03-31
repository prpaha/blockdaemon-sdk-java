package ru.prpaha.blockdaemon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EthereumFeeEstimateValue {

    @JsonProperty("max_priority_fee")
    private Long maxPriorityFee;

    @JsonProperty("max_total_fee")
    private Long maxTotalFee;

}
