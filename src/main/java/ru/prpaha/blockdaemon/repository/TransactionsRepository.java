package ru.prpaha.blockdaemon.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.prpaha.blockdaemon.api.TransactionsApi;
import ru.prpaha.blockdaemon.dto.EthereumFeeEstimate;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.invoker.Pair;
import ru.prpaha.blockdaemon.model.FeeEstimate;
import ru.prpaha.blockdaemon.model.SignedTx;
import ru.prpaha.blockdaemon.model.TxReceipt;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

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
        Object localVarPostBody = null;

        var apiClient = transactionsApi.getApiClient();

        // create path and map variables
        var localVarPath = "/{protocol}/{network}/tx/estimate_fee"
                .replaceAll("\\{" + "protocol" + "\\}", apiClient.escapeString(platform))
                .replaceAll("\\{" + "network" + "\\}", apiClient.escapeString(network));

        var localVarQueryStringJoiner = new StringJoiner("&");
        var localVarQueryParams = new ArrayList<Pair>();
        var localVarCollectionQueryParams = new ArrayList<Pair>();
        var localVarHeaderParams = new HashMap<String, String>();
        var localVarCookieParams = new HashMap<String, String>();
        var localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {"application/json"};
        final var localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

        final String[] localVarContentTypes = {};
        final var localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        var localVarAuthNames = new String[] { "apiKeyAuthHeader", "bearerAuth" };

        var localVarReturnType = new TypeReference<EthereumFeeEstimate>() {};
        return apiClient.invokeAPI(
                localVarPath,
                "GET",
                localVarQueryParams,
                localVarCollectionQueryParams,
                localVarQueryStringJoiner.toString(),
                localVarPostBody,
                localVarHeaderParams,
                localVarCookieParams,
                localVarFormParams,
                localVarAccept,
                localVarContentType,
                localVarAuthNames,
                localVarReturnType
        );
    }

    public TxReceipt sendSignedTransaction(@NotNull String platform,
                                           @NotNull String network,
                                           @NotNull String transaction) throws ApiException {
        return transactionsApi.txSend(platform, network, new SignedTx().tx(transaction));
    }

}
