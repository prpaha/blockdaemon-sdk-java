package ru.prpaha.blockdaemon.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.prpaha.blockdaemon.api.TransactionsApi;
import ru.prpaha.blockdaemon.dto.EthereumFeeEstimate;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.FeeEstimate;

public class TransactionsRepository extends AbstractApiRepository {

    private final TransactionsApi transactionsApi;

    public TransactionsRepository(TransactionsApi transactionsApi, Gson gson) {
        super(gson);
        this.transactionsApi = transactionsApi;
    }

    public FeeEstimate getFeeEstimates(String platform, String network) throws ApiException {
        return transactionsApi.feeEstimate(platform, network);
    }

    public EthereumFeeEstimate getEthereumFeeEstimates(String platform, String network) throws ApiException {
        var call = transactionsApi.feeEstimateCall(platform, network, null);
        var returnType = new TypeToken<EthereumFeeEstimate>(){}.getType();
        return (EthereumFeeEstimate) transactionsApi.getApiClient().execute(call, returnType).getData();
    }

}
