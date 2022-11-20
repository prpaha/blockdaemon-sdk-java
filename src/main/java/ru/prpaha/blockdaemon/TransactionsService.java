package ru.prpaha.blockdaemon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.FeeEstimate;
import ru.prpaha.blockdaemon.model.TxOutputs;
import ru.prpaha.blockdaemon.repository.AccountsRepository;
import ru.prpaha.blockdaemon.repository.TransactionsRepository;

@RequiredArgsConstructor
@Service
public class TransactionsService {

    private final AccountsRepository accountsRepository;
    private final TransactionsRepository transactionsRepository;

    @Value("${blockdaemon.network}")
    private String network;

    public TxOutputs getBitcoinTransactions(String address, String continuation) throws ApiException {
        return accountsRepository.getTransactions(BlockdaemonConstants.BITCOIN_PLATFORM, network, address, continuation);
    }

    public FeeEstimate getFeeEstimates() throws ApiException {
        return transactionsRepository.getFeeEstimates(BlockdaemonConstants.BITCOIN_PLATFORM, network);
    }

}
