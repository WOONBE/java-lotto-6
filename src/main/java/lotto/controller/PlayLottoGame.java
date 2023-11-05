package lotto.controller;

import lotto.domain.Game;
import lotto.domain.Lotto;
import lotto.domain.User;
import lotto.repository.GameRepository;
import lotto.repository.LottoRepository;
import lotto.service.GameService;
import lotto.service.LottoService;
import lotto.service.UserService;
import lotto.utils.Parser;
import lotto.validation.Validator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class PlayLottoGame {

    private final GameService gameService = new GameService();
    private final LottoService lottoService = new LottoService();
    private final UserService userService = new UserService();
    private final InputView inputView = new InputView();
    private final Validator validator = new Validator();
    private final LottoRepository lottoRepository = new LottoRepository();

    private final GameRepository gameRepository = new GameRepository();

    private final OutputView outputView = new OutputView();
    private final Parser parser = new Parser();

    private User user;

    private Game game;

    private static final int LEAST_AMOUNT = 1000;

    public void runLottoGame(){
        beforeStartGame();
        createNewGame();
        showGameResult();
    }


    private void beforeStartGame(){
        user = new User(inputUserAmount());
        lottoService.buyLottoAtOnce(user);
        showPurchaseResult();
    }

    private void createNewGame(){
        game = new Game(inputWinningNumbers(), inputBonusNumber());

    }

    private void showGameResult(){
        gameService.checkUserLotteries(user, game);
        outputView.winningStatistics();
        outputView.showRankResult(user.getLottoResult());
        outputView.showRateOfReturn(user.getRateOfReturn(user));
    }


    private int inputUserAmount(){
        String userInput = inputView.inputPurchaseAmount();
        int amount = isValidPurchaseAmount(userInput);
        return amount;
    }

    private void showPurchaseResult(){
        outputView.purchaseMessage(user.getPurchaseAmount() / LEAST_AMOUNT);
        for(Lotto lotto : user.getPurchasedLotteries()){
            outputView.lottoNumbers(lotto.getNumbers());
        }
    }
    private List<Integer> inputWinningNumbers(){
        String userInput = inputView.inputWinningNumbers();
        List<Integer> winningNumbers = parser.parseInputWinningNumbers(userInput);
        return winningNumbers;
    }

    private int inputBonusNumber(){
        String userInput = inputView.inputBonusNumber();
        int bonusNumber = parser.parseInputStringNumber(userInput);
        return bonusNumber;
    }

    public int isValidPurchaseAmount(String amount) {
        validator.isNumberCharInteger(amount);
        int number = parser.parseInputStringNumber(amount);
        validator.isMultipleNumber(number);
        return number;
        }

}
