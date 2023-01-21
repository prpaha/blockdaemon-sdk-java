package ru.prpaha.blockdaemon;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.prpaha.blockdaemon.dto.BlockInfo;
import ru.prpaha.blockdaemon.dto.EthereumFeeEstimate;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.FeeEstimate;
import ru.prpaha.blockdaemon.model.FeeEstimateEstimatedFees;
import ru.prpaha.blockdaemon.model.TxOutputs;
import ru.prpaha.blockdaemon.model.TxPage;
import ru.prpaha.blockdaemon.model.TxReceipt;
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

    public TxOutputs getTransactions(@NotNull BlockdaemonPlatform platform,
                                     @NotNull String address,
                                     String continuation) throws ApiException {
        return accountsRepository.getTransactionsUtxo(platform.getValue(), network, address, continuation);
    }

    public TxPage getTransactionsTxs(@NotNull BlockdaemonPlatform platform,
                                     @NotNull String address,
                                     String continuation) throws ApiException {
        return accountsRepository.getTransactionsTxs(platform.getValue(), network, address, continuation);
    }

    public FeeEstimate getFeeEstimates(@NotNull BlockdaemonPlatform platform) throws ApiException {
        return switch (platform) {
            case ETHEREUM -> map(transactionsRepository.getEthereumFeeEstimates(platform.getValue(), network));
            default -> transactionsRepository.getFeeEstimates(platform.getValue(), network);
        };
    }

    private FeeEstimate map(EthereumFeeEstimate ethereumFeeEstimates) {
        if (ethereumFeeEstimates == null) {
            return null;
        }
        return new FeeEstimate()
                .mostRecentBlock(ethereumFeeEstimates.getMostRecentBlock())
                .estimatedFees(new FeeEstimateEstimatedFees()
                        .fast(ethereumFeeEstimates.getEstimatedFees().getFast().getMaxPriorityFee())
                        .medium(ethereumFeeEstimates.getEstimatedFees().getMedium().getMaxPriorityFee())
                        .slow(ethereumFeeEstimates.getEstimatedFees().getSlow().getMaxPriorityFee()));
    }

    public BlockInfo getCurrentBlockInfo(@NotNull BlockdaemonPlatform platform) throws ApiException {
        return BlockInfo.builder()
                .id(platformsRepository.getCurrentBlockId(platform.getValue(), network))
                .number(platformsRepository.getCurrentBlockNumber(platform.getValue(), network))
                .build();
    }

    public TxReceipt sendSignedTransaction(@NotNull BlockdaemonPlatform platform,
                                           @NotNull String signedTransactionHex) throws ApiException {
        return transactionsRepository.sendSignedTransaction(platform.getValue(), network, signedTransactionHex);
    }

}
