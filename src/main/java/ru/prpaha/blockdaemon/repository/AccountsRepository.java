package ru.prpaha.blockdaemon.repository;

import com.google.gson.Gson;
import ru.prpaha.blockdaemon.api.AccountsApi;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.TxOutputs;
import ru.prpaha.blockdaemon.model.TxPage;

public class AccountsRepository extends AbstractApiRepository {

    private final AccountsApi accountsApi;

    public AccountsRepository(AccountsApi accountsApi, Gson gson) {
        super(gson);
        this.accountsApi = accountsApi;
    }

    public TxOutputs getTransactionsUtxo(String platform, String network, String address, String continuation, int limit)
            throws ApiException {
        return accountsApi.getUTXOByAccount(platform, network, address,
                null, null, null, "asc", continuation, limit);
    }

    public TxPage getTransactionsTxs(String platform, String network, String address, String continuation, int limit)
            throws ApiException {
        return accountsApi.getTxsByAddress(platform, network, address,
                null, null, null, "asc", continuation, limit);
    }

}
