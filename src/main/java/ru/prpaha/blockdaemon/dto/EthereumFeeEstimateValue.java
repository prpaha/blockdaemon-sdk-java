package ru.prpaha.blockdaemon.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class EthereumFeeEstimateValue {

    @SerializedName("max_priority_fee")
    private Long maxPriorityFee;

    @SerializedName("max_total_fee")
    private Long maxTotalFee;

}
