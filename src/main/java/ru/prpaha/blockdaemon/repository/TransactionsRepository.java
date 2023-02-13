package ru.prpaha.blockdaemon.repository;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import ru.prpaha.blockdaemon.api.TransactionsApi;
import ru.prpaha.blockdaemon.dto.EthereumFeeEstimate;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.FeeEstimate;
import ru.prpaha.blockdaemon.model.SignedTx;
import ru.prpaha.blockdaemon.model.TxReceipt;

public class TransactionsRepository {

    private final TransactionsApi transactionsApi;

    public TransactionsRepository(TransactionsApi transactionsApi) {
        this.transactionsApi = transactionsApi;
    }

    public FeeEstimate getFeeEstimates(@NotNull String platform,
                                       @NotNull String network) throws ApiException {
        return transactionsApi.feeEstimate(platform, network);
    }

    public EthereumFeeEstimate getEthereumFeeEstimates(@NotNull String platform,
                                                       @NotNull String network) throws ApiException {
        var call = transactionsApi.feeEstimateCall(platform, network, null);
        var returnType = new TypeToken<EthereumFeeEstimate>(){}.getType();
        return (EthereumFeeEstimate) transactionsApi.getApiClient().execute(call, returnType).getData();
    }

    public TxReceipt sendSignedTransaction(@NotNull String platform,
                                           @NotNull String network,
                                           @NotNull String transaction) throws ApiException {
        return transactionsApi.txSend(platform, network, new SignedTx().tx(transaction));
    }

}
