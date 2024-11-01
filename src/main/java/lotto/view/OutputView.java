package lotto.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.Lotto;
import lotto.constant.Prize;

public class OutputView {
    public void printPurchasePriceRequest() {
        System.out.println("구입금액을 입력해 주세요.");
    }

    public void printPurchaseQuantity(int quantity) {
        System.out.println();
        System.out.printf("%d개를 구매했습니다.%n", quantity);
    }

    public void printLottoNumbers(List<Lotto> lottoTickets) {
        String result = lottoTickets.stream()
                .map(Lotto::getNumbers)
                .map(List::toString)
                .collect(Collectors.joining("\n"));
        System.out.println(result);
    }

    public void printWinningNumbersRequest() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
    }

    public void printBonusNumberRequest() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
    }

    public void printWinningResult(Map<Prize, Integer> result, double rateOfReturn) {
        System.out.println("\n당첨 통계\n---");
        String resultMessage = result.entrySet().stream()
                .map(entry -> String.format(entry.getKey().getResultMessage(), entry.getValue()))
                .collect(Collectors.joining("\n"));
        System.out.println(resultMessage);
        System.out.printf("총 수익률은 %.2f%%입니다.%n", rateOfReturn);
    }
}
