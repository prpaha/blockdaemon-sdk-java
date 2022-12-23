package ru.prpaha.blockdaemon.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class EthereumFeeEstimate {

    @SerializedName("most_recent_block")
    private Integer mostRecentBlock;

    @SerializedName("estimated_fees")
    private EthereumEstimatedFees estimatedFees;

}
