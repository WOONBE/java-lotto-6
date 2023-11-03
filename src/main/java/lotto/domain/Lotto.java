package lotto.domain;

import lotto.validation.Validator;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;
    private final Validator validator = new Validator();

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    private void validate(List<Integer> numbers) {
        validator.isValidLength(numbers);
        validator.isDuplicateNumberInList(numbers);
        validator.isValidRangeNumberInList(numbers);

//        if (numbers.size() != 6) {
//            throw new IllegalArgumentException();
//        }
    }

    // TODO: 추가 기능 구현
}
