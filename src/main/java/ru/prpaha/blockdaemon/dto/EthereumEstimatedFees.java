package ru.prpaha.blockdaemon.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class EthereumEstimatedFees {

    @SerializedName("fast")
    private EthereumFeeEstimateValue fast;

    @SerializedName("medium")
    private EthereumFeeEstimateValue medium;

    @SerializedName("slow")
    private EthereumFeeEstimateValue slow;

}
