package ie.atu.sw;

@FunctionalInterface
interface Prompter {
    void prompt();
}

public interface Validator<T, R> {
    public R validate(Prompter showPrompt, T[] range);
}
