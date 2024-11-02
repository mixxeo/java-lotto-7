package lotto.controller;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import lotto.Lotto;
import lotto.constant.Prize;
import lotto.model.Purchase;
import lotto.model.WinningNumbers;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;


    public LottoController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Purchase purchase = requestPurchase();
        List<Lotto> lottoTickets = generateLottoTickets(purchase.getQuantity());
        WinningNumbers winningNumbers = requestWinningNumbers();
        LinkedHashMap<Prize, Integer> result = calculateResult(lottoTickets, winningNumbers);
        double rateOfReturn = calculateRateOfReturn(result, purchase.getPrice());
        outputView.displayWinningResult(result, rateOfReturn);
    }

    private Purchase requestPurchase() {
        outputView.displayPurchasePriceRequest();
        int purchasePrice = inputView.getInteger();
        Purchase purchase = new Purchase(purchasePrice);

        int purchaseQuantity = purchase.getQuantity();
        outputView.displayPurchaseQuantity(purchaseQuantity);

        return purchase;
    }

    private List<Lotto> generateLottoTickets(int quantity) {
        List<Lotto> lottoTickets = new ArrayList<>();
        for (int count = 0; count < quantity; count++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            lottoTickets.add(new Lotto(numbers));
        }
        outputView.displayLottoNumbers(lottoTickets);

        return lottoTickets;
    }

    private WinningNumbers requestWinningNumbers() {
        outputView.displayWinningNumbersRequest();
        List<Integer> winningNumbers = parseNumbers(inputView.getString());
        Lotto numbers = new Lotto(winningNumbers);

        outputView.displayBonusNumberRequest();
        int bonusNumber = inputView.getInteger();

        return new WinningNumbers(numbers, bonusNumber);
    }

    private List<Integer> parseNumbers(String input) {
        List<String> numbers = List.of(input.split(","));
        return numbers.stream()
                .map(Integer::parseInt)
                .toList();
    }

    private LinkedHashMap<Prize, Integer> calculateResult(List<Lotto> lottoTickets, WinningNumbers winningNumbers) {
        LinkedHashMap<Prize, Integer> result = new LinkedHashMap<>() {{
            put(Prize.FIFTH, 0);
            put(Prize.FOURTH, 0);
            put(Prize.THIRD, 0);
            put(Prize.SECOND, 0);
            put(Prize.FIRST, 0);
        }};
        for (Lotto lotto : lottoTickets) {
            Prize prize = winningNumbers.checkPrize(lotto);
            if (prize != Prize.NON) {
                result.replace(prize, result.get(prize) + 1);
            }
        }

        return result;
    }

    private double calculateRateOfReturn(LinkedHashMap<Prize, Integer> result, int purchasePrice) {
        int totalPrizeMoney = result.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getPrizeMoney())
                .sum();

        return ((double) totalPrizeMoney / purchasePrice) * 100;
    }
}
