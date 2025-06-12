package devcourse.assignment.lottomap.winninginfo;

import devcourse.assignment.lottomap.store.Store;
import devcourse.assignment.lottomap.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WinningInfoService {

    private final WinningInfoRepository winningInfoRepository;

    private final StoreService storeService;

    public int getMaxDrawNo(){
        return winningInfoRepository.getMaxDrawNo();
    }

    public WinningInfo saveWinningInfo(int drawNo,String category, int rank,long storeId){
        Store store = storeService.getStoreById(storeId);
        WinningInfo winningInfo = new WinningInfo(drawNo,category,rank, store);
        return winningInfoRepository.save(winningInfo);
    }
}
