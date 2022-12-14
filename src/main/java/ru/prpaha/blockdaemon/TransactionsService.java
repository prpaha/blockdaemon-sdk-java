package ru.prpaha.blockdaemon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.prpaha.blockdaemon.dto.BlockInfo;
import ru.prpaha.blockdaemon.dto.EthereumFeeEstimate;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.FeeEstimate;
import ru.prpaha.blockdaemon.model.FeeEstimateEstimatedFees;
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

    public TxOutputs getTransactions(BlockdaemonPlatform platform, String address, String continuation) throws ApiException {
        return accountsRepository.getTransactions(platform.getValue(), network, address, continuation);
    }

    public FeeEstimate getFeeEstimates(BlockdaemonPlatform platform) throws ApiException {
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

    public BlockInfo getCurrentBlockInfo(BlockdaemonPlatform platform) throws ApiException {
        return BlockInfo.builder()
                .id(platformsRepository.getCurrentBlockId(platform.getValue(), network))
                .number(platformsRepository.getCurrentBlockNumber(platform.getValue(), network))
                .build();
    }

}
