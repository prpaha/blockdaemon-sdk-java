package ru.prpaha.blockdaemon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.prpaha.blockdaemon.dto.BlockInfo;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.FeeEstimate;
import ru.prpaha.blockdaemon.model.TxOutputs;
import ru.prpaha.blockdaemon.repository.AccountsRepository;
import ru.prpaha.blockdaemon.repository.PlatformsRepository;
import ru.prpaha.blockdaemon.repository.TransactionsRepository;

@RequiredArgsConstructor
@Service
public class TransactionsService {

    private final AccountsRepository accountsRepository;
    private final TransactionsRepository transactionsRepository;
    private final PlatformsRepository platformsRepository;

    @Value("${blockdaemon.network}")
    private String network;

    public TxOutputs getBitcoinTransactions(String address, String continuation) throws ApiException {
        return accountsRepository.getTransactions(BlockdaemonConstants.BITCOIN_PLATFORM, network, address, continuation);
    }

    public FeeEstimate getFeeEstimates() throws ApiException {
        return transactionsRepository.getFeeEstimates(BlockdaemonConstants.BITCOIN_PLATFORM, network);
    }

    public BlockInfo getCurrentBlockInfo() throws ApiException {
        return BlockInfo.builder()
                .id(platformsRepository.getCurrentBlockId(BlockdaemonConstants.BITCOIN_PLATFORM, network))
                .number(platformsRepository.getCurrentBlockNumber(BlockdaemonConstants.BITCOIN_PLATFORM, network))
                .build();
    }

}
